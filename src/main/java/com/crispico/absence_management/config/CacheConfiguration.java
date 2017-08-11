package com.crispico.absence_management.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.crispico.absence_management.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.crispico.absence_management.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.crispico.absence_management.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.crispico.absence_management.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.crispico.absence_management.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.crispico.absence_management.domain.Blog.class.getName(), jcacheConfiguration);
            cm.createCache(com.crispico.absence_management.domain.Entry.class.getName(), jcacheConfiguration);
            cm.createCache(com.crispico.absence_management.domain.Entry.class.getName() + ".tags", jcacheConfiguration);
            cm.createCache(com.crispico.absence_management.domain.Berry.class.getName(), jcacheConfiguration);
            cm.createCache(com.crispico.absence_management.domain.Cherry.class.getName(), jcacheConfiguration);
            cm.createCache(com.crispico.absence_management.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(com.crispico.absence_management.domain.Tag.class.getName(), jcacheConfiguration);
            cm.createCache(com.crispico.absence_management.domain.Tag.class.getName() + ".entries", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
