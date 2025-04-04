package org.example.learingss.security;

import org.example.learingss.filter.TokenFilter;
import org.example.learingss.filter.UsernamePasswordLoginFilter;
import org.example.learingss.security.handler.LoginFailureHandler;
import org.example.learingss.security.handler.LoginSuccessHandler;
import org.example.learingss.security.handler.LogoutSuccessJsonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
public class SecurityConfig
{
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LogoutSuccessJsonHandler logoutSuccessJsonHandler;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private UserDetailService userDetailsService;

    public UsernamePasswordLoginFilter getUsernamePasswordLoginFilter() throws Exception
    {
        UsernamePasswordLoginFilter usernamePasswordLoginFilter = new UsernamePasswordLoginFilter();
        usernamePasswordLoginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        usernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        usernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler);
        usernamePasswordLoginFilter.setFilterProcessesUrl("/user/login");
        return usernamePasswordLoginFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/user/**", "/index.html", "/register.html").permitAll()
                                .requestMatchers("/auth/admin").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .cors(customizer -> customizer.disable())
                .csrf(csrf -> csrf.disable())
                .logout(logout -> logout.
                        logoutSuccessHandler(logoutSuccessJsonHandler));

        http.addFilterAt(getUsernamePasswordLoginFilter(), UsernamePasswordLoginFilter.class);
        http.addFilterAfter(new TokenFilter(), UsernamePasswordLoginFilter.class);
        return http.build();
    }


        @Bean
        public PasswordEncoder passwordEncoder() {
            // 使用 BCrypt 作为默认编码器
            Map<String, PasswordEncoder> encoders = new HashMap<>();
            encoders.put("bcrypt", new BCryptPasswordEncoder());

            DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
            return delegatingPasswordEncoder;
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider()
        {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(userDetailsService); // 注入你的 UserDetailsService
            provider.setPasswordEncoder(passwordEncoder());
            return provider;
        }



}
