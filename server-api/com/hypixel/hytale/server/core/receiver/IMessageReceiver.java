package com.hypixel.hytale.server.core.receiver;

import com.hypixel.hytale.server.core.Message;
import javax.annotation.Nonnull;

public interface IMessageReceiver {
  void sendMessage(@Nonnull Message paramMessage);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\receiver\IMessageReceiver.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */