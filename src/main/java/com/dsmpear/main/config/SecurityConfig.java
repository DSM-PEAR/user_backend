package com.dsmpear.main.config;

import com.dsmpear.main.security.JwtConfigurer;
import com.dsmpear.main.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .sessionManagement().disable()
                .formLogin().disable()
                .authorizeRequests()
                    .antMatchers("/**").authenticated()

                .and()
                    .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                    .csrf().disable();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring()
                .antMatchers(HttpMethod.POST, "/auth")
                .antMatchers(HttpMethod.PUT, "/auth")
                .antMatchers(HttpMethod.GET, "/email/auth")
                .antMatchers(HttpMethod.PUT, "/email/auth")
                .antMatchers(HttpMethod.POST, "/email/notification")
                .antMatchers(HttpMethod.GET, "/account")
                .antMatchers(HttpMethod.POST, "/account")
                .antMatchers(HttpMethod.GET, "/report")
                .antMatchers(HttpMethod.POST, "/question")
                .antMatchers(HttpMethod.GET, "/notice")
                .antMatchers(HttpMethod.GET, "/notice/{noticeId}")
                .antMatchers(HttpMethod.GET, "/user/profile/report")
                .antMatchers(HttpMethod.GET, "/profile/report")
                .antMatchers(HttpMethod.GET, "/profile/report/{userEmail}")
                .antMatchers(HttpMethod.GET, "/member/{reportId}")
                .antMatchers(HttpMethod.GET, "/profile/{userEmail}");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}