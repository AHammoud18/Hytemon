/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.bson.assertions.Assertions;
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
/*     */ final class PojoBuilderHelper
/*     */ {
/*     */   static <T> void configureClassModelBuilder(ClassModelBuilder<T> classModelBuilder, Class<T> clazz) {
/*  48 */     classModelBuilder.type((Class<T>)Assertions.notNull("clazz", clazz));
/*     */     
/*  50 */     ArrayList<Annotation> annotations = new ArrayList<>();
/*  51 */     Set<String> propertyNames = new TreeSet<>();
/*  52 */     Map<String, TypeParameterMap> propertyTypeParameterMap = new HashMap<>();
/*  53 */     Class<? super T> currentClass = clazz;
/*  54 */     String declaringClassName = clazz.getSimpleName();
/*  55 */     TypeData<?> parentClassTypeData = null;
/*     */     
/*  57 */     Map<String, PropertyMetadata<?>> propertyNameMap = new HashMap<>();
/*  58 */     while (!currentClass.isEnum() && currentClass.getSuperclass() != null) {
/*  59 */       annotations.addAll(Arrays.asList(currentClass.getDeclaredAnnotations()));
/*  60 */       List<String> genericTypeNames = new ArrayList<>();
/*  61 */       for (TypeVariable<? extends Class<? super T>> classTypeVariable : currentClass.getTypeParameters()) {
/*  62 */         genericTypeNames.add(classTypeVariable.getName());
/*     */       }
/*     */       
/*  65 */       PropertyReflectionUtils.PropertyMethods propertyMethods = PropertyReflectionUtils.getPropertyMethods(currentClass);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  70 */       for (Method method : propertyMethods.getSetterMethods()) {
/*  71 */         String propertyName = PropertyReflectionUtils.toPropertyName(method);
/*  72 */         propertyNames.add(propertyName);
/*  73 */         PropertyMetadata<?> propertyMetadata = getOrCreateMethodPropertyMetadata(propertyName, declaringClassName, propertyNameMap, 
/*  74 */             TypeData.newInstance(method), propertyTypeParameterMap, parentClassTypeData, genericTypeNames, 
/*  75 */             getGenericType(method));
/*     */         
/*  77 */         if (propertyMetadata.getSetter() == null) {
/*  78 */           propertyMetadata.setSetter(method);
/*  79 */           for (Annotation annotation : method.getDeclaredAnnotations()) {
/*  80 */             propertyMetadata.addWriteAnnotation(annotation);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  85 */       for (Method method : propertyMethods.getGetterMethods()) {
/*  86 */         String propertyName = PropertyReflectionUtils.toPropertyName(method);
/*  87 */         propertyNames.add(propertyName);
/*     */ 
/*     */         
/*  90 */         PropertyMetadata<?> propertyMetadata = propertyNameMap.get(propertyName);
/*  91 */         if (propertyMetadata != null && propertyMetadata.getGetter() != null) {
/*     */           continue;
/*     */         }
/*  94 */         propertyMetadata = getOrCreateMethodPropertyMetadata(propertyName, declaringClassName, propertyNameMap, 
/*  95 */             TypeData.newInstance(method), propertyTypeParameterMap, parentClassTypeData, genericTypeNames, 
/*  96 */             getGenericType(method));
/*  97 */         if (propertyMetadata.getGetter() == null) {
/*  98 */           propertyMetadata.setGetter(method);
/*  99 */           for (Annotation annotation : method.getDeclaredAnnotations()) {
/* 100 */             propertyMetadata.addReadAnnotation(annotation);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 105 */       for (Field field : currentClass.getDeclaredFields()) {
/* 106 */         propertyNames.add(field.getName());
/*     */         
/* 108 */         PropertyMetadata<?> propertyMetadata = getOrCreateFieldPropertyMetadata(field.getName(), declaringClassName, propertyNameMap, 
/* 109 */             TypeData.newInstance(field), propertyTypeParameterMap, parentClassTypeData, genericTypeNames, field
/* 110 */             .getGenericType());
/* 111 */         if (propertyMetadata != null && propertyMetadata.getField() == null) {
/* 112 */           propertyMetadata.field(field);
/* 113 */           for (Annotation annotation : field.getDeclaredAnnotations()) {
/* 114 */             propertyMetadata.addReadAnnotation(annotation);
/* 115 */             propertyMetadata.addWriteAnnotation(annotation);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 120 */       parentClassTypeData = TypeData.newInstance(currentClass.getGenericSuperclass(), currentClass);
/* 121 */       currentClass = currentClass.getSuperclass();
/*     */     } 
/*     */     
/* 124 */     if (currentClass.isInterface()) {
/* 125 */       annotations.addAll(Arrays.asList(currentClass.getDeclaredAnnotations()));
/*     */     }
/*     */     
/* 128 */     for (String propertyName : propertyNames) {
/* 129 */       PropertyMetadata<?> propertyMetadata = propertyNameMap.get(propertyName);
/* 130 */       if (propertyMetadata.isSerializable() || propertyMetadata.isDeserializable()) {
/* 131 */         classModelBuilder.addProperty(createPropertyModelBuilder(propertyMetadata));
/*     */       }
/*     */     } 
/*     */     
/* 135 */     Collections.reverse(annotations);
/* 136 */     classModelBuilder.annotations(annotations);
/* 137 */     classModelBuilder.propertyNameToTypeParameterMap(propertyTypeParameterMap);
/*     */     
/* 139 */     Constructor<T> noArgsConstructor = null;
/* 140 */     for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
/* 141 */       if ((constructor.getParameterTypes()).length == 0 && (
/* 142 */         Modifier.isPublic(constructor.getModifiers()) || Modifier.isProtected(constructor.getModifiers()))) {
/* 143 */         noArgsConstructor = (Constructor)constructor;
/* 144 */         noArgsConstructor.setAccessible(true);
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     classModelBuilder.instanceCreatorFactory(new InstanceCreatorFactoryImpl<>(new CreatorExecutable<>(clazz, noArgsConstructor)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T, S> PropertyMetadata<T> getOrCreateMethodPropertyMetadata(String propertyName, String declaringClassName, Map<String, PropertyMetadata<?>> propertyNameMap, TypeData<T> typeData, Map<String, TypeParameterMap> propertyTypeParameterMap, TypeData<S> parentClassTypeData, List<String> genericTypeNames, Type genericType) {
/* 159 */     PropertyMetadata<T> propertyMetadata = getOrCreatePropertyMetadata(propertyName, declaringClassName, propertyNameMap, typeData);
/* 160 */     if (!isAssignableClass(propertyMetadata.getTypeData().getType(), typeData.getType())) {
/* 161 */       propertyMetadata.setError(String.format("Property '%s' in %s, has differing data types: %s and %s.", new Object[] { propertyName, declaringClassName, propertyMetadata
/* 162 */               .getTypeData(), typeData }));
/*     */     }
/* 164 */     cachePropertyTypeData(propertyMetadata, propertyTypeParameterMap, parentClassTypeData, genericTypeNames, genericType);
/* 165 */     return propertyMetadata;
/*     */   }
/*     */   
/*     */   private static boolean isAssignableClass(Class<?> propertyTypeClass, Class<?> typeDataClass) {
/* 169 */     Assertions.notNull("propertyTypeClass", propertyTypeClass);
/* 170 */     Assertions.notNull("typeDataClass", typeDataClass);
/* 171 */     return (propertyTypeClass.isAssignableFrom(typeDataClass) || typeDataClass.isAssignableFrom(propertyTypeClass));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T, S> PropertyMetadata<T> getOrCreateFieldPropertyMetadata(String propertyName, String declaringClassName, Map<String, PropertyMetadata<?>> propertyNameMap, TypeData<T> typeData, Map<String, TypeParameterMap> propertyTypeParameterMap, TypeData<S> parentClassTypeData, List<String> genericTypeNames, Type genericType) {
/* 182 */     PropertyMetadata<T> propertyMetadata = getOrCreatePropertyMetadata(propertyName, declaringClassName, propertyNameMap, typeData);
/* 183 */     if (!propertyMetadata.getTypeData().getType().isAssignableFrom(typeData.getType())) {
/* 184 */       return null;
/*     */     }
/* 186 */     cachePropertyTypeData(propertyMetadata, propertyTypeParameterMap, parentClassTypeData, genericTypeNames, genericType);
/* 187 */     return propertyMetadata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> PropertyMetadata<T> getOrCreatePropertyMetadata(String propertyName, String declaringClassName, Map<String, PropertyMetadata<?>> propertyNameMap, TypeData<T> typeData) {
/* 195 */     PropertyMetadata<T> propertyMetadata = (PropertyMetadata<T>)propertyNameMap.get(propertyName);
/* 196 */     if (propertyMetadata == null) {
/* 197 */       propertyMetadata = new PropertyMetadata<>(propertyName, declaringClassName, typeData);
/* 198 */       propertyNameMap.put(propertyName, propertyMetadata);
/*     */     } 
/* 200 */     return propertyMetadata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T, S> void cachePropertyTypeData(PropertyMetadata<T> propertyMetadata, Map<String, TypeParameterMap> propertyTypeParameterMap, TypeData<S> parentClassTypeData, List<String> genericTypeNames, Type genericType) {
/* 208 */     TypeParameterMap typeParameterMap = getTypeParameterMap(genericTypeNames, genericType);
/* 209 */     propertyTypeParameterMap.put(propertyMetadata.getName(), typeParameterMap);
/* 210 */     propertyMetadata.typeParameterInfo(typeParameterMap, parentClassTypeData);
/*     */   }
/*     */   
/*     */   private static Type getGenericType(Method method) {
/* 214 */     return PropertyReflectionUtils.isGetter(method) ? method.getGenericReturnType() : method.getGenericParameterTypes()[0];
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
/*     */   static <T> PropertyModelBuilder<T> createPropertyModelBuilder(PropertyMetadata<T> propertyMetadata) {
/* 228 */     PropertyModelBuilder<T> propertyModelBuilder = PropertyModel.<T>builder().propertyName(propertyMetadata.getName()).readName(propertyMetadata.getName()).writeName(propertyMetadata.getName()).typeData(propertyMetadata.getTypeData()).readAnnotations(propertyMetadata.getReadAnnotations()).writeAnnotations(propertyMetadata.getWriteAnnotations()).propertySerialization(new PropertyModelSerializationImpl<>()).propertyAccessor(new PropertyAccessorImpl<>(propertyMetadata)).setError(propertyMetadata.getError());
/*     */     
/* 230 */     if (propertyMetadata.getTypeParameters() != null) {
/* 231 */       propertyModelBuilder.typeData(PojoSpecializationHelper.specializeTypeData(propertyModelBuilder.getTypeData(), propertyMetadata.getTypeParameters(), propertyMetadata
/* 232 */             .getTypeParameterMap()));
/*     */     }
/*     */     
/* 235 */     return propertyModelBuilder;
/*     */   }
/*     */   
/*     */   private static TypeParameterMap getTypeParameterMap(List<String> genericTypeNames, Type propertyType) {
/* 239 */     int classParamIndex = genericTypeNames.indexOf(propertyType.toString());
/* 240 */     TypeParameterMap.Builder builder = TypeParameterMap.builder();
/* 241 */     if (classParamIndex != -1) {
/* 242 */       builder.addIndex(classParamIndex);
/*     */     }
/* 244 */     else if (propertyType instanceof ParameterizedType) {
/* 245 */       ParameterizedType pt = (ParameterizedType)propertyType;
/* 246 */       for (int i = 0; i < (pt.getActualTypeArguments()).length; i++) {
/* 247 */         classParamIndex = genericTypeNames.indexOf(pt.getActualTypeArguments()[i].toString());
/* 248 */         if (classParamIndex != -1) {
/* 249 */           builder.addIndex(i, classParamIndex);
/*     */         } else {
/* 251 */           builder.addIndex(i, getTypeParameterMap(genericTypeNames, pt.getActualTypeArguments()[i]));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 256 */     return builder.build();
/*     */   }
/*     */   
/*     */   static <V> V stateNotNull(String property, V value) {
/* 260 */     if (value == null) {
/* 261 */       throw new IllegalStateException(String.format("%s cannot be null", new Object[] { property }));
/*     */     }
/* 263 */     return value;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\PojoBuilderHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */