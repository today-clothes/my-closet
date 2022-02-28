package com.oclothes.global.config.security;

import com.oclothes.domain.user.domain.User;
import com.oclothes.global.config.security.exception.CustomAccessDeniedHandler;
import com.oclothes.global.config.security.exception.CustomAuthenticationEntryPoint;
import com.oclothes.global.config.security.jwt.JwtAuthenticationFilter;
import com.oclothes.global.config.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(this.jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/closet/**").hasAnyAuthority(User.Role.ROLE_USER.name(), User.Role.ROLE_ADMIN.name())
                .antMatchers("/clothes/**").hasAnyAuthority(User.Role.ROLE_USER.name(), User.Role.ROLE_ADMIN.name())
                .antMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
                .antMatchers(HttpMethod.GET, "/email-auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/tags/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

}
