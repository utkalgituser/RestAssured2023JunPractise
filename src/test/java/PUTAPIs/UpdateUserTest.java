package PUTAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UpdateUserTest {

    public static @NotNull String getRandomEmailId(){
        // By using currentTimeMillis()
        return "random-email-" + System.currentTimeMillis() + "@eymail.com";
        //By using randomUUID()
        // return "random-email-" + UUID.randomUUID() + "@eymail.com";
    }

    // Create user --> POST --> userId
    // update user --> PUT --> /userId
    @Test
    public void updateUser_BuilderPatternsTest(){
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
            response.prettyPrint();
            System.out.println("--------------------------");

        // Update existing user
        user.setName("utkal_east_" + System.currentTimeMillis());
        user.setStatus("inactive");
        RestAssured.given()
            .contentType(ContentType.JSON)
                .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .body(user)
            .when().log().all()
                .put("/public/v2/users/" + userId) //patch will also work
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and().assertThat()
                .body("id" , equalTo(userId))
                .and()
                .body("name" , equalTo(user.getName()))
                .and()
                .body("status" , equalTo(user.getStatus()));
    }

    @Test
    public void updateUserTest(){
        RestAssured.baseURI = "https://gorest.co.in";
        User user = new User.UserBuilder()
                .name("alok" + System.currentTimeMillis())
                .email(getRandomEmailId())
                .gender("male")
                .status("active")
                .build();
        Response response  = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .body(user)
                .when().log().all()
                .post("/public/v2/users");
        Integer userId = response.jsonPath().get("id");
        System.out.println("user id is: " + userId);

        user.setName("utkal_east_" + System.currentTimeMillis());
        user.setStatus("inactive");
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .body(user)
                .when().log().all()
                .put("/public/v2/users/" + userId); //patch will also work
        // GET API to get same user
        Response getResponse  = given().log().all()
                .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .when()
                .get("/public/v2/users/" + userId);

        // De-serialization
        ObjectMapper mapper = new ObjectMapper();
        try {
            User userRes = mapper.readValue(getResponse.getBody().asString(), User.class);
            System.out.println(userRes.getId() + " , "+userRes.getGender() + " , "+userRes.getStatus() + " , "+userRes.getEmail() + " , ");
            Assert.assertEquals(userId, userRes.getId());
            Assert.assertEquals(user.getName(), userRes.getName());
            Assert.assertEquals(user.getGender(), userRes.getGender());
            Assert.assertEquals(user.getStatus(), userRes.getStatus());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
