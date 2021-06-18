package org.core.util;

import org.bson.Document;
import org.core.api.User;

public class UserMapper {
    public static User map(final Document userDocument,int isPassRequired) {
        final User user = new User();
        user.setId(userDocument.getObjectId("_id"));
        user.setUsername(userDocument.getString("username"));
        user.setEmail(userDocument.getString("email"));
        user.setMobile(userDocument.getString("mobile"));
        user.setLastName(userDocument.getString("lastName"));
        user.setFirstName(userDocument.getString("firstName"));
        user.setRole(userDocument.getString("role"));
        if(isPassRequired > 0) {
            user.setPassword(userDocument.getString("password"));
        }
        return user;
    }
}
