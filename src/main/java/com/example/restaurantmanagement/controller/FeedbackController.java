package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.feedback.FeedbackCDto;
import com.example.restaurantmanagement.model.feedback.FeedbackDto;
import com.example.restaurantmanagement.model.feedback.FeedbackUDto;
import com.example.restaurantmanagement.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public List<FeedbackDto> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/{feedback-id}")
    public FeedbackDto getFeedbackById(@PathVariable("feedback-id") String feedBackId) {
        return feedbackService.getFeedbackById(feedBackId);
    }

    @GetMapping("/current-user")
    public List<FeedbackDto> getCurrentUserFeedbacks() {
        return feedbackService.getCurrentUserFeedbacks();
    }

    @GetMapping("/user/{user-id}")
    public List<FeedbackDto> getFeedbackByUser(@PathVariable("user-id") String userId) {
        return feedbackService.getFeedbackByUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postFeedback(@RequestBody @Valid FeedbackCDto feedbackCDto) {
        feedbackService.postFeedback(feedbackCDto);
    }

    @PutMapping("/{feedback-id}")
    public void updateFeedback(@PathVariable("feedback-id") String feedbackId, @RequestBody @Valid FeedbackUDto feedbackUDto) {
        feedbackService.updateFeedback(feedbackId, feedbackUDto);
    }

    @DeleteMapping("/{feedback-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeedback(@PathVariable("feedback-id") String feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
    }
}
