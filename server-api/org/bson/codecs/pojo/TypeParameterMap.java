/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ final class TypeParameterMap
/*     */ {
/*     */   private final Map<Integer, Either<Integer, TypeParameterMap>> propertyToClassParamIndexMap;
/*     */   
/*     */   static Builder builder() {
/*  37 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Map<Integer, Either<Integer, TypeParameterMap>> getPropertyToClassParamIndexMap() {
/*  48 */     return this.propertyToClassParamIndexMap;
/*     */   }
/*     */   
/*     */   boolean hasTypeParameters() {
/*  52 */     return !this.propertyToClassParamIndexMap.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Builder
/*     */   {
/*  59 */     private final Map<Integer, Either<Integer, TypeParameterMap>> propertyToClassParamIndexMap = new HashMap<>();
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
/*     */     Builder addIndex(int classTypeParameterIndex) {
/*  71 */       this.propertyToClassParamIndexMap.put(Integer.valueOf(-1), Either.left(Integer.valueOf(classTypeParameterIndex)));
/*  72 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Builder addIndex(int propertyTypeParameterIndex, int classTypeParameterIndex) {
/*  83 */       this.propertyToClassParamIndexMap.put(Integer.valueOf(propertyTypeParameterIndex), Either.left(Integer.valueOf(classTypeParameterIndex)));
/*  84 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Builder addIndex(int propertyTypeParameterIndex, TypeParameterMap typeParameterMap) {
/*  96 */       this.propertyToClassParamIndexMap.put(Integer.valueOf(propertyTypeParameterIndex), Either.right(typeParameterMap));
/*  97 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     TypeParameterMap build() {
/* 104 */       if (this.propertyToClassParamIndexMap.size() > 1 && this.propertyToClassParamIndexMap.containsKey(Integer.valueOf(-1))) {
/* 105 */         throw new IllegalStateException("You cannot have a generic field that also has type parameters.");
/*     */       }
/* 107 */       return new TypeParameterMap(this.propertyToClassParamIndexMap);
/*     */     }
/*     */     
/*     */     private Builder() {} }
/*     */   
/*     */   public String toString() {
/* 113 */     return "TypeParameterMap{fieldToClassParamIndexMap=" + this.propertyToClassParamIndexMap + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 120 */     if (this == o) {
/* 121 */       return true;
/*     */     }
/* 123 */     if (o == null || getClass() != o.getClass()) {
/* 124 */       return false;
/*     */     }
/*     */     
/* 127 */     TypeParameterMap that = (TypeParameterMap)o;
/*     */     
/* 129 */     if (!getPropertyToClassParamIndexMap().equals(that.getPropertyToClassParamIndexMap())) {
/* 130 */       return false;
/*     */     }
/*     */     
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 138 */     return getPropertyToClassParamIndexMap().hashCode();
/*     */   }
/*     */   
/*     */   private TypeParameterMap(Map<Integer, Either<Integer, TypeParameterMap>> propertyToClassParamIndexMap) {
/* 142 */     this.propertyToClassParamIndexMap = Collections.unmodifiableMap(propertyToClassParamIndexMap);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\TypeParameterMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */