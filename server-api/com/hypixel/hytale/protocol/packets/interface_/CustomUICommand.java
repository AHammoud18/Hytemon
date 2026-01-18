/*     */ package com.hypixel.hytale.protocol.packets.interface_;
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
/*     */ public class CustomUICommand
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 14;
/*     */   public static final int MAX_SIZE = 49152029;
/*     */   @Nonnull
/*  20 */   public CustomUICommandType type = CustomUICommandType.Append;
/*     */   @Nullable
/*     */   public String selector;
/*     */   @Nullable
/*     */   public String data;
/*     */   @Nullable
/*     */   public String text;
/*     */   
/*     */   public CustomUICommand(@Nonnull CustomUICommandType type, @Nullable String selector, @Nullable String data, @Nullable String text) {
/*  29 */     this.type = type;
/*  30 */     this.selector = selector;
/*  31 */     this.data = data;
/*  32 */     this.text = text;
/*     */   }
/*     */   
/*     */   public CustomUICommand(@Nonnull CustomUICommand other) {
/*  36 */     this.type = other.type;
/*  37 */     this.selector = other.selector;
/*  38 */     this.data = other.data;
/*  39 */     this.text = other.text;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CustomUICommand deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     CustomUICommand obj = new CustomUICommand();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.type = CustomUICommandType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  48 */     if ((nullBits & 0x1) != 0) {
/*  49 */       int varPos0 = offset + 14 + buf.getIntLE(offset + 2);
/*  50 */       int selectorLen = VarInt.peek(buf, varPos0);
/*  51 */       if (selectorLen < 0) throw ProtocolException.negativeLength("Selector", selectorLen); 
/*  52 */       if (selectorLen > 4096000) throw ProtocolException.stringTooLong("Selector", selectorLen, 4096000); 
/*  53 */       obj.selector = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  55 */     if ((nullBits & 0x2) != 0) {
/*  56 */       int varPos1 = offset + 14 + buf.getIntLE(offset + 6);
/*  57 */       int dataLen = VarInt.peek(buf, varPos1);
/*  58 */       if (dataLen < 0) throw ProtocolException.negativeLength("Data", dataLen); 
/*  59 */       if (dataLen > 4096000) throw ProtocolException.stringTooLong("Data", dataLen, 4096000); 
/*  60 */       obj.data = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  62 */     if ((nullBits & 0x4) != 0) {
/*  63 */       int varPos2 = offset + 14 + buf.getIntLE(offset + 10);
/*  64 */       int textLen = VarInt.peek(buf, varPos2);
/*  65 */       if (textLen < 0) throw ProtocolException.negativeLength("Text", textLen); 
/*  66 */       if (textLen > 4096000) throw ProtocolException.stringTooLong("Text", textLen, 4096000); 
/*  67 */       obj.text = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  70 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  74 */     byte nullBits = buf.getByte(offset);
/*  75 */     int maxEnd = 14;
/*  76 */     if ((nullBits & 0x1) != 0) {
/*  77 */       int fieldOffset0 = buf.getIntLE(offset + 2);
/*  78 */       int pos0 = offset + 14 + fieldOffset0;
/*  79 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  80 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  82 */     if ((nullBits & 0x2) != 0) {
/*  83 */       int fieldOffset1 = buf.getIntLE(offset + 6);
/*  84 */       int pos1 = offset + 14 + fieldOffset1;
/*  85 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  86 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  88 */     if ((nullBits & 0x4) != 0) {
/*  89 */       int fieldOffset2 = buf.getIntLE(offset + 10);
/*  90 */       int pos2 = offset + 14 + fieldOffset2;
/*  91 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/*  92 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/*  94 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  99 */     int startPos = buf.writerIndex();
/* 100 */     byte nullBits = 0;
/* 101 */     if (this.selector != null) nullBits = (byte)(nullBits | 0x1); 
/* 102 */     if (this.data != null) nullBits = (byte)(nullBits | 0x2); 
/* 103 */     if (this.text != null) nullBits = (byte)(nullBits | 0x4); 
/* 104 */     buf.writeByte(nullBits);
/*     */     
/* 106 */     buf.writeByte(this.type.getValue());
/*     */     
/* 108 */     int selectorOffsetSlot = buf.writerIndex();
/* 109 */     buf.writeIntLE(0);
/* 110 */     int dataOffsetSlot = buf.writerIndex();
/* 111 */     buf.writeIntLE(0);
/* 112 */     int textOffsetSlot = buf.writerIndex();
/* 113 */     buf.writeIntLE(0);
/*     */     
/* 115 */     int varBlockStart = buf.writerIndex();
/* 116 */     if (this.selector != null) {
/* 117 */       buf.setIntLE(selectorOffsetSlot, buf.writerIndex() - varBlockStart);
/* 118 */       PacketIO.writeVarString(buf, this.selector, 4096000);
/*     */     } else {
/* 120 */       buf.setIntLE(selectorOffsetSlot, -1);
/*     */     } 
/* 122 */     if (this.data != null) {
/* 123 */       buf.setIntLE(dataOffsetSlot, buf.writerIndex() - varBlockStart);
/* 124 */       PacketIO.writeVarString(buf, this.data, 4096000);
/*     */     } else {
/* 126 */       buf.setIntLE(dataOffsetSlot, -1);
/*     */     } 
/* 128 */     if (this.text != null) {
/* 129 */       buf.setIntLE(textOffsetSlot, buf.writerIndex() - varBlockStart);
/* 130 */       PacketIO.writeVarString(buf, this.text, 4096000);
/*     */     } else {
/* 132 */       buf.setIntLE(textOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 138 */     int size = 14;
/* 139 */     if (this.selector != null) size += PacketIO.stringSize(this.selector); 
/* 140 */     if (this.data != null) size += PacketIO.stringSize(this.data); 
/* 141 */     if (this.text != null) size += PacketIO.stringSize(this.text);
/*     */     
/* 143 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 147 */     if (buffer.readableBytes() - offset < 14) {
/* 148 */       return ValidationResult.error("Buffer too small: expected at least 14 bytes");
/*     */     }
/*     */     
/* 151 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 154 */     if ((nullBits & 0x1) != 0) {
/* 155 */       int selectorOffset = buffer.getIntLE(offset + 2);
/* 156 */       if (selectorOffset < 0) {
/* 157 */         return ValidationResult.error("Invalid offset for Selector");
/*     */       }
/* 159 */       int pos = offset + 14 + selectorOffset;
/* 160 */       if (pos >= buffer.writerIndex()) {
/* 161 */         return ValidationResult.error("Offset out of bounds for Selector");
/*     */       }
/* 163 */       int selectorLen = VarInt.peek(buffer, pos);
/* 164 */       if (selectorLen < 0) {
/* 165 */         return ValidationResult.error("Invalid string length for Selector");
/*     */       }
/* 167 */       if (selectorLen > 4096000) {
/* 168 */         return ValidationResult.error("Selector exceeds max length 4096000");
/*     */       }
/* 170 */       pos += VarInt.length(buffer, pos);
/* 171 */       pos += selectorLen;
/* 172 */       if (pos > buffer.writerIndex()) {
/* 173 */         return ValidationResult.error("Buffer overflow reading Selector");
/*     */       }
/*     */     } 
/*     */     
/* 177 */     if ((nullBits & 0x2) != 0) {
/* 178 */       int dataOffset = buffer.getIntLE(offset + 6);
/* 179 */       if (dataOffset < 0) {
/* 180 */         return ValidationResult.error("Invalid offset for Data");
/*     */       }
/* 182 */       int pos = offset + 14 + dataOffset;
/* 183 */       if (pos >= buffer.writerIndex()) {
/* 184 */         return ValidationResult.error("Offset out of bounds for Data");
/*     */       }
/* 186 */       int dataLen = VarInt.peek(buffer, pos);
/* 187 */       if (dataLen < 0) {
/* 188 */         return ValidationResult.error("Invalid string length for Data");
/*     */       }
/* 190 */       if (dataLen > 4096000) {
/* 191 */         return ValidationResult.error("Data exceeds max length 4096000");
/*     */       }
/* 193 */       pos += VarInt.length(buffer, pos);
/* 194 */       pos += dataLen;
/* 195 */       if (pos > buffer.writerIndex()) {
/* 196 */         return ValidationResult.error("Buffer overflow reading Data");
/*     */       }
/*     */     } 
/*     */     
/* 200 */     if ((nullBits & 0x4) != 0) {
/* 201 */       int textOffset = buffer.getIntLE(offset + 10);
/* 202 */       if (textOffset < 0) {
/* 203 */         return ValidationResult.error("Invalid offset for Text");
/*     */       }
/* 205 */       int pos = offset + 14 + textOffset;
/* 206 */       if (pos >= buffer.writerIndex()) {
/* 207 */         return ValidationResult.error("Offset out of bounds for Text");
/*     */       }
/* 209 */       int textLen = VarInt.peek(buffer, pos);
/* 210 */       if (textLen < 0) {
/* 211 */         return ValidationResult.error("Invalid string length for Text");
/*     */       }
/* 213 */       if (textLen > 4096000) {
/* 214 */         return ValidationResult.error("Text exceeds max length 4096000");
/*     */       }
/* 216 */       pos += VarInt.length(buffer, pos);
/* 217 */       pos += textLen;
/* 218 */       if (pos > buffer.writerIndex()) {
/* 219 */         return ValidationResult.error("Buffer overflow reading Text");
/*     */       }
/*     */     } 
/* 222 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CustomUICommand clone() {
/* 226 */     CustomUICommand copy = new CustomUICommand();
/* 227 */     copy.type = this.type;
/* 228 */     copy.selector = this.selector;
/* 229 */     copy.data = this.data;
/* 230 */     copy.text = this.text;
/* 231 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CustomUICommand other;
/* 237 */     if (this == obj) return true; 
/* 238 */     if (obj instanceof CustomUICommand) { other = (CustomUICommand)obj; } else { return false; }
/* 239 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.selector, other.selector) && Objects.equals(this.data, other.data) && Objects.equals(this.text, other.text));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 244 */     return Objects.hash(new Object[] { this.type, this.selector, this.data, this.text });
/*     */   }
/*     */   
/*     */   public CustomUICommand() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\CustomUICommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */