package store.domain;

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
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }
}
