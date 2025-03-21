package dailyfarm.configuration;

import dailyfarm.account.*;
import dailyfarm.business.dto.BusinessResponse;
import dailyfarm.business.entity.BusinessEntity;
import dailyfarm.customer.dto.CustomerResponse;
import dailyfarm.customer.entity.CustomerEntity;
import dailyfarm.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j @Configuration
@EnableJpaAuditing
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class GlobalConfiguration {

    @Bean @Primary
    public AuthenticationManager defaultAuthenticationManager(
        AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean("BusinessSecurityFilterChain")
    public SecurityFilterChain businessFilterChain(
        HttpSecurity httpSecurity,
        JwtAuthenticationFilter jwtAuthenticationFilter,
        @Qualifier("BusinessAuthenticationManager")
        AuthenticationManager authenticationManager
    ) throws Exception {
        log.info("BusinessSecurityFilterChain");
        return httpSecurity.securityMatcher("/business/**")
            .csrf(AbstractHttpConfigurer::disable)
            .authenticationManager(authenticationManager)
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers("/business/register", "/business/login").permitAll()
                .requestMatchers("/business/refresh-token", "/business/logout").permitAll()
                .anyRequest().hasRole("BUSINESS")
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean("CustomerSecurityFilterChain")
    public SecurityFilterChain customerFilterChain(
        HttpSecurity httpSecurity,
        JwtAuthenticationFilter jwtAuthenticationFilter,
        @Qualifier("CustomerAuthenticationManager")
        AuthenticationManager authenticationManager
    ) throws Exception {
        log.info("CustomerSecurityFilterChain");
        return httpSecurity.securityMatcher("/customer/**")
            .csrf(AbstractHttpConfigurer::disable)
            .authenticationManager(authenticationManager)
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers("/customer/register", "/customer/login").permitAll()
                .requestMatchers("/customer/refresh-token", "/customer/logout").permitAll()
                .anyRequest().hasRole("CUSTOMER")
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean("BusinessAuthenticationManager")
    public AuthenticationManager businessAuthenticationManager(
        @Qualifier("BusinessAuthenticationProvider")
        AuthenticationProvider authenticationProvider
    ) {
        return new ProviderManager(authenticationProvider);
    }

    @Bean("CustomerAuthenticationManager")
    public AuthenticationManager customerAuthenticationManager(
        @Qualifier("CustomerAuthenticationProvider")
        AuthenticationProvider authenticationProvider
    ) {
        return new ProviderManager(authenticationProvider);
    }

    @Bean("BusinessAuthenticationProvider")
    public AuthenticationProvider businessAuthenticationProvider(
        AccountDetailsService<BusinessEntity> accountDetailsService,
        PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean("CustomerAuthenticationProvider")
    public AuthenticationProvider customerAuthenticationProvider(
        AccountDetailsService<CustomerEntity> accountDetailsService,
        PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AccountDetailsService<BusinessEntity> businessDetailsService(
        AccountRepository<BusinessEntity> accountRepository
    ) {
        return new AccountDetailsService<>(accountRepository);
    }

    @Bean
    public AccountDetailsService<CustomerEntity> customerDetailsService(
        AccountRepository<CustomerEntity> accountRepository
    ) {
        return new AccountDetailsService<>(accountRepository);
    }

    @Bean
    public AccountFactory<BusinessEntity> businessFactory() {
        return BusinessEntity::new;
    }

    @Bean
    public AccountFactory<CustomerEntity> customerFactory() {
        return CustomerEntity::new;
    }

    @Bean
    public AccountService<BusinessEntity> businessAccountService(
        @Qualifier("BusinessAuthenticationManager")
        AuthenticationManager authenticationManager,
        AccountRepository<BusinessEntity> accountRepository,
        AccountFactory<BusinessEntity> accountFactory,
        PasswordEncoder passwordEncoder,
        RefreshTokenRepository refreshTokenRepository
    ) {
        return new AccountService<>(
            authenticationManager,
            accountRepository,
            accountFactory,
            passwordEncoder,
            BusinessResponse.class,
            refreshTokenRepository
        );
    }

    @Bean
    public AccountService<CustomerEntity> customerAccountService(
        @Qualifier("CustomerAuthenticationManager")
        AuthenticationManager authenticationManager,
        AccountRepository<CustomerEntity> accountRepository,
        AccountFactory<CustomerEntity> accountFactory,
        PasswordEncoder passwordEncoder,
        RefreshTokenRepository refreshTokenRepository
    ) {
        return new AccountService<>(
            authenticationManager,
            accountRepository,
            accountFactory,
            passwordEncoder,
            CustomerResponse.class,
            refreshTokenRepository
        );
    }
}
