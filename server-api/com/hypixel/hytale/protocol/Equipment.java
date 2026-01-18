/*     */ package com.hypixel.hytale.protocol;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class Equipment {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String[] armorIds;
/*     */   @Nullable
/*     */   public String rightHandItemId;
/*     */   @Nullable
/*     */   public String leftHandItemId;
/*     */   
/*     */   public Equipment() {}
/*     */   
/*     */   public Equipment(@Nullable String[] armorIds, @Nullable String rightHandItemId, @Nullable String leftHandItemId) {
/*  28 */     this.armorIds = armorIds;
/*  29 */     this.rightHandItemId = rightHandItemId;
/*  30 */     this.leftHandItemId = leftHandItemId;
/*     */   }
/*     */   
/*     */   public Equipment(@Nonnull Equipment other) {
/*  34 */     this.armorIds = other.armorIds;
/*  35 */     this.rightHandItemId = other.rightHandItemId;
/*  36 */     this.leftHandItemId = other.leftHandItemId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Equipment deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     Equipment obj = new Equipment();
/*  42 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  44 */     if ((nullBits & 0x1) != 0) {
/*  45 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 1);
/*  46 */       int armorIdsCount = VarInt.peek(buf, varPos0);
/*  47 */       if (armorIdsCount < 0) throw ProtocolException.negativeLength("ArmorIds", armorIdsCount); 
/*  48 */       if (armorIdsCount > 4096000) throw ProtocolException.arrayTooLong("ArmorIds", armorIdsCount, 4096000); 
/*  49 */       int varIntLen = VarInt.length(buf, varPos0);
/*  50 */       if ((varPos0 + varIntLen) + armorIdsCount * 1L > buf.readableBytes())
/*  51 */         throw ProtocolException.bufferTooSmall("ArmorIds", varPos0 + varIntLen + armorIdsCount * 1, buf.readableBytes()); 
/*  52 */       obj.armorIds = new String[armorIdsCount];
/*  53 */       int elemPos = varPos0 + varIntLen;
/*  54 */       for (int i = 0; i < armorIdsCount; i++) {
/*  55 */         int strLen = VarInt.peek(buf, elemPos);
/*  56 */         if (strLen < 0) throw ProtocolException.negativeLength("armorIds[" + i + "]", strLen); 
/*  57 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("armorIds[" + i + "]", strLen, 4096000); 
/*  58 */         int strVarLen = VarInt.length(buf, elemPos);
/*  59 */         obj.armorIds[i] = PacketIO.readVarString(buf, elemPos);
/*  60 */         elemPos += strVarLen + strLen;
/*     */       } 
/*     */     } 
/*  63 */     if ((nullBits & 0x2) != 0) {
/*  64 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 5);
/*  65 */       int rightHandItemIdLen = VarInt.peek(buf, varPos1);
/*  66 */       if (rightHandItemIdLen < 0) throw ProtocolException.negativeLength("RightHandItemId", rightHandItemIdLen); 
/*  67 */       if (rightHandItemIdLen > 4096000) throw ProtocolException.stringTooLong("RightHandItemId", rightHandItemIdLen, 4096000); 
/*  68 */       obj.rightHandItemId = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  70 */     if ((nullBits & 0x4) != 0) {
/*  71 */       int varPos2 = offset + 13 + buf.getIntLE(offset + 9);
/*  72 */       int leftHandItemIdLen = VarInt.peek(buf, varPos2);
/*  73 */       if (leftHandItemIdLen < 0) throw ProtocolException.negativeLength("LeftHandItemId", leftHandItemIdLen); 
/*  74 */       if (leftHandItemIdLen > 4096000) throw ProtocolException.stringTooLong("LeftHandItemId", leftHandItemIdLen, 4096000); 
/*  75 */       obj.leftHandItemId = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  78 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  82 */     byte nullBits = buf.getByte(offset);
/*  83 */     int maxEnd = 13;
/*  84 */     if ((nullBits & 0x1) != 0) {
/*  85 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  86 */       int pos0 = offset + 13 + fieldOffset0;
/*  87 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/*  88 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl; i++; }
/*  89 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  91 */     if ((nullBits & 0x2) != 0) {
/*  92 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  93 */       int pos1 = offset + 13 + fieldOffset1;
/*  94 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  95 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  97 */     if ((nullBits & 0x4) != 0) {
/*  98 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/*  99 */       int pos2 = offset + 13 + fieldOffset2;
/* 100 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 101 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 103 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 108 */     int startPos = buf.writerIndex();
/* 109 */     byte nullBits = 0;
/* 110 */     if (this.armorIds != null) nullBits = (byte)(nullBits | 0x1); 
/* 111 */     if (this.rightHandItemId != null) nullBits = (byte)(nullBits | 0x2); 
/* 112 */     if (this.leftHandItemId != null) nullBits = (byte)(nullBits | 0x4); 
/* 113 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 116 */     int armorIdsOffsetSlot = buf.writerIndex();
/* 117 */     buf.writeIntLE(0);
/* 118 */     int rightHandItemIdOffsetSlot = buf.writerIndex();
/* 119 */     buf.writeIntLE(0);
/* 120 */     int leftHandItemIdOffsetSlot = buf.writerIndex();
/* 121 */     buf.writeIntLE(0);
/*     */     
/* 123 */     int varBlockStart = buf.writerIndex();
/* 124 */     if (this.armorIds != null) {
/* 125 */       buf.setIntLE(armorIdsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 126 */       if (this.armorIds.length > 4096000) throw ProtocolException.arrayTooLong("ArmorIds", this.armorIds.length, 4096000);  VarInt.write(buf, this.armorIds.length); for (String item : this.armorIds) PacketIO.writeVarString(buf, item, 4096000); 
/*     */     } else {
/* 128 */       buf.setIntLE(armorIdsOffsetSlot, -1);
/*     */     } 
/* 130 */     if (this.rightHandItemId != null) {
/* 131 */       buf.setIntLE(rightHandItemIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 132 */       PacketIO.writeVarString(buf, this.rightHandItemId, 4096000);
/*     */     } else {
/* 134 */       buf.setIntLE(rightHandItemIdOffsetSlot, -1);
/*     */     } 
/* 136 */     if (this.leftHandItemId != null) {
/* 137 */       buf.setIntLE(leftHandItemIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 138 */       PacketIO.writeVarString(buf, this.leftHandItemId, 4096000);
/*     */     } else {
/* 140 */       buf.setIntLE(leftHandItemIdOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 146 */     int size = 13;
/* 147 */     if (this.armorIds != null) {
/* 148 */       int armorIdsSize = 0;
/* 149 */       for (String elem : this.armorIds) armorIdsSize += PacketIO.stringSize(elem); 
/* 150 */       size += VarInt.size(this.armorIds.length) + armorIdsSize;
/*     */     } 
/* 152 */     if (this.rightHandItemId != null) size += PacketIO.stringSize(this.rightHandItemId); 
/* 153 */     if (this.leftHandItemId != null) size += PacketIO.stringSize(this.leftHandItemId);
/*     */     
/* 155 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 159 */     if (buffer.readableBytes() - offset < 13) {
/* 160 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 163 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 166 */     if ((nullBits & 0x1) != 0) {
/* 167 */       int armorIdsOffset = buffer.getIntLE(offset + 1);
/* 168 */       if (armorIdsOffset < 0) {
/* 169 */         return ValidationResult.error("Invalid offset for ArmorIds");
/*     */       }
/* 171 */       int pos = offset + 13 + armorIdsOffset;
/* 172 */       if (pos >= buffer.writerIndex()) {
/* 173 */         return ValidationResult.error("Offset out of bounds for ArmorIds");
/*     */       }
/* 175 */       int armorIdsCount = VarInt.peek(buffer, pos);
/* 176 */       if (armorIdsCount < 0) {
/* 177 */         return ValidationResult.error("Invalid array count for ArmorIds");
/*     */       }
/* 179 */       if (armorIdsCount > 4096000) {
/* 180 */         return ValidationResult.error("ArmorIds exceeds max length 4096000");
/*     */       }
/* 182 */       pos += VarInt.length(buffer, pos);
/* 183 */       for (int i = 0; i < armorIdsCount; i++) {
/* 184 */         int strLen = VarInt.peek(buffer, pos);
/* 185 */         if (strLen < 0) {
/* 186 */           return ValidationResult.error("Invalid string length in ArmorIds");
/*     */         }
/* 188 */         pos += VarInt.length(buffer, pos);
/* 189 */         pos += strLen;
/* 190 */         if (pos > buffer.writerIndex()) {
/* 191 */           return ValidationResult.error("Buffer overflow reading string in ArmorIds");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 196 */     if ((nullBits & 0x2) != 0) {
/* 197 */       int rightHandItemIdOffset = buffer.getIntLE(offset + 5);
/* 198 */       if (rightHandItemIdOffset < 0) {
/* 199 */         return ValidationResult.error("Invalid offset for RightHandItemId");
/*     */       }
/* 201 */       int pos = offset + 13 + rightHandItemIdOffset;
/* 202 */       if (pos >= buffer.writerIndex()) {
/* 203 */         return ValidationResult.error("Offset out of bounds for RightHandItemId");
/*     */       }
/* 205 */       int rightHandItemIdLen = VarInt.peek(buffer, pos);
/* 206 */       if (rightHandItemIdLen < 0) {
/* 207 */         return ValidationResult.error("Invalid string length for RightHandItemId");
/*     */       }
/* 209 */       if (rightHandItemIdLen > 4096000) {
/* 210 */         return ValidationResult.error("RightHandItemId exceeds max length 4096000");
/*     */       }
/* 212 */       pos += VarInt.length(buffer, pos);
/* 213 */       pos += rightHandItemIdLen;
/* 214 */       if (pos > buffer.writerIndex()) {
/* 215 */         return ValidationResult.error("Buffer overflow reading RightHandItemId");
/*     */       }
/*     */     } 
/*     */     
/* 219 */     if ((nullBits & 0x4) != 0) {
/* 220 */       int leftHandItemIdOffset = buffer.getIntLE(offset + 9);
/* 221 */       if (leftHandItemIdOffset < 0) {
/* 222 */         return ValidationResult.error("Invalid offset for LeftHandItemId");
/*     */       }
/* 224 */       int pos = offset + 13 + leftHandItemIdOffset;
/* 225 */       if (pos >= buffer.writerIndex()) {
/* 226 */         return ValidationResult.error("Offset out of bounds for LeftHandItemId");
/*     */       }
/* 228 */       int leftHandItemIdLen = VarInt.peek(buffer, pos);
/* 229 */       if (leftHandItemIdLen < 0) {
/* 230 */         return ValidationResult.error("Invalid string length for LeftHandItemId");
/*     */       }
/* 232 */       if (leftHandItemIdLen > 4096000) {
/* 233 */         return ValidationResult.error("LeftHandItemId exceeds max length 4096000");
/*     */       }
/* 235 */       pos += VarInt.length(buffer, pos);
/* 236 */       pos += leftHandItemIdLen;
/* 237 */       if (pos > buffer.writerIndex()) {
/* 238 */         return ValidationResult.error("Buffer overflow reading LeftHandItemId");
/*     */       }
/*     */     } 
/* 241 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Equipment clone() {
/* 245 */     Equipment copy = new Equipment();
/* 246 */     copy.armorIds = (this.armorIds != null) ? Arrays.<String>copyOf(this.armorIds, this.armorIds.length) : null;
/* 247 */     copy.rightHandItemId = this.rightHandItemId;
/* 248 */     copy.leftHandItemId = this.leftHandItemId;
/* 249 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Equipment other;
/* 255 */     if (this == obj) return true; 
/* 256 */     if (obj instanceof Equipment) { other = (Equipment)obj; } else { return false; }
/* 257 */      return (Arrays.equals((Object[])this.armorIds, (Object[])other.armorIds) && Objects.equals(this.rightHandItemId, other.rightHandItemId) && Objects.equals(this.leftHandItemId, other.leftHandItemId));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 262 */     int result = 1;
/* 263 */     result = 31 * result + Arrays.hashCode((Object[])this.armorIds);
/* 264 */     result = 31 * result + Objects.hashCode(this.rightHandItemId);
/* 265 */     result = 31 * result + Objects.hashCode(this.leftHandItemId);
/* 266 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Equipment.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */