package booksreview.dto;

public class CategoriesDto {
    private int id;
    private String category;

    private CategoriesDto() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }
}
