/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class ItemPullbackConfiguration
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 49;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 49;
/*     */   public static final int MAX_SIZE = 49;
/*     */   @Nullable
/*     */   public Vector3f leftOffsetOverride;
/*     */   @Nullable
/*     */   public Vector3f leftRotationOverride;
/*     */   @Nullable
/*     */   public Vector3f rightOffsetOverride;
/*     */   @Nullable
/*     */   public Vector3f rightRotationOverride;
/*     */   
/*     */   public ItemPullbackConfiguration() {}
/*     */   
/*     */   public ItemPullbackConfiguration(@Nullable Vector3f leftOffsetOverride, @Nullable Vector3f leftRotationOverride, @Nullable Vector3f rightOffsetOverride, @Nullable Vector3f rightRotationOverride) {
/*  29 */     this.leftOffsetOverride = leftOffsetOverride;
/*  30 */     this.leftRotationOverride = leftRotationOverride;
/*  31 */     this.rightOffsetOverride = rightOffsetOverride;
/*  32 */     this.rightRotationOverride = rightRotationOverride;
/*     */   }
/*     */   
/*     */   public ItemPullbackConfiguration(@Nonnull ItemPullbackConfiguration other) {
/*  36 */     this.leftOffsetOverride = other.leftOffsetOverride;
/*  37 */     this.leftRotationOverride = other.leftRotationOverride;
/*  38 */     this.rightOffsetOverride = other.rightOffsetOverride;
/*  39 */     this.rightRotationOverride = other.rightRotationOverride;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemPullbackConfiguration deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     ItemPullbackConfiguration obj = new ItemPullbackConfiguration();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     if ((nullBits & 0x1) != 0) obj.leftOffsetOverride = Vector3f.deserialize(buf, offset + 1); 
/*  47 */     if ((nullBits & 0x2) != 0) obj.leftRotationOverride = Vector3f.deserialize(buf, offset + 13); 
/*  48 */     if ((nullBits & 0x4) != 0) obj.rightOffsetOverride = Vector3f.deserialize(buf, offset + 25); 
/*  49 */     if ((nullBits & 0x8) != 0) obj.rightRotationOverride = Vector3f.deserialize(buf, offset + 37);
/*     */ 
/*     */     
/*  52 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  56 */     return 49;
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  60 */     byte nullBits = 0;
/*  61 */     if (this.leftOffsetOverride != null) nullBits = (byte)(nullBits | 0x1); 
/*  62 */     if (this.leftRotationOverride != null) nullBits = (byte)(nullBits | 0x2); 
/*  63 */     if (this.rightOffsetOverride != null) nullBits = (byte)(nullBits | 0x4); 
/*  64 */     if (this.rightRotationOverride != null) nullBits = (byte)(nullBits | 0x8); 
/*  65 */     buf.writeByte(nullBits);
/*     */     
/*  67 */     if (this.leftOffsetOverride != null) { this.leftOffsetOverride.serialize(buf); } else { buf.writeZero(12); }
/*  68 */      if (this.leftRotationOverride != null) { this.leftRotationOverride.serialize(buf); } else { buf.writeZero(12); }
/*  69 */      if (this.rightOffsetOverride != null) { this.rightOffsetOverride.serialize(buf); } else { buf.writeZero(12); }
/*  70 */      if (this.rightRotationOverride != null) { this.rightRotationOverride.serialize(buf); } else { buf.writeZero(12); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  76 */     return 49;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  80 */     if (buffer.readableBytes() - offset < 49) {
/*  81 */       return ValidationResult.error("Buffer too small: expected at least 49 bytes");
/*     */     }
/*     */ 
/*     */     
/*  85 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemPullbackConfiguration clone() {
/*  89 */     ItemPullbackConfiguration copy = new ItemPullbackConfiguration();
/*  90 */     copy.leftOffsetOverride = (this.leftOffsetOverride != null) ? this.leftOffsetOverride.clone() : null;
/*  91 */     copy.leftRotationOverride = (this.leftRotationOverride != null) ? this.leftRotationOverride.clone() : null;
/*  92 */     copy.rightOffsetOverride = (this.rightOffsetOverride != null) ? this.rightOffsetOverride.clone() : null;
/*  93 */     copy.rightRotationOverride = (this.rightRotationOverride != null) ? this.rightRotationOverride.clone() : null;
/*  94 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemPullbackConfiguration other;
/* 100 */     if (this == obj) return true; 
/* 101 */     if (obj instanceof ItemPullbackConfiguration) { other = (ItemPullbackConfiguration)obj; } else { return false; }
/* 102 */      return (Objects.equals(this.leftOffsetOverride, other.leftOffsetOverride) && Objects.equals(this.leftRotationOverride, other.leftRotationOverride) && Objects.equals(this.rightOffsetOverride, other.rightOffsetOverride) && Objects.equals(this.rightRotationOverride, other.rightRotationOverride));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     return Objects.hash(new Object[] { this.leftOffsetOverride, this.leftRotationOverride, this.rightOffsetOverride, this.rightRotationOverride });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemPullbackConfiguration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */