package specificationconcept;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

public class ResponseSpecBuilderTest {

    public static ResponseSpecification get_res_spec_200_ok (){
        return new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                    .expectHeader("Server", "cloudflare")
                        .build();
    }

    public static ResponseSpecification get_res_spec_200_ok_with_Body (){
        return new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                    .expectHeader("Server", "cloudflare")
                        .expectBody("$.size()", equalTo(10))
                            .expectBody("id", hasSize(10))
                                .build();
    }
    public static ResponseSpecification get_res_spec_401_Auth_Fail (){
        return new ResponseSpecBuilder()
            .expectStatusCode(401)
                .expectHeader("Server", "cloudflare")
                    .build();
    }

    @Test
    public void get_user_res_200_spec_Test(){
        RestAssured.baseURI = "https://gorest.co.in";
        given().log().all()
            .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .when().log().all()
                    .get("/public/v2/users")
                .then()
                    .spec(get_res_spec_200_ok_with_Body());
    }

    @Test
    public void get_user_res_401_Auth_Fail_spec_Test(){
        RestAssured.baseURI = "https://gorest.co.in";
        given().log().all()
                // wrong token
            .header("Authorization", "Bearer 260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .when().log().all()
                    .get("/public/v2/users")
                         .then()
                            .spec(get_res_spec_401_Auth_Fail());
    }
}
