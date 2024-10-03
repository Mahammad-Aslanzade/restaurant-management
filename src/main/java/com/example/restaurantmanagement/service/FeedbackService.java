package com.example.restaurantmanagement.service;

import com.example.restaurantmanagement.dao.entity.FeedbackEntity;
import com.example.restaurantmanagement.dao.entity.MealEntity;
import com.example.restaurantmanagement.dao.entity.UserEntity;
import com.example.restaurantmanagement.dao.repository.jpa.FeedbackRepository;
import com.example.restaurantmanagement.enums.ExceptionDetails;
import com.example.restaurantmanagement.enums.Role;
import com.example.restaurantmanagement.exceptions.NotAllowedException;
import com.example.restaurantmanagement.exceptions.NotFoundException;
import com.example.restaurantmanagement.mapper.FeedbackMapper;
import com.example.restaurantmanagement.model.feedback.FeedbackCDto;
import com.example.restaurantmanagement.model.feedback.FeedbackDto;
import com.example.restaurantmanagement.model.feedback.FeedbackUDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class FeedbackService {

    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;

    private final UserService userService;
    private final MealService mealService;
    private final MyUserDetailService myUserDetailService;

    public List<FeedbackDto> getAllFeedbacks() {
        log.info("ACTION.getAllFeedbacks.start");
        List<FeedbackEntity> feedbackEntityList = feedbackRepository.findAll();
        List<FeedbackDto> feedBackDtoList = feedbackMapper.listToDto(feedbackEntityList);
        log.info("ACTION.getAllFeedbacks.end");
        return feedBackDtoList;
    }

    private FeedbackEntity getFeedbackEntity(String feedbackId) {
        return feedbackRepository.findById(feedbackId).orElseThrow(() ->
                new NotFoundException(
                        ExceptionDetails.FEEDBACK_NOT_FOUND.message(),
                        ExceptionDetails.FEEDBACK_NOT_FOUND.createLogMessage("getFeedbackEntity", feedbackId)
                )
        );
    }

    public FeedbackDto getFeedbackById(String feedbackId) {
        log.info("ACTION.getFeedbackById.start id : {}", feedbackId);
        FeedbackEntity feedbackEntity = getFeedbackEntity(feedbackId);
        FeedbackDto feedBackDto = feedbackMapper.mapToDto(feedbackEntity);
        log.info("ACTION.getFeedbackById.end id : {}", feedbackId);
        return feedBackDto;
    }

    public List<FeedbackDto> getFeedbackByUser(String userId) {
        log.info("ACTION.getFeedbackByUser.start userId : {}", userId);
        UserEntity user = userService.getUserEntity(userId);
        List<FeedbackEntity> feedbackEntityList = feedbackRepository.findAllByUser(user);
        List<FeedbackDto> feedbackDtoList = feedbackMapper.listToDto(feedbackEntityList);
        log.info("ACTION.getFeedbackByUser.end userId : {}", userId);
        return feedbackDtoList;
    }

    public void postFeedback(FeedbackCDto feedBackCDto) {
        log.info("ACTION.postFeedback.start requestBody : {}", feedBackCDto);
        FeedbackEntity feedbackEntity = feedbackMapper.mapToEntity(feedBackCDto);
        UserEntity user = myUserDetailService.getCurrentAuthenticatedUser();
        MealEntity meal = mealService.getMealEntity(feedBackCDto.getMealId());
        feedbackEntity.setUser(user);
        feedbackEntity.setMeal(meal);
        feedbackRepository.save(feedbackEntity);
        log.info("ACTION.postFeedback.end requestBody : {}", feedBackCDto);
    }

    public void updateFeedback(String feedbackId, FeedbackUDto feedbackUDto) {
        log.info("ACTION.updateFeedback.start id : {} | requestBody : {}", feedbackId, feedbackUDto);
        FeedbackEntity updatedFeedback = feedbackMapper.mapToEntity(feedbackUDto);
        FeedbackEntity oldFeedback = getFeedbackEntity(feedbackId);
        UserEntity currentUser = myUserDetailService.getCurrentAuthenticatedUser();
        if (!(currentUser == oldFeedback.getUser() || currentUser.getRole() == Role.ADMIN)){
            throw new NotAllowedException(
                    "You are not allowed to update this feedback",
                    String.format("ACTION.ERROR.updateFeedBack userId : %s | feedbackId : %s", currentUser.getId() , oldFeedback.getId())
            );
        }
        updatedFeedback.setId(oldFeedback.getId());
        updatedFeedback.setUser(oldFeedback.getUser());
        feedbackRepository.save(updatedFeedback);
        log.info("ACTION.updateFeedback.end id : {} | requestBody : {}", feedbackId, feedbackUDto);
    }

    public void deleteFeedback(String feedbackId) {
        log.info("ACTION.deleteFeedback.start id : {}", feedbackId);
        FeedbackEntity entity = getFeedbackEntity(feedbackId);
        feedbackRepository.delete(entity);
        log.info("ACTION.deleteFeedback.end id : {}", feedbackId);
    }

}

