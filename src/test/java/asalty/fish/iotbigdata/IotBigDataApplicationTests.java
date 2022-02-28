package asalty.fish.iotbigdata;

import asalty.fish.iotbigdata.config.ClickHouseConfig;
import asalty.fish.iotbigdata.mapper.dao.ClickHouseDao;
import asalty.fish.iotbigdata.mapper.entity.hits_v1;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.*;

@SpringBootTest
class IotBigDataApplicationTests {

    @Resource
    Statement clickHouseStatement;

    @Resource
    ClickHouseDao clickHouseDao;

    @Test
    void contextLoads() {

    }

    @Test
    public void testClickHouse() {
        try {
            ResultSet rs = clickHouseStatement.executeQuery("select * from tutorial.hits_v1 limit 1");
            while (rs.next()) {
                hits_v1 hit = clickHouseDao.convertResultSetToClass(rs, hits_v1.class);
                System.out.println(new Gson().toJson(hit));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
