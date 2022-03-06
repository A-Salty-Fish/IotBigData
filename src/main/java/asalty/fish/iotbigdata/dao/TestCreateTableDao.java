package asalty.fish.iotbigdata.dao;

import asalty.fish.clickhousejpa.annotation.ClickHouseNativeQuery;
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

    public Long maxWatchID() {
        return null;
    }

    public Double avgWatchID() {
        return null;
    }

    public void batchCreate(List<TestCreateTable> testCreateTableList) {
        return;
    }

    public Long maxWatchIDByStartsWithGoodEvent(String GoodEvent) {
        return null;
    }

    @ClickHouseNativeQuery("SELECT max(WatchID) from test_create_table where bitShiftRight(bitXor(WatchID, ?), ?) = 0;")
    public Long maxWatchIDByBit(Long WatchID, Long bit) {
        return null;
    }

    @ClickHouseNativeQuery("SELECT avg(WatchID) from test_create_table where UserAgentMajor between ? and ?")
    public Long avgWatchIDBetweenLeftAndRight(Integer left, Integer right) {
        return null;
    }
}
