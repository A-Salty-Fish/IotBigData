package asalty.fish.iotbigdata.demo.dao;

import asalty.fish.iotbigdata.demo.entity.TestMysqlTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/4 10:20
 */
@Repository
public interface TestMysqlTableDao extends JpaRepository<TestMysqlTable, Long> {


    @Query(value = "select max(watchid) from test_mysql_table where ((watchid ^ :watchId) >> :bit) = 0", nativeQuery = true)
    Long maxWatchIDByBit(@Param("watchId") Long watchID, @Param("bit") Long bit);

    @Query(value = "select avg(watchid) from test_mysql_table where user_agent_major between :left and :right", nativeQuery = true)
    Long avgWatchIDBetweenLeftAndRight(@Param("left") Integer left, @Param("right") Integer right);
}
