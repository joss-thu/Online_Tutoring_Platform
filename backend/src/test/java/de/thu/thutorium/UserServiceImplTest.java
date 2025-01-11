package de.thu.thutorium;

import de.thu.thutorium.api.TOMappers.UserTOMapper;
import de.thu.thutorium.api.transferObjects.common.UserTO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.dbObjects.RoleDBO;
import de.thu.thutorium.database.dbObjects.enums.Role;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.implementations.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTOMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStudentCount() {
        UserDBO studentUser = buildUserWithRole(Role.STUDENT);
        UserDBO tutorUser = buildUserWithRole(Role.TUTOR);
        when(userRepository.findAll()).thenReturn(List.of(studentUser, tutorUser));

        Long result = userService.getStudentCount();

        assertEquals(1L, result);
        verify(userRepository).findAll();
    }

    @Test
    void testGetTutorCount() {
        UserDBO tutor1 = buildUserWithRole(Role.TUTOR);
        UserDBO tutor2 = buildUserWithRole(Role.TUTOR);
        UserDBO student = buildUserWithRole(Role.STUDENT);
        when(userRepository.findAll()).thenReturn(List.of(tutor1, tutor2, student));

        Long result = userService.getTutorCount();

        assertEquals(2L, result);
        verify(userRepository).findAll();
    }

    @Test
    void testFindByUserId_UserExists() {
        // Arrange
        Long userId = 100L;
        UserDBO userDBO = new UserDBO();
        userDBO.setFirstName("John");
        userDBO.setLastName("Doe");

        when(userRepository.findByUserId(userId)).thenReturn(userDBO);

        UserTO userTO = new UserTO();
        userTO.setFirstName("John");
        userTO.setLastName("Doe");
        when(userMapper.toDTO(userDBO)).thenReturn(userTO);

        UserTO result = userService.findByUserId(userId);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        verify(userRepository).findByUserId(userId);
        verify(userMapper).toDTO(userDBO);
    }

    @Test
    void testFindByUserId_UserNotFound() {
        Long userId = 200L;
        when(userRepository.findByUserId(userId)).thenReturn(null);  // Not found in DB

        UserTO result = userService.findByUserId(userId);

        assertNull(result); // service returns null if user is not found
        verify(userRepository).findByUserId(userId);
        verify(userMapper, never()).toDTO(any(UserDBO.class));
    }

    @Test
    void testGetTutorByID_TutorExists() {
        Long tutorId = 300L;
        UserDBO userDBO = new UserDBO();
        userDBO.setFirstName("Jane");
        userDBO.setLastName("Tutor");
        when(userRepository.findByTutorId(tutorId)).thenReturn(userDBO);

        UserTO mappedTutor = new UserTO();
        mappedTutor.setFirstName("Jane");
        mappedTutor.setLastName("Tutor");
        when(userMapper.toDTO(userDBO)).thenReturn(mappedTutor);

        UserTO result = userService.getTutorByID(tutorId);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        verify(userRepository).findByTutorId(tutorId);
        verify(userMapper).toDTO(userDBO);
    }

    @Test
    void testGetTutorByID_TutorNotFound() {
        Long tutorId = 400L;
        when(userRepository.findByTutorId(tutorId)).thenReturn(null);

        UserTO result = userService.getTutorByID(tutorId);

        assertNull(result);
        verify(userRepository).findByTutorId(tutorId);
        verify(userMapper, never()).toDTO(any());
    }

    @Test
    void testDeleteUser_UserExists() {
        // Arrange
        Long userId = 999L;
        UserDBO existingUser = new UserDBO();
        existingUser.setEmail("test@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.deleteUser(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).delete(existingUser);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            userService.deleteUser(userId);
        });
        assertEquals("User not found with ID: 999", ex.getMessage());
        verify(userRepository).findById(userId);
        verify(userRepository, never()).delete(any(UserDBO.class));
    }

    @Test
    void testUpdateUser_UserNotFound() {
        Long userId = 555L;
        UserTO userTO = new UserTO();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class, () -> {
            userService.updateUser(userId, userTO);
        });
        assertEquals("User not found with id 555", ex.getMessage());
        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any(UserDBO.class));
    }

    private UserDBO buildUserWithRole(Role role) {
        UserDBO user = new UserDBO();
        RoleDBO roleDBO = new RoleDBO();
        roleDBO.setRoleName(role);
        user.setRoles(Set.of(roleDBO));
        return user;
    }
}
