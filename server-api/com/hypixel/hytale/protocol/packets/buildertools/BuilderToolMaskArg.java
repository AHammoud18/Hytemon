/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
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
/*     */ public class BuilderToolMaskArg
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 16384006;
/*     */   @Nullable
/*     */   public String defaultValue;
/*     */   
/*     */   public BuilderToolMaskArg() {}
/*     */   
/*     */   public BuilderToolMaskArg(@Nullable String defaultValue) {
/*  26 */     this.defaultValue = defaultValue;
/*     */   }
/*     */   
/*     */   public BuilderToolMaskArg(@Nonnull BuilderToolMaskArg other) {
/*  30 */     this.defaultValue = other.defaultValue;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolMaskArg deserialize(@Nonnull ByteBuf buf, int offset) {
/*  35 */     BuilderToolMaskArg obj = new BuilderToolMaskArg();
/*  36 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  38 */     int pos = offset + 1;
/*  39 */     if ((nullBits & 0x1) != 0) { int defaultValueLen = VarInt.peek(buf, pos);
/*  40 */       if (defaultValueLen < 0) throw ProtocolException.negativeLength("Default", defaultValueLen); 
/*  41 */       if (defaultValueLen > 4096000) throw ProtocolException.stringTooLong("Default", defaultValueLen, 4096000); 
/*  42 */       int defaultValueVarLen = VarInt.length(buf, pos);
/*  43 */       obj.defaultValue = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  44 */       pos += defaultValueVarLen + defaultValueLen; }
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
/*  59 */     if (this.defaultValue != null) nullBits = (byte)(nullBits | 0x1); 
/*  60 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  63 */     if (this.defaultValue != null) PacketIO.writeVarString(buf, this.defaultValue, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  68 */     int size = 1;
/*  69 */     if (this.defaultValue != null) size += PacketIO.stringSize(this.defaultValue);
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
/*  84 */       int defaultLen = VarInt.peek(buffer, pos);
/*  85 */       if (defaultLen < 0) {
/*  86 */         return ValidationResult.error("Invalid string length for Default");
/*     */       }
/*  88 */       if (defaultLen > 4096000) {
/*  89 */         return ValidationResult.error("Default exceeds max length 4096000");
/*     */       }
/*  91 */       pos += VarInt.length(buffer, pos);
/*  92 */       pos += defaultLen;
/*  93 */       if (pos > buffer.writerIndex()) {
/*  94 */         return ValidationResult.error("Buffer overflow reading Default");
/*     */       }
/*     */     } 
/*  97 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolMaskArg clone() {
/* 101 */     BuilderToolMaskArg copy = new BuilderToolMaskArg();
/* 102 */     copy.defaultValue = this.defaultValue;
/* 103 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolMaskArg other;
/* 109 */     if (this == obj) return true; 
/* 110 */     if (obj instanceof BuilderToolMaskArg) { other = (BuilderToolMaskArg)obj; } else { return false; }
/* 111 */      return Objects.equals(this.defaultValue, other.defaultValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 116 */     return Objects.hash(new Object[] { this.defaultValue });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolMaskArg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */