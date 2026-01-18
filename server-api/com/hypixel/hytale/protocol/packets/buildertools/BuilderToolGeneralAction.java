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
/*    */ 
/*    */ public class BuilderToolGeneralAction
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 412;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   
/*    */   public int getId() {
/* 25 */     return 412;
/*    */   }
/*    */   @Nonnull
/* 28 */   public BuilderToolAction action = BuilderToolAction.SelectionPosition1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BuilderToolGeneralAction(@Nonnull BuilderToolAction action) {
/* 34 */     this.action = action;
/*    */   }
/*    */   
/*    */   public BuilderToolGeneralAction(@Nonnull BuilderToolGeneralAction other) {
/* 38 */     this.action = other.action;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BuilderToolGeneralAction deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     BuilderToolGeneralAction obj = new BuilderToolGeneralAction();
/*    */     
/* 45 */     obj.action = BuilderToolAction.fromValue(buf.getByte(offset + 0));
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 58 */     buf.writeByte(this.action.getValue());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 64 */     return 1;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 68 */     if (buffer.readableBytes() - offset < 1) {
/* 69 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*    */     }
/*    */ 
/*    */     
/* 73 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public BuilderToolGeneralAction clone() {
/* 77 */     BuilderToolGeneralAction copy = new BuilderToolGeneralAction();
/* 78 */     copy.action = this.action;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     BuilderToolGeneralAction other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof BuilderToolGeneralAction) { other = (BuilderToolGeneralAction)obj; } else { return false; }
/* 87 */      return Objects.equals(this.action, other.action);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { this.action });
/*    */   }
/*    */   
/*    */   public BuilderToolGeneralAction() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolGeneralAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */