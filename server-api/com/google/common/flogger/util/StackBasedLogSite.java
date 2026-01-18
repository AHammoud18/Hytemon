/*    */ package com.google.common.flogger.util;
/*    */ 
/*    */ import com.google.common.flogger.LogSite;
/*    */ import com.google.errorprone.annotations.CheckReturnValue;
/*    */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
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
/*    */ @CheckReturnValue
/*    */ public final class StackBasedLogSite
/*    */   extends LogSite
/*    */ {
/*    */   private final StackTraceElement stackElement;
/*    */   
/*    */   public StackBasedLogSite(StackTraceElement stackElement) {
/* 42 */     this.stackElement = Checks.<StackTraceElement>checkNotNull(stackElement, "stack element");
/*    */   }
/*    */ 
/*    */   
/*    */   public String getClassName() {
/* 47 */     return this.stackElement.getClassName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMethodName() {
/* 52 */     return this.stackElement.getMethodName();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLineNumber() {
/* 58 */     return Math.max(this.stackElement.getLineNumber(), 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFileName() {
/* 63 */     return this.stackElement.getFileName();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@NullableDecl Object obj) {
/* 68 */     return (obj instanceof StackBasedLogSite && this.stackElement
/* 69 */       .equals(((StackBasedLogSite)obj).stackElement));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 77 */     return this.stackElement.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogge\\util\StackBasedLogSite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */