/*     */ package io.sentry.util;
/*     */ 
/*     */ import io.sentry.Hint;
/*     */ import io.sentry.ILogger;
/*     */ import io.sentry.hints.ApplyScopeData;
/*     */ import io.sentry.hints.Backfillable;
/*     */ import io.sentry.hints.Cached;
/*     */ import io.sentry.hints.EventDropReason;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class HintUtils
/*     */ {
/*     */   public static void setIsFromHybridSdk(@NotNull Hint hint, @NotNull String sdkName) {
/*  27 */     if (sdkName.startsWith("sentry.javascript") || sdkName
/*  28 */       .startsWith("sentry.dart") || sdkName
/*  29 */       .startsWith("sentry.dotnet")) {
/*  30 */       hint.set("sentry:isFromHybridSdk", Boolean.valueOf(true));
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isFromHybridSdk(@NotNull Hint hint) {
/*  35 */     return Boolean.TRUE.equals(hint.getAs("sentry:isFromHybridSdk", Boolean.class));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setEventDropReason(@NotNull Hint hint, @NotNull EventDropReason eventDropReason) {
/*  40 */     hint.set("sentry:eventDropReason", eventDropReason);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static EventDropReason getEventDropReason(@NotNull Hint hint) {
/*  45 */     return (EventDropReason)hint.getAs("sentry:eventDropReason", EventDropReason.class);
/*     */   }
/*     */   
/*     */   public static Hint createWithTypeCheckHint(Object typeCheckHint) {
/*  49 */     Hint hint = new Hint();
/*  50 */     setTypeCheckHint(hint, typeCheckHint);
/*  51 */     return hint;
/*     */   }
/*     */   
/*     */   public static void setTypeCheckHint(@NotNull Hint hint, Object typeCheckHint) {
/*  55 */     hint.set("sentry:typeCheckHint", typeCheckHint);
/*     */   }
/*     */   @Nullable
/*     */   public static Object getSentrySdkHint(@NotNull Hint hint) {
/*  59 */     return hint.get("sentry:typeCheckHint");
/*     */   }
/*     */   
/*     */   public static boolean hasType(@NotNull Hint hint, @NotNull Class<?> clazz) {
/*  63 */     Object sentrySdkHint = getSentrySdkHint(hint);
/*  64 */     return clazz.isInstance(sentrySdkHint);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> void runIfDoesNotHaveType(@NotNull Hint hint, @NotNull Class<T> clazz, SentryNullableConsumer<Object> lambda) {
/*  69 */     runIfHasType(hint, clazz, ignored -> {
/*     */         
/*     */         }(value, clazz2) -> lambda.accept(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> void runIfHasType(@NotNull Hint hint, @NotNull Class<T> clazz, SentryConsumer<T> lambda) {
/*  80 */     runIfHasType(hint, clazz, lambda, (value, clazz2) -> {
/*     */         
/*     */         });
/*     */   }
/*     */   public static <T> void runIfHasTypeLogIfNot(@NotNull Hint hint, @NotNull Class<T> clazz, ILogger logger, SentryConsumer<T> lambda) {
/*  85 */     runIfHasType(hint, clazz, lambda, (sentrySdkHint, expectedClass) -> LogUtils.logNotInstanceOf(expectedClass, sentrySdkHint, logger));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> void runIfHasType(@NotNull Hint hint, @NotNull Class<T> clazz, SentryConsumer<T> lambda, SentryHintFallback fallbackLambda) {
/* 100 */     Object sentrySdkHint = getSentrySdkHint(hint);
/* 101 */     if (hasType(hint, clazz) && sentrySdkHint != null) {
/* 102 */       lambda.accept((T)sentrySdkHint);
/*     */     } else {
/* 104 */       fallbackLambda.accept(sentrySdkHint, clazz);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean shouldApplyScopeData(@NotNull Hint hint) {
/* 115 */     return ((!hasType(hint, Cached.class) && !hasType(hint, Backfillable.class)) || 
/* 116 */       hasType(hint, ApplyScopeData.class));
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface SentryConsumer<T> {
/*     */     void accept(@NotNull T param1T);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface SentryNullableConsumer<T> {
/*     */     void accept(@Nullable T param1T);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface SentryHintFallback {
/*     */     void accept(@Nullable Object param1Object, @NotNull Class<?> param1Class);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\HintUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */