package dailyfarm.business.dto;

import dailyfarm.account.dto.ProfileResponse;
import dailyfarm.business.entity.LocationValue;

public interface BusinessResponse extends ProfileResponse {

    LocationValue getLocation();
}
