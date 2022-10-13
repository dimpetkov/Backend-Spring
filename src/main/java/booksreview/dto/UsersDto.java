package booksreview.dto;
import java.util.Date;
import java.util.List;

public class UsersDto {
    private int id;
    private Date createdAt;
    private String email;
    private String name;
    private List<BooksDto> books;
    private List<ReviewsDto> reviews;

    public UsersDto() {
    }


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBooks(List<BooksDto> books) {
        this.books = books;
    }

    public int getBooks() {
        return books.size();
    }

    public int getReviews() {
        return reviews.size();
    }

    public void setReviews(List<ReviewsDto> reviews) {
        this.reviews = reviews;
    }
}
