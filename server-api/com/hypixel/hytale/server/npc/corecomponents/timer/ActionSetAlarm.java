/*    */ package com.hypixel.hytale.server.npc.corecomponents.timer;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.random.RandomExtra;
/*    */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.timer.builders.BuilderActionSetAlarm;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.util.Alarm;
/*    */ import java.time.Instant;
/*    */ import java.time.temporal.ChronoUnit;
/*    */ import java.time.temporal.TemporalAmount;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionSetAlarm
/*    */   extends ActionBase {
/*    */   protected final Alarm alarm;
/*    */   protected final TemporalAmount minDuration;
/*    */   
/*    */   public ActionSetAlarm(@Nonnull BuilderActionSetAlarm builder, @Nonnull BuilderSupport support) {
/* 27 */     super((BuilderActionBase)builder);
/* 28 */     this.alarm = builder.getAlarm(support);
/*    */     
/* 30 */     TemporalAmount[] durations = builder.getDurationRange(support);
/* 31 */     this.minDuration = durations[0];
/*    */     
/* 33 */     Instant max = Instant.EPOCH.plus(durations[1]);
/* 34 */     this.randomVariation = max.minus(this.minDuration).getEpochSecond();
/* 35 */     this.cancel = (max.getEpochSecond() == 0L);
/*    */   }
/*    */   protected final long randomVariation; protected final boolean cancel;
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 40 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 42 */     if (this.cancel) {
/* 43 */       this.alarm.set(ref, null, (ComponentAccessor)store);
/*    */     } else {
/* 45 */       WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(WorldTimeResource.getResourceType());
/* 46 */       this.alarm.set(ref, worldTimeResource.getGameTime().plus(this.minDuration).plus(RandomExtra.randomRange(0L, this.randomVariation), ChronoUnit.SECONDS), (ComponentAccessor)store);
/*    */     } 
/* 48 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\timer\ActionSetAlarm.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */