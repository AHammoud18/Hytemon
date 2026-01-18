/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class ItemReticle
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 6;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public boolean hideBase;
/*     */   @Nullable
/*     */   public String[] parts;
/*     */   public float duration;
/*     */   
/*     */   public ItemReticle() {}
/*     */   
/*     */   public ItemReticle(boolean hideBase, @Nullable String[] parts, float duration) {
/*  28 */     this.hideBase = hideBase;
/*  29 */     this.parts = parts;
/*  30 */     this.duration = duration;
/*     */   }
/*     */   
/*     */   public ItemReticle(@Nonnull ItemReticle other) {
/*  34 */     this.hideBase = other.hideBase;
/*  35 */     this.parts = other.parts;
/*  36 */     this.duration = other.duration;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemReticle deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     ItemReticle obj = new ItemReticle();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.hideBase = (buf.getByte(offset + 1) != 0);
/*  44 */     obj.duration = buf.getFloatLE(offset + 2);
/*     */     
/*  46 */     int pos = offset + 6;
/*  47 */     if ((nullBits & 0x1) != 0) { int partsCount = VarInt.peek(buf, pos);
/*  48 */       if (partsCount < 0) throw ProtocolException.negativeLength("Parts", partsCount); 
/*  49 */       if (partsCount > 4096000) throw ProtocolException.arrayTooLong("Parts", partsCount, 4096000); 
/*  50 */       int partsVarLen = VarInt.size(partsCount);
/*  51 */       if ((pos + partsVarLen) + partsCount * 1L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("Parts", pos + partsVarLen + partsCount * 1, buf.readableBytes()); 
/*  53 */       pos += partsVarLen;
/*  54 */       obj.parts = new String[partsCount];
/*  55 */       for (int i = 0; i < partsCount; i++) {
/*  56 */         int strLen = VarInt.peek(buf, pos);
/*  57 */         if (strLen < 0) throw ProtocolException.negativeLength("parts[" + i + "]", strLen); 
/*  58 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("parts[" + i + "]", strLen, 4096000); 
/*  59 */         int strVarLen = VarInt.length(buf, pos);
/*  60 */         obj.parts[i] = PacketIO.readVarString(buf, pos);
/*  61 */         pos += strVarLen + strLen;
/*     */       }  }
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     byte nullBits = buf.getByte(offset);
/*  69 */     int pos = offset + 6;
/*  70 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  71 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; i++; }  }
/*  72 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  77 */     byte nullBits = 0;
/*  78 */     if (this.parts != null) nullBits = (byte)(nullBits | 0x1); 
/*  79 */     buf.writeByte(nullBits);
/*     */     
/*  81 */     buf.writeByte(this.hideBase ? 1 : 0);
/*  82 */     buf.writeFloatLE(this.duration);
/*     */     
/*  84 */     if (this.parts != null) { if (this.parts.length > 4096000) throw ProtocolException.arrayTooLong("Parts", this.parts.length, 4096000);  VarInt.write(buf, this.parts.length); for (String item : this.parts) PacketIO.writeVarString(buf, item, 4096000);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  89 */     int size = 6;
/*  90 */     if (this.parts != null) {
/*  91 */       int partsSize = 0;
/*  92 */       for (String elem : this.parts) partsSize += PacketIO.stringSize(elem); 
/*  93 */       size += VarInt.size(this.parts.length) + partsSize;
/*     */     } 
/*     */     
/*  96 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 100 */     if (buffer.readableBytes() - offset < 6) {
/* 101 */       return ValidationResult.error("Buffer too small: expected at least 6 bytes");
/*     */     }
/*     */     
/* 104 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 106 */     int pos = offset + 6;
/*     */     
/* 108 */     if ((nullBits & 0x1) != 0) {
/* 109 */       int partsCount = VarInt.peek(buffer, pos);
/* 110 */       if (partsCount < 0) {
/* 111 */         return ValidationResult.error("Invalid array count for Parts");
/*     */       }
/* 113 */       if (partsCount > 4096000) {
/* 114 */         return ValidationResult.error("Parts exceeds max length 4096000");
/*     */       }
/* 116 */       pos += VarInt.length(buffer, pos);
/* 117 */       for (int i = 0; i < partsCount; i++) {
/* 118 */         int strLen = VarInt.peek(buffer, pos);
/* 119 */         if (strLen < 0) {
/* 120 */           return ValidationResult.error("Invalid string length in Parts");
/*     */         }
/* 122 */         pos += VarInt.length(buffer, pos);
/* 123 */         pos += strLen;
/* 124 */         if (pos > buffer.writerIndex()) {
/* 125 */           return ValidationResult.error("Buffer overflow reading string in Parts");
/*     */         }
/*     */       } 
/*     */     } 
/* 129 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemReticle clone() {
/* 133 */     ItemReticle copy = new ItemReticle();
/* 134 */     copy.hideBase = this.hideBase;
/* 135 */     copy.parts = (this.parts != null) ? Arrays.<String>copyOf(this.parts, this.parts.length) : null;
/* 136 */     copy.duration = this.duration;
/* 137 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemReticle other;
/* 143 */     if (this == obj) return true; 
/* 144 */     if (obj instanceof ItemReticle) { other = (ItemReticle)obj; } else { return false; }
/* 145 */      return (this.hideBase == other.hideBase && Arrays.equals((Object[])this.parts, (Object[])other.parts) && this.duration == other.duration);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 150 */     int result = 1;
/* 151 */     result = 31 * result + Boolean.hashCode(this.hideBase);
/* 152 */     result = 31 * result + Arrays.hashCode((Object[])this.parts);
/* 153 */     result = 31 * result + Float.hashCode(this.duration);
/* 154 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemReticle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */