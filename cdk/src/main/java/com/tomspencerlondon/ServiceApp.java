package com.tomspencerlondon;


import dev.stratospheric.cdk.ApplicationEnvironment;
import dev.stratospheric.cdk.Network;
import dev.stratospheric.cdk.PostgresDatabase;
import dev.stratospheric.cdk.PostgresDatabase.DatabaseOutputParameters;
import dev.stratospheric.cdk.Service;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.secretsmanager.ISecret;
import software.amazon.awscdk.services.secretsmanager.Secret;
import software.constructs.Construct;

public class ServiceApp {

  public static void main(final String[] args) {
    App app = new App();

    String environmentName = (String) app.getNode().tryGetContext("environmentName");
    Validations.requireNonEmpty(environmentName, "context variable 'environmentName' must not be null");

    String applicationName = (String) app.getNode().tryGetContext("applicationName");
    Validations.requireNonEmpty(applicationName, "context variable 'applicationName' must not be null");

    String accountId = (String) app.getNode().tryGetContext("accountId");
    Validations.requireNonEmpty(accountId, "context variable 'accountId' must not be null");

    String dockerRepositoryName = (String) app.getNode().tryGetContext("dockerRepositoryName");
    Validations.requireNonEmpty(dockerRepositoryName, "context variable 'dockerRepositoryName' must not be null");

    String dockerImageTag = (String) app.getNode().tryGetContext("dockerImageTag");
    Validations.requireNonEmpty(dockerImageTag, "context variable 'dockerImageTag' must not be null");

    String region = (String) app.getNode().tryGetContext("region");
    Validations.requireNonEmpty(region, "context variable 'region' must not be null");

    String hostedZoneDomain = (String) app.getNode().tryGetContext("hostedZoneDomain");
    Validations.requireNonEmpty(hostedZoneDomain, "context variable 'hostedZoneDomain' must not be null");

    Environment awsEnvironment = makeEnv(accountId, region);

    ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment(
        applicationName,
        environmentName
    );

    // This stack is just a container for the parameters below, because they need a Stack as a scope.
    // We're making this parameters stack unique with each deployment by adding a timestamp, because updating an existing
    // parameters stack will fail because the parameters may be used by an old service stack.
    // This means that each update will generate a new parameters stack that needs to be cleaned up manually!
    long timestamp = System.currentTimeMillis();
    Stack parametersStack = new Stack(app, "ServiceParameters-" + timestamp, StackProps.builder()
        .stackName(applicationEnvironment.prefix("Service-Parameters-" + timestamp))
        .env(awsEnvironment)
        .build());

    String stackPrefix = "Service";

    Stack serviceStack = new Stack(app, "ServiceStack", StackProps.builder()
        .stackName(applicationEnvironment.prefix(stackPrefix))
        .env(awsEnvironment)
        .build());

    PostgresDatabase.DatabaseOutputParameters databaseOutputParameters =
        PostgresDatabase.getOutputParametersFromParameterStore(parametersStack, applicationEnvironment);
    List<String> securityGroupIdsToGrantIngressFromEcs = Arrays.asList(
        databaseOutputParameters.getDatabaseSecurityGroupId()
    );

    new Service(
        serviceStack,
        stackPrefix,
        awsEnvironment,
        applicationEnvironment,
        new Service.ServiceInputParameters(
            new Service.DockerImageSource(dockerRepositoryName, dockerImageTag),
            securityGroupIdsToGrantIngressFromEcs,
            environmentVariables(
                serviceStack,
                databaseOutputParameters,
                environmentName))
            .withCpu(512)
            .withMemory(1024)
            .withTaskRolePolicyStatements(List.of(
                PolicyStatement.Builder.create()
                    .sid("AllowSendingEmails")
                    .effect(Effect.ALLOW)
                    .resources(
                        List.of(String.format("arn:aws:ses:%s:%s:identity/%s", region, accountId, hostedZoneDomain))
                    )
                    .actions(List.of(
                        "ses:SendEmail",
                        "ses:SendRawEmail"
                    ))
                    .build()
            ))
            .withStickySessionsEnabled(true)
            .withHealthCheckPath("/actuator/health")
            .withAwsLogsDateTimeFormat("%Y-%m-%dT%H:%M:%S.%f%z")
            .withHealthCheckIntervalSeconds(30), // needs to be long enough to allow for slow start up with low-end computing instances
        Network.getOutputParametersFromParameterStore(serviceStack, applicationEnvironment.getEnvironmentName()));

    app.synth();
  }

  static Map<String, String> environmentVariables(
      Construct scope,
      DatabaseOutputParameters databaseOutputParameters,
      String environmentName
  ) {
    Map<String, String> vars = new HashMap<>();

    String databaseSecretArn = databaseOutputParameters.getDatabaseSecretArn();
    ISecret databaseSecret = Secret.fromSecretCompleteArn(scope, "databaseSecret", databaseSecretArn);

    vars.put("SPRING_DATASOURCE_URL",
        String.format("jdbc:mysql://%s:%s/%s",
            databaseOutputParameters.getEndpointAddress(),
            databaseOutputParameters.getEndpointPort(),
            databaseOutputParameters.getDbName()));
    vars.put("SPRING_DATASOURCE_USERNAME",
        databaseSecret.secretValueFromJson("username").unsafeUnwrap());
    vars.put("SPRING_DATASOURCE_PASSWORD",
        databaseSecret.secretValueFromJson("password").unsafeUnwrap());
    vars.put("ENVIRONMENT_NAME", environmentName);
    return vars;
  }

  static Environment makeEnv(String account, String region) {
    return Environment.builder()
        .account(account)
        .region(region)
        .build();
  }
}

