/*     */ package org.bson.internal;
/*     */ 
/*     */ import org.bson.codecs.Codec;
/*     */ import org.bson.codecs.configuration.CodecRegistry;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ChildCodecRegistry<T>
/*     */   implements CodecRegistry
/*     */ {
/*     */   private final ChildCodecRegistry<?> parent;
/*     */   private final CycleDetectingCodecRegistry registry;
/*     */   private final Class<T> codecClass;
/*     */   
/*     */   ChildCodecRegistry(CycleDetectingCodecRegistry registry, Class<T> codecClass) {
/*  31 */     this.codecClass = codecClass;
/*  32 */     this.parent = null;
/*  33 */     this.registry = registry;
/*     */   }
/*     */ 
/*     */   
/*     */   private ChildCodecRegistry(ChildCodecRegistry<?> parent, Class<T> codecClass) {
/*  38 */     this.parent = parent;
/*  39 */     this.codecClass = codecClass;
/*  40 */     this.registry = parent.registry;
/*     */   }
/*     */   
/*     */   public Class<T> getCodecClass() {
/*  44 */     return this.codecClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public <U> Codec<U> get(Class<U> clazz) {
/*  49 */     if (hasCycles(clazz).booleanValue()) {
/*  50 */       return new LazyCodec<>(this.registry, clazz);
/*     */     }
/*  52 */     return this.registry.get(new ChildCodecRegistry(this, clazz));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <U> Codec<U> get(Class<U> clazz, CodecRegistry registry) {
/*  58 */     return this.registry.get(clazz, registry);
/*     */   }
/*     */ 
/*     */   
/*     */   private <U> Boolean hasCycles(Class<U> theClass) {
/*  63 */     ChildCodecRegistry<T> current = this;
/*  64 */     while (current != null) {
/*  65 */       if (current.codecClass.equals(theClass)) {
/*  66 */         return Boolean.valueOf(true);
/*     */       }
/*     */       
/*  69 */       current = (ChildCodecRegistry)current.parent;
/*     */     } 
/*     */     
/*  72 */     return Boolean.valueOf(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  77 */     if (this == o) {
/*  78 */       return true;
/*     */     }
/*  80 */     if (o == null || getClass() != o.getClass()) {
/*  81 */       return false;
/*     */     }
/*     */     
/*  84 */     ChildCodecRegistry<?> that = (ChildCodecRegistry)o;
/*     */     
/*  86 */     if (!this.codecClass.equals(that.codecClass)) {
/*  87 */       return false;
/*     */     }
/*  89 */     if ((this.parent != null) ? !this.parent.equals(that.parent) : (that.parent != null)) {
/*  90 */       return false;
/*     */     }
/*  92 */     if (!this.registry.equals(that.registry)) {
/*  93 */       return false;
/*     */     }
/*     */     
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     int result = (this.parent != null) ? this.parent.hashCode() : 0;
/* 102 */     result = 31 * result + this.registry.hashCode();
/* 103 */     result = 31 * result + this.codecClass.hashCode();
/* 104 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\internal\ChildCodecRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */