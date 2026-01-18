/*    */ package com.hypixel.hytale.protocol.packets.player;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.SavedMovementStates;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SetMovementStates
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 102;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 2;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 2;
/*    */   public static final int MAX_SIZE = 2;
/*    */   @Nullable
/*    */   public SavedMovementStates movementStates;
/*    */   
/*    */   public int getId() {
/* 25 */     return 102;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SetMovementStates() {}
/*    */ 
/*    */   
/*    */   public SetMovementStates(@Nullable SavedMovementStates movementStates) {
/* 34 */     this.movementStates = movementStates;
/*    */   }
/*    */   
/*    */   public SetMovementStates(@Nonnull SetMovementStates other) {
/* 38 */     this.movementStates = other.movementStates;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SetMovementStates deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     SetMovementStates obj = new SetMovementStates();
/* 44 */     byte nullBits = buf.getByte(offset);
/* 45 */     if ((nullBits & 0x1) != 0) obj.movementStates = SavedMovementStates.deserialize(buf, offset + 1);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     byte nullBits = 0;
/* 58 */     if (this.movementStates != null) nullBits = (byte)(nullBits | 0x1); 
/* 59 */     buf.writeByte(nullBits);
/*    */     
/* 61 */     if (this.movementStates != null) { this.movementStates.serialize(buf); } else { buf.writeZero(1); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 67 */     return 2;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 71 */     if (buffer.readableBytes() - offset < 2) {
/* 72 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*    */     }
/*    */ 
/*    */     
/* 76 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public SetMovementStates clone() {
/* 80 */     SetMovementStates copy = new SetMovementStates();
/* 81 */     copy.movementStates = (this.movementStates != null) ? this.movementStates.clone() : null;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     SetMovementStates other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof SetMovementStates) { other = (SetMovementStates)obj; } else { return false; }
/* 90 */      return Objects.equals(this.movementStates, other.movementStates);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { this.movementStates });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\player\SetMovementStates.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */