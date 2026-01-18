/*     */ package com.hypixel.hytale.codec.schema.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.bson.BsonValue;
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
/*     */ @Deprecated
/*     */ class ItemOrItems
/*     */   implements Codec<Object>
/*     */ {
/*     */   @Nonnull
/*     */   private ArrayCodec<Schema> array;
/*     */   
/*     */   private ItemOrItems() {
/* 121 */     this.array = new ArrayCodec((Codec)Schema.CODEC, x$0 -> new Schema[x$0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object decode(@Nonnull BsonValue bsonValue, @Nonnull ExtraInfo extraInfo) {
/* 126 */     if (bsonValue.isArray()) return this.array.decode(bsonValue, extraInfo); 
/* 127 */     return Schema.CODEC.decode(bsonValue, extraInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonValue encode(Object o, ExtraInfo extraInfo) {
/* 132 */     if (o instanceof Schema[]) {
/* 133 */       return this.array.encode((Object[])o, extraInfo);
/*     */     }
/* 135 */     return Schema.CODEC.encode(o, extraInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Schema toSchema(@Nonnull SchemaContext context) {
/* 142 */     return Schema.anyOf(new Schema[] { Schema.CODEC
/* 143 */           .toSchema(context), this.array
/* 144 */           .toSchema(context) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\config\ArraySchema$ItemOrItems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */