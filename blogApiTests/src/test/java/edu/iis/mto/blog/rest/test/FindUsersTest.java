package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

class FindUsersTest extends FunctionalTests {

    private static final String USER_API = "/blog/user";

    private static final Long ID_OF_USER_WITH_REMOVED_ACCOUNT_STATUS = 5L;

    @Test
    public void findUsersShouldReturnListWithoutUsersWithRemovedAccountStatus() {
        given().accept(ContentType.JSON)
               .header("Content-Type", "application/json;charset=UTF-8")
               .expect()
               .log()
               .all()
               .statusCode(HttpStatus.SC_OK)
               .and()
               .body("id", not(hasItem(ID_OF_USER_WITH_REMOVED_ACCOUNT_STATUS)))
               .when()
               .get(USER_API + "/find?searchString=");
    }

}
