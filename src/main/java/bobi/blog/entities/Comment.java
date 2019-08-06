package bobi.blog.entities;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    private Integer id;
    private Article article;
    private String content;
    private User author;

    public Comment(Article article, String content, User author) {
        this.article = article;
        this.setContent(content);
        this.author = author;
    }

    public Comment(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(columnDefinition = "text", nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne()
    @JoinColumn(name = "users_id")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @ManyToOne()
    @JoinColumn(name = "articles_id")
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
