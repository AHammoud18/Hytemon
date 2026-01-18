package com.hypixel.hytale.server.core.universe.world.storage;

import com.hypixel.hytale.component.system.data.EntityDataSystem;
import com.hypixel.hytale.protocol.Packet;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import java.util.concurrent.CompletableFuture;

public abstract class LoadFuturePacketDataQuerySystem extends EntityDataSystem<ChunkStore, PlayerRef, CompletableFuture<Packet>> {}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\storage\ChunkStore$LoadFuturePacketDataQuerySystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */