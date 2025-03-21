package dailyfarm.surprisebag;

import dailyfarm.surprisebag.dto.CreateSurpriseBagRequest;
import dailyfarm.surprisebag.dto.SurpriseBagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("surprise-bag")
@RequiredArgsConstructor
public class SurpriseBagController {

    private final SurpriseBagService surpriseBagService;

    @PostMapping("create")
    @PreAuthorize("hasRole('BUSINESS')")
    public SurpriseBagResponse create(Authentication authentication, @RequestBody CreateSurpriseBagRequest request) {
        return surpriseBagService.create(authentication, request);
    }
}
