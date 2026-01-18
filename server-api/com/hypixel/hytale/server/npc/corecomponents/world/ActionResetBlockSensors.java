/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionResetBlockSensors;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.role.support.WorldSupport;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionResetBlockSensors
/*    */   extends ActionBase {
/*    */   public ActionResetBlockSensors(@Nonnull BuilderActionResetBlockSensors builder, @Nonnull BuilderSupport support) {
/* 18 */     super((BuilderActionBase)builder);
/* 19 */     this.blockSets = builder.getBlockSets(support);
/* 20 */     for (int blockSet : this.blockSets)
/* 21 */       support.registerBlockSensorResetAction(blockSet); 
/*    */   }
/*    */   
/*    */   protected final int[] blockSets;
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 27 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 29 */     WorldSupport worldSupport = role.getWorldSupport();
/* 30 */     if (this.blockSets.length == 0) {
/* 31 */       worldSupport.resetAllBlockSensors();
/* 32 */       return true;
/*    */     } 
/*    */     
/* 35 */     for (int blockSet : this.blockSets) {
/* 36 */       worldSupport.resetBlockSensorFoundBlock(blockSet);
/*    */     }
/* 38 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\ActionResetBlockSensors.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */