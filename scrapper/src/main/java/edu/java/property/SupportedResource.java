package edu.java.property;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SupportedResource(
    @NotNull
    int id,

    @NotEmpty
    String baseUrl,

    @NotEmpty
    String urlPattern
) {
}
