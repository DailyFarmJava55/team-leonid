package dailyfarm.surprisebag;

import dailyfarm.surprisebag.entity.SurpriseBag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SurpriseBagRepository extends JpaRepository<SurpriseBag, UUID> {

}
