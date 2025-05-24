package services;

import entity.Friendship;
import entity.FriendshipId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.FriendshipRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;

    @Transactional
    public Friendship createFriendship(Friendship friendship) {
        return friendshipRepository.save(friendship);
    }

    @Transactional(readOnly = true)
    public List<Friendship> getFriendshipsForUser(Long userId) {
        return friendshipRepository.findByUser1IdOrUser2Id(userId, userId);
    }

    @Transactional
    public void deleteFriendship(Long user1Id, Long user2Id) {
        FriendshipId id = new FriendshipId();
        id.setUser1Id(user1Id);
        id.setUser2Id(user2Id);
        if (!friendshipRepository.existsById(id)) {
            throw new NoSuchElementException("Friendship not found");
        }
        friendshipRepository.deleteById(id);
    }
}