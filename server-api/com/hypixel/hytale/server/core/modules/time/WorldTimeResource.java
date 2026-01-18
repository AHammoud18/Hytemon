/*     */ package com.hypixel.hytale.server.core.modules.time;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.system.EcsEvent;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.math.util.TrigMathUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3f;
/*     */ import com.hypixel.hytale.protocol.InstantData;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.world.UpdateTime;
/*     */ import com.hypixel.hytale.protocol.packets.world.UpdateTimeSettings;
/*     */ import com.hypixel.hytale.server.core.asset.type.gameplay.WorldConfig;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.PlayerUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.events.ecs.MoonPhaseChangeEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.ZoneOffset;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.ChronoUnit;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class WorldTimeResource
/*     */   implements Resource<EntityStore>
/*     */ {
/*  31 */   public static final long NANOS_PER_DAY = ChronoUnit.DAYS.getDuration().toNanos();
/*  32 */   public static final int SECONDS_PER_DAY = (int)ChronoUnit.DAYS.getDuration().getSeconds();
/*  33 */   public static final int HOURS_PER_DAY = (int)ChronoUnit.DAYS.getDuration().toHours();
/*  34 */   public static final int DAYS_PER_YEAR = (int)ChronoUnit.YEARS.getDuration().toDays();
/*     */   
/*  36 */   public static final Instant ZERO_YEAR = Instant.parse("0001-01-01T00:00:00.00Z");
/*  37 */   public static final Instant MAX_TIME = Instant.ofEpochSecond(31553789759L, 99999999L);
/*  38 */   public static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;
/*     */   
/*     */   public static final float SUN_HEIGHT = 2.0F;
/*     */   
/*     */   public static final boolean USE_SHADOW_MAPPING_SAFE_ANGLE = true;
/*     */   
/*     */   public static final float DAYTIME_PORTION_PERCENTAGE = 0.6F;
/*  45 */   public static final int DAYTIME_SECONDS = (int)(SECONDS_PER_DAY * 0.6F);
/*  46 */   public static final int NIGHTTIME_SECONDS = (int)(SECONDS_PER_DAY * 0.39999998F);
/*  47 */   public static final int SUNRISE_SECONDS = NIGHTTIME_SECONDS / 2;
/*     */ 
/*     */   
/*     */   public static final float SHADOW_MAPPING_SAFE_ANGLE_LERP = 0.35F;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ResourceType<EntityStore, WorldTimeResource> getResourceType() {
/*  56 */     return TimeModule.get().getWorldTimeResourceType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  62 */   private final UpdateTime currentTimePacket = new UpdateTime();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Instant gameTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LocalDateTime _gameTimeLocalDateTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int currentHour;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double sunlightFactor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double scaledTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int moonPhase;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  98 */   private final UpdateTimeSettings currentSettings = new UpdateTimeSettings();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 104 */   private final UpdateTimeSettings tempSettings = new UpdateTimeSettings();
/*     */ 
/*     */   
/*     */   public static double getSecondsPerTick(World world) {
/* 108 */     int daytimeDurationSeconds = world.getDaytimeDurationSeconds();
/* 109 */     int nighttimeDurationSeconds = world.getNighttimeDurationSeconds();
/* 110 */     int totalDurationSeconds = daytimeDurationSeconds + nighttimeDurationSeconds;
/* 111 */     return SECONDS_PER_DAY / totalDurationSeconds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(float dt, @Nonnull Store<EntityStore> store) {
/*     */     double x0;
/* 121 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */ 
/*     */     
/* 124 */     if (!updateTimeSettingsPacket(this.tempSettings, world).equals(this.currentSettings)) {
/* 125 */       boolean wasTimePausedChanged = (this.currentSettings.timePaused != this.tempSettings.timePaused);
/*     */ 
/*     */       
/* 128 */       updateTimeSettingsPacket(this.currentSettings, world);
/*     */ 
/*     */       
/* 131 */       PlayerUtil.broadcastPacketToPlayers((ComponentAccessor)store, (Packet)this.currentSettings);
/*     */ 
/*     */       
/* 134 */       if (wasTimePausedChanged) broadcastTimePacket(store);
/*     */     
/*     */     } 
/* 137 */     if (world.getWorldConfig().isGameTimePaused())
/*     */       return; 
/* 139 */     int secondsOfDay = this._gameTimeLocalDateTime.get(ChronoField.SECOND_OF_DAY);
/*     */     
/* 141 */     int daytimeDurationSeconds = world.getDaytimeDurationSeconds();
/* 142 */     int nighttimeDurationSeconds = world.getNighttimeDurationSeconds();
/* 143 */     int totalDurationSeconds = daytimeDurationSeconds + nighttimeDurationSeconds;
/*     */     
/* 145 */     double daytimeRate = DAYTIME_SECONDS / daytimeDurationSeconds;
/* 146 */     double nighttimeRate = NIGHTTIME_SECONDS / nighttimeDurationSeconds;
/*     */ 
/*     */ 
/*     */     
/* 150 */     if (secondsOfDay >= SUNRISE_SECONDS && secondsOfDay < SUNRISE_SECONDS + DAYTIME_SECONDS) {
/* 151 */       x0 = (secondsOfDay - SUNRISE_SECONDS) / daytimeRate;
/*     */     } else {
/* 153 */       x0 = daytimeDurationSeconds + MathUtil.floorMod((secondsOfDay - SUNRISE_SECONDS - DAYTIME_SECONDS), SECONDS_PER_DAY) / nighttimeRate;
/*     */     } 
/* 155 */     double x1 = x0 + dt;
/*     */ 
/*     */     
/* 158 */     long whole = (long)Math.floor(x1 / totalDurationSeconds) - (long)Math.floor(x0 / totalDurationSeconds);
/*     */ 
/*     */     
/* 161 */     double m0 = MathUtil.floorMod(x0, totalDurationSeconds);
/* 162 */     double m1 = MathUtil.floorMod(x1, totalDurationSeconds);
/*     */ 
/*     */     
/* 165 */     double f0 = (m0 <= daytimeDurationSeconds) ? (daytimeRate * m0) : (DAYTIME_SECONDS + nighttimeRate * (m0 - daytimeDurationSeconds));
/* 166 */     double f1 = (m1 <= daytimeDurationSeconds) ? (daytimeRate * m1) : (DAYTIME_SECONDS + nighttimeRate * (m1 - daytimeDurationSeconds));
/*     */     
/* 168 */     double advance = (whole * SECONDS_PER_DAY) + f1 - f0;
/*     */ 
/*     */     
/* 171 */     Instant temp = this.gameTime.plusNanos((long)(advance * 1.0E9D));
/*     */ 
/*     */     
/* 174 */     if (temp.isBefore(ZERO_YEAR))
/*     */     {
/*     */       
/* 177 */       temp = MAX_TIME.minusSeconds(ZERO_YEAR.getEpochSecond() - this.gameTime.getEpochSecond()).minusNanos((ZERO_YEAR.getNano() - this.gameTime.getNano()));
/*     */     }
/* 179 */     if (temp.isAfter(MAX_TIME))
/*     */     {
/*     */       
/* 182 */       temp = ZERO_YEAR.plusSeconds(MAX_TIME.getEpochSecond() - this.gameTime.getEpochSecond()).plusNanos((MAX_TIME.getNano() - this.gameTime.getNano()));
/*     */     }
/*     */     
/* 185 */     setGameTime0(temp);
/* 186 */     updateMoonPhase(world, (ComponentAccessor<EntityStore>)store);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMoonPhase() {
/* 193 */     return this.moonPhase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMoonPhase(int moonPhase, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 203 */     if (moonPhase != this.moonPhase) {
/* 204 */       MoonPhaseChangeEvent event = new MoonPhaseChangeEvent(moonPhase);
/* 205 */       componentAccessor.invoke((EcsEvent)event);
/*     */     } 
/* 207 */     this.moonPhase = moonPhase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMoonPhase(@Nonnull World world, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 217 */     WorldConfig worldGameplayConfig = world.getGameplayConfig().getWorldConfig();
/* 218 */     int totalMoonPhases = worldGameplayConfig.getTotalMoonPhases();
/*     */     
/* 220 */     double dayProgress = this.currentHour / HOURS_PER_DAY;
/* 221 */     int currentDay = this._gameTimeLocalDateTime.getDayOfYear();
/*     */     
/* 223 */     int weekDay = (currentDay - 1) % totalMoonPhases;
/*     */ 
/*     */     
/* 226 */     if (dayProgress < 0.5D) {
/*     */       
/* 228 */       if (weekDay == 0) {
/* 229 */         setMoonPhase(totalMoonPhases - 1, componentAccessor);
/*     */       } else {
/* 231 */         setMoonPhase(weekDay - 1, componentAccessor);
/*     */       } 
/*     */     } else {
/* 234 */       setMoonPhase(weekDay, componentAccessor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMoonPhaseWithinRange(@Nonnull World world, int minMoonPhase, int maxMoonPhase) {
/* 247 */     WorldConfig worldGameplayConfig = world.getGameplayConfig().getWorldConfig();
/* 248 */     int totalMoonPhases = worldGameplayConfig.getTotalMoonPhases();
/*     */     
/* 250 */     if (minMoonPhase > maxMoonPhase) {
/* 251 */       return (MathUtil.within(this.moonPhase, minMoonPhase, totalMoonPhases) || MathUtil.within(this.moonPhase, 0.0D, maxMoonPhase));
/*     */     }
/* 253 */     return MathUtil.within(this.moonPhase, minMoonPhase, maxMoonPhase);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameTime0(@Nonnull Instant gameTime) {
/* 262 */     this.gameTime = gameTime;
/*     */     
/* 264 */     this._gameTimeLocalDateTime = LocalDateTime.ofInstant(gameTime, ZONE_OFFSET);
/*     */     
/* 266 */     updateTimePacket(this.currentTimePacket);
/*     */     
/* 268 */     this.currentHour = this._gameTimeLocalDateTime.getHour();
/*     */     
/* 270 */     int dayProgress = this._gameTimeLocalDateTime.get(ChronoField.SECOND_OF_DAY);
/* 271 */     float dayDuration = 0.6F * SECONDS_PER_DAY;
/* 272 */     float nightDuration = SECONDS_PER_DAY - dayDuration;
/* 273 */     float halfNight = nightDuration * 0.5F;
/*     */     
/* 275 */     updateSunlightFactor(dayProgress, halfNight);
/* 276 */     updateScaledTime(dayProgress, dayDuration, halfNight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateSunlightFactor(int dayProgress, float halfNight) {
/* 286 */     float dawnRelativeProgress = (dayProgress - halfNight) / SECONDS_PER_DAY;
/*     */ 
/*     */     
/* 289 */     this.sunlightFactor = MathUtil.clamp(TrigMathUtil.sin(6.2831855F * dawnRelativeProgress) + 0.2D, 0.0D, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateScaledTime(float dayProgress, float dayDuration, float halfNight) {
/* 300 */     if (dayProgress <= halfNight) {
/* 301 */       this.scaledTime = MathUtil.lerp(0.0F, 0.25F, dayProgress / halfNight);
/*     */       
/*     */       return;
/*     */     } 
/* 305 */     dayProgress -= halfNight;
/* 306 */     if (dayProgress <= dayDuration) {
/* 307 */       this.scaledTime = MathUtil.lerp(0.25F, 0.75F, dayProgress / dayDuration);
/*     */       
/*     */       return;
/*     */     } 
/* 311 */     dayProgress -= dayDuration;
/* 312 */     this.scaledTime = MathUtil.lerp(0.75F, 1.0F, dayProgress / halfNight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Instant getGameTime() {
/* 319 */     return this.gameTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalDateTime getGameDateTime() {
/* 326 */     return this._gameTimeLocalDateTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSunlightFactor() {
/* 333 */     return this.sunlightFactor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameTime(@Nonnull Instant gameTime, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 344 */     setGameTime0(gameTime);
/* 345 */     updateMoonPhase(world, (ComponentAccessor<EntityStore>)store);
/*     */     
/* 347 */     broadcastTimePacket(store);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDayTime(double dayTime, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 358 */     if (dayTime < 0.0D || dayTime > 1.0D) throw new IllegalArgumentException("Day time must be between 0 and 1"); 
/* 359 */     Instant oldGameTime = this.gameTime;
/*     */ 
/*     */     
/* 362 */     Instant dayStart = oldGameTime.truncatedTo(ChronoUnit.DAYS);
/*     */ 
/*     */     
/* 365 */     Instant newGameTime = dayStart.plusNanos((long)(dayTime * NANOS_PER_DAY));
/*     */ 
/*     */     
/* 368 */     if (newGameTime.isBefore(oldGameTime)) {
/* 369 */       setGameTime(newGameTime.plus(1L, ChronoUnit.DAYS), world, store);
/*     */     } else {
/* 371 */       setGameTime(newGameTime, world, store);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastTimePacket(@Nonnull Store<EntityStore> store) {
/* 382 */     PlayerUtil.broadcastPacketToPlayers((ComponentAccessor)store, (Packet)this.currentTimePacket);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendTimePackets(@Nonnull PlayerRef playerRef) {
/* 392 */     playerRef.getPacketHandler().write((Packet)this.currentSettings);
/* 393 */     playerRef.getPacketHandler().write((Packet)this.currentTimePacket);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDayTimeWithinRange(double minTime, double maxTime) {
/* 405 */     double dayProgress = this._gameTimeLocalDateTime.getHour() / HOURS_PER_DAY;
/* 406 */     if (minTime > maxTime) {
/* 407 */       return (MathUtil.within(dayProgress, minTime, 1.0D) || MathUtil.within(dayProgress, 0.0D, maxTime));
/*     */     }
/* 409 */     return MathUtil.within(dayProgress, minTime, maxTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTimePacket(@Nonnull UpdateTime currentTimePacket) {
/* 419 */     if (currentTimePacket.gameTime == null) currentTimePacket.gameTime = new InstantData(); 
/* 420 */     currentTimePacket.gameTime.seconds = this.gameTime.getEpochSecond();
/* 421 */     currentTimePacket.gameTime.nanos = this.gameTime.getNano();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateTimeSettings updateTimeSettingsPacket(@Nonnull UpdateTimeSettings settings, @Nonnull World world) {
/* 434 */     WorldConfig worldGameplayConfig = world.getGameplayConfig().getWorldConfig();
/* 435 */     settings.daytimeDurationSeconds = world.getDaytimeDurationSeconds();
/* 436 */     settings.nighttimeDurationSeconds = world.getNighttimeDurationSeconds();
/* 437 */     settings.totalMoonPhases = (byte)worldGameplayConfig.getTotalMoonPhases();
/* 438 */     settings.timePaused = world.getWorldConfig().isGameTimePaused();
/* 439 */     return settings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isScaledDayTimeWithinRange(double minTime, double maxTime) {
/* 455 */     if (minTime > maxTime) {
/* 456 */       return (MathUtil.within(this.scaledTime, minTime, 1.0D) || MathUtil.within(this.scaledTime, 0.0D, maxTime));
/*     */     }
/* 458 */     return MathUtil.within(this.scaledTime, minTime, maxTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isYearWithinRange(double minTime, double maxTime) {
/* 477 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentHour() {
/* 484 */     return this.currentHour;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDayProgress() {
/* 491 */     return this._gameTimeLocalDateTime.get(ChronoField.SECOND_OF_DAY) / SECONDS_PER_DAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3f getSunDirection() {
/* 501 */     float sunAngle, dayTime = getDayProgress() * HOURS_PER_DAY;
/*     */     
/* 503 */     float daylightDuration = 0.6F * HOURS_PER_DAY;
/* 504 */     float nightDuration = HOURS_PER_DAY - daylightDuration;
/* 505 */     float halfNightDuration = nightDuration * 0.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 511 */     if (dayTime < halfNightDuration) {
/* 512 */       float inverseAllNightDay = 1.0F / nightDuration * 2.0F;
/* 513 */       sunAngle = MathUtil.wrapAngle((dayTime * inverseAllNightDay - halfNightDuration * inverseAllNightDay) * 6.2831855F);
/* 514 */     } else if (dayTime > HOURS_PER_DAY - halfNightDuration) {
/* 515 */       float inverseAllNightDay = 1.0F / nightDuration * 2.0F;
/* 516 */       sunAngle = MathUtil.wrapAngle((dayTime * inverseAllNightDay - (HOURS_PER_DAY + halfNightDuration) * inverseAllNightDay) * 6.2831855F);
/*     */     } else {
/* 518 */       float halfDaylightDuration = daylightDuration * 0.5F;
/* 519 */       float inverseAllDaylightDay = 1.0F / daylightDuration * 2.0F;
/* 520 */       sunAngle = MathUtil.wrapAngle((dayTime * inverseAllDaylightDay - (HOURS_PER_DAY * 0.5F - halfDaylightDuration) * inverseAllDaylightDay) * 6.2831855F);
/*     */     } 
/*     */     
/* 523 */     Vector3f sunPosition = new Vector3f(TrigMathUtil.cos(sunAngle), TrigMathUtil.sin(sunAngle) * 2.0F, TrigMathUtil.sin(sunAngle));
/* 524 */     sunPosition.normalize();
/*     */ 
/*     */     
/* 527 */     float tweakedSunHeight = sunPosition.y + 0.2F;
/*     */ 
/*     */     
/* 530 */     if (tweakedSunHeight > 0.0F)
/*     */     {
/* 532 */       sunPosition.scale(-1.0F);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 541 */     sunPosition.x = MathUtil.lerp(sunPosition.x, Vector3f.DOWN.x, 0.35F);
/* 542 */     sunPosition.y = MathUtil.lerp(sunPosition.y, Vector3f.DOWN.y, 0.35F);
/* 543 */     sunPosition.z = MathUtil.lerp(sunPosition.z, Vector3f.DOWN.z, 0.35F);
/*     */ 
/*     */     
/* 546 */     return sunPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static InstantData instantToInstantData(@Nonnull Instant instant) {
/* 557 */     return new InstantData(instant.getEpochSecond(), instant.getNano());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Instant instantDataToInstant(@Nonnull InstantData instantData) {
/* 568 */     return Instant.ofEpochSecond(instantData.seconds, instantData.nanos);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Resource<EntityStore> clone() {
/* 574 */     WorldTimeResource worldTimeComponent = new WorldTimeResource();
/* 575 */     worldTimeComponent.gameTime = this.gameTime;
/* 576 */     worldTimeComponent._gameTimeLocalDateTime = this._gameTimeLocalDateTime;
/* 577 */     worldTimeComponent.currentHour = this.currentHour;
/* 578 */     worldTimeComponent.sunlightFactor = this.sunlightFactor;
/* 579 */     worldTimeComponent.scaledTime = this.scaledTime;
/* 580 */     return worldTimeComponent;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 586 */     return "WorldTimeResource{, gameTime=" + String.valueOf(this.gameTime) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\time\WorldTimeResource.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */