package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

class FindBlogPostTest extends FunctionalTests {

    private static final String USER_API = "/blog/user";

    private static final Long ID_OF_USER_WITH_CONFIRMED_ACCOUNT_STATUS = 1L;

    private static final Long ID_OF_USER_WITH_REMOVED_ACCOUNT_STATUS = 5L;

    @Test
    public void findBlogPostsOfUserWithConfirmedAccountStatusShouldReturnHttpResponseWithOkStatus() {
        given().accept(ContentType.JSON)
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(USER_API + "/" + ID_OF_USER_WITH_CONFIRMED_ACCOUNT_STATUS + "/post");
    }

    @Test
    public void findBlogPostsOfUserWithRemovedAccountStatusShouldReturnHttpResponseWithBadRequestStatus() {
        given().accept(ContentType.JSON)
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .get(USER_API + "/" + ID_OF_USER_WITH_REMOVED_ACCOUNT_STATUS + "/post");
    }

    @Test
    public void findBlogPostsOfUserWithConfirmedAccountStatusShouldReturnProperLikesCountSize() {
        given().accept(ContentType.JSON)
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .get(USER_API + "/" + ID_OF_USER_WITH_CONFIRMED_ACCOUNT_STATUS + "/post")
               .then()
               .body("likesCount", hasSize(2));
    }

}
