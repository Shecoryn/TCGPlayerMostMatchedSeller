package org.shekor.tcgmatcher.data;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MockTcgPlayerData {

    public static Map<String, List<String>> getCardToSellers() {

        Map<String, List<String>> data = new HashMap<>();

        data.put("Roserade", List.of("SellerA", "SellerB", "SellerC"));
        data.put("Cloyster", List.of("SellerA", "SellerD"));
        data.put("Banette", List.of("SellerB", "SellerC"));
        data.put("Steelix", List.of("SellerA", "SellerB"));

        return data;
    }
}

