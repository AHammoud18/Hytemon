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
/*     */ public class StringParamValue
/*     */   extends ParamValue
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 16384006;
/*     */   @Nullable
/*     */   public String value;
/*     */   
/*     */   public StringParamValue() {}
/*     */   
/*     */   public StringParamValue(@Nullable String value) {
/*  26 */     this.value = value;
/*     */   }
/*     */   
/*     */   public StringParamValue(@Nonnull StringParamValue other) {
/*  30 */     this.value = other.value;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static StringParamValue deserialize(@Nonnull ByteBuf buf, int offset) {
/*  35 */     StringParamValue obj = new StringParamValue();
/*  36 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  38 */     int pos = offset + 1;
/*  39 */     if ((nullBits & 0x1) != 0) { int valueLen = VarInt.peek(buf, pos);
/*  40 */       if (valueLen < 0) throw ProtocolException.negativeLength("Value", valueLen); 
/*  41 */       if (valueLen > 4096000) throw ProtocolException.stringTooLong("Value", valueLen, 4096000); 
/*  42 */       int valueVarLen = VarInt.length(buf, pos);
/*  43 */       obj.value = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  44 */       pos += valueVarLen + valueLen; }
/*     */     
/*  46 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  50 */     byte nullBits = buf.getByte(offset);
/*  51 */     int pos = offset + 1;
/*  52 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  53 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/*  59 */     int startPos = buf.writerIndex();
/*  60 */     byte nullBits = 0;
/*  61 */     if (this.value != null) nullBits = (byte)(nullBits | 0x1); 
/*  62 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  65 */     if (this.value != null) PacketIO.writeVarString(buf, this.value, 4096000); 
/*  66 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  72 */     int size = 1;
/*  73 */     if (this.value != null) size += PacketIO.stringSize(this.value);
/*     */     
/*  75 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  79 */     if (buffer.readableBytes() - offset < 1) {
/*  80 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  83 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  85 */     int pos = offset + 1;
/*     */     
/*  87 */     if ((nullBits & 0x1) != 0) {
/*  88 */       int valueLen = VarInt.peek(buffer, pos);
/*  89 */       if (valueLen < 0) {
/*  90 */         return ValidationResult.error("Invalid string length for Value");
/*     */       }
/*  92 */       if (valueLen > 4096000) {
/*  93 */         return ValidationResult.error("Value exceeds max length 4096000");
/*     */       }
/*  95 */       pos += VarInt.length(buffer, pos);
/*  96 */       pos += valueLen;
/*  97 */       if (pos > buffer.writerIndex()) {
/*  98 */         return ValidationResult.error("Buffer overflow reading Value");
/*     */       }
/*     */     } 
/* 101 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public StringParamValue clone() {
/* 105 */     StringParamValue copy = new StringParamValue();
/* 106 */     copy.value = this.value;
/* 107 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     StringParamValue other;
/* 113 */     if (this == obj) return true; 
/* 114 */     if (obj instanceof StringParamValue) { other = (StringParamValue)obj; } else { return false; }
/* 115 */      return Objects.equals(this.value, other.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     return Objects.hash(new Object[] { this.value });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\StringParamValue.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */