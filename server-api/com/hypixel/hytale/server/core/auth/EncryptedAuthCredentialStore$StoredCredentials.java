package com.hypixel.hytale.server.core.auth;

import java.time.Instant;
import java.util.UUID;
import javax.annotation.Nullable;

class StoredCredentials {
  @Nullable
  String accessToken;
  
  @Nullable
  String refreshToken;
  
  @Nullable
  Instant expiresAt;
  
  @Nullable
  UUID profileUuid;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\auth\EncryptedAuthCredentialStore$StoredCredentials.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */