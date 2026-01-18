/*    */ package com.google.common.flogger.parameter;
/*    */ 
/*    */ import com.google.common.flogger.backend.FormatOptions;
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
/*    */ public abstract class Parameter
/*    */ {
/*    */   private final int index;
/*    */   private final FormatOptions options;
/*    */   
/*    */   protected Parameter(FormatOptions options, int index) {
/* 41 */     if (options == null) {
/* 42 */       throw new IllegalArgumentException("format options cannot be null");
/*    */     }
/* 44 */     if (index < 0) {
/* 45 */       throw new IllegalArgumentException("invalid index: " + index);
/*    */     }
/* 47 */     this.index = index;
/* 48 */     this.options = options;
/*    */   }
/*    */ 
/*    */   
/*    */   public final int getIndex() {
/* 53 */     return this.index;
/*    */   }
/*    */ 
/*    */   
/*    */   protected final FormatOptions getFormatOptions() {
/* 58 */     return this.options;
/*    */   }
/*    */   
/*    */   public final void accept(ParameterVisitor visitor, Object[] args) {
/* 62 */     if (getIndex() < args.length) {
/* 63 */       Object value = args[getIndex()];
/* 64 */       if (value != null) {
/* 65 */         accept(visitor, value);
/*    */       } else {
/* 67 */         visitor.visitNull();
/*    */       } 
/*    */     } else {
/* 70 */       visitor.visitMissing();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract void accept(ParameterVisitor paramParameterVisitor, Object paramObject);
/*    */   
/*    */   public abstract String getFormat();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\parameter\Parameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */