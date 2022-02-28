package asalty.fish.iotbigdata.mapper.dao;

import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/2/28 14:50
 */
@Configuration
public class ClickHouseDao {
    ConcurrentHashMap<String, HashMap<String, Integer>> columnNameMap = new ConcurrentHashMap<>(16);

    public void addColumnName(String tableName, String columnName, Integer columnIndex) {
        HashMap<String, Integer> columnNameMap = this.columnNameMap.get(tableName);
        if (columnNameMap == null) {
            columnNameMap = new HashMap<>(16);
            this.columnNameMap.put(tableName, columnNameMap);
        }
        columnNameMap.put(columnName, columnIndex);
    }

    public Integer getColumnIndex(String tableName, String columnName) {
        HashMap<String, Integer> columnNameMap = this.columnNameMap.get(tableName);
        if (columnNameMap == null) {
            return null;
        }
        return columnNameMap.get(columnName);
    }

    public void addColumnName(String tableName, ResultSet resultSet) throws Exception {
        int columnCount = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = resultSet.getMetaData().getColumnName(i);
            addColumnName(tableName, columnName, i);
        }
    }

    public <T> T convertResultSetToClass(ResultSet rs, Class<T> clazz) {
        String tableName = clazz.getSimpleName();
        if (columnNameMap.get(tableName) == null) {
            try {
                addColumnName(tableName, rs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            T t = clazz.newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                String columnName = field.getName();
                Integer columnIndex = columnNameMap.get(tableName).get(columnName);
                String methodName = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                Class<?> type = field.getType();
                Method method = clazz.getMethod(methodName, type);
                convertAndSetStringToOtherType(t, type, method, rs.getString(columnIndex));
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将 string 转换为对应的类型
     */
    public void convertAndSetStringToOtherType(Object t, Class type, Method method, String value) {
        try {
            if (type == String.class) {
                method.invoke(t, value);
            } else if (type == Long.class) {
                method.invoke(t, Long.valueOf(value));
            } else if (type == Boolean.class) {
                method.invoke(t, Boolean.valueOf(value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}