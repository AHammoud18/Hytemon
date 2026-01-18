/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.URLDecoder;
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
/*    */ public final class ResourcesUtil
/*    */ {
/*    */   public static File getFile(Class resourceClass, String fileName) {
/*    */     try {
/* 36 */       return new File(URLDecoder.decode(resourceClass.getResource(fileName).getFile(), "UTF-8"));
/* 37 */     } catch (UnsupportedEncodingException e) {
/* 38 */       return new File(resourceClass.getResource(fileName).getFile());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\ResourcesUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */