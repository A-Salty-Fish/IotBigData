package asalty.fish.iotbigdata;

import asalty.fish.iotbigdata.dao.TestCreateTableDao;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.*;
import java.util.List;

@SpringBootTest
class IotBigDataApplicationTests {

    @Resource
    TestCreateTableDao testCreateTableDao;

    @Test
    void contextLoads() {
        System.out.println(testCreateTableDao.findAllByWatchID(0L).size());
    }

}
