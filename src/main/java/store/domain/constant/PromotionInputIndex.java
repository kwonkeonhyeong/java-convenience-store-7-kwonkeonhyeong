package store.domain.constant;

public enum PromotionInputIndex {

    NAME_INDEX(0),
    BUY_INDEX(1),
    GET_INDEX(2),
    START_DATE_INDEX(3),
    END_DATE_INDEX(4);

    private final int index;

    PromotionInputIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
