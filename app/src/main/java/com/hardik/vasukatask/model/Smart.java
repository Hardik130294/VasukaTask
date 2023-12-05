package com.hardik.vasukatask.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
@SuppressLint("ParcelCreator")
public class Smart implements Parcelable{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("smart")
    @Expose
    private String smart;
    @SerializedName("courses")
    @Expose
    private List<Integer> courses;
    @SerializedName("is_default")
    @Expose
    private Integer isDefault;
    @SerializedName("is_archive")
    @Expose
    private Integer isArchive;
    @SerializedName("description")
    @Expose
    private String description;

    public Smart() {
    }

    public Smart(String id, String label, String smart, List<Integer> courses, Integer isDefault, Integer isArchive, String description) {
        this.id = id;
        this.label = label;
        this.smart = smart;
        this.courses = courses;
        this.isDefault = isDefault;
        this.isArchive = isArchive;
        this.description = description;
    }

    protected Smart(Parcel in) {
        id = in.readString();
        label = in.readString();
        smart = in.readString();
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

    public static final Creator<Smart> CREATOR = new Creator<Smart>() {
        @Override
        public Smart createFromParcel(Parcel in) {
            return new Smart(in);
        }

        @Override
        public Smart[] newArray(int size) {
            return new Smart[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSmart() {
        return smart;
    }

    public void setSmart(String smart) {
        this.smart = smart;
    }

    public List<Integer> getCourses() {
        return courses;
    }

    public void setCourses(List<Integer> courses) {
        this.courses = courses;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(label);
        parcel.writeString(smart);
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
