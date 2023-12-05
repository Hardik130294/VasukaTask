package com.hardik.vasukatask.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressLint("ParcelCreator")
public class Collections implements Parcelable {

    @SerializedName("smart")
    @Expose
    private List<Smart> smart;
    @SerializedName("user")
    @Expose
    private List<User> user;
    @SerializedName("curated")
    @Expose
    private List<Object> curated;

    public Collections() {
    }

    public Collections(List<Smart> smart, List<User> user, List<Object> curated) {
        this.smart = smart;
        this.user = user;
        this.curated = curated;
    }

    protected Collections(Parcel in) {
    }

    public static final Creator<Collections> CREATOR = new Creator<Collections>() {
        @Override
        public Collections createFromParcel(Parcel in) {
            return new Collections(in);
        }

        @Override
        public Collections[] newArray(int size) {
            return new Collections[size];
        }
    };

    public List<Smart> getSmart() {
        return smart;
    }

    public void setSmart(List<Smart> smart) {
        this.smart = smart;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<Object> getCurated() {
        return curated;
    }

    public void setCurated(List<Object> curated) {
        this.curated = curated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
    }
}