/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ final class PropertyReflectionUtils
/*     */ {
/*     */   private static final String IS_PREFIX = "is";
/*     */   private static final String GET_PREFIX = "get";
/*     */   private static final String SET_PREFIX = "set";
/*     */   
/*     */   static boolean isGetter(Method method) {
/*  34 */     if ((method.getParameterTypes()).length > 0)
/*  35 */       return false; 
/*  36 */     if (method.getName().startsWith("get") && method.getName().length() > "get".length())
/*  37 */       return Character.isUpperCase(method.getName().charAt("get".length())); 
/*  38 */     if (method.getName().startsWith("is") && method.getName().length() > "is".length()) {
/*  39 */       return Character.isUpperCase(method.getName().charAt("is".length()));
/*     */     }
/*  41 */     return false;
/*     */   }
/*     */   
/*     */   static boolean isSetter(Method method) {
/*  45 */     if (method.getName().startsWith("set") && method.getName().length() > "set".length() && (method
/*  46 */       .getParameterTypes()).length == 1) {
/*  47 */       return Character.isUpperCase(method.getName().charAt("set".length()));
/*     */     }
/*  49 */     return false;
/*     */   }
/*     */   
/*     */   static String toPropertyName(Method method) {
/*  53 */     String name = method.getName();
/*  54 */     String propertyName = name.substring(name.startsWith("is") ? 2 : 3, name.length());
/*  55 */     char[] chars = propertyName.toCharArray();
/*  56 */     chars[0] = Character.toLowerCase(chars[0]);
/*  57 */     return new String(chars);
/*     */   }
/*     */   
/*     */   static PropertyMethods getPropertyMethods(Class<?> clazz) {
/*  61 */     List<Method> setters = new ArrayList<>();
/*  62 */     List<Method> getters = new ArrayList<>();
/*     */ 
/*     */     
/*  65 */     for (Class<?> i : clazz.getInterfaces()) {
/*  66 */       for (Method method : i.getDeclaredMethods()) {
/*  67 */         if (method.isDefault()) {
/*  68 */           verifyAddMethodToList(method, getters, setters);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  73 */     for (Method method : clazz.getDeclaredMethods()) {
/*  74 */       verifyAddMethodToList(method, getters, setters);
/*     */     }
/*     */     
/*  77 */     return new PropertyMethods(getters, setters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void verifyAddMethodToList(Method method, List<Method> getters, List<Method> setters) {
/*  85 */     if (Modifier.isPublic(method.getModifiers()) && !method.isBridge())
/*  86 */       if (isGetter(method)) {
/*  87 */         getters.add(method);
/*  88 */       } else if (isSetter(method)) {
/*     */         
/*  90 */         setters.add(method);
/*     */       }  
/*     */   }
/*     */   
/*     */   static class PropertyMethods
/*     */   {
/*     */     private final Collection<Method> getterMethods;
/*     */     private final Collection<Method> setterMethods;
/*     */     
/*     */     PropertyMethods(Collection<Method> getterMethods, Collection<Method> setterMethods) {
/* 100 */       this.getterMethods = getterMethods;
/* 101 */       this.setterMethods = setterMethods;
/*     */     }
/*     */     
/*     */     Collection<Method> getGetterMethods() {
/* 105 */       return this.getterMethods;
/*     */     }
/*     */     
/*     */     Collection<Method> getSetterMethods() {
/* 109 */       return this.setterMethods;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\PropertyReflectionUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */