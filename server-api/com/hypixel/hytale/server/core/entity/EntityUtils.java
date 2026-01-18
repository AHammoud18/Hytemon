/*     */ package com.hypixel.hytale.server.core.entity;
/*     */ 
/*     */ import com.hypixel.hytale.component.Archetype;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityUtils
/*     */ {
/*     */   @Nonnull
/*     */   public static Holder<EntityStore> toHolder(int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk) {
/*  26 */     Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
/*  27 */     Archetype<EntityStore> archetype = archetypeChunk.getArchetype();
/*     */     
/*  29 */     for (int i = archetype.getMinIndex(); i < archetype.length(); i++) {
/*  30 */       ComponentType componentType = archetype.get(i);
/*  31 */       if (componentType != null) {
/*     */         
/*  33 */         Component component = archetypeChunk.getComponent(index, componentType);
/*  34 */         if (component != null)
/*  35 */           holder.addComponent(componentType, component); 
/*     */       } 
/*     */     } 
/*  38 */     return holder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static <T extends Entity> ComponentType<EntityStore, T> findComponentType(@Nonnull Archetype<EntityStore> archetype) {
/*  50 */     return (ComponentType)findComponentType(archetype, Entity.class);
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
/*     */   @Nullable
/*     */   private static <C extends Component<EntityStore>, T extends C> ComponentType<EntityStore, T> findComponentType(@Nonnull Archetype<EntityStore> archetype, @Nonnull Class<C> entityClass) {
/*  64 */     for (int i = archetype.getMinIndex(); i < archetype.length(); i++) {
/*  65 */       ComponentType<EntityStore, ? extends Component<EntityStore>> componentType = archetype.get(i);
/*  66 */       if (componentType != null && 
/*  67 */         entityClass.isAssignableFrom(componentType.getTypeClass()))
/*     */       {
/*  69 */         return (ComponentType)componentType;
/*     */       }
/*     */     } 
/*     */     
/*  73 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @Nullable
/*     */   public static Entity getEntity(@Nullable Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  86 */     if (ref == null || !ref.isValid()) return null;
/*     */     
/*  88 */     ComponentType<EntityStore, Entity> componentType = findComponentType(componentAccessor.getArchetype(ref));
/*  89 */     if (componentType == null) return null;
/*     */     
/*  91 */     return (Entity)componentAccessor.getComponent(ref, componentType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public static Entity getEntity(int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk) {
/* 104 */     ComponentType<EntityStore, Entity> componentType = findComponentType(archetypeChunk.getArchetype());
/* 105 */     if (componentType == null) return null;
/*     */     
/* 107 */     return (Entity)archetypeChunk.getComponent(index, componentType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public static Entity getEntity(@Nonnull Holder<EntityStore> holder) {
/* 119 */     Archetype<EntityStore> archetype = holder.getArchetype();
/* 120 */     if (archetype == null) return null;
/*     */     
/* 122 */     ComponentType<EntityStore, Entity> componentType = findComponentType(archetype);
/* 123 */     if (componentType == null) return null;
/*     */     
/* 125 */     return (Entity)holder.getComponent(componentType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean hasEntity(@Nonnull Archetype<EntityStore> archetype) {
/* 136 */     return (findComponentType(archetype) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean hasLivingEntity(@Nonnull Archetype<EntityStore> archetype) {
/* 147 */     return (findComponentType(archetype, LivingEntity.class) != null);
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
/*     */   @Deprecated
/*     */   public static PhysicsValues getPhysicsValues(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 161 */     PhysicsValues physicsValuesComponent = (PhysicsValues)componentAccessor.getComponent(ref, PhysicsValues.getComponentType());
/* 162 */     if (physicsValuesComponent != null) {
/* 163 */       return physicsValuesComponent;
/*     */     }
/*     */     
/* 166 */     ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(ref, ModelComponent.getComponentType());
/* 167 */     Model model = (modelComponent != null) ? modelComponent.getModel() : null;
/* 168 */     if (model != null && model.getPhysicsValues() != null) {
/* 169 */       return model.getPhysicsValues();
/*     */     }
/*     */     
/* 172 */     return PhysicsValues.getDefault();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\EntityUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */