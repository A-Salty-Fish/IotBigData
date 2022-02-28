package asalty.fish.iotbigdata;

import asalty.fish.iotbigdata.clickhouseJpa.ClickHouseMapper;
import asalty.fish.iotbigdata.mapper.entity.hits_v1;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.*;
import java.util.List;

@SpringBootTest
class IotBigDataApplicationTests {

    @Resource
    Statement clickHouseStatement;

    @Resource
    ClickHouseMapper clickHouseDao;

    @Test
    void contextLoads() {

    }

    @Test
    public void testClickHouse() {
        try {
            ResultSet rs = clickHouseStatement.executeQuery("select * from tutorial.hits_v1 limit 2");
            List<hits_v1> list = clickHouseDao.convertResultSetToList(rs, hits_v1.class);
            System.out.println(new Gson().toJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
