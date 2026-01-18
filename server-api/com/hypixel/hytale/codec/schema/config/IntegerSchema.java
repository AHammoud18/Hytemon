/*     */ package com.hypixel.hytale.codec.schema.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.schema.SchemaContext;
/*     */ import java.util.Arrays;
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
/*     */ public class IntegerSchema
/*     */   extends Schema
/*     */ {
/*     */   public static final BuilderCodec<IntegerSchema> CODEC;
/*     */   private Object minimum;
/*     */   private Object exclusiveMinimum;
/*     */   private Object maximum;
/*     */   private Object exclusiveMaximum;
/*     */   private int[] enum_;
/*     */   private Integer const_;
/*     */   private Integer default_;
/*     */   
/*     */   static {
/*  51 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(IntegerSchema.class, IntegerSchema::new, Schema.BASE_CODEC).addField(new KeyedCodec("minimum", IntegerOrSchema.INSTANCE, false, true), (o, i) -> o.minimum = i, o -> o.minimum)).addField(new KeyedCodec("exclusiveMinimum", IntegerOrSchema.INSTANCE, false, true), (o, i) -> o.exclusiveMinimum = i, o -> o.exclusiveMinimum)).addField(new KeyedCodec("maximum", IntegerOrSchema.INSTANCE, false, true), (o, i) -> o.maximum = i, o -> o.maximum)).addField(new KeyedCodec("exclusiveMaximum", IntegerOrSchema.INSTANCE, false, true), (o, i) -> o.exclusiveMaximum = i, o -> o.exclusiveMaximum)).addField(new KeyedCodec("enum", (Codec)Codec.INT_ARRAY, false, true), (o, i) -> o.enum_ = i, o -> o.enum_)).addField(new KeyedCodec("const", (Codec)Codec.INTEGER, false, true), (o, i) -> o.const_ = i, o -> o.const_)).addField(new KeyedCodec("default", (Codec)Codec.INTEGER, false, true), (o, i) -> o.default_ = i, o -> o.default_)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Object getMinimum() {
/*  63 */     return this.minimum;
/*     */   }
/*     */   
/*     */   public void setMinimum(int minimum) {
/*  67 */     this.minimum = Integer.valueOf(minimum);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object getExclusiveMinimum() {
/*  72 */     return this.exclusiveMinimum;
/*     */   }
/*     */   
/*     */   public void setExclusiveMinimum(int exclusiveMinimum) {
/*  76 */     this.exclusiveMinimum = Integer.valueOf(exclusiveMinimum);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object getMaximum() {
/*  81 */     return this.maximum;
/*     */   }
/*     */   
/*     */   public void setMaximum(int maximum) {
/*  85 */     this.maximum = Integer.valueOf(maximum);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Object getExclusiveMaximum() {
/*  90 */     return this.exclusiveMaximum;
/*     */   }
/*     */   
/*     */   public void setExclusiveMaximum(int exclusiveMaximum) {
/*  94 */     this.exclusiveMaximum = Integer.valueOf(exclusiveMaximum);
/*     */   }
/*     */   
/*     */   public void setMinimum(Schema minimum) {
/*  98 */     this.minimum = minimum;
/*     */   }
/*     */   
/*     */   public void setExclusiveMinimum(Schema exclusiveMinimum) {
/* 102 */     this.exclusiveMinimum = exclusiveMinimum;
/*     */   }
/*     */   
/*     */   public void setMaximum(Schema maximum) {
/* 106 */     this.maximum = maximum;
/*     */   }
/*     */   
/*     */   public void setExclusiveMaximum(Schema exclusiveMaximum) {
/* 110 */     this.exclusiveMaximum = exclusiveMaximum;
/*     */   }
/*     */   
/*     */   public int[] getEnum() {
/* 114 */     return this.enum_;
/*     */   }
/*     */   
/*     */   public void setEnum(int[] enum_) {
/* 118 */     this.enum_ = enum_;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Integer getConst() {
/* 123 */     return this.const_;
/*     */   }
/*     */   
/*     */   public void setConst(Integer const_) {
/* 127 */     this.const_ = const_;
/*     */   }
/*     */   
/*     */   public Integer getDefault() {
/* 131 */     return this.default_;
/*     */   }
/*     */   
/*     */   public void setDefault(Integer default_) {
/* 135 */     this.default_ = default_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 140 */     if (this == o) return true; 
/* 141 */     if (o == null || getClass() != o.getClass()) return false; 
/* 142 */     if (!super.equals(o)) return false;
/*     */     
/* 144 */     IntegerSchema that = (IntegerSchema)o;
/*     */     
/* 146 */     if ((this.minimum != null) ? !this.minimum.equals(that.minimum) : (that.minimum != null)) return false; 
/* 147 */     if ((this.exclusiveMinimum != null) ? !this.exclusiveMinimum.equals(that.exclusiveMinimum) : (that.exclusiveMinimum != null)) return false; 
/* 148 */     if ((this.maximum != null) ? !this.maximum.equals(that.maximum) : (that.maximum != null)) return false; 
/* 149 */     if ((this.exclusiveMaximum != null) ? !this.exclusiveMaximum.equals(that.exclusiveMaximum) : (that.exclusiveMaximum != null)) return false; 
/* 150 */     if (!Arrays.equals(this.enum_, that.enum_)) return false; 
/* 151 */     if ((this.const_ != null) ? !this.const_.equals(that.const_) : (that.const_ != null)) return false; 
/* 152 */     return (this.default_ != null) ? this.default_.equals(that.default_) : ((that.default_ == null));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 157 */     int result = super.hashCode();
/* 158 */     result = 31 * result + ((this.minimum != null) ? this.minimum.hashCode() : 0);
/* 159 */     result = 31 * result + ((this.exclusiveMinimum != null) ? this.exclusiveMinimum.hashCode() : 0);
/* 160 */     result = 31 * result + ((this.maximum != null) ? this.maximum.hashCode() : 0);
/* 161 */     result = 31 * result + ((this.exclusiveMaximum != null) ? this.exclusiveMaximum.hashCode() : 0);
/* 162 */     result = 31 * result + Arrays.hashCode(this.enum_);
/* 163 */     result = 31 * result + ((this.const_ != null) ? this.const_.hashCode() : 0);
/* 164 */     result = 31 * result + ((this.default_ != null) ? this.default_.hashCode() : 0);
/* 165 */     return result;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Schema constant(int c) {
/* 170 */     IntegerSchema s = new IntegerSchema();
/* 171 */     s.setConst(Integer.valueOf(c));
/* 172 */     return s;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   private static class IntegerOrSchema
/*     */     implements Codec<Object> {
/* 178 */     private static final IntegerOrSchema INSTANCE = new IntegerOrSchema();
/*     */ 
/*     */     
/*     */     public Object decode(@Nonnull BsonValue bsonValue, ExtraInfo extraInfo) {
/* 182 */       if (bsonValue.isNumber()) return Codec.INTEGER.decode(bsonValue, extraInfo); 
/* 183 */       return Schema.CODEC.decode(bsonValue, extraInfo);
/*     */     }
/*     */ 
/*     */     
/*     */     public BsonValue encode(Object o, ExtraInfo extraInfo) {
/* 188 */       if (o instanceof Integer) {
/* 189 */         return Codec.INTEGER.encode((Integer)o, extraInfo);
/*     */       }
/* 191 */       return Schema.CODEC.encode(o, extraInfo);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Schema toSchema(@Nonnull SchemaContext context) {
/* 198 */       return Schema.anyOf(new Schema[] { new IntegerSchema(), Schema.CODEC
/*     */             
/* 200 */             .toSchema(context) });
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\config\IntegerSchema.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */