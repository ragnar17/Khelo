package org.core.util;

import org.bson.Document;
import org.core.api.Seller;

import java.util.ArrayList;
import java.util.List;

public class SellerMapper {
    public static Seller map(final Document sellerDocument) {
        final Seller seller = new Seller();
        seller.setId(sellerDocument.getObjectId("_id"));
        seller.setAddress(sellerDocument.getString("address"));
//        seller.setDocs((List<ArrayList>) sellerDocument.get("docs"));
        seller.setGstin(sellerDocument.getString("gstin"));
        seller.setPan(sellerDocument.getString("pan"));
        seller.setAadhar(sellerDocument.getString("aadhar"));
        seller.setUid(sellerDocument.getString("uid"));

        return seller;
    }
}
