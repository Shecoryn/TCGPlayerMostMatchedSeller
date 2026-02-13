package main.java.org.shekor.tcgmatcher.dto;

public class CardRequest {
    private String cardName;
    private String cardSet;

    public CardRequest(String cardName, String cardSet){
        this.cardName = cardName;
        this.cardSet = cardSet;
    }
    public String getCardName() {
        return cardName;
    }

    public String getCardSet() {
        return cardSet;
    }
    //No setters because we are not saving data
}

