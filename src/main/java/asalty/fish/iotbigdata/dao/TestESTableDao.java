package asalty.fish.iotbigdata.dao;

import asalty.fish.iotbigdata.entity.TestESTable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestESTableDao extends ElasticsearchRepository<TestESTable, String> {
}
