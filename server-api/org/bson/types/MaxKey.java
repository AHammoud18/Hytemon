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
/*    */ public final class MaxKey
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 5123414776151687185L;
/*    */   
/*    */   public boolean equals(Object o) {
/* 30 */     return o instanceof MaxKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 35 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     return "MaxKey";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\types\MaxKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */