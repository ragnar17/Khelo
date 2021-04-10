package org.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
        public LoginResponse(String token) {
            this.token = token;
        }

        @JsonProperty("token")
        public String token;
}
