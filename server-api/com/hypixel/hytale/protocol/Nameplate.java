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
/*     */ public class Nameplate
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 16384006;
/*     */   @Nullable
/*     */   public String text;
/*     */   
/*     */   public Nameplate() {}
/*     */   
/*     */   public Nameplate(@Nullable String text) {
/*  26 */     this.text = text;
/*     */   }
/*     */   
/*     */   public Nameplate(@Nonnull Nameplate other) {
/*  30 */     this.text = other.text;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Nameplate deserialize(@Nonnull ByteBuf buf, int offset) {
/*  35 */     Nameplate obj = new Nameplate();
/*  36 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  38 */     int pos = offset + 1;
/*  39 */     if ((nullBits & 0x1) != 0) { int textLen = VarInt.peek(buf, pos);
/*  40 */       if (textLen < 0) throw ProtocolException.negativeLength("Text", textLen); 
/*  41 */       if (textLen > 4096000) throw ProtocolException.stringTooLong("Text", textLen, 4096000); 
/*  42 */       int textVarLen = VarInt.length(buf, pos);
/*  43 */       obj.text = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  44 */       pos += textVarLen + textLen; }
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
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  58 */     byte nullBits = 0;
/*  59 */     if (this.text != null) nullBits = (byte)(nullBits | 0x1); 
/*  60 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  63 */     if (this.text != null) PacketIO.writeVarString(buf, this.text, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  68 */     int size = 1;
/*  69 */     if (this.text != null) size += PacketIO.stringSize(this.text);
/*     */     
/*  71 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  75 */     if (buffer.readableBytes() - offset < 1) {
/*  76 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  79 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  81 */     int pos = offset + 1;
/*     */     
/*  83 */     if ((nullBits & 0x1) != 0) {
/*  84 */       int textLen = VarInt.peek(buffer, pos);
/*  85 */       if (textLen < 0) {
/*  86 */         return ValidationResult.error("Invalid string length for Text");
/*     */       }
/*  88 */       if (textLen > 4096000) {
/*  89 */         return ValidationResult.error("Text exceeds max length 4096000");
/*     */       }
/*  91 */       pos += VarInt.length(buffer, pos);
/*  92 */       pos += textLen;
/*  93 */       if (pos > buffer.writerIndex()) {
/*  94 */         return ValidationResult.error("Buffer overflow reading Text");
/*     */       }
/*     */     } 
/*  97 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Nameplate clone() {
/* 101 */     Nameplate copy = new Nameplate();
/* 102 */     copy.text = this.text;
/* 103 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Nameplate other;
/* 109 */     if (this == obj) return true; 
/* 110 */     if (obj instanceof Nameplate) { other = (Nameplate)obj; } else { return false; }
/* 111 */      return Objects.equals(this.text, other.text);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 116 */     return Objects.hash(new Object[] { this.text });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Nameplate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */