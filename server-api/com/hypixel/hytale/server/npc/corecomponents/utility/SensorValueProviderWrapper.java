/*     */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderSensorValueProviderWrapper;
/*     */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderValueToParameterMapping;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.instructions.Sensor;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import com.hypixel.hytale.server.npc.role.support.DebugSupport;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.ValueWrappedInfoProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.MultipleParameterProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.ParameterProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.SingleDoubleParameterProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.SingleIntParameterProvider;
/*     */ import com.hypixel.hytale.server.npc.sensorinfo.parameterproviders.SingleStringParameterProvider;
/*     */ import com.hypixel.hytale.server.npc.util.IAnnotatedComponent;
/*     */ import com.hypixel.hytale.server.npc.valuestore.ValueStore;
/*     */ import it.unimi.dsi.fastutil.ints.IntObjectPair;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class SensorValueProviderWrapper extends SensorBase implements IAnnotatedComponentCollection {
/*  32 */   protected static final IntObjectPair<?>[] EMPTY_ARRAY = (IntObjectPair<?>[])new IntObjectPair[0];
/*     */   
/*     */   @Nonnull
/*     */   protected final Sensor sensor;
/*     */   
/*     */   protected final boolean passValues;
/*     */   
/*     */   @Nonnull
/*     */   protected final IntObjectPair<SingleStringParameterProvider>[] stringParameterProviders;
/*     */   
/*     */   @Nonnull
/*     */   protected final IntObjectPair<SingleIntParameterProvider>[] intParameterProviders;
/*     */   @Nonnull
/*     */   protected final IntObjectPair<SingleDoubleParameterProvider>[] doubleParameterProviders;
/*     */   @Nonnull
/*     */   protected final ValueWrappedInfoProvider infoProvider;
/*  48 */   protected final MultipleParameterProvider multipleParameterProvider = new MultipleParameterProvider();
/*     */   
/*     */   protected final ComponentType<EntityStore, ValueStore> valueStoreComponentType;
/*     */   
/*     */   public SensorValueProviderWrapper(@Nonnull BuilderSensorValueProviderWrapper builder, @Nonnull BuilderSupport support, @Nonnull Sensor sensor) {
/*  53 */     super((BuilderSensorBase)builder);
/*  54 */     this.sensor = sensor;
/*  55 */     this.passValues = builder.isPassValues(support);
/*  56 */     this.infoProvider = new ValueWrappedInfoProvider(sensor.getSensorInfo(), (ParameterProvider)this.multipleParameterProvider);
/*     */     
/*  58 */     ObjectArrayList<IntObjectPair<SingleStringParameterProvider>> stringMappings = new ObjectArrayList();
/*  59 */     ObjectArrayList<IntObjectPair<SingleIntParameterProvider>> intMappings = new ObjectArrayList();
/*  60 */     ObjectArrayList<IntObjectPair<SingleDoubleParameterProvider>> doubleMappings = new ObjectArrayList();
/*  61 */     List<BuilderValueToParameterMapping.ValueToParameterMapping> parameterMappings = builder.getParameterMappings(support);
/*  62 */     if (parameterMappings != null) {
/*  63 */       for (int i = 0; i < parameterMappings.size(); i++) {
/*  64 */         SingleStringParameterProvider singleStringParameterProvider; SingleIntParameterProvider singleIntParameterProvider; SingleDoubleParameterProvider provider; BuilderValueToParameterMapping.ValueToParameterMapping mapping = parameterMappings.get(i);
/*  65 */         int slot = mapping.getToParameterSlot();
/*  66 */         switch (mapping.getType()) {
/*     */           case String:
/*  68 */             singleStringParameterProvider = new SingleStringParameterProvider(slot);
/*  69 */             this.multipleParameterProvider.addParameterProvider(slot, (ParameterProvider)singleStringParameterProvider);
/*  70 */             stringMappings.add(IntObjectPair.of(mapping.getFromValueSlot(), singleStringParameterProvider));
/*     */             break;
/*     */           case Int:
/*  73 */             singleIntParameterProvider = new SingleIntParameterProvider(slot);
/*  74 */             this.multipleParameterProvider.addParameterProvider(slot, (ParameterProvider)singleIntParameterProvider);
/*  75 */             intMappings.add(IntObjectPair.of(mapping.getFromValueSlot(), singleIntParameterProvider));
/*     */             break;
/*     */           case Double:
/*  78 */             provider = new SingleDoubleParameterProvider(slot);
/*  79 */             this.multipleParameterProvider.addParameterProvider(slot, (ParameterProvider)provider);
/*  80 */             doubleMappings.add(IntObjectPair.of(mapping.getFromValueSlot(), provider));
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/*     */     }
/*  86 */     if (stringMappings.isEmpty()) {
/*  87 */       this.stringParameterProviders = (IntObjectPair[])EMPTY_ARRAY;
/*     */     } else {
/*  89 */       this.stringParameterProviders = (IntObjectPair<SingleStringParameterProvider>[])stringMappings.toArray(x$0 -> new IntObjectPair[x$0]);
/*     */     } 
/*  91 */     if (intMappings.isEmpty()) {
/*  92 */       this.intParameterProviders = (IntObjectPair[])EMPTY_ARRAY;
/*     */     } else {
/*  94 */       this.intParameterProviders = (IntObjectPair<SingleIntParameterProvider>[])intMappings.toArray(x$0 -> new IntObjectPair[x$0]);
/*     */     } 
/*  96 */     if (doubleMappings.isEmpty()) {
/*  97 */       this.doubleParameterProviders = (IntObjectPair[])EMPTY_ARRAY;
/*     */     } else {
/*  99 */       this.doubleParameterProviders = (IntObjectPair<SingleDoubleParameterProvider>[])doubleMappings.toArray(x$0 -> new IntObjectPair[x$0]);
/*     */     } 
/*     */     
/* 102 */     this.valueStoreComponentType = ValueStore.getComponentType();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 107 */     if (!super.matches(ref, role, dt, store) || !this.sensor.matches(ref, role, dt, store)) {
/* 108 */       DebugSupport debugSupport = role.getDebugSupport();
/* 109 */       if (debugSupport.isTraceSensorFails()) debugSupport.setLastFailingSensor(this.sensor); 
/* 110 */       this.multipleParameterProvider.clear();
/* 111 */       return false;
/*     */     } 
/*     */     
/* 114 */     if (!this.passValues) return true;
/*     */     
/* 116 */     ValueStore valueStore = (ValueStore)store.getComponent(ref, this.valueStoreComponentType);
/* 117 */     if (valueStore == null) return false;
/*     */     
/* 119 */     for (IntObjectPair<SingleStringParameterProvider> provider : this.stringParameterProviders) {
/* 120 */       String value = valueStore.readString(provider.firstInt());
/* 121 */       ((SingleStringParameterProvider)provider.value()).overrideString(value);
/*     */     } 
/* 123 */     for (IntObjectPair<SingleIntParameterProvider> provider : this.intParameterProviders) {
/* 124 */       int value = valueStore.readInt(provider.firstInt());
/* 125 */       ((SingleIntParameterProvider)provider.value()).overrideInt(value);
/*     */     } 
/* 127 */     for (IntObjectPair<SingleDoubleParameterProvider> provider : this.doubleParameterProviders) {
/* 128 */       double value = valueStore.readDouble(provider.firstInt());
/* 129 */       ((SingleDoubleParameterProvider)provider.value()).overrideDouble(value);
/*     */     } 
/*     */     
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InfoProvider getSensorInfo() {
/* 137 */     return (InfoProvider)this.infoProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWithSupport(Role role) {
/* 142 */     this.sensor.registerWithSupport(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void motionControllerChanged(@Nullable Ref<EntityStore> ref, @Nonnull NPCEntity npcComponent, MotionController motionController, @Nullable ComponentAccessor<EntityStore> componentAccessor) {
/* 147 */     this.sensor.motionControllerChanged(ref, npcComponent, motionController, componentAccessor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loaded(Role role) {
/* 152 */     this.sensor.loaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawned(Role role) {
/* 157 */     this.sensor.spawned(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloaded(Role role) {
/* 162 */     this.sensor.unloaded(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Role role) {
/* 167 */     this.sensor.removed(role);
/*     */   }
/*     */ 
/*     */   
/*     */   public void teleported(Role role, World from, World to) {
/* 172 */     this.sensor.teleported(role, from, to);
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() {
/* 177 */     this.sensor.done();
/*     */   }
/*     */ 
/*     */   
/*     */   public int componentCount() {
/* 182 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public IAnnotatedComponent getComponent(int index) {
/* 188 */     if (index >= componentCount()) throw new IndexOutOfBoundsException(); 
/* 189 */     return (IAnnotatedComponent)this.sensor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContext(IAnnotatedComponent parent, int index) {
/* 194 */     super.setContext(parent, index);
/* 195 */     this.sensor.setContext((IAnnotatedComponent)this, index);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\SensorValueProviderWrapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */