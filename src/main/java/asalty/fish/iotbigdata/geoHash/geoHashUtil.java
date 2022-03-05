package asalty.fish.iotbigdata.geoHash;

import com.google.common.geometry.S2CellId;
import com.google.common.geometry.S2LatLng;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/3 13:22
 */
@Service
public class geoHashUtil {

    /**
     * 默认精度
     */
    @Value("${google.s2.level}")
    private int level;

    /**
     * 获得默认精度下的GeoHash字符串
     *
     * @param lat
     * @param lng
     * @return
     */
    public String getS2Token(double lat, double lng) {
        return getS2Token(lat, lng, level);
    }

    /**
     * 获得给定精度下的GeoHash字符串
     *
     * @param lat
     * @param lng
     * @return
     */
    public String getS2Token(double lat, double lng, int newLevel) {
        S2CellId cellId = S2CellId.fromLatLng(S2LatLng.fromDegrees(lat, lng)).parent(newLevel);
        return cellId.toToken();
    }

    /**
     * 从GeoHash字符串中以某个精度获得经纬度
     *
     * @param token
     * @param newLevel
     * @return
     */
    public Double[] getLonLatLngFromToken(String token, int newLevel) {
        S2CellId cellId = S2CellId.fromToken(token).parent(newLevel);
        S2LatLng latLng = cellId.toLatLng();
        Double[] lonLat = new Double[2];
        lonLat[0] = latLng.lng().degrees();
        lonLat[1] = latLng.lat().degrees();
        return lonLat;
    }

    /**
     * 从GeoHash字符串中以默认精度获得经纬度
     *
     * @param token
     * @return
     */
    public Double[] getLonLatLngFromToken(String token) {
        return getLonLatLngFromToken(token, level);
    }

    /**
     * 以某个精度获取方形范围内的9块区域的GeoHash字符串
     *
     * @param lat
     * @param lng
     * @param newLevel
     * @return
     */
    public List<String> getNearByTokens(double lat, double lng, int newLevel) {
        S2CellId cellId = S2CellId.fromLatLng(S2LatLng.fromDegrees(lat, lng)).parent(newLevel);
        List<S2CellId> cellIds = new ArrayList<>(9);
        cellId.getAllNeighbors(newLevel, cellIds);
        cellIds.add(cellId);
        List<String> tokens = new ArrayList<>(9);
        for (S2CellId id : cellIds) {
            tokens.add(id.toToken());
        }
        return tokens;
    }

    /**
     * 以默认精度获取方形范围内的9块区域的GeoHash字符串
     *
     * @param lat
     * @param lng
     * @return
     */
    public List<String> getNearByTokens(double lat, double lng) {
        return getNearByTokens(lat, lng, level);
    }

    /**
     * 返回两个点的距离，单位：m
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public double getDistance(double lat1, double lng1, double lat2, double lng2) {
        S2LatLng latLng1 = S2LatLng.fromDegrees(lat1, lng1);
        S2LatLng latLng2 = S2LatLng.fromDegrees(lat2, lng2);
        return latLng1.getEarthDistance(latLng2);
    }
}
