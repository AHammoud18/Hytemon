/*     */ package com.google.gson.internal.bind;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import com.google.gson.internal.ConstructorConstructor;
/*     */ import com.google.gson.internal.GsonTypes;
/*     */ import com.google.gson.internal.JsonReaderInternalAccess;
/*     */ import com.google.gson.internal.ObjectConstructor;
/*     */ import com.google.gson.internal.Streams;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public final class MapTypeAdapterFactory
/*     */   implements TypeAdapterFactory
/*     */ {
/*     */   private final ConstructorConstructor constructorConstructor;
/*     */   final boolean complexMapKeySerialization;
/*     */   
/*     */   public MapTypeAdapterFactory(ConstructorConstructor constructorConstructor, boolean complexMapKeySerialization) {
/* 121 */     this.constructorConstructor = constructorConstructor;
/* 122 */     this.complexMapKeySerialization = complexMapKeySerialization;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
/* 127 */     Type type = typeToken.getType();
/*     */     
/* 129 */     Class<? super T> rawType = typeToken.getRawType();
/* 130 */     if (!Map.class.isAssignableFrom(rawType)) {
/* 131 */       return null;
/*     */     }
/*     */     
/* 134 */     Type[] keyAndValueTypes = GsonTypes.getMapKeyAndValueTypes(type, rawType);
/* 135 */     Type keyType = keyAndValueTypes[0];
/* 136 */     Type valueType = keyAndValueTypes[1];
/* 137 */     TypeAdapter<?> keyAdapter = getKeyAdapter(gson, keyType);
/* 138 */     TypeAdapter<?> wrappedKeyAdapter = new TypeAdapterRuntimeTypeWrapper(gson, keyAdapter, keyType);
/*     */     
/* 140 */     TypeAdapter<?> valueAdapter = gson.getAdapter(TypeToken.get(valueType));
/* 141 */     TypeAdapter<?> wrappedValueAdapter = new TypeAdapterRuntimeTypeWrapper(gson, valueAdapter, valueType);
/*     */ 
/*     */ 
/*     */     
/* 145 */     boolean allowUnsafe = false;
/* 146 */     ObjectConstructor<T> constructor = this.constructorConstructor.get(typeToken, allowUnsafe);
/*     */ 
/*     */ 
/*     */     
/* 150 */     TypeAdapter<T> result = (TypeAdapter)new Adapter<>(wrappedKeyAdapter, wrappedValueAdapter, (ObjectConstructor)constructor);
/* 151 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private TypeAdapter<?> getKeyAdapter(Gson context, Type keyType) {
/* 156 */     return (keyType == boolean.class || keyType == Boolean.class) ? 
/* 157 */       TypeAdapters.BOOLEAN_AS_STRING : 
/* 158 */       context.getAdapter(TypeToken.get(keyType));
/*     */   }
/*     */ 
/*     */   
/*     */   private final class Adapter<K, V>
/*     */     extends TypeAdapter<Map<K, V>>
/*     */   {
/*     */     private final TypeAdapter<K> keyTypeAdapter;
/*     */     private final TypeAdapter<V> valueTypeAdapter;
/*     */     private final ObjectConstructor<? extends Map<K, V>> constructor;
/*     */     
/*     */     Adapter(TypeAdapter<K> keyTypeAdapter, TypeAdapter<V> valueTypeAdapter, ObjectConstructor<? extends Map<K, V>> constructor) {
/* 170 */       this.keyTypeAdapter = keyTypeAdapter;
/* 171 */       this.valueTypeAdapter = valueTypeAdapter;
/* 172 */       this.constructor = constructor;
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<K, V> read(JsonReader in) throws IOException {
/* 177 */       JsonToken peek = in.peek();
/* 178 */       if (peek == JsonToken.NULL) {
/* 179 */         in.nextNull();
/* 180 */         return null;
/*     */       } 
/*     */       
/* 183 */       Map<K, V> map = (Map<K, V>)this.constructor.construct();
/*     */       
/* 185 */       if (peek == JsonToken.BEGIN_ARRAY) {
/* 186 */         in.beginArray();
/* 187 */         while (in.hasNext()) {
/* 188 */           in.beginArray();
/* 189 */           K key = (K)this.keyTypeAdapter.read(in);
/* 190 */           V value = (V)this.valueTypeAdapter.read(in);
/* 191 */           V replaced = map.put(key, value);
/* 192 */           if (replaced != null) {
/* 193 */             throw new JsonSyntaxException("duplicate key: " + key);
/*     */           }
/* 195 */           in.endArray();
/*     */         } 
/* 197 */         in.endArray();
/*     */       } else {
/* 199 */         in.beginObject();
/* 200 */         while (in.hasNext()) {
/* 201 */           JsonReaderInternalAccess.INSTANCE.promoteNameToValue(in);
/* 202 */           K key = (K)this.keyTypeAdapter.read(in);
/* 203 */           V value = (V)this.valueTypeAdapter.read(in);
/* 204 */           V replaced = map.put(key, value);
/* 205 */           if (replaced != null) {
/* 206 */             throw new JsonSyntaxException("duplicate key: " + key);
/*     */           }
/*     */         } 
/* 209 */         in.endObject();
/*     */       } 
/* 211 */       return map;
/*     */     }
/*     */     
/*     */     public void write(JsonWriter out, Map<K, V> map) throws IOException {
/*     */       int i;
/* 216 */       if (map == null) {
/* 217 */         out.nullValue();
/*     */         
/*     */         return;
/*     */       } 
/* 221 */       if (!MapTypeAdapterFactory.this.complexMapKeySerialization) {
/* 222 */         out.beginObject();
/* 223 */         for (Map.Entry<K, V> entry : map.entrySet()) {
/* 224 */           out.name(String.valueOf(entry.getKey()));
/* 225 */           this.valueTypeAdapter.write(out, entry.getValue());
/*     */         } 
/* 227 */         out.endObject();
/*     */         
/*     */         return;
/*     */       } 
/* 231 */       boolean hasComplexKeys = false;
/* 232 */       List<JsonElement> keys = new ArrayList<>(map.size());
/*     */       
/* 234 */       List<V> values = new ArrayList<>(map.size());
/* 235 */       for (Map.Entry<K, V> entry : map.entrySet()) {
/* 236 */         JsonElement keyElement = this.keyTypeAdapter.toJsonTree(entry.getKey());
/* 237 */         keys.add(keyElement);
/* 238 */         values.add(entry.getValue());
/* 239 */         i = hasComplexKeys | ((keyElement.isJsonArray() || keyElement.isJsonObject()) ? 1 : 0);
/*     */       } 
/*     */       
/* 242 */       if (i != 0) {
/* 243 */         out.beginArray();
/* 244 */         for (int j = 0, size = keys.size(); j < size; j++) {
/* 245 */           out.beginArray();
/* 246 */           Streams.write(keys.get(j), out);
/* 247 */           this.valueTypeAdapter.write(out, values.get(j));
/* 248 */           out.endArray();
/*     */         } 
/* 250 */         out.endArray();
/*     */       } else {
/* 252 */         out.beginObject();
/* 253 */         for (int j = 0, size = keys.size(); j < size; j++) {
/* 254 */           JsonElement keyElement = keys.get(j);
/* 255 */           out.name(keyToString(keyElement));
/* 256 */           this.valueTypeAdapter.write(out, values.get(j));
/*     */         } 
/* 258 */         out.endObject();
/*     */       } 
/*     */     }
/*     */     
/*     */     private String keyToString(JsonElement keyElement) {
/* 263 */       if (keyElement.isJsonPrimitive()) {
/* 264 */         JsonPrimitive primitive = keyElement.getAsJsonPrimitive();
/* 265 */         if (primitive.isNumber())
/* 266 */           return String.valueOf(primitive.getAsNumber()); 
/* 267 */         if (primitive.isBoolean())
/* 268 */           return Boolean.toString(primitive.getAsBoolean()); 
/* 269 */         if (primitive.isString()) {
/* 270 */           return primitive.getAsString();
/*     */         }
/* 272 */         throw new AssertionError();
/*     */       } 
/* 274 */       if (keyElement.isJsonNull()) {
/* 275 */         return "null";
/*     */       }
/* 277 */       throw new AssertionError();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\bind\MapTypeAdapterFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */