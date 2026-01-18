/*     */ package com.hypixel.hytale.protocol.packets.serveraccess;
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
/*     */ public class SetServerAccess
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 252;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 16384007;
/*     */   
/*     */   public int getId() {
/*  25 */     return 252;
/*     */   }
/*     */   @Nonnull
/*  28 */   public Access access = Access.Private;
/*     */   
/*     */   @Nullable
/*     */   public String password;
/*     */ 
/*     */   
/*     */   public SetServerAccess(@Nonnull Access access, @Nullable String password) {
/*  35 */     this.access = access;
/*  36 */     this.password = password;
/*     */   }
/*     */   
/*     */   public SetServerAccess(@Nonnull SetServerAccess other) {
/*  40 */     this.access = other.access;
/*  41 */     this.password = other.password;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SetServerAccess deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     SetServerAccess obj = new SetServerAccess();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.access = Access.fromValue(buf.getByte(offset + 1));
/*     */     
/*  50 */     int pos = offset + 2;
/*  51 */     if ((nullBits & 0x1) != 0) { int passwordLen = VarInt.peek(buf, pos);
/*  52 */       if (passwordLen < 0) throw ProtocolException.negativeLength("Password", passwordLen); 
/*  53 */       if (passwordLen > 4096000) throw ProtocolException.stringTooLong("Password", passwordLen, 4096000); 
/*  54 */       int passwordVarLen = VarInt.length(buf, pos);
/*  55 */       obj.password = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  56 */       pos += passwordVarLen + passwordLen; }
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
/*  72 */     if (this.password != null) nullBits = (byte)(nullBits | 0x1); 
/*  73 */     buf.writeByte(nullBits);
/*     */     
/*  75 */     buf.writeByte(this.access.getValue());
/*     */     
/*  77 */     if (this.password != null) PacketIO.writeVarString(buf, this.password, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  82 */     int size = 2;
/*  83 */     if (this.password != null) size += PacketIO.stringSize(this.password);
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
/*  98 */       int passwordLen = VarInt.peek(buffer, pos);
/*  99 */       if (passwordLen < 0) {
/* 100 */         return ValidationResult.error("Invalid string length for Password");
/*     */       }
/* 102 */       if (passwordLen > 4096000) {
/* 103 */         return ValidationResult.error("Password exceeds max length 4096000");
/*     */       }
/* 105 */       pos += VarInt.length(buffer, pos);
/* 106 */       pos += passwordLen;
/* 107 */       if (pos > buffer.writerIndex()) {
/* 108 */         return ValidationResult.error("Buffer overflow reading Password");
/*     */       }
/*     */     } 
/* 111 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SetServerAccess clone() {
/* 115 */     SetServerAccess copy = new SetServerAccess();
/* 116 */     copy.access = this.access;
/* 117 */     copy.password = this.password;
/* 118 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SetServerAccess other;
/* 124 */     if (this == obj) return true; 
/* 125 */     if (obj instanceof SetServerAccess) { other = (SetServerAccess)obj; } else { return false; }
/* 126 */      return (Objects.equals(this.access, other.access) && Objects.equals(this.password, other.password));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 131 */     return Objects.hash(new Object[] { this.access, this.password });
/*     */   }
/*     */   
/*     */   public SetServerAccess() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\serveraccess\SetServerAccess.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */