# Stage 1: build the jar
FROM gradle:8.4.0-jdk21 AS build

# copy the project files
COPY --chown=gradle:gradle . /home/gradle/src

# set the working directory
WORKDIR /home/gradle/src

# compile and build the jar
# it isn't necessary to run the tests to build the jar
RUN gradle build  -x test -x integrationTest

# Stage 2: create the docker final image
FROM openjdk:21-jdk-slim

# copy the jar file to the container
COPY --from=build /home/gradle/src/build/libs/payments-0.0.1-SNAPSHOT.jar /app/myapp.jar

# expose port 8080
EXPOSE 8080

# run the application
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]
