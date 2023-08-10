package GETAPIs;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GETAPIRequestTest_WithoutBDD {
    RequestSpecification request;

    // NON BDD APPROACH
    @BeforeTest
    public void setup(){
        RestAssured.baseURI = "https://gorest.co.in";
        request = RestAssured.given();
        request.header("Authorization", "Bearer d260ef90bd800c34a68d70b327af6491ef23821c9c7b27b5a892016d40331a9e");
    }

    @Test
    public void getUserAPITest(){
        // Request

        // Response
        Response response = request.get("/public/v2/users");
        // status code
        int statusCode = response.statusCode();
        System.out.println("status code is " + statusCode);
        // Verification point
        Assert.assertEquals(statusCode, 200);
        // status message
        String statusMsg = response.statusLine();
        System.out.println("Response status is: \t" + statusMsg);
        // fetch the body
        response.prettyPrint();
        // fetch response headers
        System.out.println("****************");
        List<Header> headerList = response.headers().asList();
        System.out.println("header size is " + headerList.size());
        for(Header header : headerList){
            System.out.println(header.getName() +" : " + header.getValue());
        }
        System.out.println("****************");
        //fetch specific header
        System.out.println("Content type is: " + response.header("Content-Type"));
    }

    @Test
    public void getUserWithQueryParamAPITest(){

        // Adding query param
        request.queryParam("name", "malati");
        request.queryParam("status", "inactive");

        // Response
        Response response = request.get("/public/v2/users");
        // status code
        int statusCode = response.statusCode();
        System.out.println("status code is " + statusCode);
        // Verification point
        Assert.assertEquals(statusCode, 200);
        // status message
        String statusMsg = response.statusLine();
        System.out.println("Response status is: \t" + statusMsg);
        // fetch the body
        response.prettyPrint();
    }

    @Test
    public void getUserWithQuery_WithHashmap_ParamAPITest(){

        // Adding query params
        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("name" , "Tagore");
        queryParamsMap.put("status" , "inactive");

        request.queryParams(queryParamsMap);

        // Response
        Response response = request.get("/public/v2/users");
        // status code
        int statusCode = response.statusCode();
        System.out.println("status code is " + statusCode);
        // Verification point
        Assert.assertEquals(statusCode, 200);
        // status message
        String statusMsg = response.statusLine();
        System.out.println("Response status is: \t" + statusMsg);
        // fetch the body
        response.prettyPrint();
    }
}
