package data.model;
/**
 * Created by Hervé on 20/10/2014.
 */
public enum DayTime {
    MORNING("morning"),
    AFTERNOON("afternoon");

    private String name ="";
    DayTime(String name){
        this.name = name;
    }
    public String toString(){
        return name;
    }
}
