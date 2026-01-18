/*    */ package com.hypixel.hytale.server.npc.corecomponents.audiovisual;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderActionDisplayName;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionDisplayName extends ActionBase {
/*    */   protected final String displayName;
/*    */   
/*    */   public ActionDisplayName(@Nonnull BuilderActionDisplayName builder, @Nonnull BuilderSupport support) {
/* 18 */     super((BuilderActionBase)builder);
/* 19 */     this.displayName = builder.getDisplayName(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 24 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && this.displayName != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 29 */     super.execute(ref, role, sensorInfo, dt, store);
/* 30 */     role.getEntitySupport().nominateDisplayName(this.displayName);
/* 31 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\audiovisual\ActionDisplayName.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */