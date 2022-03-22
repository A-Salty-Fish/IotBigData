package asalty.fish.iotbigdata.demo.dao;

import asalty.fish.iotbigdata.demo.entity.StatisticEsEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticEsEntityDao extends ElasticsearchRepository<StatisticEsEntity, String> {

}
