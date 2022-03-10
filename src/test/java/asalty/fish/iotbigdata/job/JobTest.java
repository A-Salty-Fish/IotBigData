package asalty.fish.iotbigdata.job;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/10 18:43
 */
@SpringBootTest
public class JobTest {

    @Resource
    Timer timer;

    @Test
    public void test() throws InterruptedException {
        System.out.println("start\t" + System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            timer.addTask(new TimerTask(i * 1000, () -> {
                System.out.println("task\t" + finalI + "\t" + System.currentTimeMillis());
            }));
        }
        Thread.sleep(30000);
        System.out.println("stop\t" + System.currentTimeMillis());
    }
}
