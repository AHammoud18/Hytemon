/*     */ package com.hypixel.hytale.server.npc.corecomponents.entity.builders;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.Builder;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderContext;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderObjectArrayHelper;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderObjectReferenceHelper;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderValidationHelper;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.holder.BooleanHolder;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.holder.DoubleHolder;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleSingleValidator;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleValidator;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.validators.RelationalOperator;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringNullOrNotEmptyValidator;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.validators.StringValidator;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.ISensorEntityCollector;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.ISensorEntityPrioritiser;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.entity.prioritisers.SensorEntityPrioritiserDefault;
/*     */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*     */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*     */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*     */ import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public abstract class BuilderSensorEntityBase extends BuilderSensorWithEntityFilters {
/*  30 */   protected final DoubleHolder range = new DoubleHolder();
/*  31 */   protected final DoubleHolder minRange = new DoubleHolder();
/*  32 */   protected final BooleanHolder lockOnTarget = new BooleanHolder();
/*  33 */   protected final BooleanHolder autoUnlockTarget = new BooleanHolder();
/*  34 */   protected final BooleanHolder onlyLockedTarget = new BooleanHolder();
/*  35 */   protected final StringHolder lockedTargetSlot = new StringHolder();
/*  36 */   protected final StringHolder ignoredTargetSlot = new StringHolder();
/*  37 */   protected final BooleanHolder useProjectedDistance = new BooleanHolder();
/*     */   
/*  39 */   protected final BuilderObjectReferenceHelper<ISensorEntityPrioritiser> prioritiser = new BuilderObjectReferenceHelper(ISensorEntityPrioritiser.class, (BuilderContext)this);
/*  40 */   protected final BuilderObjectReferenceHelper<ISensorEntityCollector> collector = new BuilderObjectReferenceHelper(ISensorEntityCollector.class, (BuilderContext)this);
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Builder<Sensor> readConfig(@Nonnull JsonElement data) {
/*  45 */     getDouble(data, "MinRange", this.minRange, 0.0D, (DoubleValidator)DoubleSingleValidator.greaterEqual0(), BuilderDescriptorState.Stable, "Minimum range to test entities in", null);
/*  46 */     requireDouble(data, "Range", this.range, (DoubleValidator)DoubleSingleValidator.greater0(), BuilderDescriptorState.Stable, "Maximum range to test entities in", null);
/*     */     
/*  48 */     getBoolean(data, "LockOnTarget", this.lockOnTarget, false, BuilderDescriptorState.Stable, "Matched target becomes locked target", null);
/*  49 */     getString(data, "LockedTargetSlot", this.lockedTargetSlot, "LockedTarget", (StringValidator)StringNullOrNotEmptyValidator.get(), BuilderDescriptorState.Stable, "The target slot to use for locking on or unlocking", null);
/*  50 */     getBoolean(data, "AutoUnlockTarget", this.autoUnlockTarget, false, BuilderDescriptorState.Stable, "Unlock locked target when sensor not matching it anymore", null);
/*  51 */     getBoolean(data, "OnlyLockedTarget", this.onlyLockedTarget, false, BuilderDescriptorState.Stable, "Test only locked target", null);
/*  52 */     getString(data, "IgnoredTargetSlot", this.ignoredTargetSlot, null, (StringValidator)StringNullOrNotEmptyValidator.get(), BuilderDescriptorState.Stable, "The target slot to use for ignoring", null);
/*  53 */     getBoolean(data, "UseProjectedDistance", this.useProjectedDistance, false, BuilderDescriptorState.Stable, "Use the projected movement direction vector for distance, rather than the Euclidean distance", null);
/*  54 */     getObject(data, "Prioritiser", this.prioritiser, BuilderDescriptorState.Stable, "A prioritiser for selecting results based on additional parameters", null, this.validationHelper);
/*  55 */     getObject(data, "Collector", this.collector, BuilderDescriptorState.Stable, "A collector which can process all checked entities and act on them based on whether they match or not", null, this.validationHelper);
/*     */     
/*  57 */     BuilderValidationHelper builderHelper = createFilterValidationHelper(ComponentContext.SensorEntity);
/*  58 */     getArray(data, "Filters", (BuilderObjectArrayHelper)this.filters, null, BuilderDescriptorState.Stable, "A series of entity filter sensors to test", null, builderHelper);
/*     */     
/*  60 */     validateDoubleRelation(this.range, RelationalOperator.GreaterEqual, this.minRange);
/*  61 */     provideFeature(Feature.LiveEntity);
/*  62 */     return (Builder<Sensor>)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validate(String configName, @Nonnull NPCLoadTimeValidationHelper validationHelper, @Nonnull ExecutionContext context, Scope globalScope, @Nonnull List<String> errors) {
/*  68 */     int i = this.prioritiser.validate(configName, validationHelper, this.builderManager, context, globalScope, errors) & super.validate(configName, validationHelper, context, globalScope, errors) & this.collector.validate(configName, validationHelper, this.builderManager, context, globalScope, errors);
/*  69 */     validationHelper.clearPrioritiserProvidedFilterTypes();
/*  70 */     return i;
/*     */   }
/*     */   
/*     */   public double getRange(@Nonnull BuilderSupport builderSupport) {
/*  74 */     return this.range.get(builderSupport.getExecutionContext());
/*     */   }
/*     */   
/*     */   public double getMinRange(@Nonnull BuilderSupport builderSupport) {
/*  78 */     return this.minRange.get(builderSupport.getExecutionContext());
/*     */   }
/*     */   
/*     */   public boolean isLockOnTarget(@Nonnull BuilderSupport builderSupport) {
/*  82 */     return this.lockOnTarget.get(builderSupport.getExecutionContext());
/*     */   }
/*     */   
/*     */   public boolean isOnlyLockedTarget(@Nonnull BuilderSupport builderSupport) {
/*  86 */     return this.onlyLockedTarget.get(builderSupport.getExecutionContext());
/*     */   }
/*     */   
/*     */   public int getLockedTargetSlot(@Nonnull BuilderSupport support) {
/*  90 */     if (this.lockOnTarget.get(support.getExecutionContext()) || this.onlyLockedTarget.get(support.getExecutionContext())) {
/*  91 */       return support.getTargetSlot(this.lockedTargetSlot.get(support.getExecutionContext()));
/*     */     }
/*     */     
/*  94 */     return Integer.MIN_VALUE;
/*     */   }
/*     */   
/*     */   public int getIgnoredTargetSlot(@Nonnull BuilderSupport support) {
/*  98 */     String slot = this.ignoredTargetSlot.get(support.getExecutionContext());
/*  99 */     if (slot == null) return Integer.MIN_VALUE;
/*     */     
/* 101 */     return support.getTargetSlot(slot);
/*     */   }
/*     */   
/*     */   public boolean isAutoUnlockTarget(@Nonnull BuilderSupport builderSupport) {
/* 105 */     return this.autoUnlockTarget.get(builderSupport.getExecutionContext());
/*     */   }
/*     */   
/*     */   public boolean isUseProjectedDistance(@Nonnull BuilderSupport support) {
/* 109 */     return this.useProjectedDistance.get(support.getExecutionContext());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ISensorEntityPrioritiser getPrioritiser(@Nonnull BuilderSupport support) {
/* 114 */     if (!this.prioritiser.isPresent()) return (ISensorEntityPrioritiser)new SensorEntityPrioritiserDefault();
/*     */     
/* 116 */     return (ISensorEntityPrioritiser)this.prioritiser.build(support);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ISensorEntityCollector getCollector(@Nonnull BuilderSupport support) {
/* 121 */     return this.collector.isPresent() ? (ISensorEntityCollector)this.collector.build(support) : ISensorEntityCollector.DEFAULT;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\entity\builders\BuilderSensorEntityBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */