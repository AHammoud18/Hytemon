/*    */ package io.netty.util;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ @Deprecated
/*    */ public class ResourceLeakException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 7186453858343358280L;
/*    */   private final StackTraceElement[] cachedStackTrace;
/*    */   
/*    */   public ResourceLeakException() {
/* 32 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */   
/*    */   public ResourceLeakException(String message) {
/* 36 */     super(message);
/* 37 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */   
/*    */   public ResourceLeakException(String message, Throwable cause) {
/* 41 */     super(message, cause);
/* 42 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */   
/*    */   public ResourceLeakException(Throwable cause) {
/* 46 */     super(cause);
/* 47 */     this.cachedStackTrace = getStackTrace();
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 52 */     int hashCode = 0;
/* 53 */     for (StackTraceElement e : this.cachedStackTrace) {
/* 54 */       hashCode = hashCode * 31 + e.hashCode();
/*    */     }
/* 56 */     return hashCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 61 */     if (!(o instanceof ResourceLeakException)) {
/* 62 */       return false;
/*    */     }
/* 64 */     if (o == this) {
/* 65 */       return true;
/*    */     }
/*    */     
/* 68 */     return Arrays.equals((Object[])this.cachedStackTrace, (Object[])((ResourceLeakException)o).cachedStackTrace);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\ResourceLeakException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */