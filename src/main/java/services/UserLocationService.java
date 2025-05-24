package services;

import entity.UserLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserLocationRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserLocationService {
    private final UserLocationRepository locationRepository;

    @Transactional
    public UserLocation updateLocation(UserLocation location) {
        return locationRepository.save(location);
    }

    @Transactional(readOnly = true)
    public UserLocation getLocationByUserId(Long userId) {
        return locationRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Location not found for user id: " + userId));
    }

    @Transactional(readOnly = true)
    public List<UserLocation> findUsersWithinRadius(double latitude, double longitude, double radius) {
        return locationRepository.findUsersWithinRadius(latitude, longitude, radius);
    }
}
