package store.util.constant;

public enum FilePath {

    PRODUCTS_FILE_PATH("src/main/resources/products.md"),
    PROMOTIONS_FILE_PATH("src/main/resources/promotions.md");

    private final String path;

    FilePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
