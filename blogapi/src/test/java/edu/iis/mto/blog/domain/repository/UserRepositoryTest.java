package edu.iis.mto.blog.domain.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.model.UserBuilder;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    private User userJanWithDomainAccount;

    private User userJanuszWithGmailAccount;

    private User userPiotrKowalskiWithDomainAccount;

    private User userMiloszKowalskiWithGmailAccount;

    private static final String invalidFirstName = "invalidFirstName";

    private static final String invalidLastName = "invalidLastName";

    private static final String invalidEmail = "invalidEmail@invalidEmail.com";

    private UserBuilder userBuilder;

    @Before
    public void setUp() {
        userBuilder = new UserBuilder();
        userJanWithDomainAccount = userBuilder.withFirstName("Jan")
                                              .withLastName("")
                                              .withEmail("john@domain.com")
                                              .withAccountStatus(AccountStatus.NEW)
                                              .build();
        userJanuszWithGmailAccount = userBuilder.withFirstName("Janusz")
                                                .withLastName("")
                                                .withEmail("januszex@gmail.com")
                                                .withAccountStatus(AccountStatus.NEW)
                                                .build();
        userPiotrKowalskiWithDomainAccount = userBuilder.withFirstName("Piotr")
                                                        .withLastName("Kowalski")
                                                        .withEmail("piter@domain.com")
                                                        .withAccountStatus(AccountStatus.NEW)
                                                        .build();
        userMiloszKowalskiWithGmailAccount = userBuilder.withFirstName("Milosz")
                                                        .withLastName("Kowalski")
                                                        .withEmail("mkowalski@gmail.com")
                                                        .withAccountStatus(AccountStatus.NEW)
                                                        .build();
    }

    @Test
    public void shouldFindNoUsersIfRepositoryIsEmpty() {

        List<User> users = repository.findAll();

        assertThat(users, hasSize(0));
    }

    @Test
    public void shouldFindOneUsersIfRepositoryContainsOneUserEntity() {
        User persistedUser = entityManager.persist(userJanWithDomainAccount);
        List<User> users = repository.findAll();

        assertThat(users, hasSize(1));
        assertThat(users.get(0)
                        .getEmail(),
                equalTo(persistedUser.getEmail()));
    }

    @Test
    public void shouldStoreANewUser() {

        User persistedUser = repository.save(userJanWithDomainAccount);

        assertThat(persistedUser.getId(), notNullValue());
    }

    @Test
    public void shouldFindUsersWithFirstNameBeginsForJanPhrase() {
        repository.save(userJanWithDomainAccount);
        repository.save(userJanuszWithGmailAccount);
        repository.save(userPiotrKowalskiWithDomainAccount);
        repository.save(userMiloszKowalskiWithGmailAccount);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("Jan", invalidLastName,
                invalidEmail);

        assertThat(users, hasSize(2));

        assertThat(users.get(0)
                        .getFirstName(),
                equalTo(userJanWithDomainAccount.getFirstName()));

        assertThat(users.get(0)
                        .getLastName(),
                equalTo(userJanWithDomainAccount.getLastName()));

        assertThat(users.get(0)
                        .getEmail(),
                equalTo(userJanWithDomainAccount.getEmail()));

        assertThat(users.get(1)
                        .getFirstName(),
                equalTo(userJanuszWithGmailAccount.getFirstName()));

        assertThat(users.get(1)
                        .getLastName(),
                equalTo(userJanuszWithGmailAccount.getLastName()));

        assertThat(users.get(1)
                        .getEmail(),
                equalTo(userJanuszWithGmailAccount.getEmail()));

    }

    @Test
    public void shouldFindUsersWithLastNameEqualToKowalski() {
        repository.save(userJanWithDomainAccount);
        repository.save(userJanuszWithGmailAccount);
        repository.save(userPiotrKowalskiWithDomainAccount);
        repository.save(userMiloszKowalskiWithGmailAccount);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(invalidFirstName,
                "Kowalski", invalidEmail);

        assertThat(users, hasSize(2));

        assertThat(users.get(0)
                        .getFirstName(),
                equalTo(userPiotrKowalskiWithDomainAccount.getFirstName()));

        assertThat(users.get(0)
                        .getLastName(),
                equalTo(userPiotrKowalskiWithDomainAccount.getLastName()));

        assertThat(users.get(0)
                        .getEmail(),
                equalTo(userPiotrKowalskiWithDomainAccount.getEmail()));

        assertThat(users.get(1)
                        .getFirstName(),
                equalTo(userMiloszKowalskiWithGmailAccount.getFirstName()));

        assertThat(users.get(1)
                        .getLastName(),
                equalTo(userMiloszKowalskiWithGmailAccount.getLastName()));

        assertThat(users.get(1)
                        .getEmail(),
                equalTo(userMiloszKowalskiWithGmailAccount.getEmail()));

    }

    @Test
    public void shouldFindUsersWithGmailAccount() {
        repository.save(userJanWithDomainAccount);
        repository.save(userJanuszWithGmailAccount);
        repository.save(userPiotrKowalskiWithDomainAccount);
        repository.save(userMiloszKowalskiWithGmailAccount);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(invalidFirstName,
                invalidLastName, "gmail.com");

        assertThat(users, hasSize(2));

        assertThat(users.get(0)
                        .getFirstName(),
                equalTo(userJanuszWithGmailAccount.getFirstName()));

        assertThat(users.get(0)
                        .getLastName(),
                equalTo(userJanuszWithGmailAccount.getLastName()));

        assertThat(users.get(0)
                        .getEmail(),
                equalTo(userJanuszWithGmailAccount.getEmail()));

        assertThat(users.get(1)
                        .getFirstName(),
                equalTo(userMiloszKowalskiWithGmailAccount.getFirstName()));

        assertThat(users.get(1)
                        .getLastName(),
                equalTo(userMiloszKowalskiWithGmailAccount.getLastName()));

        assertThat(users.get(1)
                        .getEmail(),
                equalTo(userMiloszKowalskiWithGmailAccount.getEmail()));

    }

    @Test
    public void shouldNotFindNotExistingUser() {
        repository.save(userJanWithDomainAccount);
        repository.save(userJanuszWithGmailAccount);
        repository.save(userPiotrKowalskiWithDomainAccount);
        repository.save(userMiloszKowalskiWithGmailAccount);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(invalidFirstName,
                invalidLastName, invalidEmail);

        assertThat(users, hasSize(0));

    }

    @Test
    public void shouldFindEachUser() {
        repository.save(userJanWithDomainAccount);
        repository.save(userJanuszWithGmailAccount);
        repository.save(userPiotrKowalskiWithDomainAccount);
        repository.save(userMiloszKowalskiWithGmailAccount);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("", "", "");

        assertThat(users, hasSize(4));

    }

    @Test
    public void shouldFindUserIgnoringLetterCase() {
        repository.save(userJanWithDomainAccount);
        repository.save(userJanuszWithGmailAccount);
        repository.save(userPiotrKowalskiWithDomainAccount);
        repository.save(userMiloszKowalskiWithGmailAccount);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase("aN", invalidLastName,
                invalidEmail);

        assertThat(users.get(0)
                        .getFirstName(),
                equalTo(userJanWithDomainAccount.getFirstName()));

        assertThat(users.get(0)
                        .getLastName(),
                equalTo(userJanWithDomainAccount.getLastName()));

        assertThat(users.get(0)
                        .getEmail(),
                equalTo(userJanWithDomainAccount.getEmail()));

    }

}
