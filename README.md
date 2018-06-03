Test steps with java -jar executable by means of curl http requests for Stage 1 and Stage 2

grig@grig:~$ curl http://localhost:9000/bricks_api/GetOrders
[]

grig@grig:~$ curl -i -X POST -H "Content-Type:application/json" -d "{  \"bricks\" : \"7\" }" http://localhost:9000/bricks_api/CreateOrder
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sun, 03 Jun 2018 09:36:21 GMT

{"id":1,"bricks":7}

grig@grig:~$ curl -i -X POST -H "Content-Type:application/json" -d "{  \"bricks\" : \"17\" }" http://localhost:9000/bricks_api/CreateOrder
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sun, 03 Jun 2018 09:36:35 GMT

{"id":2,"bricks":17}

grig@grig:~$ curl -i -X POST -H "Content-Type:application/json" -d "{  \"bricks\" : \"27\" }" http://localhost:9000/bricks_api/CreateOrder
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sun, 03 Jun 2018 09:36:44 GMT

{"id":3,"bricks":27}

grig@grig:~$ curl -i -X PUT -H "Content-Type:application/json" -d "{\"id\" : \"2\",  \"bricks\" : \"2277\" }" http://localhost:9000/bricks_api/UpdateOrder/2
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sun, 03 Jun 2018 09:37:04 GMT

{"id":2,"bricks":2277}

grig@grig:~$ curl http://localhost:9000/bricks_api/GetOrders
[{"id":1,"bricks":7},{"id":2,"bricks":2277},{"id":3,"bricks":27}]

grig@grig:~$ curl http://localhost:9000/bricks_api/GetOrder/2
{"id":2,"bricks":2277}grig@grig:~$
