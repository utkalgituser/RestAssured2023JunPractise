package XMLAPIs;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

public class GetCircuitXMLAPITest {

    // collect all the country, locality and locations for diff locations
    @Test
    public void xmlTest() {
        RestAssured.baseURI = "https://ergast.com";
        Response response = RestAssured.given()
                .when()
                .get("/api/f1/2019/circuits.xml")
                .then()
                .extract().response();
        String responseBody = response.body().asString();
        System.out.println(responseBody);

        // Create object of XmlPath
        XmlPath xmlPath = new XmlPath(responseBody);
        System.out.println("----------------------");
        List<String> circuitsName = xmlPath.getList("MRData.CircuitTable.Circuit.CircuitName");
        circuitsName.forEach(System.out::println);
        System.out.println("----------------------");
        List<String> circuitsIds = xmlPath.getList("MRData.CircuitTable.Circuit.@circuitId");
        circuitsIds.forEach(System.out::println);
        System.out.println("----------------------");
        // fetch the locality where circuitId = americas
        String location = xmlPath.get("**.findAll { it.@circuitId == 'americas' }.Location.Locality").toString();
        System.out.println("Location is " + location);
        System.out.println("----------------------");
        String latitude = xmlPath.get("**.findAll { it.@circuitId == 'americas' }.Location.@lat").toString();
        System.out.println("latitude is " + latitude);
        String longitude = xmlPath.get("**.findAll { it.@circuitId == 'americas' }.Location.@long").toString();
        System.out.println("longitude is " + longitude);
        System.out.println("----------------------");
        // fetch the Location where circuitId = americas or, circuitId = bahrain
        String location1 = xmlPath.get("**.findAll { it.@circuitId == 'americas' || it.@circuitId == 'bahrain' }.Location.Locality").toString();
        System.out.println("Location is " + location1);
        System.out.println("----------------------");
        // fetch the circuitName where circuitId = americas
        String circuitName = xmlPath.get("**.findAll { it.@circuitId == 'americas' }.CircuitName").toString();
        System.out.println("circuitName is " + circuitName);
        System.out.println("----------------------");
        // fetch the url where circuitId = americas
        String url = xmlPath.get("**.findAll { it.@circuitId == 'americas' }.@url").toString();
        System.out.println("url is " + url);
        System.out.println("----------------------");
        // fetch all the url
        List<String> urlList = xmlPath.getList("MRData.CircuitTable.Circuit.@url");
        urlList.forEach(System.out::println);
        System.out.println("----------------------");
        // fetch all the countries
        List<String> countries = xmlPath.getList("MRData.CircuitTable.Circuit.Location.Country");
        countries.forEach(System.out::println);
        System.out.println("----------------------");
        // fetch the url where Country = USA (NOT WORKING)
        String urls = xmlPath.get("**.findAll { it.Circuit.Location.Country == 'USA' }.@url").toString();
        System.out.println("urls are " + urls);
        System.out.println("----------------------");
    }
}
