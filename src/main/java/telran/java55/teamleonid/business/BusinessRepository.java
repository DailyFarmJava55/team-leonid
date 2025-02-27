package telran.java55.teamleonid.business;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.java55.teamleonid.business.model.Business;

import java.util.Optional;

interface BusinessRepository extends JpaRepository<Business, Long> {

    Optional<Business> findByEmail(String email);

    boolean existsByEmail(String email);
}
