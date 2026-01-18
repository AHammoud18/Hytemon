/*    */ package org.bson.codecs.pojo;
/*    */ 
/*    */ import org.bson.BsonObjectId;
/*    */ import org.bson.BsonType;
/*    */ import org.bson.types.ObjectId;
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
/*    */ final class ConventionObjectIdGeneratorsImpl
/*    */   implements Convention
/*    */ {
/*    */   public void apply(ClassModelBuilder<?> classModelBuilder) {
/* 26 */     if (classModelBuilder.getIdGenerator() == null && classModelBuilder.getIdPropertyName() != null) {
/* 27 */       PropertyModelBuilder<?> idProperty = classModelBuilder.getProperty(classModelBuilder.getIdPropertyName());
/* 28 */       if (idProperty != null) {
/* 29 */         Class<?> idType = idProperty.getTypeData().getType();
/* 30 */         if (classModelBuilder.getIdGenerator() == null && idType.equals(ObjectId.class)) {
/* 31 */           classModelBuilder.idGenerator(IdGenerators.OBJECT_ID_GENERATOR);
/* 32 */         } else if (classModelBuilder.getIdGenerator() == null && idType.equals(BsonObjectId.class)) {
/* 33 */           classModelBuilder.idGenerator(IdGenerators.BSON_OBJECT_ID_GENERATOR);
/* 34 */         } else if (classModelBuilder.getIdGenerator() == null && idType.equals(String.class) && idProperty
/* 35 */           .getBsonRepresentation() == BsonType.OBJECT_ID) {
/* 36 */           classModelBuilder.idGenerator(IdGenerators.STRING_ID_GENERATOR);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\ConventionObjectIdGeneratorsImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */