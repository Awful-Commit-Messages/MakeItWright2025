# MIDI Project

Utilizing the MIDI Java library to accept input from a MIDI (midi 25) device.

## Maven for Beginners

- Do magick.
- Put the stuff in the right stuff.
- Use `mvn clean install` to make majick happen.
- Use `mvn clean` to clean up after you get way too excited because it finally worked and now you're embarassed.
- Don't try to run the application in VSCode after cleaning up after yourself, because now you have to pull it all out from under your bed to start again.

## MIDI for Normies

1. Install the needful: `sudo apt-get install fluidsynth fluid-soundfont-gm alsa-utils`
2. Connect the superior user authentication device to your ~~MIDI~~ USB port.
3. Start a software MIDI device to make sounds from sounds: `fluidsynth -a alsa -m alsa_seq /usr/share/sounds/sf2/FluidR3_GM.sf2 &`
4. Determine the # of Destiny: `aconnect -l`
5. Connect the # of Destiny to the sound of sounds: `aconnect 28:0 128:0`
6. Slam face into ivories for happiness.

## How2Win w/ Docker

I ship it.

1. `docker build -t acm -f ./src/docker/Dockerfile .`
2. `docker run -it acm`
3. `docker save acm:latest | gzip > ./docker-image/acm.tar.gz`
4. Place 1st place in the Hackathon, naturally.
5. Get our own office!

## Group Members

- Adrien Abbey
- Kyle Cox
- Sean Fricke
- Joshua Dennis
- ChatGPT

## Scope

- Docker containerization
- Usage of the MIDI library to accomplish a function
