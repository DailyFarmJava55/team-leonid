package dailyfarm.business.dto;

import dailyfarm.account.dto.AccountResponse;
import dailyfarm.business.entity.Location;

public interface BusinessResponse extends AccountResponse {

    Location getLocation();
}
