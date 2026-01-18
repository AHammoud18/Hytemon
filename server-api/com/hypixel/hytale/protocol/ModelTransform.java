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
/*     */ public class ModelTransform
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 49;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 49;
/*     */   public static final int MAX_SIZE = 49;
/*     */   @Nullable
/*     */   public Position position;
/*     */   @Nullable
/*     */   public Direction bodyOrientation;
/*     */   @Nullable
/*     */   public Direction lookOrientation;
/*     */   
/*     */   public ModelTransform() {}
/*     */   
/*     */   public ModelTransform(@Nullable Position position, @Nullable Direction bodyOrientation, @Nullable Direction lookOrientation) {
/*  28 */     this.position = position;
/*  29 */     this.bodyOrientation = bodyOrientation;
/*  30 */     this.lookOrientation = lookOrientation;
/*     */   }
/*     */   
/*     */   public ModelTransform(@Nonnull ModelTransform other) {
/*  34 */     this.position = other.position;
/*  35 */     this.bodyOrientation = other.bodyOrientation;
/*  36 */     this.lookOrientation = other.lookOrientation;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ModelTransform deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     ModelTransform obj = new ModelTransform();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     if ((nullBits & 0x1) != 0) obj.position = Position.deserialize(buf, offset + 1); 
/*  44 */     if ((nullBits & 0x2) != 0) obj.bodyOrientation = Direction.deserialize(buf, offset + 25); 
/*  45 */     if ((nullBits & 0x4) != 0) obj.lookOrientation = Direction.deserialize(buf, offset + 37);
/*     */ 
/*     */     
/*  48 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  52 */     return 49;
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  56 */     byte nullBits = 0;
/*  57 */     if (this.position != null) nullBits = (byte)(nullBits | 0x1); 
/*  58 */     if (this.bodyOrientation != null) nullBits = (byte)(nullBits | 0x2); 
/*  59 */     if (this.lookOrientation != null) nullBits = (byte)(nullBits | 0x4); 
/*  60 */     buf.writeByte(nullBits);
/*     */     
/*  62 */     if (this.position != null) { this.position.serialize(buf); } else { buf.writeZero(24); }
/*  63 */      if (this.bodyOrientation != null) { this.bodyOrientation.serialize(buf); } else { buf.writeZero(12); }
/*  64 */      if (this.lookOrientation != null) { this.lookOrientation.serialize(buf); } else { buf.writeZero(12); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  70 */     return 49;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  74 */     if (buffer.readableBytes() - offset < 49) {
/*  75 */       return ValidationResult.error("Buffer too small: expected at least 49 bytes");
/*     */     }
/*     */ 
/*     */     
/*  79 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ModelTransform clone() {
/*  83 */     ModelTransform copy = new ModelTransform();
/*  84 */     copy.position = (this.position != null) ? this.position.clone() : null;
/*  85 */     copy.bodyOrientation = (this.bodyOrientation != null) ? this.bodyOrientation.clone() : null;
/*  86 */     copy.lookOrientation = (this.lookOrientation != null) ? this.lookOrientation.clone() : null;
/*  87 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ModelTransform other;
/*  93 */     if (this == obj) return true; 
/*  94 */     if (obj instanceof ModelTransform) { other = (ModelTransform)obj; } else { return false; }
/*  95 */      return (Objects.equals(this.position, other.position) && Objects.equals(this.bodyOrientation, other.bodyOrientation) && Objects.equals(this.lookOrientation, other.lookOrientation));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 100 */     return Objects.hash(new Object[] { this.position, this.bodyOrientation, this.lookOrientation });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ModelTransform.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */