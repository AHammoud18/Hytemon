/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
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
/*     */ public final class ReflectionUtil
/*     */ {
/*     */   public static Throwable trySetAccessible(AccessibleObject object, boolean checkAccessible) {
/*  35 */     if (checkAccessible && !PlatformDependent0.isExplicitTryReflectionSetAccessible()) {
/*  36 */       return new UnsupportedOperationException("Reflective setAccessible(true) disabled");
/*     */     }
/*     */     try {
/*  39 */       object.setAccessible(true);
/*  40 */       return null;
/*  41 */     } catch (SecurityException e) {
/*  42 */       return e;
/*  43 */     } catch (RuntimeException e) {
/*  44 */       return handleInaccessibleObjectException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RuntimeException handleInaccessibleObjectException(RuntimeException e) {
/*  52 */     if ("java.lang.reflect.InaccessibleObjectException".equals(e.getClass().getName())) {
/*  53 */       return e;
/*     */     }
/*  55 */     throw e;
/*     */   }
/*     */   
/*     */   private static Class<?> fail(Class<?> type, String typeParamName) {
/*  59 */     throw new IllegalStateException("cannot determine the type of the type parameter '" + typeParamName + "': " + type);
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
/*     */   public static Class<?> resolveTypeParameter(Object object, Class<?> parametrizedSuperclass, String typeParamName) {
/*  74 */     Class<?> thisClass = object.getClass();
/*  75 */     Class<?> currentClass = thisClass;
/*     */     while (true) {
/*  77 */       while (currentClass.getSuperclass() == parametrizedSuperclass) {
/*  78 */         int typeParamIndex = -1;
/*  79 */         TypeVariable[] arrayOfTypeVariable = currentClass.getSuperclass().getTypeParameters();
/*  80 */         for (int i = 0; i < arrayOfTypeVariable.length; i++) {
/*  81 */           if (typeParamName.equals(arrayOfTypeVariable[i].getName())) {
/*  82 */             typeParamIndex = i;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*  87 */         if (typeParamIndex < 0) {
/*  88 */           throw new IllegalStateException("unknown type parameter '" + typeParamName + "': " + parametrizedSuperclass);
/*     */         }
/*     */ 
/*     */         
/*  92 */         Type genericSuperType = currentClass.getGenericSuperclass();
/*  93 */         if (!(genericSuperType instanceof ParameterizedType)) {
/*  94 */           return Object.class;
/*     */         }
/*     */         
/*  97 */         Type[] actualTypeParams = ((ParameterizedType)genericSuperType).getActualTypeArguments();
/*     */         
/*  99 */         Type actualTypeParam = actualTypeParams[typeParamIndex];
/* 100 */         if (actualTypeParam instanceof ParameterizedType) {
/* 101 */           actualTypeParam = ((ParameterizedType)actualTypeParam).getRawType();
/*     */         }
/* 103 */         if (actualTypeParam instanceof Class) {
/* 104 */           return (Class)actualTypeParam;
/*     */         }
/* 106 */         if (actualTypeParam instanceof GenericArrayType) {
/* 107 */           Type componentType = ((GenericArrayType)actualTypeParam).getGenericComponentType();
/* 108 */           if (componentType instanceof ParameterizedType) {
/* 109 */             componentType = ((ParameterizedType)componentType).getRawType();
/*     */           }
/* 111 */           if (componentType instanceof Class) {
/* 112 */             return Array.newInstance((Class)componentType, 0).getClass();
/*     */           }
/*     */         } 
/* 115 */         if (actualTypeParam instanceof TypeVariable) {
/*     */           
/* 117 */           TypeVariable<?> v = (TypeVariable)actualTypeParam;
/* 118 */           if (!(v.getGenericDeclaration() instanceof Class)) {
/* 119 */             return Object.class;
/*     */           }
/*     */           
/* 122 */           currentClass = thisClass;
/* 123 */           parametrizedSuperclass = (Class)v.getGenericDeclaration();
/* 124 */           typeParamName = v.getName();
/* 125 */           if (parametrizedSuperclass.isAssignableFrom(thisClass)) {
/*     */             continue;
/*     */           }
/* 128 */           return Object.class;
/*     */         } 
/*     */         
/* 131 */         return fail(thisClass, typeParamName);
/*     */       } 
/* 133 */       currentClass = currentClass.getSuperclass();
/* 134 */       if (currentClass == null)
/* 135 */         return fail(thisClass, typeParamName); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\ReflectionUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */