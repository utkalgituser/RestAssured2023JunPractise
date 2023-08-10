package GETAPIs;

import static io.restassured.RestAssured.*;
import static  org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GETAPIWithBDDTest {
    @Test
    public void getProductsTest(){
        given()
            .when().log().all()
                .get("https://fakestoreapi.com/products")
            .then().log().all()
                .assertThat()
                    .statusCode(200)
                        .and()
                            .contentType(ContentType.JSON)
                                .and()
                                    .header("Connection", "keep-alive")
                                        .and()
                                            .body("$.size()", equalTo(20))
                                                .and()
                                                    .body("id", is(notNullValue()))
                                                        .and()
                                                            .body("title", hasItem("Mens Cotton Jacket"));
    }

    @Test
    public void getUserAPITest(){
        RestAssured.baseURI = "https://gorest.co.in";
        given().log().all()
            .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .when()
                    .get("/public/v2/users")
                        .then()
                            .assertThat().log().all()
                                .statusCode(200)
                                    .contentType(ContentType.JSON)
                                        .and()
                                            .body("$.size()", equalTo(10));

    }

    @Test
    public void getProductDataAPIWithQueryParamTest(){
        RestAssured.baseURI = "https://fakestoreapi.com";
        given().log().all()
                .queryParam("limit", 5)
                    .when()
                        .get("/products")
                            .then()
                                .assertThat().log().all()
                                    .statusCode(200)
                                        .contentType(ContentType.JSON);

    }

    @Test
    public void getUserWithQuery_ParamAPITest(){
        RestAssured.baseURI = "https://gorest.co.in";
        given().log().all()
            .queryParam("name" , "Tagore")
                    .queryParam("status" , "inactive")
                        .when()
                            .get("/public/v2/users")
                                .then()
                                    .assertThat().log().all()
                                        .statusCode(200)
                                            .contentType(ContentType.JSON);

    }
    @Test
    public void getUserWithQuery_WithHashmap_ParamAPITest(){
        RestAssured.baseURI = "https://gorest.co.in";
        Map<String, String> queryPramMap= new HashMap<>();
        queryPramMap.put("name" , "Tagore");
        queryPramMap.put("status" , "inactive");
        given().log().all()
            .queryParams(queryPramMap)
                .when()
                    .get("/public/v2/users")
                        .then()
                            .assertThat().log().all()
                                .statusCode(200)
                                    .contentType(ContentType.JSON);

    }

    @Test
    public void getProductDataAPIWithExtractBodyTest(){
        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = given().log().all()
                .queryParam("limit", 5)
                    .when().log().all()
                        .get("/products");
        JsonPath js = response.jsonPath();
        // get the id of the first product
        int firstProductId = js.getInt("[0].id");
        System.out.println("first Product Id = " + firstProductId);
        String firstProductTitle = js.getString("[0].title");
        System.out.println("first Product title = " + firstProductTitle);
        float firstProductPrice = js.getFloat("[0].price");
        System.out.println("first Product Price = " + firstProductPrice);
        int firstProductCount = js.getInt("[0].rating.count");
        System.out.println("first Product count = " + firstProductCount);
    }

    @Test
    public void getProductDataAPIWithExtractBodyWithJSONArrayTest(){
        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = given().log().all()
                .queryParam("limit", 15)
                .when().log().all()
                .get("/products");
        JsonPath js = response.jsonPath();
        List<Integer> idList = js.getList("id", Integer.class);
        List<String> titleList = js.getList("title");
        // List<Object> rateList = js.getList("rating.rate");
        List<Float> rateList = js.getList("rating.rate", Float.class);
        List<String> imageList = js.getList("image");
        List<Integer> countList = js.getList("rating.count", Integer.class);
        System.out.println(countList);
        for(int i=0; i< idList.size() ; i++){
            int id = idList.get(i);
            String title = titleList.get(i);
            // Object rate = rateList.get(i);
            Float rate = rateList.get(i);
            String image = imageList.get(i);
            int count = countList.get(i);
            System.out.println(id + " , " + title + " , " + rate + " , " + image +" , " +count);
        }
    }

    @Test
    public void getUserAPIWithExtractBodyWithJSONTest(){
        RestAssured.baseURI = "https://gorest.co.in";
        Response response = given().log().all()
            .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .when().log().all()
                    .get("/public/v2/users/3612157");
        JsonPath js = response.jsonPath();
        System.out.println(js.getInt("id"));
        System.out.println(js.getString("email"));
    }

    @Test
    public void getUserAPIWithExtractBodyWithJSON_ExtractTest(){
        // WIth extract() only one value can be fetched
        RestAssured.baseURI = "https://gorest.co.in";

/*        int id = given().log().all()
                .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                .when().log().all()
                .get("/public/v2/users/3612157")
                .then()
                .extract().path("id");*/

        Response response = given().log().all()
                .header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e")
                    .when().log().all()
                        .get("/public/v2/users/3612157")
                            .then()
                                .extract()
                                    .response();
        String email = response.path("email");
        String gender = response.path("gender");
        System.out.println(email + " , " +gender);
    }
}


