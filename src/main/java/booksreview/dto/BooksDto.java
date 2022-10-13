package booksreview.dto;

import java.util.Date;
import java.util.List;

public class BooksDto {
    private int id;
    private Date addedAt;
    private UsersDto addedBy;
    private String bookName;
    private String bookAuthor;
    private String bookLanguage;
    private int bookYear;
    private String bookCategory;
    private String articleText;
    private List<ReviewsDto> reviews;

    public BooksDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsersDto getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(UsersDto addedBy) {
        this.addedBy = addedBy;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookLanguage() {
        return bookLanguage;
    }

    public void setBookLanguage(String bookLanguage) {
        this.bookLanguage = bookLanguage;
    }

    public int getBookYear() {
        return bookYear;
    }

    public void setBookYear(int bookYear) {
        this.bookYear = bookYear;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

    public int getReviews() {
        return reviews.size();
    }

    public void setReviews(List<ReviewsDto> reviews) { this.reviews = reviews; }
}
