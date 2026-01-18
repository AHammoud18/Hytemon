/*     */ package com.hypixel.hytale.builtin.creativehub.config;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.creativehub.CreativeHubPlugin;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.UUID;
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
/*     */ 
/*     */ 
/*     */ public class CreativeHubEntityConfig
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   public static final String ID = "CreativeHub";
/*     */   @Nonnull
/*     */   public static final BuilderCodec<CreativeHubEntityConfig> CODEC;
/*     */   @Nullable
/*     */   private UUID parentHubWorldUuid;
/*     */   
/*     */   static {
/*  34 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(CreativeHubEntityConfig.class, CreativeHubEntityConfig::new).appendInherited(new KeyedCodec("ParentHubWorldUuid", (Codec)Codec.UUID_STRING), (o, i) -> o.parentHubWorldUuid = i, o -> o.parentHubWorldUuid, (o, p) -> o.parentHubWorldUuid = p.parentHubWorldUuid).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, CreativeHubEntityConfig> getComponentType() {
/*  43 */     return CreativeHubPlugin.get().getCreativeHubEntityConfigComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static CreativeHubEntityConfig ensureAndGet(@Nonnull Holder<EntityStore> holder) {
/*  54 */     ComponentType<EntityStore, CreativeHubEntityConfig> type = getComponentType();
/*  55 */     return (CreativeHubEntityConfig)holder.ensureAndGetComponent(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static CreativeHubEntityConfig get(@Nonnull Holder<EntityStore> holder) {
/*  66 */     ComponentType<EntityStore, CreativeHubEntityConfig> type = getComponentType();
/*  67 */     return (CreativeHubEntityConfig)holder.getComponent(type);
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
/*     */   @Nullable
/*     */   public UUID getParentHubWorldUuid() {
/*  85 */     return this.parentHubWorldUuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParentHubWorldUuid(@Nullable UUID parentHubWorldUuid) {
/*  94 */     this.parentHubWorldUuid = parentHubWorldUuid;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CreativeHubEntityConfig clone() {
/* 100 */     CreativeHubEntityConfig v = new CreativeHubEntityConfig();
/* 101 */     v.parentHubWorldUuid = this.parentHubWorldUuid;
/* 102 */     return v;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\creativehub\config\CreativeHubEntityConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */