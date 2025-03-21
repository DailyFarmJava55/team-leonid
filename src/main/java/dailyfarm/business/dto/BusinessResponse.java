package dailyfarm.business.dto;

import dailyfarm.account.dto.AccountResponse;
import dailyfarm.business.entity.LocationValue;

public interface BusinessResponse extends AccountResponse {

    LocationValue getLocation();
}
