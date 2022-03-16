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
    private Long DeviceId;

    private Long Value1;

    private Long Value2;

    private Double Value3;

    private Double Value4;

    // 上报时间
    private LocalDateTime UpdateDateTime;

    // 设备地理位置
    private Long GeoHash;
}
