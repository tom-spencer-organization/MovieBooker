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