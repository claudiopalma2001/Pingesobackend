# Usar una imagen base con OpenJDK para construir la aplicación
FROM openjdk:17 as build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo pom.xml para que Maven descargue las dependencias
COPY docedeseosbackend/pom.xml /app/pom.xml

# Copiar el código fuente al contenedor
COPY docedeseosbackend/src /app/src

# Ejecutar Maven para construir la aplicación
RUN mvn clean install -DskipTests

# Etapa final
FROM openjdk:17

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR desde la etapa de construcción
COPY --from=build /app/target/docedeseosbackend.jar /app/docedeseosbackend.jar

# Establecer las variables de entorno
ENV SPRING_PROFILES_ACTIVE=prod
ENV DB_URL=jdbc:postgresql://<host>:5432/docedeseos
ENV DB_USER=postgres
ENV DB_PASSWORD=password
ENV JWT_SECRET=default-secret-key
ENV TRANSBANK_COMMERCE_CODE=597055555532
ENV TRANSBANK_API_KEY=579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C
ENV PLANTILLAS_BASE_PATH=docedeseosfrontend/src/assets/Plantillas

# Exponer el puerto 8080 para acceder a la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "docedeseosbackend.jar"]
