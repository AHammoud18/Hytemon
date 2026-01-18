/*     */ package joptsimple.internal;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import joptsimple.ValueConverter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Reflection
/*     */ {
/*     */   private Reflection() {
/*  45 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> ValueConverter<V> findConverter(Class<V> clazz) {
/*  56 */     Class<V> maybeWrapper = Classes.wrapperOf(clazz);
/*     */     
/*  58 */     ValueConverter<V> valueOf = valueOfConverter(maybeWrapper);
/*  59 */     if (valueOf != null) {
/*  60 */       return valueOf;
/*     */     }
/*  62 */     ValueConverter<V> constructor = constructorConverter(maybeWrapper);
/*  63 */     if (constructor != null) {
/*  64 */       return constructor;
/*     */     }
/*  66 */     throw new IllegalArgumentException(clazz + " is not a value type");
/*     */   }
/*     */   
/*     */   private static <V> ValueConverter<V> valueOfConverter(Class<V> clazz) {
/*     */     try {
/*  71 */       Method valueOf = clazz.getMethod("valueOf", new Class[] { String.class });
/*  72 */       if (meetsConverterRequirements(valueOf, clazz)) {
/*  73 */         return new MethodInvokingValueConverter<>(valueOf, clazz);
/*     */       }
/*  75 */       return null;
/*  76 */     } catch (NoSuchMethodException ignored) {
/*  77 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static <V> ValueConverter<V> constructorConverter(Class<V> clazz) {
/*     */     try {
/*  83 */       return new ConstructorInvokingValueConverter<>(clazz.getConstructor(new Class[] { String.class }));
/*  84 */     } catch (NoSuchMethodException ignored) {
/*  85 */       return null;
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
/*     */   public static <T> T instantiate(Constructor<T> constructor, Object... args) {
/*     */     try {
/* 100 */       return constructor.newInstance(args);
/* 101 */     } catch (Exception ex) {
/* 102 */       throw reflectionException(ex);
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
/*     */   public static Object invoke(Method method, Object... args) {
/*     */     try {
/* 116 */       return method.invoke(null, args);
/* 117 */     } catch (Exception ex) {
/* 118 */       throw reflectionException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static <V> V convertWith(ValueConverter<V> converter, String raw) {
/* 124 */     return (converter == null) ? (V)raw : (V)converter.convert(raw);
/*     */   }
/*     */   
/*     */   private static boolean meetsConverterRequirements(Method method, Class<?> expectedReturnType) {
/* 128 */     int modifiers = method.getModifiers();
/* 129 */     return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && expectedReturnType.equals(method.getReturnType()));
/*     */   }
/*     */   
/*     */   private static RuntimeException reflectionException(Exception ex) {
/* 133 */     if (ex instanceof IllegalArgumentException)
/* 134 */       return new ReflectionException(ex); 
/* 135 */     if (ex instanceof java.lang.reflect.InvocationTargetException)
/* 136 */       return new ReflectionException(ex.getCause()); 
/* 137 */     if (ex instanceof RuntimeException) {
/* 138 */       return (RuntimeException)ex;
/*     */     }
/* 140 */     return new ReflectionException(ex);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\internal\Reflection.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */