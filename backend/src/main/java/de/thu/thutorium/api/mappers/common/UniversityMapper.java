package de.thu.thutorium.api.mappers.common;

import de.thu.thutorium.api.transferObjects.common.UniversityTO;
import de.thu.thutorium.database.dbObjects.UniversityDBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper interface for converting between {@link UniversityDBO} and {@link UniversityTO}. This
 * interface uses MapStruct to automatically generate the implementation for mapping between the
 * database object (DBO) and the transfer object (TO).
 *
 * <p>The {@code componentModel = "spring"} attribute indicates that the generated mapper should be
 * a Spring bean, allowing it to be injected into other Spring components. The {@code uses =
 * {AdminAddressMapper.class}} attribute specifies that the {@link AddressMapper} should be used for
 * mapping nested address objects. The {@code unmappedTargetPolicy = ReportingPolicy.IGNORE}
 * attribute specifies that unmapped target properties should be ignored, meaning that no error or
 * warning will be generated if some properties of the target type are not mapped.
 */
@Mapper(
    componentModel = "spring",
    uses = {AddressMapper.class})
public interface UniversityMapper {
  /**
   * Converts a {@link UniversityDBO} object to an {@link UniversityTO} object.
   *
   * @param universityDBO the {@code UniversityDBO} object to convert
   * @return an {@code toDTO} object containing the mapped data
   */
  @Mappings({
    @Mapping(target = "universityName", source = "universityName"),
    @Mapping(target = "address", source = "address")
  })
  UniversityTO toDTO(UniversityDBO universityDBO);

  /**
   * Converts an {@link UniversityTO} object to a {@link UniversityDBO} object.
   *
   * @param universityTO the {@code AdminUniversityTO} object to convert
   * @return a {@code toDBO} object containing the mapped data
   */
  @Mappings({
    @Mapping(target = "universityName", source = "universityName"),
    @Mapping(target = "address", source = "address")
  })
  UniversityDBO toDBO(UniversityTO universityTO);
}
