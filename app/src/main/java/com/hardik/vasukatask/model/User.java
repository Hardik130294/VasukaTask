package com.hardik.vasukatask.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
@SuppressLint("ParcelCreator")
public class User implements Parcelable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("is_default")
    @Expose
    private Integer isDefault;
    @SerializedName("is_archive")
    @Expose
    private Integer isArchive;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("courses")
    @Expose
    private List<Integer> courses;

    public User() {
    }

    public User(Integer id, String label, Integer isDefault, Integer isArchive, String description, List<Integer> courses) {
        this.id = id;
        this.label = label;
        this.isDefault = isDefault;
        this.isArchive = isArchive;
        this.description = description;
        this.courses = courses;
    }

    protected User(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        label = in.readString();
        if (in.readByte() == 0) {
            isDefault = null;
        } else {
            isDefault = in.readInt();
        }
        if (in.readByte() == 0) {
            isArchive = null;
        } else {
            isArchive = in.readInt();
        }
        description = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getIsArchive() {
        return isArchive;
    }

    public void setIsArchive(Integer isArchive) {
        this.isArchive = isArchive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getCourses() {
        return courses;
    }

    public void setCourses(List<Integer> courses) {
        this.courses = courses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(label);
        if (isDefault == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(isDefault);
        }
        if (isArchive == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(isArchive);
        }
        parcel.writeString(description);
    }
}
