/*    */ package com.hypixel.hytale.server.npc.corecomponents.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.ComponentContext;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorWithEntityFilters;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.entity.builders.BuilderSensorSelf;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.PositionProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorSelf
/*    */   extends SensorWithEntityFilters
/*    */ {
/* 20 */   protected final PositionProvider positionProvider = new PositionProvider();
/*    */   
/*    */   public SensorSelf(@Nonnull BuilderSensorSelf builder, @Nonnull BuilderSupport support) {
/* 23 */     super((BuilderSensorBase)builder, builder.getFilters(support, null, ComponentContext.SensorSelf));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 28 */     if (super.matches(ref, role, dt, store) && matchesFilters(ref, ref, role, store)) {
/* 29 */       this.positionProvider.setTarget(((TransformComponent)store.getComponent(ref, TransformComponent.getComponentType())).getPosition());
/* 30 */       return true;
/*    */     } 
/*    */     
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 38 */     return (InfoProvider)this.positionProvider;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\SensorSelf.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */