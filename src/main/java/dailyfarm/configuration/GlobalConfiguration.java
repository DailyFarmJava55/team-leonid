package dailyfarm.configuration;

import dailyfarm.account.AccountDetailsService;
import dailyfarm.account.AccountFactory;
import dailyfarm.account.AccountRepository;
import dailyfarm.account.login.LoginService;
import dailyfarm.account.profile.ProfileService;
import dailyfarm.account.register.RegisterService;
import dailyfarm.business.entity.Business;
import dailyfarm.business.profile.BusinessResponse;
import dailyfarm.customer.entity.Customer;
import dailyfarm.customer.profile.CustomerResponse;
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
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

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
    public AccountDetailsService<Business> businessDetailsService(
        AccountRepository<Business> accountRepository
    ) {
        return new AccountDetailsService<>(accountRepository);
    }

    @Bean
    public AccountDetailsService<Customer> customerDetailsService(
        AccountRepository<Customer> accountRepository
    ) {
        return new AccountDetailsService<>(accountRepository);
    }

    @Bean("BusinessAuthenticationProvider")
    public AuthenticationProvider businessAuthenticationProvider(
        AccountDetailsService<Business> accountDetailsService,
        PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean("CustomerAuthenticationProvider")
    public AuthenticationProvider customerAuthenticationProvider(
        AccountDetailsService<Customer> accountDetailsService,
        PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountDetailsService);
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

    @Bean
    public AccountFactory<Business> businessFactory() {
        return Business::new;
    }

    @Bean
    public AccountFactory<Customer> customerFactory() {
        return Customer::new;
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
    public LoginService<Business> businessLoginService(
        @Qualifier("BusinessAuthenticationManager")
        AuthenticationManager authenticationManager
    ) {
        return new LoginService<>(authenticationManager);
    }

    @Bean
    public LoginService<Customer> customerLoginService(
        @Qualifier("CustomerAuthenticationManager")
        AuthenticationManager authenticationManager
    ) {
        return new LoginService<>(authenticationManager);
    }

    @Bean
    public ProfileService<Business, BusinessResponse> businessProfileService(
        AccountRepository<Business> accountRepository
    ) {
        return new ProfileService<>(accountRepository, BusinessResponse.class);
    }

    @Bean
    public ProfileService<Customer, CustomerResponse> customerProfileService(
        AccountRepository<Customer> accountRepository
    ) {
        return new ProfileService<>(accountRepository, CustomerResponse.class);
    }
}
