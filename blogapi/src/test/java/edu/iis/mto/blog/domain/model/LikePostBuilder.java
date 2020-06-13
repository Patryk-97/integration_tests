package edu.iis.mto.blog.domain.model;

public class LikePostBuilder {

    private User user = null;

    private BlogPost blogPost = null;

    public LikePost build() {
        LikePost likePost = new LikePost();
        likePost.setUser(user);
        likePost.setPost(blogPost);
        return likePost;
    }

    public LikePostBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public LikePostBuilder withBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
        return this;
    }
}
