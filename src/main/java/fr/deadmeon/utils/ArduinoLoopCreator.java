package fr.deadmeon.utils;

public class ArduinoLoopCreator {
    private String loop; 

    public ArduinoLoopCreator() {
        loop = "\nvoid runCode() {";
    }

    public String returnLoop(){
        loop += "\n}";
        return loop;
    }



    public void setPin(int pin, Voltage voltage) {
        loop += "\n\tdigitalWrite(" + pin + ", " + voltage + ");";
    }

    public void setDelay(int millis) {
        loop += "\n\tdelay(" + millis + ");";
    }



    public void blinkPin(int pin, int millis) {
        blinkPin(pin, millis, Voltage.HIGH);
    }

    public void blinkPin(int pin, int millis, Voltage voltage) {
        setPin(pin, voltage);
        setDelay(millis);
        setPin(pin, (voltage == Voltage.HIGH) ? Voltage.LOW : Voltage.HIGH);
    }


    
    public static void main(String[] args) {
        ArduinoLoopCreator loop = new ArduinoLoopCreator();
        loop.blinkPin(8, 1000, Voltage.HIGH);
        System.out.println(loop.returnLoop());
    }
}
