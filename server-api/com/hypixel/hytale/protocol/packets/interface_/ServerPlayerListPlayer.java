/*     */ package com.hypixel.hytale.protocol.packets.interface_;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ServerPlayerListPlayer {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 37;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 37;
/*     */   public static final int MAX_SIZE = 16384042;
/*     */   @Nonnull
/*  20 */   public UUID uuid = new UUID(0L, 0L);
/*     */   
/*     */   @Nullable
/*     */   public String username;
/*     */   @Nullable
/*     */   public UUID worldUuid;
/*     */   public int ping;
/*     */   
/*     */   public ServerPlayerListPlayer(@Nonnull UUID uuid, @Nullable String username, @Nullable UUID worldUuid, int ping) {
/*  29 */     this.uuid = uuid;
/*  30 */     this.username = username;
/*  31 */     this.worldUuid = worldUuid;
/*  32 */     this.ping = ping;
/*     */   }
/*     */   
/*     */   public ServerPlayerListPlayer(@Nonnull ServerPlayerListPlayer other) {
/*  36 */     this.uuid = other.uuid;
/*  37 */     this.username = other.username;
/*  38 */     this.worldUuid = other.worldUuid;
/*  39 */     this.ping = other.ping;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ServerPlayerListPlayer deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     ServerPlayerListPlayer obj = new ServerPlayerListPlayer();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.uuid = PacketIO.readUUID(buf, offset + 1);
/*  47 */     if ((nullBits & 0x2) != 0) obj.worldUuid = PacketIO.readUUID(buf, offset + 17); 
/*  48 */     obj.ping = buf.getIntLE(offset + 33);
/*     */     
/*  50 */     int pos = offset + 37;
/*  51 */     if ((nullBits & 0x1) != 0) { int usernameLen = VarInt.peek(buf, pos);
/*  52 */       if (usernameLen < 0) throw ProtocolException.negativeLength("Username", usernameLen); 
/*  53 */       if (usernameLen > 4096000) throw ProtocolException.stringTooLong("Username", usernameLen, 4096000); 
/*  54 */       int usernameVarLen = VarInt.length(buf, pos);
/*  55 */       obj.username = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  56 */       pos += usernameVarLen + usernameLen; }
/*     */     
/*  58 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  62 */     byte nullBits = buf.getByte(offset);
/*  63 */     int pos = offset + 37;
/*  64 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  65 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  70 */     byte nullBits = 0;
/*  71 */     if (this.username != null) nullBits = (byte)(nullBits | 0x1); 
/*  72 */     if (this.worldUuid != null) nullBits = (byte)(nullBits | 0x2); 
/*  73 */     buf.writeByte(nullBits);
/*     */     
/*  75 */     PacketIO.writeUUID(buf, this.uuid);
/*  76 */     if (this.worldUuid != null) { PacketIO.writeUUID(buf, this.worldUuid); } else { buf.writeZero(16); }
/*  77 */      buf.writeIntLE(this.ping);
/*     */     
/*  79 */     if (this.username != null) PacketIO.writeVarString(buf, this.username, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  84 */     int size = 37;
/*  85 */     if (this.username != null) size += PacketIO.stringSize(this.username);
/*     */     
/*  87 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  91 */     if (buffer.readableBytes() - offset < 37) {
/*  92 */       return ValidationResult.error("Buffer too small: expected at least 37 bytes");
/*     */     }
/*     */     
/*  95 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  97 */     int pos = offset + 37;
/*     */     
/*  99 */     if ((nullBits & 0x1) != 0) {
/* 100 */       int usernameLen = VarInt.peek(buffer, pos);
/* 101 */       if (usernameLen < 0) {
/* 102 */         return ValidationResult.error("Invalid string length for Username");
/*     */       }
/* 104 */       if (usernameLen > 4096000) {
/* 105 */         return ValidationResult.error("Username exceeds max length 4096000");
/*     */       }
/* 107 */       pos += VarInt.length(buffer, pos);
/* 108 */       pos += usernameLen;
/* 109 */       if (pos > buffer.writerIndex()) {
/* 110 */         return ValidationResult.error("Buffer overflow reading Username");
/*     */       }
/*     */     } 
/* 113 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ServerPlayerListPlayer clone() {
/* 117 */     ServerPlayerListPlayer copy = new ServerPlayerListPlayer();
/* 118 */     copy.uuid = this.uuid;
/* 119 */     copy.username = this.username;
/* 120 */     copy.worldUuid = this.worldUuid;
/* 121 */     copy.ping = this.ping;
/* 122 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ServerPlayerListPlayer other;
/* 128 */     if (this == obj) return true; 
/* 129 */     if (obj instanceof ServerPlayerListPlayer) { other = (ServerPlayerListPlayer)obj; } else { return false; }
/* 130 */      return (Objects.equals(this.uuid, other.uuid) && Objects.equals(this.username, other.username) && Objects.equals(this.worldUuid, other.worldUuid) && this.ping == other.ping);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 135 */     return Objects.hash(new Object[] { this.uuid, this.username, this.worldUuid, Integer.valueOf(this.ping) });
/*     */   }
/*     */   
/*     */   public ServerPlayerListPlayer() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\ServerPlayerListPlayer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */