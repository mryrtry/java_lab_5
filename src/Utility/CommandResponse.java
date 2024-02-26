package Utility;

public class CommandResponse {
    boolean responseStatus;
    String responseMSG;

    public CommandResponse() {
        this.responseStatus = true;
        this.responseMSG = "";
    }

    public CommandResponse(boolean responseStatus) {
        this.responseStatus = responseStatus;
        this.responseMSG = "";
    }

    public CommandResponse(boolean responseStatus, String responseMSG) {
        this.responseStatus = responseStatus;
        this.responseMSG = responseMSG;
    }
}
