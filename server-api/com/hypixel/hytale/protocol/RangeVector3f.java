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
/*     */ public class RangeVector3f
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 25;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 25;
/*     */   public static final int MAX_SIZE = 25;
/*     */   @Nullable
/*     */   public Rangef x;
/*     */   @Nullable
/*     */   public Rangef y;
/*     */   @Nullable
/*     */   public Rangef z;
/*     */   
/*     */   public RangeVector3f() {}
/*     */   
/*     */   public RangeVector3f(@Nullable Rangef x, @Nullable Rangef y, @Nullable Rangef z) {
/*  28 */     this.x = x;
/*  29 */     this.y = y;
/*  30 */     this.z = z;
/*     */   }
/*     */   
/*     */   public RangeVector3f(@Nonnull RangeVector3f other) {
/*  34 */     this.x = other.x;
/*  35 */     this.y = other.y;
/*  36 */     this.z = other.z;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static RangeVector3f deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     RangeVector3f obj = new RangeVector3f();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     if ((nullBits & 0x1) != 0) obj.x = Rangef.deserialize(buf, offset + 1); 
/*  44 */     if ((nullBits & 0x2) != 0) obj.y = Rangef.deserialize(buf, offset + 9); 
/*  45 */     if ((nullBits & 0x4) != 0) obj.z = Rangef.deserialize(buf, offset + 17);
/*     */ 
/*     */     
/*  48 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  52 */     return 25;
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  56 */     byte nullBits = 0;
/*  57 */     if (this.x != null) nullBits = (byte)(nullBits | 0x1); 
/*  58 */     if (this.y != null) nullBits = (byte)(nullBits | 0x2); 
/*  59 */     if (this.z != null) nullBits = (byte)(nullBits | 0x4); 
/*  60 */     buf.writeByte(nullBits);
/*     */     
/*  62 */     if (this.x != null) { this.x.serialize(buf); } else { buf.writeZero(8); }
/*  63 */      if (this.y != null) { this.y.serialize(buf); } else { buf.writeZero(8); }
/*  64 */      if (this.z != null) { this.z.serialize(buf); } else { buf.writeZero(8); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  70 */     return 25;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  74 */     if (buffer.readableBytes() - offset < 25) {
/*  75 */       return ValidationResult.error("Buffer too small: expected at least 25 bytes");
/*     */     }
/*     */ 
/*     */     
/*  79 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public RangeVector3f clone() {
/*  83 */     RangeVector3f copy = new RangeVector3f();
/*  84 */     copy.x = (this.x != null) ? this.x.clone() : null;
/*  85 */     copy.y = (this.y != null) ? this.y.clone() : null;
/*  86 */     copy.z = (this.z != null) ? this.z.clone() : null;
/*  87 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RangeVector3f other;
/*  93 */     if (this == obj) return true; 
/*  94 */     if (obj instanceof RangeVector3f) { other = (RangeVector3f)obj; } else { return false; }
/*  95 */      return (Objects.equals(this.x, other.x) && Objects.equals(this.y, other.y) && Objects.equals(this.z, other.z));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 100 */     return Objects.hash(new Object[] { this.x, this.y, this.z });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\RangeVector3f.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */