package data.model;

/**
 * Created by Hervé on 19/10/2014.
 */
public enum Status {
    PENDING("pending"),
    VALIDATEDMGR("validatedMgr"),
    VALIDATEDHR("validatedHr"),
    REFUSED("refused"),
    PENDINGCANCEL("pendingCancel"),
    CANCELLED("cancelled");

    private String name ="";
    Status(String name){
        this.name = name;
    }
    public String toString(){
        return name;
    }
}