/*     */ package com.google.common.flogger.util;
/*     */ 
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
/*     */ public final class StaticMethodCaller
/*     */ {
/*     */   @NullableDecl
/*     */   public static <T> T getInstanceFromSystemProperty(String propertyName, @NullableDecl String defaultClassName, Class<T> type) {
/*  43 */     String className = readProperty(propertyName, defaultClassName);
/*  44 */     if (className == null) {
/*  45 */       return null;
/*     */     }
/*  47 */     return callStaticMethod(className, "getInstance", type);
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
/*     */   
/*     */   @NullableDecl
/*     */   public static <T> T callGetterFromSystemProperty(String propertyName, @NullableDecl String defaultValue, Class<T> type) {
/*  64 */     String getter = readProperty(propertyName, defaultValue);
/*  65 */     if (getter == null) {
/*  66 */       return null;
/*     */     }
/*  68 */     int idx = getter.indexOf('#');
/*  69 */     if (idx <= 0 || idx == getter.length() - 1) {
/*  70 */       error("invalid getter (expected <class>#<method>): %s\n", new Object[] { getter });
/*  71 */       return null;
/*     */     } 
/*  73 */     return callStaticMethod(getter.substring(0, idx), getter.substring(idx + 1), type);
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
/*     */   @NullableDecl
/*     */   public static <T> T callGetterFromSystemProperty(String propertyName, Class<T> type) {
/*  88 */     return callGetterFromSystemProperty(propertyName, null, type);
/*     */   }
/*     */   
/*     */   private static String readProperty(String propertyName, @NullableDecl String defaultValue) {
/*  92 */     Checks.checkNotNull(propertyName, "property name");
/*     */     try {
/*  94 */       return System.getProperty(propertyName, defaultValue);
/*  95 */     } catch (SecurityException e) {
/*  96 */       error("cannot read property name %s: %s", new Object[] { propertyName, e });
/*     */       
/*  98 */       return null;
/*     */     } 
/*     */   }
/*     */   private static <T> T callStaticMethod(String className, String methodName, Class<T> type) {
/*     */     try {
/* 103 */       return type.cast(Class.forName(className).getMethod(methodName, new Class[0]).invoke(null, new Object[0]));
/* 104 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */     
/* 106 */     } catch (ClassCastException e) {
/* 107 */       error("cannot cast result of calling '%s#%s' to '%s': %s\n", new Object[] { className, methodName, type
/*     */             
/* 109 */             .getName(), e });
/* 110 */     } catch (Exception e) {
/*     */       
/* 112 */       error("cannot call expected no-argument static method '%s#%s': %s\n", new Object[] { className, methodName, e });
/*     */     } 
/*     */     
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void error(String msg, Object... args) {
/* 120 */     System.err.println(StaticMethodCaller.class + ": " + String.format(msg, args));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogge\\util\StaticMethodCaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */