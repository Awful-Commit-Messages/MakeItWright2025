package edu.wright.acm;

import javax.sound.midi.*;

public class ListMidiDevices {
    public static void main(String[] args) {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            System.out.println("Name: " + info.getName() + 
                               ", Description: " + info.getDescription() + 
                               ", Vendor: " + info.getVendor() + 
                               ", Version: " + info.getVersion());
            try {
                MidiDevice device = MidiSystem.getMidiDevice(info);
                System.out.println("  Max Receivers: " + device.getMaxReceivers());
                System.out.println("  Max Transmitters: " + device.getMaxTransmitters());
            } catch (MidiUnavailableException e) {
                System.out.println("  (unavailable) " + e);
            }
        }
    }
}
