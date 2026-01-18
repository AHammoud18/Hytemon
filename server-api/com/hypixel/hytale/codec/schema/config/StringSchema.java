/*     */ package com.hypixel.hytale.codec.schema.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class StringSchema
/*     */   extends Schema
/*     */ {
/*     */   public static final BuilderCodec<StringSchema> CODEC;
/*     */   private String pattern;
/*     */   private String[] enum_;
/*     */   private String const_;
/*     */   private String default_;
/*     */   private Integer minLength;
/*     */   private Integer maxLength;
/*     */   private CommonAsset hytaleCommonAsset;
/*     */   private String hytaleCosmeticAsset;
/*     */   
/*     */   static {
/*  54 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(StringSchema.class, StringSchema::new, Schema.BASE_CODEC).addField(new KeyedCodec("pattern", (Codec)Codec.STRING, false, true), (o, i) -> o.pattern = i, o -> o.pattern)).addField(new KeyedCodec("enum", (Codec)Codec.STRING_ARRAY, false, true), (o, i) -> o.enum_ = i, o -> o.enum_)).addField(new KeyedCodec("const", (Codec)Codec.STRING, false, true), (o, i) -> o.const_ = i, o -> o.const_)).addField(new KeyedCodec("default", (Codec)Codec.STRING, false, true), (o, i) -> o.default_ = i, o -> o.default_)).addField(new KeyedCodec("minLength", (Codec)Codec.INTEGER, false, true), (o, i) -> o.minLength = i, o -> o.minLength)).addField(new KeyedCodec("maxLength", (Codec)Codec.INTEGER, false, true), (o, i) -> o.maxLength = i, o -> o.maxLength)).addField(new KeyedCodec("hytaleCommonAsset", (Codec)CommonAsset.CODEC, false, true), (o, i) -> o.hytaleCommonAsset = i, o -> o.hytaleCommonAsset)).addField(new KeyedCodec("hytaleCosmeticAsset", (Codec)Codec.STRING, false, true), (o, i) -> o.hytaleCosmeticAsset = i, o -> o.hytaleCosmeticAsset)).build();
/*     */   }
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
/*     */   public String getPattern() {
/*  70 */     return this.pattern;
/*     */   }
/*     */   
/*     */   public void setPattern(String pattern) {
/*  74 */     this.pattern = pattern;
/*     */   }
/*     */   
/*     */   public void setPattern(@Nonnull Pattern pattern) {
/*  78 */     if (pattern.flags() != 0) {
/*  79 */       throw new IllegalArgumentException("Pattern has flags set. Flags are not supported in schema.");
/*     */     }
/*  81 */     this.pattern = pattern.pattern();
/*     */   }
/*     */   
/*     */   public Integer getMinLength() {
/*  85 */     return this.minLength;
/*     */   }
/*     */   
/*     */   public void setMinLength(int minLength) {
/*  89 */     this.minLength = Integer.valueOf(minLength);
/*     */   }
/*     */   
/*     */   public Integer getMaxLength() {
/*  93 */     return this.maxLength;
/*     */   }
/*     */   
/*     */   public void setMaxLength(int maxLength) {
/*  97 */     this.maxLength = Integer.valueOf(maxLength);
/*     */   }
/*     */   
/*     */   public String[] getEnum() {
/* 101 */     return this.enum_;
/*     */   }
/*     */   
/*     */   public void setEnum(String[] enum_) {
/* 105 */     this.enum_ = enum_;
/*     */   }
/*     */   
/*     */   public String getConst() {
/* 109 */     return this.const_;
/*     */   }
/*     */   
/*     */   public void setConst(String const_) {
/* 113 */     this.const_ = const_;
/*     */   }
/*     */   
/*     */   public String getDefault() {
/* 117 */     return this.default_;
/*     */   }
/*     */   
/*     */   public void setDefault(String default_) {
/* 121 */     this.default_ = default_;
/*     */   }
/*     */   
/*     */   public CommonAsset getHytaleCommonAsset() {
/* 125 */     return this.hytaleCommonAsset;
/*     */   }
/*     */   
/*     */   public void setHytaleCommonAsset(CommonAsset hytaleCommonAsset) {
/* 129 */     this.hytaleCommonAsset = hytaleCommonAsset;
/*     */   }
/*     */   
/*     */   public String getHytaleCosmeticAsset() {
/* 133 */     return this.hytaleCosmeticAsset;
/*     */   }
/*     */   
/*     */   public void setHytaleCosmeticAsset(String hytaleCosmeticAsset) {
/* 137 */     this.hytaleCosmeticAsset = hytaleCosmeticAsset;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(@Nullable Object o) {
/* 142 */     if (this == o) return true; 
/* 143 */     if (o == null || getClass() != o.getClass()) return false; 
/* 144 */     if (!super.equals(o)) return false;
/*     */     
/* 146 */     StringSchema that = (StringSchema)o;
/*     */     
/* 148 */     if ((this.pattern != null) ? !this.pattern.equals(that.pattern) : (that.pattern != null)) return false;
/*     */     
/* 150 */     if (!Arrays.equals((Object[])this.enum_, (Object[])that.enum_)) return false; 
/* 151 */     if ((this.const_ != null) ? !this.const_.equals(that.const_) : (that.const_ != null)) return false; 
/* 152 */     if ((this.default_ != null) ? !this.default_.equals(that.default_) : (that.default_ != null)) return false; 
/* 153 */     if ((this.minLength != null) ? !this.minLength.equals(that.minLength) : (that.minLength != null)) return false; 
/* 154 */     if ((this.maxLength != null) ? !this.maxLength.equals(that.maxLength) : (that.maxLength != null)) return false; 
/* 155 */     if ((this.hytaleCommonAsset != null) ? !this.hytaleCommonAsset.equals(that.hytaleCommonAsset) : (that.hytaleCommonAsset != null)) return false; 
/* 156 */     return (this.hytaleCosmeticAsset != null) ? this.hytaleCosmeticAsset.equals(that.hytaleCosmeticAsset) : ((that.hytaleCosmeticAsset == null));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 161 */     int result = super.hashCode();
/* 162 */     result = 31 * result + ((this.pattern != null) ? this.pattern.hashCode() : 0);
/* 163 */     result = 31 * result + Arrays.hashCode((Object[])this.enum_);
/* 164 */     result = 31 * result + ((this.const_ != null) ? this.const_.hashCode() : 0);
/* 165 */     result = 31 * result + ((this.default_ != null) ? this.default_.hashCode() : 0);
/* 166 */     result = 31 * result + ((this.minLength != null) ? this.minLength.hashCode() : 0);
/* 167 */     result = 31 * result + ((this.maxLength != null) ? this.maxLength.hashCode() : 0);
/* 168 */     result = 31 * result + ((this.hytaleCommonAsset != null) ? this.hytaleCommonAsset.hashCode() : 0);
/* 169 */     result = 31 * result + ((this.hytaleCosmeticAsset != null) ? this.hytaleCosmeticAsset.hashCode() : 0);
/* 170 */     return result;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Schema constant(String c) {
/* 175 */     StringSchema s = new StringSchema();
/* 176 */     s.setConst(c);
/* 177 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CommonAsset
/*     */   {
/*     */     public static final BuilderCodec<CommonAsset> CODEC;
/*     */ 
/*     */     
/*     */     private String[] requiredRoots;
/*     */ 
/*     */     
/*     */     private String requiredExtension;
/*     */ 
/*     */     
/*     */     private boolean isUIAsset;
/*     */ 
/*     */     
/*     */     static {
/* 197 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CommonAsset.class, CommonAsset::new).addField(new KeyedCodec("requiredRoots", (Codec)Codec.STRING_ARRAY, false, true), (o, i) -> o.requiredRoots = i, o -> o.requiredRoots)).addField(new KeyedCodec("requiredExtension", (Codec)Codec.STRING, false, true), (o, i) -> o.requiredExtension = i, o -> o.requiredExtension)).addField(new KeyedCodec("isUIAsset", (Codec)Codec.BOOLEAN, false, true), (o, i) -> o.isUIAsset = i.booleanValue(), o -> Boolean.valueOf(o.isUIAsset))).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CommonAsset(String requiredExtension, boolean isUIAsset, String... requiredRoots) {
/* 204 */       this.requiredRoots = requiredRoots;
/* 205 */       this.requiredExtension = requiredExtension;
/* 206 */       this.isUIAsset = isUIAsset;
/*     */     }
/*     */ 
/*     */     
/*     */     protected CommonAsset() {}
/*     */     
/*     */     public String[] getRequiredRoots() {
/* 213 */       return this.requiredRoots;
/*     */     }
/*     */     
/*     */     public String getRequiredExtension() {
/* 217 */       return this.requiredExtension;
/*     */     }
/*     */     
/*     */     public boolean isUIAsset() {
/* 221 */       return this.isUIAsset;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\config\StringSchema.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */