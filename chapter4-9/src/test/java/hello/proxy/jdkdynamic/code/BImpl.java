package hello.proxy.jdkdynamic.code;

public class BImpl implements BInterface {
    @Override
    public String call() {
        return "B";
    }
}
