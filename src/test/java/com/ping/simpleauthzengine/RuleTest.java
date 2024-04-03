package com.ping.simpleauthzengine;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuleTest {

    @Test
    void testExistsRuleReturnsPermitWhenKeyIsPresent() {
        Rule rule = new Rule();
        rule.type = "EXISTS";
        rule.key = "Wally";

        Decision actual = rule.evaluate(Map.of("Wally", ""));

        assertEquals(Decision.PERMIT, actual);
    }

    @Test
    void testExistsRuleReturnsDenyWhenKeyIsMissing() {
        Rule rule = new Rule();
        rule.type = "EXISTS";
        rule.key = "Wally";

        Decision actual = rule.evaluate(Map.of("Cake", ""));

        assertEquals(Decision.DENY, actual);
    }
}
