package dailyfarm.order.dto;

import java.util.UUID;

public record OrderReadDto(UUID orderUuid, UUID customerUuid, UUID surpriseBagUuid) {

}
