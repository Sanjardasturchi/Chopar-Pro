package com.example.config;

import com.example.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTTokenFilter jwtTokenFilter;
    public static String[] AUTH_WHITELIST = {
            "/auth/*",
            "/registration/*",
            "/registration/api/register",
            "/profile/go-to-create",
            "/registration/verification/email/*",
            "/auth/api/login",
            "/email-history/api/get-history-by-email/{email}",
            "/email-history/api/get-history-by-given-date/{date}",
            "/attach/api/upload",
            "/attach/open/*",
            "/attach/api/open/*",
            "/src/resources/static/uploads/**"
          //  "/email-history/api/get-history-by-pagination"
    };
    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest()
                .authenticated());
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // authorization
//        http.authorizeHttpRequests()
//                .requestMatchers(AUTH_WHITELIST).permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin(httpSecurityFormLoginConfigurer ->
//                        httpSecurityFormLoginConfigurer
//                                .loginPage("/auth/go-to-loginPage")
//                                .loginPage("/registration/go-to-add")
//                                .loginProcessingUrl("/loginProcessUrl").permitAll()
////                                .defaultSuccessUrl()
//                                .failureUrl("/auth/go-to-loginPage?error=true")
//
//                ).logout(httpSecurityLogoutConfigurer ->
//                        httpSecurityLogoutConfigurer
//                                .logoutUrl("/auth/go-to-loginPageWithLogout")
//                                .logoutSuccessUrl("/greating")
//                );
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.cors(AbstractHttpConfigurer::disable);
//
//        return http.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String md5 = MD5Util.encode(rawPassword.toString());
                return md5.equals(encodedPassword);
            }
        };
    }

}
