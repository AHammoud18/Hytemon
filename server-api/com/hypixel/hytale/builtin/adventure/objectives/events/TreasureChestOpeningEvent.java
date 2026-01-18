/*    */ package com.hypixel.hytale.builtin.adventure.objectives.events;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TreasureChestOpeningEvent
/*    */   implements IEvent<String>
/*    */ {
/*    */   @Nonnull
/*    */   private final UUID objectiveUUID;
/*    */   @Nonnull
/*    */   private final UUID chestUUID;
/*    */   @Nonnull
/*    */   private final Ref<EntityStore> playerRef;
/*    */   @Nonnull
/*    */   private final Store<EntityStore> store;
/*    */   
/*    */   public TreasureChestOpeningEvent(@Nonnull UUID objectiveUUID, @Nonnull UUID chestUUID, @Nonnull Ref<EntityStore> playerRef, @Nonnull Store<EntityStore> store) {
/* 53 */     this.objectiveUUID = objectiveUUID;
/* 54 */     this.chestUUID = chestUUID;
/* 55 */     this.playerRef = playerRef;
/* 56 */     this.store = store;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public UUID getObjectiveUUID() {
/* 64 */     return this.objectiveUUID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public UUID getChestUUID() {
/* 72 */     return this.chestUUID;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Ref<EntityStore> getPlayerRef() {
/* 80 */     return this.playerRef;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Store<EntityStore> getStore() {
/* 88 */     return this.store;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 94 */     return "TreasureChestOpeningEvent{objectiveUUID=" + String.valueOf(this.objectiveUUID) + ", chestUUID=" + String.valueOf(this.chestUUID) + ", player=" + String.valueOf(this.playerRef) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\events\TreasureChestOpeningEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */