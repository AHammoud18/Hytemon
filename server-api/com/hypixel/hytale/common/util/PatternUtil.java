/*    */ package com.hypixel.hytale.common.util;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PatternUtil
/*    */ {
/*    */   @Nonnull
/*    */   public static String replaceBackslashWithForwardSlash(@Nonnull String name) {
/* 13 */     return name.replace("\\", "/");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\PatternUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */