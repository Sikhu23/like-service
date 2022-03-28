FROM openjdk:17
ADD target/dockerLikeService.jar dockerLikeService.jar
EXPOSE 3020
ENTRYPOINT ["java","-jar","dockerLikeService.jar"]