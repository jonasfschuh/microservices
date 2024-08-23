rem docker run --name ms-cards --network ms-network ms-cards
docker run --name ms-cards --network ms-network -e RABBITMQ_SERVER=ms-rabbitmq -e EUREKA_SERVER=ms-eureka ms-cards
