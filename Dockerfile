FROM openjdk:13-alpine
ADD build/libs/be-my-goods-0.0.1-SNAPSHOT.jar .
EXPOSE 8082
CMD java -jar be-my-goods-0.0.1-SNAPSHOT.jar