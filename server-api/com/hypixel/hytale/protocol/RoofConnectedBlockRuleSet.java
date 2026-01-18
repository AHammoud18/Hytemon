/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class RoofConnectedBlockRuleSet {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 21;
/*     */   public static final int MAX_SIZE = 49152078;
/*     */   @Nullable
/*     */   public StairConnectedBlockRuleSet regular;
/*     */   @Nullable
/*     */   public StairConnectedBlockRuleSet hollow;
/*     */   public int topperBlockId;
/*     */   public int width;
/*     */   @Nullable
/*     */   public String materialName;
/*     */   
/*     */   public RoofConnectedBlockRuleSet() {}
/*     */   
/*     */   public RoofConnectedBlockRuleSet(@Nullable StairConnectedBlockRuleSet regular, @Nullable StairConnectedBlockRuleSet hollow, int topperBlockId, int width, @Nullable String materialName) {
/*  30 */     this.regular = regular;
/*  31 */     this.hollow = hollow;
/*  32 */     this.topperBlockId = topperBlockId;
/*  33 */     this.width = width;
/*  34 */     this.materialName = materialName;
/*     */   }
/*     */   
/*     */   public RoofConnectedBlockRuleSet(@Nonnull RoofConnectedBlockRuleSet other) {
/*  38 */     this.regular = other.regular;
/*  39 */     this.hollow = other.hollow;
/*  40 */     this.topperBlockId = other.topperBlockId;
/*  41 */     this.width = other.width;
/*  42 */     this.materialName = other.materialName;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static RoofConnectedBlockRuleSet deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     RoofConnectedBlockRuleSet obj = new RoofConnectedBlockRuleSet();
/*  48 */     byte nullBits = buf.getByte(offset);
/*  49 */     obj.topperBlockId = buf.getIntLE(offset + 1);
/*  50 */     obj.width = buf.getIntLE(offset + 5);
/*     */     
/*  52 */     if ((nullBits & 0x1) != 0) {
/*  53 */       int varPos0 = offset + 21 + buf.getIntLE(offset + 9);
/*  54 */       obj.regular = StairConnectedBlockRuleSet.deserialize(buf, varPos0);
/*     */     } 
/*  56 */     if ((nullBits & 0x2) != 0) {
/*  57 */       int varPos1 = offset + 21 + buf.getIntLE(offset + 13);
/*  58 */       obj.hollow = StairConnectedBlockRuleSet.deserialize(buf, varPos1);
/*     */     } 
/*  60 */     if ((nullBits & 0x4) != 0) {
/*  61 */       int varPos2 = offset + 21 + buf.getIntLE(offset + 17);
/*  62 */       int materialNameLen = VarInt.peek(buf, varPos2);
/*  63 */       if (materialNameLen < 0) throw ProtocolException.negativeLength("MaterialName", materialNameLen); 
/*  64 */       if (materialNameLen > 4096000) throw ProtocolException.stringTooLong("MaterialName", materialNameLen, 4096000); 
/*  65 */       obj.materialName = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     byte nullBits = buf.getByte(offset);
/*  73 */     int maxEnd = 21;
/*  74 */     if ((nullBits & 0x1) != 0) {
/*  75 */       int fieldOffset0 = buf.getIntLE(offset + 9);
/*  76 */       int pos0 = offset + 21 + fieldOffset0;
/*  77 */       pos0 += StairConnectedBlockRuleSet.computeBytesConsumed(buf, pos0);
/*  78 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  80 */     if ((nullBits & 0x2) != 0) {
/*  81 */       int fieldOffset1 = buf.getIntLE(offset + 13);
/*  82 */       int pos1 = offset + 21 + fieldOffset1;
/*  83 */       pos1 += StairConnectedBlockRuleSet.computeBytesConsumed(buf, pos1);
/*  84 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  86 */     if ((nullBits & 0x4) != 0) {
/*  87 */       int fieldOffset2 = buf.getIntLE(offset + 17);
/*  88 */       int pos2 = offset + 21 + fieldOffset2;
/*  89 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/*  90 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/*  92 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  97 */     int startPos = buf.writerIndex();
/*  98 */     byte nullBits = 0;
/*  99 */     if (this.regular != null) nullBits = (byte)(nullBits | 0x1); 
/* 100 */     if (this.hollow != null) nullBits = (byte)(nullBits | 0x2); 
/* 101 */     if (this.materialName != null) nullBits = (byte)(nullBits | 0x4); 
/* 102 */     buf.writeByte(nullBits);
/*     */     
/* 104 */     buf.writeIntLE(this.topperBlockId);
/* 105 */     buf.writeIntLE(this.width);
/*     */     
/* 107 */     int regularOffsetSlot = buf.writerIndex();
/* 108 */     buf.writeIntLE(0);
/* 109 */     int hollowOffsetSlot = buf.writerIndex();
/* 110 */     buf.writeIntLE(0);
/* 111 */     int materialNameOffsetSlot = buf.writerIndex();
/* 112 */     buf.writeIntLE(0);
/*     */     
/* 114 */     int varBlockStart = buf.writerIndex();
/* 115 */     if (this.regular != null) {
/* 116 */       buf.setIntLE(regularOffsetSlot, buf.writerIndex() - varBlockStart);
/* 117 */       this.regular.serialize(buf);
/*     */     } else {
/* 119 */       buf.setIntLE(regularOffsetSlot, -1);
/*     */     } 
/* 121 */     if (this.hollow != null) {
/* 122 */       buf.setIntLE(hollowOffsetSlot, buf.writerIndex() - varBlockStart);
/* 123 */       this.hollow.serialize(buf);
/*     */     } else {
/* 125 */       buf.setIntLE(hollowOffsetSlot, -1);
/*     */     } 
/* 127 */     if (this.materialName != null) {
/* 128 */       buf.setIntLE(materialNameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 129 */       PacketIO.writeVarString(buf, this.materialName, 4096000);
/*     */     } else {
/* 131 */       buf.setIntLE(materialNameOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 137 */     int size = 21;
/* 138 */     if (this.regular != null) size += this.regular.computeSize(); 
/* 139 */     if (this.hollow != null) size += this.hollow.computeSize(); 
/* 140 */     if (this.materialName != null) size += PacketIO.stringSize(this.materialName);
/*     */     
/* 142 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 146 */     if (buffer.readableBytes() - offset < 21) {
/* 147 */       return ValidationResult.error("Buffer too small: expected at least 21 bytes");
/*     */     }
/*     */     
/* 150 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 153 */     if ((nullBits & 0x1) != 0) {
/* 154 */       int regularOffset = buffer.getIntLE(offset + 9);
/* 155 */       if (regularOffset < 0) {
/* 156 */         return ValidationResult.error("Invalid offset for Regular");
/*     */       }
/* 158 */       int pos = offset + 21 + regularOffset;
/* 159 */       if (pos >= buffer.writerIndex()) {
/* 160 */         return ValidationResult.error("Offset out of bounds for Regular");
/*     */       }
/* 162 */       ValidationResult regularResult = StairConnectedBlockRuleSet.validateStructure(buffer, pos);
/* 163 */       if (!regularResult.isValid()) {
/* 164 */         return ValidationResult.error("Invalid Regular: " + regularResult.error());
/*     */       }
/* 166 */       pos += StairConnectedBlockRuleSet.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 169 */     if ((nullBits & 0x2) != 0) {
/* 170 */       int hollowOffset = buffer.getIntLE(offset + 13);
/* 171 */       if (hollowOffset < 0) {
/* 172 */         return ValidationResult.error("Invalid offset for Hollow");
/*     */       }
/* 174 */       int pos = offset + 21 + hollowOffset;
/* 175 */       if (pos >= buffer.writerIndex()) {
/* 176 */         return ValidationResult.error("Offset out of bounds for Hollow");
/*     */       }
/* 178 */       ValidationResult hollowResult = StairConnectedBlockRuleSet.validateStructure(buffer, pos);
/* 179 */       if (!hollowResult.isValid()) {
/* 180 */         return ValidationResult.error("Invalid Hollow: " + hollowResult.error());
/*     */       }
/* 182 */       pos += StairConnectedBlockRuleSet.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 185 */     if ((nullBits & 0x4) != 0) {
/* 186 */       int materialNameOffset = buffer.getIntLE(offset + 17);
/* 187 */       if (materialNameOffset < 0) {
/* 188 */         return ValidationResult.error("Invalid offset for MaterialName");
/*     */       }
/* 190 */       int pos = offset + 21 + materialNameOffset;
/* 191 */       if (pos >= buffer.writerIndex()) {
/* 192 */         return ValidationResult.error("Offset out of bounds for MaterialName");
/*     */       }
/* 194 */       int materialNameLen = VarInt.peek(buffer, pos);
/* 195 */       if (materialNameLen < 0) {
/* 196 */         return ValidationResult.error("Invalid string length for MaterialName");
/*     */       }
/* 198 */       if (materialNameLen > 4096000) {
/* 199 */         return ValidationResult.error("MaterialName exceeds max length 4096000");
/*     */       }
/* 201 */       pos += VarInt.length(buffer, pos);
/* 202 */       pos += materialNameLen;
/* 203 */       if (pos > buffer.writerIndex()) {
/* 204 */         return ValidationResult.error("Buffer overflow reading MaterialName");
/*     */       }
/*     */     } 
/* 207 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public RoofConnectedBlockRuleSet clone() {
/* 211 */     RoofConnectedBlockRuleSet copy = new RoofConnectedBlockRuleSet();
/* 212 */     copy.regular = (this.regular != null) ? this.regular.clone() : null;
/* 213 */     copy.hollow = (this.hollow != null) ? this.hollow.clone() : null;
/* 214 */     copy.topperBlockId = this.topperBlockId;
/* 215 */     copy.width = this.width;
/* 216 */     copy.materialName = this.materialName;
/* 217 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RoofConnectedBlockRuleSet other;
/* 223 */     if (this == obj) return true; 
/* 224 */     if (obj instanceof RoofConnectedBlockRuleSet) { other = (RoofConnectedBlockRuleSet)obj; } else { return false; }
/* 225 */      return (Objects.equals(this.regular, other.regular) && Objects.equals(this.hollow, other.hollow) && this.topperBlockId == other.topperBlockId && this.width == other.width && Objects.equals(this.materialName, other.materialName));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 230 */     return Objects.hash(new Object[] { this.regular, this.hollow, Integer.valueOf(this.topperBlockId), Integer.valueOf(this.width), this.materialName });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\RoofConnectedBlockRuleSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */