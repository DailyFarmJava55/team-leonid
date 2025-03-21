package dailyfarm.order;

import dailyfarm.customer.CustomerRepository;
import dailyfarm.customer.entity.Customer;
import dailyfarm.order.dto.CreateOrderRequest;
import dailyfarm.order.dto.OrderResponse;
import dailyfarm.order.entity.Order;
import dailyfarm.surprisebag.SurpriseBagRepository;
import dailyfarm.surprisebag.entity.SurpriseBag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j @Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final SurpriseBagRepository surpriseBagRepository;

    public OrderResponse create(Authentication authentication, CreateOrderRequest request) {
        Customer customer = customerRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new EntityNotFoundException(authentication.getName()));

        SurpriseBag surpriseBag = surpriseBagRepository.findById(request.surpriseBagId())
            .orElseThrow(() -> new EntityNotFoundException(request.surpriseBagId().toString()));

        Order order = new Order();
        order.setCustomer(customer);
        order.setSurpriseBag(surpriseBag);

        orderRepository.save(order);

        return new OrderResponse(
            order.getUuid(),
            customer.getUuid(),
            surpriseBag.getUuid()
        );
    }
}
