/*    */ package com.hypixel.hytale.codec.function;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.WrappedCodec;
/*    */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import java.util.Objects;
/*    */ import java.util.function.BiFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class BsonFunctionCodec<T>
/*    */   implements Codec<T>, WrappedCodec<T>
/*    */ {
/*    */   @Nonnull
/*    */   private final Codec<T> codec;
/*    */   @Nonnull
/*    */   private final BiFunction<T, BsonValue, T> decode;
/*    */   @Nonnull
/*    */   private final BiFunction<BsonValue, T, BsonValue> encode;
/*    */   
/*    */   public BsonFunctionCodec(Codec<T> codec, BiFunction<T, BsonValue, T> decode, BiFunction<BsonValue, T, BsonValue> encode) {
/* 29 */     this.codec = Objects.<Codec<T>>requireNonNull(codec, "codec parameter can't be null");
/* 30 */     this.decode = Objects.<BiFunction<T, BsonValue, T>>requireNonNull(decode, "decode parameter can't be null");
/* 31 */     this.encode = Objects.<BiFunction<BsonValue, T, BsonValue>>requireNonNull(encode, "encode parameter can't be null");
/*    */   }
/*    */ 
/*    */   
/*    */   public T decode(BsonValue bsonValue, ExtraInfo extraInfo) {
/* 36 */     return this.decode.apply((T)this.codec.decode(bsonValue, extraInfo), bsonValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonValue encode(T r, ExtraInfo extraInfo) {
/* 41 */     return this.encode.apply(this.codec.encode(r, extraInfo), r);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Schema toSchema(@Nonnull SchemaContext context) {
/* 47 */     return this.codec.toSchema(context);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Codec<T> getChildCodec() {
/* 53 */     return this.codec;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\function\BsonFunctionCodec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */