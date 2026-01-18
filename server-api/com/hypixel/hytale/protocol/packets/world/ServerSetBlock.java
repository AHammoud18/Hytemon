/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ServerSetBlock implements Packet {
/*     */   public static final int PACKET_ID = 140;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 19;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 19;
/*     */   public static final int MAX_SIZE = 19;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   public int blockId;
/*     */   public short filler;
/*     */   public byte rotation;
/*     */   
/*     */   public int getId() {
/*  25 */     return 140;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSetBlock() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSetBlock(int x, int y, int z, int blockId, short filler, byte rotation) {
/*  39 */     this.x = x;
/*  40 */     this.y = y;
/*  41 */     this.z = z;
/*  42 */     this.blockId = blockId;
/*  43 */     this.filler = filler;
/*  44 */     this.rotation = rotation;
/*     */   }
/*     */   
/*     */   public ServerSetBlock(@Nonnull ServerSetBlock other) {
/*  48 */     this.x = other.x;
/*  49 */     this.y = other.y;
/*  50 */     this.z = other.z;
/*  51 */     this.blockId = other.blockId;
/*  52 */     this.filler = other.filler;
/*  53 */     this.rotation = other.rotation;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ServerSetBlock deserialize(@Nonnull ByteBuf buf, int offset) {
/*  58 */     ServerSetBlock obj = new ServerSetBlock();
/*     */     
/*  60 */     obj.x = buf.getIntLE(offset + 0);
/*  61 */     obj.y = buf.getIntLE(offset + 4);
/*  62 */     obj.z = buf.getIntLE(offset + 8);
/*  63 */     obj.blockId = buf.getIntLE(offset + 12);
/*  64 */     obj.filler = buf.getShortLE(offset + 16);
/*  65 */     obj.rotation = buf.getByte(offset + 18);
/*     */ 
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     return 19;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  78 */     buf.writeIntLE(this.x);
/*  79 */     buf.writeIntLE(this.y);
/*  80 */     buf.writeIntLE(this.z);
/*  81 */     buf.writeIntLE(this.blockId);
/*  82 */     buf.writeShortLE(this.filler);
/*  83 */     buf.writeByte(this.rotation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  89 */     return 19;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  93 */     if (buffer.readableBytes() - offset < 19) {
/*  94 */       return ValidationResult.error("Buffer too small: expected at least 19 bytes");
/*     */     }
/*     */ 
/*     */     
/*  98 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ServerSetBlock clone() {
/* 102 */     ServerSetBlock copy = new ServerSetBlock();
/* 103 */     copy.x = this.x;
/* 104 */     copy.y = this.y;
/* 105 */     copy.z = this.z;
/* 106 */     copy.blockId = this.blockId;
/* 107 */     copy.filler = this.filler;
/* 108 */     copy.rotation = this.rotation;
/* 109 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ServerSetBlock other;
/* 115 */     if (this == obj) return true; 
/* 116 */     if (obj instanceof ServerSetBlock) { other = (ServerSetBlock)obj; } else { return false; }
/* 117 */      return (this.x == other.x && this.y == other.y && this.z == other.z && this.blockId == other.blockId && this.filler == other.filler && this.rotation == other.rotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return Objects.hash(new Object[] { Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z), Integer.valueOf(this.blockId), Short.valueOf(this.filler), Byte.valueOf(this.rotation) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\ServerSetBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */