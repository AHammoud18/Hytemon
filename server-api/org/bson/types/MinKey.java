/*    */ package org.bson.types;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public final class MinKey
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4075901136671855684L;
/*    */   
/*    */   public boolean equals(Object o) {
/* 30 */     return o instanceof MinKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 35 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     return "MinKey";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\types\MinKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */