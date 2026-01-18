/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StabSelector
/*     */   extends Selector
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 37;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 37;
/*     */   public static final int MAX_SIZE = 37;
/*     */   public float extendTop;
/*     */   public float extendBottom;
/*     */   public float extendLeft;
/*     */   public float extendRight;
/*     */   public float yawOffset;
/*     */   public float pitchOffset;
/*     */   public float rollOffset;
/*     */   public float startDistance;
/*     */   public float endDistance;
/*     */   public boolean testLineOfSight;
/*     */   
/*     */   public StabSelector() {}
/*     */   
/*     */   public StabSelector(float extendTop, float extendBottom, float extendLeft, float extendRight, float yawOffset, float pitchOffset, float rollOffset, float startDistance, float endDistance, boolean testLineOfSight) {
/*  35 */     this.extendTop = extendTop;
/*  36 */     this.extendBottom = extendBottom;
/*  37 */     this.extendLeft = extendLeft;
/*  38 */     this.extendRight = extendRight;
/*  39 */     this.yawOffset = yawOffset;
/*  40 */     this.pitchOffset = pitchOffset;
/*  41 */     this.rollOffset = rollOffset;
/*  42 */     this.startDistance = startDistance;
/*  43 */     this.endDistance = endDistance;
/*  44 */     this.testLineOfSight = testLineOfSight;
/*     */   }
/*     */   
/*     */   public StabSelector(@Nonnull StabSelector other) {
/*  48 */     this.extendTop = other.extendTop;
/*  49 */     this.extendBottom = other.extendBottom;
/*  50 */     this.extendLeft = other.extendLeft;
/*  51 */     this.extendRight = other.extendRight;
/*  52 */     this.yawOffset = other.yawOffset;
/*  53 */     this.pitchOffset = other.pitchOffset;
/*  54 */     this.rollOffset = other.rollOffset;
/*  55 */     this.startDistance = other.startDistance;
/*  56 */     this.endDistance = other.endDistance;
/*  57 */     this.testLineOfSight = other.testLineOfSight;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static StabSelector deserialize(@Nonnull ByteBuf buf, int offset) {
/*  62 */     StabSelector obj = new StabSelector();
/*     */     
/*  64 */     obj.extendTop = buf.getFloatLE(offset + 0);
/*  65 */     obj.extendBottom = buf.getFloatLE(offset + 4);
/*  66 */     obj.extendLeft = buf.getFloatLE(offset + 8);
/*  67 */     obj.extendRight = buf.getFloatLE(offset + 12);
/*  68 */     obj.yawOffset = buf.getFloatLE(offset + 16);
/*  69 */     obj.pitchOffset = buf.getFloatLE(offset + 20);
/*  70 */     obj.rollOffset = buf.getFloatLE(offset + 24);
/*  71 */     obj.startDistance = buf.getFloatLE(offset + 28);
/*  72 */     obj.endDistance = buf.getFloatLE(offset + 32);
/*  73 */     obj.testLineOfSight = (buf.getByte(offset + 36) != 0);
/*     */ 
/*     */     
/*  76 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  80 */     return 37;
/*     */   }
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/*  85 */     int startPos = buf.writerIndex();
/*     */     
/*  87 */     buf.writeFloatLE(this.extendTop);
/*  88 */     buf.writeFloatLE(this.extendBottom);
/*  89 */     buf.writeFloatLE(this.extendLeft);
/*  90 */     buf.writeFloatLE(this.extendRight);
/*  91 */     buf.writeFloatLE(this.yawOffset);
/*  92 */     buf.writeFloatLE(this.pitchOffset);
/*  93 */     buf.writeFloatLE(this.rollOffset);
/*  94 */     buf.writeFloatLE(this.startDistance);
/*  95 */     buf.writeFloatLE(this.endDistance);
/*  96 */     buf.writeByte(this.testLineOfSight ? 1 : 0);
/*     */     
/*  98 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 104 */     return 37;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 108 */     if (buffer.readableBytes() - offset < 37) {
/* 109 */       return ValidationResult.error("Buffer too small: expected at least 37 bytes");
/*     */     }
/*     */ 
/*     */     
/* 113 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public StabSelector clone() {
/* 117 */     StabSelector copy = new StabSelector();
/* 118 */     copy.extendTop = this.extendTop;
/* 119 */     copy.extendBottom = this.extendBottom;
/* 120 */     copy.extendLeft = this.extendLeft;
/* 121 */     copy.extendRight = this.extendRight;
/* 122 */     copy.yawOffset = this.yawOffset;
/* 123 */     copy.pitchOffset = this.pitchOffset;
/* 124 */     copy.rollOffset = this.rollOffset;
/* 125 */     copy.startDistance = this.startDistance;
/* 126 */     copy.endDistance = this.endDistance;
/* 127 */     copy.testLineOfSight = this.testLineOfSight;
/* 128 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     StabSelector other;
/* 134 */     if (this == obj) return true; 
/* 135 */     if (obj instanceof StabSelector) { other = (StabSelector)obj; } else { return false; }
/* 136 */      return (this.extendTop == other.extendTop && this.extendBottom == other.extendBottom && this.extendLeft == other.extendLeft && this.extendRight == other.extendRight && this.yawOffset == other.yawOffset && this.pitchOffset == other.pitchOffset && this.rollOffset == other.rollOffset && this.startDistance == other.startDistance && this.endDistance == other.endDistance && this.testLineOfSight == other.testLineOfSight);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 141 */     return Objects.hash(new Object[] { Float.valueOf(this.extendTop), Float.valueOf(this.extendBottom), Float.valueOf(this.extendLeft), Float.valueOf(this.extendRight), Float.valueOf(this.yawOffset), Float.valueOf(this.pitchOffset), Float.valueOf(this.rollOffset), Float.valueOf(this.startDistance), Float.valueOf(this.endDistance), Boolean.valueOf(this.testLineOfSight) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\StabSelector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */