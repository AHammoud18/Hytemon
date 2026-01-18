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
/*     */ public class CameraShake
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 1130496177;
/*     */   @Nullable
/*     */   public CameraShakeConfig firstPerson;
/*     */   @Nullable
/*     */   public CameraShakeConfig thirdPerson;
/*     */   
/*     */   public CameraShake() {}
/*     */   
/*     */   public CameraShake(@Nullable CameraShakeConfig firstPerson, @Nullable CameraShakeConfig thirdPerson) {
/*  27 */     this.firstPerson = firstPerson;
/*  28 */     this.thirdPerson = thirdPerson;
/*     */   }
/*     */   
/*     */   public CameraShake(@Nonnull CameraShake other) {
/*  32 */     this.firstPerson = other.firstPerson;
/*  33 */     this.thirdPerson = other.thirdPerson;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CameraShake deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     CameraShake obj = new CameraShake();
/*  39 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  41 */     if ((nullBits & 0x1) != 0) {
/*  42 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  43 */       obj.firstPerson = CameraShakeConfig.deserialize(buf, varPos0);
/*     */     } 
/*  45 */     if ((nullBits & 0x2) != 0) {
/*  46 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  47 */       obj.thirdPerson = CameraShakeConfig.deserialize(buf, varPos1);
/*     */     } 
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int maxEnd = 9;
/*  56 */     if ((nullBits & 0x1) != 0) {
/*  57 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  58 */       int pos0 = offset + 9 + fieldOffset0;
/*  59 */       pos0 += CameraShakeConfig.computeBytesConsumed(buf, pos0);
/*  60 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  62 */     if ((nullBits & 0x2) != 0) {
/*  63 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  64 */       int pos1 = offset + 9 + fieldOffset1;
/*  65 */       pos1 += CameraShakeConfig.computeBytesConsumed(buf, pos1);
/*  66 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  68 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  73 */     int startPos = buf.writerIndex();
/*  74 */     byte nullBits = 0;
/*  75 */     if (this.firstPerson != null) nullBits = (byte)(nullBits | 0x1); 
/*  76 */     if (this.thirdPerson != null) nullBits = (byte)(nullBits | 0x2); 
/*  77 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  80 */     int firstPersonOffsetSlot = buf.writerIndex();
/*  81 */     buf.writeIntLE(0);
/*  82 */     int thirdPersonOffsetSlot = buf.writerIndex();
/*  83 */     buf.writeIntLE(0);
/*     */     
/*  85 */     int varBlockStart = buf.writerIndex();
/*  86 */     if (this.firstPerson != null) {
/*  87 */       buf.setIntLE(firstPersonOffsetSlot, buf.writerIndex() - varBlockStart);
/*  88 */       this.firstPerson.serialize(buf);
/*     */     } else {
/*  90 */       buf.setIntLE(firstPersonOffsetSlot, -1);
/*     */     } 
/*  92 */     if (this.thirdPerson != null) {
/*  93 */       buf.setIntLE(thirdPersonOffsetSlot, buf.writerIndex() - varBlockStart);
/*  94 */       this.thirdPerson.serialize(buf);
/*     */     } else {
/*  96 */       buf.setIntLE(thirdPersonOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 102 */     int size = 9;
/* 103 */     if (this.firstPerson != null) size += this.firstPerson.computeSize(); 
/* 104 */     if (this.thirdPerson != null) size += this.thirdPerson.computeSize();
/*     */     
/* 106 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 110 */     if (buffer.readableBytes() - offset < 9) {
/* 111 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 114 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 117 */     if ((nullBits & 0x1) != 0) {
/* 118 */       int firstPersonOffset = buffer.getIntLE(offset + 1);
/* 119 */       if (firstPersonOffset < 0) {
/* 120 */         return ValidationResult.error("Invalid offset for FirstPerson");
/*     */       }
/* 122 */       int pos = offset + 9 + firstPersonOffset;
/* 123 */       if (pos >= buffer.writerIndex()) {
/* 124 */         return ValidationResult.error("Offset out of bounds for FirstPerson");
/*     */       }
/* 126 */       ValidationResult firstPersonResult = CameraShakeConfig.validateStructure(buffer, pos);
/* 127 */       if (!firstPersonResult.isValid()) {
/* 128 */         return ValidationResult.error("Invalid FirstPerson: " + firstPersonResult.error());
/*     */       }
/* 130 */       pos += CameraShakeConfig.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 133 */     if ((nullBits & 0x2) != 0) {
/* 134 */       int thirdPersonOffset = buffer.getIntLE(offset + 5);
/* 135 */       if (thirdPersonOffset < 0) {
/* 136 */         return ValidationResult.error("Invalid offset for ThirdPerson");
/*     */       }
/* 138 */       int pos = offset + 9 + thirdPersonOffset;
/* 139 */       if (pos >= buffer.writerIndex()) {
/* 140 */         return ValidationResult.error("Offset out of bounds for ThirdPerson");
/*     */       }
/* 142 */       ValidationResult thirdPersonResult = CameraShakeConfig.validateStructure(buffer, pos);
/* 143 */       if (!thirdPersonResult.isValid()) {
/* 144 */         return ValidationResult.error("Invalid ThirdPerson: " + thirdPersonResult.error());
/*     */       }
/* 146 */       pos += CameraShakeConfig.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 148 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CameraShake clone() {
/* 152 */     CameraShake copy = new CameraShake();
/* 153 */     copy.firstPerson = (this.firstPerson != null) ? this.firstPerson.clone() : null;
/* 154 */     copy.thirdPerson = (this.thirdPerson != null) ? this.thirdPerson.clone() : null;
/* 155 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CameraShake other;
/* 161 */     if (this == obj) return true; 
/* 162 */     if (obj instanceof CameraShake) { other = (CameraShake)obj; } else { return false; }
/* 163 */      return (Objects.equals(this.firstPerson, other.firstPerson) && Objects.equals(this.thirdPerson, other.thirdPerson));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 168 */     return Objects.hash(new Object[] { this.firstPerson, this.thirdPerson });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\CameraShake.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */