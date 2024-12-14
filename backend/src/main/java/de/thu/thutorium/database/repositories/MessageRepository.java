package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.MessageDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageDBO, Long> {}
