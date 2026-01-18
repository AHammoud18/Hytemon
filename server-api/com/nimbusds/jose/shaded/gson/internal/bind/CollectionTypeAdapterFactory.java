/*     */ package com.nimbusds.jose.shaded.gson.internal.bind;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.gson.Gson;
/*     */ import com.nimbusds.jose.shaded.gson.TypeAdapter;
/*     */ import com.nimbusds.jose.shaded.gson.TypeAdapterFactory;
/*     */ import com.nimbusds.jose.shaded.gson.internal.ConstructorConstructor;
/*     */ import com.nimbusds.jose.shaded.gson.internal.GsonTypes;
/*     */ import com.nimbusds.jose.shaded.gson.internal.ObjectConstructor;
/*     */ import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
/*     */ import com.nimbusds.jose.shaded.gson.stream.JsonReader;
/*     */ import com.nimbusds.jose.shaded.gson.stream.JsonToken;
/*     */ import com.nimbusds.jose.shaded.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
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
/*     */ public final class CollectionTypeAdapterFactory
/*     */   implements TypeAdapterFactory
/*     */ {
/*     */   private final ConstructorConstructor constructorConstructor;
/*     */   
/*     */   public CollectionTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
/*  38 */     this.constructorConstructor = constructorConstructor;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/*  43 */     Type type = typeToken.getType();
/*     */     
/*  45 */     Class<? super T> rawType = typeToken.getRawType();
/*  46 */     if (!Collection.class.isAssignableFrom(rawType)) {
/*  47 */       return null;
/*     */     }
/*     */     
/*  50 */     Type elementType = GsonTypes.getCollectionElementType(type, rawType);
/*  51 */     TypeAdapter<?> elementTypeAdapter = gson.getAdapter(TypeToken.get(elementType));
/*  52 */     TypeAdapter<?> wrappedTypeAdapter = new TypeAdapterRuntimeTypeWrapper(gson, elementTypeAdapter, elementType);
/*     */ 
/*     */ 
/*     */     
/*  56 */     boolean allowUnsafe = false;
/*  57 */     ObjectConstructor<T> constructor = this.constructorConstructor.get(typeToken, allowUnsafe);
/*     */ 
/*     */     
/*  60 */     TypeAdapter<T> result = new Adapter(wrappedTypeAdapter, (ObjectConstructor)constructor);
/*  61 */     return result;
/*     */   }
/*     */   
/*     */   private static final class Adapter<E>
/*     */     extends TypeAdapter<Collection<E>> {
/*     */     private final TypeAdapter<E> elementTypeAdapter;
/*     */     private final ObjectConstructor<? extends Collection<E>> constructor;
/*     */     
/*     */     public Adapter(TypeAdapter<E> elementTypeAdapter, ObjectConstructor<? extends Collection<E>> constructor) {
/*  70 */       this.elementTypeAdapter = elementTypeAdapter;
/*  71 */       this.constructor = constructor;
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<E> read(JsonReader in) throws IOException {
/*  76 */       if (in.peek() == JsonToken.NULL) {
/*  77 */         in.nextNull();
/*  78 */         return null;
/*     */       } 
/*     */       
/*  81 */       Collection<E> collection = (Collection<E>)this.constructor.construct();
/*  82 */       in.beginArray();
/*  83 */       while (in.hasNext()) {
/*  84 */         E instance = (E)this.elementTypeAdapter.read(in);
/*  85 */         collection.add(instance);
/*     */       } 
/*  87 */       in.endArray();
/*  88 */       return collection;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(JsonWriter out, Collection<E> collection) throws IOException {
/*  93 */       if (collection == null) {
/*  94 */         out.nullValue();
/*     */         
/*     */         return;
/*     */       } 
/*  98 */       out.beginArray();
/*  99 */       for (E element : collection) {
/* 100 */         this.elementTypeAdapter.write(out, element);
/*     */       }
/* 102 */       out.endArray();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\internal\bind\CollectionTypeAdapterFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */