package asalty.fish.iotbigdata.demo.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.time.LocalDate;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/18 15:23
 */
@Document(indexName = "statistic_es_entity")
@Data
public class StatisticEsEntity {

    @Id
    private String id;

    private String entityName;

    private Long deviceId;

    private String propertyName;

    private Double max;

    private Double min;

    private Double avg;

    private Double sum;

    private LocalDate beginDate;

    private LocalDate endDate;

    private Long geoHash;

    private Integer geoLevel;
}
