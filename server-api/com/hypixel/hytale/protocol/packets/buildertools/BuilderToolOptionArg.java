/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
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
/*     */ public class BuilderToolOptionArg {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String defaultValue;
/*     */   @Nullable
/*     */   public String[] options;
/*     */   
/*     */   public BuilderToolOptionArg() {}
/*     */   
/*     */   public BuilderToolOptionArg(@Nullable String defaultValue, @Nullable String[] options) {
/*  27 */     this.defaultValue = defaultValue;
/*  28 */     this.options = options;
/*     */   }
/*     */   
/*     */   public BuilderToolOptionArg(@Nonnull BuilderToolOptionArg other) {
/*  32 */     this.defaultValue = other.defaultValue;
/*  33 */     this.options = other.options;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolOptionArg deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     BuilderToolOptionArg obj = new BuilderToolOptionArg();
/*  39 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  41 */     if ((nullBits & 0x1) != 0) {
/*  42 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  43 */       int defaultValueLen = VarInt.peek(buf, varPos0);
/*  44 */       if (defaultValueLen < 0) throw ProtocolException.negativeLength("Default", defaultValueLen); 
/*  45 */       if (defaultValueLen > 4096000) throw ProtocolException.stringTooLong("Default", defaultValueLen, 4096000); 
/*  46 */       obj.defaultValue = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  48 */     if ((nullBits & 0x2) != 0) {
/*  49 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  50 */       int optionsCount = VarInt.peek(buf, varPos1);
/*  51 */       if (optionsCount < 0) throw ProtocolException.negativeLength("Options", optionsCount); 
/*  52 */       if (optionsCount > 4096000) throw ProtocolException.arrayTooLong("Options", optionsCount, 4096000); 
/*  53 */       int varIntLen = VarInt.length(buf, varPos1);
/*  54 */       if ((varPos1 + varIntLen) + optionsCount * 1L > buf.readableBytes())
/*  55 */         throw ProtocolException.bufferTooSmall("Options", varPos1 + varIntLen + optionsCount * 1, buf.readableBytes()); 
/*  56 */       obj.options = new String[optionsCount];
/*  57 */       int elemPos = varPos1 + varIntLen;
/*  58 */       for (int i = 0; i < optionsCount; i++) {
/*  59 */         int strLen = VarInt.peek(buf, elemPos);
/*  60 */         if (strLen < 0) throw ProtocolException.negativeLength("options[" + i + "]", strLen); 
/*  61 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("options[" + i + "]", strLen, 4096000); 
/*  62 */         int strVarLen = VarInt.length(buf, elemPos);
/*  63 */         obj.options[i] = PacketIO.readVarString(buf, elemPos);
/*  64 */         elemPos += strVarLen + strLen;
/*     */       } 
/*     */     } 
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     byte nullBits = buf.getByte(offset);
/*  73 */     int maxEnd = 9;
/*  74 */     if ((nullBits & 0x1) != 0) {
/*  75 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  76 */       int pos0 = offset + 9 + fieldOffset0;
/*  77 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  78 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  80 */     if ((nullBits & 0x2) != 0) {
/*  81 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  82 */       int pos1 = offset + 9 + fieldOffset1;
/*  83 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  84 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl; i++; }
/*  85 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  87 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  92 */     int startPos = buf.writerIndex();
/*  93 */     byte nullBits = 0;
/*  94 */     if (this.defaultValue != null) nullBits = (byte)(nullBits | 0x1); 
/*  95 */     if (this.options != null) nullBits = (byte)(nullBits | 0x2); 
/*  96 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  99 */     int defaultValueOffsetSlot = buf.writerIndex();
/* 100 */     buf.writeIntLE(0);
/* 101 */     int optionsOffsetSlot = buf.writerIndex();
/* 102 */     buf.writeIntLE(0);
/*     */     
/* 104 */     int varBlockStart = buf.writerIndex();
/* 105 */     if (this.defaultValue != null) {
/* 106 */       buf.setIntLE(defaultValueOffsetSlot, buf.writerIndex() - varBlockStart);
/* 107 */       PacketIO.writeVarString(buf, this.defaultValue, 4096000);
/*     */     } else {
/* 109 */       buf.setIntLE(defaultValueOffsetSlot, -1);
/*     */     } 
/* 111 */     if (this.options != null) {
/* 112 */       buf.setIntLE(optionsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 113 */       if (this.options.length > 4096000) throw ProtocolException.arrayTooLong("Options", this.options.length, 4096000);  VarInt.write(buf, this.options.length); for (String item : this.options) PacketIO.writeVarString(buf, item, 4096000); 
/*     */     } else {
/* 115 */       buf.setIntLE(optionsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 121 */     int size = 9;
/* 122 */     if (this.defaultValue != null) size += PacketIO.stringSize(this.defaultValue); 
/* 123 */     if (this.options != null) {
/* 124 */       int optionsSize = 0;
/* 125 */       for (String elem : this.options) optionsSize += PacketIO.stringSize(elem); 
/* 126 */       size += VarInt.size(this.options.length) + optionsSize;
/*     */     } 
/*     */     
/* 129 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 133 */     if (buffer.readableBytes() - offset < 9) {
/* 134 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 137 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 140 */     if ((nullBits & 0x1) != 0) {
/* 141 */       int defaultOffset = buffer.getIntLE(offset + 1);
/* 142 */       if (defaultOffset < 0) {
/* 143 */         return ValidationResult.error("Invalid offset for Default");
/*     */       }
/* 145 */       int pos = offset + 9 + defaultOffset;
/* 146 */       if (pos >= buffer.writerIndex()) {
/* 147 */         return ValidationResult.error("Offset out of bounds for Default");
/*     */       }
/* 149 */       int defaultLen = VarInt.peek(buffer, pos);
/* 150 */       if (defaultLen < 0) {
/* 151 */         return ValidationResult.error("Invalid string length for Default");
/*     */       }
/* 153 */       if (defaultLen > 4096000) {
/* 154 */         return ValidationResult.error("Default exceeds max length 4096000");
/*     */       }
/* 156 */       pos += VarInt.length(buffer, pos);
/* 157 */       pos += defaultLen;
/* 158 */       if (pos > buffer.writerIndex()) {
/* 159 */         return ValidationResult.error("Buffer overflow reading Default");
/*     */       }
/*     */     } 
/*     */     
/* 163 */     if ((nullBits & 0x2) != 0) {
/* 164 */       int optionsOffset = buffer.getIntLE(offset + 5);
/* 165 */       if (optionsOffset < 0) {
/* 166 */         return ValidationResult.error("Invalid offset for Options");
/*     */       }
/* 168 */       int pos = offset + 9 + optionsOffset;
/* 169 */       if (pos >= buffer.writerIndex()) {
/* 170 */         return ValidationResult.error("Offset out of bounds for Options");
/*     */       }
/* 172 */       int optionsCount = VarInt.peek(buffer, pos);
/* 173 */       if (optionsCount < 0) {
/* 174 */         return ValidationResult.error("Invalid array count for Options");
/*     */       }
/* 176 */       if (optionsCount > 4096000) {
/* 177 */         return ValidationResult.error("Options exceeds max length 4096000");
/*     */       }
/* 179 */       pos += VarInt.length(buffer, pos);
/* 180 */       for (int i = 0; i < optionsCount; i++) {
/* 181 */         int strLen = VarInt.peek(buffer, pos);
/* 182 */         if (strLen < 0) {
/* 183 */           return ValidationResult.error("Invalid string length in Options");
/*     */         }
/* 185 */         pos += VarInt.length(buffer, pos);
/* 186 */         pos += strLen;
/* 187 */         if (pos > buffer.writerIndex()) {
/* 188 */           return ValidationResult.error("Buffer overflow reading string in Options");
/*     */         }
/*     */       } 
/*     */     } 
/* 192 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolOptionArg clone() {
/* 196 */     BuilderToolOptionArg copy = new BuilderToolOptionArg();
/* 197 */     copy.defaultValue = this.defaultValue;
/* 198 */     copy.options = (this.options != null) ? Arrays.<String>copyOf(this.options, this.options.length) : null;
/* 199 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolOptionArg other;
/* 205 */     if (this == obj) return true; 
/* 206 */     if (obj instanceof BuilderToolOptionArg) { other = (BuilderToolOptionArg)obj; } else { return false; }
/* 207 */      return (Objects.equals(this.defaultValue, other.defaultValue) && Arrays.equals((Object[])this.options, (Object[])other.options));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 212 */     int result = 1;
/* 213 */     result = 31 * result + Objects.hashCode(this.defaultValue);
/* 214 */     result = 31 * result + Arrays.hashCode((Object[])this.options);
/* 215 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolOptionArg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */