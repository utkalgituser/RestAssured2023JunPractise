package com.fake.api;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductLombok {
    private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private Product.Rating rating;

    // rating class: Inner class
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Rating {
        private double rate;
        private int count;
    }
}
