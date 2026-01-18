/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class UpdateTimeSettings
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 145;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 10;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 10;
/*     */   public static final int MAX_SIZE = 10;
/*     */   public int daytimeDurationSeconds;
/*     */   public int nighttimeDurationSeconds;
/*     */   public byte totalMoonPhases;
/*     */   public boolean timePaused;
/*     */   
/*     */   public int getId() {
/*  25 */     return 145;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdateTimeSettings() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdateTimeSettings(int daytimeDurationSeconds, int nighttimeDurationSeconds, byte totalMoonPhases, boolean timePaused) {
/*  37 */     this.daytimeDurationSeconds = daytimeDurationSeconds;
/*  38 */     this.nighttimeDurationSeconds = nighttimeDurationSeconds;
/*  39 */     this.totalMoonPhases = totalMoonPhases;
/*  40 */     this.timePaused = timePaused;
/*     */   }
/*     */   
/*     */   public UpdateTimeSettings(@Nonnull UpdateTimeSettings other) {
/*  44 */     this.daytimeDurationSeconds = other.daytimeDurationSeconds;
/*  45 */     this.nighttimeDurationSeconds = other.nighttimeDurationSeconds;
/*  46 */     this.totalMoonPhases = other.totalMoonPhases;
/*  47 */     this.timePaused = other.timePaused;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateTimeSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/*  52 */     UpdateTimeSettings obj = new UpdateTimeSettings();
/*     */     
/*  54 */     obj.daytimeDurationSeconds = buf.getIntLE(offset + 0);
/*  55 */     obj.nighttimeDurationSeconds = buf.getIntLE(offset + 4);
/*  56 */     obj.totalMoonPhases = buf.getByte(offset + 8);
/*  57 */     obj.timePaused = (buf.getByte(offset + 9) != 0);
/*     */ 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     return 10;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  70 */     buf.writeIntLE(this.daytimeDurationSeconds);
/*  71 */     buf.writeIntLE(this.nighttimeDurationSeconds);
/*  72 */     buf.writeByte(this.totalMoonPhases);
/*  73 */     buf.writeByte(this.timePaused ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  79 */     return 10;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  83 */     if (buffer.readableBytes() - offset < 10) {
/*  84 */       return ValidationResult.error("Buffer too small: expected at least 10 bytes");
/*     */     }
/*     */ 
/*     */     
/*  88 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateTimeSettings clone() {
/*  92 */     UpdateTimeSettings copy = new UpdateTimeSettings();
/*  93 */     copy.daytimeDurationSeconds = this.daytimeDurationSeconds;
/*  94 */     copy.nighttimeDurationSeconds = this.nighttimeDurationSeconds;
/*  95 */     copy.totalMoonPhases = this.totalMoonPhases;
/*  96 */     copy.timePaused = this.timePaused;
/*  97 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateTimeSettings other;
/* 103 */     if (this == obj) return true; 
/* 104 */     if (obj instanceof UpdateTimeSettings) { other = (UpdateTimeSettings)obj; } else { return false; }
/* 105 */      return (this.daytimeDurationSeconds == other.daytimeDurationSeconds && this.nighttimeDurationSeconds == other.nighttimeDurationSeconds && this.totalMoonPhases == other.totalMoonPhases && this.timePaused == other.timePaused);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 110 */     return Objects.hash(new Object[] { Integer.valueOf(this.daytimeDurationSeconds), Integer.valueOf(this.nighttimeDurationSeconds), Byte.valueOf(this.totalMoonPhases), Boolean.valueOf(this.timePaused) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\UpdateTimeSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */