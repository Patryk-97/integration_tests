package edu.iis.mto.blog.domain;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.domain.errors.DomainError;
import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.BlogPostBuilder;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.model.UserBuilder;
import edu.iis.mto.blog.domain.repository.BlogPostRepository;
import edu.iis.mto.blog.domain.repository.LikePostRepository;
import edu.iis.mto.blog.domain.repository.UserRepository;
import edu.iis.mto.blog.mapper.BlogDataMapper;
import edu.iis.mto.blog.services.BlogService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogManagerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BlogPostRepository blogPostRepository;

    @MockBean
    private LikePostRepository likePostRepository;

    @Autowired
    private BlogDataMapper dataMapper;

    @Autowired
    private BlogService blogService;

    @Captor
    private ArgumentCaptor<User> userParam;

    private UserBuilder userBuilder;

    private BlogPostBuilder blogPostBuilder;

    private User supporter;

    private User blogger;

    private BlogPost blogPost;

    @Before
    public void setUp() {
        userBuilder = new UserBuilder();
        blogPostBuilder = new BlogPostBuilder();
        blogger = buildUser(2L, "", "", "", AccountStatus.CONFIRMED);
        blogPost = buildBlogPost(1L, blogger, "", null);
    }

    @Test
    public void creatingNewUserShouldSetAccountStatusToNEW() {
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        verify(userRepository).save(userParam.capture());
        User user = userParam.getValue();
        assertThat(user.getAccountStatus(), Matchers.equalTo(AccountStatus.NEW));
    }

    @Test
    public void addLikeToPostByUserWithConfirmedAccountStatusShouldNotThrowDomainErrorException() {
        supporter = buildUser(1L, "", "", "", AccountStatus.CONFIRMED);

        when(userRepository.findById(supporter.getId())).thenReturn(Optional.of(supporter));
        when(blogPostRepository.findById(blogPost.getId())).thenReturn(Optional.of(blogPost));
        when(likePostRepository.findByUserAndPost(supporter, blogPost)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> blogService.addLikeToPost(supporter.getId(), blogPost.getId()));
    }

    @Test
    public void addLikeToPostByUserWithNewAccountStatusShouldThrowDomainErrorException() {
        supporter = buildUser(1L, "", "", "", AccountStatus.NEW);

        when(userRepository.findById(supporter.getId())).thenReturn(Optional.of(supporter));
        when(blogPostRepository.findById(blogPost.getId())).thenReturn(Optional.of(blogPost));

        assertThrows(DomainError.class, () -> blogService.addLikeToPost(supporter.getId(), blogPost.getId()));
    }

    @Test
    public void addLikeToPostByUserWithRemovedAccountStatusShouldThrowDomainErrorException() {
        supporter = buildUser(1L, "", "", "", AccountStatus.REMOVED);

        when(userRepository.findById(supporter.getId())).thenReturn(Optional.of(supporter));
        when(blogPostRepository.findById(blogPost.getId())).thenReturn(Optional.of(blogPost));

        assertThrows(DomainError.class, () -> blogService.addLikeToPost(supporter.getId(), blogPost.getId()));
    }

    private User buildUser(Long id, String firstName, String lastName, String email, AccountStatus accountStatus) {
        return userBuilder.withId(id)
                          .withFirstName(firstName)
                          .withLastName(lastName)
                          .withEmail(email)
                          .withAccountStatus(accountStatus)
                          .build();
    }

    private BlogPost buildBlogPost(Long id, User user, String entry, List<LikePost> likes) {
        return blogPostBuilder.withId(id)
                              .withUser(user)
                              .withEntry(entry)
                              .withLikes(likes)
                              .build();
    }

}
