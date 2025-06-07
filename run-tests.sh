#!/bin/bash

# QuickShelf Test Runner Script

echo "Running QuickShelf tests..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# Run the tests
echo "Running all tests..."
./gradlew clean test

# Check if tests were successful
if [ $? -eq 0 ]; then
    echo "All tests passed successfully!"
    exit 0
else
    echo "Some tests failed. Please check the test reports."
    exit 1
fi