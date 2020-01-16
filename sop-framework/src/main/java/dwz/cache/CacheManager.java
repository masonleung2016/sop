package dwz.cache;

import java.util.Date;
import java.util.Map;

/**
 * <strong>Title : ICacheManager<br></strong>
 * <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2011-12-9<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 *
 * @author peng.shi peng.shi@ecointellects.com<br>
 * @version <strong>Ecointel v1.0.0</strong><br>
 * <br>
 * <strong>修改历史:</strong><br>
 * 修改人		修改日期		修改描述<br>
 * -------------------------------------------<br>
 * <br>
 * <br>
 */

public interface CacheManager {

    /**
     * Checks to see if key exists in cache.
     *
     * @param key the key to look for
     * @return true if key found in cache, false if not (or if cache is down)
     */
    public boolean keyExists(String key);

    /**
     * Deletes an object from cache given cache key.
     *
     * @param key the key to be removed
     * @return <code>true</code>, if the data was deleted successfully
     */
    public boolean delete(String key);

    /**
     * Deletes an object from cache given cache key and expiration date.
     *
     * @param key    the key to be removed
     * @param expiry when to expire the record.
     * @return <code>true</code>, if the data was deleted successfully
     */
    public boolean delete(String key, Date expiry);

    /**
     * Deletes an object from cache given cache key, a delete time, and an optional hashcode.
     * <p>
     * The item is immediately made non retrievable.<br/>
     * Keep in mind {@link #add(String, Object) add} and {@link #replace(String, Object) replace}<br/>
     * will fail when used with the same key will fail, until the server reaches the<br/>
     * specified time. However, {@link #set(String, Object) set} will succeed,<br/>
     * and the new value will not be deleted.
     *
     * @param key      the key to be removed
     * @param hashCode if not null, then the int hashcode to use
     * @param expiry   when to expire the record.
     * @return <code>true</code>, if the data was deleted successfully
     */
    public boolean delete(String key, Integer hashCode, Date expiry);

    /**
     * Stores data on the server; only the key and the value are specified.
     *
     * @param key   key to store data under
     * @param value value to store
     * @return true, if the data was successfully stored
     */
    public boolean set(String key, Object value);

    /**
     * Stores data on the server; only the key and the value are specified.
     *
     * @param key      key to store data under
     * @param value    value to store
     * @param hashCode if not null, then the int hashcode to use
     * @return true, if the data was successfully stored
     */
    public boolean set(String key, Object value, Integer hashCode);

    /**
     * Stores data on the server; the key, value, and an expiration time are specified.
     *
     * @param key    key to store data under
     * @param value  value to store
     * @param expiry when to expire the record
     * @return true, if the data was successfully stored
     */
    public boolean set(String key, Object value, Date expiry);

    /**
     * Stores data on the server; the key, value, and an expiration time are specified.
     *
     * @param key      key to store data under
     * @param value    value to store
     * @param expiry   when to expire the record
     * @param hashCode if not null, then the int hashcode to use
     * @return true, if the data was successfully stored
     */
    public boolean set(String key, Object value, Date expiry, Integer hashCode);

    /**
     * Adds data to the server; only the key and the value are specified.
     *
     * @param key   key to store data under
     * @param value value to store
     * @return true, if the data was successfully stored
     */
    public boolean add(String key, Object value);

    /**
     * Adds data to the server; the key, value, and an optional hashcode are passed in.
     *
     * @param key      key to store data under
     * @param value    value to store
     * @param hashCode if not null, then the int hashcode to use
     * @return true, if the data was successfully stored
     */
    public boolean add(String key, Object value, Integer hashCode);

    /**
     * Adds data to the server; the key, value, and an expiration time are specified.
     *
     * @param key    key to store data under
     * @param value  value to store
     * @param expiry when to expire the record
     * @return true, if the data was successfully stored
     */
    public boolean add(String key, Object value, Date expiry);

    /**
     * Adds data to the server; the key, value, and an expiration time are specified.
     *
     * @param key      key to store data under
     * @param value    value to store
     * @param expiry   when to expire the record
     * @param hashCode if not null, then the int hashcode to use
     * @return true, if the data was successfully stored
     */
    public boolean add(String key, Object value, Date expiry, Integer hashCode);

    /**
     * Updates data on the server; only the key and the value are specified.
     *
     * @param key   key to store data under
     * @param value value to store
     * @return true, if the data was successfully stored
     */
    public boolean replace(String key, Object value);

    /**
     * Updates data on the server; only the key and the value and an optional hash are specified.
     *
     * @param key      key to store data under
     * @param value    value to store
     * @param hashCode if not null, then the int hashcode to use
     * @return true, if the data was successfully stored
     */
    public boolean replace(String key, Object value, Integer hashCode);

    /**
     * Updates data on the server; the key, value, and an expiration time are specified.
     *
     * @param key    key to store data under
     * @param value  value to store
     * @param expiry when to expire the record
     * @return true, if the data was successfully stored
     */
    public boolean replace(String key, Object value, Date expiry);

    /**
     * Updates data on the server; the key, value, and an expiration time are specified.
     *
     * @param key      key to store data under
     * @param value    value to store
     * @param expiry   when to expire the record
     * @param hashCode if not null, then the int hashcode to use
     * @return true, if the data was successfully stored
     */
    public boolean replace(String key, Object value, Date expiry, Integer hashCode);

    /**
     * Store a counter to memcached given a key
     *
     * @param key     cache key
     * @param counter number to store
     * @return true/false indicating success
     */
    public boolean storeCounter(String key, long counter);

    /**
     * Store a counter to memcached given a key
     *
     * @param key     cache key
     * @param counter number to store
     * @return true/false indicating success
     */
    public boolean storeCounter(String key, Long counter);

    /**
     * Store a counter to memcached given a key
     *
     * @param key      cache key
     * @param counter  number to store
     * @param hashCode if not null, then the int hashcode to use
     * @return true/false indicating success
     */
    public boolean storeCounter(String key, Long counter, Integer hashCode);

    /**
     * Returns value in counter at given key as long.
     *
     * @param key cache ket
     * @return counter value or -1 if not found
     */
    public long getCounter(String key);

    /**
     * Returns value in counter at given key as long.
     *
     * @param key      cache ket
     * @param hashCode if not null, then the int hashcode to use
     * @return counter value or -1 if not found
     */
    public long getCounter(String key, Integer hashCode);

    /**
     * Thread safe way to initialize and increment a counter.
     *
     * @param key key where the data is stored
     * @return value of incrementer
     */
    public long addOrIncr(String key);

    /**
     * Thread safe way to initialize and increment a counter.
     *
     * @param key key where the data is stored
     * @param inc value to set or increment by
     * @return value of incrementer
     */
    public long addOrIncr(String key, long inc);

    /**
     * Thread safe way to initialize and increment a counter.
     *
     * @param key      key where the data is stored
     * @param inc      value to set or increment by
     * @param hashCode if not null, then the int hashcode to use
     * @return value of incrementer
     */
    public long addOrIncr(String key, long inc, Integer hashCode);

    /**
     * Thread safe way to initialize and decrement a counter.
     *
     * @param key key where the data is stored
     * @return value of incrementer
     */
    public long addOrDecr(String key);

    /**
     * Thread safe way to initialize and decrement a counter.
     *
     * @param key key where the data is stored
     * @param inc value to set or increment by
     * @return value of incrementer
     */
    public long addOrDecr(String key, long inc);

    /**
     * Thread safe way to initialize and decrement a counter.
     *
     * @param key      key where the data is stored
     * @param inc      value to set or increment by
     * @param hashCode if not null, then the int hashcode to use
     * @return value of incrementer
     */
    public long addOrDecr(String key, long inc, Integer hashCode);

    /**
     * Increment the value at the specified key by 1, and then return it.
     *
     * @param key key where the data is stored
     * @return -1, if the key is not found, the value after incrementing otherwise
     */
    public long incr(String key);

    /**
     * Increment the value at the specified key by passed in val.
     *
     * @param key key where the data is stored
     * @param inc how much to increment by
     * @return -1, if the key is not found, the value after incrementing otherwise
     */
    public long incr(String key, long inc);

    /**
     * Increment the value at the specified key by the specified increment, and then return it.
     *
     * @param key      key where the data is stored
     * @param inc      how much to increment by
     * @param hashCode if not null, then the int hashcode to use
     * @return -1, if the key is not found, the value after incrementing otherwise
     */
    public long incr(String key, long inc, Integer hashCode);

    /**
     * Decrement the value at the specified key by 1, and then return it.
     *
     * @param key key where the data is stored
     * @return -1, if the key is not found, the value after incrementing otherwise
     */
    public long decr(String key);

    /**
     * Decrement the value at the specified key by passed in value, and then return it.
     *
     * @param key key where the data is stored
     * @param inc how much to increment by
     * @return -1, if the key is not found, the value after incrementing otherwise
     */
    public long decr(String key, long inc);

    /**
     * Decrement the value at the specified key by the specified increment, and then return it.
     *
     * @param key      key where the data is stored
     * @param inc      how much to increment by
     * @param hashCode if not null, then the int hashcode to use
     * @return -1, if the key is not found, the value after incrementing otherwise
     */
    public long decr(String key, long inc, Integer hashCode);

    /**
     * Retrieve a key from the server, using a specific hash.
     * <p>
     * If the data was compressed or serialized when compressed, it will automatically<br/>
     * be decompressed or serialized, as appropriate. (Inclusive or)<br/>
     * <br/>
     * Non-serialized data will be returned as a string, so explicit conversion to<br/>
     * numeric types will be necessary, if desired<br/>
     *
     * @param key key where data is stored
     * @return the object that was previously stored, or null if it was not previously stored
     */
    public Object get(String key);

    /**
     * Retrieve a key from the server, using a specific hash.
     * <p>
     * If the data was compressed or serialized when compressed, it will automatically<br/>
     * be decompressed or serialized, as appropriate. (Inclusive or)<br/>
     * <br/>
     * Non-serialized data will be returned as a string, so explicit conversion to<br/>
     * numeric types will be necessary, if desired<br/>
     *
     * @param key      key where data is stored
     * @param hashCode if not null, then the int hashcode to use
     * @return the object that was previously stored, or null if it was not previously stored
     */
    public Object get(String key, Integer hashCode);

    /**
     * Retrieve a key from the server, using a specific hash.
     * <p>
     * If the data was compressed or serialized when compressed, it will automatically<br/>
     * be decompressed or serialized, as appropriate. (Inclusive or)<br/>
     * <br/>
     * Non-serialized data will be returned as a string, so explicit conversion to<br/>
     * numeric types will be necessary, if desired<br/>
     *
     * @param key      key where data is stored
     * @param hashCode if not null, then the int hashcode to use
     * @param asString if true, then return string val
     * @return the object that was previously stored, or null if it was not previously stored
     */
    public Object get(String key, Integer hashCode, boolean asString);

    /**
     * Retrieve multiple objects from the memcache.
     * <p>
     * This is recommended over repeated calls to {@link #get(String) get()}, since it<br/>
     * is more efficient.<br/>
     *
     * @param keys String array of keys to retrieve
     * @return Object array ordered in same order as key array containing results
     */
    public Object[] getMultiArray(String[] keys);

    /**
     * Retrieve multiple objects from the memcache.
     * <p>
     * This is recommended over repeated calls to {@link #get(String) get()}, since it<br/>
     * is more efficient.<br/>
     *
     * @param keys      String array of keys to retrieve
     * @param hashCodes if not null, then the Integer array of hashCodes
     * @return Object array ordered in same order as key array containing results
     */
    public Object[] getMultiArray(String[] keys, Integer[] hashCodes);

    /**
     * Retrieve multiple objects from the memcache.
     * <p>
     * This is recommended over repeated calls to {@link #get(String) get()}, since it<br/>
     * is more efficient.<br/>
     *
     * @param keys      String array of keys to retrieve
     * @param hashCodes if not null, then the Integer array of hashCodes
     * @param asString  if true, retrieve string vals
     * @return Object array ordered in same order as key array containing results
     */
    public Object[] getMultiArray(String[] keys, Integer[] hashCodes, boolean asString);

    /**
     * Retrieve multiple objects from the memcache.
     * <p>
     * This is recommended over repeated calls to {@link #get(String) get()}, since it<br/>
     * is more efficient.<br/>
     *
     * @param keys String array of keys to retrieve
     * @return a hashmap with entries for each key is found by the server,
     * keys that are not found are not entered into the hashmap, but attempting to
     * retrieve them from the hashmap gives you null.
     */
    public Map<String, Object> getMulti(String[] keys);

    /**
     * Retrieve multiple keys from the memcache.
     * <p>
     * This is recommended over repeated calls to {@link #get(String) get()}, since it<br/>
     * is more efficient.<br/>
     *
     * @param keys      keys to retrieve
     * @param hashCodes if not null, then the Integer array of hashCodes
     * @return a hashmap with entries for each key is found by the server,
     * keys that are not found are not entered into the hashmap, but attempting to
     * retrieve them from the hashmap gives you null.
     */
    public Map<String, Object> getMulti(String[] keys, Integer[] hashCodes);

    /**
     * Retrieve multiple keys from the memcache.
     * <p>
     * This is recommended over repeated calls to {@link #get(String) get()}, since it<br/>
     * is more efficient.<br/>
     *
     * @param keys      keys to retrieve
     * @param hashCodes if not null, then the Integer array of hashCodes
     * @param asString  if true then retrieve using String val
     * @return a hashmap with entries for each key is found by the server,
     * keys that are not found are not entered into the hashmap, but attempting to
     * retrieve them from the hashmap gives you null.
     */
    public Map<String, Object> getMulti(String[] keys, Integer[] hashCodes, boolean asString);

    /**
     * Invalidates the entire cache.
     * <p>
     * Will return true only if succeeds in clearing all servers.
     *
     * @return success true/false
     */
    public boolean flushAll();

    /**
     * Invalidates the entire cache.
     * <p>
     * Will return true only if succeeds in clearing all servers.
     * If pass in null, then will try to flush all servers.
     *
     * @param servers optional array of host(s) to flush (host:port)
     * @return success true/false
     */
    public boolean flushAll(String[] servers);

    /**
     * Retrieves stats for all servers.
     * <p>
     * Returns a map keyed on the servername.
     * The value is another map which contains stats
     * with stat name as key and value as value.
     *
     * @return Stats map
     */
    public Map stats();

    /**
     * Retrieves stats for passed in servers (or all servers).
     * <p>
     * Returns a map keyed on the servername.
     * The value is another map which contains stats
     * with stat name as key and value as value.
     *
     * @param servers string array of servers to retrieve stats from, or all if this is null
     * @return Stats map
     */
    public Map stats(String[] servers);

    /**
     * Retrieves stats items for all servers.
     * <p>
     * Returns a map keyed on the servername.
     * The value is another map which contains item stats
     * with itemname:number:field as key and value as value.
     *
     * @return Stats map
     */
    public Map statsItems();

    /**
     * Retrieves stats for passed in servers (or all servers).
     * <p>
     * Returns a map keyed on the servername.
     * The value is another map which contains item stats
     * with itemname:number:field as key and value as value.
     *
     * @param servers string array of servers to retrieve stats from, or all if this is null
     * @return Stats map
     */
    public Map statsItems(String[] servers);

    /**
     * Retrieves stats items for all servers.
     * <p>
     * Returns a map keyed on the servername.
     * The value is another map which contains slabs stats
     * with slabnumber:field as key and value as value.
     *
     * @return Stats map
     */
    public Map statsSlabs();

    /**
     * Retrieves stats for passed in servers (or all servers).
     * 
     * Returns a map keyed on the servername.
     * The value is another map which contains slabs stats
     * with slabnumber:field as key and value as value.
     *
     * @param servers string array of servers to retrieve stats from, or all if this is null
     * @return Stats map
     */
    public Map statsSlabs(String[] servers);

    /**
     * Retrieves items cachedump for all servers.
     * 
     * Returns a map keyed on the servername.
     * The value is another map which contains cachedump stats
     * with the cachekey as key and byte size and unix timestamp as value.
     *
     * @param slabNumber the item number of the cache dump
     * @return Stats map
     */
    public Map statsCacheDump(int slabNumber, int limit);

    /**
     * Retrieves stats for passed in servers (or all servers).
     * 
     * Returns a map keyed on the servername.
     * The value is another map which contains cachedump stats
     * with the cachekey as key and byte size and unix timestamp as value.
     *
     * @param servers    string array of servers to retrieve stats from, or all if this is null
     * @param slabNumber the item number of the cache dump
     * @return Stats map
     */
    public Map statsCacheDump(String[] servers, int slabNumber, int limit);
}
