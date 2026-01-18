/*    */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorFlag;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorFlag extends SensorBase {
/*    */   protected final int flagIndex;
/*    */   protected final boolean value;
/*    */   
/*    */   public SensorFlag(@Nonnull BuilderSensorFlag builder, @Nonnull BuilderSupport support) {
/* 19 */     super((BuilderSensorBase)builder);
/* 20 */     this.flagIndex = builder.getFlagSlot(support);
/* 21 */     this.value = builder.getValue(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 26 */     return (super.matches(ref, role, dt, store) && role.isFlagSet(this.flagIndex) == this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 31 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\SensorFlag.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */