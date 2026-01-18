/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionResetSearchRays;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.role.support.WorldSupport;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionResetSearchRays
/*    */   extends ActionBase {
/*    */   public ActionResetSearchRays(@Nonnull BuilderActionResetSearchRays builder, @Nonnull BuilderSupport support) {
/* 18 */     super((BuilderActionBase)builder);
/* 19 */     this.searchRayIds = builder.getIds(support);
/*    */   }
/*    */   protected final int[] searchRayIds;
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 24 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 26 */     WorldSupport worldSupport = role.getWorldSupport();
/* 27 */     if (this.searchRayIds.length == 0) {
/* 28 */       worldSupport.resetAllCachedSearchRayPositions();
/* 29 */       return true;
/*    */     } 
/*    */     
/* 32 */     for (int id : this.searchRayIds) {
/* 33 */       worldSupport.resetCachedSearchRayPosition(id);
/*    */     }
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\ActionResetSearchRays.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */