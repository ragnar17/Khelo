package org.core.util;

import org.bson.Document;
import org.core.api.Court;

public class CourtMapper {
    public static Court map(final Document courtDocument) {
        final Court court = new Court();
        court.setId(courtDocument.getObjectId("_id"));
        court.setStadium_id(courtDocument.getString("stadium_id"));
        court.setType(courtDocument.getString("type"));
        court.setCourt_number(courtDocument.getString("court_number"));
        return court;
    }
}
