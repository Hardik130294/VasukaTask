package com.hardik.vasukatask.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

@SuppressLint("ParcelCreator")
public class FilterItem implements Parcelable {
    private String name;
    private boolean isSelected;
    private List<String> value;

    public FilterItem() {
    }

    public FilterItem(String name, boolean isSelected, List<String> value) {
        this.name = name;
        this.isSelected = isSelected;
        this.value = value;
    }

    protected FilterItem(Parcel in) {
        name = in.readString();
        isSelected = in.readByte() != 0;
        value = in.createStringArrayList();
    }

    public static final Creator<FilterItem> CREATOR = new Creator<FilterItem>() {
        @Override
        public FilterItem createFromParcel(Parcel in) {
            return new FilterItem(in);
        }

        @Override
        public FilterItem[] newArray(int size) {
            return new FilterItem[size];
        }
    };

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
        parcel.writeStringList(value);
    }
}
