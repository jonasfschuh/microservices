rem docker run --name ms-clients --network ms-network ms-cards

docker run --name ms-clients --network ms-network -e EUREKA_SERVER=ms-eureka ms-clients
