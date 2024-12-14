package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.MeetingDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<MeetingDBO, Long> {}
