package bobi.blog.bindingModels;

import javax.validation.constraints.NotNull;

public class ArticleCommentBindingModel {
    @NotNull
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
