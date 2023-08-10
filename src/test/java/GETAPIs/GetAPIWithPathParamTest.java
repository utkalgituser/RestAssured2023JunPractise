package GETAPIs;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static  org.hamcrest.Matchers.*;

public class GetAPIWithPathParamTest {
    // query vs. pathparam
    // <k , v> vs. <anykey , value>
    @Test
    public void getCircuitDataAPIWithQueryParamWith_YearTest(){
        baseURI = "https://ergast.com";
        given().log().all()
            .pathParam("year", "2019")
                    .when().log().all()
                        .get("/api/f1/{year}/circuits.json")
                    .then()
                        .assertThat().log().all()
                            .statusCode(200)
                        .and()
                            .contentType(ContentType.JSON)
                        .and()
                            .body("MRData.CircuitTable.season", equalTo("2019"))
                        .and()
                            .body("MRData.CircuitTable.Circuits.circuitId", hasSize(21));

        // Get the circuit size
        Response response = given().log().all()
                .pathParam("year", "2019")
                .when().log().all()
                .get("/api/f1/{year}/circuits.json");
        JsonPath js = response.jsonPath();
        List<String> circuitLst = js.getList("MRData.CircuitTable.Circuits.circuitId", String.class);
        System.out.println("Circuit size is " + circuitLst);
        System.out.println("Circuit size is " + circuitLst.size());
    }
    @DataProvider
    public Object[][] getCircuitData(){
        return new Object[][] {
                {"2019", 21},
                {"2020", 14},
                {"2018", 21},
                {"2014", 19}
        };
    }
    @Test(dataProvider = "getCircuitData")
    public void getCircuitDataAPIWithQueryParamWith_Year_DataProviderTest(String seasonYear, int circuits){
        RestAssured.baseURI = "https://ergast.com";
        given().log().all()
                .pathParam("year", seasonYear)
            .when().log().all()
                .get("/api/f1/{year}/circuits.json")
            .then()
                .assertThat().log().all()
                .statusCode(200)
                .and()
                .header("Server", "Apache/2.4.53 (AlmaLinux) OpenSSL/3.0.7")
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body("MRData.CircuitTable.season", equalTo(seasonYear))
                .and()
                .body("MRData.CircuitTable.Circuits.circuitId", hasSize(circuits));
    }
}
