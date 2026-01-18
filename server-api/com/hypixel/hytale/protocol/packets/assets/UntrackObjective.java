/*    */ package com.hypixel.hytale.protocol.packets.assets;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.PacketIO;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UntrackObjective
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 70;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 16;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 16;
/*    */   public static final int MAX_SIZE = 16;
/*    */   
/*    */   public int getId() {
/* 25 */     return 70;
/*    */   }
/*    */   @Nonnull
/* 28 */   public UUID objectiveUuid = new UUID(0L, 0L);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UntrackObjective(@Nonnull UUID objectiveUuid) {
/* 34 */     this.objectiveUuid = objectiveUuid;
/*    */   }
/*    */   
/*    */   public UntrackObjective(@Nonnull UntrackObjective other) {
/* 38 */     this.objectiveUuid = other.objectiveUuid;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static UntrackObjective deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     UntrackObjective obj = new UntrackObjective();
/*    */     
/* 45 */     obj.objectiveUuid = PacketIO.readUUID(buf, offset + 0);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 16;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 58 */     PacketIO.writeUUID(buf, this.objectiveUuid);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 64 */     return 16;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 68 */     if (buffer.readableBytes() - offset < 16) {
/* 69 */       return ValidationResult.error("Buffer too small: expected at least 16 bytes");
/*    */     }
/*    */ 
/*    */     
/* 73 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public UntrackObjective clone() {
/* 77 */     UntrackObjective copy = new UntrackObjective();
/* 78 */     copy.objectiveUuid = this.objectiveUuid;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     UntrackObjective other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof UntrackObjective) { other = (UntrackObjective)obj; } else { return false; }
/* 87 */      return Objects.equals(this.objectiveUuid, other.objectiveUuid);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { this.objectiveUuid });
/*    */   }
/*    */   
/*    */   public UntrackObjective() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UntrackObjective.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */