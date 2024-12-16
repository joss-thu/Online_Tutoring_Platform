package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.transferObjects.common.ProgressTO;
import de.thu.thutorium.database.databaseMappers.ProgressDBMapper;
import de.thu.thutorium.database.dbObjects.CourseDBO;
import de.thu.thutorium.database.dbObjects.ProgressDBO;
import de.thu.thutorium.database.dbObjects.UserDBO;
import de.thu.thutorium.database.repositories.CourseRepository;
import de.thu.thutorium.database.repositories.ProgressRepository;
import de.thu.thutorium.database.repositories.UserRepository;
import de.thu.thutorium.services.interfaces.ProgressService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProgressServiceImpl implements ProgressService {
  private final ProgressRepository progressRepository;
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final ProgressDBMapper progressDBMapper;

  public ProgressServiceImpl(
      ProgressRepository progressRepository,
      UserRepository userRepository,
      CourseRepository courseRepository,
      ProgressDBMapper progressDBMapper) {
    this.progressRepository = progressRepository;
    this.userRepository = userRepository;
    this.courseRepository = courseRepository;
    this.progressDBMapper = progressDBMapper;
  }

  @Override
  @Transactional
  public void createProgress(ProgressTO progressTO) {
    // Fetch the student and course entities
    UserDBO student =
        userRepository
            .findById(progressTO.getStudentId())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Invalid student ID: " + progressTO.getStudentId()));

    CourseDBO course =
        courseRepository
            .findById(progressTO.getCourseId())
            .orElseThrow(
                () ->
                    new IllegalArgumentException("Invalid course ID: " + progressTO.getCourseId()));

    // Create ProgressDBO
    ProgressDBO progressDBO = progressDBMapper.toEntity(progressTO, student, course);

    student.getReceivedScores().add(progressDBO);

    userRepository.save(student);
    // Save ProgressDBO
    progressRepository.save(progressDBO);
  }

  @Override
  @Transactional
  public boolean deleteProgress(Long userId, Long courseId) {
    // Find the progress record
    ProgressDBO progressDBO = progressRepository.findByUserIdAndCourseId(userId, courseId);
    if (progressDBO != null) {
      // Delete the progress record
      progressRepository.delete(progressDBO);
      return true;
    }
    return false;
  }

  @Override
  public boolean updateProgress(Long userId, Long courseId, Double points) {
    // Find the progress record
    ProgressDBO progressDBO = progressRepository.findByUserIdAndCourseId(userId, courseId);
    if (progressDBO != null) {
      // Update the points
      progressDBO.setPoints(points);
      // Save the updated progress record
      progressRepository.save(progressDBO);
      return true;
    }
    return false;
  }
}
