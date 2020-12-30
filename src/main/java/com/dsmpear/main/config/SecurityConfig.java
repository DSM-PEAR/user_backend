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

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
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
                    .antMatchers(HttpMethod.GET, "/report").authenticated()
                    .antMatchers(HttpMethod.POST, "/comment").authenticated()
                    .antMatchers(HttpMethod.POST, "/member").authenticated()
                    .antMatchers(HttpMethod.GET, "/member/**").authenticated()
                    .antMatchers(HttpMethod.DELETE, "/member/**").authenticated()
                    .antMatchers(HttpMethod.GET, "/user/profile").authenticated()
                    .antMatchers(HttpMethod.PUT, "/user/profile").authenticated()
                    .antMatchers(HttpMethod.PUT, "/user/profile/report").authenticated()
                .and()
                    .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring()
                .antMatchers("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
