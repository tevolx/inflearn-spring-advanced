package hello.proxy.config.v3_proxyfactory;

import hello.proxy.app.v1.*;
import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProxyFactoryConfigV2 {

    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
        OrderControllerV2 orderControllerV2 = new OrderControllerV2(orderServiceV2(logTrace));
        ProxyFactory factory = new ProxyFactory(orderControllerV2);
        factory.addAdvisor(getAdvisor(logTrace));
        log.info("target={}, proxy={}", orderControllerV2.getClass(), factory.getProxy().getClass());
        return (OrderControllerV2) factory.getProxy();
    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
        OrderServiceV2 orderServiceV2 = new OrderServiceV2(orderRepositoryV2(logTrace));
        ProxyFactory factory = new ProxyFactory(orderServiceV2);
        factory.addAdvisor(getAdvisor(logTrace));
        log.info("target={}, proxy={}", orderServiceV2.getClass(), factory.getProxy().getClass());
        return (OrderServiceV2) factory.getProxy();
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
        OrderRepositoryV2 orderRepositoryV2 = new OrderRepositoryV2();
        ProxyFactory factory = new ProxyFactory(orderRepositoryV2);
        factory.addAdvisor(getAdvisor(logTrace));
        log.info("target={}, proxy={}", orderRepositoryV2.getClass(), factory.getProxy().getClass());
        return (OrderRepositoryV2) factory.getProxy();
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
