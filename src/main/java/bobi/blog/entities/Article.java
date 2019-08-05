package bobi.blog.entities;

import javax.persistence.*;

@Entity
@Table(name = "articles")
public class Article {

    public Article(String title, String content, User author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Article(){}

    private Integer id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(columnDefinition = "text", nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name = "authorId")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    private String title;
    private String content;
    private User author;
}
