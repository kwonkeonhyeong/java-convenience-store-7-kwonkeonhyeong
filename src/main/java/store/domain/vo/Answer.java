package store.domain.vo;

import static store.util.constant.ErrorMessage.INVALID_FORMAT;

public class Answer {

    private final String answer;

    private Answer(String answer) {
        this.answer = answer;
    }

    public static Answer from(String input) {
        validate(input);
        return new Answer(input);
    }

    public boolean isYes() {
        return answer.equals("Y");
    }

    private static void validate(String input) {
        if (!(input.equals("Y") || input.equals("N"))) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }
    }

}
