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
/*    */ public class BuilderToolSetEntityScale
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 420;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public int entityId;
/*    */   public float scale;
/*    */   
/*    */   public int getId() {
/* 25 */     return 420;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BuilderToolSetEntityScale() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public BuilderToolSetEntityScale(int entityId, float scale) {
/* 35 */     this.entityId = entityId;
/* 36 */     this.scale = scale;
/*    */   }
/*    */   
/*    */   public BuilderToolSetEntityScale(@Nonnull BuilderToolSetEntityScale other) {
/* 40 */     this.entityId = other.entityId;
/* 41 */     this.scale = other.scale;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BuilderToolSetEntityScale deserialize(@Nonnull ByteBuf buf, int offset) {
/* 46 */     BuilderToolSetEntityScale obj = new BuilderToolSetEntityScale();
/*    */     
/* 48 */     obj.entityId = buf.getIntLE(offset + 0);
/* 49 */     obj.scale = buf.getFloatLE(offset + 4);
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
/* 62 */     buf.writeIntLE(this.entityId);
/* 63 */     buf.writeFloatLE(this.scale);
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
/*    */   public BuilderToolSetEntityScale clone() {
/* 82 */     BuilderToolSetEntityScale copy = new BuilderToolSetEntityScale();
/* 83 */     copy.entityId = this.entityId;
/* 84 */     copy.scale = this.scale;
/* 85 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     BuilderToolSetEntityScale other;
/* 91 */     if (this == obj) return true; 
/* 92 */     if (obj instanceof BuilderToolSetEntityScale) { other = (BuilderToolSetEntityScale)obj; } else { return false; }
/* 93 */      return (this.entityId == other.entityId && this.scale == other.scale);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return Objects.hash(new Object[] { Integer.valueOf(this.entityId), Float.valueOf(this.scale) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolSetEntityScale.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */