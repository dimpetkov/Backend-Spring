package booksreview.dto;

import java.util.Date;

public class ReviewsDto {
    private int id;
    private Date createdAt;
    private BooksDto book;
    private UsersDto createdBy;
    private String reviewText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy.getName();
    }

    public void setCreatedBy(UsersDto createdBy) {
        this.createdBy = createdBy;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public BooksDto getBook() {
        return book;
    }

    public void setBook(BooksDto book) {
        this.book = book;
    }
}
