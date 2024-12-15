package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynmaicA() {
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler); // 동적으로 프록시 객체 생성

        proxy.call();
        log.info("targetClass={}", target.getClass().getName());
        log.info("proxyClass={}", proxy.getClass().getName());
    }

    @Test
    void dynmaicB() {
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler); // 동적으로 프록시 객체 생성

        proxy.call();
        log.info("targetClass={}", target.getClass().getName());
        log.info("proxyClass={}", proxy.getClass().getName());
    }
}
