package repository;
import entity.Friendship;
import entity.FriendshipId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipId> {
    List<Friendship> findByUser1IdOrUser2Id(Long user1Id, Long user2Id);
}