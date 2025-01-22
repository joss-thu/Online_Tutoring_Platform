package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.TOMappers.ReportTOMapper;
import de.thu.thutorium.api.transferObjects.common.ReportTO;
import de.thu.thutorium.database.dbObjects.CourseDBO;
import de.thu.thutorium.database.dbObjects.MeetingDBO;
import de.thu.thutorium.database.dbObjects.ReportDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.repositories.CourseRepository;
import de.thu.thutorium.database.repositories.MeetingRepository;
import de.thu.thutorium.database.repositories.ReportRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.exceptions.ResourceNotFoundException;
import de.thu.thutorium.services.interfaces.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
  private final ReportRepository reportRepository;
  private final ReportTOMapper reportTOMapper;
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final MeetingRepository meetingRepository;

  @Override
  public ReportTO createReport(ReportTO reportTO) {
    // Fetch related entities to ensure they exist
    UserDBO tutor =
        userRepository
            .findById(reportTO.getTutorId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Tutor not found with ID: " + reportTO.getTutorId()));

    UserDBO student =
        userRepository
            .findById(reportTO.getStudentId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Student not found with ID: " + reportTO.getStudentId()));

    CourseDBO course =
        courseRepository
            .findById(reportTO.getCourseId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Course not found with ID: " + reportTO.getCourseId()));

    MeetingDBO meeting =
        meetingRepository
            .findById(reportTO.getMeetingId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Meeting not found with ID: " + reportTO.getMeetingId()));

    // Create the report DBO
    ReportDBO reportDBO =
        ReportDBO.builder()
            .tutor(tutor)
            .student(student)
            .course(course)
            .meeting(meeting)
            .text(reportTO.getText())
            .build();

    // Save the report to the database
    ReportDBO savedReport = reportRepository.save(reportDBO);

    // Convert saved report DBO back to ReportTO
    return reportTOMapper.toDTO(savedReport);
  }

  @Override
  public void deleteReport(Long reportId) {
    // Check if the report exists in the database
    ReportDBO report =
        reportRepository
            .findById(reportId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Report not found with ID: " + reportId));

    // Delete the report from the database
    reportRepository.delete(report);
  }
}
