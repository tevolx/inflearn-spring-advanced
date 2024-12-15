package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.*;

@RestController // 스프링은 @Controller 또는 @RequestMapping이 있어야 Controller로 인식 (스프링 3.0 이상 부터는 @RequestMapping이 있어도 인식x. 오직 @Controller가 있어야 함)
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/no-log")
    String noLog();
}
