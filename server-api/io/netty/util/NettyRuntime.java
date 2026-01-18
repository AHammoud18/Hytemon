/*    */ package io.netty.util;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import io.netty.util.internal.SystemPropertyUtil;
/*    */ import java.util.Locale;
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
/*    */ public final class NettyRuntime
/*    */ {
/*    */   static class AvailableProcessorsHolder
/*    */   {
/*    */     private int availableProcessors;
/*    */     
/*    */     synchronized void setAvailableProcessors(int availableProcessors) {
/* 44 */       ObjectUtil.checkPositive(availableProcessors, "availableProcessors");
/* 45 */       if (this.availableProcessors != 0) {
/* 46 */         String message = String.format(Locale.ROOT, "availableProcessors is already set to [%d], rejecting [%d]", new Object[] {
/*    */ 
/*    */               
/* 49 */               Integer.valueOf(this.availableProcessors), 
/* 50 */               Integer.valueOf(availableProcessors) });
/* 51 */         throw new IllegalStateException(message);
/*    */       } 
/* 53 */       this.availableProcessors = availableProcessors;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     @SuppressForbidden(reason = "to obtain default number of available processors")
/*    */     synchronized int availableProcessors() {
/* 65 */       if (this.availableProcessors == 0) {
/*    */         
/* 67 */         int availableProcessors = SystemPropertyUtil.getInt("io.netty.availableProcessors", 
/*    */             
/* 69 */             Runtime.getRuntime().availableProcessors());
/* 70 */         setAvailableProcessors(availableProcessors);
/*    */       } 
/* 72 */       return this.availableProcessors;
/*    */     }
/*    */   }
/*    */   
/* 76 */   private static final AvailableProcessorsHolder holder = new AvailableProcessorsHolder();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setAvailableProcessors(int availableProcessors) {
/* 87 */     holder.setAvailableProcessors(availableProcessors);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int availableProcessors() {
/* 98 */     return holder.availableProcessors();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\NettyRuntime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */