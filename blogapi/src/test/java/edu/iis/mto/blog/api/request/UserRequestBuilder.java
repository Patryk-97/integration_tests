package edu.iis.mto.blog.api.request;

public class UserRequestBuilder {

    private String firstName = "";

    private String lastName = "";

    private String email = "";

    public UserRequest build() {
        return new UserRequest(firstName, lastName, email);
    }

    public UserRequestBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserRequestBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserRequestBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

}
