/*     */ package com.google.common.flogger.util;
/*     */ 
/*     */ import com.google.errorprone.annotations.CheckReturnValue;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
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
/*     */ @CheckReturnValue
/*     */ final class FastStackGetter
/*     */ {
/*     */   private final Object javaLangAccess;
/*     */   private final Method getElementMethod;
/*     */   private final Method getDepthMethod;
/*     */   
/*     */   @NullableDecl
/*     */   public static FastStackGetter createIfSupported() {
/*     */     try {
/*  38 */       Object javaLangAccess = Class.forName("sun.misc.SharedSecrets").getMethod("getJavaLangAccess", new Class[0]).invoke(null, new Object[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  43 */       Method getElementMethod = Class.forName("sun.misc.JavaLangAccess").getMethod("getStackTraceElement", new Class[] { Throwable.class, int.class });
/*     */       
/*  45 */       Method getDepthMethod = Class.forName("sun.misc.JavaLangAccess").getMethod("getStackTraceDepth", new Class[] { Throwable.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  51 */       StackTraceElement unusedElement = (StackTraceElement)getElementMethod.invoke(javaLangAccess, new Object[] { new Throwable(), Integer.valueOf(0) });
/*     */       
/*  53 */       int unusedDepth = ((Integer)getDepthMethod.invoke(javaLangAccess, new Object[] { new Throwable() })).intValue();
/*     */       
/*  55 */       return new FastStackGetter(javaLangAccess, getElementMethod, getDepthMethod);
/*  56 */     } catch (ThreadDeath t) {
/*     */       
/*  58 */       throw t;
/*  59 */     } catch (Throwable t) {
/*     */ 
/*     */       
/*  62 */       return null;
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
/*     */   private FastStackGetter(Object javaLangAccess, Method getElementMethod, Method getDepthMethod) {
/*  74 */     this.javaLangAccess = javaLangAccess;
/*  75 */     this.getElementMethod = getElementMethod;
/*  76 */     this.getDepthMethod = getDepthMethod;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StackTraceElement getStackTraceElement(Throwable throwable, int n) {
/*     */     try {
/*  86 */       return (StackTraceElement)this.getElementMethod.invoke(this.javaLangAccess, new Object[] { throwable, Integer.valueOf(n) });
/*  87 */     } catch (InvocationTargetException e) {
/*     */       
/*  89 */       if (e.getCause() instanceof RuntimeException)
/*  90 */         throw (RuntimeException)e.getCause(); 
/*  91 */       if (e.getCause() instanceof Error) {
/*  92 */         throw (Error)e.getCause();
/*     */       }
/*     */ 
/*     */       
/*  96 */       throw new RuntimeException(e.getCause());
/*  97 */     } catch (IllegalAccessException e) {
/*     */       
/*  99 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStackTraceDepth(Throwable throwable) {
/*     */     try {
/* 110 */       return ((Integer)this.getDepthMethod.invoke(this.javaLangAccess, new Object[] { throwable })).intValue();
/* 111 */     } catch (InvocationTargetException e) {
/*     */       
/* 113 */       if (e.getCause() instanceof RuntimeException)
/* 114 */         throw (RuntimeException)e.getCause(); 
/* 115 */       if (e.getCause() instanceof Error) {
/* 116 */         throw (Error)e.getCause();
/*     */       }
/*     */ 
/*     */       
/* 120 */       throw new RuntimeException(e.getCause());
/* 121 */     } catch (IllegalAccessException e) {
/*     */       
/* 123 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogge\\util\FastStackGetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */