/*    */ package org.bson.codecs.pojo;
/*    */ 
/*    */ import org.bson.BsonObjectId;
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
/*    */ public final class IdGenerators
/*    */ {
/* 33 */   public static final IdGenerator<ObjectId> OBJECT_ID_GENERATOR = new IdGenerator<ObjectId>()
/*    */     {
/*    */       public ObjectId generate()
/*    */       {
/* 37 */         return new ObjectId();
/*    */       }
/*    */ 
/*    */       
/*    */       public Class<ObjectId> getType() {
/* 42 */         return ObjectId.class;
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   public static final IdGenerator<BsonObjectId> BSON_OBJECT_ID_GENERATOR = new IdGenerator<BsonObjectId>()
/*    */     {
/*    */       public BsonObjectId generate()
/*    */       {
/* 53 */         return new BsonObjectId();
/*    */       }
/*    */ 
/*    */       
/*    */       public Class<BsonObjectId> getType() {
/* 58 */         return BsonObjectId.class;
/*    */       }
/*    */     };
/*    */   
/* 62 */   public static final IdGenerator<String> STRING_ID_GENERATOR = new IdGenerator<String>()
/*    */     {
/*    */       public String generate() {
/* 65 */         return ((ObjectId)IdGenerators.OBJECT_ID_GENERATOR.generate()).toHexString();
/*    */       }
/*    */ 
/*    */       
/*    */       public Class<String> getType() {
/* 70 */         return String.class;
/*    */       }
/*    */     };
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\IdGenerators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */