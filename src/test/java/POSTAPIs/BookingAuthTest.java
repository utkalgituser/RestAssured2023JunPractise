package POSTAPIs;

import static io.restassured.RestAssured.*;
import static  org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class BookingAuthTest {
    @Test
    public void getBookingAuthToken_With_JSON_String_Test(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String tokenId = given().log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
                .when().log().all()
                    .post("/auth")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .extract()
                    .path("token");
        System.out.println(tokenId);
        Assert.assertNotNull(tokenId);
    }

    @Test
    public void getBookingAuthToken_With_JSON_File_Test(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String tokenId = given().log().all()
                .contentType(ContentType.JSON)
                    .body(new File("./src/test/resources/data/basicauth.json"))
                .when().log().all()
                    .post("/auth")
                .then()
                    .assertThat()
                        .statusCode(200)
                    .extract()
                        .path("token");
        System.out.println(tokenId);
        Assert.assertNotNull(tokenId);
    }

    // POST - add a user -> user id = 123 -> assert(201, body)
    // GET - get the user -> /users/123 -> status code 200 - user id = 123

    @Test
    public void addUserTest(){
        baseURI = "https://gorest.co.in";
        int userId = given().log().all()
            .contentType(ContentType.JSON)
                .body(new File("./src/test/resources/data/adduser.json"))
                    .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
            .when()
                    .post("/public/v2/users")
            .then().log().all()
                    .assertThat()
                    .statusCode(201)
            .and()
                    .body("name", equalTo("utkal66"))
            .extract()
                    .path("id");
        System.out.println("user id is: "+ userId);

        // Get same user id and verify it
        given()
            .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
            .when().log().all()
                    .get("/public/v2/users/" + userId)
            .then().log().all()
                .assertThat()
                    .statusCode(200)
                .and()
                    .body("id", equalTo(userId));
    }
}
