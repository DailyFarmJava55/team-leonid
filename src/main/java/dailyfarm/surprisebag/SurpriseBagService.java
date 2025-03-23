package dailyfarm.surprisebag;

import dailyfarm.business.BusinessRepository;
import dailyfarm.business.entity.Business;
import dailyfarm.surprisebag.dto.SurpriseBagReadDto;
import dailyfarm.surprisebag.dto.SurpriseBagWriteDto;
import dailyfarm.surprisebag.entity.SurpriseBag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j @Service
@RequiredArgsConstructor
public class SurpriseBagService {

    private final SurpriseBagRepository surpriseBagRepository;
    private final BusinessRepository businessRepository;

    @Transactional
    public SurpriseBagReadDto create(SurpriseBagWriteDto surpriseBagWriteDto, Authentication authentication) {
        Business business = businessRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new EntityNotFoundException(authentication.getName()));

        SurpriseBag surpriseBag = new SurpriseBag();
        surpriseBag.setBusiness(business);

        surpriseBagRepository.save(surpriseBag);

        return new SurpriseBagReadDto(surpriseBag.getUuid(), business.getUuid());
    }

    @Transactional(readOnly = true)
    public List<SurpriseBagReadDto> read() {
        return null;
    }

    @Transactional(readOnly = true)
    public SurpriseBagReadDto read(UUID surpriseBagUuid) {
        return null;
    }

    @Transactional
    public SurpriseBagReadDto update(UUID surpriseBagUuid, SurpriseBagWriteDto surpriseBagWriteDto, Authentication authentication) {
        return null;
    }

    @Transactional
    public void delete(UUID surpriseBagUuid, Authentication authentication) {

    }
}
