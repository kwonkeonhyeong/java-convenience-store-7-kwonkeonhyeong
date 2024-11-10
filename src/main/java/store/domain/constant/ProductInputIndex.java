package store.domain.constant;

public enum ProductInputIndex {

    NAME_INDEX(0),
    PRICE_INDEX(1),
    QUANTITY_INDEX(2),
    PROMOTION_INDEX(3);

    private final int index;

    ProductInputIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
