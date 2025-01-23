package de.thu.thutorium.services;

import de.thu.thutorium.api.TOMappers.MeetingTOMapper;
import de.thu.thutorium.api.TOMappers.UserTOMapper;
import de.thu.thutorium.api.transferObjects.common.MeetingTO;
import de.thu.thutorium.database.dbObjects.MeetingDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.dbObjects.CourseDBO;
import de.thu.thutorium.database.dbObjects.enums.Role;
import de.thu.thutorium.database.repositories.AddressRepository;
import de.thu.thutorium.database.repositories.MeetingRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.database.repositories.CourseRepository;
import de.thu.thutorium.services.implementations.MeetingServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeetingServiceTest {

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private MeetingTOMapper meetingTOMapper;

    @Mock
    private UserTOMapper userTOMapper;

    @InjectMocks
    private MeetingServiceImpl meetingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        meetingService = new MeetingServiceImpl(
                meetingRepository,
                userRepository,
                courseRepository,
                addressRepository,
                meetingTOMapper,
                userTOMapper
        );
    }

    @Test
    void testCreateMeeting_throwsEntityNotFoundException() {
        // Arrange
        MeetingTO meetingTO = new MeetingTO();
        meetingTO.setTutorId(1L);
        meetingTO.setCourseId(1L);

        // Simulate tutor not found
        lenient().when(userRepository.findUserDBOByUserIdAndRoles_RoleName(1L, Role.TUTOR)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> meetingService.createMeeting(meetingTO));
        assertEquals("User not found with ID: 1", exception.getMessage());
    }

    @Test
    void testDeleteMeeting_success() {
        // Arrange
        Long meetingId = 1L;
        MeetingDBO meetingDBO = new MeetingDBO();
        UserDBO tutor = new UserDBO();
        CourseDBO course = new CourseDBO();
        tutor.setMeetings(new ArrayList<>());
        meetingDBO.setTutor(tutor);
        meetingDBO.setCourse(course);

        when(meetingRepository.findById(meetingId)).thenReturn(Optional.of(meetingDBO));

        // Act
        meetingService.deleteMeeting(meetingId);

        // Assert
        verify(meetingRepository).deleteById(meetingId);
    }

    @Test
    void testUpdateMeeting() {
        // Arrange
        Long meetingId = 1L;
        MeetingTO meetingTO = new MeetingTO();
        meetingTO.setTutorId(1L);
        meetingTO.setCourseId(1L);

        MeetingDBO existingMeetingDBO = new MeetingDBO();
        UserDBO tutor = new UserDBO();
        CourseDBO course = new CourseDBO();

        when(meetingRepository.findById(meetingId)).thenReturn(Optional.of(existingMeetingDBO));
        when(userRepository.findUserDBOByUserIdAndRoles_RoleName(1L, Role.TUTOR)).thenReturn(Optional.of(tutor));
        when(courseRepository.findByCourseIdAndTutor_UserId(1L, 1L)).thenReturn(Optional.of(course));
        when(meetingTOMapper.toDTO(any())).thenReturn(meetingTO); // Updated stubbing to handle null or any argument

        // Act
        MeetingTO updatedMeeting = meetingService.updateMeeting(meetingId, meetingTO);

        // Assert
        assertNotNull(updatedMeeting);
        verify(meetingRepository).save(any(MeetingDBO.class)); // Verify save is called
    }

    @Test
    void testRetrieveMeetingById_meetingNotFound() {
        // Arrange
        Long meetingId = 1L;
        when(meetingRepository.findById(meetingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> meetingService.retrieveMeetingById(meetingId));
    }

    @Test
    void testRetrieveMeetingById_success() {
        // Arrange
        Long meetingId = 1L;
        MeetingDBO meetingDBO = new MeetingDBO();
        when(meetingRepository.findById(meetingId)).thenReturn(Optional.of(meetingDBO));
        when(meetingTOMapper.toDTO(meetingDBO)).thenReturn(new MeetingTO());

        // Act
        MeetingTO meeting = meetingService.retrieveMeetingById(meetingId);

        // Assert
        assertNotNull(meeting);
    }
}