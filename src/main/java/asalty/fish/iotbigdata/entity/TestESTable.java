package asalty.fish.iotbigdata.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/7 17:19
 */
@Document(indexName = "test_es_table")
@Data
public class TestESTable {
    @Id
    private String id;

    public Long WatchID;

    public Boolean JavaEnable;

    public String Title;

    public String GoodEvent;

    public Integer UserAgentMajor;

    public String testUserDefinedColumn;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Long CreateTime;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Long CreateDay;
}
