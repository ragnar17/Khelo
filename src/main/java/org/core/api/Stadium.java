package org.core.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.core.util.Serializers.ObjectIdSerializer;

import javax.validation.constraints.NotNull;

public class Stadium {
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    @NotNull
    private String sellerid;

    @NotNull
    private String name;

    @NotNull
    private String address;

    public Stadium(){
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
