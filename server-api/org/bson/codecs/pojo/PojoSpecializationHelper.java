/*    */ package org.bson.codecs.pojo;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class PojoSpecializationHelper
/*    */ {
/*    */   static <V> TypeData<V> specializeTypeData(TypeData<V> typeData, List<TypeData<?>> typeParameters, TypeParameterMap typeParameterMap) {
/* 28 */     if (!typeParameterMap.hasTypeParameters() || typeParameters.isEmpty()) {
/* 29 */       return typeData;
/*    */     }
/*    */     
/* 32 */     Map<Integer, Either<Integer, TypeParameterMap>> propertyToClassParamIndexMap = typeParameterMap.getPropertyToClassParamIndexMap();
/* 33 */     Either<Integer, TypeParameterMap> classTypeParamRepresentsWholeField = propertyToClassParamIndexMap.get(Integer.valueOf(-1));
/* 34 */     if (classTypeParamRepresentsWholeField != null) {
/* 35 */       Integer index = classTypeParamRepresentsWholeField.<Integer>map(i -> i, e -> {
/*    */             throw new IllegalStateException("Invalid state, the whole class cannot be represented by a subtype.");
/*    */           });
/* 38 */       return (TypeData<V>)typeParameters.get(index.intValue());
/*    */     } 
/* 40 */     return getTypeData(typeData, typeParameters, propertyToClassParamIndexMap);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static <V> TypeData<V> getTypeData(TypeData<V> typeData, List<TypeData<?>> specializedTypeParameters, Map<Integer, Either<Integer, TypeParameterMap>> propertyToClassParamIndexMap) {
/* 46 */     List<TypeData<?>> subTypeParameters = new ArrayList<>(typeData.getTypeParameters());
/* 47 */     for (int i = 0; i < typeData.getTypeParameters().size(); i++) {
/* 48 */       subTypeParameters.set(i, getTypeData(subTypeParameters.get(i), specializedTypeParameters, propertyToClassParamIndexMap, i));
/*    */     }
/* 50 */     return TypeData.<V>builder(typeData.getType()).addTypeParameters(subTypeParameters).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static TypeData<?> getTypeData(TypeData<?> typeData, List<TypeData<?>> specializedTypeParameters, Map<Integer, Either<Integer, TypeParameterMap>> propertyToClassParamIndexMap, int index) {
/* 56 */     if (!propertyToClassParamIndexMap.containsKey(Integer.valueOf(index))) {
/* 57 */       return typeData;
/*    */     }
/* 59 */     return (TypeData)((Either)propertyToClassParamIndexMap.get(Integer.valueOf(index))).map(l -> {
/*    */           if (typeData.getTypeParameters().isEmpty())
/*    */             return specializedTypeParameters.get(l.intValue()); 
/*    */           TypeData.Builder<?> builder = TypeData.builder(typeData.getType());
/*    */           List<TypeData<?>> typeParameters = new ArrayList<>(typeData.getTypeParameters());
/*    */           typeParameters.set(index, specializedTypeParameters.get(l.intValue()));
/*    */           builder.addTypeParameters(typeParameters);
/*    */           return builder.build();
/*    */         }r -> getTypeData(typeData, specializedTypeParameters, r.getPropertyToClassParamIndexMap()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\PojoSpecializationHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */