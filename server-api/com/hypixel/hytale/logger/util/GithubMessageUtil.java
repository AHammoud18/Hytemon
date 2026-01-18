/*    */ package com.hypixel.hytale.logger.util;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class GithubMessageUtil {
/*  6 */   private static final String CI = System.getenv("CI");
/*    */   
/*    */   public static boolean isGithub() {
/*  9 */     return (CI != null);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String messageError(@Nonnull String file, int line, int column, @Nonnull String message) {
/* 14 */     return "::error file=%s,line=%d,col=%d::%s\n".formatted(new Object[] { file, Integer.valueOf(line), Integer.valueOf(column), message.replace("\n", "%0A") });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String messageError(@Nonnull String file, @Nonnull String message) {
/* 19 */     return "::error file=%s::%s\n".formatted(new Object[] { file, message.replace("\n", "%0A") });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String messageWarning(@Nonnull String file, int line, int column, @Nonnull String message) {
/* 24 */     return "::warning file=%s,line=%d,col=%d::%s\n".formatted(new Object[] { file, Integer.valueOf(line), Integer.valueOf(column), message.replace("\n", "%0A") });
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String messageWarning(@Nonnull String file, @Nonnull String message) {
/* 29 */     return "::warning file=%s::%s\n".formatted(new Object[] { file, message.replace("\n", "%0A") });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\logge\\util\GithubMessageUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */