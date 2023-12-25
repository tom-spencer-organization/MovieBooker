### Movie Booker

### Design (22 March 2023)
![image](https://user-images.githubusercontent.com/27693622/226953854-95fc8aff-9684-49f9-8891-2d7fa1126037.png)

### Updated Design
![image](https://user-images.githubusercontent.com/27693622/226958765-a7f662da-0418-4c15-ab27-8713d1caf92b.png)

### Cloud Architecture
![image](https://github.com/TomSpencerLondon/LeetCode/assets/27693622/93bdbb05-03a0-4ebe-a6d4-37f9fc7d43b5)

```bash
mvn clean install spring-boot:run
and
npm run build && npm run watch
```
#### Run with maven
```bash
cd /docker/
docker-compose up -d
```

```bash
mvn clean install
```

```bash
mvn clean install spring-boot:run
and
npm run build && npm run watch
```


Notes on talk:

1. Keep it simple
2. Explain what the CDK is
3. Do a simple version - S3 bucket
4. What people hope to get from talk:
   - Understanding of CDK
   - How to do it in Java
   - Pros and Cons -- advantage versus terraform
Demo - S3 bucket
   - Terraform version of S3 bucket
based on Stratospheric book and CDK
---
- Email - how I solved the problem of spam message in email (blog post)
problem how overcame it:
https://levelup.gitconnected.com/easily-create-email-addresses-for-your-route53-custom-domain-589d099dd0f2

_dmarc.tomsawspractice.link
TXT
"v=DMARC1;p=quarantine;pct=25;rua=mailto:dmarcreports@example.com"