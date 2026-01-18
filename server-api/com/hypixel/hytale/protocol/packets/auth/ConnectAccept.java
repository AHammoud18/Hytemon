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
/*     */ public class ConnectAccept
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 14;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 70;
/*     */   @Nullable
/*     */   public byte[] passwordChallenge;
/*     */   
/*     */   public int getId() {
/*  25 */     return 14;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectAccept() {}
/*     */ 
/*     */   
/*     */   public ConnectAccept(@Nullable byte[] passwordChallenge) {
/*  34 */     this.passwordChallenge = passwordChallenge;
/*     */   }
/*     */   
/*     */   public ConnectAccept(@Nonnull ConnectAccept other) {
/*  38 */     this.passwordChallenge = other.passwordChallenge;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ConnectAccept deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     ConnectAccept obj = new ConnectAccept();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int passwordChallengeCount = VarInt.peek(buf, pos);
/*  48 */       if (passwordChallengeCount < 0) throw ProtocolException.negativeLength("PasswordChallenge", passwordChallengeCount); 
/*  49 */       if (passwordChallengeCount > 64) throw ProtocolException.arrayTooLong("PasswordChallenge", passwordChallengeCount, 64); 
/*  50 */       int passwordChallengeVarLen = VarInt.size(passwordChallengeCount);
/*  51 */       if ((pos + passwordChallengeVarLen) + passwordChallengeCount * 1L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("PasswordChallenge", pos + passwordChallengeVarLen + passwordChallengeCount * 1, buf.readableBytes()); 
/*  53 */       pos += passwordChallengeVarLen;
/*  54 */       obj.passwordChallenge = new byte[passwordChallengeCount];
/*  55 */       for (int i = 0; i < passwordChallengeCount; i++) {
/*  56 */         obj.passwordChallenge[i] = buf.getByte(pos + i * 1);
/*     */       }
/*  58 */       pos += passwordChallengeCount * 1; }
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
/*  74 */     if (this.passwordChallenge != null) nullBits = (byte)(nullBits | 0x1); 
/*  75 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  78 */     if (this.passwordChallenge != null) { if (this.passwordChallenge.length > 64) throw ProtocolException.arrayTooLong("PasswordChallenge", this.passwordChallenge.length, 64);  VarInt.write(buf, this.passwordChallenge.length); for (byte item : this.passwordChallenge) buf.writeByte(item);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  83 */     int size = 1;
/*  84 */     if (this.passwordChallenge != null) size += VarInt.size(this.passwordChallenge.length) + this.passwordChallenge.length * 1;
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
/*  99 */       int passwordChallengeCount = VarInt.peek(buffer, pos);
/* 100 */       if (passwordChallengeCount < 0) {
/* 101 */         return ValidationResult.error("Invalid array count for PasswordChallenge");
/*     */       }
/* 103 */       if (passwordChallengeCount > 64) {
/* 104 */         return ValidationResult.error("PasswordChallenge exceeds max length 64");
/*     */       }
/* 106 */       pos += VarInt.length(buffer, pos);
/* 107 */       pos += passwordChallengeCount * 1;
/* 108 */       if (pos > buffer.writerIndex()) {
/* 109 */         return ValidationResult.error("Buffer overflow reading PasswordChallenge");
/*     */       }
/*     */     } 
/* 112 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ConnectAccept clone() {
/* 116 */     ConnectAccept copy = new ConnectAccept();
/* 117 */     copy.passwordChallenge = (this.passwordChallenge != null) ? Arrays.copyOf(this.passwordChallenge, this.passwordChallenge.length) : null;
/* 118 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ConnectAccept other;
/* 124 */     if (this == obj) return true; 
/* 125 */     if (obj instanceof ConnectAccept) { other = (ConnectAccept)obj; } else { return false; }
/* 126 */      return Arrays.equals(this.passwordChallenge, other.passwordChallenge);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 131 */     int result = 1;
/* 132 */     result = 31 * result + Arrays.hashCode(this.passwordChallenge);
/* 133 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\auth\ConnectAccept.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */