package com.alexforan.keybored;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MIDITest {
    public static void main(String[] args) throws Exception {
        /*Synthesizer synth = MidiSystem.getSynthesizer();
        
        synth.open();
        Soundbank bank = synth.getDefaultSoundbank();// = MidiSystem.getSoundbank(new File("soundbank-min.gm"));
        synth.loadAllInstruments(bank);
        Instrument instrument = bank.getInstruments()[0];
        MidiChannel channel = synth.getChannels()[0];
        channel.noteOn(60, 127);
        Thread.sleep(1000);
        channel.noteOff(60, 127);
        channel.noteOn(65, 127);
        Thread.sleep(1000);
        channel.allNotesOff();*/
        
        MidiMessage PLAY = new ShortMessage(ShortMessage.NOTE_ON, 60, 127);
        MidiMessage PLAY2 = new ShortMessage(ShortMessage.NOTE_ON, 65, 127);
        MidiMessage STOP = new ShortMessage(ShortMessage.NOTE_OFF, 60, 127);
        MidiMessage CHANGE = new ShortMessage(ShortMessage.PROGRAM_CHANGE + 1, 10, 0); 
        MidiMessage START = new ShortMessage(ShortMessage.START, 0, 0);
        
        for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
            System.out.println(info.getName());
            MidiDevice device = MidiSystem.getMidiDevice(info);
            try {
                System.out.println(device.getMaxReceivers());
                device.open();
                if (device.getMaxReceivers() != 0) {
                    Receiver rec = null;
                    try {
                        rec = device.getReceiver();
                        rec.send(START, 1);
                        rec.send(CHANGE, 2);
                        rec.send(PLAY, 3);
                        Thread.sleep(1000);
                        rec.send(PLAY2, 3);
                        Thread.sleep(1000);
                        rec.send(STOP, 2);
                    } finally {
                        if (rec != null) {
                            rec.close();
                        }
                    }
                }
            } finally {
                Thread.sleep(2000);
                device.close();
            }
        }
    }
}
