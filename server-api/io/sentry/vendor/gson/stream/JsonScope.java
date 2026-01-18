package io.sentry.vendor.gson.stream;

import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
final class JsonScope {
  static final int EMPTY_ARRAY = 1;
  
  static final int NONEMPTY_ARRAY = 2;
  
  static final int EMPTY_OBJECT = 3;
  
  static final int DANGLING_NAME = 4;
  
  static final int NONEMPTY_OBJECT = 5;
  
  static final int EMPTY_DOCUMENT = 6;
  
  static final int NONEMPTY_DOCUMENT = 7;
  
  static final int CLOSED = 8;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\vendor\gson\stream\JsonScope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */