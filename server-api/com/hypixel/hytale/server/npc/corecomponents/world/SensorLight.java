/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorLight;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.spawning.util.LightRangePredicate;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorLight extends SensorBase {
/*    */   protected final int useTargetSlot;
/*    */   
/*    */   public SensorLight(@Nonnull BuilderSensorLight builderSensorLight, @Nonnull BuilderSupport builderSupport) {
/* 22 */     super((BuilderSensorBase)builderSensorLight);
/*    */     
/* 24 */     this.useTargetSlot = builderSensorLight.getUsedTargetSlot(builderSupport);
/*    */     
/* 26 */     this.lightRangePredicate = new LightRangePredicate();
/* 27 */     this.lightRangePredicate.setLightRange(builderSensorLight.getLightRange(builderSupport));
/* 28 */     this.lightRangePredicate.setSkyLightRange(builderSensorLight.getSkyLightRange(builderSupport));
/* 29 */     this.lightRangePredicate.setSunlightRange(builderSensorLight.getSunlightRange(builderSupport));
/* 30 */     this.lightRangePredicate.setRedLightRange(builderSensorLight.getRedLightRange(builderSupport));
/* 31 */     this.lightRangePredicate.setGreenLightRange(builderSensorLight.getGreenLightRange(builderSupport));
/* 32 */     this.lightRangePredicate.setBlueLightRange(builderSensorLight.getBlueLightRange(builderSupport));
/*    */   } @Nonnull
/*    */   protected final LightRangePredicate lightRangePredicate;
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/*    */     Ref<EntityStore> entityReference;
/* 37 */     if (!super.matches(ref, role, dt, store)) return false;
/*    */ 
/*    */     
/* 40 */     if (this.useTargetSlot >= 0) {
/* 41 */       entityReference = role.getMarkedEntitySupport().getMarkedEntityRef(this.useTargetSlot);
/* 42 */       if (entityReference == null) return false; 
/*    */     } else {
/* 44 */       entityReference = ref;
/*    */     } 
/*    */     
/* 47 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(entityReference, TransformComponent.getComponentType());
/* 48 */     assert transformComponent != null;
/*    */     
/* 50 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 51 */     return this.lightRangePredicate.test(world, transformComponent.getPosition(), (ComponentAccessor)store);
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 56 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\SensorLight.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */