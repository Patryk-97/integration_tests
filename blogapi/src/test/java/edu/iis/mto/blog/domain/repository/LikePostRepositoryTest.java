package edu.iis.mto.blog.domain.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.BlogPostBuilder;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.LikePostBuilder;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.model.UserBuilder;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikePostRepository likePostRepository;

    private LikePost likePost;
    private BlogPost blogPost1;
    private BlogPost blogPost2;
    private User user1;
    private User user2;
    private UserBuilder userBuilder;
    private LikePostBuilder likePostBuilder;
    private BlogPostBuilder blogPostBuilder;

    @Before
    public void setUp() {
        userBuilder = new UserBuilder();
        likePostBuilder = new LikePostBuilder();
        blogPostBuilder = new BlogPostBuilder();
        user1 = userBuilder.withFirstName("John")
                           .withLastName("Obama")
                           .withEmail("obama@domain.com")
                           .withAccountStatus(AccountStatus.CONFIRMED)
                           .build();
        user2 = userBuilder.withFirstName("Jack")
                           .withLastName("Shepard")
                           .withEmail("shepard@lost.com")
                           .withAccountStatus(AccountStatus.CONFIRMED)
                           .build();
        blogPost1 = blogPostBuilder.withUser(user1)
                                   .withEntry("entry")
                                   .build();
        blogPost2 = blogPostBuilder.withUser(user2)
                                   .withEntry("new entry")
                                   .build();
        likePost = likePostBuilder.withUser(user1)
                                  .withBlogPost(blogPost1)
                                  .build();
    }

    @Test
    public void shouldFindNoLikePostsIfRepositoryIsEmpty() {

        List<LikePost> likes = likePostRepository.findAll();

        assertThat(likes, hasSize(0));
    }

    @Test
    public void shouldFindOneLikePostIfRepositoryContainsOneLikePostEntity() {
        entityManager.persist(user1);
        entityManager.persist(blogPost1);
        entityManager.persist(likePost);
        List<LikePost> likes = likePostRepository.findAll();

        assertThat(likes, hasSize(1));
        assertThat(likes.get(0)
                        .getUser(),
                equalTo(user1));
    }

    @Test
    public void shouldStoreALikePost() {
        entityManager.persist(user1);
        entityManager.persist(blogPost1);

        LikePost savedLikePost = likePostRepository.save(likePost);

        assertThat(savedLikePost.getId(), notNullValue());
    }

    @Test
    public void shouldModifyUserForLikePost() {
        entityManager.persist(user1);
        entityManager.persist(blogPost1);
        entityManager.persist(user2);
        entityManager.persist(blogPost2);

        likePostRepository.save(likePost);
        LikePost foundLikePost = likePostRepository.findById(likePost.getId())
                                                   .get();
        foundLikePost.setUser(user2);
        likePostRepository.save(foundLikePost);

        foundLikePost = likePostRepository.findById(likePost.getId())
                                          .get();
        assertThat(foundLikePost.getUser(), is(user2));
    }

    @Test
    public void shouldModifyBlogPostForLikePost() {
        entityManager.persist(user1);
        entityManager.persist(blogPost1);
        entityManager.persist(user2);
        entityManager.persist(blogPost2);

        likePostRepository.save(likePost);
        LikePost foundLikePost = likePostRepository.findById(likePost.getId())
                                                   .get();
        foundLikePost.setPost(blogPost2);
        likePostRepository.save(foundLikePost);

        foundLikePost = likePostRepository.findById(likePost.getId())
                                          .get();
        assertThat(foundLikePost.getPost(), is(blogPost2));
    }

    @Test
    public void shouldFindLikePostIdentifiedByUserAndBlogPost() {
        entityManager.persist(user1);
        entityManager.persist(blogPost1);

        likePostRepository.save(likePost);
        LikePost foundLikePost = likePostRepository.findByUserAndPost(user1, blogPost1)
                                                   .get();

        assertThat(foundLikePost.getUser(), is(user1));
        assertThat(foundLikePost.getPost(), is(blogPost1));
    }

    @Test
    public void shouldNotFindLikePostIdentifiedByUserBlogPostIsNull() {
        entityManager.persist(user1);
        entityManager.persist(blogPost1);

        likePostRepository.save(likePost);
        assertThrows(NoSuchElementException.class, () -> likePostRepository.findByUserAndPost(user1, null)
                                                                           .get());
    }

    @Test
    public void shouldNotFindLikePostIdentifiedByBlogPostUserIsNull() {
        entityManager.persist(user1);
        entityManager.persist(blogPost1);

        likePostRepository.save(likePost);
        assertThrows(NoSuchElementException.class, () -> likePostRepository.findByUserAndPost(null, blogPost1)
                                                                           .get());
    }

    @Test
    public void shouldNotFindLikePostUserAndBlogPostAreNull() {
        entityManager.persist(user1);
        entityManager.persist(blogPost1);

        likePostRepository.save(likePost);
        assertThrows(NoSuchElementException.class, () -> likePostRepository.findByUserAndPost(null, null)
                                                                           .get());
    }
}
