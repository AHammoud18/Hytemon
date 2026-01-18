/*    */ package com.hypixel.hytale.codec.store;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*    */ import com.hypixel.hytale.codec.schema.SchemaConvertable;
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import org.bson.BsonValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StoredCodec<T>
/*    */   implements Codec<T>
/*    */ {
/*    */   private final CodecKey<T> key;
/*    */   
/*    */   public StoredCodec(CodecKey<T> key) {
/* 27 */     this.key = key;
/*    */   }
/*    */ 
/*    */   
/*    */   public T decode(BsonValue bsonValue, @Nonnull ExtraInfo extraInfo) {
/* 32 */     Codec<T> codec = extraInfo.getCodecStore().getCodec(this.key);
/* 33 */     if (codec == null) throw new IllegalArgumentException("Failed to find codec for " + String.valueOf(this.key)); 
/* 34 */     return (T)codec.decode(bsonValue, extraInfo);
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonValue encode(T t, @Nonnull ExtraInfo extraInfo) {
/* 39 */     Codec<T> codec = extraInfo.getCodecStore().getCodec(this.key);
/* 40 */     if (codec == null) throw new IllegalArgumentException("Failed to find codec for " + String.valueOf(this.key)); 
/* 41 */     return codec.encode(t, extraInfo);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public T decodeJson(@Nonnull RawJsonReader reader, ExtraInfo extraInfo) throws IOException {
/* 47 */     Codec<T> codec = extraInfo.getCodecStore().getCodec(this.key);
/* 48 */     if (codec == null) throw new IllegalArgumentException("Failed to find codec for " + String.valueOf(this.key)); 
/* 49 */     return (T)codec.decodeJson(reader, extraInfo);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Schema toSchema(@Nonnull SchemaContext context) {
/* 55 */     Codec<T> codec = CodecStore.STATIC.getCodec(this.key);
/* 56 */     if (codec == null) throw new IllegalArgumentException("Failed to find codec for " + String.valueOf(this.key)); 
/* 57 */     return context.refDefinition((SchemaConvertable)codec);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\store\StoredCodec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */