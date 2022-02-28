package asalty.fish.iotbigdata;

import asalty.fish.iotbigdata.config.ClickHouseConfig;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.*;

@SpringBootTest
class IotBigDataApplicationTests {

    @Resource
    Statement clickHouseStatement;

    @Test
    void contextLoads() {

    }

    @Test
    public void testClickHouse() {
        try {
            ResultSet rs = clickHouseStatement.executeQuery("select count(*) from tutorial.hits_v1");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
