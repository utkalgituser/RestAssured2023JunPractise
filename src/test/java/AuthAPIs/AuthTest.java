package AuthAPIs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AuthTest {

    @BeforeTest
    public void allureSetup(){
        RestAssured.filters(new AllureRestAssured());
    }
    @Test
    public void jwtAuthWithJsonBodyTest(){
        RestAssured.baseURI = "https://fakestoreapi.com";
        String jwtTokenId = RestAssured.given()
                .given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"username\": \"mor_2314\",\n" +
                        "    \"password\": \"83r5^_\"\n" +
                        "}")
                .when()
                .post("/auth/login")
                .then()
                .assertThat().statusCode(200)
                .and()
                .extract().path("token");
        System.out.println(jwtTokenId);
        String[] payLoad = jwtTokenId.split("\\.");
        System.out.println(payLoad[0]);
        System.out.println(payLoad[1]);
        System.out.println(payLoad[2]);
    }

    @Test
    public void basicAuthTest(){
        RestAssured.baseURI = "https://the-internet.herokuapp.com";
        String responseBody = RestAssured.given().log().all()
                .auth().basic("admin", "admin")
                .when()
                .get("/basic_auth")
                .then().log().all()
                .assertThat().statusCode(200)
                .and()
                .extract().body().asString();
        System.out.println(responseBody);
    }

    // preemptive is better than basic auth
    @Test
    public void preemptiveAuthTest(){
        RestAssured.baseURI = "https://the-internet.herokuapp.com";
        String responseBody = RestAssured.given().log().all()
                .auth().preemptive().basic("admin", "admin")
                .when()
                .get("/basic_auth")
                .then().log().all()
                .assertThat().statusCode(200)
                .and()
                .extract().body().asString();
        System.out.println(responseBody);
    }

    // digestAuth is better than preemptive auth
    @Test
    public void digestAuthTest(){
        RestAssured.baseURI = "https://the-internet.herokuapp.com";
        String responseBody = RestAssured.given().log().all()
                .auth().digest("admin", "admin")
                .when()
                .get("/basic_auth")
                .then().log().all()
                .assertThat().statusCode(200)
                .and()
                .extract().body().asString();
        System.out.println(responseBody);
    }

    @Test
    public void apiKeyAuthTest(){
        RestAssured.baseURI = "http://api.weatherapi.com";
        String responseBody = RestAssured.given().log().all()
                .queryParams("key", "0482eae562e64392b6975635232306")
                .queryParams("q", "London")
             .queryParams("aqi", "no")
                .when()
                .get("/v1/current.json")
                .then().log().all()
                .assertThat().statusCode(200)
                .and()
                .extract().body().asString();
        System.out.println(responseBody);
    }

}
