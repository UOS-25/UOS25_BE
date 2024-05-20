package com.uos25.uos25.event.api;

import com.uos25.uos25.auth.login.entity.PrincipalDetails;
import com.uos25.uos25.event.dto.EventDTO.EventCreateRequest;
import com.uos25.uos25.event.dto.EventDTO.EventInfoResponse;
import com.uos25.uos25.event.dto.EventDTO.EventInfoResponses;
import com.uos25.uos25.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Event", description = "Event API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

    @Operation(summary = "이벤트 생성", description = "이벤트를 생성한다")
    @PostMapping
    public ResponseEntity<?> createEvent(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                         @RequestBody EventCreateRequest request) {
        eventService.createEvent(request, principalDetails.getId());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모든 이벤트 불러오기", description = "로그인 유저의 모든 이벤트를 불러온다")
    @GetMapping("/events")
    public ResponseEntity<EventInfoResponses> getAllEventInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        EventInfoResponses responses = eventService.getAllEventInfo(principalDetails.getId());

        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "이벤트 상세 조회", description = "특정 이벤트 정보를 조회한다")
    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventInfoResponse> getEventInfo(@PathVariable Long eventId,
                                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
        EventInfoResponse response = eventService.getEventInfo(eventId, principalDetails.getId());

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "이벤트 삭제", description = "특정 이벤트를 삭제한다")
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<EventInfoResponse> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);

        return ResponseEntity.ok().build();
    }

}
