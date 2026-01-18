/*    */ package io.sentry.util;
/*    */ 
/*    */ import java.net.URI;
/*    */ import java.util.List;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class PropagationTargetsUtils
/*    */ {
/*    */   public static boolean contain(@NotNull List<String> origins, @NotNull String url) {
/* 13 */     if (origins.isEmpty()) {
/* 14 */       return false;
/*    */     }
/* 16 */     for (String origin : origins) {
/* 17 */       if (url.contains(origin)) {
/* 18 */         return true;
/*    */       }
/*    */       try {
/* 21 */         if (url.matches(origin)) {
/* 22 */           return true;
/*    */         }
/* 24 */       } catch (Exception exception) {}
/*    */     } 
/*    */ 
/*    */     
/* 28 */     return false;
/*    */   }
/*    */   
/*    */   public static boolean contain(@NotNull List<String> origins, URI uri) {
/* 32 */     return contain(origins, uri.toString());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\PropagationTargetsUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */