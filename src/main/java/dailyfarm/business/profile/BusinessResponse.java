package dailyfarm.business.profile;

import dailyfarm.account.profile.ProfileResponse;
import dailyfarm.business.entity.Location;

public interface BusinessResponse extends ProfileResponse {

    Location getLocation();
}
