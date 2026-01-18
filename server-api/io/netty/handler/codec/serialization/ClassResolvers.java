/*     */ package io.netty.handler.codec.serialization;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.lang.ref.Reference;
/*     */ import java.util.HashMap;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public final class ClassResolvers
/*     */ {
/*     */   public static ClassResolver cacheDisabled(ClassLoader classLoader) {
/*  46 */     return new ClassLoaderClassResolver(defaultClassLoader(classLoader));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ClassResolver weakCachingResolver(ClassLoader classLoader) {
/*  57 */     return new CachingClassResolver(new ClassLoaderClassResolver(
/*  58 */           defaultClassLoader(classLoader)), new WeakReferenceMap<>(new HashMap<>()));
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
/*     */   public static ClassResolver softCachingResolver(ClassLoader classLoader) {
/*  70 */     return new CachingClassResolver(new ClassLoaderClassResolver(
/*  71 */           defaultClassLoader(classLoader)), new SoftReferenceMap<>(new HashMap<>()));
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
/*     */   public static ClassResolver weakCachingConcurrentResolver(ClassLoader classLoader) {
/*  83 */     return new CachingClassResolver(new ClassLoaderClassResolver(
/*  84 */           defaultClassLoader(classLoader)), new WeakReferenceMap<>(new ConcurrentHashMap<>()));
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
/*     */   public static ClassResolver softCachingConcurrentResolver(ClassLoader classLoader) {
/*  96 */     return new CachingClassResolver(new ClassLoaderClassResolver(
/*  97 */           defaultClassLoader(classLoader)), new SoftReferenceMap<>(new ConcurrentHashMap<>()));
/*     */   }
/*     */ 
/*     */   
/*     */   static ClassLoader defaultClassLoader(ClassLoader classLoader) {
/* 102 */     if (classLoader != null) {
/* 103 */       return classLoader;
/*     */     }
/*     */     
/* 106 */     ClassLoader contextClassLoader = PlatformDependent.getContextClassLoader();
/* 107 */     if (contextClassLoader != null) {
/* 108 */       return contextClassLoader;
/*     */     }
/*     */     
/* 111 */     return PlatformDependent.getClassLoader(ClassResolvers.class);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\serialization\ClassResolvers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */