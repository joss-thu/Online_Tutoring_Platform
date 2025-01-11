package de.thu.thutorium;

import de.thu.thutorium.api.TOMappers.MeetingTOMapper;
import de.thu.thutorium.api.transferObjects.common.MeetingTO;
import de.thu.thutorium.database.DBOMappers.MeetingDBMapper;
import de.thu.thutorium.database.dbObjects.AddressDBO;
import de.thu.thutorium.database.dbObjects.CourseDBO;
import de.thu.thutorium.database.dbObjects.MeetingDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.repositories.AddressRepository;
import de.thu.thutorium.database.repositories.CourseRepository;
import de.thu.thutorium.database.repositories.MeetingRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.implementations.MeetingServiceImpl;
import de.thu.thutorium.services.interfaces.MeetingService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    private MeetingDBMapper meetingDBMapper;

    @Mock
    private MeetingTOMapper meetingTOMapper;

    @InjectMocks
    private MeetingServiceImpl meetingService;

    private UserDBO tutor;
    private CourseDBO course;
    private AddressDBO address;
    private MeetingTO meetingTO;
    private MeetingDBO meetingDBO;

    @BeforeEach
    void setUp() {
        // Initialize mocks and set up the test context
        MockitoAnnotations.openMocks(this);

        // Using Lombok's builder pattern for object instantiation
        tutor = UserDBO.builder()
                .userId(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("hashedPassword")
                .build();

        course = CourseDBO.builder()
                .courseId(1L)
                .courseName("Java 101")
                .build();

        address = AddressDBO.builder()
                .addressId(1L)
                .streetName("123 Main St")
                .city("Ulm")
                .build();

        meetingTO = new MeetingTO();
        meetingTO.setTutorId(1L);
        meetingTO.setCourseId(1L);
        meetingTO.setAddressId(1L);

        meetingDBO = new MeetingDBO();
        meetingDBO.setCourse(course);
        meetingDBO.setTutor(tutor);
        meetingDBO.setAddress(address);
    }


    @Test
    void testCreateMeeting_success() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(tutor));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(meetingDBMapper.toEntity(meetingTO)).thenReturn(meetingDBO);

        // Act
        meetingService.createMeeting(meetingTO);

        // Assert
        verify(meetingRepository, times(1)).save(meetingDBO);
    }

    @Test
    void testCreateMeeting_tutorNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            meetingService.createMeeting(meetingTO);
        });
        assertEquals("Tutor not found with ID: 1", exception.getMessage());
    }

    @Test
    void testCreateMeeting_courseNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(tutor));
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            meetingService.createMeeting(meetingTO);
        });
        assertEquals("Course not found with ID: 1", exception.getMessage());
    }

    @Test
    void testDeleteMeeting_success() {
        // Arrange
        when(meetingRepository.existsById(1L)).thenReturn(true);

        // Act
        meetingService.deleteMeeting(1L);

        // Assert
        verify(meetingRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteMeeting_notFound() {
        // Arrange
        when(meetingRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            meetingService.deleteMeeting(1L);
        });
        assertEquals("Meeting not found with ID: 1", exception.getMessage());
    }

    @Test
    void testUpdateMeeting_success() {
        // Arrange
        MeetingTO updatedMeetingTO = new MeetingTO();
        updatedMeetingTO.setTutorId(1L);
        updatedMeetingTO.setCourseId(1L);
        updatedMeetingTO.setAddressId(1L);

        when(meetingRepository.findById(1L)).thenReturn(Optional.of(meetingDBO));
        when(userRepository.findById(1L)).thenReturn(Optional.of(tutor));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        // Act
        meetingService.updateMeeting(1L, updatedMeetingTO);

        // Assert
        verify(meetingRepository, times(1)).save(meetingDBO);
    }

    @Test
    void testUpdateMeeting_meetingNotFound() {
        // Arrange
        when(meetingRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            meetingService.updateMeeting(1L, meetingTO);
        });
        assertEquals("Meeting not found with ID: 1", exception.getMessage());
    }

    @Test
    void testGetMeetingsForUser() {
        // Arrange
        List<MeetingDBO> meetings = List.of(meetingDBO);
        when(meetingRepository.findParticipatedMeetingsByUserId(1L)).thenReturn(meetings);
        when(meetingRepository.findScheduledMeetingsByTutorId(1L)).thenReturn(meetings);
        when(meetingTOMapper.toDTOList(meetings)).thenReturn(List.of(new MeetingTO()));

        // Act
        List<MeetingTO> result = meetingService.getMeetingsForUser(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
