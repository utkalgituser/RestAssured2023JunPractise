package POSTAPIs;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Oauth2Test {
    public static String access_token;
    @BeforeMethod
    public void getAccessToken(){
        RestAssured.baseURI = "https://test.api.amadeus.com";
        access_token = given()
                // .header("Content-Type", "application/x-www-form-urlencoded")
                .contentType(ContentType.URLENC)
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", "2gN3FK8VR1nY2b5fJqabKuFq0NJZGGbC")
                .formParam("client_secret", "9eHK6DQgX8ecuXdR")
                .when().log().all()
                .post("/v1/security/oauth2/token")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("access_token");
        System.out.println("access_token is " + access_token);
    }
    @Test
    public void getFlightInfoTest(){
        RestAssured.baseURI = "https://test.api.amadeus.com";

        // Get flight info : GET
        Response flightDataResponse = given().log().all()
                .header("Authorization", "Bearer " + access_token)
                .queryParam("origin", "PAR")
                .queryParam("maxPrice", 200)
                .when().log().all()
                .get("/v1/shopping/flight-destinations")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .extract().response();
        JsonPath js = flightDataResponse.jsonPath();
        String type = js.get("data[0].type");
        System.out.println("type is " + type);
    }

}
