/*     */ package com.google.gson.internal;
/*     */ 
/*     */ import com.google.gson.ExclusionStrategy;
/*     */ import com.google.gson.FieldAttributes;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.google.gson.annotations.Since;
/*     */ import com.google.gson.annotations.Until;
/*     */ import com.google.gson.internal.reflect.ReflectionHelper;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Excluder
/*     */   implements TypeAdapterFactory, Cloneable
/*     */ {
/*     */   private static final double IGNORE_VERSIONS = -1.0D;
/*  51 */   public static final Excluder DEFAULT = new Excluder();
/*     */   
/*  53 */   private double version = -1.0D;
/*  54 */   private int modifiers = 136;
/*     */   private boolean serializeInnerClasses = true;
/*     */   private boolean requireExpose;
/*  57 */   private List<ExclusionStrategy> serializationStrategies = Collections.emptyList();
/*  58 */   private List<ExclusionStrategy> deserializationStrategies = Collections.emptyList();
/*     */ 
/*     */   
/*     */   protected Excluder clone() {
/*     */     try {
/*  63 */       return (Excluder)super.clone();
/*  64 */     } catch (CloneNotSupportedException e) {
/*  65 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Excluder withVersion(double ignoreVersionsAfter) {
/*  70 */     Excluder result = clone();
/*  71 */     result.version = ignoreVersionsAfter;
/*  72 */     return result;
/*     */   }
/*     */   
/*     */   public Excluder withModifiers(int... modifiers) {
/*  76 */     Excluder result = clone();
/*  77 */     result.modifiers = 0;
/*  78 */     for (int modifier : modifiers) {
/*  79 */       result.modifiers |= modifier;
/*     */     }
/*  81 */     return result;
/*     */   }
/*     */   
/*     */   public Excluder disableInnerClassSerialization() {
/*  85 */     Excluder result = clone();
/*  86 */     result.serializeInnerClasses = false;
/*  87 */     return result;
/*     */   }
/*     */   
/*     */   public Excluder excludeFieldsWithoutExposeAnnotation() {
/*  91 */     Excluder result = clone();
/*  92 */     result.requireExpose = true;
/*  93 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Excluder withExclusionStrategy(ExclusionStrategy exclusionStrategy, boolean serialization, boolean deserialization) {
/*  98 */     Excluder result = clone();
/*  99 */     if (serialization) {
/* 100 */       result.serializationStrategies = new ArrayList<>(this.serializationStrategies);
/* 101 */       result.serializationStrategies.add(exclusionStrategy);
/*     */     } 
/* 103 */     if (deserialization) {
/* 104 */       result.deserializationStrategies = new ArrayList<>(this.deserializationStrategies);
/* 105 */       result.deserializationStrategies.add(exclusionStrategy);
/*     */     } 
/* 107 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
/* 112 */     Class<?> rawType = type.getRawType();
/*     */     
/* 114 */     final boolean skipSerialize = excludeClass(rawType, true);
/* 115 */     final boolean skipDeserialize = excludeClass(rawType, false);
/*     */     
/* 117 */     if (!skipSerialize && !skipDeserialize) {
/* 118 */       return null;
/*     */     }
/*     */     
/* 121 */     return new TypeAdapter<T>()
/*     */       {
/*     */         private volatile TypeAdapter<T> delegate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public T read(JsonReader in) throws IOException {
/* 130 */           if (skipDeserialize) {
/* 131 */             in.skipValue();
/* 132 */             return null;
/*     */           } 
/* 134 */           return (T)delegate().read(in);
/*     */         }
/*     */ 
/*     */         
/*     */         public void write(JsonWriter out, T value) throws IOException {
/* 139 */           if (skipSerialize) {
/* 140 */             out.nullValue();
/*     */             return;
/*     */           } 
/* 143 */           delegate().write(out, value);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         private TypeAdapter<T> delegate() {
/* 149 */           TypeAdapter<T> d = this.delegate;
/* 150 */           if (d == null) {
/* 151 */             d = this.delegate = gson.getDelegateAdapter(Excluder.this, type);
/*     */           }
/* 153 */           return d;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public boolean excludeField(Field field, boolean serialize) {
/* 159 */     if ((this.modifiers & field.getModifiers()) != 0) {
/* 160 */       return true;
/*     */     }
/*     */     
/* 163 */     if (this.version != -1.0D && 
/* 164 */       !isValidVersion(field.<Since>getAnnotation(Since.class), field.<Until>getAnnotation(Until.class))) {
/* 165 */       return true;
/*     */     }
/*     */     
/* 168 */     if (field.isSynthetic()) {
/* 169 */       return true;
/*     */     }
/*     */     
/* 172 */     if (this.requireExpose) {
/* 173 */       Expose annotation = field.<Expose>getAnnotation(Expose.class);
/* 174 */       if (annotation == null || (serialize ? !annotation.serialize() : !annotation.deserialize())) {
/* 175 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 179 */     if (excludeClass(field.getType(), serialize)) {
/* 180 */       return true;
/*     */     }
/*     */     
/* 183 */     List<ExclusionStrategy> list = serialize ? this.serializationStrategies : this.deserializationStrategies;
/* 184 */     if (!list.isEmpty()) {
/* 185 */       FieldAttributes fieldAttributes = new FieldAttributes(field);
/* 186 */       for (ExclusionStrategy exclusionStrategy : list) {
/* 187 */         if (exclusionStrategy.shouldSkipField(fieldAttributes)) {
/* 188 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean excludeClass(Class<?> clazz, boolean serialize) {
/* 198 */     if (this.version != -1.0D && 
/* 199 */       !isValidVersion(clazz.<Since>getAnnotation(Since.class), clazz.<Until>getAnnotation(Until.class))) {
/* 200 */       return true;
/*     */     }
/*     */     
/* 203 */     if (!this.serializeInnerClasses && isInnerClass(clazz)) {
/* 204 */       return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     if (!serialize && 
/* 220 */       !Enum.class.isAssignableFrom(clazz) && 
/* 221 */       ReflectionHelper.isAnonymousOrNonStaticLocal(clazz)) {
/* 222 */       return true;
/*     */     }
/*     */     
/* 225 */     List<ExclusionStrategy> list = serialize ? this.serializationStrategies : this.deserializationStrategies;
/* 226 */     for (ExclusionStrategy exclusionStrategy : list) {
/* 227 */       if (exclusionStrategy.shouldSkipClass(clazz)) {
/* 228 */         return true;
/*     */       }
/*     */     } 
/* 231 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean isInnerClass(Class<?> clazz) {
/* 235 */     return (clazz.isMemberClass() && !ReflectionHelper.isStatic(clazz));
/*     */   }
/*     */   
/*     */   private boolean isValidVersion(Since since, Until until) {
/* 239 */     return (isValidSince(since) && isValidUntil(until));
/*     */   }
/*     */   
/*     */   private boolean isValidSince(Since annotation) {
/* 243 */     if (annotation != null) {
/* 244 */       double annotationVersion = annotation.value();
/* 245 */       return (this.version >= annotationVersion);
/*     */     } 
/* 247 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isValidUntil(Until annotation) {
/* 251 */     if (annotation != null) {
/* 252 */       double annotationVersion = annotation.value();
/* 253 */       return (this.version < annotationVersion);
/*     */     } 
/* 255 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\Excluder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */