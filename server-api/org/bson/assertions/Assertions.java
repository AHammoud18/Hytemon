/*     */ package org.bson.assertions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Assertions
/*     */ {
/*     */   public static <T> T notNull(String name, T value) {
/*  34 */     if (value == null) {
/*  35 */       throw new IllegalArgumentException(name + " can not be null");
/*     */     }
/*  37 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void isTrue(String name, boolean condition) {
/*  48 */     if (!condition) {
/*  49 */       throw new IllegalStateException("state should be: " + name);
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
/*     */   public static void isTrueArgument(String name, boolean condition) {
/*  61 */     if (!condition) {
/*  62 */       throw new IllegalArgumentException("state should be: " + name);
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
/*     */ 
/*     */   
/*     */   public static <T> T isTrueArgument(String name, T value, boolean condition) {
/*  79 */     if (!condition) {
/*  80 */       throw new IllegalArgumentException("state should be: " + name);
/*     */     }
/*  82 */     return value;
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
/*     */   public static <T> T convertToType(Class<T> clazz, Object value, String errorMessage) {
/*  97 */     if (!clazz.isAssignableFrom(value.getClass())) {
/*  98 */       throw new IllegalArgumentException(errorMessage);
/*     */     }
/* 100 */     return (T)value;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\assertions\Assertions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */