package com.alexforan.keybored;

import java.awt.Dimension;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

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
        
        // super hacky hack to make this usable for alpha demo
        if (System.console() == null) {
            JFrame frame = new JFrame();
            frame.setTitle("Close to end keybored");
            frame.add(new JLabel("<html>Map: " + map.getName() + "<br>Device: " + device.getDeviceInfo().getName() + "</html>"));
            frame.setMinimumSize(new Dimension(300, 200));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
        // need to open devices after swing stuff has initialized, and not before
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    player.initialize();
                    //player.changeInstrument(25);
                } catch (MidiUnavailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
