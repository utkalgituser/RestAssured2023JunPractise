package DeleteAPIs;

import PUTAPIs.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;

public class DeleteUserTest {

    public static @NotNull String getRandomEmailId(){
        // By using currentTimeMillis()
        return "random-email-" + System.currentTimeMillis() + "@eymail.com";
        //By using randomUUID()
        // return "random-email-" + UUID.randomUUID() + "@eymail.com";
    }

    // Create user --> POST --> userId --> 201
    // delete user --> DELETE --> /userId --> 204
    // get user --> get user --> /userId --> 404
    @Test
    public void deleteUser_BuilderPatternsTest(){

        RestAssured.baseURI = "https://gorest.co.in";
        User user = new User("utkal234_" + System.currentTimeMillis(), getRandomEmailId(), "male" , "active");
        // POST call - create user
        Response response  = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .body(user)
                .when().log().all()
                .post("/public/v2/users");
        Integer userId = response.jsonPath().get("id");
        System.out.println("user id is: " + userId);
        System.out.println("--------------------------");

        // Delete existing user
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
            .when()
                .delete("/public/v2/users/" + userId)
                .then().log().all()
                .assertThat()
                .statusCode(204);
        System.out.println("--------------------------");
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .when().log().all()
                .get("/public/v2/users/" + userId)
                .then()
                .assertThat()
                .statusCode(404)
                .and()
                .assertThat()
                .body("message" , equalTo("Resource not found"));
    }
}
