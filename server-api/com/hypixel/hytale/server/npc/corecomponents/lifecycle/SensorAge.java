/*    */ package com.hypixel.hytale.server.npc.corecomponents.lifecycle;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.lifecycle.builders.BuilderSensorAge;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import java.time.Instant;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorAge extends SensorBase {
/*    */   protected final Instant minAgeInstant;
/*    */   protected final Instant maxAgeInstant;
/*    */   
/*    */   public SensorAge(@Nonnull BuilderSensorAge builderSensorAge, @Nonnull BuilderSupport builderSupport) {
/* 21 */     super((BuilderSensorBase)builderSensorAge);
/* 22 */     Instant[] ageRange = builderSensorAge.getAgeRange(builderSupport);
/* 23 */     this.minAgeInstant = ageRange[0];
/* 24 */     this.maxAgeInstant = ageRange[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 29 */     if (!super.matches(ref, role, dt, store)) return false;
/*    */     
/* 31 */     WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(WorldTimeResource.getResourceType());
/* 32 */     Instant currentInstant = worldTimeResource.getGameTime();
/*    */     
/* 34 */     return (!currentInstant.isBefore(this.minAgeInstant) && !currentInstant.isAfter(this.maxAgeInstant));
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 39 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\lifecycle\SensorAge.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */