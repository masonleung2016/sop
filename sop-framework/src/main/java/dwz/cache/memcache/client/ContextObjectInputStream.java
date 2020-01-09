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
 * <p>
 * Adds the ability for the MemCached client to be initialized
 * with a custom class loader.  This will allow for the
 * deserialization of classes that are not visible to the system
 * class loader.
 *
 * @author Vin Chawla <vin@tivo.com>
 */

package dwz.cache.memcache.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class ContextObjectInputStream extends ObjectInputStream {

    ClassLoader mLoader;

    public ContextObjectInputStream(InputStream in, ClassLoader loader) throws IOException, SecurityException {
        super(in);
        mLoader = loader;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Class resolveClass(ObjectStreamClass v) throws IOException, ClassNotFoundException {
        if (mLoader == null)
            return super.resolveClass(v);
        else
            return Class.forName(v.getName(), true, mLoader);
    }
}
