package com.dpckou.agoston.timetale;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by agoston on 2017.11.12..
 */

public class ContactFriend implements Parcelable {
    private String nickName;

    public static final Creator<ContactFriend> CREATOR = new Creator<ContactFriend>() {
        @Override
        public ContactFriend createFromParcel(Parcel in) {
            return new ContactFriend(in);
        }

        @Override
        public ContactFriend[] newArray(int size) {
            return new ContactFriend[size];
        }
    };

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;
    public ContactFriend(String nickName) {
        this.nickName = nickName;
    }

    private ContactFriend(Parcel in){
        boolean[] _read = new boolean[1];
        in.readBooleanArray(_read);
        isSelected = _read[0];
        nickName = in.readString();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nickName);
        parcel.writeBooleanArray(new boolean[]{isSelected});
    }
}
