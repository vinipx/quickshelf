#!/bin/bash

# QuickShelf Application Startup Script

echo "Starting QuickShelf application..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Check Java version
java_version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
echo "Using Java version: $java_version"

# Build the application if needed
if [ ! -f "./build/libs/quickshelf-0.0.1-SNAPSHOT.jar" ]; then
    echo "Building application..."
    ./gradlew build -x test
    
    # Check if build was successful
    if [ $? -ne 0 ]; then
        echo "Build failed. Please check the errors above."
        exit 1
    fi
fi

# Run the application
echo "Starting server on http://localhost:8080/api"
echo "API documentation available at http://localhost:8080/api/swagger-ui.html"
echo "H2 Console available at http://localhost:8080/api/h2-console"
echo "Press Ctrl+C to stop the server"

java -jar build/libs/quickshelf-0.0.1-SNAPSHOT.jar