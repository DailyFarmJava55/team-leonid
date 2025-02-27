package telran.java55.teamleonid.business;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import telran.java55.teamleonid.business.dto.BusinessDto;
import telran.java55.teamleonid.business.dto.LoginDto;
import telran.java55.teamleonid.business.dto.RegisterDto;
import telran.java55.teamleonid.business.dto.TokenDto;
import telran.java55.teamleonid.business.model.Business;
import telran.java55.teamleonid.business.token.TokenService;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class BusinessService {

    private final PasswordEncoder passwordEncoder;
    private final BusinessRepository businessRepository;
    private final ModelMapper modelMapper;
    private final TokenService tokenService;

    BusinessDto register(RegisterDto registerDto) {
        if (businessRepository.existsByEmail(registerDto.email())) {
            throw new EntityExistsException("");
        }
        Business business = new Business();
        business.setEmail(registerDto.email());
        business.setPassword(passwordEncoder.encode(registerDto.password()));
        business = businessRepository.save(business);
        return modelMapper.map(business, BusinessDto.class);
    }

    TokenDto login(LoginDto loginDto) {
        Business business = businessRepository.findByEmail(loginDto.email()).orElseThrow(
            () -> new EntityNotFoundException("")
        );
        if (!passwordEncoder.matches(loginDto.password(), business.getPassword())) {
            throw new BadCredentialsException("");
        }
        return tokenService.generateToken(loginDto.email(), new Date());
    }

    public void logout(String refreshToken) {
        tokenService.revokeToken(refreshToken);
    }

    public TokenDto refreshToken(String refreshToken) {
        return tokenService.refreshToken(refreshToken);
    }
}
