package com.hypixel.hytale.server.core.event.events.player;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import javax.annotation.Nonnull;

public interface Formatter {
  @Nonnull
  Message format(@Nonnull PlayerRef paramPlayerRef, @Nonnull String paramString);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\PlayerChatEvent$Formatter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */