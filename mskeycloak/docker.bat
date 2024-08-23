docker run --name ms-keycloak -p 8081:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin --network ms-network quay.io/keycloak/keycloak:18.0.0 start-dev

