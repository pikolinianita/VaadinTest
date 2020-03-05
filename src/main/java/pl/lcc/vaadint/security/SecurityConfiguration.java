/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lcc.vaadint.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 *
 * @author piko
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Require login to access internal pages and configure login form.
     *
     **/
      private static final String LOGIN_PROCESSING_URL = "/login";
      private static final String LOGIN_FAILURE_URL = "/login?error"; 
      private static final String LOGIN_URL = "/login"; 
      private static final String LOGOUT_SUCCESS_URL = "/login";
     
     
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Not using Spring CSRF here to be able to use plain HTML for the login page
        http.csrf().disable() // 

                // Register our CustomRequestCache that saves unauthorized access attempts, so
                // the user is redirected after login.
                .requestCache().requestCache(new CustomRequestCache()) // 

                // Restrict access to our application.
                .and().authorizeRequests()
                // Allow all flow internal requests.
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll() // 

                // Allow all requests by logged in users.
                .anyRequest().authenticated() // 

                // Configure the login page.
                .and().formLogin().loginPage(LOGIN_URL).permitAll() // 
                .loginProcessingUrl(LOGIN_PROCESSING_URL) // 
                .failureUrl(LOGIN_FAILURE_URL)
                // Configure logout
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                // Vaadin Flow static resources
                "/VAADIN/**",
                // the standard favicon URI
                "/favicon.ico",
                // the robots exclusion standard
                "/robots.txt",
                // web application manifest
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",
                // icons and images
                "/icons/**",
                "/images/**",
                // (development mode) static resources
                "/frontend/**",
                // (development mode) webjars
                "/webjars/**",
                // (development mode) H2 debugging console
                "/h2-console/**",
                // (production mode) static resources
                "/frontend-es5/**", "/frontend-es6/**");
    }
}
