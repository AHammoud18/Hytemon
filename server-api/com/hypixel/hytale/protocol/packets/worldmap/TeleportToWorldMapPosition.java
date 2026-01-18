/*    */ package com.hypixel.hytale.protocol.packets.worldmap;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TeleportToWorldMapPosition
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 245;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public int x;
/*    */   public int y;
/*    */   
/*    */   public int getId() {
/* 25 */     return 245;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TeleportToWorldMapPosition() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public TeleportToWorldMapPosition(int x, int y) {
/* 35 */     this.x = x;
/* 36 */     this.y = y;
/*    */   }
/*    */   
/*    */   public TeleportToWorldMapPosition(@Nonnull TeleportToWorldMapPosition other) {
/* 40 */     this.x = other.x;
/* 41 */     this.y = other.y;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static TeleportToWorldMapPosition deserialize(@Nonnull ByteBuf buf, int offset) {
/* 46 */     TeleportToWorldMapPosition obj = new TeleportToWorldMapPosition();
/*    */     
/* 48 */     obj.x = buf.getIntLE(offset + 0);
/* 49 */     obj.y = buf.getIntLE(offset + 4);
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
/* 62 */     buf.writeIntLE(this.x);
/* 63 */     buf.writeIntLE(this.y);
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
/*    */   public TeleportToWorldMapPosition clone() {
/* 82 */     TeleportToWorldMapPosition copy = new TeleportToWorldMapPosition();
/* 83 */     copy.x = this.x;
/* 84 */     copy.y = this.y;
/* 85 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     TeleportToWorldMapPosition other;
/* 91 */     if (this == obj) return true; 
/* 92 */     if (obj instanceof TeleportToWorldMapPosition) { other = (TeleportToWorldMapPosition)obj; } else { return false; }
/* 93 */      return (this.x == other.x && this.y == other.y);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return Objects.hash(new Object[] { Integer.valueOf(this.x), Integer.valueOf(this.y) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\worldmap\TeleportToWorldMapPosition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */