package asalty.fish.iotbigdata.demo.dao;

import asalty.fish.iotbigdata.demo.entity.TestESTable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestESTableDao extends ElasticsearchRepository<TestESTable, String> {
}
