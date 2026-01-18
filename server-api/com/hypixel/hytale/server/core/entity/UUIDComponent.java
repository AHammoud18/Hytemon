/*    */ package com.hypixel.hytale.server.core.entity;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.UUIDUtil;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class UUIDComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final BuilderCodec<UUIDComponent> CODEC;
/*    */   private UUID uuid;
/*    */   
/*    */   @Nonnull
/*    */   public static ComponentType<EntityStore, UUIDComponent> getComponentType() {
/* 27 */     return EntityModule.get().getUuidComponentType();
/*    */   }
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
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 46 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(UUIDComponent.class, UUIDComponent::new).append(new KeyedCodec("UUID", (Codec)Codec.UUID_BINARY), (o, i) -> o.uuid = i, o -> o.uuid).addValidator(Validators.nonNull()).add()).afterDecode(v -> { if (v.uuid == null) v.uuid = UUIDUtil.generateVersion3UUID();  })).build();
/*    */   }
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
/*    */   public UUIDComponent(@Nonnull UUID uuid) {
/* 59 */     this.uuid = uuid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private UUIDComponent() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public UUID getUuid() {
/* 72 */     return this.uuid;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 78 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static UUIDComponent generateVersion3UUID() {
/* 89 */     return new UUIDComponent(UUIDUtil.generateVersion3UUID());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static UUIDComponent randomUUID() {
/* 94 */     return new UUIDComponent(UUID.randomUUID());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\UUIDComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */