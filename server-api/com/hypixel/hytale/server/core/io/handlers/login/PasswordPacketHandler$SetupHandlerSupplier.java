package com.hypixel.hytale.server.core.io.handlers.login;

import com.hypixel.hytale.server.core.auth.PlayerAuthentication;
import com.hypixel.hytale.server.core.io.PacketHandler;
import com.hypixel.hytale.server.core.io.ProtocolVersion;
import io.netty.channel.Channel;

@FunctionalInterface
public interface SetupHandlerSupplier {
  PacketHandler create(Channel paramChannel, ProtocolVersion paramProtocolVersion, String paramString, PlayerAuthentication paramPlayerAuthentication);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\handlers\login\PasswordPacketHandler$SetupHandlerSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */