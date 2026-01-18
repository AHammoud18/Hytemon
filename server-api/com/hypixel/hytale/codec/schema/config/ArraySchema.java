/*     */ package com.hypixel.hytale.codec.schema.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class ArraySchema
/*     */   extends Schema
/*     */ {
/*     */   public static final BuilderCodec<ArraySchema> CODEC;
/*     */   private Object items;
/*     */   private Integer minItems;
/*     */   private Integer maxItems;
/*     */   private Boolean uniqueItems;
/*     */   
/*     */   static {
/*  36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ArraySchema.class, ArraySchema::new, Schema.BASE_CODEC).addField(new KeyedCodec("items", new ItemOrItems(), false, true), (o, i) -> o.items = i, o -> o.items)).addField(new KeyedCodec("minItems", (Codec)Codec.INTEGER, false, true), (o, i) -> o.minItems = i, o -> o.minItems)).addField(new KeyedCodec("maxItems", (Codec)Codec.INTEGER, false, true), (o, i) -> o.maxItems = i, o -> o.maxItems)).addField(new KeyedCodec("uniqueItems", (Codec)Codec.BOOLEAN, false, true), (o, i) -> o.uniqueItems = i, o -> o.uniqueItems)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArraySchema() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArraySchema(Schema item) {
/*  51 */     setItem(item);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object getItems() {
/*  56 */     return this.items;
/*     */   }
/*     */   
/*     */   public void setItem(Schema items) {
/*  60 */     this.items = items;
/*     */   }
/*     */   
/*     */   public void setItems(Schema... items) {
/*  64 */     this.items = items;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Integer getMinItems() {
/*  69 */     return this.minItems;
/*     */   }
/*     */   
/*     */   public void setMinItems(Integer minItems) {
/*  73 */     this.minItems = minItems;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Integer getMaxItems() {
/*  78 */     return this.maxItems;
/*     */   }
/*     */   
/*     */   public void setMaxItems(Integer maxItems) {
/*  82 */     this.maxItems = maxItems;
/*     */   }
/*     */   
/*     */   public boolean getUniqueItems() {
/*  86 */     return this.uniqueItems.booleanValue();
/*     */   }
/*     */   
/*     */   public void setUniqueItems(boolean uniqueItems) {
/*  90 */     this.uniqueItems = Boolean.valueOf(uniqueItems);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/*  95 */     if (this == o) return true; 
/*  96 */     if (o == null || getClass() != o.getClass()) return false; 
/*  97 */     if (!super.equals(o)) return false;
/*     */     
/*  99 */     ArraySchema that = (ArraySchema)o;
/*     */     
/* 101 */     if ((this.items != null) ? !this.items.equals(that.items) : (that.items != null)) return false; 
/* 102 */     if ((this.minItems != null) ? !this.minItems.equals(that.minItems) : (that.minItems != null)) return false; 
/* 103 */     if ((this.maxItems != null) ? !this.maxItems.equals(that.maxItems) : (that.maxItems != null)) return false; 
/* 104 */     return (this.uniqueItems != null) ? this.uniqueItems.equals(that.uniqueItems) : ((that.uniqueItems == null));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 109 */     int result = super.hashCode();
/* 110 */     result = 31 * result + ((this.items != null) ? this.items.hashCode() : 0);
/* 111 */     result = 31 * result + ((this.minItems != null) ? this.minItems.hashCode() : 0);
/* 112 */     result = 31 * result + ((this.maxItems != null) ? this.maxItems.hashCode() : 0);
/* 113 */     result = 31 * result + ((this.uniqueItems != null) ? this.uniqueItems.hashCode() : 0);
/* 114 */     return result;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   private static class ItemOrItems
/*     */     implements Codec<Object> {
/*     */     private ItemOrItems() {
/* 121 */       this.array = new ArrayCodec((Codec)Schema.CODEC, x$0 -> new Schema[x$0]);
/*     */     }
/*     */     @Nonnull
/*     */     private ArrayCodec<Schema> array;
/*     */     public Object decode(@Nonnull BsonValue bsonValue, @Nonnull ExtraInfo extraInfo) {
/* 126 */       if (bsonValue.isArray()) return this.array.decode(bsonValue, extraInfo); 
/* 127 */       return Schema.CODEC.decode(bsonValue, extraInfo);
/*     */     }
/*     */ 
/*     */     
/*     */     public BsonValue encode(Object o, ExtraInfo extraInfo) {
/* 132 */       if (o instanceof Schema[]) {
/* 133 */         return this.array.encode((Object[])o, extraInfo);
/*     */       }
/* 135 */       return Schema.CODEC.encode(o, extraInfo);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Schema toSchema(@Nonnull SchemaContext context) {
/* 142 */       return Schema.anyOf(new Schema[] { Schema.CODEC
/* 143 */             .toSchema(context), this.array
/* 144 */             .toSchema(context) });
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\config\ArraySchema.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */