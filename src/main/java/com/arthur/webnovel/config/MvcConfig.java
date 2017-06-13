package com.arthur.webnovel.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ctlok.springframework.web.servlet.view.rythm.RythmConfigurator;
import com.ctlok.springframework.web.servlet.view.rythm.RythmViewResolver;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private Environment env;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(viewResolver());
    }

    @Bean
    public RythmConfigurator rythmConfigurator() {
        RythmConfigurator configurator = new RythmConfigurator();

        String base = env.getProperty("rythm.base-directory");
        if (! StringUtils.endsWith(base, "/")) {
            base = base + "/";
        }
        configurator.setRootDirectory(base);
        configurator.setMode(env.getProperty("rythm.mode"));
        return configurator;
    }

    @Bean
    public RythmViewResolver viewResolver() {
        RythmViewResolver resolver = new RythmViewResolver(rythmConfigurator());
        resolver.setSuffix(".rythm");
        return resolver;
    }

    @Bean
    public ServerProperties getServerProperties() {
        return new ServerCustomization();
    }

}