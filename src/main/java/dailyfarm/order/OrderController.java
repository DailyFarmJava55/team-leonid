package dailyfarm.order;

import dailyfarm.order.dto.OrderReadDto;
import dailyfarm.order.dto.OrderWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderReadDto create(@RequestBody OrderWriteDto orderWriteDto, Authentication authentication) {
        return orderService.create(orderWriteDto, authentication);
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<OrderReadDto> read(Authentication authentication) {
        return orderService.read(authentication);
    }

    @GetMapping("{orderUuid}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderReadDto read(@PathVariable UUID orderUuid, Authentication authentication) {
        return orderService.read(orderUuid, authentication);
    }

    @PutMapping("{orderUuid}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderReadDto update(@PathVariable UUID orderUuid, @RequestBody OrderWriteDto orderWriteDto, Authentication authentication) {
        return orderService.update(orderUuid, orderWriteDto, authentication);
    }

    @DeleteMapping("{orderUuid}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void delete(@PathVariable UUID orderUuid, Authentication authentication) {
        orderService.delete(orderUuid, authentication);
    }
}
