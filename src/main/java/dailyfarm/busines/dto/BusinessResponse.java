package dailyfarm.busines.dto;

import dailyfarm.busines.entity.GeoLocation;

public record BusinessResponse(
    String email,
    GeoLocation geoLocation
) {}
