/*
 * Simplex3d, CoreData module
 * Copyright (C) 2010-2011, Aleksey Nikiforov
 *
 * This file is part of Simplex3dData.
 *
 * Simplex3dData is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Simplex3dData is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplex3d.data;

import java.io.*;
import java.nio.*;


/** Prevents gaining access to read-only content.
 *
 * @author Aleksey Nikiforov (lex)
 */
abstract class ProtectedData<A> {
    final Object sharedStore;

    ProtectedData(Object shared) {
        if (shared instanceof ProtectedData) {
            this.sharedStore = ((ProtectedData) shared).sharedStore;
        }
        else {
            this.sharedStore = shared;
        }
    }

    @SuppressWarnings("unchecked")
    final A sharedArray() {
        return (A) sharedStore;
    }

    final ByteBuffer sharedBuffer() {
        return (ByteBuffer) sharedStore;
    }

    protected final Object writeReplace() throws ObjectStreamException {
        if (this instanceof ReadDataArray) {
            ReadAbstractData src = (ReadAbstractData) this;

            if (src.primitive() == this) {
                SerializablePrimitive data = (SerializablePrimitive) mkSerializableInstance();
                data.content_$eq(sharedStore);
                data.readOnly_$eq(src.isReadOnly());
                return data;
            }
            else {
                SerializableComposite data = (SerializableComposite) mkSerializableInstance();
                data.content_$eq(src.primitive());
                return data;
            }
        }
        throw new NotSerializableException();
    }

    protected SerializableData mkSerializableInstance() throws NotSerializableException {
        throw new NotSerializableException();
    }
}