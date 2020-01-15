/**
 * Copyright (c) 2008 Greg Whalin
 * All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the BSD license
 * <p>
 * This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * <p>
 * You should have received a copy of the BSD License along with this
 * library.
 *
 * @author Greg Whalin <greg@meetup.com>
 */

package dwz.cache.memcache.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.log4j.Logger;


/**
 * This is a Memcached client for the Java platform available from
 *  <a href="http:/www.danga.com/memcached/">http://www.danga.com/memcached/</a>.
 * <br/>
 * Supports setting, adding, replacing, deleting compressed/uncompressed and<br/>
 * serialized (can be stored as string if object is native class) objects to memcached.<br/>
 * <br/>
 * Now pulls SockIO objects from SockIOPool, which is a connection pool.  The server failover<br/>
 * has also been moved into the SockIOPool class.<br/>
 * This pool needs to be initialized prior to the client working.  See javadocs from SockIOPool.<br/>
 * <br/>
 * Some examples of use follow.<br/>
 * <h3>To create cache client object and set params:</h3>
 * <pre>
 *	MemcachedClient mc = new MemcachedClient();
 *
 *	// compression is enabled by default
 *	mc.setCompressEnable(true);
 *
 *	// set compression threshhold to 4 KB (default: 15 KB)
 *	mc.setCompressThreshold(4096);
 *
 *	// turn on storing primitive types as a string representation
 *	// Should not do this in most cases.
 *	mc.setPrimitiveAsString(true);
 * </pre>
 * <h3>To store an object:</h3>
 * <pre>
 *	MemcachedClient mc = new MemcachedClient();
 *	String key   = "cacheKey1";
 *	Object value = SomeClass.getObject();
 *	mc.set(key, value);
 * </pre>
 * <h3>To store an object using a custom server hashCode:</h3>
 * <pre>
 *	MemcachedClient mc = new MemcachedClient();
 *	String key   = "cacheKey1";
 *	Object value = SomeClass.getObject();
 *	Integer hash = new Integer(45);
 *	mc.set(key, value, hash);
 * </pre>
 * The set method shown above will always set the object in the cache.<br/>
 * The add and replace methods do the same, but with a slight difference.<br/>
 * <ul>
 * 	<li>add -- will store the object only if the server does not have an entry for this key</li>
 * 	<li>replace -- will store the object only if the server already has an entry for this key</li>
 * </ul>
 * <h3>To delete a cache entry:</h3>
 * <pre>
 *	MemcachedClient mc = new MemcachedClient();
 *	String key   = "cacheKey1";
 *	mc.delete(key);
 * </pre>
 * <h3>To delete a cache entry using a custom hash code:</h3>
 * <pre>
 *	MemcachedClient mc = new MemcachedClient();
 *	String key   = "cacheKey1";
 *	Integer hash = new Integer(45);
 *	mc.delete(key, hashCode);
 * </pre>
 * <h3>To store a counter and then increment or decrement that counter:</h3>
 * <pre>
 *	MemcachedClient mc = new MemcachedClient();
 *	String key   = "counterKey";
 *	mc.storeCounter(key, new Integer(100));
 *	System.out.println("counter after adding      1: " mc.incr(key));
 *	System.out.println("counter after adding      5: " mc.incr(key, 5));
 *	System.out.println("counter after subtracting 4: " mc.decr(key, 4));
 *	System.out.println("counter after subtracting 1: " mc.decr(key));
 * </pre>
 * <h3>To store a counter and then increment or decrement that counter with custom hash:</h3>
 * <pre>
 *	MemcachedClient mc = new MemcachedClient();
 *	String key   = "counterKey";
 *	Integer hash = new Integer(45);
 *	mc.storeCounter(key, new Integer(100), hash);
 *	System.out.println("counter after adding      1: " mc.incr(key, 1, hash));
 *	System.out.println("counter after adding      5: " mc.incr(key, 5, hash));
 *	System.out.println("counter after subtracting 4: " mc.decr(key, 4, hash));
 *	System.out.println("counter after subtracting 1: " mc.decr(key, 1, hash));
 * </pre>
 * <h3>To retrieve an object from the cache:</h3>
 * <pre>
 *	MemcachedClient mc = new MemcachedClient();
 *	String key   = "key";
 *	Object value = mc.get(key);
 * </pre>
 * <h3>To retrieve an object from the cache with custom hash:</h3>
 * <pre>
 *	MemcachedClient mc = new MemcachedClient();
 *	String key   = "key";
 *	Integer hash = new Integer(45);
 *	Object value = mc.get(key, hash);
 * </pre>
 * <h3>To retrieve an multiple objects from the cache</h3>
 * <pre>
 *	MemcachedClient mc = new MemcachedClient();
 *	String[] keys      = { "key", "key1", "key2" };
 *	Map&lt;Object&gt; values = mc.getMulti(keys);
 * </pre>
 * <h3>To retrieve an multiple objects from the cache with custom hashing</h3>
 * <pre>
 *	MemcachedClient mc = new MemcachedClient();
 *	String[] keys      = { "key", "key1", "key2" };
 *	Integer[] hashes   = { new Integer(45), new Integer(32), new Integer(44) };
 *	Map&lt;Object&gt; values = mc.getMulti(keys, hashes);
 * </pre>
 * <h3>To flush all items in server(s)</h3>
 * <pre>
 *	MemcachedClient mc = new MemcachedClient();
 *	mc.flushAll();
 * </pre>
 * <h3>To get stats from server(s)</h3>
 * <pre>
 *	MemcachedClient mc = new MemcachedClient();
 *	Map stats = mc.stats();
 * </pre>
 *
 * @author greg whalin <greg@meetup.com>
 * @author Richard 'toast' Russo <russor@msoe.edu>
 * @author Kevin Burton <burton@peerfear.org>
 * @author Robert Watts <robert@wattsit.co.uk>
 * @author Vin Chawla <vin@tivo.com>
 * @version 1.5
 */
public class MemcachedClient {

    // values for cache flags
    public static final int MARKER_BYTE = 1;
    public static final int MARKER_BOOLEAN = 8192;
    public static final int MARKER_INTEGER = 4;
    public static final int MARKER_LONG = 16384;
    public static final int MARKER_CHARACTER = 16;
    public static final int MARKER_STRING = 32;
    public static final int MARKER_STRINGBUFFER = 64;
    public static final int MARKER_FLOAT = 128;
    public static final int MARKER_SHORT = 256;
    public static final int MARKER_DOUBLE = 512;
    public static final int MARKER_DATE = 1024;
    public static final int MARKER_STRINGBUILDER = 2048;
    public static final int MARKER_BYTEARR = 4096;
    public static final int F_COMPRESSED = 2;
    public static final int F_SERIALIZED = 8;
    // return codes
    private static final String VALUE = "VALUE";            // start of value line from server
    private static final String STATS = "STAT";            // start of stats line from server
    private static final String ITEM = "ITEM";            // start of item line from server
    private static final String DELETED = "DELETED";        // successful deletion
    private static final String NOTFOUND = "NOT_FOUND";        // record not found for delete or incr/decr
    private static final String STORED = "STORED";        // successful store of data
    private static final String NOTSTORED = "NOT_STORED";    // data not stored
    private static final String OK = "OK";            // success
    private static final String END = "END";            // end of data from server
    private static final String ERROR = "ERROR";            // invalid command name from client
    private static final String CLIENT_ERROR = "CLIENT_ERROR";    // client error in input line - invalid protocol
    private static final String SERVER_ERROR = "SERVER_ERROR";    // server error
    private static final byte[] B_END = "END\r\n".getBytes();
    private static final byte[] B_NOTFOUND = "NOT_FOUND\r\n".getBytes();
    private static final byte[] B_DELETED = "DELETED\r\r".getBytes();
    private static final byte[] B_STORED = "STORED\r\r".getBytes();
    // default compression threshold
    private static final int COMPRESS_THRESH = 30720;
    // logger
    private static Logger log =
            Logger.getLogger(MemcachedClient.class.getName());
    // flags
    private boolean sanitizeKeys;
    private boolean primitiveAsString;
    private boolean compressEnable;
    private long compressThreshold;
    private String defaultEncoding;

    // pool instance
    private SockIOPool pool;

    // which pool to use
    private String poolName;

    // optional passed in classloader
    private ClassLoader classLoader;

    // optional error handler
    private ErrorHandler errorHandler;

    /**
     * Creates a new instance of MemCachedClient.
     */
    public MemcachedClient() {
        init();
    }

    /**
     * Creates a new instance of MemCachedClient
     * accepting a passed in pool name.
     *
     * @param poolName name of SockIOPool
     */
    public MemcachedClient(String poolName) {
        this.poolName = poolName;
        init();
    }

    /**
     * Creates a new instance of MemCacheClient but
     * acceptes a passed in ClassLoader.
     *
     * @param classLoader ClassLoader object.
     */
    public MemcachedClient(ClassLoader classLoader) {
        this.classLoader = classLoader;
        init();
    }

    /**
     * Creates a new instance of MemCacheClient but
     * acceptes a passed in ClassLoader and a passed
     * in ErrorHandler.
     *
     * @param classLoader ClassLoader object.
     * @param errorHandler ErrorHandler object.
     */
    public MemcachedClient(ClassLoader classLoader, ErrorHandler errorHandler) {
        this.classLoader = classLoader;
        this.errorHandler = errorHandler;
        init();
    }

    /**
     * Creates a new instance of MemCacheClient but
     * acceptes a passed in ClassLoader, ErrorHandler,
     * and SockIOPool name.
     *
     * @param classLoader ClassLoader object.
     * @param errorHandler ErrorHandler object.
     * @param poolName SockIOPool name
     */
    public MemcachedClient(ClassLoader classLoader, ErrorHandler errorHandler, String poolName) {
        this.classLoader = classLoader;
        this.errorHandler = errorHandler;
        this.poolName = poolName;
        init();
    }

    /**
     * Initializes client object to defaults.
     *
     * This enables compression and sets compression threshhold to 15 KB.
     */
    private void init() {
        this.sanitizeKeys = true;
        this.primitiveAsString = false;
        this.compressEnable = true;
        this.compressThreshold = COMPRESS_THRESH;
        this.defaultEncoding = "UTF-8";
        this.poolName = (this.poolName == null) ? "default" : this.poolName;

        // get a pool instance to work with for the life of this instance
        this.pool = SockIOPool.getInstance(poolName);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#setClassLoader(java.lang.ClassLoader)
     */
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#setErrorHandler(com.ecointel.cache.memcache.client.ErrorHandler)
     */
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#setSanitizeKeys(boolean)
     */
    public void setSanitizeKeys(boolean sanitizeKeys) {
        this.sanitizeKeys = sanitizeKeys;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#setPrimitiveAsString(boolean)
     */
    public void setPrimitiveAsString(boolean primitiveAsString) {
        this.primitiveAsString = primitiveAsString;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#setDefaultEncoding(java.lang.String)
     */
    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#setCompressEnable(boolean)
     */
    public void setCompressEnable(boolean compressEnable) {
        this.compressEnable = compressEnable;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#setCompressThreshold(long)
     */
    public void setCompressThreshold(long compressThreshold) {
        this.compressThreshold = compressThreshold;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#keyExists(java.lang.String)
     */
    public boolean keyExists(String key) {
        return (this.get(key, null, true) != null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#delete(java.lang.String)
     */
    public boolean delete(String key) {
        return delete(key, null, null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#delete(java.lang.String, java.util.Date)
     */
    public boolean delete(String key, Date expiry) {
        return delete(key, null, expiry);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#delete(java.lang.String, java.lang.Integer, java.util.Date)
     */
    public boolean delete(String key, Integer hashCode, Date expiry) {

        if (key == null) {
            log.error("null value for key passed to delete()");
            return false;
        }

        try {
            key = sanitizeKey(key);
        } catch (UnsupportedEncodingException e) {

            // if we have an errorHandler, use its hook
            if (errorHandler != null)
                errorHandler.handleErrorOnDelete(this, e, key);

            log.error("failed to sanitize your key!", e);
            return false;
        }

        // get SockIO obj from hash or from key
        SockIOPool.SockIO sock = pool.getSock(key, hashCode);

        // return false if unable to get SockIO obj
        if (sock == null) {
            if (errorHandler != null)
                errorHandler.handleErrorOnDelete(this, new IOException("no socket to server available"), key);
            return false;
        }

        // build command
        StringBuilder command = new StringBuilder("delete ").append(key);
        if (expiry != null)
            command.append(" " + expiry.getTime() / 1000);

        command.append("\r\n");

        try {
            sock.write(command.toString().getBytes());
            sock.flush();

            // if we get appropriate response back, then we return true
            String line = sock.readLine();
            if (DELETED.equals(line)) {
                if (log.isInfoEnabled())
                    log.info("++++ deletion of key: " + key + " from cache was a success");

                // return sock to pool and bail here
                sock.close();
                sock = null;
                return true;
            } else if (NOTFOUND.equals(line)) {
                if (log.isInfoEnabled())
                    log.info("++++ deletion of key: " + key + " from cache failed as the key was not found");
            } else {
                log.error("++++ error deleting key: " + key);
                log.error("++++ server response: " + line);
            }
        } catch (IOException e) {

            // if we have an errorHandler, use its hook
            if (errorHandler != null)
                errorHandler.handleErrorOnDelete(this, e, key);

            // exception thrown
            log.error("++++ exception thrown while writing bytes to server on delete");
            log.error(e.getMessage(), e);

            try {
                sock.trueClose();
            } catch (IOException ioe) {
                log.error("++++ failed to close socket : " + sock.toString());
            }

            sock = null;
        }

        if (sock != null) {
            sock.close();
            sock = null;
        }

        return false;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#set(java.lang.String, java.lang.Object)
     */
    public boolean set(String key, Object value) {
        return set("set", key, value, null, null, primitiveAsString);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#set(java.lang.String, java.lang.Object, java.lang.Integer)
     */
    public boolean set(String key, Object value, Integer hashCode) {
        return set("set", key, value, null, hashCode, primitiveAsString);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#set(java.lang.String, java.lang.Object, java.util.Date)
     */
    public boolean set(String key, Object value, Date expiry) {
        return set("set", key, value, expiry, null, primitiveAsString);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#set(java.lang.String, java.lang.Object, java.util.Date, java.lang.Integer)
     */
    public boolean set(String key, Object value, Date expiry, Integer hashCode) {
        return set("set", key, value, expiry, hashCode, primitiveAsString);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#add(java.lang.String, java.lang.Object)
     */
    public boolean add(String key, Object value) {
        return set("add", key, value, null, null, primitiveAsString);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#add(java.lang.String, java.lang.Object, java.lang.Integer)
     */
    public boolean add(String key, Object value, Integer hashCode) {
        return set("add", key, value, null, hashCode, primitiveAsString);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#add(java.lang.String, java.lang.Object, java.util.Date)
     */
    public boolean add(String key, Object value, Date expiry) {
        return set("add", key, value, expiry, null, primitiveAsString);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#add(java.lang.String, java.lang.Object, java.util.Date, java.lang.Integer)
     */
    public boolean add(String key, Object value, Date expiry, Integer hashCode) {
        return set("add", key, value, expiry, hashCode, primitiveAsString);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#replace(java.lang.String, java.lang.Object)
     */
    public boolean replace(String key, Object value) {
        return set("replace", key, value, null, null, primitiveAsString);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#replace(java.lang.String, java.lang.Object, java.lang.Integer)
     */
    public boolean replace(String key, Object value, Integer hashCode) {
        return set("replace", key, value, null, hashCode, primitiveAsString);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#replace(java.lang.String, java.lang.Object, java.util.Date)
     */
    public boolean replace(String key, Object value, Date expiry) {
        return set("replace", key, value, expiry, null, primitiveAsString);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#replace(java.lang.String, java.lang.Object, java.util.Date, java.lang.Integer)
     */
    public boolean replace(String key, Object value, Date expiry, Integer hashCode) {
        return set("replace", key, value, expiry, hashCode, primitiveAsString);
    }

    /**
     * Stores data to cache.
     *
     * If data does not already exist for this key on the server, or if the key is being<br/>
     * deleted, the specified value will not be stored.<br/>
     * The server will automatically delete the value when the expiration time has been reached.<br/>
     * <br/>
     * If compression is enabled, and the data is longer than the compression threshold<br/>
     * the data will be stored in compressed form.<br/>
     * <br/>
     * As of the current release, all objects stored will use java serialization.
     *
     * @param cmdname action to take (set, add, replace)
     * @param key key to store cache under
     * @param value object to cache
     * @param expiry expiration
     * @param hashCode if not null, then the int hashcode to use
     * @param asString store this object as a string?
     * @return true/false indicating success
     */
    private boolean set(String cmdname, String key, Object value, Date expiry, Integer hashCode, boolean asString) {

        if (cmdname == null || cmdname.trim().equals("") || key == null) {
            log.error("key is null or cmd is null/empty for set()");
            return false;
        }

        try {
            key = sanitizeKey(key);
        } catch (UnsupportedEncodingException e) {

            // if we have an errorHandler, use its hook
            if (errorHandler != null)
                errorHandler.handleErrorOnSet(this, e, key);

            log.error("failed to sanitize your key!", e);
            return false;
        }

        if (value == null) {
            log.error("trying to store a null value to cache");
            return false;
        }

        // get SockIO obj
        SockIOPool.SockIO sock = pool.getSock(key, hashCode);

        if (sock == null) {
            if (errorHandler != null)
                errorHandler.handleErrorOnSet(this, new IOException("no socket to server available"), key);
            return false;
        }

        if (expiry == null)
            expiry = new Date(0);

        // store flags
        int flags = 0;

        // byte array to hold data
        byte[] val;

        if (NativeHandler.isHandled(value)) {

            if (asString) {
                // useful for sharing data between java and non-java
                // and also for storing ints for the increment method
                try {
                    if (log.isInfoEnabled())
                        log.info("++++ storing data as a string for key: " + key + " for class: " + value.getClass().getName());
                    val = value.toString().getBytes(defaultEncoding);
                } catch (UnsupportedEncodingException ue) {

                    // if we have an errorHandler, use its hook
                    if (errorHandler != null)
                        errorHandler.handleErrorOnSet(this, ue, key);

                    log.error("invalid encoding type used: " + defaultEncoding, ue);
                    sock.close();
                    sock = null;
                    return false;
                }
            } else {
                try {
                    if (log.isInfoEnabled())
                        log.info("Storing with native handler...");
                    flags |= NativeHandler.getMarkerFlag(value);
                    val = NativeHandler.encode(value);
                } catch (Exception e) {

                    // if we have an errorHandler, use its hook
                    if (errorHandler != null)
                        errorHandler.handleErrorOnSet(this, e, key);

                    log.error("Failed to native handle obj", e);

                    sock.close();
                    sock = null;
                    return false;
                }
            }
        } else {
            // always serialize for non-primitive types
            try {
                if (log.isInfoEnabled())
                    log.info("++++ serializing for key: " + key + " for class: " + value.getClass().getName());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                (new ObjectOutputStream(bos)).writeObject(value);
                val = bos.toByteArray();
                flags |= F_SERIALIZED;
            } catch (IOException e) {

                // if we have an errorHandler, use its hook
                if (errorHandler != null)
                    errorHandler.handleErrorOnSet(this, e, key);

                // if we fail to serialize, then
                // we bail
                log.error("failed to serialize obj", e);
                log.error(value.toString());

                // return socket to pool and bail
                sock.close();
                sock = null;
                return false;
            }
        }

        // now try to compress if we want to
        // and if the length is over the threshold
        if (compressEnable && val.length > compressThreshold) {

            try {
                if (log.isInfoEnabled()) {
                    log.info("++++ trying to compress data");
                    log.info("++++ size prior to compression: " + val.length);
                }
                ByteArrayOutputStream bos = new ByteArrayOutputStream(val.length);
                GZIPOutputStream gos = new GZIPOutputStream(bos);
                gos.write(val, 0, val.length);
                gos.finish();
                gos.close();

                // store it and set compression flag
                val = bos.toByteArray();
                flags |= F_COMPRESSED;

                if (log.isInfoEnabled())
                    log.info("++++ compression succeeded, size after: " + val.length);
            } catch (IOException e) {

                // if we have an errorHandler, use its hook
                if (errorHandler != null)
                    errorHandler.handleErrorOnSet(this, e, key);

                log.error("IOException while compressing stream: " + e.getMessage());
                log.error("storing data uncompressed");
            }
        }

        // now write the data to the cache server
        try {
            String cmd = String.format("%s %s %d %d %d\r\n", cmdname, key, flags, (expiry.getTime() / 1000), val.length);
            sock.write(cmd.getBytes());
            sock.write(val);
            sock.write("\r\n".getBytes());
            sock.flush();

            // get result code
            String line = sock.readLine();
            if (log.isInfoEnabled())
                log.info("++++ memcache cmd (result code): " + cmd + " (" + line + ")");

            if (STORED.equals(line)) {
                if (log.isInfoEnabled())
                    log.info("++++ data successfully stored for key: " + key);
                sock.close();
                sock = null;
                return true;
            } else if (NOTSTORED.equals(line)) {
                if (log.isInfoEnabled())
                    log.info("++++ data not stored in cache for key: " + key);
            } else {
                log.error("++++ error storing data in cache for key: " + key + " -- length: " + val.length);
                log.error("++++ server response: " + line);
            }
        } catch (IOException e) {

            // if we have an errorHandler, use its hook
            if (errorHandler != null)
                errorHandler.handleErrorOnSet(this, e, key);

            // exception thrown
            log.error("++++ exception thrown while writing bytes to server on set");
            log.error(e.getMessage(), e);

            try {
                sock.trueClose();
            } catch (IOException ioe) {
                log.error("++++ failed to close socket : " + sock.toString());
            }

            sock = null;
        }

        if (sock != null) {
            sock.close();
            sock = null;
        }

        return false;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#storeCounter(java.lang.String, long)
     */
    public boolean storeCounter(String key, long counter) {
        return set("set", key, new Long(counter), null, null, true);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#storeCounter(java.lang.String, java.lang.Long)
     */
    public boolean storeCounter(String key, Long counter) {
        return set("set", key, counter, null, null, true);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#storeCounter(java.lang.String, java.lang.Long, java.lang.Integer)
     */
    public boolean storeCounter(String key, Long counter, Integer hashCode) {
        return set("set", key, counter, null, hashCode, true);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#getCounter(java.lang.String)
     */
    public long getCounter(String key) {
        return getCounter(key, null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#getCounter(java.lang.String, java.lang.Integer)
     */
    public long getCounter(String key, Integer hashCode) {

        if (key == null) {
            log.error("null key for getCounter()");
            return -1;
        }

        long counter = -1;
        try {
            counter = Long.parseLong((String) get(key, hashCode, true));
        } catch (Exception ex) {

            // if we have an errorHandler, use its hook
            if (errorHandler != null)
                errorHandler.handleErrorOnGet(this, ex, key);

            // not found or error getting out
            if (log.isInfoEnabled())
                log.info(String.format("Failed to parse Long value for key: %s", key));
        }

        return counter;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#addOrIncr(java.lang.String)
     */
    public long addOrIncr(String key) {
        return addOrIncr(key, 0, null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#addOrIncr(java.lang.String, long)
     */
    public long addOrIncr(String key, long inc) {
        return addOrIncr(key, inc, null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#addOrIncr(java.lang.String, long, java.lang.Integer)
     */
    public long addOrIncr(String key, long inc, Integer hashCode) {
        boolean ret = set("add", key, new Long(inc), null, hashCode, true);

        if (ret) {
            return inc;
        } else {
            return incrdecr("incr", key, inc, hashCode);
        }
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#addOrDecr(java.lang.String)
     */
    public long addOrDecr(String key) {
        return addOrDecr(key, 0, null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#addOrDecr(java.lang.String, long)
     */
    public long addOrDecr(String key, long inc) {
        return addOrDecr(key, inc, null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#addOrDecr(java.lang.String, long, java.lang.Integer)
     */
    public long addOrDecr(String key, long inc, Integer hashCode) {
        boolean ret = set("add", key, new Long(inc), null, hashCode, true);

        if (ret) {
            return inc;
        } else {
            return incrdecr("decr", key, inc, hashCode);
        }
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#incr(java.lang.String)
     */
    public long incr(String key) {
        return incrdecr("incr", key, 1, null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#incr(java.lang.String, long)
     */
    public long incr(String key, long inc) {
        return incrdecr("incr", key, inc, null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#incr(java.lang.String, long, java.lang.Integer)
     */
    public long incr(String key, long inc, Integer hashCode) {
        return incrdecr("incr", key, inc, hashCode);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#decr(java.lang.String)
     */
    public long decr(String key) {
        return incrdecr("decr", key, 1, null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#decr(java.lang.String, long)
     */
    public long decr(String key, long inc) {
        return incrdecr("decr", key, inc, null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#decr(java.lang.String, long, java.lang.Integer)
     */
    public long decr(String key, long inc, Integer hashCode) {
        return incrdecr("decr", key, inc, hashCode);
    }

    /**
     * Increments/decrements the value at the specified key by inc.
     *
     *  Note that the server uses a 32-bit unsigned integer, and checks for<br/>
     *  underflow. In the event of underflow, the result will be zero.  Because<br/>
     *  Java lacks unsigned types, the value is returned as a 64-bit integer.<br/>
     *  The server will only decrement a value if it already exists;<br/>
     *  if a value is not found, -1 will be returned.
     *
     * @param cmdname increment/decrement
     * @param key cache key
     * @param inc amount to incr or decr
     * @param hashCode if not null, then the int hashcode to use
     * @return new value or -1 if not exist
     */
    private long incrdecr(String cmdname, String key, long inc, Integer hashCode) {

        if (key == null) {
            log.error("null key for incrdecr()");
            return -1;
        }

        try {
            key = sanitizeKey(key);
        } catch (UnsupportedEncodingException e) {

            // if we have an errorHandler, use its hook
            if (errorHandler != null)
                errorHandler.handleErrorOnGet(this, e, key);

            log.error("failed to sanitize your key!", e);
            return -1;
        }

        // get SockIO obj for given cache key
        SockIOPool.SockIO sock = pool.getSock(key, hashCode);

        if (sock == null) {
            if (errorHandler != null)
                errorHandler.handleErrorOnSet(this, new IOException("no socket to server available"), key);
            return -1;
        }

        try {
            String cmd = String.format("%s %s %d\r\n", cmdname, key, inc);
            if (log.isDebugEnabled())
                log.debug("++++ memcache incr/decr command: " + cmd);

            sock.write(cmd.getBytes());
            sock.flush();

            // get result back
            String line = sock.readLine();

            if (line.matches("\\d+")) {

                // return sock to pool and return result
                sock.close();
                try {
                    return Long.parseLong(line);
                } catch (Exception ex) {

                    // if we have an errorHandler, use its hook
                    if (errorHandler != null)
                        errorHandler.handleErrorOnGet(this, ex, key);

                    log.error(String.format("Failed to parse Long value for key: %s", key));
                }
            } else if (NOTFOUND.equals(line)) {
                if (log.isInfoEnabled())
                    log.info("++++ key not found to incr/decr for key: " + key);
            } else {
                log.error("++++ error incr/decr key: " + key);
                log.error("++++ server response: " + line);
            }
        } catch (IOException e) {

            // if we have an errorHandler, use its hook
            if (errorHandler != null)
                errorHandler.handleErrorOnGet(this, e, key);

            // exception thrown
            log.error("++++ exception thrown while writing bytes to server on incr/decr");
            log.error(e.getMessage(), e);

            try {
                sock.trueClose();
            } catch (IOException ioe) {
                log.error("++++ failed to close socket : " + sock.toString());
            }

            sock = null;
        }

        if (sock != null) {
            sock.close();
            sock = null;
        }

        return -1;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#get(java.lang.String)
     */
    public Object get(String key) {
        return get(key, null, false);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#get(java.lang.String, java.lang.Integer)
     */
    public Object get(String key, Integer hashCode) {
        return get(key, hashCode, false);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#get(java.lang.String, java.lang.Integer, boolean)
     */
    public Object get(String key, Integer hashCode, boolean asString) {

        if (key == null) {
            log.error("key is null for get()");
            return null;
        }

        try {
            key = sanitizeKey(key);
        } catch (UnsupportedEncodingException e) {

            // if we have an errorHandler, use its hook
            if (errorHandler != null)
                errorHandler.handleErrorOnGet(this, e, key);

            log.error("failed to sanitize your key!", e);
            return null;
        }

        // get SockIO obj using cache key
        SockIOPool.SockIO sock = pool.getSock(key, hashCode);

        if (sock == null) {
            if (errorHandler != null)
                errorHandler.handleErrorOnGet(this, new IOException("no socket to server available"), key);
            return null;
        }

        try {
            String cmd = "get " + key + "\r\n";

            if (log.isDebugEnabled())
                log.debug("++++ memcache get command: " + cmd);

            sock.write(cmd.getBytes());
            sock.flush();

            // ready object
            Object o = null;

            while (true) {
                String line = sock.readLine();

                if (log.isDebugEnabled())
                    log.debug("++++ line: " + line);

                if (line.startsWith(VALUE)) {
                    String[] info = line.split(" ");
                    int flag = Integer.parseInt(info[2]);
                    int length = Integer.parseInt(info[3]);

                    if (log.isDebugEnabled()) {
                        log.debug("++++ key: " + key);
                        log.debug("++++ flags: " + flag);
                        log.debug("++++ length: " + length);
                    }

                    // read obj into buffer
                    byte[] buf = new byte[length];
                    sock.read(buf);
                    sock.clearEOL();

                    if ((flag & F_COMPRESSED) == F_COMPRESSED) {
                        try {
                            // read the input stream, and write to a byte array output stream since
                            // we have to read into a byte array, but we don't know how large it
                            // will need to be, and we don't want to resize it a bunch
                            GZIPInputStream gzi = new GZIPInputStream(new ByteArrayInputStream(buf));
                            ByteArrayOutputStream bos = new ByteArrayOutputStream(buf.length);

                            int count;
                            byte[] tmp = new byte[2048];
                            while ((count = gzi.read(tmp)) != -1) {
                                bos.write(tmp, 0, count);
                            }

                            // store uncompressed back to buffer
                            buf = bos.toByteArray();
                            gzi.close();
                        } catch (IOException e) {

                            // if we have an errorHandler, use its hook
                            if (errorHandler != null)
                                errorHandler.handleErrorOnGet(this, e, key);

                            log.error("++++ IOException thrown while trying to uncompress input stream for key: " + key + " -- " + e.getMessage());
                            throw new NestedIOException("++++ IOException thrown while trying to uncompress input stream for key: " + key, e);
                        }
                    }

                    // we can only take out serialized objects
                    if ((flag & F_SERIALIZED) != F_SERIALIZED) {
                        if (primitiveAsString || asString) {
                            // pulling out string value
                            if (log.isInfoEnabled())
                                log.info("++++ retrieving object and stuffing into a string.");
                            o = new String(buf, defaultEncoding);
                        } else {
                            // decoding object
                            try {
                                o = NativeHandler.decode(buf, flag);
                            } catch (Exception e) {

                                // if we have an errorHandler, use its hook
                                if (errorHandler != null)
                                    errorHandler.handleErrorOnGet(this, e, key);

                                log.error("++++ Exception thrown while trying to deserialize for key: " + key, e);
                                throw new NestedIOException(e);
                            }
                        }
                    } else {
                        // deserialize if the data is serialized
                        ContextObjectInputStream ois =
                                new ContextObjectInputStream(new ByteArrayInputStream(buf), classLoader);
                        try {
                            o = ois.readObject();
                            if (log.isInfoEnabled())
                                log.info("++++ deserializing " + o.getClass());
                        } catch (Exception e) {
                            if (errorHandler != null)
                                errorHandler.handleErrorOnGet(this, e, key);

                            o = null;
                            log.error("++++ Exception thrown while trying to deserialize for key: " + key + " -- " + e.getMessage());
                        }
                    }
                } else if (END.equals(line)) {
                    if (log.isDebugEnabled())
                        log.debug("++++ finished reading from cache server");
                    break;
                }
            }

            sock.close();
            sock = null;
            return o;
        } catch (IOException e) {

            // if we have an errorHandler, use its hook
            if (errorHandler != null)
                errorHandler.handleErrorOnGet(this, e, key);

            // exception thrown
            log.error("++++ exception thrown while trying to get object from cache for key: " + key + " -- " + e.getMessage());

            try {
                sock.trueClose();
            } catch (IOException ioe) {
                log.error("++++ failed to close socket : " + sock.toString());
            }
            sock = null;
        }

        if (sock != null)
            sock.close();

        return null;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#getMultiArray(java.lang.String[])
     */
    public Object[] getMultiArray(String[] keys) {
        return getMultiArray(keys, null, false);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#getMultiArray(java.lang.String[], java.lang.Integer[])
     */
    public Object[] getMultiArray(String[] keys, Integer[] hashCodes) {
        return getMultiArray(keys, hashCodes, false);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#getMultiArray(java.lang.String[], java.lang.Integer[], boolean)
     */
    public Object[] getMultiArray(String[] keys, Integer[] hashCodes, boolean asString) {

        Map<String, Object> data = getMulti(keys, hashCodes, asString);

        if (data == null)
            return null;

        Object[] res = new Object[keys.length];
        for (int i = 0; i < keys.length; i++) {
            res[i] = data.get(keys[i]);
        }

        return res;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#getMulti(java.lang.String[])
     */
    public Map<String, Object> getMulti(String[] keys) {
        return getMulti(keys, null, false);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#getMulti(java.lang.String[], java.lang.Integer[])
     */
    public Map<String, Object> getMulti(String[] keys, Integer[] hashCodes) {
        return getMulti(keys, hashCodes, false);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#getMulti(java.lang.String[], java.lang.Integer[], boolean)
     */
    public Map<String, Object> getMulti(String[] keys, Integer[] hashCodes, boolean asString) {

        if (keys == null || keys.length == 0) {
            log.error("missing keys for getMulti()");
            return null;
        }

        Map<String, StringBuilder> cmdMap =
                new HashMap<String, StringBuilder>();

        for (int i = 0; i < keys.length; ++i) {

            String key = keys[i];
            if (key == null) {
                log.error("null key, so skipping");
                continue;
            }

            Integer hash = null;
            if (hashCodes != null && hashCodes.length > i)
                hash = hashCodes[i];

            String cleanKey = key;
            try {
                cleanKey = sanitizeKey(key);
            } catch (UnsupportedEncodingException e) {

                // if we have an errorHandler, use its hook
                if (errorHandler != null)
                    errorHandler.handleErrorOnGet(this, e, key);

                log.error("failed to sanitize your key!", e);
                continue;
            }

            // get SockIO obj from cache key
            SockIOPool.SockIO sock = pool.getSock(cleanKey, hash);

            if (sock == null) {
                if (errorHandler != null)
                    errorHandler.handleErrorOnGet(this, new IOException("no socket to server available"), key);
                continue;
            }

            // store in map and list if not already
            if (!cmdMap.containsKey(sock.getHost()))
                cmdMap.put(sock.getHost(), new StringBuilder("get"));

            cmdMap.get(sock.getHost()).append(" " + cleanKey);

            // return to pool
            sock.close();
        }

        if (log.isInfoEnabled())
            log.info("multi get socket count : " + cmdMap.size());

        // now query memcache
        Map<String, Object> ret =
                new HashMap<String, Object>(keys.length);

        // now use new NIO implementation
        (new NIOLoader(this)).doMulti(asString, cmdMap, keys, ret);

        // fix the return array in case we had to rewrite any of the keys
        for (String key : keys) {

            String cleanKey = key;
            try {
                cleanKey = sanitizeKey(key);
            } catch (UnsupportedEncodingException e) {

                // if we have an errorHandler, use its hook
                if (errorHandler != null)
                    errorHandler.handleErrorOnGet(this, e, key);

                log.error("failed to sanitize your key!", e);
                continue;
            }

            if (!key.equals(cleanKey) && ret.containsKey(cleanKey)) {
                ret.put(key, ret.get(cleanKey));
                ret.remove(cleanKey);
            }

            // backfill missing keys w/ null value
            if (!ret.containsKey(key))
                ret.put(key, null);
        }

        if (log.isDebugEnabled())
            log.debug("++++ memcache: got back " + ret.size() + " results");
        return ret;
    }

    /**
     * This method loads the data from cache into a Map.
     *
     * Pass a SockIO object which is ready to receive data and a HashMap<br/>
     * to store the results.
     *
     * @param sock socket waiting to pass back data
     * @param hm hashmap to store data into
     * @param asString if true, and if we are using NativehHandler, return string val
     * @throws IOException if io exception happens while reading from socket
     */
    private void loadMulti(LineInputStream input, Map<String, Object> hm, boolean asString) throws IOException {

        while (true) {
            String line = input.readLine();
            if (log.isDebugEnabled())
                log.debug("++++ line: " + line);

            if (line.startsWith(VALUE)) {
                String[] info = line.split(" ");
                String key = info[1];
                int flag = Integer.parseInt(info[2]);
                int length = Integer.parseInt(info[3]);

                if (log.isDebugEnabled()) {
                    log.debug("++++ key: " + key);
                    log.debug("++++ flags: " + flag);
                    log.debug("++++ length: " + length);
                }

                // read obj into buffer
                byte[] buf = new byte[length];
                input.read(buf);
                input.clearEOL();

                // ready object
                Object o;

                // check for compression
                if ((flag & F_COMPRESSED) == F_COMPRESSED) {
                    try {
                        // read the input stream, and write to a byte array output stream since
                        // we have to read into a byte array, but we don't know how large it
                        // will need to be, and we don't want to resize it a bunch
                        GZIPInputStream gzi = new GZIPInputStream(new ByteArrayInputStream(buf));
                        ByteArrayOutputStream bos = new ByteArrayOutputStream(buf.length);

                        int count;
                        byte[] tmp = new byte[2048];
                        while ((count = gzi.read(tmp)) != -1) {
                            bos.write(tmp, 0, count);
                        }

                        // store uncompressed back to buffer
                        buf = bos.toByteArray();
                        gzi.close();
                    } catch (IOException e) {

                        // if we have an errorHandler, use its hook
                        if (errorHandler != null)
                            errorHandler.handleErrorOnGet(this, e, key);

                        log.error("++++ IOException thrown while trying to uncompress input stream for key: " + key + " -- " + e.getMessage());
                        throw new NestedIOException("++++ IOException thrown while trying to uncompress input stream for key: " + key, e);
                    }
                }

                // we can only take out serialized objects
                if ((flag & F_SERIALIZED) != F_SERIALIZED) {
                    if (primitiveAsString || asString) {
                        // pulling out string value
                        if (log.isInfoEnabled())
                            log.info("++++ retrieving object and stuffing into a string.");
                        o = new String(buf, defaultEncoding);
                    } else {
                        // decoding object
                        try {
                            o = NativeHandler.decode(buf, flag);
                        } catch (Exception e) {

                            // if we have an errorHandler, use its hook
                            if (errorHandler != null)
                                errorHandler.handleErrorOnGet(this, e, key);

                            log.error("++++ Exception thrown while trying to deserialize for key: " + key + " -- " + e.getMessage());
                            throw new NestedIOException(e);
                        }
                    }
                } else {
                    // deserialize if the data is serialized
                    ContextObjectInputStream ois =
                            new ContextObjectInputStream(new ByteArrayInputStream(buf), classLoader);
                    try {
                        o = ois.readObject();
                        if (log.isInfoEnabled())
                            log.info("++++ deserializing " + o.getClass());
                    } catch (InvalidClassException e) {
                        /* Errors de-serializing are to be expected in the case of a
                         * long running server that spans client restarts with updated
                         * classes.
                         */
                        // if we have an errorHandler, use its hook
                        if (errorHandler != null)
                            errorHandler.handleErrorOnGet(this, e, key);

                        o = null;
                        log.error("++++ InvalidClassException thrown while trying to deserialize for key: " + key + " -- " + e.getMessage());
                    } catch (ClassNotFoundException e) {

                        // if we have an errorHandler, use its hook
                        if (errorHandler != null)
                            errorHandler.handleErrorOnGet(this, e, key);

                        o = null;
                        log.error("++++ ClassNotFoundException thrown while trying to deserialize for key: " + key + " -- " + e.getMessage());
                    }
                }

                // store the object into the cache
                if (o != null)
                    hm.put(key, o);
            } else if (END.equals(line)) {
                if (log.isDebugEnabled())
                    log.debug("++++ finished reading from cache server");
                break;
            }
        }
    }

    private String sanitizeKey(String key) throws UnsupportedEncodingException {
        return (sanitizeKeys) ? URLEncoder.encode(key, "UTF-8") : key;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#flushAll()
     */
    public boolean flushAll() {
        return flushAll(null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#flushAll(java.lang.String[])
     */
    public boolean flushAll(String[] servers) {

        // get SockIOPool instance
        // return false if unable to get SockIO obj
        if (pool == null) {
            log.error("++++ unable to get SockIOPool instance");
            return false;
        }

        // get all servers and iterate over them
        servers = (servers == null)
                ? pool.getServers()
                : servers;

        // if no servers, then return early
        if (servers == null || servers.length <= 0) {
            log.error("++++ no servers to flush");
            return false;
        }

        boolean success = true;

        for (int i = 0; i < servers.length; i++) {

            SockIOPool.SockIO sock = pool.getConnection(servers[i]);
            if (sock == null) {
                log.error("++++ unable to get connection to : " + servers[i]);
                success = false;
                if (errorHandler != null)
                    errorHandler.handleErrorOnFlush(this, new IOException("no socket to server available"));
                continue;
            }

            // build command
            String command = "flush_all\r\n";

            try {
                sock.write(command.getBytes());
                sock.flush();

                // if we get appropriate response back, then we return true
                String line = sock.readLine();
                success = (OK.equals(line))
                        ? success && true
                        : false;
            } catch (IOException e) {

                // if we have an errorHandler, use its hook
                if (errorHandler != null)
                    errorHandler.handleErrorOnFlush(this, e);

                // exception thrown
                log.error("++++ exception thrown while writing bytes to server on flushAll");
                log.error(e.getMessage(), e);

                try {
                    sock.trueClose();
                } catch (IOException ioe) {
                    log.error("++++ failed to close socket : " + sock.toString());
                }

                success = false;
                sock = null;
            }

            if (sock != null) {
                sock.close();
                sock = null;
            }
        }

        return success;
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#stats()
     */
    public Map stats() {
        return stats(null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#stats(java.lang.String[])
     */
    public Map stats(String[] servers) {
        return stats(servers, "stats\r\n", STATS);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#statsItems()
     */
    public Map statsItems() {
        return statsItems(null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#statsItems(java.lang.String[])
     */
    public Map statsItems(String[] servers) {
        return stats(servers, "stats items\r\n", STATS);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#statsSlabs()
     */
    public Map statsSlabs() {
        return statsSlabs(null);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#statsSlabs(java.lang.String[])
     */
    public Map statsSlabs(String[] servers) {
        return stats(servers, "stats slabs\r\n", STATS);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#statsCacheDump(int, int)
     */
    public Map statsCacheDump(int slabNumber, int limit) {
        return statsCacheDump(null, slabNumber, limit);
    }

    /* (non-Javadoc)
     * @see com.ecointel.cache.memcache.client.aaa#statsCacheDump(java.lang.String[], int, int)
     */
    public Map statsCacheDump(String[] servers, int slabNumber, int limit) {
        return stats(servers, String.format("stats cachedump %d %d\r\n", slabNumber, limit), ITEM);
    }

    private Map stats(String[] servers, String command, String lineStart) {

        if (command == null || command.trim().equals("")) {
            log.error("++++ invalid / missing command for stats()");
            return null;
        }

        // get all servers and iterate over them
        servers = (servers == null)
                ? pool.getServers()
                : servers;

        // if no servers, then return early
        if (servers == null || servers.length <= 0) {
            log.error("++++ no servers to check stats");
            return null;
        }

        // array of stats Maps
        Map<String, Map> statsMaps =
                new HashMap<String, Map>();

        for (int i = 0; i < servers.length; i++) {

            SockIOPool.SockIO sock = pool.getConnection(servers[i]);
            if (sock == null) {
                log.error("++++ unable to get connection to : " + servers[i]);
                if (errorHandler != null)
                    errorHandler.handleErrorOnStats(this, new IOException("no socket to server available"));
                continue;
            }

            // build command
            try {
                sock.write(command.getBytes());
                sock.flush();

                // map to hold key value pairs
                Map<String, String> stats = new HashMap<String, String>();

                // loop over results
                while (true) {
                    String line = sock.readLine();
                    if (log.isDebugEnabled())
                        log.debug("++++ line: " + line);

                    if (line.startsWith(lineStart)) {
                        String[] info = line.split(" ", 3);
                        String key = info[1];
                        String value = info[2];

                        if (log.isDebugEnabled()) {
                            log.debug("++++ key  : " + key);
                            log.debug("++++ value: " + value);
                        }

                        stats.put(key, value);
                    } else if (END.equals(line)) {
                        // finish when we get end from server
                        if (log.isDebugEnabled())
                            log.debug("++++ finished reading from cache server");
                        break;
                    } else if (line.startsWith(ERROR) || line.startsWith(CLIENT_ERROR) || line.startsWith(SERVER_ERROR)) {
                        log.error("++++ failed to query stats");
                        log.error("++++ server response: " + line);
                        break;
                    }

                    statsMaps.put(servers[i], stats);
                }
            } catch (IOException e) {

                // if we have an errorHandler, use its hook
                if (errorHandler != null)
                    errorHandler.handleErrorOnStats(this, e);

                // exception thrown
                log.error("++++ exception thrown while writing bytes to server on stats");
                log.error(e.getMessage(), e);

                try {
                    sock.trueClose();
                } catch (IOException ioe) {
                    log.error("++++ failed to close socket : " + sock.toString());
                }

                sock = null;
            }

            if (sock != null) {
                sock.close();
                sock = null;
            }
        }

        return statsMaps;
    }

    protected final class NIOLoader {
        protected Selector selector;
        protected int numConns = 0;
        protected MemcachedClient mc;
        protected Connection[] conns;

        public NIOLoader(MemcachedClient mc) {
            this.mc = mc;
        }

        public void doMulti(boolean asString, Map<String, StringBuilder> sockKeys, String[] keys, Map<String, Object> ret) {

            long timeRemaining = 0;
            try {
                selector = Selector.open();

                // get the sockets, flip them to non-blocking, and set up data
                // structures
                conns = new Connection[sockKeys.keySet().size()];
                numConns = 0;
                for (Iterator<String> i = sockKeys.keySet().iterator(); i.hasNext(); ) {
                    // get SockIO obj from hostname
                    String host = i.next();

                    SockIOPool.SockIO sock = pool.getConnection(host);

                    if (sock == null) {
                        if (errorHandler != null)
                            errorHandler.handleErrorOnGet(this.mc, new IOException("no socket to server available"), keys);
                        return;
                    }

                    conns[numConns++] = new Connection(sock, sockKeys.get(host));
                }

                // the main select loop; ends when
                // 1) we've received data from all the servers, or
                // 2) we time out
                long startTime = System.currentTimeMillis();

                long timeout = pool.getMaxBusy();
                timeRemaining = timeout;

                while (numConns > 0 && timeRemaining > 0) {
                    int n = selector.select(Math.min(timeout, 5000));
                    if (n > 0) {
                        // we've got some activity; handle it
                        Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                        while (it.hasNext()) {
                            SelectionKey key = it.next();
                            it.remove();
                            handleKey(key);
                        }
                    } else {
                        // timeout likely... better check
                        // TODO:  This seems like a problem area that we need to figure out how to handle.
                        log.error("selector timed out waiting for activity");
                    }

                    timeRemaining = timeout - (System.currentTimeMillis() - startTime);
                }
            } catch (IOException e) {
                // errors can happen just about anywhere above, from
                // connection setup to any of the mechanics
                handleError(e, keys);
                return;
            } finally {
                if (log.isDebugEnabled())
                    log.debug("Disconnecting; numConns=" + numConns + "  timeRemaining=" + timeRemaining);

                // run through our conns and either return them to the pool
                // or forcibly close them
                try {
                    if (selector != null)
                        selector.close();
                } catch (IOException ignoreMe) {
                }

                for (Connection c : conns) {
                    if (c != null)
                        c.close();
                }
            }

            // Done!  Build the list of results and return them.  If we get
            // here by a timeout, then some of the connections are probably
            // not done.  But we'll return what we've got...
            for (Connection c : conns) {
                try {
                    if (c.incoming.size() > 0 && c.isDone())
                        loadMulti(new ByteBufArrayInputStream(c.incoming), ret, asString);
                } catch (Exception e) {
                    // shouldn't happen; we have all the data already
                    log.warn("Caught the aforementioned exception on " + c);
                }
            }
        }

        private void handleError(Throwable e, String[] keys) {
            // if we have an errorHandler, use its hook
            if (errorHandler != null)
                errorHandler.handleErrorOnGet(MemcachedClient.this, e, keys);

            // exception thrown
            log.error("++++ exception thrown while getting from cache on getMulti");
            log.error(e.getMessage());
        }

        private void handleKey(SelectionKey key) throws IOException {
            if (log.isDebugEnabled())
                log.debug("handling selector op " + key.readyOps() + " for key " + key);

            if (key.isReadable())
                readResponse(key);
            else if (key.isWritable())
                writeRequest(key);
        }

        public void writeRequest(SelectionKey key) throws IOException {
            ByteBuffer buf = ((Connection) key.attachment()).outgoing;
            SocketChannel sc = (SocketChannel) key.channel();

            if (buf.hasRemaining()) {
                if (log.isDebugEnabled())
                    log.debug("writing " + buf.remaining() + "B to " + ((SocketChannel) key.channel()).socket().getInetAddress());

                sc.write(buf);
            }

            if (!buf.hasRemaining()) {
                if (log.isDebugEnabled())
                    log.debug("switching to read mode for server " + ((SocketChannel) key.channel()).socket().getInetAddress());

                key.interestOps(SelectionKey.OP_READ);
            }
        }

        public void readResponse(SelectionKey key) throws IOException {
            Connection conn = (Connection) key.attachment();
            ByteBuffer buf = conn.getBuffer();
            int count = conn.channel.read(buf);
            if (count > 0) {
                if (log.isDebugEnabled())
                    log.debug("read  " + count + " from " + conn.channel.socket().getInetAddress());

                if (conn.isDone()) {
                    if (log.isDebugEnabled())
                        log.debug("connection done to  " + conn.channel.socket().getInetAddress());

                    key.cancel();
                    numConns--;
                    return;
                }
            }
        }

        private final class Connection {

            public List<ByteBuffer> incoming = new ArrayList<ByteBuffer>();
            public ByteBuffer outgoing;
            public SockIOPool.SockIO sock;
            public SocketChannel channel;
            private boolean isDone = false;

            public Connection(SockIOPool.SockIO sock, StringBuilder request) throws IOException {
                if (log.isDebugEnabled())
                    log.debug("setting up connection to " + sock.getHost());

                this.sock = sock;
                outgoing = ByteBuffer.wrap(request.append("\r\n").toString().getBytes());

                channel = sock.getChannel();
                if (channel == null)
                    throw new IOException("dead connection to: " + sock.getHost());

                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_WRITE, this);
            }

            public void close() {
                try {
                    if (isDone) {
                        // turn off non-blocking IO and return to pool
                        if (log.isDebugEnabled())
                            log.debug("++++ gracefully closing connection to " + sock.getHost());

                        channel.configureBlocking(true);
                        sock.close();
                        return;
                    }
                } catch (IOException e) {
                    log.warn("++++ memcache: unexpected error closing normally");
                }

                try {
                    if (log.isDebugEnabled())
                        log.debug("forcefully closing connection to " + sock.getHost());

                    channel.close();
                    sock.trueClose();
                } catch (IOException ignoreMe) {
                }
            }

            public boolean isDone() {
                // if we know we're done, just say so
                if (isDone)
                    return true;

                // else find out the hard way
                int strPos = B_END.length - 1;

                int bi = incoming.size() - 1;
                while (bi >= 0 && strPos >= 0) {
                    ByteBuffer buf = incoming.get(bi);
                    int pos = buf.position() - 1;
                    while (pos >= 0 && strPos >= 0) {
                        if (buf.get(pos--) != B_END[strPos--])
                            return false;
                    }
                    bi--;
                }
                isDone = strPos < 0;
                return isDone;
            }

            public ByteBuffer getBuffer() {
                int last = incoming.size() - 1;
                if (last >= 0 && incoming.get(last).hasRemaining()) {
                    return incoming.get(last);
                } else {
                    ByteBuffer newBuf = ByteBuffer.allocate(8192);
                    incoming.add(newBuf);
                    return newBuf;
                }
            }

            public String toString() {
                return "Connection to " + sock.getHost() + " with " + incoming.size() + " bufs; done is " + isDone;
            }
        }
    }
}
