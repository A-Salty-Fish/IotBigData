package asalty.fish.iotbigdata.demo.service;

import asalty.fish.iotbigdata.demo.dao.IotEntity2Dao;
import asalty.fish.iotbigdata.demo.entity.IotEntity2;
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
 * @date 2022/3/27 16:30
 */
@Service
public class IotEntity2Service {

    @Resource
    IotEntity2Dao iotEntity2Dao;

    @InsertProxy
    public void create(IotEntity2 iotEntity2) {
        iotEntity2Dao.create(iotEntity2);
    }

    @InsertProxy
    public void batchCreate(List<IotEntity2> iotEntity2s) {
        System.out.println(Long.MAX_VALUE);
        iotEntity2Dao.batchCreate(iotEntity2s);
    }

    @ReadProxy
    public Long maxValue1() {
        return iotEntity2Dao.maxValue1();
    }
}
