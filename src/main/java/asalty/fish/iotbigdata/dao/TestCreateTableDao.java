package asalty.fish.iotbigdata.dao;

import asalty.fish.clickhousejpa.annotation.ClickHouseRepository;
import asalty.fish.iotbigdata.entity.TestCreateTable;

import java.util.List;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/2 21:46
 */
@ClickHouseRepository(entity = TestCreateTable.class)
public class TestCreateTableDao {

    public List<TestCreateTable> findAllByWatchID(Long watchID) {
        return null;
    }

    public void create(TestCreateTable testCreateTable) {
        return;
    }
}
