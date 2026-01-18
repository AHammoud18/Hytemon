/*    */ package com.hypixel.hytale.codec.function;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.ExtraInfo;
/*    */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*    */ import com.hypixel.hytale.codec.schema.config.Schema;
/*    */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*    */ import java.io.IOException;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.bson.BsonValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class FunctionCodec<T, R>
/*    */   implements Codec<R>
/*    */ {
/*    */   @Nonnull
/*    */   private final Codec<T> codec;
/*    */   @Nonnull
/*    */   private final Function<T, R> decode;
/*    */   @Nonnull
/*    */   private final Function<R, T> encode;
/*    */   
/*    */   public FunctionCodec(Codec<T> codec, Function<T, R> decode, Function<R, T> encode) {
/* 30 */     this.codec = Objects.<Codec<T>>requireNonNull(codec, "codec parameter can't be null");
/* 31 */     this.decode = Objects.<Function<T, R>>requireNonNull(decode, "decode parameter can't be null");
/* 32 */     this.encode = Objects.<Function<R, T>>requireNonNull(encode, "encode parameter can't be null");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public R decode(BsonValue bsonValue, ExtraInfo extraInfo) {
/* 38 */     T decode = (T)this.codec.decode(bsonValue, extraInfo);
/* 39 */     R value = this.decode.apply(decode);
/* 40 */     if (value == null) throw new IllegalArgumentException("Failed to apply function to '" + String.valueOf(decode) + "' decoded from '" + String.valueOf(bsonValue) + "'!"); 
/* 41 */     return value;
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonValue encode(R r, ExtraInfo extraInfo) {
/* 46 */     return this.codec.encode(this.encode.apply(r), extraInfo);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public R decodeJson(@Nonnull RawJsonReader reader, ExtraInfo extraInfo) throws IOException {
/* 52 */     T decode = (T)this.codec.decodeJson(reader, extraInfo);
/* 53 */     R value = this.decode.apply(decode);
/* 54 */     if (value == null) throw new IllegalArgumentException("Failed to apply function to '" + String.valueOf(decode) + "'!"); 
/* 55 */     return value;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Schema toSchema(@Nonnull SchemaContext context) {
/* 61 */     return this.codec.toSchema(context);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\function\FunctionCodec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */