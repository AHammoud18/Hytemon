/*    */ package com.hypixel.hytale.server.core.entity.reference;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PersistentRef
/*    */ {
/*    */   public static final BuilderCodec<PersistentRef> CODEC;
/*    */   
/*    */   static {
/* 22 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PersistentRef.class, PersistentRef::new).append(new KeyedCodec("UUID", (Codec)Codec.UUID_BINARY), (instance, value) -> instance.uuid = value, instance -> instance.uuid).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected UUID uuid;
/*    */   
/*    */   @Nullable
/*    */   protected Ref<EntityStore> reference;
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public UUID getUuid() {
/* 35 */     return this.uuid;
/*    */   }
/*    */   
/*    */   public void setUuid(UUID uuid) {
/* 39 */     this.uuid = uuid;
/* 40 */     this.reference = null;
/*    */   }
/*    */   
/*    */   public void setEntity(Ref<EntityStore> ref, UUID uuid) {
/* 44 */     this.uuid = uuid;
/* 45 */     this.reference = ref;
/*    */   }
/*    */   
/*    */   public void setEntity(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 49 */     this.uuid = ((UUIDComponent)componentAccessor.getComponent(ref, UUIDComponent.getComponentType())).getUuid();
/* 50 */     this.reference = ref;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 54 */     this.uuid = null;
/* 55 */     this.reference = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValid() {
/* 60 */     return (this.uuid != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Ref<EntityStore> getEntity(@Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 66 */     if (!isValid()) {
/* 67 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 71 */     if (this.reference != null && this.reference.isValid()) return this.reference;
/*    */     
/* 73 */     this.reference = ((EntityStore)componentAccessor.getExternalData()).getRefFromUUID(this.uuid);
/*    */ 
/*    */     
/* 76 */     if (this.reference != null && !validateEntityReference(this.reference, componentAccessor)) {
/* 77 */       clear();
/*    */     }
/*    */     
/* 80 */     return this.reference;
/*    */   }
/*    */   
/*    */   protected boolean validateEntityReference(Ref<EntityStore> ref, ComponentAccessor<EntityStore> componentAccessor) {
/* 84 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\reference\PersistentRef.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */