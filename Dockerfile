FROM openjdk:17
EXPOSE 8080
RUN mkdir /uploads
ADD build/libs/restaurant-management-0.0.1-SNAPSHOT.jar restaurant-management-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java" , "-jar" , "restaurant-management-0.0.1-SNAPSHOT.jar"]