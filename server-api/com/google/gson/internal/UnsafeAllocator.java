/*     */ package com.google.gson.internal;
/*     */ 
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class UnsafeAllocator
/*     */ {
/*     */   private static void assertInstantiable(Class<?> c) {
/*  39 */     String exceptionMessage = ConstructorConstructor.checkInstantiable(c);
/*  40 */     if (exceptionMessage != null) {
/*  41 */       throw new AssertionError("UnsafeAllocator is used for non-instantiable type: " + exceptionMessage);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  46 */   public static final UnsafeAllocator INSTANCE = create();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static UnsafeAllocator create() {
/*     */     try {
/*  54 */       Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
/*  55 */       Field f = unsafeClass.getDeclaredField("theUnsafe");
/*  56 */       f.setAccessible(true);
/*  57 */       final Object unsafe = f.get(null);
/*  58 */       final Method allocateInstance = unsafeClass.getMethod("allocateInstance", new Class[] { Class.class });
/*  59 */       return new UnsafeAllocator()
/*     */         {
/*     */           public <T> T newInstance(Class<T> c) throws Exception
/*     */           {
/*  63 */             UnsafeAllocator.assertInstantiable(c);
/*  64 */             return (T)allocateInstance.invoke(unsafe, new Object[] { c });
/*     */           }
/*     */         };
/*  67 */     } catch (Exception exception) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  78 */         Method getConstructorId = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[] { Class.class });
/*  79 */         getConstructorId.setAccessible(true);
/*  80 */         final int constructorId = ((Integer)getConstructorId.invoke(null, new Object[] { Object.class })).intValue();
/*     */         
/*  82 */         final Method newInstance = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[] { Class.class, int.class });
/*  83 */         newInstance.setAccessible(true);
/*  84 */         return new UnsafeAllocator()
/*     */           {
/*     */             public <T> T newInstance(Class<T> c) throws Exception
/*     */             {
/*  88 */               UnsafeAllocator.assertInstantiable(c);
/*  89 */               return (T)newInstance.invoke(null, new Object[] { c, Integer.valueOf(this.val$constructorId) });
/*     */             }
/*     */           };
/*  92 */       } catch (Exception exception1) {
/*     */ 
/*     */         
/*     */         try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 103 */           final Method newInstance = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[] { Class.class, Class.class });
/* 104 */           newInstance.setAccessible(true);
/* 105 */           return new UnsafeAllocator()
/*     */             {
/*     */               public <T> T newInstance(Class<T> c) throws Exception
/*     */               {
/* 109 */                 UnsafeAllocator.assertInstantiable(c);
/* 110 */                 return (T)newInstance.invoke(null, new Object[] { c, Object.class });
/*     */               }
/*     */             };
/* 113 */         } catch (Exception exception2) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 118 */           return new UnsafeAllocator()
/*     */             {
/*     */               public <T> T newInstance(Class<T> c) {
/* 121 */                 throw new UnsupportedOperationException("Cannot allocate " + c + ". Usage of JDK sun.misc.Unsafe is enabled, but it could not be used. Make sure your runtime is configured correctly.");
/*     */               }
/*     */             };
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract <T> T newInstance(Class<T> paramClass) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\UnsafeAllocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */