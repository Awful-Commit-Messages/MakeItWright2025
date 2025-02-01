# Use the official Ubuntu 24.04 image:
FROM ubuntu:24.04

# Set the work directory:
WORKDIR /app/

# Copy the local files to the container:
COPY ./src/docker/oh-em-gee.sh .
COPY ./target/makeitwright2025-1.0-SNAPSHOT-jar-with-dependencies.jar .

# Install dependencies:
RUN apt-get update
RUN apt-get install openjdk-21-jre -y
RUN apt-get install fluidsynth fluid-soundfont-gm alsa-utils -y

# Define the command to run the app:
CMD ["./oh-em-gee.sh"]
