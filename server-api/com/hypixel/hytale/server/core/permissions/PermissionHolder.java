package com.hypixel.hytale.server.core.permissions;

import javax.annotation.Nonnull;

public interface PermissionHolder {
  boolean hasPermission(@Nonnull String paramString);
  
  boolean hasPermission(@Nonnull String paramString, boolean paramBoolean);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\PermissionHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */