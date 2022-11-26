# dropwizard-joke-service

* ### Build and run service

    `mvn clean install && java -jar target/finx-joke-service-1.0.0.jar server application.yml`

* ### Testing 
  curl --location --request GET 'http://localhost:8080/api/v1/joke?search=abc'

