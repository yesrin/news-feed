package com.sparta.newsfeed.Feed.controller;

import com.sparta.newsfeed.Common.security.UserDetailsImpl;
import com.sparta.newsfeed.Feed.dto.FeedRequestDto;
import com.sparta.newsfeed.Feed.dto.FeedResponseDto;
import com.sparta.newsfeed.Feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeed")
public class FeedController {

    private final FeedService feedService;

    @PostMapping("/feed/{user_id}")
    public String create(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody FeedRequestDto requestDto){
        feedService.create(userDetails.getUser(),requestDto);
        return "redirect:/";
    }

    @GetMapping("/feeds/{user_id}")
    public List<FeedResponseDto> getFeedsByUser(@PathVariable Long user_id) {
        return feedService.getFeedsByUser(user_id);
    }

    @GetMapping("/feeds/{folder_id}")
    public List<FeedResponseDto> getFeedsByFolder(@PathVariable Long folder_id) {
        return feedService.getFeedsByFolder(folder_id);
    }

    @PutMapping("/feed/{id}") // Restful하다고 생각돼서 수정
    public String updateFeed(@RequestBody FeedRequestDto requestDto, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.updateFeed(requestDto, id, userDetails.getUser());
        return "redirect:/";
    }

    @DeleteMapping("/feed/{id}")
    public String deleteFeed(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        feedService.deleteFeed(id, userDetails.getUser());
        return "redirect:/";
    }
}