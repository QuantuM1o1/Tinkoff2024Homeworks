package edu.java.configuration;

import edu.java.property.SupportedResource;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "resources", ignoreUnknownFields = false)
public record ResourcesConfig(
    @NotNull
    Map<String, SupportedResource> supportedResources
) {
}
