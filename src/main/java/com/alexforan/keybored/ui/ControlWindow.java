package com.alexforan.keybored.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.alexforan.keybored.input.KeyAdapter;
import com.alexforan.keybored.input.KeyAdapterEvent;
import com.alexforan.keybored.input.KeyAdapterListener;
import com.alexforan.keybored.mapping.KeyMap;
import com.alexforan.keybored.midi.MIDIInstruments;
import com.alexforan.keybored.midi.MIDIPlayer;
import com.alexforan.keybored.midi.MIDISystem;

public class ControlWindow {
    private static final String DEFAULT_KEYMAP = "/keymaps/us-classic.json";
    
    private JFrame frame;
    private volatile MIDIPlayer player;
    private List<MidiDevice> devices;
    private KeyMap keyMap;
    private boolean playerInitialized;
    private boolean suspended;
    
    public ControlWindow(Image iconImage, boolean exitOnClose) throws MidiUnavailableException, IOException {
        devices = MIDISystem.getReceivableDevices();
        player = new MIDIPlayer(devices.get(0));
        loadKeyMap();
        
        frame = new JFrame();
        setupFrame(iconImage, exitOnClose);
        
        setupKeyListener();
        
        playerInitialized = false;
        suspended = false;
    }
    
    private void loadKeyMap() throws IOException {
        keyMap = KeyMap.load(getClass().getResourceAsStream(DEFAULT_KEYMAP));
    }
    
    @SuppressWarnings("serial")
    private void setupFrame(Image iconImage, final boolean exitOnClose) {
        frame.setTitle("KeyBored Settings");
        frame.setIconImage(iconImage);
        frame.setDefaultCloseOperation(exitOnClose ? JFrame.EXIT_ON_CLOSE : JFrame.HIDE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 300));
        frame.setAutoRequestFocus(true);
        frame.setLocationByPlatform(true);
        
        final JLabel instrumentLabel = new JLabel("Instrument");
        final JComboBox<String> instrumentCombo = new JComboBox<String>(MIDIInstruments.STANDARD) {{
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    player.changeInstrument(getSelectedIndex());
                }
            });
        }};
        
        final JLabel deviceLabel = new JLabel("MIDI Device");
        final JComboBox<WrappedMidiDevice> deviceCombo =
                new JComboBox<WrappedMidiDevice>(wrapDevices(devices)) {{
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    MidiDevice newDevice = ((WrappedMidiDevice)getSelectedItem()).getDevice();
                    player.close();
                    try {
                        player = new MIDIPlayer(newDevice);
                        player.initialize();
                        player.changeInstrument(instrumentCombo.getSelectedIndex());
                    } catch (MidiUnavailableException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }};
        
        final JButton button = new JButton() {{
            this.setText(exitOnClose ? "Quit KeyBored" : "Minimize to Tray");
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (exitOnClose) {
                        dispose();
                    } else {
                        frame.setVisible(false);
                    }
                };
            });
        }};
        
        final JScrollPane scratchArea = new JScrollPane(new JTextArea("Test typing here"));
        
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(instrumentLabel)
                        .addComponent(deviceLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(instrumentCombo)
                        .addComponent(deviceCombo)));
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(instrumentLabel)
                        .addComponent(instrumentCombo))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(deviceLabel)
                        .addComponent(deviceCombo)));
        
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 2;
        c.ipady = 2;
        c.insets = new Insets(2,2,2,2);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weightx = 1;
        c.weighty = 0;
        
        frame.add(panel, c);
        
        c.gridy = 1;
        c.weighty = 1;
        frame.add(scratchArea, c);
        
        c.gridy = 2;
        c.weighty = 0;
        frame.add(button, c);
    }
    
    private void setupKeyListener() {
        KeyAdapter.getInstance().addListener(new KeyAdapterListener() {
            @Override
            public void keyDown(KeyAdapterEvent e) {
                if (!suspended) {
                    char key = e.getKeyChar();
                    Integer normalNote = keyMap.getNormalKeys().get(key);
                    Integer shiftedNote = keyMap.getShiftedKeys().get(key);
                    if (e.isShifted() && shiftedNote != null) {
                        player.play(shiftedNote);
                    } else if (normalNote != null) {
                        player.play(normalNote);
                    }
                }
            }

            @Override
            public void keyUp(KeyAdapterEvent e) {
                if (!suspended) {
                    char key = e.getKeyChar();
                    Integer normalNote = keyMap.getNormalKeys().get(key);
                    Integer shiftedNote = keyMap.getShiftedKeys().get(key);
                    if (shiftedNote != null) {
                        player.stop(shiftedNote);
                    }
                    if (normalNote != null) {
                        player.stop(normalNote);
                    }
                }
            }
        });
    }
    
    public void dispose() {
        frame.dispose();
        player.close();
        KeyAdapter.getInstance().close();
    }
    
    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
        if (suspended) {
            player.stopAll();
        }
    }
    
    public void show(){
        frame.setVisible(true);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.toFront();
                frame.requestFocus();
                OS.requestForeground();
            }
        });
        
        if (!playerInitialized) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        player.initialize();
                        playerInitialized = true;
                    } catch (MidiUnavailableException e) {
                        // no choice
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }
    
    private WrappedMidiDevice[] wrapDevices(List<MidiDevice> devices) {
        WrappedMidiDevice[] wrapped = new WrappedMidiDevice[devices.size()];
        for (int i=0; i<wrapped.length; i++) {
            wrapped[i] = new WrappedMidiDevice(devices.get(i));
        }
        return wrapped;
    }
    
    private class WrappedMidiDevice {
        private final MidiDevice device;
        
        public WrappedMidiDevice(MidiDevice device) {
            this.device = device;
        }
        
        public MidiDevice getDevice() {
            return device;
        }
        
        @Override
        public String toString() {
            return device.getDeviceInfo().getName();
        }
    }
}
