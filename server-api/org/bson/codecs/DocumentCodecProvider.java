/*     */ package org.bson.codecs;
/*     */ 
/*     */ import org.bson.Document;
/*     */ import org.bson.Transformer;
/*     */ import org.bson.assertions.Assertions;
/*     */ import org.bson.codecs.configuration.CodecProvider;
/*     */ import org.bson.codecs.configuration.CodecRegistry;
/*     */ import org.bson.types.CodeWithScope;
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
/*     */ public class DocumentCodecProvider
/*     */   implements CodecProvider
/*     */ {
/*     */   private final BsonTypeClassMap bsonTypeClassMap;
/*     */   private final Transformer valueTransformer;
/*     */   
/*     */   public DocumentCodecProvider() {
/*  41 */     this(BsonTypeClassMap.DEFAULT_BSON_TYPE_CLASS_MAP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentCodecProvider(Transformer valueTransformer) {
/*  52 */     this(BsonTypeClassMap.DEFAULT_BSON_TYPE_CLASS_MAP, valueTransformer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentCodecProvider(BsonTypeClassMap bsonTypeClassMap) {
/*  62 */     this(bsonTypeClassMap, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentCodecProvider(BsonTypeClassMap bsonTypeClassMap, Transformer valueTransformer) {
/*  73 */     this.bsonTypeClassMap = (BsonTypeClassMap)Assertions.notNull("bsonTypeClassMap", bsonTypeClassMap);
/*  74 */     this.valueTransformer = valueTransformer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
/*  80 */     if (clazz == CodeWithScope.class) {
/*  81 */       return new CodeWithScopeCodec(registry.get(Document.class));
/*     */     }
/*     */     
/*  84 */     if (clazz == Document.class) {
/*  85 */       return new DocumentCodec(registry, this.bsonTypeClassMap, this.valueTransformer);
/*     */     }
/*     */     
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  93 */     if (this == o) {
/*  94 */       return true;
/*     */     }
/*  96 */     if (o == null || getClass() != o.getClass()) {
/*  97 */       return false;
/*     */     }
/*     */     
/* 100 */     DocumentCodecProvider that = (DocumentCodecProvider)o;
/*     */     
/* 102 */     if (!this.bsonTypeClassMap.equals(that.bsonTypeClassMap)) {
/* 103 */       return false;
/*     */     }
/* 105 */     if ((this.valueTransformer != null) ? !this.valueTransformer.equals(that.valueTransformer) : (that.valueTransformer != null)) {
/* 106 */       return false;
/*     */     }
/*     */     
/* 109 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 114 */     int result = this.bsonTypeClassMap.hashCode();
/* 115 */     result = 31 * result + ((this.valueTransformer != null) ? this.valueTransformer.hashCode() : 0);
/* 116 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\DocumentCodecProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */