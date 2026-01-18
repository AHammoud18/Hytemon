/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColorAlpha
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 4;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 4;
/*     */   public static final int MAX_SIZE = 4;
/*     */   public byte alpha;
/*     */   public byte red;
/*     */   public byte green;
/*     */   public byte blue;
/*     */   
/*     */   public ColorAlpha() {}
/*     */   
/*     */   public ColorAlpha(byte alpha, byte red, byte green, byte blue) {
/*  29 */     this.alpha = alpha;
/*  30 */     this.red = red;
/*  31 */     this.green = green;
/*  32 */     this.blue = blue;
/*     */   }
/*     */   
/*     */   public ColorAlpha(@Nonnull ColorAlpha other) {
/*  36 */     this.alpha = other.alpha;
/*  37 */     this.red = other.red;
/*  38 */     this.green = other.green;
/*  39 */     this.blue = other.blue;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ColorAlpha deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     ColorAlpha obj = new ColorAlpha();
/*     */     
/*  46 */     obj.alpha = buf.getByte(offset + 0);
/*  47 */     obj.red = buf.getByte(offset + 1);
/*  48 */     obj.green = buf.getByte(offset + 2);
/*  49 */     obj.blue = buf.getByte(offset + 3);
/*     */ 
/*     */     
/*  52 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  56 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  61 */     buf.writeByte(this.alpha);
/*  62 */     buf.writeByte(this.red);
/*  63 */     buf.writeByte(this.green);
/*  64 */     buf.writeByte(this.blue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  70 */     return 4;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  74 */     if (buffer.readableBytes() - offset < 4) {
/*  75 */       return ValidationResult.error("Buffer too small: expected at least 4 bytes");
/*     */     }
/*     */ 
/*     */     
/*  79 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ColorAlpha clone() {
/*  83 */     ColorAlpha copy = new ColorAlpha();
/*  84 */     copy.alpha = this.alpha;
/*  85 */     copy.red = this.red;
/*  86 */     copy.green = this.green;
/*  87 */     copy.blue = this.blue;
/*  88 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ColorAlpha other;
/*  94 */     if (this == obj) return true; 
/*  95 */     if (obj instanceof ColorAlpha) { other = (ColorAlpha)obj; } else { return false; }
/*  96 */      return (this.alpha == other.alpha && this.red == other.red && this.green == other.green && this.blue == other.blue);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     return Objects.hash(new Object[] { Byte.valueOf(this.alpha), Byte.valueOf(this.red), Byte.valueOf(this.green), Byte.valueOf(this.blue) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ColorAlpha.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */