/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bson.codecs.configuration.CodecConfigurationException;
/*     */ import org.bson.codecs.pojo.annotations.BsonId;
/*     */ import org.bson.codecs.pojo.annotations.BsonProperty;
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
/*     */ final class CreatorExecutable<T>
/*     */ {
/*     */   private final Class<T> clazz;
/*     */   private final Constructor<T> constructor;
/*     */   private final Method method;
/*  37 */   private final List<BsonProperty> properties = new ArrayList<>();
/*     */   private final Integer idPropertyIndex;
/*  39 */   private final List<Class<?>> parameterTypes = new ArrayList<>();
/*  40 */   private final List<Type> parameterGenericTypes = new ArrayList<>();
/*     */   
/*     */   CreatorExecutable(Class<T> clazz, Constructor<T> constructor) {
/*  43 */     this(clazz, constructor, null);
/*     */   }
/*     */   
/*     */   CreatorExecutable(Class<T> clazz, Method method) {
/*  47 */     this(clazz, null, method);
/*     */   }
/*     */   
/*     */   private CreatorExecutable(Class<T> clazz, Constructor<T> constructor, Method method) {
/*  51 */     this.clazz = clazz;
/*  52 */     this.constructor = constructor;
/*  53 */     this.method = method;
/*  54 */     Integer idPropertyIndex = null;
/*     */     
/*  56 */     if (constructor != null || method != null) {
/*  57 */       Class<?>[] paramTypes = (constructor != null) ? constructor.getParameterTypes() : method.getParameterTypes();
/*  58 */       Type[] genericParamTypes = (constructor != null) ? constructor.getGenericParameterTypes() : method.getGenericParameterTypes();
/*  59 */       this.parameterTypes.addAll(Arrays.asList(paramTypes));
/*  60 */       this.parameterGenericTypes.addAll(Arrays.asList(genericParamTypes));
/*     */       
/*  62 */       Annotation[][] parameterAnnotations = (constructor != null) ? constructor.getParameterAnnotations() : method.getParameterAnnotations();
/*     */       
/*  64 */       for (int i = 0; i < parameterAnnotations.length; i++) {
/*  65 */         Annotation[] parameterAnnotation = parameterAnnotations[i];
/*     */         
/*  67 */         for (Annotation annotation : parameterAnnotation) {
/*  68 */           if (annotation.annotationType().equals(BsonProperty.class)) {
/*  69 */             this.properties.add((BsonProperty)annotation);
/*     */             
/*     */             break;
/*     */           } 
/*  73 */           if (annotation.annotationType().equals(BsonId.class)) {
/*  74 */             this.properties.add(null);
/*  75 */             idPropertyIndex = Integer.valueOf(i);
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  82 */     this.idPropertyIndex = idPropertyIndex;
/*     */   }
/*     */   
/*     */   Class<T> getType() {
/*  86 */     return this.clazz;
/*     */   }
/*     */   
/*     */   List<BsonProperty> getProperties() {
/*  90 */     return this.properties;
/*     */   }
/*     */   
/*     */   Integer getIdPropertyIndex() {
/*  94 */     return this.idPropertyIndex;
/*     */   }
/*     */   
/*     */   List<Class<?>> getParameterTypes() {
/*  98 */     return this.parameterTypes;
/*     */   }
/*     */   
/*     */   List<Type> getParameterGenericTypes() {
/* 102 */     return this.parameterGenericTypes;
/*     */   }
/*     */ 
/*     */   
/*     */   T getInstance() {
/* 107 */     checkHasAnExecutable();
/*     */     try {
/* 109 */       if (this.constructor != null) {
/* 110 */         return this.constructor.newInstance(new Object[0]);
/*     */       }
/* 112 */       return (T)this.method.invoke(this.clazz, new Object[0]);
/*     */     }
/* 114 */     catch (Exception e) {
/* 115 */       throw new CodecConfigurationException(e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   T getInstance(Object[] params) {
/* 121 */     checkHasAnExecutable();
/*     */     try {
/* 123 */       if (this.constructor != null) {
/* 124 */         return this.constructor.newInstance(params);
/*     */       }
/* 126 */       return (T)this.method.invoke(this.clazz, params);
/*     */     }
/* 128 */     catch (Exception e) {
/* 129 */       throw new CodecConfigurationException(e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   CodecConfigurationException getError(Class<?> clazz, String msg) {
/* 135 */     return getError(clazz, (this.constructor != null), msg);
/*     */   }
/*     */   
/*     */   private void checkHasAnExecutable() {
/* 139 */     if (this.constructor == null && this.method == null) {
/* 140 */       throw new CodecConfigurationException(String.format("Cannot find a public constructor for '%s'.", new Object[] { this.clazz.getSimpleName() }));
/*     */     }
/*     */   }
/*     */   
/*     */   private static CodecConfigurationException getError(Class<?> clazz, boolean isConstructor, String msg) {
/* 145 */     return new CodecConfigurationException(String.format("Invalid @BsonCreator %s in %s. %s", new Object[] { isConstructor ? "constructor" : "method", clazz
/* 146 */             .getSimpleName(), msg }));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\CreatorExecutable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */