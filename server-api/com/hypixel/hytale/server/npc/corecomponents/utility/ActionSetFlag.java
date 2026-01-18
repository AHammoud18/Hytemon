/*    */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderActionSetFlag;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionSetFlag extends ActionBase {
/*    */   protected final int flagIndex;
/*    */   protected final boolean value;
/*    */   
/*    */   public ActionSetFlag(@Nonnull BuilderActionSetFlag builder, @Nonnull BuilderSupport support) {
/* 19 */     super((BuilderActionBase)builder);
/* 20 */     this.flagIndex = builder.getFlagSlot(support);
/* 21 */     this.value = builder.getValue(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 26 */     super.execute(ref, role, sensorInfo, dt, store);
/* 27 */     role.setFlag(this.flagIndex, this.value);
/* 28 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\ActionSetFlag.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */