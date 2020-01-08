package com.dwelling.app


import com.dwelling.app.constants.URLConstants
import com.dwelling.app.security.JwtAuthenticationEntryPoint
import com.dwelling.app.security.JwtAuthorizationTokenFilter
import com.dwelling.app.security.services.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        jsr250Enabled = true ,
        securedEnabled = true)
class SecurityConfiguration : WebSecurityConfigurerAdapter(false) {

    @Autowired
    private lateinit var jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint

    @Autowired
    private lateinit var jwtAuthorizationTokenFilter: JwtAuthorizationTokenFilter

    @Autowired
    private lateinit var jwtUserDetailsService: JwtUserDetailsService


    @Value("\${jwt.header}")
    private lateinit var tokenHeader: String

    @Value("\${jwt.route.authentication.path}")
    private lateinit var authenticationPath: String


    /*
    * configuracion global que ignora paths de recursos y el login path post
    * */
    override fun configure(web: WebSecurity) {
        web.ignoring()
                .antMatchers(HttpMethod.POST,authenticationPath)
                // allow anonymous resource requests
                .and()
                .ignoring()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                )
                // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
                .and()
                .ignoring()
                .antMatchers("/h2-console/**/**")
    }


    @Bean(name = [BeanIds.AUTHENTICATION_MANAGER])
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }


    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(encoder())
    }

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {

        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()
                //entry point
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // Un-secure H2 Database
                .antMatchers("/h2-console/**/**").permitAll()
                .antMatchers("auth/**/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers(URLConstants.TEST).permitAll()
                .antMatchers(URLConstants.RESULT_URL).permitAll()
                .antMatchers(URLConstants.SEARCH).permitAll()
                .anyRequest().authenticated()

        httpSecurity
                .addFilterBefore(jwtAuthorizationTokenFilter, UsernamePasswordAuthenticationFilter::class.java)

        // disable page caching
        httpSecurity
                .headers()
                .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
                .cacheControl()
    }


}
