/*     */ package org.bson.codecs;
/*     */ 
/*     */ import java.util.Map;
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
/*     */ 
/*     */ public class MapCodecProvider
/*     */   implements CodecProvider
/*     */ {
/*     */   private final BsonTypeClassMap bsonTypeClassMap;
/*     */   private final Transformer valueTransformer;
/*     */   
/*     */   public MapCodecProvider() {
/*  41 */     this(BsonTypeClassMap.DEFAULT_BSON_TYPE_CLASS_MAP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapCodecProvider(BsonTypeClassMap bsonTypeClassMap) {
/*  51 */     this(bsonTypeClassMap, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapCodecProvider(Transformer valueTransformer) {
/*  61 */     this(BsonTypeClassMap.DEFAULT_BSON_TYPE_CLASS_MAP, valueTransformer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapCodecProvider(BsonTypeClassMap bsonTypeClassMap, Transformer valueTransformer) {
/*  71 */     this.bsonTypeClassMap = (BsonTypeClassMap)Assertions.notNull("bsonTypeClassMap", bsonTypeClassMap);
/*  72 */     this.valueTransformer = valueTransformer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
/*  78 */     if (Map.class.isAssignableFrom(clazz)) {
/*  79 */       return new MapCodec(registry, this.bsonTypeClassMap, this.valueTransformer);
/*     */     }
/*     */     
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  87 */     if (this == o) {
/*  88 */       return true;
/*     */     }
/*  90 */     if (o == null || getClass() != o.getClass()) {
/*  91 */       return false;
/*     */     }
/*     */     
/*  94 */     MapCodecProvider that = (MapCodecProvider)o;
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\MapCodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */