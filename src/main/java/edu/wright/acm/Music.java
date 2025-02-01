package edu.wright.acm;

import com.google.gson.*;
import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Music {

    public static ArrayList<Integer> pressedButtons = new ArrayList<>(); // Array tracking
    public static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        try (FileReader reader = new FileReader("./UserData.json")) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            deserialize(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = null;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        for (User thisUser : users) {
            if (thisUser.getUsername().equals(username)) {
                user = thisUser; // User found to find a password for!
                break;
            }
        }
        if (user != null) { // Looking for password
            System.out.println("Play your password!");
            initializeMusic(true, username, user);
        } else { // Ask if they want to make a new account
            System.out.print("No user detected. Do you want to make an account? (y/n) ");
            String response = scanner.nextLine();
            scanner.close();
            if (response.equals("y")) { // Go make a password
                initializeMusic(false, username, user);
            }
        }
    }

    // serialize: Serialize the group of Users into a JsonObject.
    static JsonObject serialize() {
        JsonObject json = new JsonObject();
        JsonArray array = new JsonArray();
        for (User user : users) {
            array.add(user.serialize());
        }
        json.add("users", array);
        return json;
    }

    // deserialize: Deserialize the group of Users from a JsonObject.
    static void deserialize(JsonObject json) {
        for (JsonElement user : json.getAsJsonArray("users")) {
            users.add(User.deserialize(user.getAsJsonObject()));
        }
    }

    // initializeMusic: Initialize the music player; boolean is based on if the
    // password is being checked or not.
    // Parameters - checkingPassword: If the password is being checked for, or is
    // just being entered
    // username: The username of the user, relevant if you are making a new account
    // user: The user being logged into, OR null if making a new user
    public static void initializeMusic(boolean checkingPassword, String username, User user) {
        try {
            MidiDevice.Info[] midiDeviceInfo = MidiSystem.getMidiDeviceInfo();
            MidiDevice inputDevice = MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[0]); // default in case
                                                                                                  // none is found
            if (midiDeviceInfo.length == 0) {
                System.out.println("No MIDI devices found.");
                return;
            }

            // Print available MIDI devices
            System.out.println("Available MIDI Devices:");
            for (int i = 0; i < midiDeviceInfo.length; i++) {
                System.out.println(i + ": " + midiDeviceInfo[i].getName()); // TODO: delete
                inputDevice = MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[i]);
                if (midiDeviceInfo[i].getName().contains("LPK25 mk2") && inputDevice.getMaxReceivers() != -1) {
                    System.out.println("Chose " + i);
                    break;
                }
            }

            inputDevice.open();

            Receiver receiver = new Receiver() {
                @Override
                public void send(MidiMessage message, long timeStamp) {
                    // Check if the message is a pressed note, as that means it is pressed for the
                    // user to play sound
                    if (message instanceof ShortMessage) {
                        ShortMessage shortMessage = (ShortMessage) message;
                        int command = shortMessage.getCommand();
                        if (command == ShortMessage.NOTE_ON) {
                            int note = shortMessage.getData1(); // Note number
                            int modNote = (note % 25);
                            pressedButtons.add(modNote);
                            System.out.println(pressedButtons); // TODO: delete
                            int velocity = shortMessage.getData2(); // Volume (0-127)
                            System.out.println("Note ON: " + note + " Velocity: " + velocity); // TODO: delete
                            playNote(note, velocity, checkingPassword, username, user);
                        }
                    }
                }

                @Override
                public void close() {
                    // Close resources when done
                }
            };

            // Attach the receiver to the input device
            inputDevice.getTransmitter().setReceiver(receiver);

            // Open a synthesizer for playing sounds
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();

            Runnable inputListener = new Runnable() {
                public void run() {
                    while (true) { // run forever until program ends
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Thread listenerThread = new Thread(inputListener);
            listenerThread.start();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    // playNote: Play a note; method is executed whenever a key is pressed.
    // Parameters - note: the note number
    // velocity: the sound to be played
    private static void playNote(int note, int velocity, boolean checkingPassword, String username, User user) {
        try {
            // Create a synthesizer instance
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();

            MidiChannel channel = synthesizer.getChannels()[0]; // Use channel 0 for the synthesizer

            // Play the note on the channel
            channel.noteOn(note, velocity); // Note, velocity

            // Give time for the note to be played
            Thread.sleep(200); // Play the note for 200 ms
            synthesizer.close(); // Close the synthesizer when done
            channel.noteOff(note, velocity);

            // TODO: Need to find a way to only make it so whenever the user is done typing
            // a password
            if (checkingPassword) {
                try {
                    if (Auth.authenticate(pressedButtons, user)) {
                        System.out.println("You successfully logged into " + user.getUsername() + "'s account.");

                    } else {
                        System.out.println("You got " + user.getUsername() + "'s password wrong. How could you?!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Random rng = new Random();
                String salt = "" + (rng.nextInt() * rng.nextInt());
                try (FileWriter writer = new FileWriter("./UserData.json")) {
                    user = new User(username, salt, Auth.hash(pressedButtons, salt));
                    users.add(user);
                    Gson gson = new Gson();
                    gson.toJson(serialize(), writer);
                    System.out.println("Registered new user " + username + ".");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
