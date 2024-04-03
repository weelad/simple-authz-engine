package com.ping.simpleauthzengine;

import java.util.Map;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

public class Rule {

    public String key;

    public String type;

    public String comparand;

    public Pattern pattern;

    public Decision evaluate(Map<String, Object> context) {
        if (context == null) return Decision.DENY;

        Object value = context.get(this.key);

        switch (this.type) {
            case "EXISTS":
                return value != null ? Decision.PERMIT : Decision.DENY;

            case "GREATER_THAN":
                if (!(value instanceof Number)) {
                    return Decision.DENY;
                }
                return ((Number) value).doubleValue() > parseDouble(comparand) ? Decision.PERMIT : Decision.DENY;

            case "LESS_THAN":
                if (!(value instanceof Number)) {
                    return Decision.DENY;
                }
                return ((Number) value).doubleValue() < parseDouble(comparand) ? Decision.PERMIT : Decision.DENY;

            case "MATCH":
                if (value == null) {
                    return Decision.DENY;
                }
                return pattern.matcher(String.valueOf(value)).matches() ? Decision.PERMIT : Decision.DENY;
            default:
                return Decision.DENY;
        }
    }
}

