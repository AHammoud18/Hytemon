package com.hypixel.hytale.server.core.modules.accesscontrol.provider;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

public interface AccessProvider {
  @Nonnull
  CompletableFuture<Optional<String>> getDisconnectReason(UUID paramUUID);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\accesscontrol\provider\AccessProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */