package ism.absence.data.records;

import lombok.Builder;

@Builder
public record Localisation(
        double longitude,
        double latitude
) {
}