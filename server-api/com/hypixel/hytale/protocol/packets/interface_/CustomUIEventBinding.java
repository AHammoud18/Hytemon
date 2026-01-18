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
/*     */ public class CustomUIEventBinding
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 3;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 11;
/*     */   public static final int MAX_SIZE = 32768021;
/*     */   @Nonnull
/*  20 */   public CustomUIEventBindingType type = CustomUIEventBindingType.Activating;
/*     */   
/*     */   @Nullable
/*     */   public String selector;
/*     */   @Nullable
/*     */   public String data;
/*     */   public boolean locksInterface;
/*     */   
/*     */   public CustomUIEventBinding(@Nonnull CustomUIEventBindingType type, @Nullable String selector, @Nullable String data, boolean locksInterface) {
/*  29 */     this.type = type;
/*  30 */     this.selector = selector;
/*  31 */     this.data = data;
/*  32 */     this.locksInterface = locksInterface;
/*     */   }
/*     */   
/*     */   public CustomUIEventBinding(@Nonnull CustomUIEventBinding other) {
/*  36 */     this.type = other.type;
/*  37 */     this.selector = other.selector;
/*  38 */     this.data = other.data;
/*  39 */     this.locksInterface = other.locksInterface;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CustomUIEventBinding deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     CustomUIEventBinding obj = new CustomUIEventBinding();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.type = CustomUIEventBindingType.fromValue(buf.getByte(offset + 1));
/*  47 */     obj.locksInterface = (buf.getByte(offset + 2) != 0);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 11 + buf.getIntLE(offset + 3);
/*  51 */       int selectorLen = VarInt.peek(buf, varPos0);
/*  52 */       if (selectorLen < 0) throw ProtocolException.negativeLength("Selector", selectorLen); 
/*  53 */       if (selectorLen > 4096000) throw ProtocolException.stringTooLong("Selector", selectorLen, 4096000); 
/*  54 */       obj.selector = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  56 */     if ((nullBits & 0x2) != 0) {
/*  57 */       int varPos1 = offset + 11 + buf.getIntLE(offset + 7);
/*  58 */       int dataLen = VarInt.peek(buf, varPos1);
/*  59 */       if (dataLen < 0) throw ProtocolException.negativeLength("Data", dataLen); 
/*  60 */       if (dataLen > 4096000) throw ProtocolException.stringTooLong("Data", dataLen, 4096000); 
/*  61 */       obj.data = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     byte nullBits = buf.getByte(offset);
/*  69 */     int maxEnd = 11;
/*  70 */     if ((nullBits & 0x1) != 0) {
/*  71 */       int fieldOffset0 = buf.getIntLE(offset + 3);
/*  72 */       int pos0 = offset + 11 + fieldOffset0;
/*  73 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  74 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  76 */     if ((nullBits & 0x2) != 0) {
/*  77 */       int fieldOffset1 = buf.getIntLE(offset + 7);
/*  78 */       int pos1 = offset + 11 + fieldOffset1;
/*  79 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  80 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  82 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  87 */     int startPos = buf.writerIndex();
/*  88 */     byte nullBits = 0;
/*  89 */     if (this.selector != null) nullBits = (byte)(nullBits | 0x1); 
/*  90 */     if (this.data != null) nullBits = (byte)(nullBits | 0x2); 
/*  91 */     buf.writeByte(nullBits);
/*     */     
/*  93 */     buf.writeByte(this.type.getValue());
/*  94 */     buf.writeByte(this.locksInterface ? 1 : 0);
/*     */     
/*  96 */     int selectorOffsetSlot = buf.writerIndex();
/*  97 */     buf.writeIntLE(0);
/*  98 */     int dataOffsetSlot = buf.writerIndex();
/*  99 */     buf.writeIntLE(0);
/*     */     
/* 101 */     int varBlockStart = buf.writerIndex();
/* 102 */     if (this.selector != null) {
/* 103 */       buf.setIntLE(selectorOffsetSlot, buf.writerIndex() - varBlockStart);
/* 104 */       PacketIO.writeVarString(buf, this.selector, 4096000);
/*     */     } else {
/* 106 */       buf.setIntLE(selectorOffsetSlot, -1);
/*     */     } 
/* 108 */     if (this.data != null) {
/* 109 */       buf.setIntLE(dataOffsetSlot, buf.writerIndex() - varBlockStart);
/* 110 */       PacketIO.writeVarString(buf, this.data, 4096000);
/*     */     } else {
/* 112 */       buf.setIntLE(dataOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 118 */     int size = 11;
/* 119 */     if (this.selector != null) size += PacketIO.stringSize(this.selector); 
/* 120 */     if (this.data != null) size += PacketIO.stringSize(this.data);
/*     */     
/* 122 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 126 */     if (buffer.readableBytes() - offset < 11) {
/* 127 */       return ValidationResult.error("Buffer too small: expected at least 11 bytes");
/*     */     }
/*     */     
/* 130 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 133 */     if ((nullBits & 0x1) != 0) {
/* 134 */       int selectorOffset = buffer.getIntLE(offset + 3);
/* 135 */       if (selectorOffset < 0) {
/* 136 */         return ValidationResult.error("Invalid offset for Selector");
/*     */       }
/* 138 */       int pos = offset + 11 + selectorOffset;
/* 139 */       if (pos >= buffer.writerIndex()) {
/* 140 */         return ValidationResult.error("Offset out of bounds for Selector");
/*     */       }
/* 142 */       int selectorLen = VarInt.peek(buffer, pos);
/* 143 */       if (selectorLen < 0) {
/* 144 */         return ValidationResult.error("Invalid string length for Selector");
/*     */       }
/* 146 */       if (selectorLen > 4096000) {
/* 147 */         return ValidationResult.error("Selector exceeds max length 4096000");
/*     */       }
/* 149 */       pos += VarInt.length(buffer, pos);
/* 150 */       pos += selectorLen;
/* 151 */       if (pos > buffer.writerIndex()) {
/* 152 */         return ValidationResult.error("Buffer overflow reading Selector");
/*     */       }
/*     */     } 
/*     */     
/* 156 */     if ((nullBits & 0x2) != 0) {
/* 157 */       int dataOffset = buffer.getIntLE(offset + 7);
/* 158 */       if (dataOffset < 0) {
/* 159 */         return ValidationResult.error("Invalid offset for Data");
/*     */       }
/* 161 */       int pos = offset + 11 + dataOffset;
/* 162 */       if (pos >= buffer.writerIndex()) {
/* 163 */         return ValidationResult.error("Offset out of bounds for Data");
/*     */       }
/* 165 */       int dataLen = VarInt.peek(buffer, pos);
/* 166 */       if (dataLen < 0) {
/* 167 */         return ValidationResult.error("Invalid string length for Data");
/*     */       }
/* 169 */       if (dataLen > 4096000) {
/* 170 */         return ValidationResult.error("Data exceeds max length 4096000");
/*     */       }
/* 172 */       pos += VarInt.length(buffer, pos);
/* 173 */       pos += dataLen;
/* 174 */       if (pos > buffer.writerIndex()) {
/* 175 */         return ValidationResult.error("Buffer overflow reading Data");
/*     */       }
/*     */     } 
/* 178 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CustomUIEventBinding clone() {
/* 182 */     CustomUIEventBinding copy = new CustomUIEventBinding();
/* 183 */     copy.type = this.type;
/* 184 */     copy.selector = this.selector;
/* 185 */     copy.data = this.data;
/* 186 */     copy.locksInterface = this.locksInterface;
/* 187 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CustomUIEventBinding other;
/* 193 */     if (this == obj) return true; 
/* 194 */     if (obj instanceof CustomUIEventBinding) { other = (CustomUIEventBinding)obj; } else { return false; }
/* 195 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.selector, other.selector) && Objects.equals(this.data, other.data) && this.locksInterface == other.locksInterface);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 200 */     return Objects.hash(new Object[] { this.type, this.selector, this.data, Boolean.valueOf(this.locksInterface) });
/*     */   }
/*     */   
/*     */   public CustomUIEventBinding() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\CustomUIEventBinding.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */