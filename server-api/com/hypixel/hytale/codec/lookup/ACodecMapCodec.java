/*     */ package com.hypixel.hytale.codec.lookup;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.InheritCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.exception.CodecException;
/*     */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*     */ import com.hypixel.hytale.codec.schema.SchemaConvertable;
/*     */ import com.hypixel.hytale.codec.schema.config.ObjectSchema;
/*     */ import com.hypixel.hytale.codec.schema.config.Schema;
/*     */ import com.hypixel.hytale.codec.schema.config.StringSchema;
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import com.hypixel.hytale.codec.validation.ValidatableCodec;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ public abstract class ACodecMapCodec<K, T, C extends Codec<? extends T>> implements Codec<T>, ValidatableCodec<T>, InheritCodec<T> {
/*     */   protected final String key;
/*     */   protected final Codec<K> keyCodec;
/*  34 */   protected final Map<K, C> idToCodec = new ConcurrentHashMap<>();
/*  35 */   protected final Map<Class<? extends T>, K> classToId = new ConcurrentHashMap<>();
/*  36 */   protected final Map<K, Class<? extends T>> idToClass = new ConcurrentHashMap<>();
/*     */   @Nonnull
/*  38 */   protected AtomicReference<CodecPriority<C>[]> codecs = new AtomicReference(new CodecPriority[0]);
/*     */   
/*     */   protected final boolean allowDefault;
/*     */   
/*     */   protected final boolean encodeDefaultKey;
/*     */   
/*     */   public ACodecMapCodec(Codec<K> keyCodec) {
/*  45 */     this(keyCodec, false);
/*     */   }
/*     */   
/*     */   public ACodecMapCodec(Codec<K> keyCodec, boolean allowDefault) {
/*  49 */     this("Id", keyCodec, allowDefault);
/*     */   }
/*     */   
/*     */   public ACodecMapCodec(String id, Codec<K> keyCodec) {
/*  53 */     this(id, keyCodec, false);
/*     */   }
/*     */   
/*     */   public ACodecMapCodec(String key, Codec<K> keyCodec, boolean allowDefault) {
/*  57 */     this(key, keyCodec, allowDefault, true);
/*     */   }
/*     */   
/*     */   public ACodecMapCodec(String key, Codec<K> keyCodec, boolean allowDefault, boolean encodeDefaultKey) {
/*  61 */     this.key = key;
/*  62 */     this.allowDefault = allowDefault;
/*  63 */     this.encodeDefaultKey = encodeDefaultKey;
/*  64 */     this.keyCodec = keyCodec;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ACodecMapCodec<K, T, C> register(K id, Class<? extends T> aClass, C codec) {
/*  69 */     register(Priority.NORMAL, id, aClass, codec);
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   public ACodecMapCodec<K, T, C> register(@Nonnull Priority priority, K id, Class<? extends T> aClass, C codec) {
/*     */     CodecPriority[] arrayOfCodecPriority1, arrayOfCodecPriority2;
/*  75 */     this.idToCodec.put(id, codec);
/*  76 */     this.classToId.put(aClass, id);
/*  77 */     this.idToClass.put(id, aClass);
/*     */     
/*  79 */     if (codec instanceof ValidatableCodec)
/*     */     {
/*  81 */       ((ValidatableCodec)codec).validateDefaults(new ExtraInfo(), new HashSet());
/*     */     }
/*     */ 
/*     */     
/*  85 */     if (!this.allowDefault && !priority.equals(Priority.NORMAL)) {
/*  86 */       throw new IllegalStateException("Defaults disallowed but non-normal priority provided");
/*     */     }
/*     */ 
/*     */     
/*  90 */     if (!this.allowDefault) return this;
/*     */     
/*  92 */     CodecPriority<C> codecPriority = new CodecPriority<>(codec, priority);
/*     */     
/*     */     do {
/*     */       int insertionPoint;
/*     */       
/*  97 */       arrayOfCodecPriority1 = (CodecPriority[])this.codecs.get();
/*  98 */       int index = Arrays.binarySearch(arrayOfCodecPriority1, codecPriority, 
/*     */           
/* 100 */           Comparator.comparingInt(a -> a.priority().getLevel()));
/*     */ 
/*     */       
/* 103 */       if (index >= 0) {
/* 104 */         insertionPoint = index + 1;
/*     */       } else {
/* 106 */         insertionPoint = -(index + 1);
/*     */       } 
/*     */       
/* 109 */       arrayOfCodecPriority2 = new CodecPriority[arrayOfCodecPriority1.length + 1];
/* 110 */       System.arraycopy(arrayOfCodecPriority1, 0, arrayOfCodecPriority2, 0, insertionPoint);
/* 111 */       arrayOfCodecPriority2[insertionPoint] = codecPriority;
/* 112 */       System.arraycopy(arrayOfCodecPriority1, insertionPoint, arrayOfCodecPriority2, insertionPoint + 1, arrayOfCodecPriority1.length - insertionPoint);
/* 113 */     } while (!this.codecs.compareAndSet(arrayOfCodecPriority1, arrayOfCodecPriority2));
/*     */     
/* 115 */     return this;
/*     */   }
/*     */   
/*     */   public void remove(Class<? extends T> aClass) {
/*     */     CodecPriority[] arrayOfCodecPriority1, arrayOfCodecPriority2;
/* 120 */     K id = this.classToId.remove(aClass);
/* 121 */     Codec codec = (Codec)this.idToCodec.remove(id);
/* 122 */     this.idToClass.remove(id);
/*     */ 
/*     */     
/* 125 */     if (!this.allowDefault) {
/*     */       return;
/*     */     }
/*     */     
/*     */     do {
/* 130 */       arrayOfCodecPriority1 = (CodecPriority[])this.codecs.get();
/*     */       
/* 132 */       int index = -1;
/* 133 */       for (int i = 0; i < arrayOfCodecPriority1.length; i++) {
/* 134 */         CodecPriority<C> c = arrayOfCodecPriority1[i];
/* 135 */         if (c.codec() == codec) {
/* 136 */           index = i;
/*     */           break;
/*     */         } 
/*     */       } 
/* 140 */       if (index == -1)
/*     */         return; 
/* 142 */       arrayOfCodecPriority2 = new CodecPriority[arrayOfCodecPriority1.length - 1];
/* 143 */       System.arraycopy(arrayOfCodecPriority1, 0, arrayOfCodecPriority2, 0, index);
/* 144 */       System.arraycopy(arrayOfCodecPriority1, index + 1, arrayOfCodecPriority2, index, arrayOfCodecPriority1.length - index - 1);
/* 145 */     } while (!this.codecs.compareAndSet(arrayOfCodecPriority1, arrayOfCodecPriority2));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public C getDefaultCodec() {
/* 150 */     CodecPriority[] arrayOfCodecPriority = (CodecPriority[])this.codecs.get();
/* 151 */     if (arrayOfCodecPriority.length == 0) return null; 
/* 152 */     return (C)arrayOfCodecPriority[0].codec();
/*     */   }
/*     */   
/*     */   public C getCodecFor(K key) {
/* 156 */     return this.idToCodec.get(key);
/*     */   }
/*     */   
/*     */   public C getCodecFor(Class<? extends T> key) {
/* 160 */     return this.idToCodec.get(this.classToId.get(key));
/*     */   }
/*     */   
/*     */   public Class<? extends T> getClassFor(K key) {
/* 164 */     return this.idToClass.get(key);
/*     */   }
/*     */   
/*     */   public K getIdFor(Class<? extends T> key) {
/* 168 */     return this.classToId.get(key);
/*     */   }
/*     */   
/*     */   public Set<K> getRegisteredIds() {
/* 172 */     return Collections.unmodifiableSet(this.idToCodec.keySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public T decode(@Nonnull BsonValue bsonValue, ExtraInfo extraInfo) {
/* 177 */     BsonDocument document = bsonValue.asDocument();
/*     */     
/* 179 */     BsonValue id = document.get(this.key);
/* 180 */     Codec codec = (id == null) ? null : (Codec)this.idToCodec.get(this.keyCodec.decode(id, extraInfo));
/* 181 */     if (codec == null) {
/* 182 */       C defaultCodec = getDefaultCodec();
/* 183 */       if (defaultCodec == null) throw new UnknownIdException("No codec registered with for '" + this.key + "': " + String.valueOf(id)); 
/* 184 */       return (T)defaultCodec.decode((BsonValue)document, extraInfo);
/*     */     } 
/* 186 */     return (T)codec.decode((BsonValue)document, extraInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T decodeAndInherit(@Nonnull BsonDocument document, T parent, ExtraInfo extraInfo) {
/* 193 */     BsonValue id = document.get(this.key);
/* 194 */     Codec codec = (Codec)this.idToCodec.get((id == null) ? null : id.asString().getValue());
/* 195 */     if (codec == null) {
/* 196 */       C defaultCodec = getDefaultCodec();
/* 197 */       if (defaultCodec == null) throw new UnknownIdException("No codec registered with for '" + this.key + "': " + String.valueOf(id)); 
/* 198 */       if (defaultCodec instanceof InheritCodec) {
/* 199 */         return (T)((InheritCodec)defaultCodec).decodeAndInherit(document, parent, extraInfo);
/*     */       }
/* 201 */       return (T)defaultCodec.decode((BsonValue)document, extraInfo);
/*     */     } 
/* 203 */     if (codec instanceof InheritCodec) {
/* 204 */       return (T)((InheritCodec)codec).decodeAndInherit(document, parent, extraInfo);
/*     */     }
/* 206 */     return (T)codec.decode((BsonValue)document, extraInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decodeAndInherit(@Nonnull BsonDocument document, T t, T parent, ExtraInfo extraInfo) {
/* 212 */     BsonValue id = document.get(this.key);
/* 213 */     Codec codec = (Codec)this.idToCodec.get((id == null) ? null : id.asString().getValue());
/* 214 */     if (codec == null) {
/* 215 */       C defaultCodec = getDefaultCodec();
/* 216 */       if (defaultCodec == null) throw new UnknownIdException("No codec registered with for '" + this.key + "': " + String.valueOf(id)); 
/* 217 */       if (defaultCodec instanceof InheritCodec) {
/* 218 */         ((InheritCodec)defaultCodec).decodeAndInherit(document, t, parent, extraInfo);
/*     */         return;
/*     */       } 
/* 221 */       throw new UnsupportedOperationException();
/*     */     } 
/* 223 */     if (codec instanceof InheritCodec) {
/* 224 */       ((InheritCodec)codec).decodeAndInherit(document, t, parent, extraInfo);
/*     */       return;
/*     */     } 
/* 227 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue encode(@Nonnull T t, ExtraInfo extraInfo) {
/*     */     C c1;
/* 233 */     Class<? extends T> aClass = (Class)t.getClass();
/* 234 */     K id = this.classToId.get(aClass);
/* 235 */     C defaultCodec = getDefaultCodec();
/* 236 */     if (id == null && defaultCodec == null) throw new UnknownIdException("No id registered with for '" + String.valueOf(aClass) + "': " + String.valueOf(t)); 
/* 237 */     Codec codec = (Codec)this.idToCodec.get(id);
/* 238 */     if (codec == null) {
/* 239 */       if (defaultCodec == null) throw new UnknownIdException("No codec registered with for '" + String.valueOf(aClass) + "': " + String.valueOf(t)); 
/* 240 */       c1 = defaultCodec;
/*     */     } 
/*     */     
/* 243 */     BsonValue encode = c1.encode(t, extraInfo);
/* 244 */     if (id == null) return encode;
/*     */     
/* 246 */     BsonDocument document = new BsonDocument();
/* 247 */     if (this.encodeDefaultKey || c1 != defaultCodec) {
/* 248 */       document.put(this.key, this.keyCodec.encode(id, extraInfo));
/*     */     }
/* 250 */     document.putAll((Map)encode.asDocument());
/* 251 */     return (BsonValue)document;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T decodeJson(@Nonnull RawJsonReader reader, @Nonnull ExtraInfo extraInfo) throws IOException {
/* 257 */     reader.mark();
/* 258 */     K id = null;
/* 259 */     if (RawJsonReader.seekToKey(reader, this.key)) {
/* 260 */       id = (K)this.keyCodec.decodeJson(reader, extraInfo);
/*     */     }
/* 262 */     reader.reset();
/*     */     
/* 264 */     extraInfo.ignoreUnusedKey(this.key);
/*     */     try {
/* 266 */       Codec codec = (id == null) ? null : (Codec)this.idToCodec.get(id);
/* 267 */       if (codec == null) {
/* 268 */         C defaultCodec = getDefaultCodec();
/* 269 */         if (defaultCodec == null) throw new UnknownIdException("No codec registered with for '" + this.key + "': " + String.valueOf(id)); 
/* 270 */         return (T)defaultCodec.decodeJson(reader, extraInfo);
/*     */       } 
/* 272 */       return (T)codec.decodeJson(reader, extraInfo);
/*     */     } finally {
/* 274 */       extraInfo.popIgnoredUnusedKey();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T decodeAndInheritJson(@Nonnull RawJsonReader reader, @Nullable T parent, @Nonnull ExtraInfo extraInfo) throws IOException {
/* 282 */     reader.mark();
/* 283 */     K id = null;
/* 284 */     if (RawJsonReader.seekToKey(reader, this.key)) {
/* 285 */       id = (K)this.keyCodec.decodeJson(reader, extraInfo);
/* 286 */     } else if (parent != null) {
/* 287 */       id = getIdFor((Class)parent.getClass());
/*     */     } 
/* 289 */     reader.reset();
/*     */     
/* 291 */     extraInfo.ignoreUnusedKey(this.key);
/*     */     try {
/* 293 */       Codec codec = (id == null) ? null : (Codec)this.idToCodec.get(id);
/* 294 */       if (codec == null) {
/* 295 */         C defaultCodec = getDefaultCodec();
/* 296 */         if (defaultCodec == null) throw new UnknownIdException("No codec registered with for '" + this.key + "': " + String.valueOf(id)); 
/* 297 */         if (defaultCodec instanceof InheritCodec) {
/* 298 */           return (T)((InheritCodec)defaultCodec).decodeAndInheritJson(reader, parent, extraInfo);
/*     */         }
/* 300 */         return (T)defaultCodec.decodeJson(reader, extraInfo);
/*     */       } 
/* 302 */       if (codec instanceof InheritCodec) {
/* 303 */         return (T)((InheritCodec)codec).decodeAndInheritJson(reader, parent, extraInfo);
/*     */       }
/* 305 */       return (T)codec.decodeJson(reader, extraInfo);
/*     */     } finally {
/* 307 */       extraInfo.popIgnoredUnusedKey();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decodeAndInheritJson(@Nonnull RawJsonReader reader, T t, @Nullable T parent, @Nonnull ExtraInfo extraInfo) throws IOException {
/* 314 */     reader.mark();
/* 315 */     K id = null;
/* 316 */     if (RawJsonReader.seekToKey(reader, this.key)) {
/* 317 */       id = (K)this.keyCodec.decodeJson(reader, extraInfo);
/* 318 */     } else if (parent != null) {
/* 319 */       id = getIdFor((Class)parent.getClass());
/*     */     } 
/* 321 */     reader.reset();
/*     */     
/* 323 */     extraInfo.ignoreUnusedKey(this.key);
/*     */     try {
/* 325 */       Codec codec = (id == null) ? null : (Codec)this.idToCodec.get(id);
/* 326 */       if (codec == null) {
/* 327 */         C defaultCodec = getDefaultCodec();
/* 328 */         if (defaultCodec == null) throw new UnknownIdException("No codec registered with for '" + this.key + "': " + String.valueOf(id)); 
/* 329 */         if (defaultCodec instanceof InheritCodec) {
/* 330 */           ((InheritCodec)defaultCodec).decodeAndInheritJson(reader, t, parent, extraInfo);
/*     */           return;
/*     */         } 
/* 333 */         throw new UnsupportedOperationException();
/*     */       } 
/* 335 */       if (codec instanceof InheritCodec) {
/* 336 */         ((InheritCodec)codec).decodeAndInheritJson(reader, t, parent, extraInfo);
/*     */         return;
/*     */       } 
/* 339 */       throw new UnsupportedOperationException();
/*     */     } finally {
/* 341 */       extraInfo.popIgnoredUnusedKey();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate(@Nonnull T t, ExtraInfo extraInfo) {
/* 348 */     K id = getIdFor((Class)t.getClass());
/* 349 */     C codec = getCodecFor(id);
/* 350 */     if (this.keyCodec instanceof ValidatableCodec) {
/* 351 */       ((ValidatableCodec)this.keyCodec).validate(id, extraInfo);
/*     */     }
/* 353 */     if (codec instanceof ValidatableCodec) {
/* 354 */       ((ValidatableCodec)codec).validate(t, extraInfo);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void validateDefaults(ExtraInfo extraInfo, @Nonnull Set<Codec<?>> tested) {
/* 360 */     if (!tested.add(this))
/* 361 */       return;  ValidatableCodec.validateDefaults(this.keyCodec, extraInfo, tested);
/* 362 */     for (Codec codec : this.idToCodec.values()) {
/* 363 */       ValidatableCodec.validateDefaults(codec, extraInfo, tested);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Schema toSchema(@Nonnull SchemaContext context) {
/* 371 */     ObjectArrayList<Schema> objectArrayList = new ObjectArrayList();
/* 372 */     Map.Entry[] arrayOfEntry = (Map.Entry[])this.idToCodec.entrySet().toArray(x$0 -> new Map.Entry[x$0]);
/*     */     
/* 374 */     Arrays.sort(arrayOfEntry, Comparator.comparing(e -> (e.getKey() instanceof Comparable) ? (Comparable)e.getKey() : e.getKey().toString()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 383 */     C def = this.allowDefault ? getDefaultCodec() : null;
/* 384 */     String defKey = null;
/*     */     
/* 386 */     for (Map.Entry<K, C> entry : arrayOfEntry) {
/* 387 */       Codec codec = (Codec)entry.getValue();
/* 388 */       if (codec == def) {
/* 389 */         defKey = entry.getKey().toString();
/*     */       }
/* 391 */       Schema schema = context.refDefinition((SchemaConvertable)codec);
/* 392 */       if (schema.getRef() != null && codec instanceof BuilderCodec) {
/* 393 */         BuilderCodec<? extends T> bc = (BuilderCodec<? extends T>)codec;
/* 394 */         Schema subSchema = context.getRawDefinition(bc);
/* 395 */         if (subSchema instanceof ObjectSchema) { ObjectSchema objectSchema = (ObjectSchema)subSchema;
/* 396 */           mutateChildSchema(entry.getKey().toString(), context, bc, objectSchema); }
/*     */       
/*     */       } 
/* 399 */       objectArrayList.add(schema);
/*     */     } 
/*     */     
/* 402 */     if (objectArrayList.isEmpty()) {
/* 403 */       ObjectSchema objectSchema = new ObjectSchema();
/* 404 */       objectSchema.setAdditionalProperties(false);
/* 405 */       return (Schema)objectSchema;
/*     */     } 
/*     */     
/* 408 */     Schema s = Schema.anyOf((Schema[])objectArrayList.toArray(x$0 -> new Schema[x$0]));
/* 409 */     s.getHytale().setMergesProperties(true);
/* 410 */     s.setTitle("Type Selector");
/* 411 */     s.setHytaleSchemaTypeField(new Schema.SchemaTypeField(this.key, defKey, 
/*     */ 
/*     */           
/* 414 */           (String[])Arrays.<Map.Entry>stream(arrayOfEntry).map(e -> e.getKey().toString()).toArray(x$0 -> new String[x$0])));
/*     */     
/* 416 */     return s;
/*     */   }
/*     */   
/*     */   protected void mutateChildSchema(String key, @Nonnull SchemaContext context, BuilderCodec<? extends T> c, @Nonnull ObjectSchema objectSchema) {
/* 420 */     C def = null;
/* 421 */     if (this.allowDefault) def = getDefaultCodec(); 
/* 422 */     Schema keySchema = this.keyCodec.toSchema(context);
/* 423 */     if (def == c) {
/* 424 */       keySchema.setTypes(new String[] { "null", "string" });
/*     */ 
/*     */       
/* 427 */       Schema origKey = keySchema;
/* 428 */       keySchema = new Schema();
/* 429 */       StringSchema enum_ = new StringSchema();
/* 430 */       enum_.setEnum((String[])this.idToCodec.entrySet().stream()
/* 431 */           .filter(v -> (v.getValue() != c))
/* 432 */           .map(Map.Entry::getKey)
/* 433 */           .map(Object::toString)
/* 434 */           .toArray(x$0 -> new String[x$0]));
/* 435 */       keySchema.setAllOf(new Schema[] { origKey, Schema.not((Schema)enum_) });
/*     */     } else {
/* 437 */       ((StringSchema)keySchema).setConst(key);
/*     */     } 
/* 439 */     keySchema.setMarkdownDescription("This field controls the type, it must be set to the constant value \"" + key + "\" to function as this type.");
/*     */ 
/*     */     
/* 442 */     LinkedHashMap<String, Schema> props = new LinkedHashMap<>();
/* 443 */     props.put(this.key, keySchema);
/* 444 */     Map<String, Schema> otherProps = objectSchema.getProperties();
/* 445 */     otherProps.remove(this.key);
/* 446 */     props.putAll(otherProps);
/* 447 */     objectSchema.setProperties(props);
/*     */   }
/*     */   
/*     */   public static class UnknownIdException extends CodecException {
/*     */     public UnknownIdException(String message) {
/* 452 */       super(message);
/*     */     } }
/*     */   private static final class CodecPriority<C> extends Record { private final C codec; private final Priority priority;
/*     */     
/* 456 */     private CodecPriority(C codec, Priority priority) { this.codec = codec; this.priority = priority; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/codec/lookup/ACodecMapCodec$CodecPriority;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #456	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/codec/lookup/ACodecMapCodec$CodecPriority;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 456 */       //   0	7	0	this	Lcom/hypixel/hytale/codec/lookup/ACodecMapCodec$CodecPriority<TC;>; } public C codec() { return this.codec; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/codec/lookup/ACodecMapCodec$CodecPriority;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #456	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/codec/lookup/ACodecMapCodec$CodecPriority;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/codec/lookup/ACodecMapCodec$CodecPriority<TC;>; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/codec/lookup/ACodecMapCodec$CodecPriority;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #456	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/codec/lookup/ACodecMapCodec$CodecPriority;
/*     */       //   0	8	1	o	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 456 */       //   0	8	0	this	Lcom/hypixel/hytale/codec/lookup/ACodecMapCodec$CodecPriority<TC;>; } public Priority priority() { return this.priority; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\lookup\ACodecMapCodec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */