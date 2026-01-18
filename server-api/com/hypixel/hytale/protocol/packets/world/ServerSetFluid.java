/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ServerSetFluid
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 142;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 17;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 17;
/*     */   public static final int MAX_SIZE = 17;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   public int fluidId;
/*     */   public byte fluidLevel;
/*     */   
/*     */   public int getId() {
/*  25 */     return 142;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSetFluid() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSetFluid(int x, int y, int z, int fluidId, byte fluidLevel) {
/*  38 */     this.x = x;
/*  39 */     this.y = y;
/*  40 */     this.z = z;
/*  41 */     this.fluidId = fluidId;
/*  42 */     this.fluidLevel = fluidLevel;
/*     */   }
/*     */   
/*     */   public ServerSetFluid(@Nonnull ServerSetFluid other) {
/*  46 */     this.x = other.x;
/*  47 */     this.y = other.y;
/*  48 */     this.z = other.z;
/*  49 */     this.fluidId = other.fluidId;
/*  50 */     this.fluidLevel = other.fluidLevel;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ServerSetFluid deserialize(@Nonnull ByteBuf buf, int offset) {
/*  55 */     ServerSetFluid obj = new ServerSetFluid();
/*     */     
/*  57 */     obj.x = buf.getIntLE(offset + 0);
/*  58 */     obj.y = buf.getIntLE(offset + 4);
/*  59 */     obj.z = buf.getIntLE(offset + 8);
/*  60 */     obj.fluidId = buf.getIntLE(offset + 12);
/*  61 */     obj.fluidLevel = buf.getByte(offset + 16);
/*     */ 
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     return 17;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     buf.writeIntLE(this.x);
/*  75 */     buf.writeIntLE(this.y);
/*  76 */     buf.writeIntLE(this.z);
/*  77 */     buf.writeIntLE(this.fluidId);
/*  78 */     buf.writeByte(this.fluidLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  84 */     return 17;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  88 */     if (buffer.readableBytes() - offset < 17) {
/*  89 */       return ValidationResult.error("Buffer too small: expected at least 17 bytes");
/*     */     }
/*     */ 
/*     */     
/*  93 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ServerSetFluid clone() {
/*  97 */     ServerSetFluid copy = new ServerSetFluid();
/*  98 */     copy.x = this.x;
/*  99 */     copy.y = this.y;
/* 100 */     copy.z = this.z;
/* 101 */     copy.fluidId = this.fluidId;
/* 102 */     copy.fluidLevel = this.fluidLevel;
/* 103 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ServerSetFluid other;
/* 109 */     if (this == obj) return true; 
/* 110 */     if (obj instanceof ServerSetFluid) { other = (ServerSetFluid)obj; } else { return false; }
/* 111 */      return (this.x == other.x && this.y == other.y && this.z == other.z && this.fluidId == other.fluidId && this.fluidLevel == other.fluidLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 116 */     return Objects.hash(new Object[] { Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z), Integer.valueOf(this.fluidId), Byte.valueOf(this.fluidLevel) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\ServerSetFluid.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */