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
/*    */ public class BuilderToolRotateClipboard
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 406;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 5;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 5;
/*    */   public static final int MAX_SIZE = 5;
/*    */   public int angle;
/*    */   
/*    */   public int getId() {
/* 25 */     return 406;
/*    */   }
/*    */   
/*    */   @Nonnull
/* 29 */   public Axis axis = Axis.X;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BuilderToolRotateClipboard(int angle, @Nonnull Axis axis) {
/* 35 */     this.angle = angle;
/* 36 */     this.axis = axis;
/*    */   }
/*    */   
/*    */   public BuilderToolRotateClipboard(@Nonnull BuilderToolRotateClipboard other) {
/* 40 */     this.angle = other.angle;
/* 41 */     this.axis = other.axis;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static BuilderToolRotateClipboard deserialize(@Nonnull ByteBuf buf, int offset) {
/* 46 */     BuilderToolRotateClipboard obj = new BuilderToolRotateClipboard();
/*    */     
/* 48 */     obj.angle = buf.getIntLE(offset + 0);
/* 49 */     obj.axis = Axis.fromValue(buf.getByte(offset + 4));
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
/* 62 */     buf.writeIntLE(this.angle);
/* 63 */     buf.writeByte(this.axis.getValue());
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
/*    */   public BuilderToolRotateClipboard clone() {
/* 82 */     BuilderToolRotateClipboard copy = new BuilderToolRotateClipboard();
/* 83 */     copy.angle = this.angle;
/* 84 */     copy.axis = this.axis;
/* 85 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     BuilderToolRotateClipboard other;
/* 91 */     if (this == obj) return true; 
/* 92 */     if (obj instanceof BuilderToolRotateClipboard) { other = (BuilderToolRotateClipboard)obj; } else { return false; }
/* 93 */      return (this.angle == other.angle && Objects.equals(this.axis, other.axis));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return Objects.hash(new Object[] { Integer.valueOf(this.angle), this.axis });
/*    */   }
/*    */   
/*    */   public BuilderToolRotateClipboard() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolRotateClipboard.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */