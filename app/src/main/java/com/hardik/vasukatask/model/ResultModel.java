package com.hardik.vasukatask.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressLint("ParcelCreator")
public class ResultModel implements Parcelable {

    @SerializedName("result")
    @Expose
    private Result result;

    public ResultModel() {
    }

    public ResultModel(Result result) {
        this.result = result;
    }


    protected ResultModel(Parcel in) {
    }

    public static final Creator<ResultModel> CREATOR = new Creator<ResultModel>() {
        @Override
        public ResultModel createFromParcel(Parcel in) {
            return new ResultModel(in);
        }

        @Override
        public ResultModel[] newArray(int size) {
            return new ResultModel[size];
        }
    };

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
    }


    public static class Result {

        @SerializedName("index")
        @Expose
        private List<Index> index;
        @SerializedName("collections")
        @Expose
        private Collections collections;

        public Result() {
        }

        public Result(List<Index> index, Collections collections) {
            this.index = index;
            this.collections = collections;
        }

        public List<Index> getIndex() {
            return index;
        }

        public void setIndex(List<Index> index) {
            this.index = index;
        }

        public Collections getCollections() {
            return collections;
        }

        public void setCollections(Collections collections) {
            this.collections = collections;
        }

    }
}


