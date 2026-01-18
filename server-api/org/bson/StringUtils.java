/*    */ package org.bson;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
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
/*    */ final class StringUtils
/*    */ {
/*    */   public static String join(String delimiter, Collection<?> s) {
/* 24 */     StringBuilder builder = new StringBuilder();
/* 25 */     Iterator<?> iter = s.iterator();
/* 26 */     while (iter.hasNext()) {
/* 27 */       builder.append(iter.next());
/* 28 */       if (!iter.hasNext()) {
/*    */         break;
/*    */       }
/* 31 */       builder.append(delimiter);
/*    */     } 
/* 33 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\StringUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */