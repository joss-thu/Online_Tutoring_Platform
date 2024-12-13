package de.thu.thutorium.api.transferObjects.common;

import de.thu.thutorium.database.dbObjects.AddressDBO;
import de.thu.thutorium.database.dbObjects.CourseDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.dbObjects.enums.MeetingStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingTO {
    @NotNull(message = "Meeting ID cannot be null")
    private Long meetingId;

    @NotEmpty(message = "The tutor id cannot be null")
    private UserDBO tutor;

    @NotEmpty(message = "The course id cannot be null")
    private CourseDBO course;

    @NotNull(message = "Meeting date cannot be null")
    private LocalDate meetingDate;

    @NotNull(message = "Meeting time cannot be null")
    private LocalDateTime meetingTime;

    @NotNull(message = "Duration cannot be null")
    @Positive(message = "Duration must be a positive integer")
    private Integer duration;

    private MeetingStatus meetingStatus;

    @NotNull(message = "Room number cannot be null")
    private String roomNum;

    private String meetingLink;

    @NotNull(message = "Address cannot be null")
    private AddressDBO address;
}
