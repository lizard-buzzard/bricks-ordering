Simple ordering system for a fictional organisation that sells bricks
=====================================================================
The task is to create a simple ordering system for a fictional organisation that sells bricks. No UI is required.

The design is guided by the junit tests.

Prerequisites
-------------
To build and run the examples, you must have the following items installed:
+ [JDK 8] (tested on java version "1.8.0_171" Java HotSpot(TM) 64-Bit Server VM, build 25.171-b11)
+ [Apache Maven] (tested on maven version 3.5.2)

The development and junit tests were done in Intellij Idea 2018.1.4 (Community Edition) in Ubuntu 18.04 LTS.
 
Build and install
-----------------
1. Get a copy of the code: git clone https://github.com/lizard-buzzard/bricks-ordering.git
2. Change into the bricks-ordering directory.
3. Type: mvn clean package
4. Copy the JAR file (brick-ordering-0.1.0.jar) from the target directory to the your working directory

Run server from command line
---------------------------
java -jar brick-ordering-0.1.0.jar

Run tests
---------
**First option**
The mvn goal test makes tests run from command line

mvn clean test

**Second option**

You can start tests in your IDE (for that example Intellij Idea 2018.1.4 (Community Edition) was used as IDE)

**Third option**

Test steps with java -jar executable by means of curl http requests (check that curl is installed).
+ ensure that there is no orders yet by /bricks_api/GetOrders GET call

$ curl http://localhost:8080/bricks_api/GetOrders

[]
+ create three orders by /bricks_api/CreateOrder POST call

$ curl -i -X POST -H "Content-Type:application/json" -d "{  \"bricks\" : \"7\" }" http://localhost:8080/bricks_api/CreateOrder

  HTTP/1.1 200 
  
  Content-Type: application/json;charset=UTF-8
  
  Transfer-Encoding: chunked
  
  Date: Mon, 04 Jun 2018 06:27:01 GMT
  
  {"id":1,"bricks":7,"isDispatched":"no"}

$ curl -i -X POST -H "Content-Type:application/json" -d "{  \"bricks\" : \"17\" }" http://localhost:8080/bricks_api/CreateOrder

  HTTP/1.1 200 
  
  Content-Type: application/json;charset=UTF-8
  
  Transfer-Encoding: chunked
  
  Date: Mon, 04 Jun 2018 06:28:00 GMT
    
  {"id":2,"bricks":17,"isDispatched":"no"}
 
  
$ curl -i -X POST -H "Content-Type:application/json" -d "{  \"bricks\" : \"27\" }" http://localhost:8080/bricks_api/CreateOrder

HTTP/1.1 200 

Content-Type: application/json;charset=UTF-8

Transfer-Encoding: chunked

Date: Mon, 04 Jun 2018 06:28:48 GMT

{"id":3,"bricks":27,"isDispatched":"no"}  
+ print list of created orders by /bricks_api/GetOrders

$ curl http://localhost:8080/bricks_api/GetOrders

[{"id":1,"bricks":7,"isDispatched":"no"},{"id":2,"bricks":17,"isDispatched":"no"},{"id":3,"bricks":27,"isDispatched":"no"}]

+ change number of bricks ordered for order #2 by /bricks_api/UpdateOrder/2 PUT call

$ curl -i -X PUT -H "Content-Type:application/json" -d "{\"bricks\" : \"2277\" }" http://localhost:8080/bricks_api/UpdateOrder/2

HTTP/1.1 200 

Content-Type: application/json;charset=UTF-8

Transfer-Encoding: chunked

Date: Mon, 04 Jun 2018 06:39:37 GMT

{"id":2,"bricks":2277,"isDispatched":"no"}

+ get description of the changed order by /bricks_api/GetOrder/2 GET call

$ curl http://localhost:8080/bricks_api/GetOrder/2

{"id":2,"bricks":2277,"isDispatched":"no"}

+ dispatch the changed order (#2) by /bricks_api/FulfilOrder/2 PUT call

$ curl -i -X PUT -H "Content-Type:application/json" -d "{\"isDispatched\" : \"yes\" }" http://localhost:8080/bricks_api/FulfilOrder/2

HTTP/1.1 200 

Content-Type: application/json;charset=UTF-8

Transfer-Encoding: chunked

Date: Mon, 04 Jun 2018 06:50:10 GMT

{"id":2,"bricks":2277,"isDispatched":"yes"}

+ try to dispatch an order with an invalid order reference (#123) by /bricks_api/DiapatchOrder/123 call. Ensure that HTTP/1.1 400 is returned

$ curl -i -X PUT -H "Content-Type:application/json" -d "{\"isDispatched\" : \"yes\" }" http://localhost:8080/bricks_api/DiapatchOrder/123

HTTP/1.1 404 

Content-Type: application/hal+json;charset=UTF-8

Transfer-Encoding: chunked

Date: Mon, 04 Jun 2018 07:08:03 GMT

{"timestamp":"2018-06-04T07:08:03.858+0000","status":404,"error":"Not Found","message":"No message available","path":"/bricks_api/DiapatchOrder/123"} 

+ try to change the order already dispatched, ensure that HTTP/1.1 400 is returned

$ curl -i -X PUT -H "Content-Type:application/json" -d "{\"bricks\" : \"3377\" }" http://localhost:8080/bricks_api/UpdateOrder/2

HTTP/1.1 400 

Content-Type: application/json;charset=UTF-8

Transfer-Encoding: chunked

Date: Mon, 04 Jun 2018 07:03:39 GMT

Connection: close

{"timestamp":"2018-06-04T07:03:39.564+0000","status":400,"error":"Bad Request","message":"No message available","path":"/bricks_api/UpdateOrder/2"}