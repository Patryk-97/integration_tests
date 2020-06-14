package edu.iis.mto.blog.domain.model;

import java.util.List;

public class BlogPostBuilder {

    private Long id = null;

    private User user = null;

    private String entry = "";

    private List<LikePost> likes = null;

    public BlogPost build() {
        BlogPost blogPost = new BlogPost();
        if (id != null) {
            blogPost.setId(id);
        }
        blogPost.setUser(user);
        blogPost.setEntry(entry);
        blogPost.setLikes(likes);
        return blogPost;
    }

    public BlogPostBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public BlogPostBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public BlogPostBuilder withEntry(String entry) {
        this.entry = entry;
        return this;
    }

    public BlogPostBuilder addLike(LikePost likePost) {
        likes.add(likePost);
        return this;
    }

    public BlogPostBuilder withLikes(List<LikePost> likes) {
        this.likes = likes;
        return this;
    }
}
