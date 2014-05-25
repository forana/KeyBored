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
            if (device.getMaxReceivers() != 0 &&
                    /* stupid code deserves an explanation - this is a sun-provided
                     * device that reports itself as being a receiver, but behaves
                     * in unexpected and mysterious ways.
                     */
                    !device.getClass().getName().equals("com.sun.media.sound.RealTimeSequencer")) {
                list.add(device);
            }
        }
        return list;
    }
}
