package service;

import org.junit.jupiter.api.Test;
import org.shekor.tcgmatcher.dto.SellerMatch;
import org.shekor.tcgmatcher.service.SellerMatcherService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SellerMatcherServiceTest {

    @Test
    void testFindMatches() {
        SellerMatcherService matcher = new SellerMatcherService();

        Map<String, List<String>> mockData = Map.of(
                "Charizard", List.of("SellerA", "SellerB"),
                "Pikachu", List.of("SellerA")
        );

        List<String> requested = List.of("Charizard", "Pikachu");

        List<SellerMatch> matches = matcher.findMatches(requested, mockData);

        assertEquals(2, matches.size()); // Two sellers found
        assertTrue(matches.stream()
                .anyMatch(s -> s.getSellerName().equals("SellerA") &&
                        s.getMatchedCardCount() == 2));
    }
}
