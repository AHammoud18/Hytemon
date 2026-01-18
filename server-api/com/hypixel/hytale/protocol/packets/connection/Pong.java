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
/*     */ public class Pong
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 3;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 20;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 20;
/*     */   public static final int MAX_SIZE = 20;
/*     */   public int id;
/*     */   @Nullable
/*     */   public InstantData time;
/*     */   
/*     */   public int getId() {
/*  25 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*  30 */   public PongType type = PongType.Raw;
/*     */ 
/*     */   
/*     */   public short packetQueueSize;
/*     */ 
/*     */   
/*     */   public Pong(int id, @Nullable InstantData time, @Nonnull PongType type, short packetQueueSize) {
/*  37 */     this.id = id;
/*  38 */     this.time = time;
/*  39 */     this.type = type;
/*  40 */     this.packetQueueSize = packetQueueSize;
/*     */   }
/*     */   
/*     */   public Pong(@Nonnull Pong other) {
/*  44 */     this.id = other.id;
/*  45 */     this.time = other.time;
/*  46 */     this.type = other.type;
/*  47 */     this.packetQueueSize = other.packetQueueSize;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Pong deserialize(@Nonnull ByteBuf buf, int offset) {
/*  52 */     Pong obj = new Pong();
/*  53 */     byte nullBits = buf.getByte(offset);
/*  54 */     obj.id = buf.getIntLE(offset + 1);
/*  55 */     if ((nullBits & 0x1) != 0) obj.time = InstantData.deserialize(buf, offset + 5); 
/*  56 */     obj.type = PongType.fromValue(buf.getByte(offset + 17));
/*  57 */     obj.packetQueueSize = buf.getShortLE(offset + 18);
/*     */ 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     return 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  69 */     byte nullBits = 0;
/*  70 */     if (this.time != null) nullBits = (byte)(nullBits | 0x1); 
/*  71 */     buf.writeByte(nullBits);
/*     */     
/*  73 */     buf.writeIntLE(this.id);
/*  74 */     if (this.time != null) { this.time.serialize(buf); } else { buf.writeZero(12); }
/*  75 */      buf.writeByte(this.type.getValue());
/*  76 */     buf.writeShortLE(this.packetQueueSize);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  82 */     return 20;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  86 */     if (buffer.readableBytes() - offset < 20) {
/*  87 */       return ValidationResult.error("Buffer too small: expected at least 20 bytes");
/*     */     }
/*     */ 
/*     */     
/*  91 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Pong clone() {
/*  95 */     Pong copy = new Pong();
/*  96 */     copy.id = this.id;
/*  97 */     copy.time = (this.time != null) ? this.time.clone() : null;
/*  98 */     copy.type = this.type;
/*  99 */     copy.packetQueueSize = this.packetQueueSize;
/* 100 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Pong other;
/* 106 */     if (this == obj) return true; 
/* 107 */     if (obj instanceof Pong) { other = (Pong)obj; } else { return false; }
/* 108 */      return (this.id == other.id && Objects.equals(this.time, other.time) && Objects.equals(this.type, other.type) && this.packetQueueSize == other.packetQueueSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return Objects.hash(new Object[] { Integer.valueOf(this.id), this.time, this.type, Short.valueOf(this.packetQueueSize) });
/*     */   }
/*     */   
/*     */   public Pong() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\connection\Pong.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */