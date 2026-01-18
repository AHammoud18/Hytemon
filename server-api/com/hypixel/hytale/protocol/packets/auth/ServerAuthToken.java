/*     */ package com.hypixel.hytale.protocol.packets.auth;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerAuthToken
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 13;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   
/*     */   public int getId() {
/*  25 */     return 13;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 2; public static final int VARIABLE_BLOCK_START = 9; public static final int MAX_SIZE = 32851; @Nullable
/*     */   public String serverAccessToken;
/*     */   @Nullable
/*     */   public byte[] passwordChallenge;
/*     */   
/*     */   public ServerAuthToken() {}
/*     */   
/*     */   public ServerAuthToken(@Nullable String serverAccessToken, @Nullable byte[] passwordChallenge) {
/*  35 */     this.serverAccessToken = serverAccessToken;
/*  36 */     this.passwordChallenge = passwordChallenge;
/*     */   }
/*     */   
/*     */   public ServerAuthToken(@Nonnull ServerAuthToken other) {
/*  40 */     this.serverAccessToken = other.serverAccessToken;
/*  41 */     this.passwordChallenge = other.passwordChallenge;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ServerAuthToken deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     ServerAuthToken obj = new ServerAuthToken();
/*  47 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  51 */       int serverAccessTokenLen = VarInt.peek(buf, varPos0);
/*  52 */       if (serverAccessTokenLen < 0) throw ProtocolException.negativeLength("ServerAccessToken", serverAccessTokenLen); 
/*  53 */       if (serverAccessTokenLen > 8192) throw ProtocolException.stringTooLong("ServerAccessToken", serverAccessTokenLen, 8192); 
/*  54 */       obj.serverAccessToken = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  56 */     if ((nullBits & 0x2) != 0) {
/*  57 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  58 */       int passwordChallengeCount = VarInt.peek(buf, varPos1);
/*  59 */       if (passwordChallengeCount < 0) throw ProtocolException.negativeLength("PasswordChallenge", passwordChallengeCount); 
/*  60 */       if (passwordChallengeCount > 64) throw ProtocolException.arrayTooLong("PasswordChallenge", passwordChallengeCount, 64); 
/*  61 */       int varIntLen = VarInt.length(buf, varPos1);
/*  62 */       if ((varPos1 + varIntLen) + passwordChallengeCount * 1L > buf.readableBytes())
/*  63 */         throw ProtocolException.bufferTooSmall("PasswordChallenge", varPos1 + varIntLen + passwordChallengeCount * 1, buf.readableBytes()); 
/*  64 */       obj.passwordChallenge = new byte[passwordChallengeCount];
/*  65 */       for (int i = 0; i < passwordChallengeCount; i++) {
/*  66 */         obj.passwordChallenge[i] = buf.getByte(varPos1 + varIntLen + i * 1);
/*     */       }
/*     */     } 
/*     */     
/*  70 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  74 */     byte nullBits = buf.getByte(offset);
/*  75 */     int maxEnd = 9;
/*  76 */     if ((nullBits & 0x1) != 0) {
/*  77 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  78 */       int pos0 = offset + 9 + fieldOffset0;
/*  79 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  80 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  82 */     if ((nullBits & 0x2) != 0) {
/*  83 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  84 */       int pos1 = offset + 9 + fieldOffset1;
/*  85 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + arrLen * 1;
/*  86 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  88 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  94 */     int startPos = buf.writerIndex();
/*  95 */     byte nullBits = 0;
/*  96 */     if (this.serverAccessToken != null) nullBits = (byte)(nullBits | 0x1); 
/*  97 */     if (this.passwordChallenge != null) nullBits = (byte)(nullBits | 0x2); 
/*  98 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 101 */     int serverAccessTokenOffsetSlot = buf.writerIndex();
/* 102 */     buf.writeIntLE(0);
/* 103 */     int passwordChallengeOffsetSlot = buf.writerIndex();
/* 104 */     buf.writeIntLE(0);
/*     */     
/* 106 */     int varBlockStart = buf.writerIndex();
/* 107 */     if (this.serverAccessToken != null) {
/* 108 */       buf.setIntLE(serverAccessTokenOffsetSlot, buf.writerIndex() - varBlockStart);
/* 109 */       PacketIO.writeVarString(buf, this.serverAccessToken, 8192);
/*     */     } else {
/* 111 */       buf.setIntLE(serverAccessTokenOffsetSlot, -1);
/*     */     } 
/* 113 */     if (this.passwordChallenge != null) {
/* 114 */       buf.setIntLE(passwordChallengeOffsetSlot, buf.writerIndex() - varBlockStart);
/* 115 */       if (this.passwordChallenge.length > 64) throw ProtocolException.arrayTooLong("PasswordChallenge", this.passwordChallenge.length, 64);  VarInt.write(buf, this.passwordChallenge.length); for (byte item : this.passwordChallenge) buf.writeByte(item); 
/*     */     } else {
/* 117 */       buf.setIntLE(passwordChallengeOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 123 */     int size = 9;
/* 124 */     if (this.serverAccessToken != null) size += PacketIO.stringSize(this.serverAccessToken); 
/* 125 */     if (this.passwordChallenge != null) size += VarInt.size(this.passwordChallenge.length) + this.passwordChallenge.length * 1;
/*     */     
/* 127 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 131 */     if (buffer.readableBytes() - offset < 9) {
/* 132 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 135 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 138 */     if ((nullBits & 0x1) != 0) {
/* 139 */       int serverAccessTokenOffset = buffer.getIntLE(offset + 1);
/* 140 */       if (serverAccessTokenOffset < 0) {
/* 141 */         return ValidationResult.error("Invalid offset for ServerAccessToken");
/*     */       }
/* 143 */       int pos = offset + 9 + serverAccessTokenOffset;
/* 144 */       if (pos >= buffer.writerIndex()) {
/* 145 */         return ValidationResult.error("Offset out of bounds for ServerAccessToken");
/*     */       }
/* 147 */       int serverAccessTokenLen = VarInt.peek(buffer, pos);
/* 148 */       if (serverAccessTokenLen < 0) {
/* 149 */         return ValidationResult.error("Invalid string length for ServerAccessToken");
/*     */       }
/* 151 */       if (serverAccessTokenLen > 8192) {
/* 152 */         return ValidationResult.error("ServerAccessToken exceeds max length 8192");
/*     */       }
/* 154 */       pos += VarInt.length(buffer, pos);
/* 155 */       pos += serverAccessTokenLen;
/* 156 */       if (pos > buffer.writerIndex()) {
/* 157 */         return ValidationResult.error("Buffer overflow reading ServerAccessToken");
/*     */       }
/*     */     } 
/*     */     
/* 161 */     if ((nullBits & 0x2) != 0) {
/* 162 */       int passwordChallengeOffset = buffer.getIntLE(offset + 5);
/* 163 */       if (passwordChallengeOffset < 0) {
/* 164 */         return ValidationResult.error("Invalid offset for PasswordChallenge");
/*     */       }
/* 166 */       int pos = offset + 9 + passwordChallengeOffset;
/* 167 */       if (pos >= buffer.writerIndex()) {
/* 168 */         return ValidationResult.error("Offset out of bounds for PasswordChallenge");
/*     */       }
/* 170 */       int passwordChallengeCount = VarInt.peek(buffer, pos);
/* 171 */       if (passwordChallengeCount < 0) {
/* 172 */         return ValidationResult.error("Invalid array count for PasswordChallenge");
/*     */       }
/* 174 */       if (passwordChallengeCount > 64) {
/* 175 */         return ValidationResult.error("PasswordChallenge exceeds max length 64");
/*     */       }
/* 177 */       pos += VarInt.length(buffer, pos);
/* 178 */       pos += passwordChallengeCount * 1;
/* 179 */       if (pos > buffer.writerIndex()) {
/* 180 */         return ValidationResult.error("Buffer overflow reading PasswordChallenge");
/*     */       }
/*     */     } 
/* 183 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ServerAuthToken clone() {
/* 187 */     ServerAuthToken copy = new ServerAuthToken();
/* 188 */     copy.serverAccessToken = this.serverAccessToken;
/* 189 */     copy.passwordChallenge = (this.passwordChallenge != null) ? Arrays.copyOf(this.passwordChallenge, this.passwordChallenge.length) : null;
/* 190 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ServerAuthToken other;
/* 196 */     if (this == obj) return true; 
/* 197 */     if (obj instanceof ServerAuthToken) { other = (ServerAuthToken)obj; } else { return false; }
/* 198 */      return (Objects.equals(this.serverAccessToken, other.serverAccessToken) && Arrays.equals(this.passwordChallenge, other.passwordChallenge));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 203 */     int result = 1;
/* 204 */     result = 31 * result + Objects.hashCode(this.serverAccessToken);
/* 205 */     result = 31 * result + Arrays.hashCode(this.passwordChallenge);
/* 206 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\auth\ServerAuthToken.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */