package org.core.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.core.util.Serializers.ObjectIdSerializer;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Seller {
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    @NotNull
    private String uid;

    @NotNull
    private String gstin;

    @NotNull
    private String pan;

    @NotNull
    private String aadhar;

//    @NotNull
//    private List<ArrayList> docs;

    public Seller(){

    }
    @NotNull
    private String address;

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", gstin='" + gstin + '\'' +
                ", pan='" + pan + '\'' +
                ", aadhar='" + aadhar + '\'' +
//                ", docs='" + docs + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
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

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

//    public List<ArrayList> getDocs() {
//        return docs;
//    }
//
//    public void setDocs(List<ArrayList> docs) {
//        this.docs = docs;
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
