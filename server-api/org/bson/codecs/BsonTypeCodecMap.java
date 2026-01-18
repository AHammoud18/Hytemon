/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonType;
/*    */ import org.bson.assertions.Assertions;
/*    */ import org.bson.codecs.configuration.CodecConfigurationException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BsonTypeCodecMap
/*    */ {
/*    */   private final BsonTypeClassMap bsonTypeClassMap;
/* 33 */   private final Codec<?>[] codecs = (Codec<?>[])new Codec[256];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BsonTypeCodecMap(BsonTypeClassMap bsonTypeClassMap, CodecRegistry codecRegistry) {
/* 41 */     this.bsonTypeClassMap = (BsonTypeClassMap)Assertions.notNull("bsonTypeClassMap", bsonTypeClassMap);
/* 42 */     Assertions.notNull("codecRegistry", codecRegistry);
/* 43 */     for (BsonType cur : bsonTypeClassMap.keys()) {
/* 44 */       Class<?> clazz = bsonTypeClassMap.get(cur);
/* 45 */       if (clazz != null) {
/*    */         try {
/* 47 */           this.codecs[cur.getValue()] = codecRegistry.get(clazz);
/* 48 */         } catch (CodecConfigurationException codecConfigurationException) {}
/*    */       }
/*    */     } 
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
/*    */   public Codec<?> get(BsonType bsonType) {
/* 62 */     Codec<?> codec = this.codecs[bsonType.getValue()];
/* 63 */     if (codec == null) {
/* 64 */       Class<?> clazz = this.bsonTypeClassMap.get(bsonType);
/* 65 */       if (clazz == null) {
/* 66 */         throw new CodecConfigurationException(String.format("No class mapped for BSON type %s.", new Object[] { bsonType }));
/*    */       }
/* 68 */       throw new CodecConfigurationException(String.format("Can't find a codec for %s.", new Object[] { clazz }));
/*    */     } 
/*    */     
/* 71 */     return codec;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonTypeCodecMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */