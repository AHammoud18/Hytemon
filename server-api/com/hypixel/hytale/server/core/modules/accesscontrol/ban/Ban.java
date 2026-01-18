package com.hypixel.hytale.server.core.modules.accesscontrol.ban;

import com.google.gson.JsonObject;
import com.hypixel.hytale.server.core.modules.accesscontrol.provider.AccessProvider;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface Ban extends AccessProvider {
  UUID getTarget();
  
  UUID getBy();
  
  Instant getTimestamp();
  
  boolean isInEffect();
  
  Optional<String> getReason();
  
  String getType();
  
  JsonObject toJsonObject();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\ban\Ban.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */