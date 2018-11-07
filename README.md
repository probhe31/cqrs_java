# Event Sourcing and CQRS Examples

## Trying it out
### Requirements
- Java 8
- Maven

### Building the application
` mvn clean verify `

### Starting the application
` java -jar target/bank-service-1.0-SNAPSHOT.jar server src/environments/development.yml `

### Examples of use
#### Create a client
` curl -vvv -X POST -H "Content-Type: application/json" -d '{"name":"Jane Doe", "email":"jane.doe@example.com"}' http://localhost:8080/clients `

Check the created client from the response 'Location' header

#### Create an account for the client
` curl -vvv -X POST -H "Content-Type: application/json" -d '{"clientId":"{clientId}"}' http://localhost:8080/accounts `

Check the created account from the response 'Location' header

#### Make a deposit to the account
` curl -vvv -X POST -H "Content-Type: application/json" -d '{"amount":1000000}' http://localhost:8080/accounts/{accountId}/deposits `

#### Check that you created a millionare!
` curl -vvv http://localhost:8080/accounts/{accountId} `

#### More operations
Go ahead and check the code! :)