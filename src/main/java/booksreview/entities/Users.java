package booksreview.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name="Users")
@Table(name="users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @CreationTimestamp
    private Date createdAt;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "addedByUser")
    private List<Books> books = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy")
    private List<Reviews> reviews = new ArrayList<>();


    public Users() {
    }

    public Users(int id, Date createdAt, String email,
                 String name) {
        this.id = id;
        this.createdAt = createdAt;
        this.email = email;
        this.name = name;
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

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }
}
