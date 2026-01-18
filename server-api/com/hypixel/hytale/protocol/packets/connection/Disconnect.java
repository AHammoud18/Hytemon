/*     */ package com.hypixel.hytale.protocol.packets.connection;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class Disconnect implements Packet {
/*     */   public static final int PACKET_ID = 1;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 16384007;
/*     */   @Nullable
/*     */   public String reason;
/*     */   
/*     */   public int getId() {
/*  25 */     return 1;
/*     */   }
/*     */   
/*     */   @Nonnull
/*  29 */   public DisconnectType type = DisconnectType.Disconnect;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Disconnect(@Nullable String reason, @Nonnull DisconnectType type) {
/*  35 */     this.reason = reason;
/*  36 */     this.type = type;
/*     */   }
/*     */   
/*     */   public Disconnect(@Nonnull Disconnect other) {
/*  40 */     this.reason = other.reason;
/*  41 */     this.type = other.type;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Disconnect deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     Disconnect obj = new Disconnect();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.type = DisconnectType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  50 */     int pos = offset + 2;
/*  51 */     if ((nullBits & 0x1) != 0) { int reasonLen = VarInt.peek(buf, pos);
/*  52 */       if (reasonLen < 0) throw ProtocolException.negativeLength("Reason", reasonLen); 
/*  53 */       if (reasonLen > 4096000) throw ProtocolException.stringTooLong("Reason", reasonLen, 4096000); 
/*  54 */       int reasonVarLen = VarInt.length(buf, pos);
/*  55 */       obj.reason = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  56 */       pos += reasonVarLen + reasonLen; }
/*     */     
/*  58 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  62 */     byte nullBits = buf.getByte(offset);
/*  63 */     int pos = offset + 2;
/*  64 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  65 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  71 */     byte nullBits = 0;
/*  72 */     if (this.reason != null) nullBits = (byte)(nullBits | 0x1); 
/*  73 */     buf.writeByte(nullBits);
/*     */     
/*  75 */     buf.writeByte(this.type.getValue());
/*     */     
/*  77 */     if (this.reason != null) PacketIO.writeVarString(buf, this.reason, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  82 */     int size = 2;
/*  83 */     if (this.reason != null) size += PacketIO.stringSize(this.reason);
/*     */     
/*  85 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  89 */     if (buffer.readableBytes() - offset < 2) {
/*  90 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */     
/*  93 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  95 */     int pos = offset + 2;
/*     */     
/*  97 */     if ((nullBits & 0x1) != 0) {
/*  98 */       int reasonLen = VarInt.peek(buffer, pos);
/*  99 */       if (reasonLen < 0) {
/* 100 */         return ValidationResult.error("Invalid string length for Reason");
/*     */       }
/* 102 */       if (reasonLen > 4096000) {
/* 103 */         return ValidationResult.error("Reason exceeds max length 4096000");
/*     */       }
/* 105 */       pos += VarInt.length(buffer, pos);
/* 106 */       pos += reasonLen;
/* 107 */       if (pos > buffer.writerIndex()) {
/* 108 */         return ValidationResult.error("Buffer overflow reading Reason");
/*     */       }
/*     */     } 
/* 111 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Disconnect clone() {
/* 115 */     Disconnect copy = new Disconnect();
/* 116 */     copy.reason = this.reason;
/* 117 */     copy.type = this.type;
/* 118 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Disconnect other;
/* 124 */     if (this == obj) return true; 
/* 125 */     if (obj instanceof Disconnect) { other = (Disconnect)obj; } else { return false; }
/* 126 */      return (Objects.equals(this.reason, other.reason) && Objects.equals(this.type, other.type));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 131 */     return Objects.hash(new Object[] { this.reason, this.type });
/*     */   }
/*     */   
/*     */   public Disconnect() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\connection\Disconnect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */