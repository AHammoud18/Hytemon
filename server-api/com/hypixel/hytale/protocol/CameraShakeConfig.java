/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class CameraShakeConfig
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 20;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 28;
/*     */   public static final int MAX_SIZE = 565248084;
/*     */   public float duration;
/*     */   public float startTime;
/*     */   public boolean continuous;
/*     */   @Nullable
/*     */   public EasingConfig easeIn;
/*     */   @Nullable
/*     */   public EasingConfig easeOut;
/*     */   @Nullable
/*     */   public OffsetNoise offset;
/*     */   @Nullable
/*     */   public RotationNoise rotation;
/*     */   
/*     */   public CameraShakeConfig() {}
/*     */   
/*     */   public CameraShakeConfig(float duration, float startTime, boolean continuous, @Nullable EasingConfig easeIn, @Nullable EasingConfig easeOut, @Nullable OffsetNoise offset, @Nullable RotationNoise rotation) {
/*  32 */     this.duration = duration;
/*  33 */     this.startTime = startTime;
/*  34 */     this.continuous = continuous;
/*  35 */     this.easeIn = easeIn;
/*  36 */     this.easeOut = easeOut;
/*  37 */     this.offset = offset;
/*  38 */     this.rotation = rotation;
/*     */   }
/*     */   
/*     */   public CameraShakeConfig(@Nonnull CameraShakeConfig other) {
/*  42 */     this.duration = other.duration;
/*  43 */     this.startTime = other.startTime;
/*  44 */     this.continuous = other.continuous;
/*  45 */     this.easeIn = other.easeIn;
/*  46 */     this.easeOut = other.easeOut;
/*  47 */     this.offset = other.offset;
/*  48 */     this.rotation = other.rotation;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CameraShakeConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/*  53 */     CameraShakeConfig obj = new CameraShakeConfig();
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     obj.duration = buf.getFloatLE(offset + 1);
/*  56 */     obj.startTime = buf.getFloatLE(offset + 5);
/*  57 */     obj.continuous = (buf.getByte(offset + 9) != 0);
/*  58 */     if ((nullBits & 0x1) != 0) obj.easeIn = EasingConfig.deserialize(buf, offset + 10); 
/*  59 */     if ((nullBits & 0x2) != 0) obj.easeOut = EasingConfig.deserialize(buf, offset + 15);
/*     */     
/*  61 */     if ((nullBits & 0x4) != 0) {
/*  62 */       int varPos0 = offset + 28 + buf.getIntLE(offset + 20);
/*  63 */       obj.offset = OffsetNoise.deserialize(buf, varPos0);
/*     */     } 
/*  65 */     if ((nullBits & 0x8) != 0) {
/*  66 */       int varPos1 = offset + 28 + buf.getIntLE(offset + 24);
/*  67 */       obj.rotation = RotationNoise.deserialize(buf, varPos1);
/*     */     } 
/*     */     
/*  70 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  74 */     byte nullBits = buf.getByte(offset);
/*  75 */     int maxEnd = 28;
/*  76 */     if ((nullBits & 0x4) != 0) {
/*  77 */       int fieldOffset0 = buf.getIntLE(offset + 20);
/*  78 */       int pos0 = offset + 28 + fieldOffset0;
/*  79 */       pos0 += OffsetNoise.computeBytesConsumed(buf, pos0);
/*  80 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  82 */     if ((nullBits & 0x8) != 0) {
/*  83 */       int fieldOffset1 = buf.getIntLE(offset + 24);
/*  84 */       int pos1 = offset + 28 + fieldOffset1;
/*  85 */       pos1 += RotationNoise.computeBytesConsumed(buf, pos1);
/*  86 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  88 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  93 */     int startPos = buf.writerIndex();
/*  94 */     byte nullBits = 0;
/*  95 */     if (this.easeIn != null) nullBits = (byte)(nullBits | 0x1); 
/*  96 */     if (this.easeOut != null) nullBits = (byte)(nullBits | 0x2); 
/*  97 */     if (this.offset != null) nullBits = (byte)(nullBits | 0x4); 
/*  98 */     if (this.rotation != null) nullBits = (byte)(nullBits | 0x8); 
/*  99 */     buf.writeByte(nullBits);
/*     */     
/* 101 */     buf.writeFloatLE(this.duration);
/* 102 */     buf.writeFloatLE(this.startTime);
/* 103 */     buf.writeByte(this.continuous ? 1 : 0);
/* 104 */     if (this.easeIn != null) { this.easeIn.serialize(buf); } else { buf.writeZero(5); }
/* 105 */      if (this.easeOut != null) { this.easeOut.serialize(buf); } else { buf.writeZero(5); }
/*     */     
/* 107 */     int offsetOffsetSlot = buf.writerIndex();
/* 108 */     buf.writeIntLE(0);
/* 109 */     int rotationOffsetSlot = buf.writerIndex();
/* 110 */     buf.writeIntLE(0);
/*     */     
/* 112 */     int varBlockStart = buf.writerIndex();
/* 113 */     if (this.offset != null) {
/* 114 */       buf.setIntLE(offsetOffsetSlot, buf.writerIndex() - varBlockStart);
/* 115 */       this.offset.serialize(buf);
/*     */     } else {
/* 117 */       buf.setIntLE(offsetOffsetSlot, -1);
/*     */     } 
/* 119 */     if (this.rotation != null) {
/* 120 */       buf.setIntLE(rotationOffsetSlot, buf.writerIndex() - varBlockStart);
/* 121 */       this.rotation.serialize(buf);
/*     */     } else {
/* 123 */       buf.setIntLE(rotationOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 129 */     int size = 28;
/* 130 */     if (this.offset != null) size += this.offset.computeSize(); 
/* 131 */     if (this.rotation != null) size += this.rotation.computeSize();
/*     */     
/* 133 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 137 */     if (buffer.readableBytes() - offset < 28) {
/* 138 */       return ValidationResult.error("Buffer too small: expected at least 28 bytes");
/*     */     }
/*     */     
/* 141 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 144 */     if ((nullBits & 0x4) != 0) {
/* 145 */       int offsetOffset = buffer.getIntLE(offset + 20);
/* 146 */       if (offsetOffset < 0) {
/* 147 */         return ValidationResult.error("Invalid offset for Offset");
/*     */       }
/* 149 */       int pos = offset + 28 + offsetOffset;
/* 150 */       if (pos >= buffer.writerIndex()) {
/* 151 */         return ValidationResult.error("Offset out of bounds for Offset");
/*     */       }
/* 153 */       ValidationResult offsetResult = OffsetNoise.validateStructure(buffer, pos);
/* 154 */       if (!offsetResult.isValid()) {
/* 155 */         return ValidationResult.error("Invalid Offset: " + offsetResult.error());
/*     */       }
/* 157 */       pos += OffsetNoise.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 160 */     if ((nullBits & 0x8) != 0) {
/* 161 */       int rotationOffset = buffer.getIntLE(offset + 24);
/* 162 */       if (rotationOffset < 0) {
/* 163 */         return ValidationResult.error("Invalid offset for Rotation");
/*     */       }
/* 165 */       int pos = offset + 28 + rotationOffset;
/* 166 */       if (pos >= buffer.writerIndex()) {
/* 167 */         return ValidationResult.error("Offset out of bounds for Rotation");
/*     */       }
/* 169 */       ValidationResult rotationResult = RotationNoise.validateStructure(buffer, pos);
/* 170 */       if (!rotationResult.isValid()) {
/* 171 */         return ValidationResult.error("Invalid Rotation: " + rotationResult.error());
/*     */       }
/* 173 */       pos += RotationNoise.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 175 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CameraShakeConfig clone() {
/* 179 */     CameraShakeConfig copy = new CameraShakeConfig();
/* 180 */     copy.duration = this.duration;
/* 181 */     copy.startTime = this.startTime;
/* 182 */     copy.continuous = this.continuous;
/* 183 */     copy.easeIn = (this.easeIn != null) ? this.easeIn.clone() : null;
/* 184 */     copy.easeOut = (this.easeOut != null) ? this.easeOut.clone() : null;
/* 185 */     copy.offset = (this.offset != null) ? this.offset.clone() : null;
/* 186 */     copy.rotation = (this.rotation != null) ? this.rotation.clone() : null;
/* 187 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CameraShakeConfig other;
/* 193 */     if (this == obj) return true; 
/* 194 */     if (obj instanceof CameraShakeConfig) { other = (CameraShakeConfig)obj; } else { return false; }
/* 195 */      return (this.duration == other.duration && this.startTime == other.startTime && this.continuous == other.continuous && Objects.equals(this.easeIn, other.easeIn) && Objects.equals(this.easeOut, other.easeOut) && Objects.equals(this.offset, other.offset) && Objects.equals(this.rotation, other.rotation));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 200 */     return Objects.hash(new Object[] { Float.valueOf(this.duration), Float.valueOf(this.startTime), Boolean.valueOf(this.continuous), this.easeIn, this.easeOut, this.offset, this.rotation });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\CameraShakeConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */