package com.arthur.webnovel.config;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtServerConfiguration {
    @Bean
    public ExtServerConfigurationProperties extServerConfigurationProperties() {
        return new ExtServerConfigurationProperties();
    }

    @ConfigurationProperties(prefix = "ext.server", ignoreUnknownFields = false)
    public static class ExtServerConfigurationProperties implements EmbeddedServletContainerCustomizer {
        private String documentRoot;

        public String getDocumentRoot() {
            return documentRoot;
        }

        public void setDocumentRoot(String documentRoot) {
            this.documentRoot = documentRoot;
        }

        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            if (StringUtils.isNotEmpty(getDocumentRoot())) {
                container.setDocumentRoot(new File(getDocumentRoot()));
            }
        }
    }
}
