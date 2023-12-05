package com.hardik.vasukatask.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressLint("ParcelCreator")
public class Index implements Parcelable {

    @SerializedName("downloadid")
    @Expose
    private Integer downloadid;
    @SerializedName("cd_downloads")
    @Expose
    private Integer cdDownloads;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("authorid")
    @Expose
    private Integer authorid;
    @SerializedName("video_count")
    @Expose
    private Integer videoCount;
    @SerializedName("style_tags")
    @Expose
    private List<String> styleTags;
    @SerializedName("skill_tags")
    @Expose
    private List<String> skillTags;
    @SerializedName("series_tags")
    @Expose
    private List<String> seriesTags;
    @SerializedName("curriculum_tags")
    @Expose
    private List<String> curriculumTags;
    @SerializedName("educator")
    @Expose
    private String educator;
    @SerializedName("owned")
    @Expose
    private Integer owned;
    @SerializedName("sale")
    @Expose
    private Integer sale;
    @SerializedName("purchase_order")
    @Expose
    private int purchaseOrder;
    @SerializedName("watched")
    @Expose
    private Integer watched;
    @SerializedName("progress_tracking")
    @Expose
    private int progressTracking;


    public Index() {
    }

    public Index(Integer downloadid, Integer cdDownloads, Integer id, String title, Integer status, String releaseDate, Integer authorid, Integer videoCount, List<String> styleTags, List<String> skillTags, List<String> seriesTags, List<String> curriculumTags, String educator, Integer owned, Integer sale, int purchaseOrder, Integer watched, int progressTracking) {
        this.downloadid = downloadid;
        this.cdDownloads = cdDownloads;
        this.id = id;
        this.title = title;
        this.status = status;
        this.releaseDate = releaseDate;
        this.authorid = authorid;
        this.videoCount = videoCount;
        this.styleTags = styleTags;
        this.skillTags = skillTags;
        this.seriesTags = seriesTags;
        this.curriculumTags = curriculumTags;
        this.educator = educator;
        this.owned = owned;
        this.sale = sale;
        this.purchaseOrder = purchaseOrder;
        this.watched = watched;
        this.progressTracking = progressTracking;
    }

    protected Index(Parcel in) {
        if (in.readByte() == 0) {
            downloadid = null;
        } else {
            downloadid = in.readInt();
        }
        if (in.readByte() == 0) {
            cdDownloads = null;
        } else {
            cdDownloads = in.readInt();
        }
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        releaseDate = in.readString();
        if (in.readByte() == 0) {
            authorid = null;
        } else {
            authorid = in.readInt();
        }
        if (in.readByte() == 0) {
            videoCount = null;
        } else {
            videoCount = in.readInt();
        }
        styleTags = in.createStringArrayList();
        skillTags = in.createStringArrayList();
        seriesTags = in.createStringArrayList();
        curriculumTags = in.createStringArrayList();
        educator = in.readString();
        if (in.readByte() == 0) {
            owned = null;
        } else {
            owned = in.readInt();
        }
        if (in.readByte() == 0) {
            sale = null;
        } else {
            sale = in.readInt();
        }
        purchaseOrder = in.readInt();
        if (in.readByte() == 0) {
            watched = null;
        } else {
            watched = in.readInt();
        }
        progressTracking = in.readInt();
    }

    public static final Creator<Index> CREATOR = new Creator<Index>() {
        @Override
        public Index createFromParcel(Parcel in) {
            return new Index(in);
        }

        @Override
        public Index[] newArray(int size) {
            return new Index[size];
        }
    };

    public Integer getDownloadid() {
        return downloadid;
    }

    public void setDownloadid(Integer downloadid) {
        this.downloadid = downloadid;
    }

    public Integer getCdDownloads() {
        return cdDownloads;
    }

    public void setCdDownloads(Integer cdDownloads) {
        this.cdDownloads = cdDownloads;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getAuthorid() {
        return authorid;
    }

    public void setAuthorid(Integer authorid) {
        this.authorid = authorid;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public List<String> getStyleTags() {
        return styleTags;
    }

    public void setStyleTags(List<String> styleTags) {
        this.styleTags = styleTags;
    }

    public List<String> getSkillTags() {
        return skillTags;
    }

    public void setSkillTags(List<String> skillTags) {
        this.skillTags = skillTags;
    }

    public List<String> getSeriesTags() {
        return seriesTags;
    }

    public void setSeriesTags(List<String> seriesTags) {
        this.seriesTags = seriesTags;
    }

    public List<String> getCurriculumTags() {
        return curriculumTags;
    }

    public void setCurriculumTags(List<String> curriculumTags) {
        this.curriculumTags = curriculumTags;
    }

    public String getEducator() {
        return educator;
    }

    public void setEducator(String educator) {
        this.educator = educator;
    }

    public Integer getOwned() {
        return owned;
    }

    public void setOwned(Integer owned) {
        this.owned = owned;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public int getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(int purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Integer getWatched() {
        return watched;
    }

    public void setWatched(Integer watched) {
        this.watched = watched;
    }

    public int getProgressTracking() {
        return progressTracking;
    }

    public void setProgressTracking(int progressTracking) {
        this.progressTracking = progressTracking;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (downloadid == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(downloadid);
        }
        if (cdDownloads == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(cdDownloads);
        }
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(title);
        if (status == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(status);
        }
        parcel.writeString(releaseDate);
        if (authorid == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(authorid);
        }
        if (videoCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(videoCount);
        }
        parcel.writeStringList(styleTags);
        parcel.writeStringList(skillTags);
        parcel.writeStringList(seriesTags);
        parcel.writeStringList(curriculumTags);
        parcel.writeString(educator);
        if (owned == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(owned);
        }
        if (sale == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(sale);
        }
        parcel.writeInt(purchaseOrder);
        if (watched == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(watched);
        }
        parcel.writeInt(progressTracking);
    }
}
