package com.alexforan.keybored.midi;

import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MIDIPlayer {
    private MidiDevice device;
    private Receiver receiver;
    private Map<Integer, Boolean> noteStates;
    int velocity;
    
    public MIDIPlayer(MidiDevice device) throws MidiUnavailableException  {
        this.device = device;
        receiver = null;
        noteStates = new HashMap<>();
        velocity = 127;
    }
    
    public void initialize() throws MidiUnavailableException {
        device.open();
        receiver = device.getReceiver();
    }
    
    public void changeInstrument(int patch) {
        try {
            receiver.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE, patch, 0), 2);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }
    
    public synchronized void play(int note) {
        Boolean state = noteStates.get(note);
        if (state == null || !state) {
            noteStates.put(note, true);
            try {
                receiver.send(new ShortMessage(ShortMessage.NOTE_ON, note, velocity), 3);
            } catch (InvalidMidiDataException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    public synchronized void stop(int note) {
        noteStates.put(note, false);
        try {
            receiver.send(new ShortMessage(ShortMessage.NOTE_OFF, note, 0), 2);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void stopAll() {
        for (int note : noteStates.keySet()) {
            if (noteStates.get(note)) {
                stop(note);
            }
        }
    }
    
    public void close() {
        receiver.close();
        device.close();
    }
    
    @Override
    protected final void finalize() throws Throwable {
        super.finalize();
        close();
    }
}
