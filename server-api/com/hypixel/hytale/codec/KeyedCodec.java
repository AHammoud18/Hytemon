/*     */ package com.hypixel.hytale.codec;
/*     */ 
/*     */ import com.hypixel.hytale.codec.exception.CodecException;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonSerializationException;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyedCodec<T>
/*     */ {
/*     */   @Nonnull
/*     */   private final String key;
/*     */   @Nonnull
/*     */   private final Codec<T> codec;
/*     */   private final boolean required;
/*     */   
/*     */   public KeyedCodec(@Nonnull String key, Codec<T> codec) {
/*  27 */     this(key, codec, false);
/*     */   }
/*     */   
/*     */   public KeyedCodec(@Nonnull String key, Codec<T> codec, boolean required) {
/*  31 */     this.key = Objects.<String>requireNonNull(key, "key parameter can't be null");
/*  32 */     this.codec = Objects.<Codec<T>>requireNonNull(codec, "codec parameter can't be null");
/*  33 */     this.required = required;
/*     */     
/*  35 */     if (key.isEmpty()) throw new IllegalArgumentException("Key must not be empty! Key: '" + key + "'");
/*     */     
/*  37 */     char firstCharFromKey = key.charAt(0);
/*  38 */     if (Character.isLetter(firstCharFromKey) && !Character.isUpperCase(firstCharFromKey)) throw new IllegalArgumentException("Key must start with an upper case character! Key: '" + key + "'");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public KeyedCodec(@Nonnull String key, Codec<T> codec, boolean required, boolean bypassCaseCheck) {
/*  46 */     this.key = Objects.<String>requireNonNull(key, "key parameter can't be null");
/*  47 */     this.codec = Objects.<Codec<T>>requireNonNull(codec, "codec parameter can't be null");
/*  48 */     this.required = required;
/*     */     
/*  50 */     if (key.isEmpty()) throw new IllegalArgumentException("Key must not be empty! Key: '" + key + "'");
/*     */     
/*  52 */     char firstCharFromKey = key.charAt(0);
/*  53 */     if (!bypassCaseCheck && Character.isLetter(firstCharFromKey) && !Character.isUpperCase(firstCharFromKey)) throw new IllegalArgumentException("Key must start with an upper case character! Key: '" + key + "'"); 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String getKey() {
/*  58 */     return this.key;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public T getNow(BsonDocument document) {
/*  63 */     return getNow(document, EmptyExtraInfo.EMPTY);
/*     */   }
/*     */   
/*     */   public T getNow(BsonDocument document, @Nonnull ExtraInfo extraInfo) {
/*  67 */     return get(document, extraInfo).orElseThrow(() -> new BsonSerializationException(this.key + " does not exist in document!"));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @Deprecated
/*     */   public T getOrNull(BsonDocument document) {
/*  73 */     return getOrNull(document, EmptyExtraInfo.EMPTY);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T getOrNull(BsonDocument document, @Nonnull ExtraInfo extraInfo) {
/*  78 */     return get(document, extraInfo).orElse(null);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   @Deprecated
/*     */   public Optional<T> get(BsonDocument document) {
/*  84 */     return get(document, EmptyExtraInfo.EMPTY);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Optional<T> get(@Nullable BsonDocument document, @Nonnull ExtraInfo extraInfo) {
/*  89 */     extraInfo.pushKey(this.key);
/*     */     try {
/*  91 */       if (document == null) return (Optional)Optional.empty();
/*     */       
/*  93 */       BsonValue bsonValue = document.get(this.key);
/*  94 */       if (Codec.isNullBsonValue(bsonValue)) return (Optional)Optional.empty(); 
/*  95 */       return Optional.ofNullable(decode(bsonValue, extraInfo));
/*  96 */     } catch (Exception e) {
/*  97 */       throw new CodecException("Failed decode", document, extraInfo, e);
/*     */     } finally {
/*  99 */       extraInfo.popKey();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T getOrDefault(@Nullable BsonDocument document, @Nonnull ExtraInfo extraInfo, T def) {
/* 105 */     extraInfo.pushKey(this.key);
/*     */     try {
/* 107 */       if (document == null) return def;
/*     */       
/* 109 */       BsonValue bsonValue = document.get(this.key);
/* 110 */       if (Codec.isNullBsonValue(bsonValue)) return def;
/*     */       
/* 112 */       return this.codec.decode(bsonValue, extraInfo);
/* 113 */     } catch (Exception e) {
/* 114 */       throw new CodecException("Failed decode", document, extraInfo, e);
/*     */     } finally {
/* 116 */       extraInfo.popKey();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Optional<T> getAndInherit(@Nullable BsonDocument document, T parent, @Nonnull ExtraInfo extraInfo) {
/* 122 */     extraInfo.pushKey(this.key);
/*     */     try {
/* 124 */       if (document == null) return Optional.ofNullable(decodeAndInherit(null, parent, extraInfo));
/*     */       
/* 126 */       BsonValue bsonValue = document.get(this.key);
/* 127 */       if (Codec.isNullBsonValue(bsonValue)) return Optional.ofNullable(decodeAndInherit(null, parent, extraInfo)); 
/* 128 */       return Optional.ofNullable(decodeAndInherit(bsonValue, parent, extraInfo));
/* 129 */     } catch (Exception e) {
/* 130 */       throw new CodecException("Failed decode", document, extraInfo, e);
/*     */     } finally {
/* 132 */       extraInfo.popKey();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void put(@Nonnull BsonDocument document, T t) {
/* 138 */     put(document, t, EmptyExtraInfo.EMPTY);
/*     */   }
/*     */   
/*     */   public void put(@Nonnull BsonDocument document, @Nullable T t, @Nonnull ExtraInfo extraInfo) {
/* 142 */     if (t != null) {
/*     */       try {
/* 144 */         document.put(this.key, encode(t, extraInfo));
/* 145 */       } catch (Exception e) {
/* 146 */         throw new CodecException("Failed encode", t, extraInfo, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected T decode(BsonValue bsonValue, @Nonnull ExtraInfo extraInfo) {
/* 153 */     if (!this.required && Codec.isNullBsonValue(bsonValue)) {
/* 154 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 158 */       return this.codec.decode(bsonValue, extraInfo);
/* 159 */     } catch (Exception e) {
/* 160 */       throw new CodecException("Failed to decode", bsonValue, extraInfo, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected T decodeAndInherit(@Nullable BsonValue bsonValue, T parent, @Nonnull ExtraInfo extraInfo) {
/* 166 */     if (!this.required && Codec.isNullBsonValue(bsonValue)) {
/* 167 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 171 */       if (bsonValue != null && bsonValue.isDocument() && this.codec instanceof InheritCodec) {
/* 172 */         return ((InheritCodec<T>)this.codec).decodeAndInherit(bsonValue.asDocument(), parent, extraInfo);
/*     */       }
/* 174 */       return this.codec.decode(bsonValue, extraInfo);
/* 175 */     } catch (Exception e) {
/* 176 */       throw new CodecException("Failed to decode", bsonValue, extraInfo, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected BsonValue encode(T t, ExtraInfo extraInfo) {
/* 181 */     return this.codec.encode(t, extraInfo);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Codec<T> getChildCodec() {
/* 186 */     return this.codec;
/*     */   }
/*     */   
/*     */   public boolean isRequired() {
/* 190 */     return this.required;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 196 */     return "KeyedCodec{key='" + this.key + "', codec=" + String.valueOf(this.codec) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\KeyedCodec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */