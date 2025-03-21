package dailyfarm.order.dto;

import java.util.UUID;

public record OrderResponse(UUID uuid, UUID customerUuid, UUID surpriseBagUuid) {

}
