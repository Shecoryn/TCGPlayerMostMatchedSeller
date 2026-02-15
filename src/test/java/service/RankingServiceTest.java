package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shekor.tcgmatcher.dto.SellerMatch;
import org.shekor.tcgmatcher.service.RankingService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RankingServiceTest {

    private RankingService rankingService;
    private List<SellerMatch> sellers;

    @BeforeEach
    void setUp() {
        rankingService = new RankingService();
        sellers = new ArrayList<>();

        SellerMatch sellerA = new SellerMatch("SellerA");
        sellerA.addCard("Charizard");
        sellerA.addCard("Pikachu"); // 2 matches

        SellerMatch sellerB = new SellerMatch("SellerB");
        sellerB.addCard("Charizard"); // 1 match

        SellerMatch sellerC = new SellerMatch("SellerC");
        sellerC.addCard("Charizard");
        sellerC.addCard("Pikachu");
        sellerC.addCard("Blastoise"); // 3 matches

        sellers.add(sellerA);
        sellers.add(sellerB);
        sellers.add(sellerC);
    }

    @Test
    void testRankByMatchCount() {
        List<SellerMatch> ranked = rankingService.rankByMatchCount(sellers);

        // SellerC should be first (3 matches)
        assertEquals("SellerC", ranked.get(0).getSellerName());
        assertEquals(3, ranked.get(0).getMatchedCardCount());

        // SellerA should be second (2 matches)
        assertEquals("SellerA", ranked.get(1).getSellerName());
        assertEquals(2, ranked.get(1).getMatchedCardCount());

        // SellerB should be last (1 match)
        assertEquals("SellerB", ranked.get(2).getSellerName());
        assertEquals(1, ranked.get(2).getMatchedCardCount());
    }

    @Test
    void testGetTopMatches_limit2() {
        List<SellerMatch> top2 = rankingService.getTopMatches(sellers, 2);

        assertEquals(2, top2.size());
        assertEquals("SellerC", top2.get(0).getSellerName());
        assertEquals("SellerA", top2.get(1).getSellerName());
    }

    @Test
    void testGetTopMatches_limitExceedsList() {
        List<SellerMatch> top10 = rankingService.getTopMatches(sellers, 10);

        // Should return all sellers if limit > list size
        assertEquals(3, top10.size());
        assertEquals("SellerC", top10.get(0).getSellerName());
        assertEquals("SellerB", top10.get(2).getSellerName());
    }
}
