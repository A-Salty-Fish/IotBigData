package asalty.fish.iotbigdata.util;

import com.google.gson.Gson;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/10 15:15
 */

public class ThreadLocalGson {
    public static ThreadLocal<Gson> threadLocalGson = ThreadLocal.withInitial(Gson::new);
}
