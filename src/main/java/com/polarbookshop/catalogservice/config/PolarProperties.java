package com.polarbookshop.catalogservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

// NOTE: @RefreshScope annotation listens for refresh triggers. The
//       @ConfigurationProperties sets this bean to listen to the
//       RefreshScopeRefreshedEvent by default, which triggers as well,
//       so @RefreshScope is not needed here.
@ConfigurationProperties(prefix = "polar")
public class PolarProperties {
    /**
     * A message to welcome users.
     */
    private String greeting;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
