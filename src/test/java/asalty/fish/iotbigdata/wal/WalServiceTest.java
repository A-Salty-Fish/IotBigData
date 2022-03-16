package asalty.fish.iotbigdata.wal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/11 14:36
 */
@SpringBootTest
public class WalServiceTest {

    @Resource
    WalService mapDBService;

    @Test
    public void testDB() throws Exception {
        for (int i = 0; i < 100; i++) {
            mapDBService.put("test" + i);
        }
        List<String> list = mapDBService.getAll(String.class, mapDBService.get(String.class));
        for (String s : list) {
            System.out.println(s);
        }
    }
}
