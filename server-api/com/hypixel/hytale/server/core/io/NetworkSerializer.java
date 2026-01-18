package com.hypixel.hytale.server.core.io;

@FunctionalInterface
public interface NetworkSerializer<Type, Packet> {
  Packet toPacket(Type paramType);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\NetworkSerializer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */