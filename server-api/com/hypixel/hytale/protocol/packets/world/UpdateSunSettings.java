/*    */ package com.hypixel.hytale.protocol.packets.world;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UpdateSunSettings
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 360;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public float heightPercentage;
/*    */   public float angleRadians;
/*    */   
/*    */   public int getId() {
/* 25 */     return 360;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public UpdateSunSettings() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public UpdateSunSettings(float heightPercentage, float angleRadians) {
/* 35 */     this.heightPercentage = heightPercentage;
/* 36 */     this.angleRadians = angleRadians;
/*    */   }
/*    */   
/*    */   public UpdateSunSettings(@Nonnull UpdateSunSettings other) {
/* 40 */     this.heightPercentage = other.heightPercentage;
/* 41 */     this.angleRadians = other.angleRadians;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static UpdateSunSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/* 46 */     UpdateSunSettings obj = new UpdateSunSettings();
/*    */     
/* 48 */     obj.heightPercentage = buf.getFloatLE(offset + 0);
/* 49 */     obj.angleRadians = buf.getFloatLE(offset + 4);
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
/* 62 */     buf.writeFloatLE(this.heightPercentage);
/* 63 */     buf.writeFloatLE(this.angleRadians);
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
/*    */   public UpdateSunSettings clone() {
/* 82 */     UpdateSunSettings copy = new UpdateSunSettings();
/* 83 */     copy.heightPercentage = this.heightPercentage;
/* 84 */     copy.angleRadians = this.angleRadians;
/* 85 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     UpdateSunSettings other;
/* 91 */     if (this == obj) return true; 
/* 92 */     if (obj instanceof UpdateSunSettings) { other = (UpdateSunSettings)obj; } else { return false; }
/* 93 */      return (this.heightPercentage == other.heightPercentage && this.angleRadians == other.angleRadians);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return Objects.hash(new Object[] { Float.valueOf(this.heightPercentage), Float.valueOf(this.angleRadians) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\UpdateSunSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */