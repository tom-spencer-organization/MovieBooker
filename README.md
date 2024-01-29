### Movie Booker

#### Overview

This application streamlines the process of booking movie tickets and managing movie programs for both moviegoers and staff.

#### Key Features:

##### User Roles:

*Movie Goer*: Can create accounts, book tickets, earn and redeem loyalty points.

*Staff*: Can add movies and programs.

##### Login and Registration:

The first screen prompts users to either log in or register for their respective roles.

##### Movie and Program Management (Staff):

Staff can add new movies with details like title, genre, release date, picture, and synopsis.
Staff can create programs for movies, specifying showtimes, available seats, screens, and pricing.

##### Ticket Booking (Movie Goer):

Movie goers can browse available movies and programs.
Payment is calculated based on the number of seats and ticket prices.

##### Loyalty Program:

Movie goers earn loyalty points for each booking.
Loyalty points can be redeemed for discounts on future bookings.
The payment calculation automatically considers loyalty points and applies discounts.

### Cloud Architecture
![image](https://github.com/TomSpencerLondon/LeetCode/assets/27693622/93bdbb05-03a0-4ebe-a6d4-37f9fc7d43b5)

### Local SETUP

- This is a monolithic maven project, built in java with [Thymeleaf](https://www.thymeleaf.org/). 
- You require java version 20 and node version 20 to run locally.
- The application stores and fetches data from database, therefore you need to get mysql database running locally.
- Staff upload movie images which are stored in S3 bucket 
- using [localstack](https://www.localstack.cloud/) we will have our own local running s3 bucket

#### Local DB and S3
```bash
cd /docker/
docker-compose up -d
```

#### Build the jar
```bash
mvn clean install
```

#### Running the script to set environment variables

```bash
chmod +x LocalEnvironmentVariablesSet.sh
. ./LocalEnvironmentVariablesSet.sh
```


#### Running the application with terminal

```bash
mvn spring-boot:run
and
npm run build && npm run watch
```

#### Running the application with IntelliJ IDE
Run below configuration:

```bash
.run/MovieBooker - Local Run.run.xml

```
# MovieBooker Application Deployment

## Step 1: Deploy the Surrounding Infrastructure

1. Clone the MovieBooker repository from GitHub:

    ```bash
    git clone https://github.com/tom-spencer-organisation/MovieBooker.git
    cd MovieBooker/cdk
    ```

2. Navigate to the cdk folder:

    ```bash
    cd cdk
    ```

3. Adjust the configuration in `cdk.json`:

   Edit the `cdk.json` file and modify the following parameters:
   - `applicationName`: Name of your application.
   - `region`: AWS region to deploy the infrastructure to (e.g., `eu-west-2`).
   - `accountId`: Your AWS account ID.
   - `dockerRepositoryName`: Name of your Docker repository (e.g., `todo-app`).
   - `dockerImageTag`: Docker image version.
   - `applicationUrl`: Full application URL.

4. Bootstrap CDK for your AWS account:

    ```bash
    npm install
    npm run bootstrap
    ```

5. Create an SSL certificate for your domain:

    ```bash
    npm run certificate:deploy
    ```

   Copy the `sslCertificateArn` to the `cdk.json` file.

6. Deploy the NetworkStack-dependent infrastructure:

    ```bash
    npm run network:deploy
    npm run database:deploy
    ```

7. Deploy NetworkStack-independent infrastructure:

    ```bash
    npm run repository:deploy
    ```

8. Route traffic from your custom domain to the ELB:

    ```bash
    npm run domain:deploy
    ```

## Step 2: Build and Push the First Docker Image

1. Build the first Docker image:

    ```bash
    cd ..
    mvn clean install
    docker build -t <YOUR_ECR_REGISTRY>/<YOUR_REPOSITORY_NAME>:1 .
    aws ecr get-login-password --region <YOUR_REGION> | docker login --username AWS --password-stdin <YOUR_ECR_REGISTRY>
    docker push <YOUR_ECR_REGISTRY>/<YOUR_REPOSITORY_NAME>:1
    ```

   On Apple M1:

    ```bash
    docker build --platform linux/amd64 --push -t <YOUR_ECR_REGISTRY>/<YOUR_REPOSITORY_NAME>:1 .
    ```

## Step 3: Deploy the Docker Image to the ECS Cluster

1. Customize the `dockerImageTag` property:

   Edit the `cdk/cdk.json` file and set the `dockerImageTag` property.

2. Deploy the service:

    ```bash
    npm run service:deploy
    ```

## Step 4: Destroy Everything

Run the following commands in reverse order to destroy resources:

```bash
npm run service:destroy
npm run domain:destroy
npm run repository:destroy
npm run database:destroy
npm run network:destroy
