package org.core.api;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;

public class BookingFindQuery {
    @NotNull
    private Date date;

    @NotNull
    private Time time;

    @NotNull
    private int duration;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) {
        this.date = Date.valueOf(date);
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setTime(String time) {
        this.time = Time.valueOf(time);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
