package com.google.common.flogger.backend;

import com.google.common.flogger.backend.system.DefaultPlatform;

public final class PlatformProvider {
  public static Platform getPlatform() {
    try {
      return DefaultPlatform.class.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
    } catch (NoClassDefFoundError|IllegalAccessException|InstantiationException|java.lang.reflect.InvocationTargetException|NoSuchMethodException noClassDefFoundError) {
      return null;
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\PlatformProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */