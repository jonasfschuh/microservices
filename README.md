# Credit Cards Microservices 

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Keycloak](https://img.shields.io/badge/Keycloak-blue?style=for-the-badge&logo=keycloak&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

This project controls credit cards, customers, credit assessment and processes card issuance requests through a complete microservices architecture.

- Java 17
- Spring Cloud/Boot 2.7.0 Modules
- Service Discovery
- Api Gateway
- Load Balancing
- Synchronous and Asynchronous Microservices Communication
- Messaging Service/Queue with RabbitMQ
- Authorization Server with Keycloak
- Custom Images and Containers with Docker
- Microservices Replicas

## Table of Contents

- [Installation](#installation)
- [Configuration for local environment](#Configuration-for-local-environment)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)
- [Database](#database)
- [Contributing](#contributing)
- [Configuration for production environment](#Configuration-for-production-environment)

## Installation

1. Clone the repository:

```bash
git clone https://github.com/kenzor1979/microservices
```
2. Install dependencies with Maven

3. Install [Docker](https://www.docker.com/)

4. Install [RabbitMQ](https://www.rabbitmq.com/) 
Install docker image "rabbitmq:3.13-management" and create a container with docker
In the rabbitMQ, create a new queue "card-issuance"

5. Install [Keycloak](https://www.keycloak.org/) image "quay.io/keycloak/keycloak:18.0.0" and create a container with docker
In keycloak, create a new realm with the import option. The "realm-export.json" file is located inside the "mskeycloak" folder. There are two files. "Realm-local" for the local environment and "realm-export.json" for the production environment that will be accessed by the microservices containers.

## Configuration for local environment

1. Start the RabbitMQ and Keycloak containers with docker

![Docker containers](https://github.com/kenzor1979/microservices/blob/main/images/docker_containers.gif?raw=true&sanitize=true)

2. Start the application with [Maven](https://maven.apache.org/) or [IntelliJ IDEA](https://www.jetbrains.com/idea/)

notes:
Preferably start the applications in this order:

![Order to start applications](https://github.com/kenzor1979/microservices/blob/main/images/order_to_start_applications.gif?raw=true&sanitize=true)

3. The APIs will be accessible on the eureka server at http://localhost:8761/
![Eureka Server](https://github.com/kenzor1979/microservices/blob/main/images/eurekaserver.gif?raw=true&sanitize=true)

4. To access the swagger of each instance, click on the instance address in the eureka server to open the link in a new page. Add address + "/swagger-ui/index.html"
For example, to access the client microservice address: 
http://host.docker.internal:55012/swagger-ui/index.html

All available endpoints will be displayed.

![Swagger-ui](https://github.com/kenzor1979/microservices/blob/main/images/swagger-ui.gif?raw=true&sanitize=true)


## API Endpoints

The APIs provide the following endpoints:

```markdown
msclients

GET   /clients - Retrieves the status of the API
GET   /clients?cpf=123 - Retrieves all clients registered for that CPF
POST  /clients - Persists the client's credit card data. 

{
	"nome": "person",
	"cpf": "123",
	"idade": 14 
}

mscards

GET   /cards - Retrieves the status of the API
GET   /cards?renda=10000 - Retrieves all cards with income up to
GET   /cards?cpf=123 - Retrieves all cards registered for that CPF
POST  /cards - Persists the credit´s card data. 

{
    "nome": "bradesco visa", 
    "bandeira": "VISA", 
    "renda": "5000", 
    "limite": "8000"
}

mscreditassessor

GET   /credit-assessor - Retrieves the status of the API
POST  /credit-assessor - Register a new credit assessment

{
    "cpf": "123",     
    "renda": "5000"     
}

GET   /credit-assessor/customer-status?cpf=123 - Retrieves all credit ratings registered for that CPF
POST  /credit-assessor/solicitacoes-cartao - Registers a credit card request and returns a request protocol that is sent to the messaging service

{
    "idCartao": "1",
    "cpf": "123",   
    "endereco": "Rua Benjamin Constant",
    "limiteLiberado": 11200
}

eurekaserver
Eureka server address on port 8761
http://localhost:8761/


mscloudgateway
Note: All cards, clients and credit-assessor API instances will be managed by the load balancer through the gateway at the address:

http://host.docker.internal:8080/


http://localhost:8081/realms/msrealm/protocol/openid-connect/token


```

## Authentication

The APIs use Spring Security and Keycloak with OAuth 2.0 for authentication control.
The following roles are available:

```
Keycloak:

user: admin 
password: admin
```

All endpoints are protected. There are two Keycloak endpoints, one for local environment and one for access between containers.

Example of Keycloak for local authentication request with [Postman](https://www.postman.com/)

![Generate Bearer Token Local](https://github.com/kenzor1979/microservices/blob/main/images/generateBearerTokenLocal.gif?raw=true&sanitize=true)

Authorization type is Bearer Token.

Generated token example
![Generated token example](https://github.com/kenzor1979/microservices/blob/main/images/generatedToken.gif?raw=true&sanitize=true)

Example of authorized authentication to use our endpoints
![Authorized authentication example](https://github.com/kenzor1979/microservices/blob/main/images/authorized%20authentication%20example.gif?raw=true&sanitize=true)

notes:
The process to generate the authorization token for the microservices within the containers, just changes the address from port 8080 to port 8081 through the address
http://localhost:8081/realms/msrealm/protocol/openid-connect/token


## Database

The project utilizes [H2](https://www.h2database.com/) as the database. 

This database is created in memory automatically when each microservice is initialized.


## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request to the repository.

When contributing to this project, please follow the existing code style, [commit conventions](https://www.conventionalcommits.org/en/v1.0.0/), and submit your changes in a separate branch.


## Configuration for local environment

1. It´s necessary to create a network so that the application containers can communicate. Create it only once.


```
docker network create ms-network
```

2. Inside each folder of each Mavem module, there are the following files: 

```
"dockerBuild.bat": Will create a docker image with all the compiled files to run the microservice.
docker build --tag ms-clients .

"dockerRun.bat" Will create a container in docker with the microservice's production environment parameters.
docker run --name ms-clients --network ms-network -e EUREKA_SERVER=ms-eureka ms-clients

```

3. Run all containers in docker via command line or through dockerDesktop.

Complete architecture of microservices running in containers
![Complete architecture of microservices running in containers](https://github.com/kenzor1979/microservices/blob/main/images/docker_containers%20production.gif?raw=true&sanitize=true)

Example of accessing microservices through containers managed by the gateway with load balancing by instances

![Example of accessing microservices through containers managed by the gateway with load balancing by instances](https://github.com/kenzor1979/microservices/blob/main/images/example%20of%20accessing%20microservices%20through%20containers%20managed%20by%20the%20gateway%20with%20load%20balancing%20by%20instances.gif?raw=true&sanitize=true)

notes:
In the production environment, all containers are accessed through the ms-network network, and the endpoints should be accessed through the gateway at the address:

http://host.docker.internal:8080/

Example:

http://host.docker.internal:8080/credit-assessor

http://host.docker.internal:8080//cards

http://host.docker.internal:8080//clients