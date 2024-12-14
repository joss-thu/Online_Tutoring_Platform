package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.common.MeetingTO;
import de.thu.thutorium.services.interfaces.MeetingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutor")
@RequiredArgsConstructor
public class TutorController {
    private final MeetingService meetingService;

    //Create Meeting
    @PostMapping("/create-meeting")
    public ResponseEntity<String> createMeeting(@RequestBody @Valid MeetingTO meetingTO) {
        meetingService.createMeeting(meetingTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Meeting created successfully");
    }

    //Delete Meeting
    @DeleteMapping("/delete-meeting/{meetingId}")
    public ResponseEntity<String> deleteMeeting(@PathVariable Long meetingId) {
        meetingService.deleteMeeting(meetingId);
        return ResponseEntity.ok("Meeting deleted successfully");
    }

    @PutMapping("/update-meeting/{meetingId}")
    public ResponseEntity<String> updateMeeting(
            @PathVariable Long meetingId,
            @RequestBody @Valid MeetingTO meetingTO) {
        meetingService.updateMeeting(meetingId, meetingTO);
        return ResponseEntity.ok("Meeting updated successfully");
    }
}
