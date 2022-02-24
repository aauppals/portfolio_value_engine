package instrument;

import java.util.Arrays;

public enum OptionType {
    CALL("C"),
    PUT("P");
    private final String text;

    OptionType(String t) {
        this.text = t;
    }

    public String getText() {
        return text;
    }

    public static OptionType fromText(String text) {
        OptionType[] values = values();
        for (OptionType value : values) {
            if (value.text.equals(text)) {
                return value;
            }
        }
        return null;
    }

}
