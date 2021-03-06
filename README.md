# Microservices
Rest and Microservices hands-on

All the code developed using Java and Spring Cloud in eclipse oxygen

REST is a architectural approach
SOAP have restrictions on format of the XML which is exchanged between client & server

REST stands for Representational State Transfer

* Swagger -- documentation format for RESTful services
	* http://localhost:8080/v2/api-docs
	* http://localhost:8080/swagger-ui.html

* Boot actuator -- a monitoring tool
	* http://localhost:8080/actuator

* Versioning:
	1. in url (v1/Person)
	2. in url as param (/person?version=1)
	3. in header 

* Richardson Maturity Model
	* level 0 -- expose soap web services in rest style (getuser,deleteuser)
	* level 1 -- expose resources with proper uri. improper user of http methods 
	* level 2 -- level 1 + http methods
	* level 3 -- level 2 + HATEOAS -- DATA + Next possible actions

* Microservices: REST + small well chosen deployable units + cloud based

* Microservice challenges
	* bounded context -- defining boundaries for each microservice
	* configuration management -- configuring many microservices in different environments 
	* dynamic scale up & scale down -- on the fly increase/decrease instances of microservice as per incoming requests
	* visibility -- need to know what is happening behind microservices as a whole as a functionality can be dividied among many microservices
	* pack of cards -- whole microservices system depend one on another if one fails whole system falls like pack of cards

* Spring cloud
	* for configuration management -- spring cloud config server
	* for dynamic scale up & scale down -- Eureka(naming server), ribbon(client side load balancing), Feign(Easier REST clients)
	* for visibility & monitoring -- Zipkin distributed tracing, Netflix API Gateway
	* for fault tolerance -- Hystrix

* Adv of Microservices
	* process adaption -- different microservice can be developed in language
	* dynamic scaling
	* faster release cycles

* To enable as a spring cloud config server we should give "@EnableConfigServer"
* For every microservice we should give "spring.application.name" in application.properties??
* Any changes to be picked by spring cloud config server should be committed to git	
* The files to be picked by spring cloud config server as different profiles should suffix as dev or qa (i.e., limits-service-qa.properites, limits-service-dev.properites)

* To access SpringCloudConfigServer
	1. rename application.properties to bootstrap.properties
	2. update bootstrap.properties to link spring cloud url (i.e., spring.cloud.config.uri=http://localhost:8888)

* To access specific profiles (i.e., developer, qa), in boostrap.properties add lines like "spring.profiles.active=dev" or "spring.profiles.active=qa"

* In a project we have to add eureka dependency and add annotation of ``` @EnableDiscoveryClient ``` for a project to be added to naming server. Also we have add ``` "eureka.client.service-url.default-zone = http://localhost:8761/eureka" ``` in application.properties of the project
	```
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
	</dependency>
	```
	ribbon will talk to eureka and get running instances

* API gateways -- Zuul API gateway
	* authentication, authorization and security
	* rate limits
	* fault tolerance
	* serivce aggregation

* 3 steps to setup Zuul server
	1. create a component
	2. should decide what it should do when it intercepts a request
	3. make sure all important requests are getting passed through Zuul API gateway

* after deploying zuul application at 8765 we can access other application via 
	```
	http://localhost:8765/{application-name}/{uri}															
		ex: http://localhost:8765/currency-exchange-service/currency-exchange/from/EUR/to/INR       (no captials)
	```

* Zuul uses app name in the url to talk to Eureka and find the url of the service
	```
	http://localhost:8100/currency-converter-feign/from/EUR/to/INR/quantitity/1000
	http://localhost:8765/currency-conversion-service/currency-converter-feign/from/EUR/to/INR/quantitity/1000
	```

###### Distributed Tracing 
* Zipkin -- http://localhost:9411/zipkin
* assign unique id to each request spring cloud sleuth
	```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-sleuth</artifactId>
	   	</dependency>
	```
* in server application (i.e., in @SpringBootApplication class file)
	```	
		@Bean
		public Sampler defaultSampler() {
			return Sampler.ALWAYS_SAMPLE;
		}
	```
* centralized log -- Rabbit MQ, Elastic search, kibana(ELK stack)
* We can use elastic search to search through consolidated log
* to start zipkin: first we have to get zipkin via https://zipkin.io/pages/quickstart
	* set RABBIT_URI=amqp://localhost
	* java -jar zipkin-server-2.7.0-exec.jar
	```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-sleuth-zipkin</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-bus-amqp</artifactId>
		</dependency>
	```
	make sure rabbit MQ service is running

###### Spring Cloud Bus 
* will make all instances of microservices pick latest data
	```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-bus-amqp</artifactId>
		</dependency>
	```
* management.endpoints.web.exposure.include=* -- disables management security
* to get latest data we have to do POST request from postman with url http://localhost:8080/actuator/bus-refresh

###### Hystrix
* we do fault tolerance with Hystrix 
* when one of the dependent service is down instead of taking whole system down we can return default behaviour
	```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix</artifactId>
		</dependency>
	```	
* in spring boot application enable via @EnableHystrix
* on all commands in rest controller we can write default behaviour via @HystrixCommand(fallbackMethod="some method name")

###### Microservices characteristics
	* small, specific functionality
	* independent deployment
	* simple communication(HTTP via RestFul)
	* stateless(so scaled up or down)
	* automated build and deployment