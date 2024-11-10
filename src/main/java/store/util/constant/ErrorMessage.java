package store.util.constant;
/*
* 공통적으로 사용되는 에러 메세지 관리
* */
public enum ErrorMessage {

    INVALID_FORMAT("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_INPUT("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),
    FAIL_FILE_UPLOAD("[Error] 파일을 업로드할 수 없습니다."),
    FAIL_FILE_UPDATE("[Error] 파일 업데이트를 실패했습니다");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
