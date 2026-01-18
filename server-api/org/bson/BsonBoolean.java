/*    */ package org.bson;
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
/*    */ public final class BsonBoolean
/*    */   extends BsonValue
/*    */   implements Comparable<BsonBoolean>
/*    */ {
/*    */   private final boolean value;
/* 28 */   public static final BsonBoolean TRUE = new BsonBoolean(true);
/*    */   
/* 30 */   public static final BsonBoolean FALSE = new BsonBoolean(false);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static BsonBoolean valueOf(boolean value) {
/* 39 */     return value ? TRUE : FALSE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BsonBoolean(boolean value) {
/* 48 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(BsonBoolean o) {
/* 53 */     return Boolean.valueOf(this.value).compareTo(Boolean.valueOf(o.value));
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonType getBsonType() {
/* 58 */     return BsonType.BOOLEAN;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getValue() {
/* 67 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 72 */     if (this == o) {
/* 73 */       return true;
/*    */     }
/* 75 */     if (o == null || getClass() != o.getClass()) {
/* 76 */       return false;
/*    */     }
/*    */     
/* 79 */     BsonBoolean that = (BsonBoolean)o;
/*    */     
/* 81 */     if (this.value != that.value) {
/* 82 */       return false;
/*    */     }
/*    */     
/* 85 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 90 */     return this.value ? 1 : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 95 */     return "BsonBoolean{value=" + this.value + '}';
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonBoolean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */