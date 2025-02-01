# Use the official Ubuntu 24.04 image:
FROM ubuntu:24.04

# Set the work directory:
WORKDIR /app

# Copy the local files to the container:
COPY . ../../app/

# Install dependencies:
RUN apt-get update
RUN apt-get install openjdk-21-jre -y

# Define the command to run the app:
CMD ["echo", "hi"]
