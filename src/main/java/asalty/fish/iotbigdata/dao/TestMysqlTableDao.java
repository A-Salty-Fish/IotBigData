package asalty.fish.iotbigdata.dao;

import asalty.fish.iotbigdata.entity.TestMysqlTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/4 10:20
 */
@Repository
public interface TestMysqlTableDao extends JpaRepository<TestMysqlTable, Long> {

}
