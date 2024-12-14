package de.thu.thutorium.database.repositories;

import de.thu.thutorium.database.dbObjects.AddressDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing {@link AddressDBO} entities
 * in the database.
 * This interface extends {@link JpaRepository}, providing CRUD operations and
 * additional query methods
 * for the {@code AddressDBO} entity.
 * <p>
 * The {@code JpaRepository} interface provides methods such as {@code save},
 * {@code findById}, {@code findAll},
 * {@code deleteById}, and many more, allowing for easy interaction with the
 * database.
 * </p>
 * <p>
 * By extending {@code JpaRepository}, this interface inherits these methods and
 * can be used to perform
 * database operations on {@code AddressDBO} entities.
 * </p>
 */
@Repository
public interface AddressRepository extends JpaRepository<AddressDBO, Long> {
}
