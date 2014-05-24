package com.alexforan.keybored.midi;

import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

public class MIDISystem {
    public static List<MidiDevice> getReceivableDevices() throws MidiUnavailableException {
        List<MidiDevice> list = new LinkedList<>();
        for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
            MidiDevice device = MidiSystem.getMidiDevice(info);
            if (device.getMaxReceivers() != 0) {
                list.add(device);
            }
        }
        return list;
    }
}
