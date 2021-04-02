package org.core.util;

import org.core.api.Donut;
import org.bson.Document;


public class DonutMapper {
    public static Donut map(final Document donutDocument) {
        final Donut donut = new Donut();
        donut.setId(donutDocument.getObjectId("_id"));
        donut.setFlavor(donutDocument.getString("flavor"));
        donut.setPrice(donutDocument.getDouble("price"));
        return donut;
    }
}
