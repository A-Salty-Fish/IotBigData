package asalty.fish.iotbigdata.demo.entity;

import asalty.fish.clickhousejpa.annotation.*;
import lombok.Data;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/31 15:17
 */
@Data
@ClickHouseEntity
@ClickHouseTable(name = "IotEntity_3", engine = ClickHouseEngine.MergeTree)
@ClickHouseTimeColumns(day = true, year = true, month = true)
@ClickHouseGeoColumns(level = 5)
public class IotEntity {
    // 设备编号
    @ClickHouseColumn(isPrimaryKey = true)
    public Long DeviceId;

    public Long Value1;

    public Long Value2;

    public Double Value3;

    public Double Value4;

    // 上报时间
    public Long UpdateDateTime;

    // 设备地理位置
    public Long GeoHash;
}
