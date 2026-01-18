/*    */ package com.hypixel.hytale.common.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ExceptionUtil {
/*    */   @Nonnull
/*    */   public static String combineMessages(Throwable thrown, @Nonnull String joiner) {
/* 11 */     StringBuilder sb = new StringBuilder();
/* 12 */     Throwable throwable = thrown;
/* 13 */     while (throwable != null) {
/*    */       
/* 15 */       if (throwable.getCause() == throwable) {
/* 16 */         return sb.toString();
/*    */       }
/* 18 */       if (throwable.getMessage() != null) {
/* 19 */         sb.append(throwable.getMessage()).append(joiner);
/*    */       }
/* 21 */       throwable = throwable.getCause();
/*    */     } 
/* 23 */     sb.setLength(sb.length() - joiner.length());
/* 24 */     return sb.toString();
/*    */   }
/*    */   public static String toStringWithStack(@Nonnull Throwable t) {
/*    */     
/* 28 */     try { StringWriter out = new StringWriter(); 
/* 29 */       try { t.printStackTrace(new PrintWriter(out));
/* 30 */         String str = out.toString();
/* 31 */         out.close(); return str; } catch (Throwable throwable) { try { out.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException iOException)
/*    */     
/* 33 */     { return t.toString(); }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\ExceptionUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */