package main.java.org.shekor.tcgmatcher.dto;

import java.util.HashSet;
import java.util.Set;

public class SellerMatch {
    private String sellerName;
    private double sellerRating;
    private Set<String> matchedCards = new HashSet<>();

    public SellerMatch(String sellerName, double sellerRating) {
        this.sellerName = sellerName;
        this.sellerRating = sellerRating;
    }

    public String getSellerName() {
        return sellerName;
    }

    public double getSellerRating() {
        return sellerRating;
    }

    public Set<String> getMatchedCards() {
        return matchedCards;
    }

    public void addCard(String card) {
        matchedCards.add(card);
    }

    public int getMatchedCardCount() {
        return matchedCards.size();
    }
}
