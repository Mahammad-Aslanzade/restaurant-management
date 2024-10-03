package com.example.restaurantmanagement.dao.repository.elastic;

import com.example.restaurantmanagement.dao.entity.MealEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MealElasticSearchRepository extends ElasticsearchRepository<MealEntity, String> {

    List<MealEntity> searchAllByTitle(String title);

}
