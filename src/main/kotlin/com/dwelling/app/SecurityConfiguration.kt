package com.dwelling.app

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
class SecurityConfiguration : WebSecurityConfigurerAdapter(false) {


    @Bean(name = arrayOf(BeanIds.AUTHENTICATION_MANAGER))
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }


    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        // http.csrf();

        http.authorizeRequests()
                .antMatchers("/save/**","/resources/**", "/", "/about", "/contact", "/blog", "/team", "/redirect",
                        "/registration", "/detail/**", "/search/**", "/h2-console/*", "/h2-console/**", "/assets/**")
                .permitAll()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                // .formLogin()
                // .loginPage("/login")
                //.successHandler(authenticationSuccessHandler())
                // .permitAll()
                // .and()
                .logout()
                .permitAll()
        http.headers().frameOptions().disable()
    }


}