package services;


import entity.SmokingPreference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.SmokingPreferenceRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SmokingPreferenceService {
    private final SmokingPreferenceRepository preferenceRepository;

    @Transactional
    public SmokingPreference createPreference(SmokingPreference preference) {
        if (preferenceRepository.findByName(preference.getName()).isPresent()) {
            throw new IllegalArgumentException("Preference already exists");
        }
        return preferenceRepository.save(preference);
    }

    @Transactional(readOnly = true)
    public SmokingPreference getPreferenceById(Long id) {
        return preferenceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Preference not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<SmokingPreference> getAllPreferences() {
        return preferenceRepository.findAll();
    }
}