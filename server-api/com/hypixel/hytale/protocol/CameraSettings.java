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
/*     */ public class CameraSettings
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 13;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 21;
/*     */   public static final int MAX_SIZE = 8192049;
/*     */   @Nullable
/*     */   public Vector3f positionOffset;
/*     */   @Nullable
/*     */   public CameraAxis yaw;
/*     */   @Nullable
/*     */   public CameraAxis pitch;
/*     */   
/*     */   public CameraSettings() {}
/*     */   
/*     */   public CameraSettings(@Nullable Vector3f positionOffset, @Nullable CameraAxis yaw, @Nullable CameraAxis pitch) {
/*  28 */     this.positionOffset = positionOffset;
/*  29 */     this.yaw = yaw;
/*  30 */     this.pitch = pitch;
/*     */   }
/*     */   
/*     */   public CameraSettings(@Nonnull CameraSettings other) {
/*  34 */     this.positionOffset = other.positionOffset;
/*  35 */     this.yaw = other.yaw;
/*  36 */     this.pitch = other.pitch;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CameraSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     CameraSettings obj = new CameraSettings();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     if ((nullBits & 0x1) != 0) obj.positionOffset = Vector3f.deserialize(buf, offset + 1);
/*     */     
/*  45 */     if ((nullBits & 0x2) != 0) {
/*  46 */       int varPos0 = offset + 21 + buf.getIntLE(offset + 13);
/*  47 */       obj.yaw = CameraAxis.deserialize(buf, varPos0);
/*     */     } 
/*  49 */     if ((nullBits & 0x4) != 0) {
/*  50 */       int varPos1 = offset + 21 + buf.getIntLE(offset + 17);
/*  51 */       obj.pitch = CameraAxis.deserialize(buf, varPos1);
/*     */     } 
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int maxEnd = 21;
/*  60 */     if ((nullBits & 0x2) != 0) {
/*  61 */       int fieldOffset0 = buf.getIntLE(offset + 13);
/*  62 */       int pos0 = offset + 21 + fieldOffset0;
/*  63 */       pos0 += CameraAxis.computeBytesConsumed(buf, pos0);
/*  64 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  66 */     if ((nullBits & 0x4) != 0) {
/*  67 */       int fieldOffset1 = buf.getIntLE(offset + 17);
/*  68 */       int pos1 = offset + 21 + fieldOffset1;
/*  69 */       pos1 += CameraAxis.computeBytesConsumed(buf, pos1);
/*  70 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  72 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  77 */     int startPos = buf.writerIndex();
/*  78 */     byte nullBits = 0;
/*  79 */     if (this.positionOffset != null) nullBits = (byte)(nullBits | 0x1); 
/*  80 */     if (this.yaw != null) nullBits = (byte)(nullBits | 0x2); 
/*  81 */     if (this.pitch != null) nullBits = (byte)(nullBits | 0x4); 
/*  82 */     buf.writeByte(nullBits);
/*     */     
/*  84 */     if (this.positionOffset != null) { this.positionOffset.serialize(buf); } else { buf.writeZero(12); }
/*     */     
/*  86 */     int yawOffsetSlot = buf.writerIndex();
/*  87 */     buf.writeIntLE(0);
/*  88 */     int pitchOffsetSlot = buf.writerIndex();
/*  89 */     buf.writeIntLE(0);
/*     */     
/*  91 */     int varBlockStart = buf.writerIndex();
/*  92 */     if (this.yaw != null) {
/*  93 */       buf.setIntLE(yawOffsetSlot, buf.writerIndex() - varBlockStart);
/*  94 */       this.yaw.serialize(buf);
/*     */     } else {
/*  96 */       buf.setIntLE(yawOffsetSlot, -1);
/*     */     } 
/*  98 */     if (this.pitch != null) {
/*  99 */       buf.setIntLE(pitchOffsetSlot, buf.writerIndex() - varBlockStart);
/* 100 */       this.pitch.serialize(buf);
/*     */     } else {
/* 102 */       buf.setIntLE(pitchOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 108 */     int size = 21;
/* 109 */     if (this.yaw != null) size += this.yaw.computeSize(); 
/* 110 */     if (this.pitch != null) size += this.pitch.computeSize();
/*     */     
/* 112 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 116 */     if (buffer.readableBytes() - offset < 21) {
/* 117 */       return ValidationResult.error("Buffer too small: expected at least 21 bytes");
/*     */     }
/*     */     
/* 120 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 123 */     if ((nullBits & 0x2) != 0) {
/* 124 */       int yawOffset = buffer.getIntLE(offset + 13);
/* 125 */       if (yawOffset < 0) {
/* 126 */         return ValidationResult.error("Invalid offset for Yaw");
/*     */       }
/* 128 */       int pos = offset + 21 + yawOffset;
/* 129 */       if (pos >= buffer.writerIndex()) {
/* 130 */         return ValidationResult.error("Offset out of bounds for Yaw");
/*     */       }
/* 132 */       ValidationResult yawResult = CameraAxis.validateStructure(buffer, pos);
/* 133 */       if (!yawResult.isValid()) {
/* 134 */         return ValidationResult.error("Invalid Yaw: " + yawResult.error());
/*     */       }
/* 136 */       pos += CameraAxis.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 139 */     if ((nullBits & 0x4) != 0) {
/* 140 */       int pitchOffset = buffer.getIntLE(offset + 17);
/* 141 */       if (pitchOffset < 0) {
/* 142 */         return ValidationResult.error("Invalid offset for Pitch");
/*     */       }
/* 144 */       int pos = offset + 21 + pitchOffset;
/* 145 */       if (pos >= buffer.writerIndex()) {
/* 146 */         return ValidationResult.error("Offset out of bounds for Pitch");
/*     */       }
/* 148 */       ValidationResult pitchResult = CameraAxis.validateStructure(buffer, pos);
/* 149 */       if (!pitchResult.isValid()) {
/* 150 */         return ValidationResult.error("Invalid Pitch: " + pitchResult.error());
/*     */       }
/* 152 */       pos += CameraAxis.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 154 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CameraSettings clone() {
/* 158 */     CameraSettings copy = new CameraSettings();
/* 159 */     copy.positionOffset = (this.positionOffset != null) ? this.positionOffset.clone() : null;
/* 160 */     copy.yaw = (this.yaw != null) ? this.yaw.clone() : null;
/* 161 */     copy.pitch = (this.pitch != null) ? this.pitch.clone() : null;
/* 162 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CameraSettings other;
/* 168 */     if (this == obj) return true; 
/* 169 */     if (obj instanceof CameraSettings) { other = (CameraSettings)obj; } else { return false; }
/* 170 */      return (Objects.equals(this.positionOffset, other.positionOffset) && Objects.equals(this.yaw, other.yaw) && Objects.equals(this.pitch, other.pitch));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 175 */     return Objects.hash(new Object[] { this.positionOffset, this.yaw, this.pitch });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\CameraSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */