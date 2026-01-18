/*    */ package com.hypixel.hytale.server.core.entity.reference;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PersistentRefCount
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final BuilderCodec<PersistentRefCount> CODEC;
/*    */   private int refCount;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(PersistentRefCount.class, PersistentRefCount::new).append(new KeyedCodec("Count", (Codec)Codec.INTEGER), (instance, value) -> instance.refCount = value.intValue(), instance -> Integer.valueOf(instance.refCount)).add()).build();
/*    */   }
/*    */   public static ComponentType<EntityStore, PersistentRefCount> getComponentType() {
/* 27 */     return EntityModule.get().getPersistentRefCountComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int get() {
/* 33 */     return this.refCount;
/*    */   }
/*    */   
/*    */   public void increment() {
/* 37 */     if (this.refCount >= Integer.MAX_VALUE) {
/* 38 */       this.refCount = 0;
/*    */     } else {
/* 40 */       this.refCount++;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 48 */     PersistentRefCount ref = new PersistentRefCount();
/* 49 */     ref.refCount = this.refCount;
/* 50 */     return ref;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\reference\PersistentRefCount.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */