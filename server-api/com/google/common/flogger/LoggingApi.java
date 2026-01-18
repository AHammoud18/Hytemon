/*     */ package com.google.common.flogger;
/*     */ 
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import com.google.errorprone.annotations.CheckReturnValue;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CheckReturnValue
/*     */ public interface LoggingApi<API extends LoggingApi<API>>
/*     */ {
/*     */   API withCause(@NullableDecl Throwable paramThrowable);
/*     */   
/*     */   API every(int paramInt);
/*     */   
/*     */   API atMostEvery(int paramInt, TimeUnit paramTimeUnit);
/*     */   
/*     */   API withStackTrace(StackSize paramStackSize);
/*     */   
/*     */   <T> API with(MetadataKey<T> paramMetadataKey, @NullableDecl T paramT);
/*     */   
/*     */   <T> API with(MetadataKey<Boolean> paramMetadataKey);
/*     */   
/*     */   API withInjectedLogSite(LogSite paramLogSite);
/*     */   
/*     */   API withInjectedLogSite(String paramString1, String paramString2, int paramInt, @NullableDecl String paramString3);
/*     */   
/*     */   boolean isEnabled();
/*     */   
/*     */   void logVarargs(String paramString, @NullableDecl Object[] paramArrayOfObject);
/*     */   
/*     */   void log();
/*     */   
/*     */   void log(String paramString);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject1, @NullableDecl Object paramObject2);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject1, @NullableDecl Object paramObject2, @NullableDecl Object paramObject3);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject1, @NullableDecl Object paramObject2, @NullableDecl Object paramObject3, @NullableDecl Object paramObject4);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject1, @NullableDecl Object paramObject2, @NullableDecl Object paramObject3, @NullableDecl Object paramObject4, @NullableDecl Object paramObject5);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject1, @NullableDecl Object paramObject2, @NullableDecl Object paramObject3, @NullableDecl Object paramObject4, @NullableDecl Object paramObject5, @NullableDecl Object paramObject6);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject1, @NullableDecl Object paramObject2, @NullableDecl Object paramObject3, @NullableDecl Object paramObject4, @NullableDecl Object paramObject5, @NullableDecl Object paramObject6, @NullableDecl Object paramObject7);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject1, @NullableDecl Object paramObject2, @NullableDecl Object paramObject3, @NullableDecl Object paramObject4, @NullableDecl Object paramObject5, @NullableDecl Object paramObject6, @NullableDecl Object paramObject7, @NullableDecl Object paramObject8);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject1, @NullableDecl Object paramObject2, @NullableDecl Object paramObject3, @NullableDecl Object paramObject4, @NullableDecl Object paramObject5, @NullableDecl Object paramObject6, @NullableDecl Object paramObject7, @NullableDecl Object paramObject8, @NullableDecl Object paramObject9);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject1, @NullableDecl Object paramObject2, @NullableDecl Object paramObject3, @NullableDecl Object paramObject4, @NullableDecl Object paramObject5, @NullableDecl Object paramObject6, @NullableDecl Object paramObject7, @NullableDecl Object paramObject8, @NullableDecl Object paramObject9, @NullableDecl Object paramObject10);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject1, @NullableDecl Object paramObject2, @NullableDecl Object paramObject3, @NullableDecl Object paramObject4, @NullableDecl Object paramObject5, @NullableDecl Object paramObject6, @NullableDecl Object paramObject7, @NullableDecl Object paramObject8, @NullableDecl Object paramObject9, @NullableDecl Object paramObject10, Object... paramVarArgs);
/*     */   
/*     */   void log(String paramString, char paramChar);
/*     */   
/*     */   void log(String paramString, byte paramByte);
/*     */   
/*     */   void log(String paramString, short paramShort);
/*     */   
/*     */   void log(String paramString, int paramInt);
/*     */   
/*     */   void log(String paramString, long paramLong);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject, boolean paramBoolean);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject, char paramChar);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject, byte paramByte);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject, short paramShort);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject, int paramInt);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject, long paramLong);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject, float paramFloat);
/*     */   
/*     */   void log(String paramString, @NullableDecl Object paramObject, double paramDouble);
/*     */   
/*     */   void log(String paramString, boolean paramBoolean, @NullableDecl Object paramObject);
/*     */   
/*     */   void log(String paramString, char paramChar, @NullableDecl Object paramObject);
/*     */   
/*     */   void log(String paramString, byte paramByte, @NullableDecl Object paramObject);
/*     */   
/*     */   void log(String paramString, short paramShort, @NullableDecl Object paramObject);
/*     */   
/*     */   void log(String paramString, int paramInt, @NullableDecl Object paramObject);
/*     */   
/*     */   void log(String paramString, long paramLong, @NullableDecl Object paramObject);
/*     */   
/*     */   void log(String paramString, float paramFloat, @NullableDecl Object paramObject);
/*     */   
/*     */   void log(String paramString, double paramDouble, @NullableDecl Object paramObject);
/*     */   
/*     */   void log(String paramString, boolean paramBoolean1, boolean paramBoolean2);
/*     */   
/*     */   void log(String paramString, char paramChar, boolean paramBoolean);
/*     */   
/*     */   void log(String paramString, byte paramByte, boolean paramBoolean);
/*     */   
/*     */   void log(String paramString, short paramShort, boolean paramBoolean);
/*     */   
/*     */   void log(String paramString, int paramInt, boolean paramBoolean);
/*     */   
/*     */   void log(String paramString, long paramLong, boolean paramBoolean);
/*     */   
/*     */   void log(String paramString, float paramFloat, boolean paramBoolean);
/*     */   
/*     */   void log(String paramString, double paramDouble, boolean paramBoolean);
/*     */   
/*     */   void log(String paramString, boolean paramBoolean, char paramChar);
/*     */   
/*     */   void log(String paramString, char paramChar1, char paramChar2);
/*     */   
/*     */   void log(String paramString, byte paramByte, char paramChar);
/*     */   
/*     */   void log(String paramString, short paramShort, char paramChar);
/*     */   
/*     */   void log(String paramString, int paramInt, char paramChar);
/*     */   
/*     */   void log(String paramString, long paramLong, char paramChar);
/*     */   
/*     */   void log(String paramString, float paramFloat, char paramChar);
/*     */   
/*     */   void log(String paramString, double paramDouble, char paramChar);
/*     */   
/*     */   void log(String paramString, boolean paramBoolean, byte paramByte);
/*     */   
/*     */   void log(String paramString, char paramChar, byte paramByte);
/*     */   
/*     */   void log(String paramString, byte paramByte1, byte paramByte2);
/*     */   
/*     */   void log(String paramString, short paramShort, byte paramByte);
/*     */   
/*     */   void log(String paramString, int paramInt, byte paramByte);
/*     */   
/*     */   void log(String paramString, long paramLong, byte paramByte);
/*     */   
/*     */   void log(String paramString, float paramFloat, byte paramByte);
/*     */   
/*     */   void log(String paramString, double paramDouble, byte paramByte);
/*     */   
/*     */   void log(String paramString, boolean paramBoolean, short paramShort);
/*     */   
/*     */   void log(String paramString, char paramChar, short paramShort);
/*     */   
/*     */   void log(String paramString, byte paramByte, short paramShort);
/*     */   
/*     */   void log(String paramString, short paramShort1, short paramShort2);
/*     */   
/*     */   void log(String paramString, int paramInt, short paramShort);
/*     */   
/*     */   void log(String paramString, long paramLong, short paramShort);
/*     */   
/*     */   void log(String paramString, float paramFloat, short paramShort);
/*     */   
/*     */   void log(String paramString, double paramDouble, short paramShort);
/*     */   
/*     */   void log(String paramString, boolean paramBoolean, int paramInt);
/*     */   
/*     */   void log(String paramString, char paramChar, int paramInt);
/*     */   
/*     */   void log(String paramString, byte paramByte, int paramInt);
/*     */   
/*     */   void log(String paramString, short paramShort, int paramInt);
/*     */   
/*     */   void log(String paramString, int paramInt1, int paramInt2);
/*     */   
/*     */   void log(String paramString, long paramLong, int paramInt);
/*     */   
/*     */   void log(String paramString, float paramFloat, int paramInt);
/*     */   
/*     */   void log(String paramString, double paramDouble, int paramInt);
/*     */   
/*     */   void log(String paramString, boolean paramBoolean, long paramLong);
/*     */   
/*     */   void log(String paramString, char paramChar, long paramLong);
/*     */   
/*     */   void log(String paramString, byte paramByte, long paramLong);
/*     */   
/*     */   void log(String paramString, short paramShort, long paramLong);
/*     */   
/*     */   void log(String paramString, int paramInt, long paramLong);
/*     */   
/*     */   void log(String paramString, long paramLong1, long paramLong2);
/*     */   
/*     */   void log(String paramString, float paramFloat, long paramLong);
/*     */   
/*     */   void log(String paramString, double paramDouble, long paramLong);
/*     */   
/*     */   void log(String paramString, boolean paramBoolean, float paramFloat);
/*     */   
/*     */   void log(String paramString, char paramChar, float paramFloat);
/*     */   
/*     */   void log(String paramString, byte paramByte, float paramFloat);
/*     */   
/*     */   void log(String paramString, short paramShort, float paramFloat);
/*     */   
/*     */   void log(String paramString, int paramInt, float paramFloat);
/*     */   
/*     */   void log(String paramString, long paramLong, float paramFloat);
/*     */   
/*     */   void log(String paramString, float paramFloat1, float paramFloat2);
/*     */   
/*     */   void log(String paramString, double paramDouble, float paramFloat);
/*     */   
/*     */   void log(String paramString, boolean paramBoolean, double paramDouble);
/*     */   
/*     */   void log(String paramString, char paramChar, double paramDouble);
/*     */   
/*     */   void log(String paramString, byte paramByte, double paramDouble);
/*     */   
/*     */   void log(String paramString, short paramShort, double paramDouble);
/*     */   
/*     */   void log(String paramString, int paramInt, double paramDouble);
/*     */   
/*     */   void log(String paramString, long paramLong, double paramDouble);
/*     */   
/*     */   void log(String paramString, float paramFloat, double paramDouble);
/*     */   
/*     */   void log(String paramString, double paramDouble1, double paramDouble2);
/*     */   
/*     */   public static class NoOp<API extends LoggingApi<API>>
/*     */     implements LoggingApi<API>
/*     */   {
/*     */     protected final API noOp() {
/* 646 */       return (API)this;
/*     */     }
/*     */ 
/*     */     
/*     */     public API withInjectedLogSite(LogSite logSite) {
/* 651 */       return noOp();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public API withInjectedLogSite(String internalClassName, String methodName, int encodedLineNumber, @NullableDecl String sourceFileName) {
/* 660 */       return noOp();
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isEnabled() {
/* 665 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final <T> API with(MetadataKey<T> key, @NullableDecl T value) {
/* 671 */       Checks.checkNotNull(key, "metadata key");
/* 672 */       return noOp();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final <T> API with(MetadataKey<Boolean> key) {
/* 678 */       Checks.checkNotNull(key, "metadata key");
/* 679 */       return noOp();
/*     */     }
/*     */ 
/*     */     
/*     */     public final API withCause(@NullableDecl Throwable cause) {
/* 684 */       return noOp();
/*     */     }
/*     */ 
/*     */     
/*     */     public final API every(int n) {
/* 689 */       return noOp();
/*     */     }
/*     */ 
/*     */     
/*     */     public final API atMostEvery(int n, TimeUnit unit) {
/* 694 */       Checks.checkNotNull(unit, "time unit");
/* 695 */       return noOp();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public API withStackTrace(StackSize size) {
/* 701 */       Checks.checkNotNull(size, "stack size");
/* 702 */       return noOp();
/*     */     }
/*     */     
/*     */     public final void logVarargs(String msg, Object[] params) {}
/*     */     
/*     */     public final void log() {}
/*     */     
/*     */     public final void log(String msg) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4, @NullableDecl Object p5) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4, @NullableDecl Object p5, @NullableDecl Object p6) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4, @NullableDecl Object p5, @NullableDecl Object p6, @NullableDecl Object p7) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4, @NullableDecl Object p5, @NullableDecl Object p6, @NullableDecl Object p7, @NullableDecl Object p8) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4, @NullableDecl Object p5, @NullableDecl Object p6, @NullableDecl Object p7, @NullableDecl Object p8, @NullableDecl Object p9) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4, @NullableDecl Object p5, @NullableDecl Object p6, @NullableDecl Object p7, @NullableDecl Object p8, @NullableDecl Object p9, @NullableDecl Object p10) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, @NullableDecl Object p2, @NullableDecl Object p3, @NullableDecl Object p4, @NullableDecl Object p5, @NullableDecl Object p6, @NullableDecl Object p7, @NullableDecl Object p8, @NullableDecl Object p9, @NullableDecl Object p10, Object... rest) {}
/*     */     
/*     */     public final void log(String msg, char p1) {}
/*     */     
/*     */     public final void log(String msg, byte p1) {}
/*     */     
/*     */     public final void log(String msg, short p1) {}
/*     */     
/*     */     public final void log(String msg, int p1) {}
/*     */     
/*     */     public final void log(String msg, long p1) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, boolean p2) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, char p2) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, byte p2) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, short p2) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, int p2) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, long p2) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, float p2) {}
/*     */     
/*     */     public final void log(String msg, @NullableDecl Object p1, double p2) {}
/*     */     
/*     */     public final void log(String msg, boolean p1, @NullableDecl Object p2) {}
/*     */     
/*     */     public final void log(String msg, char p1, @NullableDecl Object p2) {}
/*     */     
/*     */     public final void log(String msg, byte p1, @NullableDecl Object p2) {}
/*     */     
/*     */     public final void log(String msg, short p1, @NullableDecl Object p2) {}
/*     */     
/*     */     public final void log(String msg, int p1, @NullableDecl Object p2) {}
/*     */     
/*     */     public final void log(String msg, long p1, @NullableDecl Object p2) {}
/*     */     
/*     */     public final void log(String msg, float p1, @NullableDecl Object p2) {}
/*     */     
/*     */     public final void log(String msg, double p1, @NullableDecl Object p2) {}
/*     */     
/*     */     public final void log(String msg, boolean p1, boolean p2) {}
/*     */     
/*     */     public final void log(String msg, char p1, boolean p2) {}
/*     */     
/*     */     public final void log(String msg, byte p1, boolean p2) {}
/*     */     
/*     */     public final void log(String msg, short p1, boolean p2) {}
/*     */     
/*     */     public final void log(String msg, int p1, boolean p2) {}
/*     */     
/*     */     public final void log(String msg, long p1, boolean p2) {}
/*     */     
/*     */     public final void log(String msg, float p1, boolean p2) {}
/*     */     
/*     */     public final void log(String msg, double p1, boolean p2) {}
/*     */     
/*     */     public final void log(String msg, boolean p1, char p2) {}
/*     */     
/*     */     public final void log(String msg, char p1, char p2) {}
/*     */     
/*     */     public final void log(String msg, byte p1, char p2) {}
/*     */     
/*     */     public final void log(String msg, short p1, char p2) {}
/*     */     
/*     */     public final void log(String msg, int p1, char p2) {}
/*     */     
/*     */     public final void log(String msg, long p1, char p2) {}
/*     */     
/*     */     public final void log(String msg, float p1, char p2) {}
/*     */     
/*     */     public final void log(String msg, double p1, char p2) {}
/*     */     
/*     */     public final void log(String msg, boolean p1, byte p2) {}
/*     */     
/*     */     public final void log(String msg, char p1, byte p2) {}
/*     */     
/*     */     public final void log(String msg, byte p1, byte p2) {}
/*     */     
/*     */     public final void log(String msg, short p1, byte p2) {}
/*     */     
/*     */     public final void log(String msg, int p1, byte p2) {}
/*     */     
/*     */     public final void log(String msg, long p1, byte p2) {}
/*     */     
/*     */     public final void log(String msg, float p1, byte p2) {}
/*     */     
/*     */     public final void log(String msg, double p1, byte p2) {}
/*     */     
/*     */     public final void log(String msg, boolean p1, short p2) {}
/*     */     
/*     */     public final void log(String msg, char p1, short p2) {}
/*     */     
/*     */     public final void log(String msg, byte p1, short p2) {}
/*     */     
/*     */     public final void log(String msg, short p1, short p2) {}
/*     */     
/*     */     public final void log(String msg, int p1, short p2) {}
/*     */     
/*     */     public final void log(String msg, long p1, short p2) {}
/*     */     
/*     */     public final void log(String msg, float p1, short p2) {}
/*     */     
/*     */     public final void log(String msg, double p1, short p2) {}
/*     */     
/*     */     public final void log(String msg, boolean p1, int p2) {}
/*     */     
/*     */     public final void log(String msg, char p1, int p2) {}
/*     */     
/*     */     public final void log(String msg, byte p1, int p2) {}
/*     */     
/*     */     public final void log(String msg, short p1, int p2) {}
/*     */     
/*     */     public final void log(String msg, int p1, int p2) {}
/*     */     
/*     */     public final void log(String msg, long p1, int p2) {}
/*     */     
/*     */     public final void log(String msg, float p1, int p2) {}
/*     */     
/*     */     public final void log(String msg, double p1, int p2) {}
/*     */     
/*     */     public final void log(String msg, boolean p1, long p2) {}
/*     */     
/*     */     public final void log(String msg, char p1, long p2) {}
/*     */     
/*     */     public final void log(String msg, byte p1, long p2) {}
/*     */     
/*     */     public final void log(String msg, short p1, long p2) {}
/*     */     
/*     */     public final void log(String msg, int p1, long p2) {}
/*     */     
/*     */     public final void log(String msg, long p1, long p2) {}
/*     */     
/*     */     public final void log(String msg, float p1, long p2) {}
/*     */     
/*     */     public final void log(String msg, double p1, long p2) {}
/*     */     
/*     */     public final void log(String msg, boolean p1, float p2) {}
/*     */     
/*     */     public final void log(String msg, char p1, float p2) {}
/*     */     
/*     */     public final void log(String msg, byte p1, float p2) {}
/*     */     
/*     */     public final void log(String msg, short p1, float p2) {}
/*     */     
/*     */     public final void log(String msg, int p1, float p2) {}
/*     */     
/*     */     public final void log(String msg, long p1, float p2) {}
/*     */     
/*     */     public final void log(String msg, float p1, float p2) {}
/*     */     
/*     */     public final void log(String msg, double p1, float p2) {}
/*     */     
/*     */     public final void log(String msg, boolean p1, double p2) {}
/*     */     
/*     */     public final void log(String msg, char p1, double p2) {}
/*     */     
/*     */     public final void log(String msg, byte p1, double p2) {}
/*     */     
/*     */     public final void log(String msg, short p1, double p2) {}
/*     */     
/*     */     public final void log(String msg, int p1, double p2) {}
/*     */     
/*     */     public final void log(String msg, long p1, double p2) {}
/*     */     
/*     */     public final void log(String msg, float p1, double p2) {}
/*     */     
/*     */     public final void log(String msg, double p1, double p2) {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\LoggingApi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */