package booksreview.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity(name="Reviews")
@Table(name="reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @CreationTimestamp
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "book")
    private Books book;

    @ManyToOne
    @JoinColumn(name = "createdBy")
    private Users createdBy;

    @Column(length = 1000)
    private String reviewText;


    public Reviews() {
    }

    public Reviews(int id, Books book, Date createdAt,
                   Users createdBy, String reviewText) {
        this.id = id;
        this.book = book;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.reviewText = reviewText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
