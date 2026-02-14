package org.shekor.tcgmatcher.service;

import org.shekor.tcgmatcher.dto.SellerMatch;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RankingService {

    // Rank sellers by match count (highest first)
    public List<SellerMatch> rankByMatchCount(List<SellerMatch> sellers) {

        return sellers.stream()
                .sorted(Comparator.comparingInt(SellerMatch::getMatchedCardCount)
                        .reversed())
                .collect(Collectors.toList());
    }

    // Return top N sellers
    public List<SellerMatch> getTopMatches(List<SellerMatch> sellers, int limit) {

        return rankByMatchCount(sellers).stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
}
