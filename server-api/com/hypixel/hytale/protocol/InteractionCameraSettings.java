/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class InteractionCameraSettings
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 237568019;
/*     */   @Nullable
/*     */   public InteractionCamera[] firstPerson;
/*     */   @Nullable
/*     */   public InteractionCamera[] thirdPerson;
/*     */   
/*     */   public InteractionCameraSettings() {}
/*     */   
/*     */   public InteractionCameraSettings(@Nullable InteractionCamera[] firstPerson, @Nullable InteractionCamera[] thirdPerson) {
/*  27 */     this.firstPerson = firstPerson;
/*  28 */     this.thirdPerson = thirdPerson;
/*     */   }
/*     */   
/*     */   public InteractionCameraSettings(@Nonnull InteractionCameraSettings other) {
/*  32 */     this.firstPerson = other.firstPerson;
/*  33 */     this.thirdPerson = other.thirdPerson;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static InteractionCameraSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     InteractionCameraSettings obj = new InteractionCameraSettings();
/*  39 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  41 */     if ((nullBits & 0x1) != 0) {
/*  42 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  43 */       int firstPersonCount = VarInt.peek(buf, varPos0);
/*  44 */       if (firstPersonCount < 0) throw ProtocolException.negativeLength("FirstPerson", firstPersonCount); 
/*  45 */       if (firstPersonCount > 4096000) throw ProtocolException.arrayTooLong("FirstPerson", firstPersonCount, 4096000); 
/*  46 */       int varIntLen = VarInt.length(buf, varPos0);
/*  47 */       if ((varPos0 + varIntLen) + firstPersonCount * 29L > buf.readableBytes())
/*  48 */         throw ProtocolException.bufferTooSmall("FirstPerson", varPos0 + varIntLen + firstPersonCount * 29, buf.readableBytes()); 
/*  49 */       obj.firstPerson = new InteractionCamera[firstPersonCount];
/*  50 */       int elemPos = varPos0 + varIntLen;
/*  51 */       for (int i = 0; i < firstPersonCount; i++) {
/*  52 */         obj.firstPerson[i] = InteractionCamera.deserialize(buf, elemPos);
/*  53 */         elemPos += InteractionCamera.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  56 */     if ((nullBits & 0x2) != 0) {
/*  57 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  58 */       int thirdPersonCount = VarInt.peek(buf, varPos1);
/*  59 */       if (thirdPersonCount < 0) throw ProtocolException.negativeLength("ThirdPerson", thirdPersonCount); 
/*  60 */       if (thirdPersonCount > 4096000) throw ProtocolException.arrayTooLong("ThirdPerson", thirdPersonCount, 4096000); 
/*  61 */       int varIntLen = VarInt.length(buf, varPos1);
/*  62 */       if ((varPos1 + varIntLen) + thirdPersonCount * 29L > buf.readableBytes())
/*  63 */         throw ProtocolException.bufferTooSmall("ThirdPerson", varPos1 + varIntLen + thirdPersonCount * 29, buf.readableBytes()); 
/*  64 */       obj.thirdPerson = new InteractionCamera[thirdPersonCount];
/*  65 */       int elemPos = varPos1 + varIntLen;
/*  66 */       for (int i = 0; i < thirdPersonCount; i++) {
/*  67 */         obj.thirdPerson[i] = InteractionCamera.deserialize(buf, elemPos);
/*  68 */         elemPos += InteractionCamera.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  76 */     byte nullBits = buf.getByte(offset);
/*  77 */     int maxEnd = 9;
/*  78 */     if ((nullBits & 0x1) != 0) {
/*  79 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  80 */       int pos0 = offset + 9 + fieldOffset0;
/*  81 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/*  82 */       for (int i = 0; i < arrLen; ) { pos0 += InteractionCamera.computeBytesConsumed(buf, pos0); i++; }
/*  83 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  85 */     if ((nullBits & 0x2) != 0) {
/*  86 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  87 */       int pos1 = offset + 9 + fieldOffset1;
/*  88 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  89 */       for (int i = 0; i < arrLen; ) { pos1 += InteractionCamera.computeBytesConsumed(buf, pos1); i++; }
/*  90 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  92 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  97 */     int startPos = buf.writerIndex();
/*  98 */     byte nullBits = 0;
/*  99 */     if (this.firstPerson != null) nullBits = (byte)(nullBits | 0x1); 
/* 100 */     if (this.thirdPerson != null) nullBits = (byte)(nullBits | 0x2); 
/* 101 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 104 */     int firstPersonOffsetSlot = buf.writerIndex();
/* 105 */     buf.writeIntLE(0);
/* 106 */     int thirdPersonOffsetSlot = buf.writerIndex();
/* 107 */     buf.writeIntLE(0);
/*     */     
/* 109 */     int varBlockStart = buf.writerIndex();
/* 110 */     if (this.firstPerson != null) {
/* 111 */       buf.setIntLE(firstPersonOffsetSlot, buf.writerIndex() - varBlockStart);
/* 112 */       if (this.firstPerson.length > 4096000) throw ProtocolException.arrayTooLong("FirstPerson", this.firstPerson.length, 4096000);  VarInt.write(buf, this.firstPerson.length); for (InteractionCamera item : this.firstPerson) item.serialize(buf); 
/*     */     } else {
/* 114 */       buf.setIntLE(firstPersonOffsetSlot, -1);
/*     */     } 
/* 116 */     if (this.thirdPerson != null) {
/* 117 */       buf.setIntLE(thirdPersonOffsetSlot, buf.writerIndex() - varBlockStart);
/* 118 */       if (this.thirdPerson.length > 4096000) throw ProtocolException.arrayTooLong("ThirdPerson", this.thirdPerson.length, 4096000);  VarInt.write(buf, this.thirdPerson.length); for (InteractionCamera item : this.thirdPerson) item.serialize(buf); 
/*     */     } else {
/* 120 */       buf.setIntLE(thirdPersonOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 126 */     int size = 9;
/* 127 */     if (this.firstPerson != null) size += VarInt.size(this.firstPerson.length) + this.firstPerson.length * 29; 
/* 128 */     if (this.thirdPerson != null) size += VarInt.size(this.thirdPerson.length) + this.thirdPerson.length * 29;
/*     */     
/* 130 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 134 */     if (buffer.readableBytes() - offset < 9) {
/* 135 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 138 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 141 */     if ((nullBits & 0x1) != 0) {
/* 142 */       int firstPersonOffset = buffer.getIntLE(offset + 1);
/* 143 */       if (firstPersonOffset < 0) {
/* 144 */         return ValidationResult.error("Invalid offset for FirstPerson");
/*     */       }
/* 146 */       int pos = offset + 9 + firstPersonOffset;
/* 147 */       if (pos >= buffer.writerIndex()) {
/* 148 */         return ValidationResult.error("Offset out of bounds for FirstPerson");
/*     */       }
/* 150 */       int firstPersonCount = VarInt.peek(buffer, pos);
/* 151 */       if (firstPersonCount < 0) {
/* 152 */         return ValidationResult.error("Invalid array count for FirstPerson");
/*     */       }
/* 154 */       if (firstPersonCount > 4096000) {
/* 155 */         return ValidationResult.error("FirstPerson exceeds max length 4096000");
/*     */       }
/* 157 */       pos += VarInt.length(buffer, pos);
/* 158 */       pos += firstPersonCount * 29;
/* 159 */       if (pos > buffer.writerIndex()) {
/* 160 */         return ValidationResult.error("Buffer overflow reading FirstPerson");
/*     */       }
/*     */     } 
/*     */     
/* 164 */     if ((nullBits & 0x2) != 0) {
/* 165 */       int thirdPersonOffset = buffer.getIntLE(offset + 5);
/* 166 */       if (thirdPersonOffset < 0) {
/* 167 */         return ValidationResult.error("Invalid offset for ThirdPerson");
/*     */       }
/* 169 */       int pos = offset + 9 + thirdPersonOffset;
/* 170 */       if (pos >= buffer.writerIndex()) {
/* 171 */         return ValidationResult.error("Offset out of bounds for ThirdPerson");
/*     */       }
/* 173 */       int thirdPersonCount = VarInt.peek(buffer, pos);
/* 174 */       if (thirdPersonCount < 0) {
/* 175 */         return ValidationResult.error("Invalid array count for ThirdPerson");
/*     */       }
/* 177 */       if (thirdPersonCount > 4096000) {
/* 178 */         return ValidationResult.error("ThirdPerson exceeds max length 4096000");
/*     */       }
/* 180 */       pos += VarInt.length(buffer, pos);
/* 181 */       pos += thirdPersonCount * 29;
/* 182 */       if (pos > buffer.writerIndex()) {
/* 183 */         return ValidationResult.error("Buffer overflow reading ThirdPerson");
/*     */       }
/*     */     } 
/* 186 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public InteractionCameraSettings clone() {
/* 190 */     InteractionCameraSettings copy = new InteractionCameraSettings();
/* 191 */     copy.firstPerson = (this.firstPerson != null) ? (InteractionCamera[])Arrays.<InteractionCamera>stream(this.firstPerson).map(e -> e.clone()).toArray(x$0 -> new InteractionCamera[x$0]) : null;
/* 192 */     copy.thirdPerson = (this.thirdPerson != null) ? (InteractionCamera[])Arrays.<InteractionCamera>stream(this.thirdPerson).map(e -> e.clone()).toArray(x$0 -> new InteractionCamera[x$0]) : null;
/* 193 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     InteractionCameraSettings other;
/* 199 */     if (this == obj) return true; 
/* 200 */     if (obj instanceof InteractionCameraSettings) { other = (InteractionCameraSettings)obj; } else { return false; }
/* 201 */      return (Arrays.equals((Object[])this.firstPerson, (Object[])other.firstPerson) && Arrays.equals((Object[])this.thirdPerson, (Object[])other.thirdPerson));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 206 */     int result = 1;
/* 207 */     result = 31 * result + Arrays.hashCode((Object[])this.firstPerson);
/* 208 */     result = 31 * result + Arrays.hashCode((Object[])this.thirdPerson);
/* 209 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\InteractionCameraSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */