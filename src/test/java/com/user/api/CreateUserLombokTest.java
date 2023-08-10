package com.user.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateUserLombokTest {

    public static String getRandomEmailId(){
        // By using currentTimeMillis()
        return "random-email" + System.currentTimeMillis() + "@eymail.com";
        //By using randomUUID()
        // return "random-email-" + UUID.randomUUID() + "@eymail.com";
    }
    @Test
    public void createUserTest(){
        RestAssured.baseURI = "https://gorest.co.in";
        User user = new User("utkal234t" + System.currentTimeMillis(), getRandomEmailId(), "male" , "active");
        Response response  = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .body(user)
                .when().log().all()
                .post("/public/v2/users");
        Integer userId = response.jsonPath().get("id");
        System.out.println("user id is: " + userId);

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

    @Test
    public void createUserBuilderPatternTest(){
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
