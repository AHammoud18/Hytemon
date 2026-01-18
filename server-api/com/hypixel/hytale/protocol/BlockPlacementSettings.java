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
/*     */ public class BlockPlacementSettings
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 16;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 16;
/*     */   public static final int MAX_SIZE = 16;
/*     */   public boolean allowRotationKey;
/*     */   public boolean placeInEmptyBlocks;
/*     */   @Nonnull
/*  22 */   public BlockPreviewVisibility previewVisibility = BlockPreviewVisibility.AlwaysVisible; @Nonnull
/*  23 */   public BlockPlacementRotationMode rotationMode = BlockPlacementRotationMode.FacingPlayer;
/*     */   
/*     */   public int wallPlacementOverrideBlockId;
/*     */   
/*     */   public int floorPlacementOverrideBlockId;
/*     */   
/*     */   public int ceilingPlacementOverrideBlockId;
/*     */   
/*     */   public BlockPlacementSettings(boolean allowRotationKey, boolean placeInEmptyBlocks, @Nonnull BlockPreviewVisibility previewVisibility, @Nonnull BlockPlacementRotationMode rotationMode, int wallPlacementOverrideBlockId, int floorPlacementOverrideBlockId, int ceilingPlacementOverrideBlockId) {
/*  32 */     this.allowRotationKey = allowRotationKey;
/*  33 */     this.placeInEmptyBlocks = placeInEmptyBlocks;
/*  34 */     this.previewVisibility = previewVisibility;
/*  35 */     this.rotationMode = rotationMode;
/*  36 */     this.wallPlacementOverrideBlockId = wallPlacementOverrideBlockId;
/*  37 */     this.floorPlacementOverrideBlockId = floorPlacementOverrideBlockId;
/*  38 */     this.ceilingPlacementOverrideBlockId = ceilingPlacementOverrideBlockId;
/*     */   }
/*     */   
/*     */   public BlockPlacementSettings(@Nonnull BlockPlacementSettings other) {
/*  42 */     this.allowRotationKey = other.allowRotationKey;
/*  43 */     this.placeInEmptyBlocks = other.placeInEmptyBlocks;
/*  44 */     this.previewVisibility = other.previewVisibility;
/*  45 */     this.rotationMode = other.rotationMode;
/*  46 */     this.wallPlacementOverrideBlockId = other.wallPlacementOverrideBlockId;
/*  47 */     this.floorPlacementOverrideBlockId = other.floorPlacementOverrideBlockId;
/*  48 */     this.ceilingPlacementOverrideBlockId = other.ceilingPlacementOverrideBlockId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BlockPlacementSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/*  53 */     BlockPlacementSettings obj = new BlockPlacementSettings();
/*     */     
/*  55 */     obj.allowRotationKey = (buf.getByte(offset + 0) != 0);
/*  56 */     obj.placeInEmptyBlocks = (buf.getByte(offset + 1) != 0);
/*  57 */     obj.previewVisibility = BlockPreviewVisibility.fromValue(buf.getByte(offset + 2));
/*  58 */     obj.rotationMode = BlockPlacementRotationMode.fromValue(buf.getByte(offset + 3));
/*  59 */     obj.wallPlacementOverrideBlockId = buf.getIntLE(offset + 4);
/*  60 */     obj.floorPlacementOverrideBlockId = buf.getIntLE(offset + 8);
/*  61 */     obj.ceilingPlacementOverrideBlockId = buf.getIntLE(offset + 12);
/*     */ 
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     return 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  73 */     buf.writeByte(this.allowRotationKey ? 1 : 0);
/*  74 */     buf.writeByte(this.placeInEmptyBlocks ? 1 : 0);
/*  75 */     buf.writeByte(this.previewVisibility.getValue());
/*  76 */     buf.writeByte(this.rotationMode.getValue());
/*  77 */     buf.writeIntLE(this.wallPlacementOverrideBlockId);
/*  78 */     buf.writeIntLE(this.floorPlacementOverrideBlockId);
/*  79 */     buf.writeIntLE(this.ceilingPlacementOverrideBlockId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  85 */     return 16;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  89 */     if (buffer.readableBytes() - offset < 16) {
/*  90 */       return ValidationResult.error("Buffer too small: expected at least 16 bytes");
/*     */     }
/*     */ 
/*     */     
/*  94 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockPlacementSettings clone() {
/*  98 */     BlockPlacementSettings copy = new BlockPlacementSettings();
/*  99 */     copy.allowRotationKey = this.allowRotationKey;
/* 100 */     copy.placeInEmptyBlocks = this.placeInEmptyBlocks;
/* 101 */     copy.previewVisibility = this.previewVisibility;
/* 102 */     copy.rotationMode = this.rotationMode;
/* 103 */     copy.wallPlacementOverrideBlockId = this.wallPlacementOverrideBlockId;
/* 104 */     copy.floorPlacementOverrideBlockId = this.floorPlacementOverrideBlockId;
/* 105 */     copy.ceilingPlacementOverrideBlockId = this.ceilingPlacementOverrideBlockId;
/* 106 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockPlacementSettings other;
/* 112 */     if (this == obj) return true; 
/* 113 */     if (obj instanceof BlockPlacementSettings) { other = (BlockPlacementSettings)obj; } else { return false; }
/* 114 */      return (this.allowRotationKey == other.allowRotationKey && this.placeInEmptyBlocks == other.placeInEmptyBlocks && Objects.equals(this.previewVisibility, other.previewVisibility) && Objects.equals(this.rotationMode, other.rotationMode) && this.wallPlacementOverrideBlockId == other.wallPlacementOverrideBlockId && this.floorPlacementOverrideBlockId == other.floorPlacementOverrideBlockId && this.ceilingPlacementOverrideBlockId == other.ceilingPlacementOverrideBlockId);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 119 */     return Objects.hash(new Object[] { Boolean.valueOf(this.allowRotationKey), Boolean.valueOf(this.placeInEmptyBlocks), this.previewVisibility, this.rotationMode, Integer.valueOf(this.wallPlacementOverrideBlockId), Integer.valueOf(this.floorPlacementOverrideBlockId), Integer.valueOf(this.ceilingPlacementOverrideBlockId) });
/*     */   }
/*     */   
/*     */   public BlockPlacementSettings() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockPlacementSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */