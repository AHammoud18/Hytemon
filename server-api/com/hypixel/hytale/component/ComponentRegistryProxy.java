/*     */ package com.hypixel.hytale.component;
/*     */ 
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.event.EntityEventType;
/*     */ import com.hypixel.hytale.component.event.WorldEventType;
/*     */ import com.hypixel.hytale.component.spatial.SpatialResource;
/*     */ import com.hypixel.hytale.component.spatial.SpatialStructure;
/*     */ import com.hypixel.hytale.component.system.EcsEvent;
/*     */ import com.hypixel.hytale.component.system.ISystem;
/*     */ import com.hypixel.hytale.function.consumer.BooleanConsumer;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ComponentRegistryProxy<ECS_TYPE>
/*     */   implements IComponentRegistry<ECS_TYPE>
/*     */ {
/*     */   private final ComponentRegistry<ECS_TYPE> registry;
/*     */   private final List<BooleanConsumer> unregister;
/*     */   
/*     */   public ComponentRegistryProxy(List<BooleanConsumer> registrations, ComponentRegistry<ECS_TYPE> registry) {
/*  22 */     this.unregister = registrations;
/*  23 */     this.registry = registry;
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdown() {}
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <T extends Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponent(@Nonnull Class<? super T> tClass, @Nonnull Supplier<T> supplier) {
/*  32 */     return registerComponentType(this.registry.registerComponent(tClass, supplier));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <T extends Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponent(@Nonnull Class<? super T> tClass, @Nonnull String id, @Nonnull BuilderCodec<T> codec) {
/*  38 */     return registerComponentType(this.registry.registerComponent(tClass, id, codec));
/*     */   }
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   @Nonnull
/*     */   public <T extends Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponent(@Nonnull Class<? super T> tClass, @Nonnull String id, @Nonnull BuilderCodec<T> codec, boolean skipValidation) {
/*  44 */     return registerComponentType(this.registry.registerComponent(tClass, id, codec, skipValidation));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <T extends Resource<ECS_TYPE>> ResourceType<ECS_TYPE, T> registerResource(@Nonnull Class<? super T> tClass, @Nonnull Supplier<T> supplier) {
/*  50 */     return registerResourceType(this.registry.registerResource(tClass, supplier));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <T extends Resource<ECS_TYPE>> ResourceType<ECS_TYPE, T> registerResource(@Nonnull Class<? super T> tClass, @Nonnull String id, @Nonnull BuilderCodec<T> codec) {
/*  56 */     return registerResourceType(this.registry.registerResource(tClass, id, codec));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ResourceType<ECS_TYPE, SpatialResource<Ref<ECS_TYPE>, ECS_TYPE>> registerSpatialResource(@Nonnull Supplier<SpatialStructure<Ref<ECS_TYPE>>> supplier) {
/*  62 */     return registerResourceType(this.registry.registerSpatialResource(supplier));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <T extends ISystem<ECS_TYPE>> SystemType<ECS_TYPE, T> registerSystemType(@Nonnull Class<? super T> systemTypeClass) {
/*  68 */     return registerSystemType(this.registry.registerSystemType(systemTypeClass));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <T extends EcsEvent> EntityEventType<ECS_TYPE, T> registerEntityEventType(@Nonnull Class<? super T> eventTypeClass) {
/*  74 */     return registerEntityEventType(this.registry.registerEntityEventType(eventTypeClass));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public <T extends EcsEvent> WorldEventType<ECS_TYPE, T> registerWorldEventType(@Nonnull Class<? super T> eventTypeClass) {
/*  80 */     return registerWorldEventType(this.registry.registerWorldEventType(eventTypeClass));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public SystemGroup<ECS_TYPE> registerSystemGroup() {
/*  86 */     return registerSystemGroup(this.registry.registerSystemGroup());
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSystem(@Nonnull ISystem<ECS_TYPE> system) {
/*  91 */     Class<? extends ISystem<ECS_TYPE>> systemClass = system.getClass();
/*  92 */     this.registry.registerSystem(system);
/*  93 */     this.unregister.add(shutdown -> {
/*     */           if (shutdown)
/*     */             return; 
/*     */           if (this.registry.hasSystemClass(systemClass))
/*     */             this.registry.unregisterSystem(systemClass); 
/*     */         });
/*     */   }
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public void registerSystem(@Nonnull ISystem<ECS_TYPE> system, boolean bypassClassCheck) {
/* 103 */     Class<? extends ISystem<ECS_TYPE>> systemClass = system.getClass();
/* 104 */     this.registry.registerSystem(system, bypassClassCheck);
/* 105 */     this.unregister.add(shutdown -> {
/*     */           if (shutdown)
/*     */             return; 
/*     */           if (this.registry.hasSystemClass(systemClass))
/*     */             this.registry.unregisterSystem(systemClass); 
/*     */         });
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private <T extends Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponentType(@Nonnull ComponentType<ECS_TYPE, T> componentType) {
/* 115 */     this.unregister.add(shutdown -> {
/*     */           if (shutdown)
/*     */             return; 
/*     */           if (componentType.isValid())
/*     */             this.registry.unregisterComponent(componentType); 
/*     */         });
/* 121 */     return componentType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private <T extends Resource<ECS_TYPE>> ResourceType<ECS_TYPE, T> registerResourceType(@Nonnull ResourceType<ECS_TYPE, T> componentType) {
/* 126 */     this.unregister.add(shutdown -> {
/*     */           if (shutdown)
/*     */             return; 
/*     */           if (componentType.isValid())
/*     */             this.registry.unregisterResource(componentType); 
/*     */         });
/* 132 */     return componentType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private <T extends ISystem<ECS_TYPE>> SystemType<ECS_TYPE, T> registerSystemType(@Nonnull SystemType<ECS_TYPE, T> systemType) {
/* 137 */     this.unregister.add(shutdown -> {
/*     */           if (shutdown)
/*     */             return; 
/*     */           if (systemType.isValid())
/*     */             this.registry.unregisterSystemType(systemType); 
/*     */         });
/* 143 */     return systemType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private <T extends EcsEvent> EntityEventType<ECS_TYPE, T> registerEntityEventType(@Nonnull EntityEventType<ECS_TYPE, T> eventType) {
/* 148 */     this.unregister.add(shutdown -> {
/*     */           if (shutdown)
/*     */             return; 
/*     */           if (eventType.isValid())
/*     */             this.registry.unregisterEntityEventType(eventType); 
/*     */         });
/* 154 */     return eventType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private <T extends EcsEvent> WorldEventType<ECS_TYPE, T> registerWorldEventType(@Nonnull WorldEventType<ECS_TYPE, T> eventType) {
/* 159 */     this.unregister.add(shutdown -> {
/*     */           if (shutdown)
/*     */             return; 
/*     */           if (eventType.isValid())
/*     */             this.registry.unregisterWorldEventType(eventType); 
/*     */         });
/* 165 */     return eventType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private SystemGroup<ECS_TYPE> registerSystemGroup(@Nonnull SystemGroup<ECS_TYPE> systemGroup) {
/* 170 */     this.unregister.add(shutdown -> {
/*     */           if (shutdown)
/*     */             return; 
/*     */           if (systemGroup.isValid())
/*     */             this.registry.unregisterSystemGroup(systemGroup); 
/*     */         });
/* 176 */     return systemGroup;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\ComponentRegistryProxy.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */