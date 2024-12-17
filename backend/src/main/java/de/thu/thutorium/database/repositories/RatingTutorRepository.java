package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.RatingTutorDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingTutorRepository extends JpaRepository<RatingTutorDBO, Long> { }
