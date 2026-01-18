/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class BuilderToolLineAction implements Packet {
/*     */   public static final int PACKET_ID = 414;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 24;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 24;
/*     */   public static final int MAX_SIZE = 24;
/*     */   public int xStart;
/*     */   public int yStart;
/*     */   public int zStart;
/*     */   public int xEnd;
/*     */   public int yEnd;
/*     */   public int zEnd;
/*     */   
/*     */   public int getId() {
/*  25 */     return 414;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderToolLineAction() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderToolLineAction(int xStart, int yStart, int zStart, int xEnd, int yEnd, int zEnd) {
/*  39 */     this.xStart = xStart;
/*  40 */     this.yStart = yStart;
/*  41 */     this.zStart = zStart;
/*  42 */     this.xEnd = xEnd;
/*  43 */     this.yEnd = yEnd;
/*  44 */     this.zEnd = zEnd;
/*     */   }
/*     */   
/*     */   public BuilderToolLineAction(@Nonnull BuilderToolLineAction other) {
/*  48 */     this.xStart = other.xStart;
/*  49 */     this.yStart = other.yStart;
/*  50 */     this.zStart = other.zStart;
/*  51 */     this.xEnd = other.xEnd;
/*  52 */     this.yEnd = other.yEnd;
/*  53 */     this.zEnd = other.zEnd;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolLineAction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  58 */     BuilderToolLineAction obj = new BuilderToolLineAction();
/*     */     
/*  60 */     obj.xStart = buf.getIntLE(offset + 0);
/*  61 */     obj.yStart = buf.getIntLE(offset + 4);
/*  62 */     obj.zStart = buf.getIntLE(offset + 8);
/*  63 */     obj.xEnd = buf.getIntLE(offset + 12);
/*  64 */     obj.yEnd = buf.getIntLE(offset + 16);
/*  65 */     obj.zEnd = buf.getIntLE(offset + 20);
/*     */ 
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     return 24;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  78 */     buf.writeIntLE(this.xStart);
/*  79 */     buf.writeIntLE(this.yStart);
/*  80 */     buf.writeIntLE(this.zStart);
/*  81 */     buf.writeIntLE(this.xEnd);
/*  82 */     buf.writeIntLE(this.yEnd);
/*  83 */     buf.writeIntLE(this.zEnd);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  89 */     return 24;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  93 */     if (buffer.readableBytes() - offset < 24) {
/*  94 */       return ValidationResult.error("Buffer too small: expected at least 24 bytes");
/*     */     }
/*     */ 
/*     */     
/*  98 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolLineAction clone() {
/* 102 */     BuilderToolLineAction copy = new BuilderToolLineAction();
/* 103 */     copy.xStart = this.xStart;
/* 104 */     copy.yStart = this.yStart;
/* 105 */     copy.zStart = this.zStart;
/* 106 */     copy.xEnd = this.xEnd;
/* 107 */     copy.yEnd = this.yEnd;
/* 108 */     copy.zEnd = this.zEnd;
/* 109 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolLineAction other;
/* 115 */     if (this == obj) return true; 
/* 116 */     if (obj instanceof BuilderToolLineAction) { other = (BuilderToolLineAction)obj; } else { return false; }
/* 117 */      return (this.xStart == other.xStart && this.yStart == other.yStart && this.zStart == other.zStart && this.xEnd == other.xEnd && this.yEnd == other.yEnd && this.zEnd == other.zEnd);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return Objects.hash(new Object[] { Integer.valueOf(this.xStart), Integer.valueOf(this.yStart), Integer.valueOf(this.zStart), Integer.valueOf(this.xEnd), Integer.valueOf(this.yEnd), Integer.valueOf(this.zEnd) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolLineAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */