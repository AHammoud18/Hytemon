/*    */ package io.netty.util;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface HashingStrategy<T>
/*    */ {
/* 62 */   public static final HashingStrategy JAVA_HASHER = new HashingStrategy()
/*    */     {
/*    */       public int hashCode(Object obj) {
/* 65 */         return (obj != null) ? obj.hashCode() : 0;
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean equals(Object a, Object b) {
/* 70 */         return (a == b || (a != null && a.equals(b)));
/*    */       }
/*    */     };
/*    */   
/*    */   int hashCode(T paramT);
/*    */   
/*    */   boolean equals(T paramT1, T paramT2);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\HashingStrategy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */