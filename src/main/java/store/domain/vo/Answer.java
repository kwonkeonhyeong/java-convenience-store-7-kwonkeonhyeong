package store.domain.vo;

import static store.util.constant.ErrorMessage.INVALID_FORMAT;
/*
* 긍정,부정에 대한 대답은 변하지 않는 값이므로 vo로 구현
* */
public class Answer {

    private static final String ANSWER_YES = "Y";
    private static final String ANSWER_NO = "N";

    private final String answer;

    private Answer(String answer) {
        this.answer = answer;
    }

    public static Answer from(String input) {
        validate(input);
        return new Answer(input);
    }

    public boolean isYes() {
        return answer.equals(ANSWER_YES);
    }

    private static void validate(String input) {
        if (!(input.equals(ANSWER_YES) || input.equals(ANSWER_NO))) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }
    }

}
