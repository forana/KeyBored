package com.alexforan.keybored.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties({"keys"})
public class KeyMap {
    public static KeyMap load(InputStream mapFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        KeyMap keyMap = mapper.readValue(mapFile, KeyMap.class);
        return keyMap;
    }

    private String name;

    private String creator;

    private String description;

    private String date;

    private Map<Character, Integer> normalKeys;
    
    private Map<Character, Integer> shiftedKeys;
    
    public KeyMap() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<Character, Integer> getNormalKeys() {
        return normalKeys;
    }

    public void setNormalKeys(Map<Character, Integer> normalKeys) {
        this.normalKeys = normalKeys;
    }

    public Map<Character, Integer> getShiftedKeys() {
        return shiftedKeys;
    }

    public void setShiftedKeys(Map<Character, Integer> shiftedKeys) {
        this.shiftedKeys = shiftedKeys;
    }
}
