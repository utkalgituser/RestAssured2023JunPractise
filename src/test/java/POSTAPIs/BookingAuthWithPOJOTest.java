package POSTAPIs;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.Credentials;

import static io.restassured.RestAssured.given;

public class BookingAuthWithPOJOTest {
    //POJO -- Plain Old Java Object
    // POJO class will not extend any other class
    // oop -- Encapsulation
    // private class variables -- json body
    // public getters/setters

    @Test
    public void getBookingAuthToken_With_JSON_String_Test(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        Credentials creds = new Credentials("admin", "password123");
        String tokenId = given().log().all()
                .contentType(ContentType.JSON)
                    .body(creds)
                .when().log().all()
                    .post("/auth")
                .then().log().all()
                    .assertThat()
                    .statusCode(200)
                .extract()
                .path("token");
        System.out.println(tokenId);
        Assert.assertNotNull(tokenId);

        // from response json -- username and compare with getter

    }
}
