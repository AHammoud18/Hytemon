/*     */ package com.hypixel.hytale.protocol.packets.interface_;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class UpdateServerPlayerListPing
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 227;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   
/*     */   public int getId() {
/*  26 */     return 227;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 1; public static final int VARIABLE_BLOCK_START = 1; public static final int MAX_SIZE = 81920006;
/*     */   @Nullable
/*     */   public Map<UUID, Integer> players;
/*     */   
/*     */   public UpdateServerPlayerListPing() {}
/*     */   
/*     */   public UpdateServerPlayerListPing(@Nullable Map<UUID, Integer> players) {
/*  35 */     this.players = players;
/*     */   }
/*     */   
/*     */   public UpdateServerPlayerListPing(@Nonnull UpdateServerPlayerListPing other) {
/*  39 */     this.players = other.players;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateServerPlayerListPing deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     UpdateServerPlayerListPing obj = new UpdateServerPlayerListPing();
/*  45 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  47 */     int pos = offset + 1;
/*  48 */     if ((nullBits & 0x1) != 0) { int playersCount = VarInt.peek(buf, pos);
/*  49 */       if (playersCount < 0) throw ProtocolException.negativeLength("Players", playersCount); 
/*  50 */       if (playersCount > 4096000) throw ProtocolException.dictionaryTooLarge("Players", playersCount, 4096000); 
/*  51 */       pos += VarInt.size(playersCount);
/*  52 */       obj.players = new HashMap<>(playersCount);
/*  53 */       for (int i = 0; i < playersCount; i++) {
/*  54 */         UUID key = PacketIO.readUUID(buf, pos); pos += 16;
/*  55 */         int val = buf.getIntLE(pos); pos += 4;
/*  56 */         if (obj.players.put(key, Integer.valueOf(val)) != null)
/*  57 */           throw ProtocolException.duplicateKey("players", key); 
/*     */       }  }
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int pos = offset + 1;
/*  66 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  67 */       for (int i = 0; i < dictLen; ) { pos += 16; pos += 4; i++; }  }
/*  68 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     byte nullBits = 0;
/*  75 */     if (this.players != null) nullBits = (byte)(nullBits | 0x1); 
/*  76 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  79 */     if (this.players != null) { if (this.players.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Players", this.players.size(), 4096000);  VarInt.write(buf, this.players.size()); for (Map.Entry<UUID, Integer> e : this.players.entrySet()) { PacketIO.writeUUID(buf, e.getKey()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  84 */     int size = 1;
/*  85 */     if (this.players != null) size += VarInt.size(this.players.size()) + this.players.size() * 20;
/*     */     
/*  87 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  91 */     if (buffer.readableBytes() - offset < 1) {
/*  92 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  95 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  97 */     int pos = offset + 1;
/*     */     
/*  99 */     if ((nullBits & 0x1) != 0) {
/* 100 */       int playersCount = VarInt.peek(buffer, pos);
/* 101 */       if (playersCount < 0) {
/* 102 */         return ValidationResult.error("Invalid dictionary count for Players");
/*     */       }
/* 104 */       if (playersCount > 4096000) {
/* 105 */         return ValidationResult.error("Players exceeds max length 4096000");
/*     */       }
/* 107 */       pos += VarInt.length(buffer, pos);
/* 108 */       for (int i = 0; i < playersCount; i++) {
/* 109 */         pos += 16;
/* 110 */         if (pos > buffer.writerIndex()) {
/* 111 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 113 */         pos += 4;
/* 114 */         if (pos > buffer.writerIndex()) {
/* 115 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 119 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateServerPlayerListPing clone() {
/* 123 */     UpdateServerPlayerListPing copy = new UpdateServerPlayerListPing();
/* 124 */     copy.players = (this.players != null) ? new HashMap<>(this.players) : null;
/* 125 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateServerPlayerListPing other;
/* 131 */     if (this == obj) return true; 
/* 132 */     if (obj instanceof UpdateServerPlayerListPing) { other = (UpdateServerPlayerListPing)obj; } else { return false; }
/* 133 */      return Objects.equals(this.players, other.players);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 138 */     return Objects.hash(new Object[] { this.players });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\UpdateServerPlayerListPing.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */