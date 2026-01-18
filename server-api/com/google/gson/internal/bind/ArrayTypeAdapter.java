/*     */ package com.google.gson.internal.bind;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import com.google.gson.internal.GsonTypes;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
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
/*     */ public final class ArrayTypeAdapter<E>
/*     */   extends TypeAdapter<Object>
/*     */ {
/*  35 */   public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
/*     */     {
/*     */       public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken)
/*     */       {
/*  39 */         Type type = typeToken.getType();
/*  40 */         if (!(type instanceof java.lang.reflect.GenericArrayType) && (!(type instanceof Class) || 
/*  41 */           !((Class)type).isArray())) {
/*  42 */           return null;
/*     */         }
/*     */         
/*  45 */         Type componentType = GsonTypes.getArrayComponentType(type);
/*  46 */         TypeAdapter<?> componentTypeAdapter = gson.getAdapter(TypeToken.get(componentType));
/*     */ 
/*     */ 
/*     */         
/*  50 */         TypeAdapter<T> arrayAdapter = new ArrayTypeAdapter(gson, componentTypeAdapter, GsonTypes.getRawType(componentType));
/*  51 */         return arrayAdapter;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private final Class<E> componentType;
/*     */   private final TypeAdapter<E> componentTypeAdapter;
/*     */   
/*     */   public ArrayTypeAdapter(Gson context, TypeAdapter<E> componentTypeAdapter, Class<E> componentType) {
/*  60 */     this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper<>(context, componentTypeAdapter, componentType);
/*     */     
/*  62 */     this.componentType = componentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object read(JsonReader in) throws IOException {
/*  67 */     if (in.peek() == JsonToken.NULL) {
/*  68 */       in.nextNull();
/*  69 */       return null;
/*     */     } 
/*     */     
/*  72 */     ArrayList<E> list = new ArrayList<>();
/*  73 */     in.beginArray();
/*  74 */     while (in.hasNext()) {
/*  75 */       E instance = (E)this.componentTypeAdapter.read(in);
/*  76 */       list.add(instance);
/*     */     } 
/*  78 */     in.endArray();
/*     */     
/*  80 */     int size = list.size();
/*     */     
/*  82 */     if (this.componentType.isPrimitive()) {
/*  83 */       Object object = Array.newInstance(this.componentType, size);
/*  84 */       for (int i = 0; i < size; i++) {
/*  85 */         Array.set(object, i, list.get(i));
/*     */       }
/*  87 */       return object;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  92 */     E[] array = (E[])Array.newInstance(this.componentType, size);
/*  93 */     return list.toArray(array);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, Object array) throws IOException {
/*  99 */     if (array == null) {
/* 100 */       out.nullValue();
/*     */       
/*     */       return;
/*     */     } 
/* 104 */     out.beginArray();
/* 105 */     for (int i = 0, length = Array.getLength(array); i < length; i++) {
/*     */       
/* 107 */       E value = (E)Array.get(array, i);
/* 108 */       this.componentTypeAdapter.write(out, value);
/*     */     } 
/* 110 */     out.endArray();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\bind\ArrayTypeAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */