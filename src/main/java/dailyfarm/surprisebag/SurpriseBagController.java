package dailyfarm.surprisebag;

import dailyfarm.surprisebag.dto.SurpriseBagWriteDto;
import dailyfarm.surprisebag.dto.SurpriseBagReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("surprise-bag")
@RequiredArgsConstructor
public class SurpriseBagController {

    private final SurpriseBagService surpriseBagService;

    @PostMapping
    @PreAuthorize("hasRole('BUSINESS')")
    public SurpriseBagReadDto create(@RequestBody SurpriseBagWriteDto surpriseBagWriteDto, Authentication authentication) {
        return surpriseBagService.create(surpriseBagWriteDto, authentication);
    }

    @GetMapping
    public List<SurpriseBagReadDto> read() {
        return surpriseBagService.read();
    }

    @GetMapping("{surpriseBagUuid}")
    public SurpriseBagReadDto read(@PathVariable UUID surpriseBagUuid) {
        return surpriseBagService.read(surpriseBagUuid);
    }

    @PutMapping("{surpriseBagUuid}")
    @PreAuthorize("hasRole('BUSINESS')")
    public SurpriseBagReadDto update(@PathVariable UUID surpriseBagUuid, @RequestBody SurpriseBagWriteDto surpriseBagWriteDto, Authentication authentication) {
        return surpriseBagService.update(surpriseBagUuid, surpriseBagWriteDto, authentication);
    }

    @DeleteMapping("{surpriseBagUuid}")
    @PreAuthorize("hasRole('BUSINESS')")
    public void delete(@PathVariable UUID surpriseBagUuid, Authentication authentication) {
        surpriseBagService.delete(surpriseBagUuid, authentication);
    }
}
