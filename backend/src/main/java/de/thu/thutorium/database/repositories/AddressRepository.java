package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.AddressDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressDBO, Long> { }
