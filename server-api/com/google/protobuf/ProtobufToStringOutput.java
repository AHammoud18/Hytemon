/*    */ package com.google.protobuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ProtobufToStringOutput
/*    */ {
/*    */   private enum OutputMode
/*    */   {
/* 13 */     DEBUG_FORMAT,
/* 14 */     TEXT_FORMAT,
/* 15 */     DEFAULT_FORMAT;
/*    */   }
/*    */ 
/*    */   
/* 19 */   private static final ThreadLocal<OutputMode> outputMode = ThreadLocal.withInitial(() -> OutputMode.DEFAULT_FORMAT);
/*    */ 
/*    */ 
/*    */   
/*    */   private static OutputMode setOutputMode(OutputMode newMode) {
/* 24 */     OutputMode oldMode = outputMode.get();
/* 25 */     outputMode.set(newMode);
/* 26 */     return oldMode;
/*    */   }
/*    */   
/*    */   private static void callWithSpecificFormat(Runnable impl, OutputMode mode) {
/* 30 */     OutputMode oldMode = setOutputMode(mode);
/*    */     try {
/* 32 */       impl.run();
/*    */     } finally {
/* 34 */       OutputMode outputMode = setOutputMode(oldMode);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void callWithDebugFormat(Runnable impl) {
/* 39 */     callWithSpecificFormat(impl, OutputMode.DEBUG_FORMAT);
/*    */   }
/*    */   
/*    */   public static void callWithTextFormat(Runnable impl) {
/* 43 */     callWithSpecificFormat(impl, OutputMode.TEXT_FORMAT);
/*    */   }
/*    */   
/*    */   public static boolean shouldOutputDebugFormat() {
/* 47 */     return (outputMode.get() == OutputMode.DEBUG_FORMAT);
/*    */   }
/*    */   
/*    */   public static boolean isDefaultFormat() {
/* 51 */     return (outputMode.get() == OutputMode.DEFAULT_FORMAT);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\ProtobufToStringOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */