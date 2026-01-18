/*    */ package com.hypixel.hytale.server.npc.corecomponents.audiovisual;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderActionAppearance;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionAppearance
/*    */   extends ActionBase {
/*    */   public ActionAppearance(@Nonnull BuilderActionAppearance builderActionAppearance) {
/* 18 */     super((BuilderActionBase)builderActionAppearance);
/* 19 */     this.appearance = builderActionAppearance.getAppearance();
/*    */   }
/*    */   protected final String appearance;
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 24 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && this.appearance != null && !this.appearance.isEmpty());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 29 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 31 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, NPCEntity.getComponentType());
/* 32 */     assert npcComponent != null;
/*    */     
/* 34 */     NPCEntity.setAppearance(ref, this.appearance, (ComponentAccessor)store);
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\audiovisual\ActionAppearance.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */