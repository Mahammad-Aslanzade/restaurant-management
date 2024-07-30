package com.example.restaurantmanagement.controller;

import com.example.restaurantmanagement.model.feedback.FeedbackCDto;
import com.example.restaurantmanagement.model.feedback.FeedbackDto;
import com.example.restaurantmanagement.model.feedback.FeedbackUDto;
import com.example.restaurantmanagement.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public List<FeedbackDto> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/{feedBackId}")
    public FeedbackDto getFeedbackById(@PathVariable String feedBackId) {
        return feedbackService.getFeedbackById(feedBackId);
    }

    @GetMapping("/user/{userId}")
    public List<FeedbackDto> getFeedbackByUser(@PathVariable String userId){
        return feedbackService.getFeedbackByUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postFeedback(@RequestBody @Valid FeedbackCDto feedbackCDto) {
        feedbackService.postFeedback(feedbackCDto);
    }

    @PutMapping("/{feedbackId}")
    public void updateFeedback(@PathVariable String feedbackId, @RequestBody @Valid FeedbackUDto feedbackUDto) {
        feedbackService.updateFeedback(feedbackId, feedbackUDto);
    }

    @DeleteMapping("/{feedbackId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeedback(@PathVariable String feedbackId){
        feedbackService.deleteFeedback(feedbackId);
    }
}
