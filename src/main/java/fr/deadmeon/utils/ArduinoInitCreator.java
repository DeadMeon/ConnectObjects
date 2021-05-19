package fr.deadmeon.utils;

public class ArduinoInitCreator {
    private String init; 

    public ArduinoInitCreator() {
        init = "\nvoid initCode() {";
    }

    public String returnInit(){
        init += "\n}";
        return init;
    }

    public void initPin(int pin, PinMode mode) {
        init += "\npinMode(" + pin + ", " + mode + ");";
    }
}
