package com.example.recyclerviewhomework.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

public class ListItem implements Parcelable {

private  int id;
private String description;
private  String title;
private  String viewer;
private  @DrawableRes int moreIcon;

    public ListItem() {
    }

    public ListItem(String description, String title, String viewer,@DrawableRes int moreIcon) {
        this.description = description;
        this.title = title;
        this.viewer = viewer;
        this.moreIcon=moreIcon;

    }


    protected ListItem(Parcel in) {
        id = in.readInt();
        description = in.readString();
        title = in.readString();
        viewer = in.readString();
        moreIcon=in.readInt();

    }

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getViewer() {
        return viewer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setViewer(String viewer) {
        this.viewer = viewer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getMoreIcon() {
        return moreIcon;
    }

    public void setMoreIcon(int moreIcon) {
        this.moreIcon = moreIcon;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeInt(id);
       dest.writeString(description);
       dest.writeString(title);
       dest.writeString(viewer);
       dest.writeInt(moreIcon);
    }
}
