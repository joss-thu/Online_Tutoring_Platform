package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.UniversityDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<UniversityDBO, Long> {

}
