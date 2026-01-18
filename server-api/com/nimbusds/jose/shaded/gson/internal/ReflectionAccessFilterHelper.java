/*     */ package com.nimbusds.jose.shaded.gson.internal;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.gson.ReflectionAccessFilter;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectionAccessFilterHelper
/*     */ {
/*     */   public static boolean isJavaType(Class<?> c) {
/*  34 */     return isJavaType(c.getName());
/*     */   }
/*     */   
/*     */   private static boolean isJavaType(String className) {
/*  38 */     return (className.startsWith("java.") || className.startsWith("javax."));
/*     */   }
/*     */   
/*     */   public static boolean isAndroidType(Class<?> c) {
/*  42 */     return isAndroidType(c.getName());
/*     */   }
/*     */   
/*     */   private static boolean isAndroidType(String className) {
/*  46 */     return (className.startsWith("android.") || className
/*  47 */       .startsWith("androidx.") || 
/*  48 */       isJavaType(className));
/*     */   }
/*     */   
/*     */   public static boolean isAnyPlatformType(Class<?> c) {
/*  52 */     String className = c.getName();
/*  53 */     return (isAndroidType(className) || className
/*  54 */       .startsWith("kotlin.") || className
/*  55 */       .startsWith("kotlinx.") || className
/*  56 */       .startsWith("scala."));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReflectionAccessFilter.FilterResult getFilterResult(List<ReflectionAccessFilter> reflectionFilters, Class<?> c) {
/*  66 */     for (ReflectionAccessFilter filter : reflectionFilters) {
/*  67 */       ReflectionAccessFilter.FilterResult result = filter.check(c);
/*  68 */       if (result != ReflectionAccessFilter.FilterResult.INDECISIVE) {
/*  69 */         return result;
/*     */       }
/*     */     } 
/*  72 */     return ReflectionAccessFilter.FilterResult.ALLOW;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canAccess(AccessibleObject accessibleObject, Object object) {
/*  77 */     return AccessChecker.INSTANCE.canAccess(accessibleObject, object);
/*     */   }
/*     */   
/*     */   private static abstract class AccessChecker {
/*     */     public static final AccessChecker INSTANCE;
/*     */     
/*     */     static {
/*  84 */       AccessChecker accessChecker = null;
/*     */       
/*  86 */       if (JavaVersion.isJava9OrLater()) {
/*     */         
/*     */         try {
/*  89 */           final Method canAccessMethod = AccessibleObject.class.getDeclaredMethod("canAccess", new Class[] { Object.class });
/*  90 */           accessChecker = new AccessChecker()
/*     */             {
/*     */               public boolean canAccess(AccessibleObject accessibleObject, Object object)
/*     */               {
/*     */                 try {
/*  95 */                   return ((Boolean)canAccessMethod.invoke(accessibleObject, new Object[] { object })).booleanValue();
/*  96 */                 } catch (Exception e) {
/*  97 */                   throw new RuntimeException("Failed invoking canAccess", e);
/*     */                 } 
/*     */               }
/*     */             };
/* 101 */         } catch (NoSuchMethodException noSuchMethodException) {}
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 106 */       if (accessChecker == null) {
/* 107 */         accessChecker = new AccessChecker()
/*     */           {
/*     */             
/*     */             public boolean canAccess(AccessibleObject accessibleObject, Object object)
/*     */             {
/* 112 */               return true;
/*     */             }
/*     */           };
/*     */       }
/* 116 */       INSTANCE = accessChecker;
/*     */     }
/*     */     
/*     */     private AccessChecker() {}
/*     */     
/*     */     public abstract boolean canAccess(AccessibleObject param1AccessibleObject, Object param1Object);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\internal\ReflectionAccessFilterHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */