/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class HostAddress
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 1031;
/*     */   @Nonnull
/*  20 */   public String host = "";
/*     */ 
/*     */   
/*     */   public short port;
/*     */ 
/*     */   
/*     */   public HostAddress(@Nonnull String host, short port) {
/*  27 */     this.host = host;
/*  28 */     this.port = port;
/*     */   }
/*     */   
/*     */   public HostAddress(@Nonnull HostAddress other) {
/*  32 */     this.host = other.host;
/*  33 */     this.port = other.port;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static HostAddress deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     HostAddress obj = new HostAddress();
/*     */     
/*  40 */     obj.port = buf.getShortLE(offset + 0);
/*     */     
/*  42 */     int pos = offset + 2;
/*  43 */     int hostLen = VarInt.peek(buf, pos);
/*  44 */     if (hostLen < 0) throw ProtocolException.negativeLength("Host", hostLen); 
/*  45 */     if (hostLen > 256) throw ProtocolException.stringTooLong("Host", hostLen, 256); 
/*  46 */     int hostVarLen = VarInt.length(buf, pos);
/*  47 */     obj.host = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  48 */     pos += hostVarLen + hostLen;
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     int pos = offset + 2;
/*  55 */     int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl;
/*  56 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  62 */     buf.writeShortLE(this.port);
/*     */     
/*  64 */     PacketIO.writeVarString(buf, this.host, 256);
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  69 */     int size = 2;
/*  70 */     size += PacketIO.stringSize(this.host);
/*     */     
/*  72 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  76 */     if (buffer.readableBytes() - offset < 2) {
/*  77 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */ 
/*     */     
/*  81 */     int pos = offset + 2;
/*     */     
/*  83 */     int hostLen = VarInt.peek(buffer, pos);
/*  84 */     if (hostLen < 0) {
/*  85 */       return ValidationResult.error("Invalid string length for Host");
/*     */     }
/*  87 */     if (hostLen > 256) {
/*  88 */       return ValidationResult.error("Host exceeds max length 256");
/*     */     }
/*  90 */     pos += VarInt.length(buffer, pos);
/*  91 */     pos += hostLen;
/*  92 */     if (pos > buffer.writerIndex()) {
/*  93 */       return ValidationResult.error("Buffer overflow reading Host");
/*     */     }
/*  95 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public HostAddress clone() {
/*  99 */     HostAddress copy = new HostAddress();
/* 100 */     copy.host = this.host;
/* 101 */     copy.port = this.port;
/* 102 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     HostAddress other;
/* 108 */     if (this == obj) return true; 
/* 109 */     if (obj instanceof HostAddress) { other = (HostAddress)obj; } else { return false; }
/* 110 */      return (Objects.equals(this.host, other.host) && this.port == other.port);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 115 */     return Objects.hash(new Object[] { this.host, Short.valueOf(this.port) });
/*     */   }
/*     */   
/*     */   public HostAddress() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\HostAddress.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */