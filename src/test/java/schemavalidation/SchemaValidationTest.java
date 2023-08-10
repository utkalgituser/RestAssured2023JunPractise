package schemavalidation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import  static io.restassured.module.jsv.JsonSchemaValidator.*;

import java.io.File;

public class SchemaValidationTest {

    @Test
    public void createUserAPISchemaValidationTest(){

        RestAssured.baseURI = "https://gorest.co.in/";
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new File("./src/test/resources/data/adduser.json"))
                .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .when()
                .post("/public/v2/users/")
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .and()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("createUserSchema.json"));
    }
    @Test
    public void getUserAPISchemaValidationTest(){
        RestAssured.baseURI = "https://gorest.co.in/";
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .when()
                .get("/public/v2/users/")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("getuserschema.json"));
    }
}
