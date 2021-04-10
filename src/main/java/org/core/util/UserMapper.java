package org.core.util;

import org.bson.Document;
import org.core.api.User;

public class UserMapper {
    public static User map(final Document userDocument) {
        final User user = new User();
        user.setId(userDocument.getObjectId("_id"));
        user.setUsername(userDocument.getString("username"));
        user.setEmail(userDocument.getString("email"));
        user.setPassword(userDocument.getString("password"));
        user.setMobile(userDocument.getString("mobile"));
        return user;
    }
}
