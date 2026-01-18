/*     */ package com.google.common.flogger.util;
/*     */ 
/*     */ import com.google.errorprone.annotations.CheckReturnValue;
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
/*     */ @CheckReturnValue
/*     */ public final class CallerFinder
/*     */ {
/*  27 */   private static final FastStackGetter stackGetter = FastStackGetter.createIfSupported();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NullableDecl
/*     */   public static StackTraceElement findCallerOf(Class<?> target, Throwable throwable, int skip) {
/*  45 */     Checks.checkNotNull(target, "target");
/*  46 */     Checks.checkNotNull(throwable, "throwable");
/*  47 */     if (skip < 0) {
/*  48 */       throw new IllegalArgumentException("skip count cannot be negative: " + skip);
/*     */     }
/*     */     
/*  51 */     StackTraceElement[] stack = (stackGetter != null) ? null : throwable.getStackTrace();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     boolean foundCaller = false;
/*     */     try {
/*  58 */       for (int index = skip;; index++) {
/*     */ 
/*     */ 
/*     */         
/*  62 */         StackTraceElement element = (stackGetter != null) ? stackGetter.getStackTraceElement(throwable, index) : stack[index];
/*  63 */         if (target.getName().equals(element.getClassName())) {
/*  64 */           foundCaller = true;
/*  65 */         } else if (foundCaller) {
/*  66 */           return element;
/*     */         } 
/*     */       } 
/*  69 */     } catch (Exception e) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  74 */       return null;
/*     */     } 
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
/*     */   public static StackTraceElement[] getStackForCallerOf(Class<?> target, Throwable throwable, int maxDepth, int skip) {
/*     */     StackTraceElement[] stack;
/*     */     int depth;
/*  91 */     Checks.checkNotNull(target, "target");
/*  92 */     Checks.checkNotNull(throwable, "throwable");
/*  93 */     if (maxDepth <= 0 && maxDepth != -1) {
/*  94 */       throw new IllegalArgumentException("invalid maximum depth: " + maxDepth);
/*     */     }
/*  96 */     if (skip < 0) {
/*  97 */       throw new IllegalArgumentException("skip count cannot be negative: " + skip);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 102 */     if (stackGetter != null) {
/* 103 */       stack = null;
/* 104 */       depth = stackGetter.getStackTraceDepth(throwable);
/*     */     } else {
/* 106 */       stack = throwable.getStackTrace();
/* 107 */       depth = stack.length;
/*     */     } 
/* 109 */     boolean foundCaller = false;
/* 110 */     for (int index = skip; index < depth; index++) {
/*     */       
/* 112 */       StackTraceElement element = (stackGetter != null) ? stackGetter.getStackTraceElement(throwable, index) : stack[index];
/* 113 */       if (target.getName().equals(element.getClassName())) {
/* 114 */         foundCaller = true;
/* 115 */       } else if (foundCaller) {
/*     */         
/* 117 */         int elementsToAdd = depth - index;
/* 118 */         if (maxDepth > 0 && maxDepth < elementsToAdd) {
/* 119 */           elementsToAdd = maxDepth;
/*     */         }
/* 121 */         StackTraceElement[] syntheticStack = new StackTraceElement[elementsToAdd];
/* 122 */         syntheticStack[0] = element;
/* 123 */         for (int n = 1; n < elementsToAdd; n++) {
/* 124 */           syntheticStack[n] = 
/* 125 */             (stackGetter != null) ? 
/* 126 */             stackGetter.getStackTraceElement(throwable, index + n) : 
/* 127 */             stack[index + n];
/*     */         }
/* 129 */         return syntheticStack;
/*     */       } 
/*     */     } 
/* 132 */     return new StackTraceElement[0];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogge\\util\CallerFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */