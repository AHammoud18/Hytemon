/*     */ package com.google.gson.internal.bind;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
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
/*     */ final class TypeAdapterRuntimeTypeWrapper<T>
/*     */   extends TypeAdapter<T>
/*     */ {
/*     */   private final Gson context;
/*     */   private final TypeAdapter<T> delegate;
/*     */   private final Type type;
/*     */   
/*     */   TypeAdapterRuntimeTypeWrapper(Gson context, TypeAdapter<T> delegate, Type type) {
/*  33 */     this.context = context;
/*  34 */     this.delegate = delegate;
/*  35 */     this.type = type;
/*     */   }
/*     */ 
/*     */   
/*     */   public T read(JsonReader in) throws IOException {
/*  40 */     return (T)this.delegate.read(in);
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
/*     */   public void write(JsonWriter out, T value) throws IOException {
/*  52 */     TypeAdapter<T> chosen = this.delegate;
/*  53 */     Type runtimeType = getRuntimeTypeIfMoreSpecific(this.type, value);
/*  54 */     if (runtimeType != this.type) {
/*     */ 
/*     */       
/*  57 */       TypeAdapter<T> runtimeTypeAdapter = this.context.getAdapter(TypeToken.get(runtimeType));
/*     */ 
/*     */ 
/*     */       
/*  61 */       if (!(runtimeTypeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter)) {
/*     */         
/*  63 */         chosen = runtimeTypeAdapter;
/*  64 */       } else if (!isReflective(this.delegate)) {
/*     */ 
/*     */         
/*  67 */         chosen = this.delegate;
/*     */       } else {
/*     */         
/*  70 */         chosen = runtimeTypeAdapter;
/*     */       } 
/*     */     } 
/*  73 */     chosen.write(out, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isReflective(TypeAdapter<?> typeAdapter) {
/*  83 */     while (typeAdapter instanceof SerializationDelegatingTypeAdapter) {
/*     */       
/*  85 */       TypeAdapter<?> delegate = ((SerializationDelegatingTypeAdapter)typeAdapter).getSerializationDelegate();
/*     */       
/*  87 */       if (delegate == typeAdapter) {
/*     */         break;
/*     */       }
/*  90 */       typeAdapter = delegate;
/*     */     } 
/*     */     
/*  93 */     return typeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Type getRuntimeTypeIfMoreSpecific(Type<?> type, Object value) {
/*  98 */     if (value != null && (type instanceof Class || type instanceof java.lang.reflect.TypeVariable)) {
/*  99 */       type = value.getClass();
/*     */     }
/* 101 */     return type;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\bind\TypeAdapterRuntimeTypeWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */