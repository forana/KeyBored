package com.alexforan.keybored.input;

public class KeyAdapterEvent {
    private final char keyChar;
    private final boolean shifted;
    
    public KeyAdapterEvent(char keyChar, boolean shifted) {
        this.keyChar = keyChar;
        this.shifted = shifted;
    }
    
    public char getKeyChar() {
        return keyChar;
    }
    
    public boolean isShifted() {
        return shifted;
    }
}
