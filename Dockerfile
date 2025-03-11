FROM openjdk:25-ea-4-jdk-oraclelinux9

# Set working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/mini1.jar /app/app.jar

# Create a directory for JSON data files
RUN mkdir -p /app/data

# Set environment variables for JSON file paths
ENV USERS_JSON_PATH=/app/data/users.json
ENV PRODUCTS_JSON_PATH=/app/data/products.json
ENV CARTS_JSON_PATH=/app/data/carts.json
ENV ORDERS_JSON_PATH=/app/data/orders.json

# Expose the application's port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app/app.jar"]
