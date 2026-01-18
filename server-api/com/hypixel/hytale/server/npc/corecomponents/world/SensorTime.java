/*    */ package com.hypixel.hytale.server.npc.corecomponents.world;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderSensorTime;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorTime extends SensorBase {
/*    */   protected final double minTime;
/*    */   protected final double maxTime;
/*    */   protected final boolean checkDay;
/*    */   protected final boolean checkYear;
/*    */   protected final boolean scaleDayTimeRange;
/*    */   
/*    */   public SensorTime(@Nonnull BuilderSensorTime builderSensorTime, @Nonnull BuilderSupport support) {
/* 23 */     super((BuilderSensorBase)builderSensorTime);
/*    */     
/* 25 */     double[] timePeriod = builderSensorTime.getPeriod(support);
/*    */     
/* 27 */     this.minTime = timePeriod[0] / WorldTimeResource.HOURS_PER_DAY;
/* 28 */     this.maxTime = timePeriod[1] / WorldTimeResource.HOURS_PER_DAY;
/* 29 */     this.checkDay = builderSensorTime.isCheckDay();
/* 30 */     this.checkYear = builderSensorTime.isCheckYear();
/* 31 */     this.scaleDayTimeRange = builderSensorTime.isScaleDayTimeRange();
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 41 */     if (!super.matches(ref, role, dt, store)) return false;
/*    */     
/* 43 */     WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(WorldTimeResource.getResourceType());
/*    */     
/* 45 */     if (this.checkDay) {
/* 46 */       boolean withinTimeRange = this.scaleDayTimeRange ? worldTimeResource.isScaledDayTimeWithinRange(this.minTime, this.maxTime) : worldTimeResource.isDayTimeWithinRange(this.minTime, this.maxTime);
/* 47 */       return (withinTimeRange && (!this.checkYear || worldTimeResource.isYearWithinRange(this.minTime, this.maxTime)));
/*    */     } 
/*    */     
/* 50 */     if (this.checkYear) return worldTimeResource.isYearWithinRange(this.minTime, this.maxTime); 
/* 51 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\world\SensorTime.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */