package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

public class AddPostByUserTest extends FunctionalTests {

    private static final String USER_API = "/blog/user";

    @Test
    public void createPostByConfirmedUserReturnsCreatedStatus() {
        final Long ID_OF_USER_WITH_CONFIRMED_ACCOUNT_STATUS = 1L;
        JSONObject jsonObj = new JSONObject().put("entry", "testEntry1");
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .body(jsonObj.toString())
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_CREATED)
               .when()
               .post(USER_API + "/" + String.valueOf(ID_OF_USER_WITH_CONFIRMED_ACCOUNT_STATUS) + "/post");
    }

    @Test
    public void createPostByNotConfirmedUserNotReturnsCreatedStatus() {
        final Long ID_OF_USER_WITH_NOT_CONFIRMED_ACCOUNT_STATUS = 2L;
        JSONObject jsonObj = new JSONObject().put("entry", "testEntry2");
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .body(jsonObj.toString())
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_BAD_REQUEST)
               .when()
               .post(USER_API + "/" + String.valueOf(ID_OF_USER_WITH_NOT_CONFIRMED_ACCOUNT_STATUS) + "/post");
    }
}
