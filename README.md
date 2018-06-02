a simple ordering system for a fictional organisation that sells bricks

$ curl -i -X POST -H "Content-Type:application/json" -d "{  \"bricks\" : \"17\",  \"orderSpec\" : \"YYYvvv\" }" http://localhost:9000/order
HTTP/1.1 201 
Location: http://localhost:9000/order/1
Content-Type: application/hal+json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sat, 02 Jun 2018 07:03:48 GMT

{
  "bricks" : 17,
  "_links" : {
    "self" : {
      "href" : "http://localhost:9000/order/1"
    },
    "customerOrder" : {
      "href" : "http://localhost:9000/order/1"
    }
  }
}

$curl -i -X POST -H "Content-Type:application/json" -d "{  \"bricks\" : \"22\",  \"orderSpec\" : \"ZZZZZZZfffffff\" }" http://localhost:9000/order
HTTP/1.1 201 
Location: http://localhost:9000/order/2
Content-Type: application/hal+json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sat, 02 Jun 2018 07:03:59 GMT

{
  "bricks" : 22,
  "_links" : {
    "self" : {
      "href" : "http://localhost:9000/order/2"
    },
    "customerOrder" : {
      "href" : "http://localhost:9000/order/2"
    }
  }
}

$ curl  http://localhost:9000/order/search/GetOrders
{
  "_embedded" : {
    "CustomerOrder" : [ {
      "bricks" : 17,
      "_links" : {
        "self" : {
          "href" : "http://localhost:9000/order/1"
        },
        "customerOrder" : {
          "href" : "http://localhost:9000/order/1"
        }
      }
    }, {
      "bricks" : 22,
      "_links" : {
        "self" : {
          "href" : "http://localhost:9000/order/2"
        },
        "customerOrder" : {
          "href" : "http://localhost:9000/order/2"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:9000/order/search/GetOrders"
    }
  }
}

$ curl  http://localhost:9000/order/search/GetOrder?id=2
{
  "bricks" : 22,
  "_links" : {
    "self" : {
      "href" : "http://localhost:9000/order/2"
    },
    "customerOrder" : {
      "href" : "http://localhost:9000/order/2"
    }
  }
}

