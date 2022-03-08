package asalty.fish.iotbigdata.geoHash;

import com.google.common.geometry.S2Cell;
import com.google.common.geometry.S2CellId;
import com.google.common.geometry.S2LatLng;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/3 13:22
 */

public class geoHashTest {

    @Test
    public void testGetDist() {
        S2LatLng latLng1 = S2LatLng.fromDegrees(114.372921,30.543803);
        S2LatLng latLng2 = S2LatLng.fromDegrees(114.363463,30.541757);
        System.out.println(latLng1.getEarthDistance(latLng2));
    }

    int parentLevel = 5;

    @Test
    public void getCellId() {
        for (int i = 1; i <= 30; i++) {
            System.out.println("level " + i + ":");
            S2CellId cellId1 = S2CellId.fromLatLng(S2LatLng.fromDegrees(30.538655,114.368005)).parent(i);
            S2CellId cellId2 = S2CellId.fromLatLng(S2LatLng.fromDegrees(30.538636, 114.368067)).parent(i);
            System.out.println(cellId1.toToken());
            System.out.println(cellId2.toToken());
        }
    }

    @Test
    public void getLonLat( ) {
        S2CellId cellId1 = S2CellId.fromLatLng(S2LatLng.fromDegrees(30.543803,114.372921)).parent(13);
        System.out.println(cellId1.toToken());
        S2CellId cellId = S2CellId.fromToken(cellId1.toToken()).parent(13);
        System.out.println(cellId.toLatLng().lng().degrees());
        System.out.println(cellId.toLatLng().lat().degrees());
    }

    @Test
    public void testCell() {
        S2Cell cell = new S2Cell(S2CellId.fromLatLng(S2LatLng.fromDegrees(30.543803,114.372921)).parent(13));
        System.out.println(cell.getCenter().toDegreesString());
    }

    @Test
    public void testIdChange() {
        S2CellId cellId1 = S2CellId.fromLatLng(S2LatLng.fromDegrees(30.543803,114.372921)).parent(13);
        System.out.println(cellId1.id());
        S2CellId cellId = new S2CellId(cellId1.id());
        System.out.println(cellId.toLatLng().lng().degrees());
        System.out.println(cellId.toLatLng().lat().degrees());
    }

    @Test
    public void testNearBy() {
        S2CellId cellId1 = S2CellId.fromLatLng(S2LatLng.fromDegrees(30.543803,114.372921)).parent(13);
        List<S2CellId> list = new ArrayList<>();
        cellId1.getAllNeighbors(13, list);
        for (S2CellId cellId : list) {
            System.out.println(cellId.toLatLng().lng().degrees());
            System.out.println(cellId.toLatLng().lat().degrees());
        }
    }

    @Test
    public void getCellIdBit() {
        for (int i = 1; i <= 30; i++) {
            System.out.println("level " + i + ":");
            S2CellId cellId1 = S2CellId.fromLatLng(S2LatLng.fromDegrees(30.538655,114.368005)).parent(i);
            S2CellId cellId2 = S2CellId.fromLatLng(S2LatLng.fromDegrees(30.538636, 114.368067)).parent(i);
            System.out.println(Long.toBinaryString(cellId1.id()));
            System.out.println(Long.toBinaryString(cellId2.id()));
            if (!Long.toBinaryString(cellId1.id()).equals(Long.toBinaryString(cellId2.id()))) {
                System.out.println("not same");
                System.out.println(Long.toBinaryString(cellId2.id() ^ cellId1.id()));
            }
            System.out.println();
        }
    }


}
