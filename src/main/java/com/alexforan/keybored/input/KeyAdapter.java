package com.alexforan.keybored.input;

import java.util.Collection;
import java.util.HashSet;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyAdapter {
    private static final KeyAdapter instance = new KeyAdapter();
    
    public static final KeyAdapter getInstance() {
        return instance;
    }
    
    private Collection<KeyAdapterListener> listeners;
    
    private KeyAdapter() {
        listeners = new HashSet<>();
    }
    
    public void initialize() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
        
        GlobalScreen.getInstance().addNativeKeyListener(new NativeKeyListener() {
            public void nativeKeyPressed(NativeKeyEvent e) {
                if (!e.isActionKey()) {
                    KeyAdapterEvent event = new KeyAdapterEvent(
                            (char)e.getKeyCode(),
                            ((e.getModifiers() & NativeKeyEvent.SHIFT_MASK) > 0));
                    for (KeyAdapterListener l : listeners) {
                        l.keyDown(event);
                    }
                }
            }
            
            public void nativeKeyReleased(NativeKeyEvent e) {
                if (!e.isActionKey()) {
                    KeyAdapterEvent event = new KeyAdapterEvent(
                            (char)e.getKeyCode(),
                            false);
                    for (KeyAdapterListener l : listeners) {
                        l.keyUp(event);
                    }
                }
            }
            
            public void nativeKeyTyped(NativeKeyEvent e) {
                // don't care
            }
        });
    }
    
    public void addListener(KeyAdapterListener listener) {
        listeners.add(listener);
    }
    
    public boolean removeListener(KeyAdapterListener listener) {
        return listeners.remove(listener);
    }
    
    public void close() {
        GlobalScreen.unregisterNativeHook();
    }
}
