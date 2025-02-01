import javax.sound.midi.*;
import java.util.Scanner;

public class Music {

    public static void main(String[] args) {
        try {
            MidiDevice.Info[] midiDeviceInfo = MidiSystem.getMidiDeviceInfo();
            MidiDevice inputDevice;
            if (midiDeviceInfo.length == 0) {
                System.out.println("No MIDI devices found.");
                return;
            }

            // Print available MIDI devices
            System.out.println("Available MIDI Devices:");
            for (int i = 0; i < midiDeviceInfo.length; i++) {
                System.out.println(i + ": " + midiDeviceInfo[i].getName());
            }

            // Ask for what MIDI device to use
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.close();
            inputDevice = MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[choice]);
            inputDevice.open();

            Receiver receiver = new Receiver() {
                @Override
                public void send(MidiMessage message, long timeStamp) {
                    // Check if the message is a 'note on', as that means it is pressed for the user to play sound
                    if (message instanceof ShortMessage) {
                        ShortMessage shortMessage = (ShortMessage) message;
                        int command = shortMessage.getCommand();

                        // MIDI 'note on' is 144, and 'note off' is 128
                        if (command == ShortMessage.NOTE_ON) {
                            int note = shortMessage.getData1(); // Note number (60 is Middle C)
                            int velocity = shortMessage.getData2(); // Volume (0-127)
                            System.out.println("Note ON: " + note + " Velocity: " + velocity);
                            // Play the note using the synthesizer
                            playNote(note, velocity);
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

    // playNote: Play a note; meethod is executed whenever a key is pressed.
    private static void playNote(int note, int velocity) {
        try {
            // Create a synthesizer instance
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();

            MidiChannel channel = synthesizer.getChannels()[0]; // Use channel 0

            // Play the note on the channel
            channel.noteOn(note, velocity); // Note, velocity

            // Give time for the note to be played
            Thread.sleep(200); // Play the note for 5 ms
            synthesizer.close(); // Close the synthesizer when done
            channel.noteOff(note, velocity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

