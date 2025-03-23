package dailyfarm.order;

import dailyfarm.customer.CustomerRepository;
import dailyfarm.customer.entity.Customer;
import dailyfarm.order.dto.OrderReadDto;
import dailyfarm.order.dto.OrderWriteDto;
import dailyfarm.order.entity.Order;
import dailyfarm.surprisebag.SurpriseBagRepository;
import dailyfarm.surprisebag.entity.SurpriseBag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j @Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final SurpriseBagRepository surpriseBagRepository;

    @Transactional
    public OrderReadDto create(OrderWriteDto orderWriteDto, Authentication authentication) {
        Customer customer = customerRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new EntityNotFoundException(authentication.getName()));

        SurpriseBag surpriseBag = surpriseBagRepository.findById(orderWriteDto.surpriseBagUuid())
            .orElseThrow(() -> new EntityNotFoundException(orderWriteDto.surpriseBagUuid().toString()));

        Order order = new Order();
        order.setCustomer(customer);
        order.setSurpriseBag(surpriseBag);

        orderRepository.save(order);

        return new OrderReadDto(order.getUuid(), customer.getUuid(), surpriseBag.getUuid());
    }

    @Transactional(readOnly = true)
    public List<OrderReadDto> read(Authentication authentication) {
        return null;
    }

    @Transactional(readOnly = true)
    public OrderReadDto read(UUID orderUuid, Authentication authentication) {
        return null;
    }

    @Transactional
    public OrderReadDto update(UUID orderUuid, OrderWriteDto orderWriteDto, Authentication authentication) {
        return null;
    }

    @Transactional
    public void delete(UUID orderUuid, Authentication authentication) {

    }
}
