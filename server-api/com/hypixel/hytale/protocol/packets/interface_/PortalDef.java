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
/*     */ 
/*     */ public class PortalDef
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 16384014;
/*     */   @Nullable
/*     */   public String nameKey;
/*     */   public int explorationSeconds;
/*     */   public int breachSeconds;
/*     */   
/*     */   public PortalDef() {}
/*     */   
/*     */   public PortalDef(@Nullable String nameKey, int explorationSeconds, int breachSeconds) {
/*  28 */     this.nameKey = nameKey;
/*  29 */     this.explorationSeconds = explorationSeconds;
/*  30 */     this.breachSeconds = breachSeconds;
/*     */   }
/*     */   
/*     */   public PortalDef(@Nonnull PortalDef other) {
/*  34 */     this.nameKey = other.nameKey;
/*  35 */     this.explorationSeconds = other.explorationSeconds;
/*  36 */     this.breachSeconds = other.breachSeconds;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PortalDef deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     PortalDef obj = new PortalDef();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.explorationSeconds = buf.getIntLE(offset + 1);
/*  44 */     obj.breachSeconds = buf.getIntLE(offset + 5);
/*     */     
/*  46 */     int pos = offset + 9;
/*  47 */     if ((nullBits & 0x1) != 0) { int nameKeyLen = VarInt.peek(buf, pos);
/*  48 */       if (nameKeyLen < 0) throw ProtocolException.negativeLength("NameKey", nameKeyLen); 
/*  49 */       if (nameKeyLen > 4096000) throw ProtocolException.stringTooLong("NameKey", nameKeyLen, 4096000); 
/*  50 */       int nameKeyVarLen = VarInt.length(buf, pos);
/*  51 */       obj.nameKey = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  52 */       pos += nameKeyVarLen + nameKeyLen; }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 9;
/*  60 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  61 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     byte nullBits = 0;
/*  67 */     if (this.nameKey != null) nullBits = (byte)(nullBits | 0x1); 
/*  68 */     buf.writeByte(nullBits);
/*     */     
/*  70 */     buf.writeIntLE(this.explorationSeconds);
/*  71 */     buf.writeIntLE(this.breachSeconds);
/*     */     
/*  73 */     if (this.nameKey != null) PacketIO.writeVarString(buf, this.nameKey, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  78 */     int size = 9;
/*  79 */     if (this.nameKey != null) size += PacketIO.stringSize(this.nameKey);
/*     */     
/*  81 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  85 */     if (buffer.readableBytes() - offset < 9) {
/*  86 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/*  89 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  91 */     int pos = offset + 9;
/*     */     
/*  93 */     if ((nullBits & 0x1) != 0) {
/*  94 */       int nameKeyLen = VarInt.peek(buffer, pos);
/*  95 */       if (nameKeyLen < 0) {
/*  96 */         return ValidationResult.error("Invalid string length for NameKey");
/*     */       }
/*  98 */       if (nameKeyLen > 4096000) {
/*  99 */         return ValidationResult.error("NameKey exceeds max length 4096000");
/*     */       }
/* 101 */       pos += VarInt.length(buffer, pos);
/* 102 */       pos += nameKeyLen;
/* 103 */       if (pos > buffer.writerIndex()) {
/* 104 */         return ValidationResult.error("Buffer overflow reading NameKey");
/*     */       }
/*     */     } 
/* 107 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public PortalDef clone() {
/* 111 */     PortalDef copy = new PortalDef();
/* 112 */     copy.nameKey = this.nameKey;
/* 113 */     copy.explorationSeconds = this.explorationSeconds;
/* 114 */     copy.breachSeconds = this.breachSeconds;
/* 115 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     PortalDef other;
/* 121 */     if (this == obj) return true; 
/* 122 */     if (obj instanceof PortalDef) { other = (PortalDef)obj; } else { return false; }
/* 123 */      return (Objects.equals(this.nameKey, other.nameKey) && this.explorationSeconds == other.explorationSeconds && this.breachSeconds == other.breachSeconds);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 128 */     return Objects.hash(new Object[] { this.nameKey, Integer.valueOf(this.explorationSeconds), Integer.valueOf(this.breachSeconds) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\PortalDef.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */