/*     */ package com.hypixel.hytale.protocol;
/*     */ 
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
/*     */ public class BenchRequirement {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 14;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nonnull
/*  20 */   public BenchType type = BenchType.Crafting;
/*     */   
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public String[] categories;
/*     */   public int requiredTierLevel;
/*     */   
/*     */   public BenchRequirement(@Nonnull BenchType type, @Nullable String id, @Nullable String[] categories, int requiredTierLevel) {
/*  29 */     this.type = type;
/*  30 */     this.id = id;
/*  31 */     this.categories = categories;
/*  32 */     this.requiredTierLevel = requiredTierLevel;
/*     */   }
/*     */   
/*     */   public BenchRequirement(@Nonnull BenchRequirement other) {
/*  36 */     this.type = other.type;
/*  37 */     this.id = other.id;
/*  38 */     this.categories = other.categories;
/*  39 */     this.requiredTierLevel = other.requiredTierLevel;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BenchRequirement deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     BenchRequirement obj = new BenchRequirement();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.type = BenchType.fromValue(buf.getByte(offset + 1));
/*  47 */     obj.requiredTierLevel = buf.getIntLE(offset + 2);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 14 + buf.getIntLE(offset + 6);
/*  51 */       int idLen = VarInt.peek(buf, varPos0);
/*  52 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  53 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  54 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  56 */     if ((nullBits & 0x2) != 0) {
/*  57 */       int varPos1 = offset + 14 + buf.getIntLE(offset + 10);
/*  58 */       int categoriesCount = VarInt.peek(buf, varPos1);
/*  59 */       if (categoriesCount < 0) throw ProtocolException.negativeLength("Categories", categoriesCount); 
/*  60 */       if (categoriesCount > 4096000) throw ProtocolException.arrayTooLong("Categories", categoriesCount, 4096000); 
/*  61 */       int varIntLen = VarInt.length(buf, varPos1);
/*  62 */       if ((varPos1 + varIntLen) + categoriesCount * 1L > buf.readableBytes())
/*  63 */         throw ProtocolException.bufferTooSmall("Categories", varPos1 + varIntLen + categoriesCount * 1, buf.readableBytes()); 
/*  64 */       obj.categories = new String[categoriesCount];
/*  65 */       int elemPos = varPos1 + varIntLen;
/*  66 */       for (int i = 0; i < categoriesCount; i++) {
/*  67 */         int strLen = VarInt.peek(buf, elemPos);
/*  68 */         if (strLen < 0) throw ProtocolException.negativeLength("categories[" + i + "]", strLen); 
/*  69 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("categories[" + i + "]", strLen, 4096000); 
/*  70 */         int strVarLen = VarInt.length(buf, elemPos);
/*  71 */         obj.categories[i] = PacketIO.readVarString(buf, elemPos);
/*  72 */         elemPos += strVarLen + strLen;
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  80 */     byte nullBits = buf.getByte(offset);
/*  81 */     int maxEnd = 14;
/*  82 */     if ((nullBits & 0x1) != 0) {
/*  83 */       int fieldOffset0 = buf.getIntLE(offset + 6);
/*  84 */       int pos0 = offset + 14 + fieldOffset0;
/*  85 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  86 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  88 */     if ((nullBits & 0x2) != 0) {
/*  89 */       int fieldOffset1 = buf.getIntLE(offset + 10);
/*  90 */       int pos1 = offset + 14 + fieldOffset1;
/*  91 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  92 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl; i++; }
/*  93 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  95 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 100 */     int startPos = buf.writerIndex();
/* 101 */     byte nullBits = 0;
/* 102 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 103 */     if (this.categories != null) nullBits = (byte)(nullBits | 0x2); 
/* 104 */     buf.writeByte(nullBits);
/*     */     
/* 106 */     buf.writeByte(this.type.getValue());
/* 107 */     buf.writeIntLE(this.requiredTierLevel);
/*     */     
/* 109 */     int idOffsetSlot = buf.writerIndex();
/* 110 */     buf.writeIntLE(0);
/* 111 */     int categoriesOffsetSlot = buf.writerIndex();
/* 112 */     buf.writeIntLE(0);
/*     */     
/* 114 */     int varBlockStart = buf.writerIndex();
/* 115 */     if (this.id != null) {
/* 116 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 117 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 119 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 121 */     if (this.categories != null) {
/* 122 */       buf.setIntLE(categoriesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 123 */       if (this.categories.length > 4096000) throw ProtocolException.arrayTooLong("Categories", this.categories.length, 4096000);  VarInt.write(buf, this.categories.length); for (String item : this.categories) PacketIO.writeVarString(buf, item, 4096000); 
/*     */     } else {
/* 125 */       buf.setIntLE(categoriesOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 131 */     int size = 14;
/* 132 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 133 */     if (this.categories != null) {
/* 134 */       int categoriesSize = 0;
/* 135 */       for (String elem : this.categories) categoriesSize += PacketIO.stringSize(elem); 
/* 136 */       size += VarInt.size(this.categories.length) + categoriesSize;
/*     */     } 
/*     */     
/* 139 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 143 */     if (buffer.readableBytes() - offset < 14) {
/* 144 */       return ValidationResult.error("Buffer too small: expected at least 14 bytes");
/*     */     }
/*     */     
/* 147 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 150 */     if ((nullBits & 0x1) != 0) {
/* 151 */       int idOffset = buffer.getIntLE(offset + 6);
/* 152 */       if (idOffset < 0) {
/* 153 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 155 */       int pos = offset + 14 + idOffset;
/* 156 */       if (pos >= buffer.writerIndex()) {
/* 157 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 159 */       int idLen = VarInt.peek(buffer, pos);
/* 160 */       if (idLen < 0) {
/* 161 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 163 */       if (idLen > 4096000) {
/* 164 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 166 */       pos += VarInt.length(buffer, pos);
/* 167 */       pos += idLen;
/* 168 */       if (pos > buffer.writerIndex()) {
/* 169 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 173 */     if ((nullBits & 0x2) != 0) {
/* 174 */       int categoriesOffset = buffer.getIntLE(offset + 10);
/* 175 */       if (categoriesOffset < 0) {
/* 176 */         return ValidationResult.error("Invalid offset for Categories");
/*     */       }
/* 178 */       int pos = offset + 14 + categoriesOffset;
/* 179 */       if (pos >= buffer.writerIndex()) {
/* 180 */         return ValidationResult.error("Offset out of bounds for Categories");
/*     */       }
/* 182 */       int categoriesCount = VarInt.peek(buffer, pos);
/* 183 */       if (categoriesCount < 0) {
/* 184 */         return ValidationResult.error("Invalid array count for Categories");
/*     */       }
/* 186 */       if (categoriesCount > 4096000) {
/* 187 */         return ValidationResult.error("Categories exceeds max length 4096000");
/*     */       }
/* 189 */       pos += VarInt.length(buffer, pos);
/* 190 */       for (int i = 0; i < categoriesCount; i++) {
/* 191 */         int strLen = VarInt.peek(buffer, pos);
/* 192 */         if (strLen < 0) {
/* 193 */           return ValidationResult.error("Invalid string length in Categories");
/*     */         }
/* 195 */         pos += VarInt.length(buffer, pos);
/* 196 */         pos += strLen;
/* 197 */         if (pos > buffer.writerIndex()) {
/* 198 */           return ValidationResult.error("Buffer overflow reading string in Categories");
/*     */         }
/*     */       } 
/*     */     } 
/* 202 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BenchRequirement clone() {
/* 206 */     BenchRequirement copy = new BenchRequirement();
/* 207 */     copy.type = this.type;
/* 208 */     copy.id = this.id;
/* 209 */     copy.categories = (this.categories != null) ? Arrays.<String>copyOf(this.categories, this.categories.length) : null;
/* 210 */     copy.requiredTierLevel = this.requiredTierLevel;
/* 211 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BenchRequirement other;
/* 217 */     if (this == obj) return true; 
/* 218 */     if (obj instanceof BenchRequirement) { other = (BenchRequirement)obj; } else { return false; }
/* 219 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.id, other.id) && Arrays.equals((Object[])this.categories, (Object[])other.categories) && this.requiredTierLevel == other.requiredTierLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 224 */     int result = 1;
/* 225 */     result = 31 * result + Objects.hashCode(this.type);
/* 226 */     result = 31 * result + Objects.hashCode(this.id);
/* 227 */     result = 31 * result + Arrays.hashCode((Object[])this.categories);
/* 228 */     result = 31 * result + Integer.hashCode(this.requiredTierLevel);
/* 229 */     return result;
/*     */   }
/*     */   
/*     */   public BenchRequirement() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BenchRequirement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */