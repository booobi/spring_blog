package bobi.blog.config;

import bobi.blog.entities.Article;
import bobi.blog.entities.Comment;
import bobi.blog.models.viewModels.ArticleViewModel;
import bobi.blog.models.viewModels.CommentViewModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;

@Configuration
public class BeanConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        Converter<Comment, CommentViewModel> commentConverter = new Converter<Comment, CommentViewModel>() {
            @Override
            public CommentViewModel convert(MappingContext<Comment, CommentViewModel> mappingContext) {
                Comment src = mappingContext.getSource();
                CommentViewModel dest = mappingContext.getDestination();

                dest.setId(src.getId());
                dest.setContent(src.getContent());
                dest.setAuthorName(src.getAuthor().getFullName());

                return dest;
            }
        };
        modelMapper.addConverter(commentConverter);

        Converter<Article, ArticleViewModel> articleConverter = new Converter<Article, ArticleViewModel>() {
            @Override
            public ArticleViewModel convert(MappingContext<Article, ArticleViewModel> mappingContext) {
                Article src = mappingContext.getSource();
                ArticleViewModel dst = mappingContext.getDestination();

                dst.setId(src.getId());
                dst.setContent(src.getContent());
                dst.setTitle(src.getTitle());
                dst.setAuthorName(src.getAuthor().getFullName());
                dst.setCategoryName(src.getCategory().getName());

                return dst;
            }
        };
        modelMapper.addConverter(articleConverter);

        return modelMapper;
    }
}
