package specificationconcept;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class RequestBuilderTest {

    public static RequestSpecification user_req_spec(){
        return new RequestSpecBuilder()
                .setBaseUri("https://gorest.co.in")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .build();
    }
    @Test
    public void getUser_With_Request_Spec(){

        RestAssured.given().log().all()
                .spec(user_req_spec())
                    .get("/public/v2/users")
                        .then()
                             .statusCode(200);
    }
    @Test
    public void getUser_With_Param_Request_Spec(){

        RestAssured.given().log().all()
                .queryParams("name", "Tagore")
                .queryParams("status", "active")
                    .spec(user_req_spec())
                        .get("/public/v2/users")
                            .then()
                                .statusCode(200);
    }
}
