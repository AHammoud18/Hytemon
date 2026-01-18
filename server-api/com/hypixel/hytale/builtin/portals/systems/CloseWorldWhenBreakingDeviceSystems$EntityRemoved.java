/*    */ package com.hypixel.hytale.builtin.portals.systems;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.components.PortalDevice;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityRemoved
/*    */   extends RefSystem<ChunkStore>
/*    */ {
/*    */   public void onEntityAdded(@NonNullDecl Ref<ChunkStore> ref, @NonNullDecl AddReason reason, @NonNullDecl Store<ChunkStore> store, @NonNullDecl CommandBuffer<ChunkStore> commandBuffer) {}
/*    */   
/*    */   public void onEntityRemove(@NonNullDecl Ref<ChunkStore> ref, @NonNullDecl RemoveReason reason, @NonNullDecl Store<ChunkStore> store, @NonNullDecl CommandBuffer<ChunkStore> commandBuffer) {
/* 53 */     PortalDevice device = (PortalDevice)store.getComponent(ref, PortalDevice.getComponentType());
/* 54 */     CloseWorldWhenBreakingDeviceSystems.maybeCloseFragmentWorld(device);
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<ChunkStore> getQuery() {
/* 59 */     return (Query<ChunkStore>)PortalDevice.getComponentType();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\systems\CloseWorldWhenBreakingDeviceSystems$EntityRemoved.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */