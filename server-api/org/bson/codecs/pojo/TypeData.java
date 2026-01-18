/*     */ package org.bson.codecs.pojo;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ final class TypeData<T>
/*     */   implements TypeWithTypeParameters<T>
/*     */ {
/*     */   private final Class<T> type;
/*     */   private final List<TypeData<?>> typeParameters;
/*     */   private static final Map<Class<?>, Class<?>> PRIMITIVE_CLASS_MAP;
/*     */   
/*     */   public static <T> Builder<T> builder(Class<T> type) {
/*  48 */     return new Builder<>((Class)Assertions.notNull("type", type));
/*     */   }
/*     */   
/*     */   public static TypeData<?> newInstance(Method method) {
/*  52 */     if (PropertyReflectionUtils.isGetter(method)) {
/*  53 */       return newInstance(method.getGenericReturnType(), method.getReturnType());
/*     */     }
/*  55 */     return newInstance(method.getGenericParameterTypes()[0], method.getParameterTypes()[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static TypeData<?> newInstance(Field field) {
/*  60 */     return newInstance(field.getGenericType(), field.getType());
/*     */   }
/*     */   
/*     */   public static <T> TypeData<T> newInstance(Type genericType, Class<T> clazz) {
/*  64 */     Builder<T> builder = builder(clazz);
/*  65 */     if (genericType instanceof ParameterizedType) {
/*  66 */       ParameterizedType pType = (ParameterizedType)genericType;
/*  67 */       for (Type argType : pType.getActualTypeArguments()) {
/*  68 */         getNestedTypeData(builder, argType);
/*     */       }
/*     */     } 
/*  71 */     return builder.build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> void getNestedTypeData(Builder<T> builder, Type type) {
/*  76 */     if (type instanceof ParameterizedType) {
/*  77 */       ParameterizedType pType = (ParameterizedType)type;
/*  78 */       Builder<?> paramBuilder = builder((Class)pType.getRawType());
/*  79 */       for (Type argType : pType.getActualTypeArguments()) {
/*  80 */         getNestedTypeData(paramBuilder, argType);
/*     */       }
/*  82 */       builder.addTypeParameter(paramBuilder.build());
/*  83 */     } else if (type instanceof WildcardType) {
/*  84 */       builder.addTypeParameter(builder((Class)((WildcardType)type).getUpperBounds()[0]).build());
/*  85 */     } else if (type instanceof java.lang.reflect.TypeVariable) {
/*  86 */       builder.addTypeParameter(builder(Object.class).build());
/*  87 */     } else if (type instanceof Class) {
/*  88 */       builder.addTypeParameter(builder((Class)type).build());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<T> getType() {
/*  97 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TypeData<?>> getTypeParameters() {
/* 105 */     return this.typeParameters;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder<T>
/*     */   {
/*     */     private final Class<T> type;
/*     */ 
/*     */     
/* 115 */     private final List<TypeData<?>> typeParameters = new ArrayList<>();
/*     */     
/*     */     private Builder(Class<T> type) {
/* 118 */       this.type = type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <S> Builder<T> addTypeParameter(TypeData<S> typeParameter) {
/* 129 */       this.typeParameters.add((TypeData)Assertions.notNull("typeParameter", typeParameter));
/* 130 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<T> addTypeParameters(List<TypeData<?>> typeParameters) {
/* 140 */       Assertions.notNull("typeParameters", typeParameters);
/* 141 */       for (TypeData<?> typeParameter : typeParameters) {
/* 142 */         addTypeParameter(typeParameter);
/*     */       }
/* 144 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TypeData<T> build() {
/* 151 */       return new TypeData<>(this.type, Collections.unmodifiableList(this.typeParameters));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 158 */     String typeParams = this.typeParameters.isEmpty() ? "" : (", typeParameters=[" + nestedTypeParameters(this.typeParameters) + "]");
/* 159 */     return "TypeData{type=" + this.type
/* 160 */       .getSimpleName() + typeParams + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String nestedTypeParameters(List<TypeData<?>> typeParameters) {
/* 166 */     StringBuilder builder = new StringBuilder();
/* 167 */     int count = 0;
/* 168 */     int last = typeParameters.size();
/* 169 */     for (TypeData<?> typeParameter : typeParameters) {
/* 170 */       count++;
/* 171 */       builder.append(typeParameter.getType().getSimpleName());
/* 172 */       if (!typeParameter.getTypeParameters().isEmpty()) {
/* 173 */         builder.append(String.format("<%s>", new Object[] { nestedTypeParameters(typeParameter.getTypeParameters()) }));
/*     */       }
/* 175 */       if (count < last) {
/* 176 */         builder.append(", ");
/*     */       }
/*     */     } 
/* 179 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 184 */     if (this == o) {
/* 185 */       return true;
/*     */     }
/* 187 */     if (!(o instanceof TypeData)) {
/* 188 */       return false;
/*     */     }
/*     */     
/* 191 */     TypeData<?> that = (TypeData)o;
/*     */     
/* 193 */     if (!getType().equals(that.getType())) {
/* 194 */       return false;
/*     */     }
/* 196 */     if (!getTypeParameters().equals(that.getTypeParameters())) {
/* 197 */       return false;
/*     */     }
/*     */     
/* 200 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 205 */     int result = getType().hashCode();
/* 206 */     result = 31 * result + getTypeParameters().hashCode();
/* 207 */     return result;
/*     */   }
/*     */   
/*     */   private TypeData(Class<T> type, List<TypeData<?>> typeParameters) {
/* 211 */     this.type = boxType(type);
/* 212 */     this.typeParameters = typeParameters;
/*     */   }
/*     */   
/*     */   boolean isAssignableFrom(Class<?> cls) {
/* 216 */     return this.type.isAssignableFrom(boxType(cls));
/*     */   }
/*     */ 
/*     */   
/*     */   private <S> Class<S> boxType(Class<S> clazz) {
/* 221 */     if (clazz.isPrimitive()) {
/* 222 */       return (Class<S>)PRIMITIVE_CLASS_MAP.get(clazz);
/*     */     }
/* 224 */     return clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 230 */     Map<Class<?>, Class<?>> map = new HashMap<>();
/* 231 */     map.put(boolean.class, Boolean.class);
/* 232 */     map.put(byte.class, Byte.class);
/* 233 */     map.put(char.class, Character.class);
/* 234 */     map.put(double.class, Double.class);
/* 235 */     map.put(float.class, Float.class);
/* 236 */     map.put(int.class, Integer.class);
/* 237 */     map.put(long.class, Long.class);
/* 238 */     map.put(short.class, Short.class);
/* 239 */     PRIMITIVE_CLASS_MAP = map;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\pojo\TypeData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */