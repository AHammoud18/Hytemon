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
/*     */ public class BuilderToolBlockArg
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 16384007;
/*     */   @Nullable
/*     */   public String defaultValue;
/*     */   public boolean allowPattern;
/*     */   
/*     */   public BuilderToolBlockArg() {}
/*     */   
/*     */   public BuilderToolBlockArg(@Nullable String defaultValue, boolean allowPattern) {
/*  27 */     this.defaultValue = defaultValue;
/*  28 */     this.allowPattern = allowPattern;
/*     */   }
/*     */   
/*     */   public BuilderToolBlockArg(@Nonnull BuilderToolBlockArg other) {
/*  32 */     this.defaultValue = other.defaultValue;
/*  33 */     this.allowPattern = other.allowPattern;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolBlockArg deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     BuilderToolBlockArg obj = new BuilderToolBlockArg();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     obj.allowPattern = (buf.getByte(offset + 1) != 0);
/*     */     
/*  42 */     int pos = offset + 2;
/*  43 */     if ((nullBits & 0x1) != 0) { int defaultValueLen = VarInt.peek(buf, pos);
/*  44 */       if (defaultValueLen < 0) throw ProtocolException.negativeLength("Default", defaultValueLen); 
/*  45 */       if (defaultValueLen > 4096000) throw ProtocolException.stringTooLong("Default", defaultValueLen, 4096000); 
/*  46 */       int defaultValueVarLen = VarInt.length(buf, pos);
/*  47 */       obj.defaultValue = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  48 */       pos += defaultValueVarLen + defaultValueLen; }
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int pos = offset + 2;
/*  56 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  57 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  62 */     byte nullBits = 0;
/*  63 */     if (this.defaultValue != null) nullBits = (byte)(nullBits | 0x1); 
/*  64 */     buf.writeByte(nullBits);
/*     */     
/*  66 */     buf.writeByte(this.allowPattern ? 1 : 0);
/*     */     
/*  68 */     if (this.defaultValue != null) PacketIO.writeVarString(buf, this.defaultValue, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  73 */     int size = 2;
/*  74 */     if (this.defaultValue != null) size += PacketIO.stringSize(this.defaultValue);
/*     */     
/*  76 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  80 */     if (buffer.readableBytes() - offset < 2) {
/*  81 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */     
/*  84 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  86 */     int pos = offset + 2;
/*     */     
/*  88 */     if ((nullBits & 0x1) != 0) {
/*  89 */       int defaultLen = VarInt.peek(buffer, pos);
/*  90 */       if (defaultLen < 0) {
/*  91 */         return ValidationResult.error("Invalid string length for Default");
/*     */       }
/*  93 */       if (defaultLen > 4096000) {
/*  94 */         return ValidationResult.error("Default exceeds max length 4096000");
/*     */       }
/*  96 */       pos += VarInt.length(buffer, pos);
/*  97 */       pos += defaultLen;
/*  98 */       if (pos > buffer.writerIndex()) {
/*  99 */         return ValidationResult.error("Buffer overflow reading Default");
/*     */       }
/*     */     } 
/* 102 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolBlockArg clone() {
/* 106 */     BuilderToolBlockArg copy = new BuilderToolBlockArg();
/* 107 */     copy.defaultValue = this.defaultValue;
/* 108 */     copy.allowPattern = this.allowPattern;
/* 109 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolBlockArg other;
/* 115 */     if (this == obj) return true; 
/* 116 */     if (obj instanceof BuilderToolBlockArg) { other = (BuilderToolBlockArg)obj; } else { return false; }
/* 117 */      return (Objects.equals(this.defaultValue, other.defaultValue) && this.allowPattern == other.allowPattern);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return Objects.hash(new Object[] { this.defaultValue, Boolean.valueOf(this.allowPattern) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolBlockArg.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */