/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bson.codecs.configuration.CodecConfigurationException;
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
/*     */ final class PropertyMetadata<T>
/*     */ {
/*     */   private final String name;
/*     */   private final String declaringClassName;
/*     */   private final TypeData<T> typeData;
/*  39 */   private final Map<Class<? extends Annotation>, Annotation> readAnnotations = new HashMap<>();
/*  40 */   private final Map<Class<? extends Annotation>, Annotation> writeAnnotations = new HashMap<>();
/*     */   
/*     */   private TypeParameterMap typeParameterMap;
/*     */   private List<TypeData<?>> typeParameters;
/*     */   private String error;
/*     */   private Field field;
/*     */   private Method getter;
/*     */   private Method setter;
/*     */   
/*     */   PropertyMetadata(String name, String declaringClassName, TypeData<T> typeData) {
/*  50 */     this.name = name;
/*  51 */     this.declaringClassName = declaringClassName;
/*  52 */     this.typeData = typeData;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  56 */     return this.name;
/*     */   }
/*     */   
/*     */   public List<Annotation> getReadAnnotations() {
/*  60 */     return new ArrayList<>(this.readAnnotations.values());
/*     */   }
/*     */   
/*     */   public PropertyMetadata<T> addReadAnnotation(Annotation annotation) {
/*  64 */     if (this.readAnnotations.containsKey(annotation.annotationType())) {
/*  65 */       if (annotation.equals(this.readAnnotations.get(annotation.annotationType()))) {
/*  66 */         return this;
/*     */       }
/*  68 */       throw new CodecConfigurationException(String.format("Read annotation %s for '%s' already exists in %s", new Object[] { annotation.annotationType(), this.name, this.declaringClassName }));
/*     */     } 
/*     */     
/*  71 */     this.readAnnotations.put(annotation.annotationType(), annotation);
/*  72 */     return this;
/*     */   }
/*     */   
/*     */   public List<Annotation> getWriteAnnotations() {
/*  76 */     return new ArrayList<>(this.writeAnnotations.values());
/*     */   }
/*     */   
/*     */   public PropertyMetadata<T> addWriteAnnotation(Annotation annotation) {
/*  80 */     if (this.writeAnnotations.containsKey(annotation.annotationType())) {
/*  81 */       if (annotation.equals(this.writeAnnotations.get(annotation.annotationType()))) {
/*  82 */         return this;
/*     */       }
/*  84 */       throw new CodecConfigurationException(String.format("Write annotation %s for '%s' already exists in %s", new Object[] { annotation.annotationType(), this.name, this.declaringClassName }));
/*     */     } 
/*     */     
/*  87 */     this.writeAnnotations.put(annotation.annotationType(), annotation);
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   public Field getField() {
/*  92 */     return this.field;
/*     */   }
/*     */   
/*     */   public PropertyMetadata<T> field(Field field) {
/*  96 */     this.field = field;
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   public Method getGetter() {
/* 101 */     return this.getter;
/*     */   }
/*     */   
/*     */   public void setGetter(Method getter) {
/* 105 */     this.getter = getter;
/*     */   }
/*     */   
/*     */   public Method getSetter() {
/* 109 */     return this.setter;
/*     */   }
/*     */   
/*     */   public void setSetter(Method setter) {
/* 113 */     this.setter = setter;
/*     */   }
/*     */   
/*     */   public String getDeclaringClassName() {
/* 117 */     return this.declaringClassName;
/*     */   }
/*     */   
/*     */   public TypeData<T> getTypeData() {
/* 121 */     return this.typeData;
/*     */   }
/*     */   
/*     */   public TypeParameterMap getTypeParameterMap() {
/* 125 */     return this.typeParameterMap;
/*     */   }
/*     */   
/*     */   public List<TypeData<?>> getTypeParameters() {
/* 129 */     return this.typeParameters;
/*     */   }
/*     */   
/*     */   public <S> PropertyMetadata<T> typeParameterInfo(TypeParameterMap typeParameterMap, TypeData<S> parentTypeData) {
/* 133 */     if (typeParameterMap != null && parentTypeData != null) {
/* 134 */       this.typeParameterMap = typeParameterMap;
/* 135 */       this.typeParameters = parentTypeData.getTypeParameters();
/*     */     } 
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   String getError() {
/* 141 */     return this.error;
/*     */   }
/*     */   
/*     */   void setError(String error) {
/* 145 */     this.error = error;
/*     */   }
/*     */   
/*     */   public boolean isSerializable() {
/* 149 */     if (this.getter != null) {
/* 150 */       return (this.field == null || notStaticOrTransient(this.field.getModifiers()));
/*     */     }
/* 152 */     return (this.field != null && isPublicAndNotStaticOrTransient(this.field.getModifiers()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDeserializable() {
/* 157 */     if (this.setter != null) {
/* 158 */       return (this.field == null || (!Modifier.isFinal(this.field.getModifiers()) && notStaticOrTransient(this.field.getModifiers())));
/*     */     }
/* 160 */     return (this.field != null && !Modifier.isFinal(this.field.getModifiers()) && isPublicAndNotStaticOrTransient(this.field.getModifiers()));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean notStaticOrTransient(int modifiers) {
/* 165 */     return (!Modifier.isTransient(modifiers) && !Modifier.isStatic(modifiers));
/*     */   }
/*     */   
/*     */   private boolean isPublicAndNotStaticOrTransient(int modifiers) {
/* 169 */     return (Modifier.isPublic(modifiers) && notStaticOrTransient(modifiers));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 174 */     return "PropertyMetadata{name='" + this.name + '\'' + ", declaringClassName='" + this.declaringClassName + '\'' + ", typeData=" + this.typeData + ", readAnnotations=" + this.readAnnotations + ", writeAnnotations=" + this.writeAnnotations + ", typeParameterMap=" + this.typeParameterMap + ", typeParameters=" + this.typeParameters + ", error='" + this.error + '\'' + ", field=" + this.field + ", getter=" + this.getter + ", setter=" + this.setter + '}';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\PropertyMetadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */