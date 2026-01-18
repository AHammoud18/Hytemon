/*    */ package com.hypixel.hytale.protocol.packets.player;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.MovementSettings;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class UpdateMovementSettings
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 110;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 252;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 252;
/*    */   public static final int MAX_SIZE = 252;
/*    */   @Nullable
/*    */   public MovementSettings movementSettings;
/*    */   
/*    */   public int getId() {
/* 25 */     return 110;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public UpdateMovementSettings() {}
/*    */ 
/*    */   
/*    */   public UpdateMovementSettings(@Nullable MovementSettings movementSettings) {
/* 34 */     this.movementSettings = movementSettings;
/*    */   }
/*    */   
/*    */   public UpdateMovementSettings(@Nonnull UpdateMovementSettings other) {
/* 38 */     this.movementSettings = other.movementSettings;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static UpdateMovementSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     UpdateMovementSettings obj = new UpdateMovementSettings();
/* 44 */     byte nullBits = buf.getByte(offset);
/* 45 */     if ((nullBits & 0x1) != 0) obj.movementSettings = MovementSettings.deserialize(buf, offset + 1);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 252;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     byte nullBits = 0;
/* 58 */     if (this.movementSettings != null) nullBits = (byte)(nullBits | 0x1); 
/* 59 */     buf.writeByte(nullBits);
/*    */     
/* 61 */     if (this.movementSettings != null) { this.movementSettings.serialize(buf); } else { buf.writeZero(251); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 67 */     return 252;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 71 */     if (buffer.readableBytes() - offset < 252) {
/* 72 */       return ValidationResult.error("Buffer too small: expected at least 252 bytes");
/*    */     }
/*    */ 
/*    */     
/* 76 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public UpdateMovementSettings clone() {
/* 80 */     UpdateMovementSettings copy = new UpdateMovementSettings();
/* 81 */     copy.movementSettings = (this.movementSettings != null) ? this.movementSettings.clone() : null;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     UpdateMovementSettings other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof UpdateMovementSettings) { other = (UpdateMovementSettings)obj; } else { return false; }
/* 90 */      return Objects.equals(this.movementSettings, other.movementSettings);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { this.movementSettings });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\player\UpdateMovementSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */