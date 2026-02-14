package org.shekor.tcgmatcher;

import org.shekor.tcgmatcher.data.MockTcgPlayerData;
import org.shekor.tcgmatcher.dto.SellerMatch;
import org.shekor.tcgmatcher.service.RankingService;
import org.shekor.tcgmatcher.service.SellerMatcherService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import  java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MostMatchedSellerMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter card names seperated by commas:\n");
        String input = scanner.nextLine();

        List<String> requestedCardsA = List.of(
                "Roserade",
                "Cloyster",
                "Banette"
        );

        List<String> requestedCardsB = List.of(
                "Roserade",
                "Steelix"
        );

        List<String> requestedCardsC =
                Arrays.stream(input.split(","))
                        .map(String::trim)
                        .toList();

        Map<String, List<String>> marketPlace =
                MockTcgPlayerData.getCardToSellers();

        // Create service instances
        SellerMatcherService matcherService = new SellerMatcherService();
        RankingService rankingService = new RankingService();

        // Step 1: Find matches
        List<SellerMatch> matches =
                matcherService.findMatches(requestedCardsC, marketPlace);

        // Step 2: Rank sellers
        List<SellerMatch> ranked =
                rankingService.getTopMatches(matches, 20);

        // Step 3: Display results (presentation layer)
        displayResults(ranked, requestedCardsC.size());
    }

    private static void displayResults(
            List<SellerMatch> sellers,
            int totalRequested) {

        List<SellerMatch> fullMatches = sellers.stream()
                .filter(s -> s.getMatchedCardCount() == totalRequested)
                .toList();

        if (!fullMatches.isEmpty()) {
            System.out.println("Sellers with ALL requested cards:");
            for (SellerMatch seller : fullMatches) {
                System.out.println(seller.getSellerName());
            }
        } else {
            System.out.println("No full matches found.");
            System.out.println("Top matches:");

            for (SellerMatch seller : sellers) {
                System.out.println(
                        seller.getSellerName() +
                                " - Matches: " +
                                seller.getMatchedCardCount());
            }
        }
    }
}
