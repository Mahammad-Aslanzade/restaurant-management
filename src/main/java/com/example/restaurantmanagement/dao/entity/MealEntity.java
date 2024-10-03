package com.example.restaurantmanagement.dao.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "meals")
@Document(indexName = "meals")
public class MealEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String ingredients;
    private String image;
    private String description;
    private Double carbohydrates;
    private Double calories;
    private Double fat;
    private Double gram;
    private Double protein;
    private Double price;
    private Double salePrice;
    private Double rate;
    @OneToMany(mappedBy = "meal" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<FeedbackEntity> feedbackEntities;
    @OneToOne
    @JoinColumn(name = "category")
    private MealCategoryEntity category;

    @Transient
    private List<String> ingredientsList;
    @Transient
    private static ObjectMapper objectMapper = new ObjectMapper();


    public void setIngredientsList(List<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
        this.ingredients = serializeIngredients(this.ingredientsList);
    }
    @PostConstruct
    public List<String> getIngredientsList(){
        return deserializeIngredients(this.ingredients);
    }

    private String serializeIngredients(List<String> ingredients) {
        try {
            return objectMapper.writeValueAsString(ingredients);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String> deserializeIngredients(String ingredientList) {
        try {
            return objectMapper.readValue(ingredientList, List.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
