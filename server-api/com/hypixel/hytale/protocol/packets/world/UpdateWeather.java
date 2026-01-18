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
/*    */ public class UpdateWeather
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 149;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public int weatherIndex;
/*    */   public float transitionSeconds;
/*    */   
/*    */   public int getId() {
/* 25 */     return 149;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public UpdateWeather() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public UpdateWeather(int weatherIndex, float transitionSeconds) {
/* 35 */     this.weatherIndex = weatherIndex;
/* 36 */     this.transitionSeconds = transitionSeconds;
/*    */   }
/*    */   
/*    */   public UpdateWeather(@Nonnull UpdateWeather other) {
/* 40 */     this.weatherIndex = other.weatherIndex;
/* 41 */     this.transitionSeconds = other.transitionSeconds;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static UpdateWeather deserialize(@Nonnull ByteBuf buf, int offset) {
/* 46 */     UpdateWeather obj = new UpdateWeather();
/*    */     
/* 48 */     obj.weatherIndex = buf.getIntLE(offset + 0);
/* 49 */     obj.transitionSeconds = buf.getFloatLE(offset + 4);
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
/* 62 */     buf.writeIntLE(this.weatherIndex);
/* 63 */     buf.writeFloatLE(this.transitionSeconds);
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
/*    */   public UpdateWeather clone() {
/* 82 */     UpdateWeather copy = new UpdateWeather();
/* 83 */     copy.weatherIndex = this.weatherIndex;
/* 84 */     copy.transitionSeconds = this.transitionSeconds;
/* 85 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     UpdateWeather other;
/* 91 */     if (this == obj) return true; 
/* 92 */     if (obj instanceof UpdateWeather) { other = (UpdateWeather)obj; } else { return false; }
/* 93 */      return (this.weatherIndex == other.weatherIndex && this.transitionSeconds == other.transitionSeconds);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return Objects.hash(new Object[] { Integer.valueOf(this.weatherIndex), Float.valueOf(this.transitionSeconds) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\UpdateWeather.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */