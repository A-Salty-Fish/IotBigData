package asalty.fish.iotbigdata.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/4 10:16
 */
@Entity
@Table
@Data
public class TestMysqlTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long WatchID;

    public Boolean JavaEnable;

    public String Title;

    public String GoodEvent;

    public Integer UserAgentMajor;

    public String testUserDefinedColumn;

    public LocalDateTime CreateTime;

    public LocalDate CreateDay;
}
