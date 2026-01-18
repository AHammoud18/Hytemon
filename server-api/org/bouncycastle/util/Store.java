package org.bouncycastle.util;

import java.util.Collection;

public interface Store<T> {
  Collection<T> getMatches(Selector<T> paramSelector) throws StoreException;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastl\\util\Store.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */