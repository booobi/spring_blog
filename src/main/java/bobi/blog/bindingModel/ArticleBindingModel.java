package bobi.blog.bindingModel;

import bobi.blog.entities.Category;

import javax.validation.constraints.NotNull;

public class ArticleBindingModel {

    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private Integer categoryId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
