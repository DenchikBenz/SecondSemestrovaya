package repository;

import entity.SmokingPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SmokingPreferenceRepository extends JpaRepository<SmokingPreference, Long> {
    Optional<SmokingPreference> findByName(String name);
}
