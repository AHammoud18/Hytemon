package com.hypixel.hytale.server.core.command.system;

import com.hypixel.hytale.server.core.permissions.PermissionHolder;
import com.hypixel.hytale.server.core.receiver.IMessageReceiver;
import java.util.UUID;

public interface CommandSender extends IMessageReceiver, PermissionHolder {
  String getDisplayName();
  
  UUID getUuid();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\CommandSender.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */