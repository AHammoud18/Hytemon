/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class TagPattern
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 14;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nonnull
/*  20 */   public TagPatternType type = TagPatternType.Equals;
/*     */   
/*     */   public int tagIndex;
/*     */   @Nullable
/*     */   public TagPattern[] operands;
/*     */   @Nullable
/*     */   public TagPattern not;
/*     */   
/*     */   public TagPattern(@Nonnull TagPatternType type, int tagIndex, @Nullable TagPattern[] operands, @Nullable TagPattern not) {
/*  29 */     this.type = type;
/*  30 */     this.tagIndex = tagIndex;
/*  31 */     this.operands = operands;
/*  32 */     this.not = not;
/*     */   }
/*     */   
/*     */   public TagPattern(@Nonnull TagPattern other) {
/*  36 */     this.type = other.type;
/*  37 */     this.tagIndex = other.tagIndex;
/*  38 */     this.operands = other.operands;
/*  39 */     this.not = other.not;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static TagPattern deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     TagPattern obj = new TagPattern();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.type = TagPatternType.fromValue(buf.getByte(offset + 1));
/*  47 */     obj.tagIndex = buf.getIntLE(offset + 2);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 14 + buf.getIntLE(offset + 6);
/*  51 */       int operandsCount = VarInt.peek(buf, varPos0);
/*  52 */       if (operandsCount < 0) throw ProtocolException.negativeLength("Operands", operandsCount); 
/*  53 */       if (operandsCount > 4096000) throw ProtocolException.arrayTooLong("Operands", operandsCount, 4096000); 
/*  54 */       int varIntLen = VarInt.length(buf, varPos0);
/*  55 */       if ((varPos0 + varIntLen) + operandsCount * 6L > buf.readableBytes())
/*  56 */         throw ProtocolException.bufferTooSmall("Operands", varPos0 + varIntLen + operandsCount * 6, buf.readableBytes()); 
/*  57 */       obj.operands = new TagPattern[operandsCount];
/*  58 */       int elemPos = varPos0 + varIntLen;
/*  59 */       for (int i = 0; i < operandsCount; i++) {
/*  60 */         obj.operands[i] = deserialize(buf, elemPos);
/*  61 */         elemPos += computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  64 */     if ((nullBits & 0x2) != 0) {
/*  65 */       int varPos1 = offset + 14 + buf.getIntLE(offset + 10);
/*  66 */       obj.not = deserialize(buf, varPos1);
/*     */     } 
/*     */     
/*  69 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  73 */     byte nullBits = buf.getByte(offset);
/*  74 */     int maxEnd = 14;
/*  75 */     if ((nullBits & 0x1) != 0) {
/*  76 */       int fieldOffset0 = buf.getIntLE(offset + 6);
/*  77 */       int pos0 = offset + 14 + fieldOffset0;
/*  78 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/*  79 */       for (int i = 0; i < arrLen; ) { pos0 += computeBytesConsumed(buf, pos0); i++; }
/*  80 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  82 */     if ((nullBits & 0x2) != 0) {
/*  83 */       int fieldOffset1 = buf.getIntLE(offset + 10);
/*  84 */       int pos1 = offset + 14 + fieldOffset1;
/*  85 */       pos1 += computeBytesConsumed(buf, pos1);
/*  86 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  88 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  93 */     int startPos = buf.writerIndex();
/*  94 */     byte nullBits = 0;
/*  95 */     if (this.operands != null) nullBits = (byte)(nullBits | 0x1); 
/*  96 */     if (this.not != null) nullBits = (byte)(nullBits | 0x2); 
/*  97 */     buf.writeByte(nullBits);
/*     */     
/*  99 */     buf.writeByte(this.type.getValue());
/* 100 */     buf.writeIntLE(this.tagIndex);
/*     */     
/* 102 */     int operandsOffsetSlot = buf.writerIndex();
/* 103 */     buf.writeIntLE(0);
/* 104 */     int notOffsetSlot = buf.writerIndex();
/* 105 */     buf.writeIntLE(0);
/*     */     
/* 107 */     int varBlockStart = buf.writerIndex();
/* 108 */     if (this.operands != null) {
/* 109 */       buf.setIntLE(operandsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 110 */       if (this.operands.length > 4096000) throw ProtocolException.arrayTooLong("Operands", this.operands.length, 4096000);  VarInt.write(buf, this.operands.length); for (TagPattern item : this.operands) item.serialize(buf); 
/*     */     } else {
/* 112 */       buf.setIntLE(operandsOffsetSlot, -1);
/*     */     } 
/* 114 */     if (this.not != null) {
/* 115 */       buf.setIntLE(notOffsetSlot, buf.writerIndex() - varBlockStart);
/* 116 */       this.not.serialize(buf);
/*     */     } else {
/* 118 */       buf.setIntLE(notOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 124 */     int size = 14;
/* 125 */     if (this.operands != null) {
/* 126 */       int operandsSize = 0;
/* 127 */       for (TagPattern elem : this.operands) operandsSize += elem.computeSize(); 
/* 128 */       size += VarInt.size(this.operands.length) + operandsSize;
/*     */     } 
/* 130 */     if (this.not != null) size += this.not.computeSize();
/*     */     
/* 132 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 136 */     if (buffer.readableBytes() - offset < 14) {
/* 137 */       return ValidationResult.error("Buffer too small: expected at least 14 bytes");
/*     */     }
/*     */     
/* 140 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 143 */     if ((nullBits & 0x1) != 0) {
/* 144 */       int operandsOffset = buffer.getIntLE(offset + 6);
/* 145 */       if (operandsOffset < 0) {
/* 146 */         return ValidationResult.error("Invalid offset for Operands");
/*     */       }
/* 148 */       int pos = offset + 14 + operandsOffset;
/* 149 */       if (pos >= buffer.writerIndex()) {
/* 150 */         return ValidationResult.error("Offset out of bounds for Operands");
/*     */       }
/* 152 */       int operandsCount = VarInt.peek(buffer, pos);
/* 153 */       if (operandsCount < 0) {
/* 154 */         return ValidationResult.error("Invalid array count for Operands");
/*     */       }
/* 156 */       if (operandsCount > 4096000) {
/* 157 */         return ValidationResult.error("Operands exceeds max length 4096000");
/*     */       }
/* 159 */       pos += VarInt.length(buffer, pos);
/* 160 */       for (int i = 0; i < operandsCount; i++) {
/* 161 */         ValidationResult structResult = validateStructure(buffer, pos);
/* 162 */         if (!structResult.isValid()) {
/* 163 */           return ValidationResult.error("Invalid TagPattern in Operands[" + i + "]: " + structResult.error());
/*     */         }
/* 165 */         pos += computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     if ((nullBits & 0x2) != 0) {
/* 170 */       int notOffset = buffer.getIntLE(offset + 10);
/* 171 */       if (notOffset < 0) {
/* 172 */         return ValidationResult.error("Invalid offset for Not");
/*     */       }
/* 174 */       int pos = offset + 14 + notOffset;
/* 175 */       if (pos >= buffer.writerIndex()) {
/* 176 */         return ValidationResult.error("Offset out of bounds for Not");
/*     */       }
/* 178 */       ValidationResult notResult = validateStructure(buffer, pos);
/* 179 */       if (!notResult.isValid()) {
/* 180 */         return ValidationResult.error("Invalid Not: " + notResult.error());
/*     */       }
/* 182 */       pos += computeBytesConsumed(buffer, pos);
/*     */     } 
/* 184 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public TagPattern clone() {
/* 188 */     TagPattern copy = new TagPattern();
/* 189 */     copy.type = this.type;
/* 190 */     copy.tagIndex = this.tagIndex;
/* 191 */     copy.operands = (this.operands != null) ? (TagPattern[])Arrays.<TagPattern>stream(this.operands).map(e -> e.clone()).toArray(x$0 -> new TagPattern[x$0]) : null;
/* 192 */     copy.not = (this.not != null) ? this.not.clone() : null;
/* 193 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     TagPattern other;
/* 199 */     if (this == obj) return true; 
/* 200 */     if (obj instanceof TagPattern) { other = (TagPattern)obj; } else { return false; }
/* 201 */      return (Objects.equals(this.type, other.type) && this.tagIndex == other.tagIndex && Arrays.equals((Object[])this.operands, (Object[])other.operands) && Objects.equals(this.not, other.not));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 206 */     int result = 1;
/* 207 */     result = 31 * result + Objects.hashCode(this.type);
/* 208 */     result = 31 * result + Integer.hashCode(this.tagIndex);
/* 209 */     result = 31 * result + Arrays.hashCode((Object[])this.operands);
/* 210 */     result = 31 * result + Objects.hashCode(this.not);
/* 211 */     return result;
/*     */   }
/*     */   
/*     */   public TagPattern() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\TagPattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */