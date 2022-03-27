package asalty.fish.iotbigdata.demo.dao;

import asalty.fish.clickhousejpa.annotation.ClickHouseRepository;
import asalty.fish.iotbigdata.demo.entity.IotEntity2;
import asalty.fish.iotbigdata.demo.entity.TestCreateTable;

import java.util.List;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/27 16:28
 */
@ClickHouseRepository(entity = IotEntity2.class)
public class IotEntity2Dao {

    public void batchCreate(List<IotEntity2> testCreateTableList) {
        return;
    }

    public List<IotEntity2> findAllByDeviceId(Long watchID) {
        return null;
    }

    public void create(IotEntity2 testCreateTable) {
        return;
    }

    public Long maxValue1() {
        return null;
    }
}
