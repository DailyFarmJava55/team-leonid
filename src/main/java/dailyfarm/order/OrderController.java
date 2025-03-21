package dailyfarm.order;

import dailyfarm.order.dto.CreateOrderRequest;
import dailyfarm.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderResponse create(Authentication authentication, @RequestBody CreateOrderRequest request) {
        return orderService.create(authentication, request);
    }
}
