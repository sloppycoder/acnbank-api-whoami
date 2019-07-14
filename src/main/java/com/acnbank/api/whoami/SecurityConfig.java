package com.acnbank.api.whoami;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends
        ResourceServerConfigurerAdapter {

    @Value("${custom.resource.audience}")
    String audience;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(audience);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/api/**").authenticated()
        ;
    }

    protected static class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
            // insert custom expression handler stuff here
    }
}