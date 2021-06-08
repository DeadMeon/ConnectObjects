package fr.deadmeon.data;

public class OptionSave {

    private boolean toggleTwitch;
    private String pathForArduinoFile;

    public OptionSave() {
    }

    public OptionSave(boolean toggleTwitch, String pathForArduinoFile) {
        this.toggleTwitch = toggleTwitch;
        this.pathForArduinoFile = pathForArduinoFile;
    }

    public boolean isToggleTwitch() {
        return toggleTwitch;
    }

    public void setToggleTwitch(boolean toggleTwitch) {
        this.toggleTwitch = toggleTwitch;
    }

    public String getPathForArduinoFile() {
        return pathForArduinoFile;
    }

    public void setPathForArduinoFile(String pathForArduinoFile) {
        this.pathForArduinoFile = pathForArduinoFile;
    }
}
