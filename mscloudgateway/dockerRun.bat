docker run --name ms-gateway -p 8080:8080 -e EUREKA_SERVER=ms-eureka -e KEYCLOAK_SERVER=ms-keycloak -e KEYCLOAK_PORT=8080 --network ms-network  ms-gateway
