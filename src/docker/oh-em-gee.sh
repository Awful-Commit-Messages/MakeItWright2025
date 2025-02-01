#!/bin/bash

# Make the sounds available for making sounds when we want sounds:
fluidsynth -a alsa -m alsa_seq /usr/share/sounds/sf2/FluidR3_GM.sf2 &

# Connect the sound device to the sound connector that has the sounds we want to make sounds of:
aconnect 28:0 128:0