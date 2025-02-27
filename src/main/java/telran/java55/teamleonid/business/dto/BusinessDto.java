package telran.java55.teamleonid.business.dto;

import telran.java55.teamleonid.business.model.GeoLocation;

public record BusinessDto(
    String email,
    GeoLocation geoLocation
) {}
