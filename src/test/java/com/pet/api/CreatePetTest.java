package com.pet.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class CreatePetTest {

    @Test
    public void createPetTest(){
        RestAssured.baseURI = "https://petstore.swagger.io";
        PetLombok.Category  category = new PetLombok.Category(11, "Dog");
        List<String> photoUrls = Arrays.asList("http://www.dog.com", "http://www.dog2.com");
        PetLombok.Tag tag1 = new PetLombok.Tag(5, "red");
        PetLombok.Tag tag2 = new PetLombok.Tag(7, "black");
        List<PetLombok.Tag> tags = Arrays.asList(tag1, tag2);
        PetLombok pet = new PetLombok(12, category , "golden retriever", photoUrls , tags, "available");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post("/v2/pet");
        System.out.println(response.getStatusCode());
        response.prettyPrint();

        // De-serializer
        ObjectMapper mapper = new ObjectMapper();
        try {
            PetLombok petResponse = mapper.readValue(response.getBody().asString(), PetLombok.class);
            System.out.println("Id is: " + petResponse.getId());
            System.out.println("getName is: " + petResponse.getName());
            System.out.println("getStatus is: " + petResponse.getStatus());

            System.out.println("getCategory is: " + petResponse.getCategory().getId());
            System.out.println("getCategory is: " + petResponse.getCategory().getName());

            System.out.println("getPhotoUrls is: " + petResponse.getPhotoUrls());
            System.out.println("getTags is: " + petResponse.getTags().get(0).getId());
            System.out.println("getTags is: " + petResponse.getTags().get(0).getName());

            // Assertions
            Assert.assertEquals(pet.getId(), petResponse.getId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void createPetWithBuilderPatternTest(){
        RestAssured.baseURI = "https://petstore.swagger.io";
        PetLombok.Category category =  new PetLombok.Category.CategoryBuilder()
                .id(422)
                .name("Animal")
                .build();
        PetLombok.Tag tag1 = new PetLombok.Tag.TagBuilder()
                .id(50)
                .name("blue")
                .id(87)
                .name("green")
                .build();
        PetLombok.Tag tag2 = new PetLombok.Tag.TagBuilder()
                .id(78)
                .name("orange")
                .build();
        PetLombok pet = new PetLombok.PetLombokBuilder()
                .id(12)
                .category(category)
                .name("golden retriever")
                .photoUrls(Arrays.asList("http://www.dog.com", "http://www.dog2.com"))
                .tags(Arrays.asList(tag1, tag2))
                .status("available")
                .build();
        Response response  = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post("/v2/pet");
        System.out.println(response.getStatusCode());
        response.prettyPrint();

        // De-serializer
        ObjectMapper mapper = new ObjectMapper();
        try {
            PetLombok petResponse = mapper.readValue(response.getBody().asString(), PetLombok.class);
            System.out.println("Id is: " + petResponse.getId());
            System.out.println("getName is: " + petResponse.getName());
            System.out.println("getStatus is: " + petResponse.getStatus());

            System.out.println("getCategory is: " + petResponse.getCategory().getId());
            System.out.println("getCategory is: " + petResponse.getCategory().getName());

            System.out.println("getPhotoUrls is: " + petResponse.getPhotoUrls());
            System.out.println("getTags is: " + petResponse.getTags().get(0).getId());
            System.out.println("getTags is: " + petResponse.getTags().get(0).getName());

            // Assertions
            Assert.assertEquals(pet.getId(), petResponse.getId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
