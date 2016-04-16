package io.github.simcards.libcards.game;

/**
 * Created by Abraham on 4/9/2016.
 */
public class GameInfo {
    private String name;
    private String rules;
    private String desc;
    private String gameRules;
    private String image;
    private String id;

    public GameInfo() {
        // for firebase
    }

    public GameInfo(String name, String rules, String id) {
        this.name=name;
        this.rules=rules;
        this.id=id;
    }

    public String getImage() {return image;}
    public String getName() {
        return name;
    }
    public String getRules() {
        return rules;
    }
    public String getID() {
        return id;
    }
    public String getGameRules() { return gameRules; }
    public String getDesc() { return desc;}
    public String toString() {
        return name +" " +rules + " " + id;
    }

}