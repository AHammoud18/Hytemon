/*    */ package com.hypixel.hytale.server.core.asset.type.gameplay;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.time.Duration;
/*    */ import java.time.LocalDateTime;
/*    */ import java.time.LocalTime;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SleepConfig
/*    */ {
/*    */   public static final BuilderCodec<SleepConfig> CODEC;
/*    */   
/*    */   static {
/* 30 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SleepConfig.class, SleepConfig::new).append(new KeyedCodec("WakeUpHour", (Codec)Codec.FLOAT), (sleepConfig, i) -> sleepConfig.wakeUpHour = i.floatValue(), o -> Float.valueOf(o.wakeUpHour)).documentation("The in-game hour at which players naturally wake up from sleep.").add()).append(new KeyedCodec("AllowedSleepHoursRange", (Codec)Codec.DOUBLE_ARRAY), (sleepConfig, i) -> sleepConfig.allowedSleepHoursRange = i, o -> o.allowedSleepHoursRange).addValidator(Validators.doubleArraySize(2)).documentation("The in-game hours during which players can sleep to skip to the WakeUpHour. If missing, there is no restriction.").add()).build();
/*    */   }
/* 32 */   public static final SleepConfig DEFAULT = new SleepConfig();
/*    */   
/* 34 */   private float wakeUpHour = 5.5F;
/*    */ 
/*    */   
/*    */   private double[] allowedSleepHoursRange;
/*    */ 
/*    */   
/*    */   public float getWakeUpHour() {
/* 41 */     return this.wakeUpHour;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public double[] getAllowedSleepHoursRange() {
/* 49 */     return this.allowedSleepHoursRange;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public LocalTime getSleepStartTime() {
/* 54 */     if (this.allowedSleepHoursRange == null) return null;
/*    */     
/* 56 */     double sleepStartHour = this.allowedSleepHoursRange[0];
/*    */     
/* 58 */     int hour = (int)sleepStartHour;
/* 59 */     int minute = (int)((sleepStartHour - hour) * 60.0D);
/*    */     
/* 61 */     return LocalTime.of(hour, minute);
/*    */   }
/*    */   
/*    */   public boolean isWithinSleepHoursRange(LocalDateTime gameTime) {
/* 65 */     if (this.allowedSleepHoursRange == null) return true;
/*    */     
/* 67 */     float hour = getFractionalHourOfDay(gameTime);
/* 68 */     double min = this.allowedSleepHoursRange[0];
/* 69 */     double max = this.allowedSleepHoursRange[1];
/*    */     
/* 71 */     return ((hour - min + 24.0D) % 24.0D <= (max - min + 24.0D) % 24.0D);
/*    */   }
/*    */   
/*    */   public Duration computeDurationUntilSleep(LocalDateTime now) {
/* 75 */     if (this.allowedSleepHoursRange == null) return Duration.ZERO;
/*    */     
/* 77 */     float currentHour = getFractionalHourOfDay(now);
/* 78 */     double sleepStartHour = this.allowedSleepHoursRange[0];
/*    */     
/* 80 */     double hoursUntilSleep = (sleepStartHour - currentHour + 24.0D) % 24.0D;
/*    */     
/* 82 */     long seconds = (long)(hoursUntilSleep * 3600.0D);
/* 83 */     return Duration.ofSeconds(seconds);
/*    */   }
/*    */   
/*    */   private static float getFractionalHourOfDay(LocalDateTime dateTime) {
/* 87 */     return dateTime.getHour() + dateTime
/* 88 */       .getMinute() / 60.0F + dateTime
/* 89 */       .getSecond() / 3600.0F;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\gameplay\SleepConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */