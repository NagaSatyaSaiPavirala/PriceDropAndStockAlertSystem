# PriceDropAndStockAlertSystem 

Designed Price Drop And Stock Alert System using High Level Design and Low Level Design concepts using Springboot, React, Bootstrap, HTML, CSS, Javascript, Cassandra, Kafka, Postman, Nginx load balancer, Docker images, Jmeter, Redis cache, Mongo, Zuul API gateway, Eureka Discovery service, Observer design pattern.

## Register Page, Bcrypt hashing:

The user registers in the system by filling the details like name, mobile number, email, password and clicking the submit button in the user registration page. Then a random 5 digit OTP is sent to the user-provided email with admin mail as sender mail. Once the user enters the correct OTP, then only the user will be registered and the details will be stored in the user table which contains userid, email, mobile number, password, role, username. The userid will be filled with the user id counter value from the sequence table; once it is consumed, the user id value will be incremented by one in the sequence table. The reason is Cassandra doesn't have an auto-increment feature. Remaining values like email, mobile number, and username are filled normally; by default, role is filled as role_user and password is stored in Bcrypt hash format like $2a$10$adf.. where $2a represents bcrypt hashing and $10 represents workfactor, meaning the number of iterations used to hash the password. Once the user is registered successfully, the user is redirected to the login page.

## Login Page, Authentication:

In the Login page, the user enters his email and password. Then in the backend, some logic is executed to check the presence of email in the user table, and again bcrypt hash of the user-given password is calculated and is compared with the hashed password in the user table based on work factor. The hashed passwords may not look the same, but they imply the same plain text password, meaning a single plain text password can have many hashed passwords with the same work factor. If the user is validated, then authenticated changes to true, and once the user is logged in, the entire session depends on the session object which contains all user details and products added by that user.

## Dashboard Page:

In the dashboard page, there are two buttons: add product and product list.

## Add Product Page, Jsoup scraping, Incremental fetch, Redis cache, Cassandra, Kafka queueing service and topics, Mongodb:

When the user clicks the add product button in the dashboard and enters the product URL and threshold price in the opened form page and clicks the submit button, then the URL table is checked for the product URL. The URL table contains URLid, product URL, product image URL, product name, times processed, content type, last processed, created date. If the product URL already exists in the URL table, then check for the last processed time value; if the last processed value is within 7 days, we check the cache; else, we won't check the cache. This technique of using the last processed value to check the cache is called incremental fetch, where we scrape data only if required, reducing the API calls required. It took 12 seconds without incremental fetch and took 8 seconds with incremental fetch when tested with MOZ Top 500 Websites dataset. The cache is checked for the presence of the product; Redis cache is checked with the key as product URL. If there is a product, it implies the product has been added by the user in the past 7 days; this relies on the property I used in application.properties file which is TTL=7 days. If it is present in the cache, retrieve the details like product image URL and product name, which are stored in hash set data structure and are retrieved using the product URL key. Without using cache, it took 3 seconds, and by using cache, it took 1 second when tested with MOZ Top 500 Websites dataset. If the product is not present in the cache, then the jsoup code which I wrote scrapes the product name with id "product title", product image with id "landing image", product price with id "open.a-size-medium.a-color-success". The scraped details are sent as a message in the JSON format which contains unique ID by using Universal Unique Identifier in Java code, product URL, product image URL, product name, times processed, content type, last processed, created date. The timesprocessed increments for every URL entry by the user. The content type is either stock or price; if the scraped price is not null, then the message is sent to the price topic. If the scraped price is currently unavailable, implying null, the message is sent to the stock topic, and later these messages are used to fill the URL table and product API table. At the same time, the details like ID, URL ID, product URL, product name, category, product price, product availability, product image URL, and reviews are stored as JSON format in web_collections of MongoDB. This data in MongoDB can be used to analyze the product to make better purchase decisions.

## Significance of Kafka in improving throughput of database:

The usage of Kafka messages to fill the database improves the throughput of the database as we can do bulk writes instead of traditional single writes; however, the reads will be single reads in both cases. The product API table contains product ID, product image URL, product name, product price, product URL. If the product URL is new, it won't be present in the product API table, so the scraped details in Kafka message are filled normally into the product API table, and the product ID will be filled with the product ID counter value from the sequence table and is then incremented by one by my code. The reason is Cassandra doesn't have an auto-increment feature. Then the product table, which contains product ID, product name, product URL, price, topic, and user ID, are filled. User ID is filled based on the session object, and product ID is filled with the same value as the product API table. If the product URL already exists in the product API table, then the same product ID of the product API table will be used to fill the product table. It takes less time to access message data from Kafka queue than accessing the database data, as Kafka uses RAM to cache frequently used data.

## Optimal processor threads:

Among the two types of threads: Java non-blocking I/O threads and processor threads, using the processor threads is a better decision choice as my system doesn't require I/O operations but requires some processing capability power. It took 7 seconds without using processor threads and using traditional non-blocking I/O threads, and it took 3 seconds by using optimal processor threads and using threadpool configuration.

## Products List Page and finding and deleting product using product ID and threshold price in product table:

When the user clicks the products list link, a page is opened which contains scraped product images, scraped product name, product current price, and threshold price from the product table. Two buttons: edit and delete are also displayed. Clicking the edit button will open a form where the user can update product name, product URL, threshold price, which will be updated in the product table as the product table is specific for the user. I am maintaining a combination of product ID and threshold price as the primary key, so the user can add the same product multiple times with different threshold prices. When the user clicks the delete button, the product will be deleted by deleting the row from the product table by using a delete SQL query with a combination of product ID and threshold price, as there can be the same product added by the user multiple times with different threshold prices. Similarly, for finding a product uniquely in the product table, I used a combination of product ID and threshold price.

## Sending mail using SMTP (Simple Mail Transfer Protocol) by using observer design pattern and deletion of entry from product table:

The system periodically scrapes the current price by using every product URL of the product table and is checked with the threshold price every 10 minutes. If the current price is less than or equal to the threshold price, then based on the topic in the product table, the corresponding event is triggered. Event may be price or stock. I designed the code using the observer design pattern, so the subscriber (user) need not busy wait for the notification; instead, the publisher (system) will send a notification in case of event triggering. The mail comprises product name, threshold price, current price, and product URL with which the user can buy the product. The sender mail is admin mail, and the subject line will be "Price is dropped!!" if it is a price alert and "Stock is back!!" if it is a stock alert. Once the email is sent to the user using the SMTP protocol, then the entry will be deleted from the product table and will be removed from the dashboard, as the dashboard displays products added by a particular user into the product table.

## Database decision choice between MySQL and NoSQL Cassandra:

I initially built the system using MySQL, later migrated to NoSQL Cassandra database because my system doesn't require any atomic operations, and using MySQL is a poor design choice. Using Cassandra is an optimal choice as we just require only key-value pairs, and I am using a replication factor of 3 in Cassandra database, which is the golden number used industrially, meaning the data is replicated across 3 nodes. MySQL offers consistency and availability, but my system doesn't require consistency; in my case, even eventual consistency is sufficient, so I am using Cassandra, which offers high availability and partition tolerance.

## Disadvantage of hardcoding URLs in Zuul API gateway:

I used Netflix Zuul API gateway where I hardcoded URL patterns to render code on the browser. In my case, to run React code which actually runs on port number 3000 with URL *http://localhost:3000*, after hardcoding URL with pattern "pricedropreact", I can run the same code on *http://localhost:5000/pricedropreact*. Similarly, we can run Spring Boot code which used to run earlier on port 8080 with URL *http://localhost:8080* by the URL *http://localhost:5000/pricedropspring* by hardcoding with pattern "pricedropspring" in the gateway service.

## Advantage of displaying running services in Eureka Discovery service:

To overcome the problem of hardcoding URLs and remembering the pattern for every URL, which is like remembering contact numbers of people, I used Eureka Discovery service, which displays all the running services on the URL *http://localhost:9001*. The discovery service is like a contact book where we can see the running services instead of remembering the patterns for your URLs.

## Load balancing using Nginx:

I created the Java archive file for the entire code repository and I ran the JAR file on ports 8080, 8081, 8082, and they were accessible by using URLs *http://localhost:8080*, *http://localhost:8081*, *http://localhost:8082*. Next, I configured the nginx.conf file by setting the listening server on port 7070 and the port numbers 8081 8082 8083 in the upstream backend servers in nginx.conf file by default the load balancing is round robin and the system will be accessible on port 7070 using URL *http://localhost:7070* and any request given by the user is uniformly distributed among services which are running on port numbers 8080,8081,8082 in round robin manner.We can also configure upstream backend servers as least connection if we want traffic to be routed to devices with less connections if we want to the traffic to be routed to the same server which handled the user request previously we can use IP hash.If we have different server with different processing capabilities we can configure nginx.conf file by specifying weights by giving more weight to highgest processing capability server.Apart from load balancing Nginx also serves as a reverse proxy meaning having single client and many servers the nginx server routes request to matching server and the user is unaware of which server handled the user request.Reverse proxy is opposite of forward proxy for example VPN is a forward proxy where there are multiple users sending requests to server and these requests are passed through VPN and server doesn't know from which client the request came.Nginx also offers HTTP caching where Nginx caches frequent accessed data within its server cache.It took 10 seconds without load balancing and it took 2 seconds with round robin load balancing when tested for MOZ Top 500 Websites dataset.

##Optimisation techniques and performance testing using Jmeter:
The performance of the system can be tested using in case of heavy user request traffic by simulating traffic in Jmeter for testing purpose i checked with MOZ Top 500 Websites dataset and with Kaggle Top 10 Million Dataset for larger dataset without using the techniques like incremental fetch,load balancer,optimal processor threads,caching it took around 1222 minutes and after using techniques it took around 588 minutes where the time is reduced by by 51.87%.

##Microsoft Internet Information Services(IIS), Docker, Actuators, Kubernetes:
I hosted some e-commerce product webpages Microsoft Internet Information Services(IIS) for testing the working of code instead of sending traffic to the actual e-commerce websites during testing phase.I am using docker for containerisation so that it avoids dependency issues and my system can run on multiple devices with the same preset configurations and environment variables.Iam using open source docker images of Cassandra database, Redis cache, Nginx load balancer, Mongo database, Kafka queueing service.I am using actuators for checking the health of the system and various other parameters of system using the URL *http://localhost:8080/actuator*. I am using Kubernetes for container orchestration and easy management of the system and for easy starting and stopping of services.


##Problem with using Nginx load balancer alone, Kubernetes deployment:
The round robin strategy we used in load balancing is creating problem of session data loss while using application to solve this i changed the strategy to ip_hash where the user request will be directed to the same system everytime which handled the user's request previously so the session data is not lost and can be even benefited by the caching feature of nginx.The earlier nginx we developed by running on 2 ports is facing problems when any service deployed on the ports 8080,8081,8082 is down then the entire application is failing to solve this i create deployment.yaml which when applied using kubectl will be creating pricedrop-dep which internally creates 3 pods which will automatically start when any of the pod is down and then i created nginx.yaml with ip_hash strategy which handles the requests internally on the 3 pods.The running status of deployment and pods can be seen on the Kubernetes dashboard with the url *https://localhost:8443*.

##Problem of local hosting:
The application deployed on local machine can be accessible over devices which are connected with same network as local machine for example we can access the application using wifi ip address but the problem is the wifi address is dynamic and can change overtime remembering it and checking ip address everytime using ip config command is a difficult task and the other problem is the application doesn't run when the local machine is turned off.To address above problems the solution i proposed is to deploy the application on virtual machine which has static ip address and the application runs 24*7 still the problem with this is remembering the ip address of the VM to access the application to overcome this problem i configured DNS with iittp domain so there is no need to remember ip address.The IP address assigned to virtual machine by my college is 10.21.4.56 and the domain i got configured is pdsa.iittp.ac.in and i deployed my application on the virtual machine from now on the earlier links which have localhost can be replaced with pdsa.iittp.ac.in and my application is accessible with the url *http://pdsa.iittp.ac.in* and the url will be accessible only on devices connected to college network as virtual machine ip is private to college and the GitHub url for my project is *https://github.com/NagaSatyaSaiPavirala/PriceDropAndStockAlertSystem*.



## Running Instructions

First start redis, mongo, cassandra containers

```bash
docker start redis mongo Cassandra
```

Navigate to the kafka folder and use below docker compose command

```bash
docker compose up -d
```

Build the react code

```bash
npm run build
```

Build docker image as react-frontend for react code

```bash
docker build -t react-frontend .
```

Run the image on react-frontend container exposing port number 3000

```bash
docker run -d --name react-frontend -p 3000:8080 react-frontend
```

Create jar file for discovery spring boot code

```bash
mvn clean install -DskipTests
```

Build docker image as discovery for spring boot code

```bash
docker build -f Dockerfile -t discovery .
```

Run the image on discovery container exposing port number 9001

```bash
docker run -d --name discovery -p 9001:9001 discovery
```

Create jar file for pricedrop spring boot code

```bash
mvn clean install -DskipTests
```

Build docker image as pricedrop for spring boot code

```bash
docker build -f Dockerfile -t pricedrop .
```

Run the image on pricedrop container exposing port number 8080. If wanted load balancing skip below command

```bash
docker run --name pricedrop -p 8080:8080 pricedrop
```

Run the Kubernetes dashboard exposing port number 8443

```bash
kubectl -n kubernetes-dashboard port-forward svc/kubernetes-dashboard-kong-proxy 8443:443
```

Apply deployment.yaml file

```bash
kubectl apply -f deployment.yaml
```

Apply nginx.yaml file

```bash
kubectl apply -f nginx.yaml
```

Forward nginx service to port number 8080

```bash
kubectl port-forward service/nginx-service 8080:8080
```


