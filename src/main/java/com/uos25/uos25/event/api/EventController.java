package com.uos25.uos25.event.api;

import com.uos25.uos25.auth.login.entity.PrincipalDetails;
import com.uos25.uos25.event.dto.EventDTO.EventCreateRequest;
import com.uos25.uos25.event.dto.EventDTO.EventInfoResponse;
import com.uos25.uos25.event.dto.EventDTO.EventInfoResponses;
import com.uos25.uos25.event.service.EventService;
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

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/event")
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<?> createEvent(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                         @RequestBody EventCreateRequest request) {
        eventService.createEvent(request, principalDetails.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/events")
    public ResponseEntity<EventInfoResponses> getAllEventInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        EventInfoResponses responses = eventService.getAllEventInfo(principalDetails.getId());

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventInfoResponse> getEventInfo(@PathVariable Long eventId,
                                                          @AuthenticationPrincipal PrincipalDetails principalDetails) {
        EventInfoResponse response = eventService.getEventInfo(eventId, principalDetails.getId());

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<EventInfoResponse> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        
        return ResponseEntity.ok().build();
    }

}
