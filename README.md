# Simple-e-com-project
A rookie project while in Nashtech

Database:
https://drive.google.com/file/d/14H272GfzP6Oy5U8Kl7yqrvYh9VIeE8B0/view?usp=sharing

This project use postgre container for database and redis container for blacklist token. No cart, order or payment yet.
In front end use react, typescript, build with vite, and call api with axios.

To run back end locally:

Pull the Back-End folder

Run:

./mvnw clean package -DskipTests
This create a package (like a jar file) while skipping test (cause it will check for data source if don't skip and fail. Check application.properties and docker compose file for data source and change as need).

Then run:

docker compose up --build 
This will run the container and init data for database in init.sql (check the docker compose file).
The init.sql will create default role for the app, and insert admin account.
