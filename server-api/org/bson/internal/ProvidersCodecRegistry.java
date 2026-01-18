/*    */ package org.bson.internal;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bson.assertions.Assertions;
/*    */ import org.bson.codecs.Codec;
/*    */ import org.bson.codecs.configuration.CodecProvider;
/*    */ import org.bson.codecs.configuration.CodecRegistry;
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
/*    */ 
/*    */ public final class ProvidersCodecRegistry
/*    */   implements CodecRegistry, CycleDetectingCodecRegistry
/*    */ {
/*    */   private final List<CodecProvider> codecProviders;
/* 30 */   private final CodecCache codecCache = new CodecCache();
/*    */   
/*    */   public ProvidersCodecRegistry(List<? extends CodecProvider> codecProviders) {
/* 33 */     Assertions.isTrueArgument("codecProviders must not be null or empty", (codecProviders != null && codecProviders.size() > 0));
/* 34 */     this.codecProviders = new ArrayList<>(codecProviders);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> Codec<T> get(Class<T> clazz) {
/* 39 */     return get(new ChildCodecRegistry<>(this, clazz));
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
/* 44 */     for (CodecProvider provider : this.codecProviders) {
/* 45 */       Codec<T> codec = provider.get(clazz, registry);
/* 46 */       if (codec != null) {
/* 47 */         return codec;
/*    */       }
/*    */     } 
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> Codec<T> get(ChildCodecRegistry<T> context) {
/* 55 */     if (!this.codecCache.containsKey(context.getCodecClass())) {
/* 56 */       for (CodecProvider provider : this.codecProviders) {
/* 57 */         Codec<T> codec = provider.get(context.getCodecClass(), context);
/* 58 */         if (codec != null) {
/* 59 */           return this.codecCache.putIfMissing(context.getCodecClass(), codec);
/*    */         }
/*    */       } 
/* 62 */       this.codecCache.put(context.getCodecClass(), null);
/*    */     } 
/* 64 */     return this.codecCache.getOrThrow(context.getCodecClass());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 69 */     if (this == o) {
/* 70 */       return true;
/*    */     }
/* 72 */     if (o == null || getClass() != o.getClass()) {
/* 73 */       return false;
/*    */     }
/*    */     
/* 76 */     ProvidersCodecRegistry that = (ProvidersCodecRegistry)o;
/* 77 */     if (this.codecProviders.size() != that.codecProviders.size()) {
/* 78 */       return false;
/*    */     }
/* 80 */     for (int i = 0; i < this.codecProviders.size(); i++) {
/* 81 */       if (((CodecProvider)this.codecProviders.get(i)).getClass() != ((CodecProvider)that.codecProviders.get(i)).getClass()) {
/* 82 */         return false;
/*    */       }
/*    */     } 
/* 85 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 90 */     return this.codecProviders.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\internal\ProvidersCodecRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */