package booksreview.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name="Books")
@Table(name="books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @CreationTimestamp
    private Date addedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addedByUser")
    private Users addedByUser;
    @Column(nullable = false, unique = true)
    private String bookName;
    @Column(nullable = false)
    private String bookAuthor;
    @Column(nullable = false)
    private String bookLanguage;

    private int bookYear;
    @Column(nullable = false)
    private String bookCategory;

    @Column(length = 1000)
    private String articleText;
    @OneToMany(mappedBy = "book")
    private List<Reviews> reviews = new ArrayList<>();


    public Books() {
    }

    public Books(int id, Users addedByUser, Date addedAt,
                 String bookName, String bookAuthor, String bookLanguage,
                 int bookYear, String bookCategory, String articleText) {
        this.id = id;
        this.addedByUser = addedByUser;
        this.addedAt = addedAt;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookLanguage = bookLanguage;
        this.bookYear = bookYear;
        this.bookCategory = bookCategory;
        this.articleText = articleText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getAddedByUser() {
        return addedByUser;
    }

    public void setAddedByUser(Users addedByUser) {
        this.addedByUser = addedByUser;
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

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }
}
