This is a simple authorization engine as a REST service. Resource access requests are modeled as basic JSON documents composed of a map of key-value pairs. The application applies several access control rules to each request to decide whether to permit or deny access. The response is entirely determined by the request's content and the configuration of the access control rules. It is designed for an environment that demands handling thousands of requests per second, with many being identical. Producing quick responses is crucial - the faster, the better.

The web service accepts HTTP POST requests at http://localhost:8080/authorize with a JSON-formatted payload in the body of the request. The JSON payload consists of key-value pairs, with values of either arbitrary strings, or integers.

An example payload:
```json
{
    "id": "123456789",
    "firstName": "John",
    "lastName": "Doe",
    "age": 41,
    "address": "123 Edinburgh Drive"
}
```

An example cURL command:
```shell
curl --location -v --request POST 'http://localhost:8080/authorize' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": "123456789",
    "firstName": "John",
    "lastName": "Doe",
    "age": 41,
    "address": "123 Edinburgh Drive"
}'
```

There are four different kinds of rule that can be applied to a resource request:
* `MATCH` - If the key exists, and the value for that key matches the regexp, produce PERMIT, otherwise produce DENY.
* `GREATER_THAN` - If the key exists, and the value for that key is greater than the number, produce permit, otherwise produce DENY.
* `LESS_THAN` - If the key exists, and the value for that key is less than the number, produce permit, otherwise produce DENY.
* `EXISTS` - If the key exists, produce PERMIT, otherwise produce DENY.

The application can be configured to apply more than one rule to the request context – these can be setup in: `resources/configuration.json`

Run the application using: `mvn clean spring-boot:run`

Run the tests using `mvn test`

The server port is set to `8080`, in the case of a conflict, this can be modified by adjusting `server.port` in `application.properties`
