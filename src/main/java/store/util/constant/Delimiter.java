package store.util.constant;
/*
* 공통적으로 사용되는 구분자 관리
* */
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
