package dailyfarm.surprisebag;

import dailyfarm.business.BusinessRepository;
import dailyfarm.business.entity.BusinessEntity;
import dailyfarm.surprisebag.dto.CreateSurpriseBagRequest;
import dailyfarm.surprisebag.dto.SurpriseBagResponse;
import dailyfarm.surprisebag.entity.SurpriseBagEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j @Service
@RequiredArgsConstructor
public class SurpriseBagService {

    private final SurpriseBagRepository surpriseBagRepository;
    private final BusinessRepository businessRepository;

    @Transactional
    public SurpriseBagResponse create(Authentication authentication, CreateSurpriseBagRequest request) {
        BusinessEntity business = businessRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new EntityNotFoundException(authentication.getName()));

        SurpriseBagEntity surpriseBag = new SurpriseBagEntity();
        surpriseBag.setBusiness(business);

        surpriseBagRepository.save(surpriseBag);

        return new SurpriseBagResponse(
            surpriseBag.getUuid(),
            business.getUuid()
        );
    }
}
