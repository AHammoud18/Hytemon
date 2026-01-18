/*    */ package com.hypixel.hytale.protocol;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldInteraction
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 20;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 20;
/*    */   public static final int MAX_SIZE = 20;
/*    */   public int entityId;
/*    */   @Nullable
/*    */   public BlockPosition blockPosition;
/*    */   @Nullable
/*    */   public BlockRotation blockRotation;
/*    */   
/*    */   public WorldInteraction() {}
/*    */   
/*    */   public WorldInteraction(int entityId, @Nullable BlockPosition blockPosition, @Nullable BlockRotation blockRotation) {
/* 28 */     this.entityId = entityId;
/* 29 */     this.blockPosition = blockPosition;
/* 30 */     this.blockRotation = blockRotation;
/*    */   }
/*    */   
/*    */   public WorldInteraction(@Nonnull WorldInteraction other) {
/* 34 */     this.entityId = other.entityId;
/* 35 */     this.blockPosition = other.blockPosition;
/* 36 */     this.blockRotation = other.blockRotation;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static WorldInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     WorldInteraction obj = new WorldInteraction();
/* 42 */     byte nullBits = buf.getByte(offset);
/* 43 */     obj.entityId = buf.getIntLE(offset + 1);
/* 44 */     if ((nullBits & 0x1) != 0) obj.blockPosition = BlockPosition.deserialize(buf, offset + 5); 
/* 45 */     if ((nullBits & 0x2) != 0) obj.blockRotation = BlockRotation.deserialize(buf, offset + 17);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 20;
/*    */   }
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 56 */     byte nullBits = 0;
/* 57 */     if (this.blockPosition != null) nullBits = (byte)(nullBits | 0x1); 
/* 58 */     if (this.blockRotation != null) nullBits = (byte)(nullBits | 0x2); 
/* 59 */     buf.writeByte(nullBits);
/*    */     
/* 61 */     buf.writeIntLE(this.entityId);
/* 62 */     if (this.blockPosition != null) { this.blockPosition.serialize(buf); } else { buf.writeZero(12); }
/* 63 */      if (this.blockRotation != null) { this.blockRotation.serialize(buf); } else { buf.writeZero(3); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 69 */     return 20;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 73 */     if (buffer.readableBytes() - offset < 20) {
/* 74 */       return ValidationResult.error("Buffer too small: expected at least 20 bytes");
/*    */     }
/*    */ 
/*    */     
/* 78 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public WorldInteraction clone() {
/* 82 */     WorldInteraction copy = new WorldInteraction();
/* 83 */     copy.entityId = this.entityId;
/* 84 */     copy.blockPosition = (this.blockPosition != null) ? this.blockPosition.clone() : null;
/* 85 */     copy.blockRotation = (this.blockRotation != null) ? this.blockRotation.clone() : null;
/* 86 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     WorldInteraction other;
/* 92 */     if (this == obj) return true; 
/* 93 */     if (obj instanceof WorldInteraction) { other = (WorldInteraction)obj; } else { return false; }
/* 94 */      return (this.entityId == other.entityId && Objects.equals(this.blockPosition, other.blockPosition) && Objects.equals(this.blockRotation, other.blockRotation));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 99 */     return Objects.hash(new Object[] { Integer.valueOf(this.entityId), this.blockPosition, this.blockRotation });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\WorldInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */