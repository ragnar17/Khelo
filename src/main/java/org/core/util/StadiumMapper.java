package org.core.util;

import org.bson.Document;
import org.core.api.Stadium;

public class StadiumMapper {
    public static Stadium map(final Document stadiumDocument) {
        final Stadium stadium = new Stadium();
        stadium.setId(stadiumDocument.getObjectId("_id"));
        stadium.setName(stadiumDocument.getString("name"));
        stadium.setSellerid(stadiumDocument.getString("sellerid"));
        stadium.setAddress(stadiumDocument.getString("address"));

        return stadium;
    }
}
