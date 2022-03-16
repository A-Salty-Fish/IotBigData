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
 * @date 2022/3/10 14:51
 */
@ClickHouseEntity
@Data
@ClickHouseTable(name = "iot_entity_1", engine = ClickHouseEngine.MergeTree)
public class IotEntity1 {
    // 设备编号
    @ClickHouseColumn(isPrimaryKey = true)
    private Long DeviceId;
    // 设备属性名
    private String PropertyName;
    // 设备属性值
    private Long Value;
    // 上报时间
    private LocalDateTime UpdateDateTime;
    // 设备地理位置
    private Long GeoHash;
}
