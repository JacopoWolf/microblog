package edu.marconivr.jacopo.microblog.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import edu.marconivr.jacopo.microblog.security.NoRedirectStrategy;
import edu.marconivr.jacopo.microblog.security.TokenAuthFilter;
import edu.marconivr.jacopo.microblog.security.TokenAuthProvider;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    //* protected urls. the /rest/ with post requests except for auth methods
    private static final RequestMatcher PROTECTED_URLS =
    new OrRequestMatcher
    (
        new AntPathRequestMatcher("/rest/users/**", "POST"),
        new AntPathRequestMatcher("/rest/posts/**", "POST"),
        new AntPathRequestMatcher("/rest/comments/**", "POST")
    );

    //* every other urls is public
    private static final RequestMatcher PUBLIC_URLS = new NegatedRequestMatcher(PROTECTED_URLS);




    @Autowired
    private TokenAuthProvider authProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception 
    {
        auth.authenticationProvider(authProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception 
    {
        web.ignoring().requestMatchers(PUBLIC_URLS);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {
        http
            .sessionManagement()
            .sessionCreationPolicy( SessionCreationPolicy.STATELESS )

            .and()
            .exceptionHandling()
            .defaultAuthenticationEntryPointFor( this.forbiddenEntryPoint() , PROTECTED_URLS )

            .and()
            .authenticationProvider( authProvider )
            .addFilterBefore(this.restAuthFilter(), AnonymousAuthenticationFilter.class )
            .authorizeRequests()
            .anyRequest()
            .authenticated()

            .and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .logout().disable();       
    }


    

    //* filter authentication 
    @Bean
    TokenAuthFilter restAuthFilter() throws Exception
    {
        TokenAuthFilter filter = new TokenAuthFilter(PROTECTED_URLS);
            filter.setAuthenticationManager( this.authenticationManager() );
            filter.setAuthenticationSuccessHandler( this.successHandler() );
        return filter;
    }



    //* if authentication fails, return a 403 forbidden response status 
    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() 
    {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN); 
    }

    //* authentication succeeded, redirect to the same URL 
    @Bean
    SimpleUrlAuthenticationSuccessHandler successHandler()
    {
        SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
            successHandler.setRedirectStrategy( new NoRedirectStrategy() );
        return successHandler;
    }

    

    //*
    @Bean
    FilterRegistrationBean<?> disableAutoRegistration( TokenAuthFilter filter )
    {
        FilterRegistrationBean<?> registration = new FilterRegistrationBean<>(filter);
            registration.setEnabled(false);
        return registration;
    }

}