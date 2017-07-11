package com.asi.educatyapp.Data.Data.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Abdulrhman on 23/02/2017.
 */

public class SkillModel implements Parcelable {

    private String name;
    private int Icon;

    public SkillModel() {
        //required for firebase database
    }

    public SkillModel(String name, int icon, int endorse) {
        this.name = name;
        Icon = icon;
        Endorse = endorse;
    }

    private int Endorse;

    public SkillModel(Parcel in) {
        this.name = in.readString();
        Icon = in.readInt();
        Endorse = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
        //in case of having supclasses to return different object
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

parcel.writeString(name);
        parcel.writeInt(Icon);
        parcel.writeInt(Icon);
    }

}
