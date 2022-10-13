package booksreview.entities;

import javax.persistence.*;

@Entity(name="Categories")
@Table(name="categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false, length = 150, unique = true)
    private String category;

    public Categories() {
    }

    public Categories(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
