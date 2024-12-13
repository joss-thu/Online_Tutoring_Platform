package de.thu.thutorium.services.implementations;

import de.thu.thutorium.api.transferObjects.common.UniversityTO;
import de.thu.thutorium.database.dbObjects.AddressDBO;
import de.thu.thutorium.database.dbObjects.UniversityDBO;
import de.thu.thutorium.database.repositories.AddressRepository;
import de.thu.thutorium.database.repositories.UniversityRepository;
import de.thu.thutorium.services.interfaces.UniversityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public UniversityDBO createUniversity(UniversityTO university) {
        //Map DTO to DBO
        AddressDBO address = AddressDBO.builder()
                .campusName(university.getAddress().getCampusName())
                .houseNum(university.getAddress().getHouseNum())
                .streetName(university.getAddress().getStreetName())
                .city(university.getAddress().getCity())
                .postalCode(university.getAddress().getPostalCode())
                .country(university.getAddress().getCountry())
                .phoneNumber(university.getAddress().getPhoneNumber())
                .faxNumber(university.getAddress().getFaxNumber())
                .emailAddress(university.getAddress().getEmailAddress())
                .build();

        addressRepository.save(address);

        UniversityDBO universityDBO =  UniversityDBO.builder()
                .name(university.getUniversityName())
                .address(address)
                .build();
        return universityRepository.save(universityDBO);
    }
}
