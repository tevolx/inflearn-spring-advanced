package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {

    @Aspect
    @Order(2)
    public static class LogAspect {

        @Around("hello.aop.order.aop.PointCuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[log] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

    @Aspect
    @Order(1)
    public static class TxAspect {

        @Around("hello.aop.order.aop.PointCuts.orderAndService()") // hello.aop.order 패키지와 하위 패키지이면서 클래스 이름 패턴이 *Service
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
            try {
                log.info("[transaction start] {}", joinPoint.getSignature());
                Object result = joinPoint.proceed();
                log.info("[transaction commit] {}", joinPoint.getSignature());
                return result;
            } catch (Exception e) {
                log.info("[transaction rollback] {}", joinPoint.getSignature());
                throw e;
            } finally {
                log.info("[resource release] {}", joinPoint.getSignature());
            }
        }
    }

}
