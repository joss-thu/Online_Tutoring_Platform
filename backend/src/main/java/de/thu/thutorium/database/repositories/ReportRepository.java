package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.ReportDBO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportDBO, Long> { }
