package org.core.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.core.util.Serializers.ObjectIdSerializer;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Court {
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    @NotNull
    private String stadium_id;

    @NotNull
    private String type;

    @NotNull
    private String court_number;

    private List<String> active_bookings;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getStadium_id() {
        return stadium_id;
    }

    public void setStadium_id(String stadium_id) {
        this.stadium_id = stadium_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCourt_number() {
        return court_number;
    }

    public void setCourt_number(String court_number) {
        this.court_number = court_number;
    }
}
