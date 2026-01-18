/*    */ package com.hypixel.hytale.protocol.packets.buildertools;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderToolEntityAction
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 401;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 5;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 5;
/*    */   public static final int MAX_SIZE = 5;
/*    */   public int entityId;
/*    */   
/*    */   public int getId() {
/* 25 */     return 401;
/*    */   }
/*    */   
/*    */   @Nonnull
/* 29 */   public EntityToolAction action = EntityToolAction.Remove;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BuilderToolEntityAction(int entityId, @Nonnull EntityToolAction action) {
/* 35 */     this.entityId = entityId;
/* 36 */     this.action = action;
/*    */   }
/*    */   
/*    */   public BuilderToolEntityAction(@Nonnull BuilderToolEntityAction other) {
/* 40 */     this.entityId = other.entityId;
/* 41 */     this.action = other.action;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BuilderToolEntityAction deserialize(@Nonnull ByteBuf buf, int offset) {
/* 46 */     BuilderToolEntityAction obj = new BuilderToolEntityAction();
/*    */     
/* 48 */     obj.entityId = buf.getIntLE(offset + 0);
/* 49 */     obj.action = EntityToolAction.fromValue(buf.getByte(offset + 4));
/*    */ 
/*    */     
/* 52 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 56 */     return 5;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 62 */     buf.writeIntLE(this.entityId);
/* 63 */     buf.writeByte(this.action.getValue());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 69 */     return 5;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 73 */     if (buffer.readableBytes() - offset < 5) {
/* 74 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*    */     }
/*    */ 
/*    */     
/* 78 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public BuilderToolEntityAction clone() {
/* 82 */     BuilderToolEntityAction copy = new BuilderToolEntityAction();
/* 83 */     copy.entityId = this.entityId;
/* 84 */     copy.action = this.action;
/* 85 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     BuilderToolEntityAction other;
/* 91 */     if (this == obj) return true; 
/* 92 */     if (obj instanceof BuilderToolEntityAction) { other = (BuilderToolEntityAction)obj; } else { return false; }
/* 93 */      return (this.entityId == other.entityId && Objects.equals(this.action, other.action));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return Objects.hash(new Object[] { Integer.valueOf(this.entityId), this.action });
/*    */   }
/*    */   
/*    */   public BuilderToolEntityAction() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolEntityAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */