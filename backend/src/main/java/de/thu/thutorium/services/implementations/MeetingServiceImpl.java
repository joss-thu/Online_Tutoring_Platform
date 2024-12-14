package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.transferObjects.common.MeetingTO;
import de.thu.thutorium.database.databaseMappers.MeetingDBMapper;
import de.thu.thutorium.database.dbObjects.AddressDBO;
import de.thu.thutorium.database.dbObjects.CourseDBO;
import de.thu.thutorium.database.dbObjects.MeetingDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.repositories.AddressRepository;
import de.thu.thutorium.database.repositories.CourseRepository;
import de.thu.thutorium.database.repositories.MeetingRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.interfaces.MeetingService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
  private final MeetingRepository meetingRepository;
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final AddressRepository addressRepository;
  private final MeetingDBMapper meetingMapper;

  @Override
  @Transactional
  public void createMeeting(MeetingTO meetingTO) {
    UserDBO tutor =
        userRepository
            .findById(meetingTO.getTutorId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Tutor not found with ID: " + meetingTO.getTutorId()));
    CourseDBO course =
        courseRepository
            .findById(meetingTO.getCourseId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Course not found with ID: " + meetingTO.getCourseId()));
    AddressDBO address =
        addressRepository
            .findById(meetingTO.getAddressId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Address not found with ID: " + meetingTO.getAddressId()));

    MeetingDBO meetingDBO = meetingMapper.toEntity(meetingTO);

    //Set References
    meetingDBO.setTutor(tutor);
    meetingDBO.setCourse(course);
    meetingDBO.setAddress(address);

    meetingRepository.save(meetingDBO);
  }

  @Override
  @Transactional
  public void deleteMeeting(Long meetingId){
    if (!meetingRepository.existsById(meetingId)) {
      throw new EntityNotFoundException("Meeting not found with ID: " + meetingId);
    }

    // Delete the meeting
    meetingRepository.deleteById(meetingId);
  }

  @Transactional
  public void updateMeeting(Long meetingId, MeetingTO meetingTO) {
    // Fetch the existing meeting
    MeetingDBO existingMeeting =
        meetingRepository
            .findById(meetingId)
            .orElseThrow(
                () -> new EntityNotFoundException("Meeting not found with ID: " + meetingId));

    // Update fields
    existingMeeting.setMeetingDate(meetingTO.getMeetingDate());
    existingMeeting.setMeetingTime(meetingTO.getMeetingTime());
    existingMeeting.setDuration(meetingTO.getDuration());
    existingMeeting.setMeetingType(meetingTO.getMeetingType());
    existingMeeting.setMeetingStatus(meetingTO.getMeetingStatus());

    // Update associated objects (tutor, course, and address)
    UserDBO tutor =
        userRepository
            .findById(meetingTO.getTutorId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Tutor not found with ID: " + meetingTO.getTutorId()));
    existingMeeting.setTutor(tutor);

    CourseDBO course =
        courseRepository
            .findById(meetingTO.getCourseId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Course not found with ID: " + meetingTO.getCourseId()));
    existingMeeting.setCourse(course);

    AddressDBO address =
        addressRepository
            .findById(meetingTO.getAddressId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Address not found with ID: " + meetingTO.getAddressId()));
    existingMeeting.setAddress(address);

    // Save the updated meeting
    meetingRepository.save(existingMeeting);
  }
}
