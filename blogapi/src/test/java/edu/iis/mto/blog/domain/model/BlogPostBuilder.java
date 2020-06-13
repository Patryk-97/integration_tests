package edu.iis.mto.blog.domain.model;

import java.util.List;

public class BlogPostBuilder {

    private User user;

    private String entry;

    private List<LikePost> likes;

    public BlogPost build() {
        BlogPost blogPost = new BlogPost();
        blogPost.setUser(user);
        blogPost.setEntry(entry);
        blogPost.setLikes(likes);
        return blogPost;
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
