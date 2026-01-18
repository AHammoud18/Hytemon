/*    */ package com.hypixel.hytale.server.core.entity.reference;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class InvalidatablePersistentRef
/*    */   extends PersistentRef
/*    */ {
/*    */   public static final BuilderCodec<InvalidatablePersistentRef> CODEC;
/*    */   protected int refCount;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(InvalidatablePersistentRef.class, InvalidatablePersistentRef::new, PersistentRef.CODEC).append(new KeyedCodec("RefCount", (Codec)Codec.INTEGER), (instance, value) -> instance.refCount = value.intValue(), instance -> Integer.valueOf(instance.refCount)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setEntity(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 29 */     super.setEntity(ref, componentAccessor);
/* 30 */     PersistentRefCount refCount = (PersistentRefCount)componentAccessor.getComponent(ref, PersistentRefCount.getComponentType());
/* 31 */     if (refCount == null) {
/* 32 */       refCount = new PersistentRefCount();
/* 33 */       componentAccessor.addComponent(ref, PersistentRefCount.getComponentType(), refCount);
/*    */     } 
/* 35 */     this.refCount = refCount.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 40 */     super.clear();
/* 41 */     this.refCount = -1;
/*    */   }
/*    */   
/*    */   public void setRefCount(int refCount) {
/* 45 */     this.refCount = refCount;
/*    */   }
/*    */   
/*    */   public int getRefCount() {
/* 49 */     return this.refCount;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean validateEntityReference(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 54 */     PersistentRefCount refCount = (PersistentRefCount)componentAccessor.getComponent(ref, PersistentRefCount.getComponentType());
/* 55 */     return (super.validateEntityReference(ref, componentAccessor) && refCount != null && refCount.get() == this.refCount);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\reference\InvalidatablePersistentRef.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */