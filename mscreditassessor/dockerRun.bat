rem docker run --name ms-creditassessor --network ms-network ms-creditassessor

docker run --name ms-creditassessor --network ms-network -e RABBITMQ_SERVER=ms-rabbitmq -e EUREKA_SERVER=ms-eureka ms-creditassessor
