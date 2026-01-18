/*    */ package com.hypixel.hytale.protocol.packets.world;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetFluidCmd
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 7;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 7;
/*    */   public static final int MAX_SIZE = 7;
/*    */   public short index;
/*    */   public int fluidId;
/*    */   public byte fluidLevel;
/*    */   
/*    */   public SetFluidCmd() {}
/*    */   
/*    */   public SetFluidCmd(short index, int fluidId, byte fluidLevel) {
/* 28 */     this.index = index;
/* 29 */     this.fluidId = fluidId;
/* 30 */     this.fluidLevel = fluidLevel;
/*    */   }
/*    */   
/*    */   public SetFluidCmd(@Nonnull SetFluidCmd other) {
/* 34 */     this.index = other.index;
/* 35 */     this.fluidId = other.fluidId;
/* 36 */     this.fluidLevel = other.fluidLevel;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SetFluidCmd deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     SetFluidCmd obj = new SetFluidCmd();
/*    */     
/* 43 */     obj.index = buf.getShortLE(offset + 0);
/* 44 */     obj.fluidId = buf.getIntLE(offset + 2);
/* 45 */     obj.fluidLevel = buf.getByte(offset + 6);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 7;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     buf.writeShortLE(this.index);
/* 58 */     buf.writeIntLE(this.fluidId);
/* 59 */     buf.writeByte(this.fluidLevel);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 65 */     return 7;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 69 */     if (buffer.readableBytes() - offset < 7) {
/* 70 */       return ValidationResult.error("Buffer too small: expected at least 7 bytes");
/*    */     }
/*    */ 
/*    */     
/* 74 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public SetFluidCmd clone() {
/* 78 */     SetFluidCmd copy = new SetFluidCmd();
/* 79 */     copy.index = this.index;
/* 80 */     copy.fluidId = this.fluidId;
/* 81 */     copy.fluidLevel = this.fluidLevel;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     SetFluidCmd other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof SetFluidCmd) { other = (SetFluidCmd)obj; } else { return false; }
/* 90 */      return (this.index == other.index && this.fluidId == other.fluidId && this.fluidLevel == other.fluidLevel);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { Short.valueOf(this.index), Integer.valueOf(this.fluidId), Byte.valueOf(this.fluidLevel) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\SetFluidCmd.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */