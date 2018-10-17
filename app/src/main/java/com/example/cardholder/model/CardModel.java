package com.example.cardholder.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Required;

public class CardModel extends RealmObject implements Parcelable {

    @Required
    private String name;
    @Required
    private String code;
    @Required
    private byte[] bitmapArray;
    @Ignore
    private Bitmap photoId;

    public CardModel() {
        if (null != bitmapArray) {
            photoId = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        }
    }

    private CardModel(Parcel in) {
        name = in.readString();
        code = in.readString();
        photoId = Bitmap.CREATOR.createFromParcel(in);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getPhoto() {
        return photoId;
    }

    public void setPhoto(Bitmap photoId) {
        this.photoId = photoId;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photoId.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        bitmapArray = stream.toByteArray();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void copyFrom(CardModel source) {
        name = source.getName();
        code = source.getCode();
        photoId = source.getPhoto();
        bitmapArray = source.bitmapArray;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(code);
        photoId.writeToParcel(dest, 0);
    }

    public static final Creator<CardModel> CREATOR = new Creator<CardModel>() {
        @Override
        public CardModel createFromParcel(Parcel in) {
            return new CardModel(in);
        }

        @Override
        public CardModel[] newArray(int size) {
            return new CardModel[size];
        }
    };
}
