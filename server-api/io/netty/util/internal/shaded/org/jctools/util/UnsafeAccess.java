/*     */ package io.netty.util.internal.shaded.org.jctools.util;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnsafeAccess
/*     */ {
/*     */   public static final boolean SUPPORTS_GET_AND_SET_REF;
/*     */   public static final boolean SUPPORTS_GET_AND_ADD_LONG;
/*  44 */   public static final Unsafe UNSAFE = getUnsafe(); static {
/*  45 */     SUPPORTS_GET_AND_SET_REF = hasGetAndSetSupport();
/*  46 */     SUPPORTS_GET_AND_ADD_LONG = hasGetAndAddLongSupport();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Unsafe getUnsafe() {
/*     */     Unsafe instance;
/*     */     try {
/*  54 */       Field field = Unsafe.class.getDeclaredField("theUnsafe");
/*  55 */       field.setAccessible(true);
/*  56 */       instance = (Unsafe)field.get(null);
/*     */     }
/*  58 */     catch (Exception ignored) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */         
/*  65 */         Constructor<Unsafe> c = Unsafe.class.getDeclaredConstructor(new Class[0]);
/*  66 */         c.setAccessible(true);
/*  67 */         instance = c.newInstance(new Object[0]);
/*     */       }
/*  69 */       catch (Exception e) {
/*     */         
/*  71 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } 
/*  74 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean hasGetAndSetSupport() {
/*     */     try {
/*  81 */       Unsafe.class.getMethod("getAndSetObject", new Class[] { Object.class, long.class, Object.class });
/*  82 */       return true;
/*     */     }
/*  84 */     catch (Exception exception) {
/*     */ 
/*     */       
/*  87 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasGetAndAddLongSupport() {
/*     */     try {
/*  94 */       Unsafe.class.getMethod("getAndAddLong", new Class[] { Object.class, long.class, long.class });
/*  95 */       return true;
/*     */     }
/*  97 */     catch (Exception exception) {
/*     */ 
/*     */       
/* 100 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static long fieldOffset(Class clz, String fieldName) throws RuntimeException {
/*     */     try {
/* 107 */       return UNSAFE.objectFieldOffset(clz.getDeclaredField(fieldName));
/*     */     }
/* 109 */     catch (NoSuchFieldException e) {
/*     */       
/* 111 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\shaded\org\jctool\\util\UnsafeAccess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */