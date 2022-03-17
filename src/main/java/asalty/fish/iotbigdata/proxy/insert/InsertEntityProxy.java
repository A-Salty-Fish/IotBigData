package asalty.fish.iotbigdata.proxy.insert;

import asalty.fish.clickhousejpa.exception.TypeNotSupportException;
import asalty.fish.iotbigdata.wal.WalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/16 16:29
 */
@Service
public class InsertEntityProxy {


    @Resource
    WalService walService;

    public void handleBatchInsert(List<?> list) throws TypeNotSupportException {
        walService.insert(list);
    }

    public void handleSingleInsert(Object object) throws TypeNotSupportException {
        walService.insert(object);
    }

}
