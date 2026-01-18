/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionStorePosition;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ActionStorePosition extends ActionBase {
/*    */   protected final int slot;
/*    */   
/*    */   public ActionStorePosition(@Nonnull BuilderActionStorePosition builder, @Nonnull BuilderSupport support) {
/* 19 */     super((BuilderActionBase)builder);
/* 20 */     this.slot = builder.getSlot(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nullable InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 25 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && sensorInfo != null && sensorInfo.hasPosition());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, @Nonnull InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 30 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */ 
/*    */ 
/*    */     
/* 34 */     sensorInfo.getPositionProvider().providePosition(role.getMarkedEntitySupport().getStoredPosition(this.slot));
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\ActionStorePosition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */