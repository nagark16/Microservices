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