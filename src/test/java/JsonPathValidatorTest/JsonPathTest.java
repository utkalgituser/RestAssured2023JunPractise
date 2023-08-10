package JsonPathValidatorTest;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class JsonPathTest {

    @Test
    public void getCircuitDataAPIWithQueryParamWith_YearTest() {
        baseURI = "https://ergast.com";
        Response response = given()
                .when()
                .get("/api/f1/2019/circuits.json");
        String jsonResponse = response.asString();
        System.out.println("+++++++++++++++++++++");
        System.out.println(jsonResponse);
        List<String> countryList = JsonPath.read(jsonResponse, "$..Circuits..country");
        System.out.println("+++++++++++++++++++++");
        System.out.println("Countries: "+countryList);
        int totalNumberOfCircuits = JsonPath.read(jsonResponse, "$..MRData.CircuitTable.Circuits.length()");
        System.out.println("totalNumberOfCircuits " + totalNumberOfCircuits);
    }

    @Test
    public void getProductsTest(){
        Response response = given()
                .when().log().all()
                .get("https://fakestoreapi.com/products");
        String jsonResponse = response.asString();
        System.out.println(jsonResponse);
        List<Float> rateList = JsonPath.read(jsonResponse, "$[?(@.rating.rate < 3)].rating.rate");
        System.out.println("Rate List: " + rateList);
        // With three attributes
        List<Map<String, Object>> jeweleryList = JsonPath.read(jsonResponse, "$[?(@.category == 'jewelery')].[\"title\",\"price\", \"id\"]");
        System.out.println(jeweleryList);
        for(Map<String, Object> m : jeweleryList){
            String title  = String.valueOf(m.get("title"));
            String price  = String.valueOf(m.get("price"));
            Integer id  = (Integer) m.get("id");
            System.out.println("title:  " + title);
            System.out.println("price:  " + price);
            System.out.println("id:  " + id);
        }
    }
}
