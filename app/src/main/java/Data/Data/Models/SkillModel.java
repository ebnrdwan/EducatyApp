package Models;

/**
 * Created by Abdulrhman on 23/02/2017.
 */

public class SkillModel {

    private String name;
    private int Icon;
    private int Endorse;

    public SkillModel(String name, int icon, int endorse) {
        this.name = name;
        Icon = icon;
        Endorse = endorse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    public int getEndorse() {
        return Endorse;
    }

    public void setEndorse(int endorse) {
        Endorse = endorse;
    }
}
