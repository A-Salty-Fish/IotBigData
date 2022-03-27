package asalty.fish.iotbigdata.demo.entity;

import asalty.fish.clickhousejpa.annotation.ClickHouseColumn;
import asalty.fish.clickhousejpa.annotation.ClickHouseEngine;
import asalty.fish.clickhousejpa.annotation.ClickHouseEntity;
import asalty.fish.clickhousejpa.annotation.ClickHouseTable;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/16 15:55
 */
@ClickHouseEntity
@Data
@ClickHouseTable(name = "iot_entity_2", engine = ClickHouseEngine.MergeTree)
public class IotEntity2 {
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
