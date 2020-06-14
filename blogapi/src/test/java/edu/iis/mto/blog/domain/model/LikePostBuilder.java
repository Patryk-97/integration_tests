package edu.iis.mto.blog.domain.model;

public class LikePostBuilder {

    private Long id = null;

    private User user = null;

    private BlogPost blogPost = null;

    public LikePost build() {
        LikePost likePost = new LikePost();
        if (id != null) {
            likePost.setId(id);
        }
        likePost.setUser(user);
        likePost.setPost(blogPost);
        return likePost;
    }

    public LikePostBuilder withId(Long id) {
        this.id = id;
        return this;
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
