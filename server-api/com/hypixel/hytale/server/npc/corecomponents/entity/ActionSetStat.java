/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderActionSetStat;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionSetStat extends ActionBase {
/* 17 */   protected static final ComponentType<EntityStore, EntityStatMap> STAT_MAP_COMPONENT_TYPE = EntityStatMap.getComponentType();
/*    */   
/*    */   protected final int stat;
/*    */   protected final float value;
/*    */   protected final boolean add;
/*    */   
/*    */   public ActionSetStat(@Nonnull BuilderActionSetStat builder, @Nonnull BuilderSupport support) {
/* 24 */     super((BuilderActionBase)builder);
/* 25 */     this.stat = builder.getStat(support);
/* 26 */     this.value = builder.getValue(support);
/* 27 */     this.add = builder.isAdd(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 32 */     if (!super.canExecute(ref, role, sensorInfo, dt, store)) return false; 
/* 33 */     return (((EntityStatMap)store.getComponent(ref, STAT_MAP_COMPONENT_TYPE)).get(this.stat) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 38 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 40 */     EntityStatMap entityStatMapComponent = (EntityStatMap)store.getComponent(ref, STAT_MAP_COMPONENT_TYPE);
/* 41 */     assert entityStatMapComponent != null;
/*    */     
/* 43 */     if (this.add) {
/* 44 */       entityStatMapComponent.addStatValue(this.stat, this.value);
/*    */     } else {
/* 46 */       entityStatMapComponent.setStatValue(this.stat, this.value);
/*    */     } 
/* 48 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\ActionSetStat.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */