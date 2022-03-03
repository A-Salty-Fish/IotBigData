package asalty.fish.iotbigdata;

import asalty.fish.iotbigdata.dao.TestCreateTableDao;
import asalty.fish.iotbigdata.entity.TestCreateTable;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class IotBigDataApplicationTests {

    @Resource
    TestCreateTableDao testCreateTableDao;

    @Test
    void contextLoads() {
        System.out.println(testCreateTableDao.findAllByWatchID(0L).size());
    }

    public TestCreateTable getTestTimeEntity() {
        TestCreateTable h = new TestCreateTable();
        h.setId(0L);
        h.setGoodEvent("goodEvent");
        h.setJavaEnable(false);
        h.setTitle("title");
        h.setWatchID(1L);
        h.setUserAgentMajor(222);
        h.setTestUserDefinedColumn("7777");
        h.setCreateDay(LocalDate.now());
        h.setCreateTime(LocalDateTime.now());
        return h;
    }

    @Test
    void testCreateTable() {
        testCreateTableDao.create(getTestTimeEntity());
    }
}
