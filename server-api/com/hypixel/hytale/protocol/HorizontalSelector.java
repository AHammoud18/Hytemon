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
/*     */ public class HorizontalSelector
/*     */   extends Selector
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 34;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 34;
/*     */   public static final int MAX_SIZE = 34;
/*     */   public float extendTop;
/*     */   public float extendBottom;
/*     */   public float yawLength;
/*     */   public float yawStartOffset;
/*     */   public float pitchOffset;
/*     */   public float rollOffset;
/*     */   public float startDistance;
/*     */   public float endDistance;
/*     */   @Nonnull
/*  28 */   public HorizontalSelectorDirection direction = HorizontalSelectorDirection.ToLeft;
/*     */ 
/*     */   
/*     */   public boolean testLineOfSight;
/*     */ 
/*     */   
/*     */   public HorizontalSelector(float extendTop, float extendBottom, float yawLength, float yawStartOffset, float pitchOffset, float rollOffset, float startDistance, float endDistance, @Nonnull HorizontalSelectorDirection direction, boolean testLineOfSight) {
/*  35 */     this.extendTop = extendTop;
/*  36 */     this.extendBottom = extendBottom;
/*  37 */     this.yawLength = yawLength;
/*  38 */     this.yawStartOffset = yawStartOffset;
/*  39 */     this.pitchOffset = pitchOffset;
/*  40 */     this.rollOffset = rollOffset;
/*  41 */     this.startDistance = startDistance;
/*  42 */     this.endDistance = endDistance;
/*  43 */     this.direction = direction;
/*  44 */     this.testLineOfSight = testLineOfSight;
/*     */   }
/*     */   
/*     */   public HorizontalSelector(@Nonnull HorizontalSelector other) {
/*  48 */     this.extendTop = other.extendTop;
/*  49 */     this.extendBottom = other.extendBottom;
/*  50 */     this.yawLength = other.yawLength;
/*  51 */     this.yawStartOffset = other.yawStartOffset;
/*  52 */     this.pitchOffset = other.pitchOffset;
/*  53 */     this.rollOffset = other.rollOffset;
/*  54 */     this.startDistance = other.startDistance;
/*  55 */     this.endDistance = other.endDistance;
/*  56 */     this.direction = other.direction;
/*  57 */     this.testLineOfSight = other.testLineOfSight;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static HorizontalSelector deserialize(@Nonnull ByteBuf buf, int offset) {
/*  62 */     HorizontalSelector obj = new HorizontalSelector();
/*     */     
/*  64 */     obj.extendTop = buf.getFloatLE(offset + 0);
/*  65 */     obj.extendBottom = buf.getFloatLE(offset + 4);
/*  66 */     obj.yawLength = buf.getFloatLE(offset + 8);
/*  67 */     obj.yawStartOffset = buf.getFloatLE(offset + 12);
/*  68 */     obj.pitchOffset = buf.getFloatLE(offset + 16);
/*  69 */     obj.rollOffset = buf.getFloatLE(offset + 20);
/*  70 */     obj.startDistance = buf.getFloatLE(offset + 24);
/*  71 */     obj.endDistance = buf.getFloatLE(offset + 28);
/*  72 */     obj.direction = HorizontalSelectorDirection.fromValue(buf.getByte(offset + 32));
/*  73 */     obj.testLineOfSight = (buf.getByte(offset + 33) != 0);
/*     */ 
/*     */     
/*  76 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  80 */     return 34;
/*     */   }
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/*  85 */     int startPos = buf.writerIndex();
/*     */     
/*  87 */     buf.writeFloatLE(this.extendTop);
/*  88 */     buf.writeFloatLE(this.extendBottom);
/*  89 */     buf.writeFloatLE(this.yawLength);
/*  90 */     buf.writeFloatLE(this.yawStartOffset);
/*  91 */     buf.writeFloatLE(this.pitchOffset);
/*  92 */     buf.writeFloatLE(this.rollOffset);
/*  93 */     buf.writeFloatLE(this.startDistance);
/*  94 */     buf.writeFloatLE(this.endDistance);
/*  95 */     buf.writeByte(this.direction.getValue());
/*  96 */     buf.writeByte(this.testLineOfSight ? 1 : 0);
/*     */     
/*  98 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 104 */     return 34;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 108 */     if (buffer.readableBytes() - offset < 34) {
/* 109 */       return ValidationResult.error("Buffer too small: expected at least 34 bytes");
/*     */     }
/*     */ 
/*     */     
/* 113 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public HorizontalSelector clone() {
/* 117 */     HorizontalSelector copy = new HorizontalSelector();
/* 118 */     copy.extendTop = this.extendTop;
/* 119 */     copy.extendBottom = this.extendBottom;
/* 120 */     copy.yawLength = this.yawLength;
/* 121 */     copy.yawStartOffset = this.yawStartOffset;
/* 122 */     copy.pitchOffset = this.pitchOffset;
/* 123 */     copy.rollOffset = this.rollOffset;
/* 124 */     copy.startDistance = this.startDistance;
/* 125 */     copy.endDistance = this.endDistance;
/* 126 */     copy.direction = this.direction;
/* 127 */     copy.testLineOfSight = this.testLineOfSight;
/* 128 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     HorizontalSelector other;
/* 134 */     if (this == obj) return true; 
/* 135 */     if (obj instanceof HorizontalSelector) { other = (HorizontalSelector)obj; } else { return false; }
/* 136 */      return (this.extendTop == other.extendTop && this.extendBottom == other.extendBottom && this.yawLength == other.yawLength && this.yawStartOffset == other.yawStartOffset && this.pitchOffset == other.pitchOffset && this.rollOffset == other.rollOffset && this.startDistance == other.startDistance && this.endDistance == other.endDistance && Objects.equals(this.direction, other.direction) && this.testLineOfSight == other.testLineOfSight);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 141 */     return Objects.hash(new Object[] { Float.valueOf(this.extendTop), Float.valueOf(this.extendBottom), Float.valueOf(this.yawLength), Float.valueOf(this.yawStartOffset), Float.valueOf(this.pitchOffset), Float.valueOf(this.rollOffset), Float.valueOf(this.startDistance), Float.valueOf(this.endDistance), this.direction, Boolean.valueOf(this.testLineOfSight) });
/*     */   }
/*     */   
/*     */   public HorizontalSelector() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\HorizontalSelector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */