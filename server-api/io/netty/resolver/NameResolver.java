package io.netty.resolver;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import java.io.Closeable;
import java.util.List;

public interface NameResolver<T> extends Closeable {
  Future<T> resolve(String paramString);
  
  Future<T> resolve(String paramString, Promise<T> paramPromise);
  
  Future<List<T>> resolveAll(String paramString);
  
  Future<List<T>> resolveAll(String paramString, Promise<List<T>> paramPromise);
  
  void close();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\NameResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */