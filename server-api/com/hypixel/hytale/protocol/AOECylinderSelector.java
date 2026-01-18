/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AOECylinderSelector
/*     */   extends Selector
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 21;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 21;
/*     */   public static final int MAX_SIZE = 21;
/*     */   public float range;
/*     */   public float height;
/*     */   @Nullable
/*     */   public Vector3f offset;
/*     */   
/*     */   public AOECylinderSelector() {}
/*     */   
/*     */   public AOECylinderSelector(float range, float height, @Nullable Vector3f offset) {
/*  28 */     this.range = range;
/*  29 */     this.height = height;
/*  30 */     this.offset = offset;
/*     */   }
/*     */   
/*     */   public AOECylinderSelector(@Nonnull AOECylinderSelector other) {
/*  34 */     this.range = other.range;
/*  35 */     this.height = other.height;
/*  36 */     this.offset = other.offset;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AOECylinderSelector deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     AOECylinderSelector obj = new AOECylinderSelector();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.range = buf.getFloatLE(offset + 1);
/*  44 */     obj.height = buf.getFloatLE(offset + 5);
/*  45 */     if ((nullBits & 0x1) != 0) obj.offset = Vector3f.deserialize(buf, offset + 9);
/*     */ 
/*     */     
/*  48 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  52 */     return 21;
/*     */   }
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/*  57 */     int startPos = buf.writerIndex();
/*  58 */     byte nullBits = 0;
/*  59 */     if (this.offset != null) nullBits = (byte)(nullBits | 0x1); 
/*  60 */     buf.writeByte(nullBits);
/*     */     
/*  62 */     buf.writeFloatLE(this.range);
/*  63 */     buf.writeFloatLE(this.height);
/*  64 */     if (this.offset != null) { this.offset.serialize(buf); } else { buf.writeZero(12); }
/*     */     
/*  66 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  72 */     return 21;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  76 */     if (buffer.readableBytes() - offset < 21) {
/*  77 */       return ValidationResult.error("Buffer too small: expected at least 21 bytes");
/*     */     }
/*     */ 
/*     */     
/*  81 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AOECylinderSelector clone() {
/*  85 */     AOECylinderSelector copy = new AOECylinderSelector();
/*  86 */     copy.range = this.range;
/*  87 */     copy.height = this.height;
/*  88 */     copy.offset = (this.offset != null) ? this.offset.clone() : null;
/*  89 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AOECylinderSelector other;
/*  95 */     if (this == obj) return true; 
/*  96 */     if (obj instanceof AOECylinderSelector) { other = (AOECylinderSelector)obj; } else { return false; }
/*  97 */      return (this.range == other.range && this.height == other.height && Objects.equals(this.offset, other.offset));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 102 */     return Objects.hash(new Object[] { Float.valueOf(this.range), Float.valueOf(this.height), this.offset });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AOECylinderSelector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */