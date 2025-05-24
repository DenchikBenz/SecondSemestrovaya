package entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class FriendshipId implements Serializable {
    private Long user1Id;
    private Long user2Id;
}
