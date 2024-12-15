package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.ChatDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatDBO, Long> { }
