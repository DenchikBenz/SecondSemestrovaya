package aspect;


import entity.Role;
import entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityNotFoundException;
import repository.UserRepository;

@Aspect
@Component
public class AdminAccessAspect {
    private final UserRepository userRepository;

    public AdminAccessAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Before("execution(* com.gosmoke.service.UserService.deleteUser(Long, Long)) || " +
            "execution(* com.gosmoke.service.EventService.deleteEvent(Long, Long))")
    public void checkAdminRole(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long userId = (Long) args[1]; // Второй параметр — currentUserId

        // Поиск текущего пользователя
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Current user not found with id: " + userId));

        if (!Role.ADMIN.equals(currentUser.getRole())) {
            throw new SecurityException("Only ADMIN can perform this operation");
        }
    }
}