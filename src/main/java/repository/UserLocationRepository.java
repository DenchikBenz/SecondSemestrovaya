package repository;

import entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    Optional<UserLocation> findByUserId(Long userId);

    @Query(value = "SELECT * FROM UserLocations ul WHERE ul.is_ready_to_smoke = TRUE " +
            "AND ST_DWithin(" +
            "ST_SetSRID(ST_MakePoint(ul.longitude, ul.latitude), 4326)::geography, " +
            "ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography, " +
            ":radius)", nativeQuery = true)
    List<UserLocation> findUsersWithinRadius(double latitude, double longitude, double radius);
}
