package store.util.constant;

public enum Delimiter {

    COMMA(","),
    DASH("-");

    private final String delimiter;

    Delimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getDelimiter() {
        return delimiter;
    }

}
