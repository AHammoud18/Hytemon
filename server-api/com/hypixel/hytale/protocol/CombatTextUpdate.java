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
/*     */ 
/*     */ public class CombatTextUpdate
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 16384010;
/*     */   public float hitAngleDeg;
/*     */   @Nullable
/*     */   public String text;
/*     */   
/*     */   public CombatTextUpdate() {}
/*     */   
/*     */   public CombatTextUpdate(float hitAngleDeg, @Nullable String text) {
/*  27 */     this.hitAngleDeg = hitAngleDeg;
/*  28 */     this.text = text;
/*     */   }
/*     */   
/*     */   public CombatTextUpdate(@Nonnull CombatTextUpdate other) {
/*  32 */     this.hitAngleDeg = other.hitAngleDeg;
/*  33 */     this.text = other.text;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CombatTextUpdate deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     CombatTextUpdate obj = new CombatTextUpdate();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     obj.hitAngleDeg = buf.getFloatLE(offset + 1);
/*     */     
/*  42 */     int pos = offset + 5;
/*  43 */     if ((nullBits & 0x1) != 0) { int textLen = VarInt.peek(buf, pos);
/*  44 */       if (textLen < 0) throw ProtocolException.negativeLength("Text", textLen); 
/*  45 */       if (textLen > 4096000) throw ProtocolException.stringTooLong("Text", textLen, 4096000); 
/*  46 */       int textVarLen = VarInt.length(buf, pos);
/*  47 */       obj.text = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  48 */       pos += textVarLen + textLen; }
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int pos = offset + 5;
/*  56 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  57 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  62 */     byte nullBits = 0;
/*  63 */     if (this.text != null) nullBits = (byte)(nullBits | 0x1); 
/*  64 */     buf.writeByte(nullBits);
/*     */     
/*  66 */     buf.writeFloatLE(this.hitAngleDeg);
/*     */     
/*  68 */     if (this.text != null) PacketIO.writeVarString(buf, this.text, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  73 */     int size = 5;
/*  74 */     if (this.text != null) size += PacketIO.stringSize(this.text);
/*     */     
/*  76 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  80 */     if (buffer.readableBytes() - offset < 5) {
/*  81 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/*  84 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  86 */     int pos = offset + 5;
/*     */     
/*  88 */     if ((nullBits & 0x1) != 0) {
/*  89 */       int textLen = VarInt.peek(buffer, pos);
/*  90 */       if (textLen < 0) {
/*  91 */         return ValidationResult.error("Invalid string length for Text");
/*     */       }
/*  93 */       if (textLen > 4096000) {
/*  94 */         return ValidationResult.error("Text exceeds max length 4096000");
/*     */       }
/*  96 */       pos += VarInt.length(buffer, pos);
/*  97 */       pos += textLen;
/*  98 */       if (pos > buffer.writerIndex()) {
/*  99 */         return ValidationResult.error("Buffer overflow reading Text");
/*     */       }
/*     */     } 
/* 102 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CombatTextUpdate clone() {
/* 106 */     CombatTextUpdate copy = new CombatTextUpdate();
/* 107 */     copy.hitAngleDeg = this.hitAngleDeg;
/* 108 */     copy.text = this.text;
/* 109 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CombatTextUpdate other;
/* 115 */     if (this == obj) return true; 
/* 116 */     if (obj instanceof CombatTextUpdate) { other = (CombatTextUpdate)obj; } else { return false; }
/* 117 */      return (this.hitAngleDeg == other.hitAngleDeg && Objects.equals(this.text, other.text));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return Objects.hash(new Object[] { Float.valueOf(this.hitAngleDeg), this.text });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\CombatTextUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */