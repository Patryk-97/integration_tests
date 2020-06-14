package edu.iis.mto.blog.domain.model;

public class UserBuilder {

    private Long id = null;

    private String firstName = "";

    private String lastName = "";

    private String email = "";

    private AccountStatus accountStatus = null;

    public User build() {
        User user = new User();
        if (id != null) {
            user.setId(id);
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAccountStatus(accountStatus);
        return user;
    }

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
        return this;
    }
}
