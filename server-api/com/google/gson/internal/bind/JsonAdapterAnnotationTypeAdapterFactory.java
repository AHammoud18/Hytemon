/*     */ package com.google.gson.internal.bind;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import com.google.gson.annotations.JsonAdapter;
/*     */ import com.google.gson.internal.ConstructorConstructor;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
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
/*     */ public final class JsonAdapterAnnotationTypeAdapterFactory
/*     */   implements TypeAdapterFactory
/*     */ {
/*     */   private static class DummyTypeAdapterFactory
/*     */     implements TypeAdapterFactory
/*     */   {
/*     */     private DummyTypeAdapterFactory() {}
/*     */     
/*     */     public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
/*  41 */       throw new AssertionError("Factory should not be used");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  46 */   private static final TypeAdapterFactory TREE_TYPE_CLASS_DUMMY_FACTORY = new DummyTypeAdapterFactory();
/*     */ 
/*     */ 
/*     */   
/*  50 */   private static final TypeAdapterFactory TREE_TYPE_FIELD_DUMMY_FACTORY = new DummyTypeAdapterFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ConstructorConstructor constructorConstructor;
/*     */ 
/*     */ 
/*     */   
/*     */   private final ConcurrentMap<Class<?>, TypeAdapterFactory> adapterFactoryMap;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JsonAdapterAnnotationTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
/*  65 */     this.constructorConstructor = constructorConstructor;
/*  66 */     this.adapterFactoryMap = new ConcurrentHashMap<>();
/*     */   }
/*     */ 
/*     */   
/*     */   private static JsonAdapter getAnnotation(Class<?> rawType) {
/*  71 */     return rawType.<JsonAdapter>getAnnotation(JsonAdapter.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> targetType) {
/*  78 */     Class<? super T> rawType = targetType.getRawType();
/*  79 */     JsonAdapter annotation = getAnnotation(rawType);
/*  80 */     if (annotation == null) {
/*  81 */       return null;
/*     */     }
/*  83 */     return (TypeAdapter)
/*  84 */       getTypeAdapter(this.constructorConstructor, gson, targetType, annotation, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object createAdapter(ConstructorConstructor constructorConstructor, Class<?> adapterClass) {
/*  95 */     boolean allowUnsafe = true;
/*  96 */     return constructorConstructor.get(TypeToken.get(adapterClass), allowUnsafe).construct();
/*     */   }
/*     */ 
/*     */   
/*     */   private TypeAdapterFactory putFactoryAndGetCurrent(Class<?> rawType, TypeAdapterFactory factory) {
/* 101 */     TypeAdapterFactory existingFactory = this.adapterFactoryMap.putIfAbsent(rawType, factory);
/* 102 */     return (existingFactory != null) ? existingFactory : factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TypeAdapter<?> getTypeAdapter(ConstructorConstructor constructorConstructor, Gson gson, TypeToken<?> type, JsonAdapter annotation, boolean isClassAnnotation) {
/*     */     TypeAdapter<?> typeAdapter;
/* 111 */     Object instance = createAdapter(constructorConstructor, annotation.value());
/*     */ 
/*     */     
/* 114 */     boolean nullSafe = annotation.nullSafe();
/* 115 */     if (instance instanceof TypeAdapter) {
/* 116 */       typeAdapter = (TypeAdapter)instance;
/* 117 */     } else if (instance instanceof TypeAdapterFactory) {
/* 118 */       TypeAdapterFactory factory = (TypeAdapterFactory)instance;
/*     */       
/* 120 */       if (isClassAnnotation) {
/* 121 */         factory = putFactoryAndGetCurrent(type.getRawType(), factory);
/*     */       }
/*     */       
/* 124 */       typeAdapter = factory.create(gson, type);
/* 125 */     } else if (instance instanceof JsonSerializer || instance instanceof JsonDeserializer) {
/*     */       TypeAdapterFactory skipPast;
/* 127 */       JsonSerializer<?> serializer = (instance instanceof JsonSerializer) ? (JsonSerializer)instance : null;
/*     */       
/* 129 */       JsonDeserializer<?> deserializer = (instance instanceof JsonDeserializer) ? (JsonDeserializer)instance : null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 135 */       if (isClassAnnotation) {
/* 136 */         skipPast = TREE_TYPE_CLASS_DUMMY_FACTORY;
/*     */       } else {
/* 138 */         skipPast = TREE_TYPE_FIELD_DUMMY_FACTORY;
/*     */       } 
/*     */       
/* 141 */       TypeAdapter<?> tempAdapter = new TreeTypeAdapter(serializer, deserializer, gson, type, skipPast, nullSafe);
/*     */       
/* 143 */       typeAdapter = tempAdapter;
/*     */ 
/*     */       
/* 146 */       nullSafe = false;
/*     */     } else {
/* 148 */       throw new IllegalArgumentException("Invalid attempt to bind an instance of " + instance
/*     */           
/* 150 */           .getClass().getName() + " as a @JsonAdapter for " + type
/*     */           
/* 152 */           .toString() + ". @JsonAdapter value must be a TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer.");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 157 */     if (typeAdapter != null && nullSafe) {
/* 158 */       typeAdapter = typeAdapter.nullSafe();
/*     */     }
/*     */     
/* 161 */     return typeAdapter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClassJsonAdapterFactory(TypeToken<?> type, TypeAdapterFactory factory) {
/* 169 */     Objects.requireNonNull(type);
/* 170 */     Objects.requireNonNull(factory);
/*     */     
/* 172 */     if (factory == TREE_TYPE_CLASS_DUMMY_FACTORY) {
/* 173 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 177 */     Class<?> rawType = type.getRawType();
/*     */     
/* 179 */     TypeAdapterFactory existingFactory = this.adapterFactoryMap.get(rawType);
/* 180 */     if (existingFactory != null)
/*     */     {
/* 182 */       return (existingFactory == factory);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     JsonAdapter annotation = getAnnotation(rawType);
/* 190 */     if (annotation == null) {
/* 191 */       return false;
/*     */     }
/*     */     
/* 194 */     Class<?> adapterClass = annotation.value();
/* 195 */     if (!TypeAdapterFactory.class.isAssignableFrom(adapterClass)) {
/* 196 */       return false;
/*     */     }
/*     */     
/* 199 */     Object adapter = createAdapter(this.constructorConstructor, adapterClass);
/* 200 */     TypeAdapterFactory newFactory = (TypeAdapterFactory)adapter;
/*     */     
/* 202 */     return (putFactoryAndGetCurrent(rawType, newFactory) == factory);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\bind\JsonAdapterAnnotationTypeAdapterFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */