package asalty.fish.iotbigdata.proxy;

import asalty.fish.iotbigdata.demo.dao.StatisticEsEntityDao;
import asalty.fish.iotbigdata.demo.entity.StatisticEsEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Random;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/18 16:05
 */
@SpringBootTest
public class StatisticsProxyTest {

    private StatisticEsEntity generateStatisticsTestEntity() {
        StatisticEsEntity statisticEsEntity = new StatisticEsEntity();
        Random rand = new Random();
        Long base = 10000L;
        statisticEsEntity.setEntityName("test_entity");
        statisticEsEntity.setPropertyName("test_property");
        statisticEsEntity.setAvg(rand.nextDouble() * base);
        statisticEsEntity.setMax(statisticEsEntity.getAvg() + base / 2);
        statisticEsEntity.setMin(statisticEsEntity.getAvg() - base / 2);
        statisticEsEntity.setSum(statisticEsEntity.getAvg() * base);
        statisticEsEntity.setGeoLevel(20);
        statisticEsEntity.setGeoHash(0L);
        statisticEsEntity.setBeginDate(LocalDate.now());
        statisticEsEntity.setEndDate(LocalDate.now());
        return statisticEsEntity;
    }

    @Resource
    StatisticEsEntityDao statisticEsEntityDao;

    @Test
    public void generateStatisticsTestData() throws Exception {
//        statisticEsEntityDao.deleteAll();
        for (int i = 0; i < 100; i++) {
            StatisticEsEntity statisticEsEntity = generateStatisticsTestEntity();
            statisticEsEntity.setDeviceId((long) i);
            statisticEsEntityDao.save(statisticEsEntity);
        }
    }

}
