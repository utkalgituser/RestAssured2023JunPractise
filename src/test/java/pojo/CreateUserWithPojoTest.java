package pojo;

import io.restassured.http.ContentType;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;
import pojo.User.User;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserWithPojoTest {
    //1. by using json String in body
    //2. By using json file
    // 3. By using pojo - java object - with the help of jackson data bind
    @NotNull
    public static String getRandomEmailId(){
        // By using currentTimeMillis()
        return "random-email" + System.currentTimeMillis() + "@eymail.com";
        //By using randomUUID()
        // return "random-email-" + UUID.randomUUID() + "@eymail.com";
    }

    User user = new User("alok13", getRandomEmailId(), "male", "active");
    @Test
    public void addUserTest(){
        baseURI = "https://gorest.co.in";
        int userId = given().log().all()
                .contentType(ContentType.JSON)
                .body(user)
                    .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .when()
                    .post("/public/v2/users")
                .then().log().all()
                    .assertThat()
                    .statusCode(201)
                    .and()
                    .body("name", equalTo(user.getName()))
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
                            .body("id", equalTo(userId))
                            .and()
                            .body("name", equalTo(user.getName()))
                            .and()
                            .body("status", equalTo(user.getStatus()));
    }

}
