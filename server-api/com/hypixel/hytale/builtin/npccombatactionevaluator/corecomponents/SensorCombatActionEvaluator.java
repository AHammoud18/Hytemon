/*     */ package com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.CombatActionEvaluatorSystems;
/*     */ import com.hypixel.hytale.builtin.npccombatactionevaluator.corecomponents.builders.BuilderSensorCombatActionEvaluator;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.EntityPositionProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.MultipleParameterProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.ParameterProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.SingleDoubleParameterProvider;
/*     */ import com.hypixel.hytale.server.npc.valuestore.ValueStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SensorCombatActionEvaluator extends SensorBase {
/*  24 */   protected static final ComponentType<EntityStore, TransformComponent> TRANSFORM_COMPONENT_TYPE = TransformComponent.getComponentType();
/*     */   
/*     */   protected final boolean targetInRange;
/*     */   
/*     */   protected final double allowableDeviation;
/*     */   
/*     */   protected final int minRangeStoreSlot;
/*     */   protected final int maxRangeStoreSlot;
/*     */   protected final int positioningAngleStoreSlot;
/*     */   protected final int targetSlot;
/*     */   @Nonnull
/*     */   protected final SingleDoubleParameterProvider minRangeParameterProvider;
/*     */   @Nonnull
/*     */   protected final SingleDoubleParameterProvider maxRangeParameterProvider;
/*     */   @Nonnull
/*     */   protected final SingleDoubleParameterProvider positioningAngleParameterProvider;
/*  40 */   protected final MultipleParameterProvider parameterProvider = new MultipleParameterProvider();
/*  41 */   protected final EntityPositionProvider positionProvider = new EntityPositionProvider((ParameterProvider)this.parameterProvider);
/*     */   
/*     */   protected final ComponentType<EntityStore, ValueStore> valueStoreComponentType;
/*     */   
/*     */   public SensorCombatActionEvaluator(@Nonnull BuilderSensorCombatActionEvaluator builder, @Nonnull BuilderSupport support) {
/*  46 */     super((BuilderSensorBase)builder);
/*  47 */     this.targetInRange = builder.isTargetInRange(support);
/*  48 */     this.allowableDeviation = builder.getAllowableDeviation(support);
/*  49 */     this.targetSlot = builder.getTargetSlot(support);
/*     */     
/*  51 */     int minRangeParameter = support.getParameterSlot("MinRange");
/*  52 */     int maxRangeParameter = support.getParameterSlot("MaxRange");
/*  53 */     int positioningAngleParameter = support.getParameterSlot("PositioningAngle");
/*  54 */     this.minRangeParameterProvider = new SingleDoubleParameterProvider(minRangeParameter);
/*  55 */     this.maxRangeParameterProvider = new SingleDoubleParameterProvider(maxRangeParameter);
/*  56 */     this.positioningAngleParameterProvider = new SingleDoubleParameterProvider(positioningAngleParameter);
/*  57 */     this.parameterProvider.addParameterProvider(minRangeParameter, (ParameterProvider)this.minRangeParameterProvider);
/*  58 */     this.parameterProvider.addParameterProvider(maxRangeParameter, (ParameterProvider)this.maxRangeParameterProvider);
/*  59 */     this.parameterProvider.addParameterProvider(positioningAngleParameter, (ParameterProvider)this.positioningAngleParameterProvider);
/*     */     
/*  61 */     this.minRangeStoreSlot = builder.getMinRangeStoreSlot(support);
/*  62 */     this.maxRangeStoreSlot = builder.getMaxRangeStoreSlot(support);
/*  63 */     this.positioningAngleStoreSlot = builder.getPositioningAngleStoreSlot(support);
/*     */     
/*  65 */     this.valueStoreComponentType = ValueStore.getComponentType();
/*     */     
/*  67 */     Holder<EntityStore> holder = support.getHolder();
/*  68 */     CombatActionEvaluatorSystems.CombatConstructionData constructionData = (CombatActionEvaluatorSystems.CombatConstructionData)holder.ensureAndGetComponent(CombatActionEvaluatorSystems.CombatConstructionData.getComponentType());
/*  69 */     constructionData.setCombatState(support.getCurrentStateName());
/*     */     
/*  71 */     constructionData.setMarkedTargetSlot(support.getTargetSlot("CAETargetSlot"));
/*  72 */     constructionData.setMinRangeSlot(this.minRangeStoreSlot);
/*  73 */     constructionData.setMaxRangeSlot(this.maxRangeStoreSlot);
/*  74 */     constructionData.setPositioningAngleSlot(this.positioningAngleStoreSlot);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/*  79 */     if (!super.matches(ref, role, dt, store)) {
/*  80 */       this.positionProvider.clear();
/*  81 */       this.parameterProvider.clear();
/*  82 */       return false;
/*     */     } 
/*     */     
/*  85 */     Ref<EntityStore> target = role.getMarkedEntitySupport().getMarkedEntityRef(this.targetSlot);
/*  86 */     if (target == null) {
/*  87 */       this.positionProvider.clear();
/*  88 */       this.parameterProvider.clear();
/*  89 */       return false;
/*     */     } 
/*     */     
/*  92 */     this.positionProvider.setTarget(target, (ComponentAccessor)store);
/*     */     
/*  94 */     ValueStore valueStore = (ValueStore)store.getComponent(ref, this.valueStoreComponentType);
/*  95 */     double minRange = valueStore.readDouble(this.minRangeStoreSlot);
/*  96 */     double maxRange = valueStore.readDouble(this.maxRangeStoreSlot);
/*  97 */     this.minRangeParameterProvider.overrideDouble(minRange);
/*  98 */     this.maxRangeParameterProvider.overrideDouble(maxRange);
/*     */     
/* 100 */     double positioningAngle = valueStore.readDouble(this.positioningAngleStoreSlot);
/* 101 */     this.positioningAngleParameterProvider.overrideDouble(positioningAngle);
/*     */     
/* 103 */     Vector3d selfPosition = ((TransformComponent)store.getComponent(ref, TRANSFORM_COMPONENT_TYPE)).getPosition();
/* 104 */     Vector3d targetPosition = ((TransformComponent)store.getComponent(target, TRANSFORM_COMPONENT_TYPE)).getPosition();
/* 105 */     double distance = targetPosition.distanceTo(selfPosition);
/*     */ 
/*     */     
/* 108 */     return (this.targetInRange == ((distance <= maxRange + this.allowableDeviation)));
/*     */   }
/*     */ 
/*     */   
/*     */   public InfoProvider getSensorInfo() {
/* 113 */     return (InfoProvider)this.positionProvider;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\corecomponents\SensorCombatActionEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */