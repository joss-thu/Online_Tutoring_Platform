package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.AffiliationDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffiliationRepository extends JpaRepository<AffiliationDBO, Long> {}
