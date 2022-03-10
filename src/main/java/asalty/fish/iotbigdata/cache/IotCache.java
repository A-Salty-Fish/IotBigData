package asalty.fish.iotbigdata.cache;

public interface IotCache {

    public <T> T getByString(String key, Class<T> clazz) throws IotCacheException;

    public <T> T putByString(String key, T value) throws IotCacheException;

    public void removeByString(String key);

    public void init();
}
