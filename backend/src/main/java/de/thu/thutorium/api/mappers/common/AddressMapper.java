package de.thu.thutorium.api.mappers.common;

import de.thu.thutorium.api.transferObjects.common.AddressTO;
import de.thu.thutorium.database.dbObjects.AddressDBO;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between {@link AddressDBO} and {@link AddressTO}. This interface
 * uses MapStruct to automatically generate the implementation for mapping between the database
 * object (DBO) and the transfer object (TO).
 *
 * <p>The {@code componentModel = "spring"} attribute indicates that the generated mapper should be
 * a Spring bean, allowing it to be injected into other Spring components. The {@code
 * unmappedTargetPolicy = ReportingPolicy.IGNORE} attribute specifies that unmapped target
 * properties should be ignored, meaning that no error or warning will be generated if some
 * properties of the target type are not mapped.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper {
  /**
   * Converts an {@link AddressDBO} object to an {@link AddressTO} object.
   *
   * @param addressDBO the {@code AddressDBO} object to convert
   * @return an {@code AdminAddressTO} object containing the mapped data
   */
  AddressTO toDTO(AddressDBO addressDBO);

  /**
   * Converts an {@link AddressTO} object to an {@link AddressDBO} object.
   *
   * @param addressTO the {@code AdminAddressTO} object to convert
   * @return an {@code AddressDBO} object containing the mapped data
   */
  AddressDBO toDBO(AddressTO addressTO);
}
