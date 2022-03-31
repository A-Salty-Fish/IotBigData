package asalty.fish.iotbigdata.demo.entity;

import asalty.fish.clickhousejpa.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/31 15:17
 */
@Data
@ClickHouseEntity
@ClickHouseTable(name = "TestAutoTimeEntity", engine = ClickHouseEngine.MergeTree)
@ClickHouseTimeColumns
@ClickHouseGeoColumns
public class TestAutoTimeEntity {
    @ClickHouseColumn(isPrimaryKey = true)
    public Long id;

    @ClickHouseColumn(comment = "观看id")
    public Long WatchID;

    public Boolean JavaEnable;

    @ClickHouseColumn(comment = "标题")
    public String Title;

    public String GoodEvent;

    public Integer UserAgentMajor;

    @ClickHouseColumn(name = "URLDomain")
    public String testUserDefinedColumn;

    public LocalDateTime CreateTime;

    public LocalDate CreateDay;
}
