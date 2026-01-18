/*    */ package org.bson;
/*    */ 
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
/*    */ public class BsonObjectId
/*    */   extends BsonValue
/*    */   implements Comparable<BsonObjectId>
/*    */ {
/*    */   private final ObjectId value;
/*    */   
/*    */   public BsonObjectId() {
/* 34 */     this(new ObjectId());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BsonObjectId(ObjectId value) {
/* 42 */     if (value == null) {
/* 43 */       throw new IllegalArgumentException("value may not be null");
/*    */     }
/* 45 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectId getValue() {
/* 54 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonType getBsonType() {
/* 59 */     return BsonType.OBJECT_ID;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(BsonObjectId o) {
/* 64 */     return this.value.compareTo(o.value);
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
/* 76 */     BsonObjectId that = (BsonObjectId)o;
/*    */     
/* 78 */     if (!this.value.equals(that.value)) {
/* 79 */       return false;
/*    */     }
/*    */     
/* 82 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 87 */     return this.value.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 92 */     return "BsonObjectId{value=" + this.value
/* 93 */       .toHexString() + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonObjectId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */