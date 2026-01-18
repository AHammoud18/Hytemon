/*     */ package com.hypixel.hytale.protocol.packets.connection;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.InstantData;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Ping
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 2;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 29;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 29;
/*     */   
/*     */   public int getId() {
/*  25 */     return 2;
/*     */   }
/*     */   public static final int MAX_SIZE = 29;
/*     */   public int id;
/*     */   @Nullable
/*     */   public InstantData time;
/*     */   public int lastPingValueRaw;
/*     */   public int lastPingValueDirect;
/*     */   public int lastPingValueTick;
/*     */   
/*     */   public Ping() {}
/*     */   
/*     */   public Ping(int id, @Nullable InstantData time, int lastPingValueRaw, int lastPingValueDirect, int lastPingValueTick) {
/*  38 */     this.id = id;
/*  39 */     this.time = time;
/*  40 */     this.lastPingValueRaw = lastPingValueRaw;
/*  41 */     this.lastPingValueDirect = lastPingValueDirect;
/*  42 */     this.lastPingValueTick = lastPingValueTick;
/*     */   }
/*     */   
/*     */   public Ping(@Nonnull Ping other) {
/*  46 */     this.id = other.id;
/*  47 */     this.time = other.time;
/*  48 */     this.lastPingValueRaw = other.lastPingValueRaw;
/*  49 */     this.lastPingValueDirect = other.lastPingValueDirect;
/*  50 */     this.lastPingValueTick = other.lastPingValueTick;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Ping deserialize(@Nonnull ByteBuf buf, int offset) {
/*  55 */     Ping obj = new Ping();
/*  56 */     byte nullBits = buf.getByte(offset);
/*  57 */     obj.id = buf.getIntLE(offset + 1);
/*  58 */     if ((nullBits & 0x1) != 0) obj.time = InstantData.deserialize(buf, offset + 5); 
/*  59 */     obj.lastPingValueRaw = buf.getIntLE(offset + 17);
/*  60 */     obj.lastPingValueDirect = buf.getIntLE(offset + 21);
/*  61 */     obj.lastPingValueTick = buf.getIntLE(offset + 25);
/*     */ 
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     return 29;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  73 */     byte nullBits = 0;
/*  74 */     if (this.time != null) nullBits = (byte)(nullBits | 0x1); 
/*  75 */     buf.writeByte(nullBits);
/*     */     
/*  77 */     buf.writeIntLE(this.id);
/*  78 */     if (this.time != null) { this.time.serialize(buf); } else { buf.writeZero(12); }
/*  79 */      buf.writeIntLE(this.lastPingValueRaw);
/*  80 */     buf.writeIntLE(this.lastPingValueDirect);
/*  81 */     buf.writeIntLE(this.lastPingValueTick);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  87 */     return 29;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  91 */     if (buffer.readableBytes() - offset < 29) {
/*  92 */       return ValidationResult.error("Buffer too small: expected at least 29 bytes");
/*     */     }
/*     */ 
/*     */     
/*  96 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Ping clone() {
/* 100 */     Ping copy = new Ping();
/* 101 */     copy.id = this.id;
/* 102 */     copy.time = (this.time != null) ? this.time.clone() : null;
/* 103 */     copy.lastPingValueRaw = this.lastPingValueRaw;
/* 104 */     copy.lastPingValueDirect = this.lastPingValueDirect;
/* 105 */     copy.lastPingValueTick = this.lastPingValueTick;
/* 106 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Ping other;
/* 112 */     if (this == obj) return true; 
/* 113 */     if (obj instanceof Ping) { other = (Ping)obj; } else { return false; }
/* 114 */      return (this.id == other.id && Objects.equals(this.time, other.time) && this.lastPingValueRaw == other.lastPingValueRaw && this.lastPingValueDirect == other.lastPingValueDirect && this.lastPingValueTick == other.lastPingValueTick);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 119 */     return Objects.hash(new Object[] { Integer.valueOf(this.id), this.time, Integer.valueOf(this.lastPingValueRaw), Integer.valueOf(this.lastPingValueDirect), Integer.valueOf(this.lastPingValueTick) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\connection\Ping.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */