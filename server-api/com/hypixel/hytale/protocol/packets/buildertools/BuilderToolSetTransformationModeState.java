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
/*    */ public class BuilderToolSetTransformationModeState
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 408;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   public boolean enabled;
/*    */   
/*    */   public int getId() {
/* 25 */     return 408;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BuilderToolSetTransformationModeState() {}
/*    */ 
/*    */   
/*    */   public BuilderToolSetTransformationModeState(boolean enabled) {
/* 34 */     this.enabled = enabled;
/*    */   }
/*    */   
/*    */   public BuilderToolSetTransformationModeState(@Nonnull BuilderToolSetTransformationModeState other) {
/* 38 */     this.enabled = other.enabled;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BuilderToolSetTransformationModeState deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     BuilderToolSetTransformationModeState obj = new BuilderToolSetTransformationModeState();
/*    */     
/* 45 */     obj.enabled = (buf.getByte(offset + 0) != 0);
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
/* 58 */     buf.writeByte(this.enabled ? 1 : 0);
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
/*    */   public BuilderToolSetTransformationModeState clone() {
/* 77 */     BuilderToolSetTransformationModeState copy = new BuilderToolSetTransformationModeState();
/* 78 */     copy.enabled = this.enabled;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     BuilderToolSetTransformationModeState other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof BuilderToolSetTransformationModeState) { other = (BuilderToolSetTransformationModeState)obj; } else { return false; }
/* 87 */      return (this.enabled == other.enabled);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Boolean.valueOf(this.enabled) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolSetTransformationModeState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */