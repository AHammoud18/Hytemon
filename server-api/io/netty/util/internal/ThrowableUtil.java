/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
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
/*    */ public final class ThrowableUtil
/*    */ {
/*    */   public static <T extends Throwable> T unknownStackTrace(T cause, Class<?> clazz, String method) {
/* 31 */     cause.setStackTrace(new StackTraceElement[] { new StackTraceElement(clazz.getName(), method, null, -1) });
/* 32 */     return cause;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static String stackTraceToString(Throwable cause) {
/* 43 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 44 */     PrintStream pout = new PrintStream(out);
/* 45 */     cause.printStackTrace(pout);
/* 46 */     pout.flush();
/*    */     try {
/* 48 */       return out.toString();
/*    */     } finally {
/*    */       try {
/* 51 */         out.close();
/* 52 */       } catch (IOException iOException) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static boolean haveSuppressed() {
/* 60 */     return true;
/*    */   }
/*    */   
/*    */   public static void addSuppressed(Throwable target, Throwable suppressed) {
/* 64 */     if (suppressed != null) {
/* 65 */       target.addSuppressed(suppressed);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void addSuppressedAndClear(Throwable target, List<Throwable> suppressed) {
/* 70 */     addSuppressed(target, suppressed);
/* 71 */     suppressed.clear();
/*    */   }
/*    */   
/*    */   public static void addSuppressed(Throwable target, List<Throwable> suppressed) {
/* 75 */     for (Throwable t : suppressed) {
/* 76 */       addSuppressed(target, t);
/*    */     }
/*    */   }
/*    */   
/*    */   public static Throwable[] getSuppressed(Throwable source) {
/* 81 */     return source.getSuppressed();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\ThrowableUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */