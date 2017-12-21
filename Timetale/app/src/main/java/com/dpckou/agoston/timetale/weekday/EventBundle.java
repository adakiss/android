package com.dpckou.agoston.timetale.weekday;

import android.os.Parcel;
import android.os.Parcelable;

import com.dpckou.agoston.timetale.persistence.Event;

import java.util.ArrayList;
import java.util.List;


public class EventBundle implements Parcelable {

    public static final String NAME = "eventBundleParcelableName_unique_";

    private Event event;

    public Event getEvent() {
        return event;
    }

    public EventBundle(Event event){
        this.event = event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    private EventBundle(Parcel in) {
        Event e = new Event();
        //friends list
        List<String> _fr = new ArrayList<>();
        in.readList(_fr, null);
        e.setEventFriends(_fr);
        //event name
        e.setEventName(in.readString());
        //event description
        e.setEventDescription(in.readString());
        //event from
        e.setEventStart(in.readLong());
        //event to
        e.setEventEnd(in.readLong());
        //event location
        e.setEventLocation(in.readString());
        //event id
        e.setId(in.readInt());

        event = e;

    }

    public static final Creator<EventBundle> CREATOR = new Creator<EventBundle>() {
        @Override
        public EventBundle createFromParcel(Parcel in) {
            return new EventBundle(in);
        }

        @Override
        public EventBundle[] newArray(int size) {
            return new EventBundle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //THE ORDER MATTERS GREATLY!!!
        //friends list
        parcel.writeList(event.getFriends());
        //event name
        parcel.writeString(event.getEventName());
        //event description
        parcel.writeString(event.getEventDescription());
        //event from
        parcel.writeLong(event.getEventStart());
        //event to
        parcel.writeLong(event.getEventEnd());
        //event location
        parcel.writeString(event.getEventLocation());
        //event id
        parcel.writeInt(event.getId());
    }
}
