package com.hypixel.hytale.server.core.entity;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionSyncData;
import com.hypixel.hytale.protocol.packets.interaction.SyncInteractionChain;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ChainSyncStorage {
  InteractionState getClientState();
  
  void setClientState(InteractionState paramInteractionState);
  
  @Nullable
  InteractionEntry getInteraction(int paramInt);
  
  void putInteractionSyncData(int paramInt, InteractionSyncData paramInteractionSyncData);
  
  void updateSyncPosition(int paramInt);
  
  boolean isSyncDataOutOfOrder(int paramInt);
  
  void syncFork(@Nonnull Ref<EntityStore> paramRef, @Nonnull InteractionManager paramInteractionManager, @Nonnull SyncInteractionChain paramSyncInteractionChain);
  
  void clearInteractionSyncData(int paramInt);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\ChainSyncStorage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */