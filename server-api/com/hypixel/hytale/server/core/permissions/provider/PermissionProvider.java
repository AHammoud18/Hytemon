package com.hypixel.hytale.server.core.permissions.provider;

import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;

public interface PermissionProvider {
  @Nonnull
  String getName();
  
  void addUserPermissions(@Nonnull UUID paramUUID, @Nonnull Set<String> paramSet);
  
  void removeUserPermissions(@Nonnull UUID paramUUID, @Nonnull Set<String> paramSet);
  
  Set<String> getUserPermissions(@Nonnull UUID paramUUID);
  
  void addGroupPermissions(@Nonnull String paramString, @Nonnull Set<String> paramSet);
  
  void removeGroupPermissions(@Nonnull String paramString, @Nonnull Set<String> paramSet);
  
  Set<String> getGroupPermissions(@Nonnull String paramString);
  
  void addUserToGroup(@Nonnull UUID paramUUID, @Nonnull String paramString);
  
  void removeUserFromGroup(@Nonnull UUID paramUUID, @Nonnull String paramString);
  
  Set<String> getGroupsForUser(@Nonnull UUID paramUUID);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\permissions\provider\PermissionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */