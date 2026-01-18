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
/*     */ public class PasswordRejected implements Packet {
/*     */   public static final int PACKET_ID = 17;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 74;
/*     */   @Nullable
/*     */   public byte[] newChallenge;
/*     */   public int attemptsRemaining;
/*     */   
/*     */   public int getId() {
/*  25 */     return 17;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PasswordRejected() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public PasswordRejected(@Nullable byte[] newChallenge, int attemptsRemaining) {
/*  35 */     this.newChallenge = newChallenge;
/*  36 */     this.attemptsRemaining = attemptsRemaining;
/*     */   }
/*     */   
/*     */   public PasswordRejected(@Nonnull PasswordRejected other) {
/*  40 */     this.newChallenge = other.newChallenge;
/*  41 */     this.attemptsRemaining = other.attemptsRemaining;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PasswordRejected deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     PasswordRejected obj = new PasswordRejected();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.attemptsRemaining = buf.getIntLE(offset + 1);
/*     */     
/*  50 */     int pos = offset + 5;
/*  51 */     if ((nullBits & 0x1) != 0) { int newChallengeCount = VarInt.peek(buf, pos);
/*  52 */       if (newChallengeCount < 0) throw ProtocolException.negativeLength("NewChallenge", newChallengeCount); 
/*  53 */       if (newChallengeCount > 64) throw ProtocolException.arrayTooLong("NewChallenge", newChallengeCount, 64); 
/*  54 */       int newChallengeVarLen = VarInt.size(newChallengeCount);
/*  55 */       if ((pos + newChallengeVarLen) + newChallengeCount * 1L > buf.readableBytes())
/*  56 */         throw ProtocolException.bufferTooSmall("NewChallenge", pos + newChallengeVarLen + newChallengeCount * 1, buf.readableBytes()); 
/*  57 */       pos += newChallengeVarLen;
/*  58 */       obj.newChallenge = new byte[newChallengeCount];
/*  59 */       for (int i = 0; i < newChallengeCount; i++) {
/*  60 */         obj.newChallenge[i] = buf.getByte(pos + i * 1);
/*     */       }
/*  62 */       pos += newChallengeCount * 1; }
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     byte nullBits = buf.getByte(offset);
/*  69 */     int pos = offset + 5;
/*  70 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 1; }
/*  71 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  77 */     byte nullBits = 0;
/*  78 */     if (this.newChallenge != null) nullBits = (byte)(nullBits | 0x1); 
/*  79 */     buf.writeByte(nullBits);
/*     */     
/*  81 */     buf.writeIntLE(this.attemptsRemaining);
/*     */     
/*  83 */     if (this.newChallenge != null) { if (this.newChallenge.length > 64) throw ProtocolException.arrayTooLong("NewChallenge", this.newChallenge.length, 64);  VarInt.write(buf, this.newChallenge.length); for (byte item : this.newChallenge) buf.writeByte(item);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  88 */     int size = 5;
/*  89 */     if (this.newChallenge != null) size += VarInt.size(this.newChallenge.length) + this.newChallenge.length * 1;
/*     */     
/*  91 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  95 */     if (buffer.readableBytes() - offset < 5) {
/*  96 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/*  99 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 101 */     int pos = offset + 5;
/*     */     
/* 103 */     if ((nullBits & 0x1) != 0) {
/* 104 */       int newChallengeCount = VarInt.peek(buffer, pos);
/* 105 */       if (newChallengeCount < 0) {
/* 106 */         return ValidationResult.error("Invalid array count for NewChallenge");
/*     */       }
/* 108 */       if (newChallengeCount > 64) {
/* 109 */         return ValidationResult.error("NewChallenge exceeds max length 64");
/*     */       }
/* 111 */       pos += VarInt.length(buffer, pos);
/* 112 */       pos += newChallengeCount * 1;
/* 113 */       if (pos > buffer.writerIndex()) {
/* 114 */         return ValidationResult.error("Buffer overflow reading NewChallenge");
/*     */       }
/*     */     } 
/* 117 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public PasswordRejected clone() {
/* 121 */     PasswordRejected copy = new PasswordRejected();
/* 122 */     copy.newChallenge = (this.newChallenge != null) ? Arrays.copyOf(this.newChallenge, this.newChallenge.length) : null;
/* 123 */     copy.attemptsRemaining = this.attemptsRemaining;
/* 124 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     PasswordRejected other;
/* 130 */     if (this == obj) return true; 
/* 131 */     if (obj instanceof PasswordRejected) { other = (PasswordRejected)obj; } else { return false; }
/* 132 */      return (Arrays.equals(this.newChallenge, other.newChallenge) && this.attemptsRemaining == other.attemptsRemaining);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 137 */     int result = 1;
/* 138 */     result = 31 * result + Arrays.hashCode(this.newChallenge);
/* 139 */     result = 31 * result + Integer.hashCode(this.attemptsRemaining);
/* 140 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\auth\PasswordRejected.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */