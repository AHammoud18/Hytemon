/*    */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorSwitch;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorSwitch extends SensorBase {
/*    */   protected final boolean flag;
/*    */   
/*    */   public SensorSwitch(@Nonnull BuilderSensorSwitch builderSensorSwitch, @Nonnull BuilderSupport builderSupport) {
/* 18 */     super((BuilderSensorBase)builderSensorSwitch);
/* 19 */     this.flag = builderSensorSwitch.getSwitch(builderSupport);
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 24 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 29 */     return (super.matches(ref, role, dt, store) && this.flag);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\SensorSwitch.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */