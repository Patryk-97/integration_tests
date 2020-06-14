package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

class LikePostTest extends FunctionalTests {

    private static final String USER_API = "/blog/user";

    private static final Long ID_OF_USER_WITH_CONFIRMED_ACCOUNT_STATUS = 1L;

    private static final Long ID_OF_USER_WITH_NOT_CONFIRMED_ACCOUNT_STATUS = 2L;

    private static final Long ID_OF_POST_BLOG_OF_FIRST_USER = 1L;

    private static final Long ID_OF_POST_BLOG_OF_SECOND_USER = 2L;

    private static final Long ID_OF_POST_BLOG_WHICH_IS_LIKED_TWICED_BY_FIRST_USER = 3L;

    @Test
    public void likePostByUserWithConfirmedAccountStatusShouldReturnOkStatus() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .post(getPostPath(ID_OF_USER_WITH_CONFIRMED_ACCOUNT_STATUS, ID_OF_POST_BLOG_OF_SECOND_USER));
    }

    @Test
    public void likePostByUserWithNewAccountStatusShouldReturnBadRequestStatus() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(getPostPath(ID_OF_USER_WITH_NOT_CONFIRMED_ACCOUNT_STATUS, ID_OF_POST_BLOG_OF_FIRST_USER));
    }

    @Test
    public void likePostByUserWhoIsOwnerOfThatPostShouldReturnBadRequestStatus() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(getPostPath(ID_OF_USER_WITH_CONFIRMED_ACCOUNT_STATUS, ID_OF_POST_BLOG_OF_FIRST_USER));
    }

    @Test
    public void repeatLikePostByTheSameUserShouldIncreasePostLikeCountOnlyOnce() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .post(getPostPath(ID_OF_USER_WITH_CONFIRMED_ACCOUNT_STATUS, ID_OF_POST_BLOG_WHICH_IS_LIKED_TWICED_BY_FIRST_USER));

        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .when()
               .post(getPostPath(ID_OF_USER_WITH_CONFIRMED_ACCOUNT_STATUS, ID_OF_POST_BLOG_WHICH_IS_LIKED_TWICED_BY_FIRST_USER));

        given().accept(ContentType.JSON)
               .when()
               .get("/blog/post/" + String.valueOf(ID_OF_POST_BLOG_WHICH_IS_LIKED_TWICED_BY_FIRST_USER))
               .then()
               .body("likesCount", is(1));
    }

    private String getPostPath(Long userId, Long postBlogId) {
        return USER_API + "/" + String.valueOf(userId) + "/like/" + String.valueOf(postBlogId);
    }
}
