package org.core.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.core.util.Serializers.JsonDateTimeSerializer;
import org.core.util.Serializers.ObjectIdSerializer;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class Booking {
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    @NotNull
    private String uid;

    @NotNull
    private String court_id;

    @NotNull
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Timestamp created_time;

    @NotNull
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Timestamp booking_time;

    @NotNull
    private int duration;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCourt_id() {
        return court_id;
    }

    public void setCourt_id(String court_id) {
        this.court_id = court_id;
    }

    public Timestamp getCreated_time() {
        return created_time;
    }


    public void setCreated_time(Timestamp created_time) {
        this.created_time = created_time;
    }
    public void setCreated_time(String created_time) {
        this.created_time = Timestamp.valueOf(created_time);
    }


    public Timestamp getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(Timestamp booking_time) {
        this.booking_time = booking_time;
    }

    public void setBooking_time(String booking_time) {
        this.booking_time = Timestamp.valueOf(booking_time);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", court_id='" + court_id + '\'' +
                ", created_time=" + created_time.toString() +
                ", booking_time=" + booking_time.toString() +
                ", duration=" + duration +
                '}';
    }
}
