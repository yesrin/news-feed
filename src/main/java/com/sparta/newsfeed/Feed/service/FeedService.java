package com.sparta.newsfeed.Feed.service;

import com.sparta.newsfeed.Feed.dto.FeedRequestDto;
import com.sparta.newsfeed.Feed.dto.FeedResponseDto;
import com.sparta.newsfeed.Feed.entity.Feed;
import com.sparta.newsfeed.Feed.repository.FeedRepository;
import com.sparta.newsfeed.User.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;

    public void create(User user, FeedRequestDto requestDto) {
        feedRepository.save(new Feed(requestDto,user));
    }

    @Transactional(readOnly = true)
    public List<FeedResponseDto> getFeeds() {
        return feedRepository.findAllByOrderByModifiedAtDesc().stream().map(FeedResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public List<FeedResponseDto> getFeedsByUser(Long userId) {
        return feedRepository.findAllByUserId(userId).stream().map(FeedResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public List<FeedResponseDto> getFeedsByFolder(Long folderId) {
        return feedRepository.findAllByFolderId(folderId).stream().map(FeedResponseDto::new).toList();
    }

    @Transactional
    public void updateFeed(FeedRequestDto requestDto, Long id, User user) {
        Feed feed = feedRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 피드는 존재하지 않습니다.")
        );
        if (feed.getUser().getUsername().equals(user.getUsername())) {
            feed.update(requestDto);
        } else {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    public void deleteFeed(Long id, User user) {
        Feed feed = feedRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 피드는 존재하지 않습니다.")
        );
        if (feed.getUser().getUsername().equals(user.getUsername())) {
            feedRepository.delete(feed);
        } else {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }
}