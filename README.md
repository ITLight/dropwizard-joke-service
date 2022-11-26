# dropwizard-joke-service

* ### Build and run service

    `mvn clean install && java -jar target/finx-joke-service-1.0.0.jar server application.yml`

* ### Testing 
  curl --location --request GET 'http://localhost:8080/api/v1/joke?search=abc'

* ### Example

  #### Request: `GET http://localhost:8080/api/v1/joke?search=abc`
 
  #### Response: 
```yaml 
  {
    "code":1,
    "message":"The request is handled successfully",
    "searchText":"abc",
    "data":["Chuck Norris is not as smart as Einstein. It is impossible for Einstein to arrange M&M's in abc order but Chuck Norris can."]
  }
