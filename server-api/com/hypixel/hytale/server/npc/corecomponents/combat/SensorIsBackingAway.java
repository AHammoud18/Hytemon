/*    */ package com.hypixel.hytale.server.npc.corecomponents.combat;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.combat.builders.BuilderSensorIsBackingAway;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorIsBackingAway
/*    */   extends SensorBase
/*    */ {
/*    */   public SensorIsBackingAway(@Nonnull BuilderSensorIsBackingAway builder) {
/* 17 */     super((BuilderSensorBase)builder);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 22 */     if (!super.matches(ref, role, dt, store)) {
/* 23 */       return false;
/*    */     }
/*    */     
/* 26 */     return role.isBackingAway();
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 31 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\combat\SensorIsBackingAway.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */