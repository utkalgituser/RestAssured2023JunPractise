package com.fake.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class ProductAPITest {

    @Test
    public void getProducts_With_Pojo_Test() {
        baseURI = "https://fakestoreapi.com";
        Response response  = given()
                .when().log().all()
                .get("/products");
        // json to pojo mapping -- de-serialization
        ObjectMapper mapper = new ObjectMapper();
        try {
            Product[] product = mapper.readValue(response.getBody().asString(), Product[].class);
            for(Product p : product){
                System.out.println("Id: "+ p.getId());
                System.out.println("Category: "+ p.getCategory());
                System.out.println("Rate: "+ p.getRating().getRate());
                System.out.println("________________________");
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getProducts_With_Pojo_Lombok_Test() {
        baseURI = "https://fakestoreapi.com";
        Response response  = given()
                .when().log().all()
                .get("/products");
        // json to pojo mapping -- de-serialization
        ObjectMapper mapper = new ObjectMapper();
        try {
            ProductLombok[] product = mapper.readValue(response.getBody().asString(), ProductLombok[].class);
            for(ProductLombok p : product){
                System.out.println("Id: "+ p.getId());
                System.out.println("Category: "+ p.getCategory());
                System.out.println("Rate: "+ p.getRating().getRate());
                System.out.println("________________________");
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getProducts_With_Pojo_Lombok_BuilderPattern_Test() {
        baseURI = "https://fakestoreapi.com";
        given().log().all()
                .when().log().all()
                .get("/products")
        ;
    }
}
