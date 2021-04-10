package org.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProtectedResourceResponse {

    public ProtectedResourceResponse(String role, String username) {
        this.role = role;
        this.username = username;
    }

    @JsonProperty("role")
    public String role;

    @JsonProperty("username")
    public String username;
}
