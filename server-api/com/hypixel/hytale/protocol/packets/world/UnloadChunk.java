/*    */ package com.hypixel.hytale.protocol.packets.world;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnloadChunk
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 135;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public int chunkX;
/*    */   public int chunkZ;
/*    */   
/*    */   public int getId() {
/* 25 */     return 135;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public UnloadChunk() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public UnloadChunk(int chunkX, int chunkZ) {
/* 35 */     this.chunkX = chunkX;
/* 36 */     this.chunkZ = chunkZ;
/*    */   }
/*    */   
/*    */   public UnloadChunk(@Nonnull UnloadChunk other) {
/* 40 */     this.chunkX = other.chunkX;
/* 41 */     this.chunkZ = other.chunkZ;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static UnloadChunk deserialize(@Nonnull ByteBuf buf, int offset) {
/* 46 */     UnloadChunk obj = new UnloadChunk();
/*    */     
/* 48 */     obj.chunkX = buf.getIntLE(offset + 0);
/* 49 */     obj.chunkZ = buf.getIntLE(offset + 4);
/*    */ 
/*    */     
/* 52 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 56 */     return 8;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 62 */     buf.writeIntLE(this.chunkX);
/* 63 */     buf.writeIntLE(this.chunkZ);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 69 */     return 8;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 73 */     if (buffer.readableBytes() - offset < 8) {
/* 74 */       return ValidationResult.error("Buffer too small: expected at least 8 bytes");
/*    */     }
/*    */ 
/*    */     
/* 78 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public UnloadChunk clone() {
/* 82 */     UnloadChunk copy = new UnloadChunk();
/* 83 */     copy.chunkX = this.chunkX;
/* 84 */     copy.chunkZ = this.chunkZ;
/* 85 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     UnloadChunk other;
/* 91 */     if (this == obj) return true; 
/* 92 */     if (obj instanceof UnloadChunk) { other = (UnloadChunk)obj; } else { return false; }
/* 93 */      return (this.chunkX == other.chunkX && this.chunkZ == other.chunkZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return Objects.hash(new Object[] { Integer.valueOf(this.chunkX), Integer.valueOf(this.chunkZ) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\UnloadChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */