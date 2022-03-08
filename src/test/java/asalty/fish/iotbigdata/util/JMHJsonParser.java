package asalty.fish.iotbigdata.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/8 13:24
 */

public class JMHJsonParser {
    public static String reader(String filePath) {
        JsonParser parse = new JsonParser();  //创建json解析器
        try {
            Object json = parse.parse(new FileReader(filePath));  //创建jsonObject string
            return json.toString();// 对象
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<String, HashMap<String, String>> getScoreMap() {
        JsonArray o = new Gson().fromJson(reader("E:\\clickhouse-jpa\\jmh-result-insert.json"), JsonArray.class);
        HashMap<String, HashMap<String, String>> map = new HashMap<>();
        for (int i = 0; i < o.size(); i++) {
            JsonObject jsonObject = o.get(i).getAsJsonObject();
            String entireFunctionName = jsonObject.get("benchmark").getAsString();
            String[] split = entireFunctionName.split("\\.");
            String functionName = split[split.length - 1];
            split = functionName.split("BatchInsertBy");
            JsonObject primaryMetric = jsonObject.get("primaryMetric").getAsJsonObject();
            String score = primaryMetric.get("score").getAsString();
//            System.out.println(split[1] + "\t" + split[0] + "\t" + score);
            String dbName = split[0];
            String batchSize = split[1];
            HashMap<String, String> hashMap = map.get(batchSize);
            if (hashMap == null) {
                hashMap = new HashMap<>();
            }
            hashMap.put(dbName, score);
            map.put(batchSize, hashMap);
        }
        return map;
    }

    @Test
    public void getScore() {
        HashMap<String, HashMap<String, String>> map = getScoreMap();
        List<String> batchSizes = map.keySet().stream().sorted(Comparator.comparing(Long::valueOf)).collect(Collectors.toList());
        for (String batchSize : batchSizes) {
            String mysqlScore = map.get(batchSize).get("Mysql");
            String clickHouseScore = map.get(batchSize).get("ClickHouse");
            String ESScore = map.get(batchSize).get("Es");
            System.out.println(batchSize + "\t" + mysqlScore + "\t" + clickHouseScore + "\t" + ESScore);
        }
    }
}