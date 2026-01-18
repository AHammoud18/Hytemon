/*     */ package org.bson.codecs;
/*     */ 
/*     */ import org.bson.Transformer;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.codecs.configuration.CodecProvider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IterableCodecProvider
/*     */   implements CodecProvider
/*     */ {
/*     */   private final BsonTypeClassMap bsonTypeClassMap;
/*     */   private final Transformer valueTransformer;
/*     */   
/*     */   public IterableCodecProvider() {
/*  39 */     this(BsonTypeClassMap.DEFAULT_BSON_TYPE_CLASS_MAP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IterableCodecProvider(Transformer valueTransformer) {
/*  49 */     this(BsonTypeClassMap.DEFAULT_BSON_TYPE_CLASS_MAP, valueTransformer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IterableCodecProvider(BsonTypeClassMap bsonTypeClassMap) {
/*  59 */     this(bsonTypeClassMap, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IterableCodecProvider(BsonTypeClassMap bsonTypeClassMap, Transformer valueTransformer) {
/*  70 */     this.bsonTypeClassMap = (BsonTypeClassMap)Assertions.notNull("bsonTypeClassMap", bsonTypeClassMap);
/*  71 */     this.valueTransformer = valueTransformer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
/*  77 */     if (Iterable.class.isAssignableFrom(clazz)) {
/*  78 */       return new IterableCodec(registry, this.bsonTypeClassMap, this.valueTransformer);
/*     */     }
/*     */     
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  86 */     if (this == o) {
/*  87 */       return true;
/*     */     }
/*  89 */     if (o == null || getClass() != o.getClass()) {
/*  90 */       return false;
/*     */     }
/*     */     
/*  93 */     IterableCodecProvider that = (IterableCodecProvider)o;
/*     */     
/*  95 */     if (!this.bsonTypeClassMap.equals(that.bsonTypeClassMap)) {
/*  96 */       return false;
/*     */     }
/*  98 */     if ((this.valueTransformer != null) ? !this.valueTransformer.equals(that.valueTransformer) : (that.valueTransformer != null)) {
/*  99 */       return false;
/*     */     }
/*     */     
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     int result = this.bsonTypeClassMap.hashCode();
/* 108 */     result = 31 * result + ((this.valueTransformer != null) ? this.valueTransformer.hashCode() : 0);
/* 109 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\IterableCodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */