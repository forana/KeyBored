package com.alexforan.keybored;

import javax.sound.midi.MidiDevice;

import com.alexforan.keybored.input.KeyAdapter;
import com.alexforan.keybored.input.KeyAdapterEvent;
import com.alexforan.keybored.input.KeyAdapterListener;
import com.alexforan.keybored.mapping.KeyMap;
import com.alexforan.keybored.midi.MIDIPlayer;
import com.alexforan.keybored.midi.MIDISystem;

public class POC {
    public static void main(String[] args) throws Exception {
        final KeyMap map = KeyMap.load(POC.class.getResourceAsStream("/keymaps/us-classic.json"));
        System.out.println("Map: " + map.getName());
        
        MidiDevice device = MIDISystem.getReceivableDevices().get(0);
        System.out.println("Device: " + device.getDeviceInfo().getName());
        
        final MIDIPlayer player = new MIDIPlayer(device);
        
        KeyAdapter.getInstance().initialize();
        KeyAdapter.getInstance().addListener(new KeyAdapterListener() {
            @Override
            public void keyDown(KeyAdapterEvent e) {
                char key = e.getKeyChar();
                Integer normalNote = map.getNormalKeys().get(key);
                Integer shiftedNote = map.getShiftedKeys().get(key);
                if (e.isShifted() && shiftedNote != null) {
                    player.play(shiftedNote);
                } else if (normalNote != null) {
                    player.play(normalNote);
                }
            }

            @Override
            public void keyUp(KeyAdapterEvent e) {
                char key = e.getKeyChar();
                Integer normalNote = map.getNormalKeys().get(key);
                Integer shiftedNote = map.getShiftedKeys().get(key);
                if (shiftedNote != null) {
                    player.stop(shiftedNote);
                }
                if (normalNote != null) {
                    player.stop(normalNote);
                }
            }
        });
    }
}
