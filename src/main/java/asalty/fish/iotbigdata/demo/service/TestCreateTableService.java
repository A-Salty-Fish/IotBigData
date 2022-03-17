package asalty.fish.iotbigdata.demo.service;

import asalty.fish.iotbigdata.demo.dao.TestCreateTableDao;
import asalty.fish.iotbigdata.demo.entity.TestCreateTable;
import asalty.fish.iotbigdata.proxy.insert.InsertProxy;
import asalty.fish.iotbigdata.proxy.read.ReadProxy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/16 17:08
 */
@Service
public class TestCreateTableService {

    @Resource
    private TestCreateTableDao testCreateTableDao;

    @InsertProxy
    public void create(TestCreateTable testCreateTable) {
        testCreateTableDao.create(testCreateTable);
    }

    @InsertProxy
    public void batchCreate(List<TestCreateTable> testCreateTables) {
        testCreateTableDao.batchCreate(testCreateTables);
    }

    @ReadProxy
    public String read(String id) {
        return "test" + id;
    }
}
