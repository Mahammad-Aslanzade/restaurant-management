package com.example.restaurantmanagement.mapper;

import com.example.restaurantmanagement.dao.entity.FeedbackEntity;
import com.example.restaurantmanagement.model.feedback.FeedbackCDto;
import com.example.restaurantmanagement.model.feedback.FeedbackDto;
import com.example.restaurantmanagement.model.feedback.FeedbackUDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    List<FeedbackDto> listToDto(List<FeedbackEntity> feedbackEntities);

    FeedbackDto mapToDto(FeedbackEntity feedbackEntity);

    FeedbackEntity mapToEntity(FeedbackCDto feedBackCDto);
    FeedbackEntity mapToEntity(FeedbackUDto feedbackUDto);
}
