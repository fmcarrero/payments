# Stage 1: Construir el JAR con Gradle
FROM gradle:8.4.0-jdk21 AS build

# Copia el código fuente al contenedor
COPY --chown=gradle:gradle . /home/gradle/src

# Establece el directorio de trabajo
WORKDIR /home/gradle/src

# Compila el código fuente y construye el JAR
# No es necesario instalar Gradle ya que la imagen ya viene con él
RUN gradle build  -x test -x integrationTest

# Stage 2: Crear la imagen de ejecución
FROM openjdk:21-jdk-slim

# Copia el JAR del stage de construcción al stage de ejecución
COPY --from=build /home/gradle/src/build/libs/payments-0.0.1-SNAPSHOT.jar /app/myapp.jar

# Expone el puerto en el que tu aplicación se ejecutará
EXPOSE 8080

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]
