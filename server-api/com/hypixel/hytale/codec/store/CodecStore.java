/*    */ package com.hypixel.hytale.codec.store;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CodecStore
/*    */ {
/* 16 */   public static final CodecStore STATIC = new CodecStore();
/*    */   
/*    */   private final CodecStore parent;
/* 19 */   private final Map<CodecKey<?>, Codec<?>> codecs = new ConcurrentHashMap<>();
/* 20 */   private final Map<CodecKey<?>, Supplier<Codec<?>>> codecSuppliers = new ConcurrentHashMap<>();
/*    */   
/*    */   public CodecStore() {
/* 23 */     this.parent = STATIC;
/*    */   }
/*    */   
/*    */   public CodecStore(CodecStore parent) {
/* 27 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T> Codec<T> getCodec(CodecKey<T> key) {
/* 33 */     Codec<T> codec = (Codec<T>)this.codecs.get(key);
/* 34 */     if (codec != null) return codec;
/*    */     
/* 36 */     Supplier<Codec<?>> supplier = this.codecSuppliers.get(key);
/* 37 */     if (supplier != null) codec = (Codec<T>)supplier.get(); 
/* 38 */     if (codec != null) return codec;
/*    */     
/* 40 */     return (this.parent != null) ? this.parent.<T>getCodec(key) : null;
/*    */   }
/*    */   
/*    */   public <T> void putCodec(CodecKey<T> key, Codec<T> codec) {
/* 44 */     this.codecs.put(key, codec);
/*    */   }
/*    */   
/*    */   public <T> Codec<?> removeCodec(CodecKey<T> key) {
/* 48 */     return this.codecs.remove(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> void putCodecSupplier(CodecKey<T> key, Supplier<Codec<T>> supplier) {
/* 53 */     this.codecSuppliers.put(key, supplier);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> Supplier<Codec<?>> removeCodecSupplier(CodecKey<T> key) {
/* 58 */     return this.codecSuppliers.remove(key);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\store\CodecStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */