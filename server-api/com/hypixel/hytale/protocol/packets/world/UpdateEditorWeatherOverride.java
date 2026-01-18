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
/*    */ 
/*    */ public class UpdateEditorWeatherOverride
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 150;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 4;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 4;
/*    */   public static final int MAX_SIZE = 4;
/*    */   public int weatherIndex;
/*    */   
/*    */   public int getId() {
/* 25 */     return 150;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public UpdateEditorWeatherOverride() {}
/*    */ 
/*    */   
/*    */   public UpdateEditorWeatherOverride(int weatherIndex) {
/* 34 */     this.weatherIndex = weatherIndex;
/*    */   }
/*    */   
/*    */   public UpdateEditorWeatherOverride(@Nonnull UpdateEditorWeatherOverride other) {
/* 38 */     this.weatherIndex = other.weatherIndex;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static UpdateEditorWeatherOverride deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     UpdateEditorWeatherOverride obj = new UpdateEditorWeatherOverride();
/*    */     
/* 45 */     obj.weatherIndex = buf.getIntLE(offset + 0);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 4;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 58 */     buf.writeIntLE(this.weatherIndex);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 64 */     return 4;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 68 */     if (buffer.readableBytes() - offset < 4) {
/* 69 */       return ValidationResult.error("Buffer too small: expected at least 4 bytes");
/*    */     }
/*    */ 
/*    */     
/* 73 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public UpdateEditorWeatherOverride clone() {
/* 77 */     UpdateEditorWeatherOverride copy = new UpdateEditorWeatherOverride();
/* 78 */     copy.weatherIndex = this.weatherIndex;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     UpdateEditorWeatherOverride other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof UpdateEditorWeatherOverride) { other = (UpdateEditorWeatherOverride)obj; } else { return false; }
/* 87 */      return (this.weatherIndex == other.weatherIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Integer.valueOf(this.weatherIndex) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\UpdateEditorWeatherOverride.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */