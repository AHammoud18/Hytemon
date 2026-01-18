/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.InstantData;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SleepClock
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 33;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 33;
/*     */   public static final int MAX_SIZE = 33;
/*     */   @Nullable
/*     */   public InstantData startGametime;
/*     */   @Nullable
/*     */   public InstantData targetGametime;
/*     */   public float progress;
/*     */   public float durationSeconds;
/*     */   
/*     */   public SleepClock() {}
/*     */   
/*     */   public SleepClock(@Nullable InstantData startGametime, @Nullable InstantData targetGametime, float progress, float durationSeconds) {
/*  29 */     this.startGametime = startGametime;
/*  30 */     this.targetGametime = targetGametime;
/*  31 */     this.progress = progress;
/*  32 */     this.durationSeconds = durationSeconds;
/*     */   }
/*     */   
/*     */   public SleepClock(@Nonnull SleepClock other) {
/*  36 */     this.startGametime = other.startGametime;
/*  37 */     this.targetGametime = other.targetGametime;
/*  38 */     this.progress = other.progress;
/*  39 */     this.durationSeconds = other.durationSeconds;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SleepClock deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     SleepClock obj = new SleepClock();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     if ((nullBits & 0x1) != 0) obj.startGametime = InstantData.deserialize(buf, offset + 1); 
/*  47 */     if ((nullBits & 0x2) != 0) obj.targetGametime = InstantData.deserialize(buf, offset + 13); 
/*  48 */     obj.progress = buf.getFloatLE(offset + 25);
/*  49 */     obj.durationSeconds = buf.getFloatLE(offset + 29);
/*     */ 
/*     */     
/*  52 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  56 */     return 33;
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  60 */     byte nullBits = 0;
/*  61 */     if (this.startGametime != null) nullBits = (byte)(nullBits | 0x1); 
/*  62 */     if (this.targetGametime != null) nullBits = (byte)(nullBits | 0x2); 
/*  63 */     buf.writeByte(nullBits);
/*     */     
/*  65 */     if (this.startGametime != null) { this.startGametime.serialize(buf); } else { buf.writeZero(12); }
/*  66 */      if (this.targetGametime != null) { this.targetGametime.serialize(buf); } else { buf.writeZero(12); }
/*  67 */      buf.writeFloatLE(this.progress);
/*  68 */     buf.writeFloatLE(this.durationSeconds);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  74 */     return 33;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  78 */     if (buffer.readableBytes() - offset < 33) {
/*  79 */       return ValidationResult.error("Buffer too small: expected at least 33 bytes");
/*     */     }
/*     */ 
/*     */     
/*  83 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SleepClock clone() {
/*  87 */     SleepClock copy = new SleepClock();
/*  88 */     copy.startGametime = (this.startGametime != null) ? this.startGametime.clone() : null;
/*  89 */     copy.targetGametime = (this.targetGametime != null) ? this.targetGametime.clone() : null;
/*  90 */     copy.progress = this.progress;
/*  91 */     copy.durationSeconds = this.durationSeconds;
/*  92 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SleepClock other;
/*  98 */     if (this == obj) return true; 
/*  99 */     if (obj instanceof SleepClock) { other = (SleepClock)obj; } else { return false; }
/* 100 */      return (Objects.equals(this.startGametime, other.startGametime) && Objects.equals(this.targetGametime, other.targetGametime) && this.progress == other.progress && this.durationSeconds == other.durationSeconds);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 105 */     return Objects.hash(new Object[] { this.startGametime, this.targetGametime, Float.valueOf(this.progress), Float.valueOf(this.durationSeconds) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\SleepClock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */