/*     */ package com.hypixel.hytale.protocol.packets.auth;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class PasswordResponse
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 15;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 70;
/*     */   @Nullable
/*     */   public byte[] hash;
/*     */   
/*     */   public int getId() {
/*  25 */     return 15;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PasswordResponse() {}
/*     */ 
/*     */   
/*     */   public PasswordResponse(@Nullable byte[] hash) {
/*  34 */     this.hash = hash;
/*     */   }
/*     */   
/*     */   public PasswordResponse(@Nonnull PasswordResponse other) {
/*  38 */     this.hash = other.hash;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PasswordResponse deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     PasswordResponse obj = new PasswordResponse();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int hashCount = VarInt.peek(buf, pos);
/*  48 */       if (hashCount < 0) throw ProtocolException.negativeLength("Hash", hashCount); 
/*  49 */       if (hashCount > 64) throw ProtocolException.arrayTooLong("Hash", hashCount, 64); 
/*  50 */       int hashVarLen = VarInt.size(hashCount);
/*  51 */       if ((pos + hashVarLen) + hashCount * 1L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("Hash", pos + hashVarLen + hashCount * 1, buf.readableBytes()); 
/*  53 */       pos += hashVarLen;
/*  54 */       obj.hash = new byte[hashCount];
/*  55 */       for (int i = 0; i < hashCount; i++) {
/*  56 */         obj.hash[i] = buf.getByte(pos + i * 1);
/*     */       }
/*  58 */       pos += hashCount * 1; }
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int pos = offset + 1;
/*  66 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 1; }
/*  67 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  73 */     byte nullBits = 0;
/*  74 */     if (this.hash != null) nullBits = (byte)(nullBits | 0x1); 
/*  75 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  78 */     if (this.hash != null) { if (this.hash.length > 64) throw ProtocolException.arrayTooLong("Hash", this.hash.length, 64);  VarInt.write(buf, this.hash.length); for (byte item : this.hash) buf.writeByte(item);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  83 */     int size = 1;
/*  84 */     if (this.hash != null) size += VarInt.size(this.hash.length) + this.hash.length * 1;
/*     */     
/*  86 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  90 */     if (buffer.readableBytes() - offset < 1) {
/*  91 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  94 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  96 */     int pos = offset + 1;
/*     */     
/*  98 */     if ((nullBits & 0x1) != 0) {
/*  99 */       int hashCount = VarInt.peek(buffer, pos);
/* 100 */       if (hashCount < 0) {
/* 101 */         return ValidationResult.error("Invalid array count for Hash");
/*     */       }
/* 103 */       if (hashCount > 64) {
/* 104 */         return ValidationResult.error("Hash exceeds max length 64");
/*     */       }
/* 106 */       pos += VarInt.length(buffer, pos);
/* 107 */       pos += hashCount * 1;
/* 108 */       if (pos > buffer.writerIndex()) {
/* 109 */         return ValidationResult.error("Buffer overflow reading Hash");
/*     */       }
/*     */     } 
/* 112 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public PasswordResponse clone() {
/* 116 */     PasswordResponse copy = new PasswordResponse();
/* 117 */     copy.hash = (this.hash != null) ? Arrays.copyOf(this.hash, this.hash.length) : null;
/* 118 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     PasswordResponse other;
/* 124 */     if (this == obj) return true; 
/* 125 */     if (obj instanceof PasswordResponse) { other = (PasswordResponse)obj; } else { return false; }
/* 126 */      return Arrays.equals(this.hash, other.hash);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 131 */     int result = 1;
/* 132 */     result = 31 * result + Arrays.hashCode(this.hash);
/* 133 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\auth\PasswordResponse.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */