/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ final class ApplicationProtocolUtil
/*    */ {
/*    */   private static final int DEFAULT_LIST_SIZE = 2;
/*    */   
/*    */   static List<String> toList(Iterable<String> protocols) {
/* 33 */     return toList(2, protocols);
/*    */   }
/*    */   
/*    */   static List<String> toList(int initialListSize, Iterable<String> protocols) {
/* 37 */     if (protocols == null) {
/* 38 */       return null;
/*    */     }
/*    */     
/* 41 */     List<String> result = new ArrayList<>(initialListSize);
/* 42 */     for (String p : protocols) {
/* 43 */       result.add(ObjectUtil.checkNonEmpty(p, "p"));
/*    */     }
/*    */     
/* 46 */     return (List<String>)ObjectUtil.checkNonEmpty(result, "result");
/*    */   }
/*    */   
/*    */   static List<String> toList(String... protocols) {
/* 50 */     return toList(2, protocols);
/*    */   }
/*    */   
/*    */   static List<String> toList(int initialListSize, String... protocols) {
/* 54 */     if (protocols == null) {
/* 55 */       return null;
/*    */     }
/*    */     
/* 58 */     List<String> result = new ArrayList<>(initialListSize);
/* 59 */     for (String p : protocols) {
/* 60 */       result.add(ObjectUtil.checkNonEmpty(p, "p"));
/*    */     }
/*    */     
/* 63 */     return (List<String>)ObjectUtil.checkNonEmpty(result, "result");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\ApplicationProtocolUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */