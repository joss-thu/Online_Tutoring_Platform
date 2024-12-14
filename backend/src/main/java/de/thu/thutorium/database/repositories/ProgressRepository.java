package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.ProgressDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressRepository extends JpaRepository<ProgressDBO, Long> {}
