package org.shekor.tcgmatcher.service;

import org.shekor.tcgmatcher.dto.SellerMatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerMatcherService {

    public List<SellerMatch> findMatches(
            List<String> requestedCards,
            Map<String, List<String>> cardToSellers) {

        Map<String, SellerMatch> sellerMap = new HashMap<>();

        for (String card : requestedCards) {

            List<String> sellers = cardToSellers.get(card);

            if (sellers == null) {
                continue; // No sellers for this card
            }

            for (String seller : sellers) {

                sellerMap
                        .computeIfAbsent(seller, SellerMatch::new)
                        .addCard(card);
            }
        }

        return new ArrayList<>(sellerMap.values());
    }
}
