package de.thu.thutorium.api.controllers;

import de.thu.thutorium.api.transferObjects.common.MeetingTO;
import de.thu.thutorium.database.dbObjects.enums.MeetingStatus;
import de.thu.thutorium.services.interfaces.MeetingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
