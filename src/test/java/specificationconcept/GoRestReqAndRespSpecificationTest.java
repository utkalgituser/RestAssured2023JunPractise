package specificationconcept;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

public class GoRestReqAndRespSpecificationTest {

    public static RequestSpecification user_request_Spec(){
        return new RequestSpecBuilder()
            .setBaseUri("https://gorest.co.in")
                .setContentType(ContentType.JSON)
                    .addHeader("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                        .build();
    }

    public static ResponseSpecification user_response_Spec_200_ok(){
        return new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                    .expectHeader("Server" , "cloudflare")
                        .build();
    }

    @Test
    public void get_user_res_200_spec_Test(){
        RestAssured.baseURI = "https://gorest.co.in";
        given().log().all()
                .spec(user_request_Spec())
            .when().log().all()
                .get("/public/v2/users")
            .then().log().all()
                .spec(user_response_Spec_200_ok());
    }
}
