package dailyfarm.configuration;

import dailyfarm.account.AccountFactory;
import dailyfarm.account.AccountRepository;
import dailyfarm.account.login.LoginService;
import dailyfarm.account.profile.ProfileService;
import dailyfarm.account.register.RegisterService;
import dailyfarm.business.BusinessDetailsService;
import dailyfarm.business.entity.Business;
import dailyfarm.customer.CustomerDetailsService;
import dailyfarm.customer.entity.Customer;
import dailyfarm.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j @Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class GlobalConfiguration {

    @Bean("BusinessSecurityFilterChain")
    public SecurityFilterChain businessFilterChain(HttpSecurity http) throws Exception {
        log.info("BusinessSecurityFilterChain");
        return http.securityMatcher("/business/**")
            .csrf(AbstractHttpConfigurer::disable)
            // .headers(headers->headers.frameOptions(opt->opt.disable()))
            // .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers("/business/login", "/business/register").permitAll()
                .anyRequest().hasRole("BUSINESS")
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter(), BasicAuthenticationFilter.class)
            // .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean("CustomerSecurityFilterChain")
    public SecurityFilterChain customerFilterChain(HttpSecurity http) throws Exception {
        log.info("CustomerSecurityFilterChain");
        return http.securityMatcher("/customer/**")
            .csrf(AbstractHttpConfigurer::disable)
            // .headers(headers->headers.frameOptions(opt->opt.disable()))
            // .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers("/customer/login", "/customer/register").permitAll()
                .anyRequest().hasRole("CUSTOMER")
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter(), BasicAuthenticationFilter.class)
            // .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean("BusinessAuthenticationProvider")
    public AuthenticationProvider businessAuthenticationProvider(
        BusinessDetailsService businessDetailsService,
        PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(businessDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean("CustomerAuthenticationProvider")
    public AuthenticationProvider customerAuthenticationProvider(
        CustomerDetailsService customerDetailsService,
        PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customerDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean("BusinessAuthenticationManager")
    public AuthenticationManager businessAuthenticationManager(
        @Qualifier("BusinessAuthenticationProvider")
        AuthenticationProvider authenticationProvider
    ) {
        return new ProviderManager(authenticationProvider);
    }

    @Bean("CustomerAuthenticationManager")
    public AuthenticationManager customAuthenticationManager(
        @Qualifier("CustomerAuthenticationProvider")
        AuthenticationProvider authenticationProvider
    ) {
        return new ProviderManager(authenticationProvider);
    }

    @Bean @Primary
    public AuthenticationManager defaultAuthenticationManager(
        AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public RegisterService<Business> businessRegisterService(
        AccountRepository<Business> accountRepository,
        AccountFactory<Business> accountFactory,
        PasswordEncoder passwordEncoder
    ) {
        return new RegisterService<>(
            accountRepository, accountFactory, passwordEncoder
        );
    }

    @Bean
    public LoginService<Business> businessLoginService(
        @Qualifier("BusinessAuthenticationManager")
        AuthenticationManager authenticationManager
    ) {
        return new LoginService<>(authenticationManager);
    }

    @Bean
    public ProfileService<Business> businessProfileService(
        AccountRepository<Business> accountRepository
    ) {
        return new ProfileService<>(accountRepository);
    }

    @Bean
    public RegisterService<Customer> customerRegisterService(
        AccountRepository<Customer> accountRepository,
        AccountFactory<Customer> accountFactory,
        PasswordEncoder passwordEncoder
    ) {
        return new RegisterService<>(
            accountRepository, accountFactory, passwordEncoder
        );
    }

    @Bean
    public LoginService<Customer> customerLoginService(
        @Qualifier("CustomerAuthenticationManager")
        AuthenticationManager authenticationManager
    ) {
        return new LoginService<>(authenticationManager);
    }

    @Bean
    public ProfileService<Customer> customerProfileService(
        AccountRepository<Customer> accountRepository
    ) {
        return new ProfileService<>(accountRepository);
    }
}
