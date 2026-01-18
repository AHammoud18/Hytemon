/*     */ package com.hypixel.hytale.component.system;
/*     */ 
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.ComponentRegistration;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.ResourceRegistration;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class System<ECS_TYPE>
/*     */   implements ISystem<ECS_TYPE>
/*     */ {
/*     */   @Nonnull
/*  26 */   private final ObjectList<ComponentRegistration<ECS_TYPE, ?>> componentRegistrations = (ObjectList<ComponentRegistration<ECS_TYPE, ?>>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  32 */   private final ObjectList<ResourceRegistration<ECS_TYPE, ?>> resourceRegistrations = (ObjectList<ResourceRegistration<ECS_TYPE, ?>>)new ObjectArrayList();
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
/*     */   @Nonnull
/*     */   protected <T extends com.hypixel.hytale.component.Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponent(@Nonnull Class<? super T> tClass, @Nonnull Supplier<T> supplier) {
/*  47 */     return registerComponent(tClass, null, null, supplier);
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
/*     */   @Nonnull
/*     */   protected <T extends com.hypixel.hytale.component.Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponent(@Nonnull Class<? super T> tClass, @Nonnull String id, @Nonnull BuilderCodec<T> codec) {
/*  63 */     Objects.requireNonNull(codec); return registerComponent(tClass, id, codec, codec::getDefaultValue);
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
/*     */   @Nonnull
/*     */   protected <T extends com.hypixel.hytale.component.Component<ECS_TYPE>> ComponentType<ECS_TYPE, T> registerComponent(@Nonnull Class<? super T> tClass, @Nullable String id, @Nullable BuilderCodec<T> codec, @Nonnull Supplier<T> supplier) {
/*  80 */     ComponentType<ECS_TYPE, T> componentType = new ComponentType();
/*  81 */     this.componentRegistrations.add(new ComponentRegistration(tClass, id, codec, supplier, componentType));
/*  82 */     return componentType;
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
/*     */   @Nonnull
/*     */   public <T extends com.hypixel.hytale.component.Resource<ECS_TYPE>> ResourceType<ECS_TYPE, T> registerResource(@Nonnull Class<? super T> tClass, @Nonnull Supplier<T> supplier) {
/*  97 */     return registerResource(tClass, null, null, supplier);
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
/*     */   @Nonnull
/*     */   public <T extends com.hypixel.hytale.component.Resource<ECS_TYPE>> ResourceType<ECS_TYPE, T> registerResource(@Nonnull Class<? super T> tClass, @Nonnull String id, @Nonnull BuilderCodec<T> codec) {
/* 113 */     Objects.requireNonNull(codec); return registerResource(tClass, id, codec, codec::getDefaultValue);
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
/*     */   @Nonnull
/*     */   private <T extends com.hypixel.hytale.component.Resource<ECS_TYPE>> ResourceType<ECS_TYPE, T> registerResource(@Nonnull Class<? super T> tClass, @Nullable String id, @Nullable BuilderCodec<T> codec, @Nonnull Supplier<T> supplier) {
/* 130 */     ResourceType<ECS_TYPE, T> componentType = new ResourceType();
/* 131 */     this.resourceRegistrations.add(new ResourceRegistration(tClass, id, codec, supplier, componentType));
/* 132 */     return componentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<ComponentRegistration<ECS_TYPE, ?>> getComponentRegistrations() {
/* 140 */     return (List<ComponentRegistration<ECS_TYPE, ?>>)this.componentRegistrations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<ResourceRegistration<ECS_TYPE, ?>> getResourceRegistrations() {
/* 148 */     return (List<ResourceRegistration<ECS_TYPE, ?>>)this.resourceRegistrations;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\System.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */