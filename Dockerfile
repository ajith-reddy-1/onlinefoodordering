FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw && ./mvnw -DskipTests package

CMD ["sh", "-c", "java -Dserver.port=${PORT:-8081} -jar target/*.jar"]