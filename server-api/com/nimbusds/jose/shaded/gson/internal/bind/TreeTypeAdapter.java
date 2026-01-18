/*     */ package com.nimbusds.jose.shaded.gson.internal.bind;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.gson.Gson;
/*     */ import com.nimbusds.jose.shaded.gson.JsonDeserializationContext;
/*     */ import com.nimbusds.jose.shaded.gson.JsonDeserializer;
/*     */ import com.nimbusds.jose.shaded.gson.JsonElement;
/*     */ import com.nimbusds.jose.shaded.gson.JsonParseException;
/*     */ import com.nimbusds.jose.shaded.gson.JsonSerializationContext;
/*     */ import com.nimbusds.jose.shaded.gson.JsonSerializer;
/*     */ import com.nimbusds.jose.shaded.gson.TypeAdapter;
/*     */ import com.nimbusds.jose.shaded.gson.TypeAdapterFactory;
/*     */ import com.nimbusds.jose.shaded.gson.internal.GsonPreconditions;
/*     */ import com.nimbusds.jose.shaded.gson.internal.Streams;
/*     */ import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
/*     */ import com.nimbusds.jose.shaded.gson.stream.JsonReader;
/*     */ import com.nimbusds.jose.shaded.gson.stream.JsonWriter;
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
/*     */ public final class TreeTypeAdapter<T>
/*     */   extends SerializationDelegatingTypeAdapter<T>
/*     */ {
/*     */   private final JsonSerializer<T> serializer;
/*     */   private final JsonDeserializer<T> deserializer;
/*     */   final Gson gson;
/*     */   private final TypeToken<T> typeToken;
/*     */   private final TypeAdapterFactory skipPastForGetDelegateAdapter;
/*  53 */   private final GsonContextImpl context = new GsonContextImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean nullSafe;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile TypeAdapter<T> delegate;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeTypeAdapter(JsonSerializer<T> serializer, JsonDeserializer<T> deserializer, Gson gson, TypeToken<T> typeToken, TypeAdapterFactory skipPast, boolean nullSafe) {
/*  69 */     this.serializer = serializer;
/*  70 */     this.deserializer = deserializer;
/*  71 */     this.gson = gson;
/*  72 */     this.typeToken = typeToken;
/*  73 */     this.skipPastForGetDelegateAdapter = skipPast;
/*  74 */     this.nullSafe = nullSafe;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeTypeAdapter(JsonSerializer<T> serializer, JsonDeserializer<T> deserializer, Gson gson, TypeToken<T> typeToken, TypeAdapterFactory skipPast) {
/*  83 */     this(serializer, deserializer, gson, typeToken, skipPast, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public T read(JsonReader in) throws IOException {
/*  88 */     if (this.deserializer == null) {
/*  89 */       return (T)delegate().read(in);
/*     */     }
/*  91 */     JsonElement value = Streams.parse(in);
/*  92 */     if (this.nullSafe && value.isJsonNull()) {
/*  93 */       return null;
/*     */     }
/*  95 */     return (T)this.deserializer.deserialize(value, this.typeToken.getType(), this.context);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(JsonWriter out, T value) throws IOException {
/* 100 */     if (this.serializer == null) {
/* 101 */       delegate().write(out, value);
/*     */       return;
/*     */     } 
/* 104 */     if (this.nullSafe && value == null) {
/* 105 */       out.nullValue();
/*     */       return;
/*     */     } 
/* 108 */     JsonElement tree = this.serializer.serialize(value, this.typeToken.getType(), this.context);
/* 109 */     Streams.write(tree, out);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TypeAdapter<T> delegate() {
/* 115 */     TypeAdapter<T> d = this.delegate;
/* 116 */     if (d == null) {
/* 117 */       d = this.delegate = this.gson.getDelegateAdapter(this.skipPastForGetDelegateAdapter, this.typeToken);
/*     */     }
/* 119 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeAdapter<T> getSerializationDelegate() {
/* 128 */     return (this.serializer != null) ? this : delegate();
/*     */   }
/*     */ 
/*     */   
/*     */   public static TypeAdapterFactory newFactory(TypeToken<?> exactType, Object typeAdapter) {
/* 133 */     return new SingleTypeFactory(typeAdapter, exactType, false, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapterFactory newFactoryWithMatchRawType(TypeToken<?> exactType, Object typeAdapter) {
/* 140 */     boolean matchRawType = (exactType.getType() == exactType.getRawType());
/* 141 */     return new SingleTypeFactory(typeAdapter, exactType, matchRawType, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeAdapterFactory newTypeHierarchyFactory(Class<?> hierarchyType, Object typeAdapter) {
/* 150 */     return new SingleTypeFactory(typeAdapter, null, false, hierarchyType);
/*     */   }
/*     */   
/*     */   private static final class SingleTypeFactory
/*     */     implements TypeAdapterFactory {
/*     */     private final TypeToken<?> exactType;
/*     */     private final boolean matchRawType;
/*     */     private final Class<?> hierarchyType;
/*     */     private final JsonSerializer<?> serializer;
/*     */     private final JsonDeserializer<?> deserializer;
/*     */     
/*     */     SingleTypeFactory(Object typeAdapter, TypeToken<?> exactType, boolean matchRawType, Class<?> hierarchyType) {
/* 162 */       this.serializer = (typeAdapter instanceof JsonSerializer) ? (JsonSerializer)typeAdapter : null;
/* 163 */       this
/* 164 */         .deserializer = (typeAdapter instanceof JsonDeserializer) ? (JsonDeserializer)typeAdapter : null;
/* 165 */       GsonPreconditions.checkArgument((this.serializer != null || this.deserializer != null));
/* 166 */       this.exactType = exactType;
/* 167 */       this.matchRawType = matchRawType;
/* 168 */       this.hierarchyType = hierarchyType;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
/* 177 */       boolean matches = (this.exactType != null) ? ((this.exactType.equals(type) || (this.matchRawType && this.exactType.getType() == type.getRawType()))) : this.hierarchyType.isAssignableFrom(type.getRawType());
/* 178 */       return matches ? 
/* 179 */         new TreeTypeAdapter<>((JsonSerializer)this.serializer, (JsonDeserializer)this.deserializer, gson, type, this) : 
/*     */         
/* 181 */         null;
/*     */     }
/*     */   }
/*     */   
/*     */   private final class GsonContextImpl implements JsonSerializationContext, JsonDeserializationContext {
/*     */     private GsonContextImpl() {}
/*     */     
/*     */     public JsonElement serialize(Object src) {
/* 189 */       return TreeTypeAdapter.this.gson.toJsonTree(src);
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(Object src, Type typeOfSrc) {
/* 194 */       return TreeTypeAdapter.this.gson.toJsonTree(src, typeOfSrc);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <R> R deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
/* 200 */       return (R)TreeTypeAdapter.this.gson.fromJson(json, typeOfT);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\shaded\gson\internal\bind\TreeTypeAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */