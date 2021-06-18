package org.core.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.core.util.Serializers.ObjectIdSerializer;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    @NotNull
    private String firstName;

    private String lastName;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    private String mobile;

    @NotNull
    private String role;

    public User(){

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
