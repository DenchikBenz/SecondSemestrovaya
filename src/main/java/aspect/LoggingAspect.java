package aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.gosmoke.service.*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        long startTime = System.nanoTime();

        logger.info("Entering method {} with arguments: {}", methodName, Arrays.toString(args));

        Object result = joinPoint.proceed();

        long duration = (System.nanoTime() - startTime) / 1_000_000; // В миллисекундах
        logger.info("Exiting method {} with result: {} (took {} ms)", methodName, result, duration);

        return result;
    }
}
