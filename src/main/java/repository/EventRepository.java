package repository;

import entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "SELECT * FROM Events e WHERE " +
            "ST_DWithin(" +
            "ST_SetSRID(ST_MakePoint(e.longitude, e.latitude), 4326)::geography, " +
            "ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography, " +
            ":radius)", nativeQuery = true)
    List<Event> findEventsWithinRadius(double latitude, double longitude, double radius);
}
