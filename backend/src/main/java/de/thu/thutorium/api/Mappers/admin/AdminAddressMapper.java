package de.thu.thutorium.api.Mappers.admin;

import de.thu.thutorium.api.transferObjects.admin.AdminAddressTO;
import de.thu.thutorium.database.dbObjects.AddressDBO;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between {@link AddressDBO} and
 * {@link AdminAddressTO}.
 * This interface uses MapStruct to automatically generate the implementation
 * for mapping
 * between the database object (DBO) and the transfer object (TO).
 * <p>
 * The {@code componentModel = "spring"} attribute indicates that the generated
 * mapper
 * should be a Spring bean, allowing it to be injected into other Spring
 * components.
 * The {@code unmappedTargetPolicy = ReportingPolicy.IGNORE} attribute specifies
 * that
 * unmapped target properties should be ignored, meaning that no error or
 * warning will
 * be generated if some properties of the target type are not mapped.
 * </p>
 */
@Mapper(
        componentModel = "spring")
public interface AdminAddressMapper {
    /**
     * Converts an {@link AddressDBO} object to an {@link AdminAddressTO} object.
     *
     * @param addressDBO the {@code AddressDBO} object to convert
     * @return an {@code AdminAddressTO} object containing the mapped data
     */
    AdminAddressTO toDTO(AddressDBO addressDBO);

    /**
     * Converts an {@link AdminAddressTO} object to an {@link AddressDBO} object.
     *
     * @param adminAddressTO the {@code AdminAddressTO} object to convert
     * @return an {@code AddressDBO} object containing the mapped data
     */
    AddressDBO toDBO(AdminAddressTO adminAddressTO);
}
