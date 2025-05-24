package aspect;


import entity.Notification;
import entity.NotificationType;
import entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import repository.NotificationRepository;
import repository.UserRepository;

import java.time.LocalDateTime;

@Aspect
@Component
public class ErrorNotificationAspect {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public ErrorNotificationAspect(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @AfterThrowing(pointcut = "execution(* com.gosmoke.service.*.*(..))", throwing = "exception")
    public void notifyOnError(JoinPoint joinPoint, Exception exception) {
        // Админ с ID 1
        User admin = userRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("Admin user not found"));

        String methodName = joinPoint.getSignature().toShortString();
        String errorMessage = String.format("Error in %s: %s", methodName, exception.getMessage());

        Notification notification = new Notification();
        notification.setUser(admin);
        notification.setType(NotificationType.ERROR);
        notification.setText(errorMessage);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }
}