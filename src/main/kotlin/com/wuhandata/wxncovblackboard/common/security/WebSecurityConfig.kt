package com.wuhandata.wxncovblackboard.common.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class WebSecurityConfig: WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var myFilterSecurityInterceptor: MyFilterSecurityInterceptor

    @Bean
    fun customUserService(): UserDetailsService {
        return CustomUserService()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(customUserService()).passwordEncoder(BCryptPasswordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.headers().frameOptions().disable()
        http.csrf().disable()
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**", "/fonts/**", "/sound/**", "/favicon.ico", "/init").permitAll()
                .antMatchers("/wxmp/**").permitAll()
                .antMatchers(HttpMethod.GET,"/report/result").permitAll()
                .antMatchers(HttpMethod.GET,"/code/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .failureUrl("/login.html?error")
                .permitAll()
                .defaultSuccessUrl("/#/dashboard.html", true)
                .and()
                .logout()
                .logoutRequestMatcher(AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login.html")
                .permitAll()
                .invalidateHttpSession(true)
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor::class.java)
    }

}
