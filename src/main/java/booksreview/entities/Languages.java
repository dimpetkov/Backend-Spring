package booksreview.entities;

import javax.persistence.*;
import java.util.List;

@Entity(name="Languages")
@Table(name="languages")
public class Languages {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false, length = 100, unique = true)
    private String language;

    public Languages() {
    }

    public Languages(int id, String language, List<Books> books) {
        this.id = id;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
