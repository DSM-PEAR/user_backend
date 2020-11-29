package com.dsmpear.main.config;

import com.dsmpear.main.security.JwtConfigurer;
import com.dsmpear.main.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
                    .antMatchers(HttpMethod.POST, "/auth").permitAll()
<<<<<<< HEAD
                    .antMatchers(HttpMethod.GET, "/report").permitAll()
                    .antMatchers(HttpMethod.POST, "/report").authenticated()
=======
                    .antMatchers(HttpMethod.POST, "/question").permitAll()
                    .antMatchers(HttpMethod.GET, "/notice").permitAll()
                    .antMatchers(HttpMethod.GET, "/notice/{noticeId}").permitAll()
                    .antMatchers(HttpMethod.GET, "/user/profile/report").permitAll()
                    .antMatchers(HttpMethod.GET, "/profile/report/{userEmail}").permitAll()
                    .antMatchers(HttpMethod.GET, "/profile/{userEmail}").permitAll()

>>>>>>> develop
                .and()
                    .apply(new JwtConfigurer(jwtTokenProvider));
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
