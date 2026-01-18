/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeployableConfig
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 10;
/*     */   public static final int MAX_SIZE = 2058;
/*     */   @Nullable
/*     */   public Model model;
/*     */   @Nullable
/*     */   public Model modelPreview;
/*     */   public boolean allowPlaceOnWalls;
/*     */   
/*     */   public DeployableConfig() {}
/*     */   
/*     */   public DeployableConfig(@Nullable Model model, @Nullable Model modelPreview, boolean allowPlaceOnWalls) {
/*  28 */     this.model = model;
/*  29 */     this.modelPreview = modelPreview;
/*  30 */     this.allowPlaceOnWalls = allowPlaceOnWalls;
/*     */   }
/*     */   
/*     */   public DeployableConfig(@Nonnull DeployableConfig other) {
/*  34 */     this.model = other.model;
/*  35 */     this.modelPreview = other.modelPreview;
/*  36 */     this.allowPlaceOnWalls = other.allowPlaceOnWalls;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DeployableConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     DeployableConfig obj = new DeployableConfig();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.allowPlaceOnWalls = (buf.getByte(offset + 1) != 0);
/*     */     
/*  45 */     if ((nullBits & 0x1) != 0) {
/*  46 */       int varPos0 = offset + 10 + buf.getIntLE(offset + 2);
/*  47 */       obj.model = Model.deserialize(buf, varPos0);
/*     */     } 
/*  49 */     if ((nullBits & 0x2) != 0) {
/*  50 */       int varPos1 = offset + 10 + buf.getIntLE(offset + 6);
/*  51 */       obj.modelPreview = Model.deserialize(buf, varPos1);
/*     */     } 
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int maxEnd = 10;
/*  60 */     if ((nullBits & 0x1) != 0) {
/*  61 */       int fieldOffset0 = buf.getIntLE(offset + 2);
/*  62 */       int pos0 = offset + 10 + fieldOffset0;
/*  63 */       pos0 += Model.computeBytesConsumed(buf, pos0);
/*  64 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  66 */     if ((nullBits & 0x2) != 0) {
/*  67 */       int fieldOffset1 = buf.getIntLE(offset + 6);
/*  68 */       int pos1 = offset + 10 + fieldOffset1;
/*  69 */       pos1 += Model.computeBytesConsumed(buf, pos1);
/*  70 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  72 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  77 */     int startPos = buf.writerIndex();
/*  78 */     byte nullBits = 0;
/*  79 */     if (this.model != null) nullBits = (byte)(nullBits | 0x1); 
/*  80 */     if (this.modelPreview != null) nullBits = (byte)(nullBits | 0x2); 
/*  81 */     buf.writeByte(nullBits);
/*     */     
/*  83 */     buf.writeByte(this.allowPlaceOnWalls ? 1 : 0);
/*     */     
/*  85 */     int modelOffsetSlot = buf.writerIndex();
/*  86 */     buf.writeIntLE(0);
/*  87 */     int modelPreviewOffsetSlot = buf.writerIndex();
/*  88 */     buf.writeIntLE(0);
/*     */     
/*  90 */     int varBlockStart = buf.writerIndex();
/*  91 */     if (this.model != null) {
/*  92 */       buf.setIntLE(modelOffsetSlot, buf.writerIndex() - varBlockStart);
/*  93 */       this.model.serialize(buf);
/*     */     } else {
/*  95 */       buf.setIntLE(modelOffsetSlot, -1);
/*     */     } 
/*  97 */     if (this.modelPreview != null) {
/*  98 */       buf.setIntLE(modelPreviewOffsetSlot, buf.writerIndex() - varBlockStart);
/*  99 */       this.modelPreview.serialize(buf);
/*     */     } else {
/* 101 */       buf.setIntLE(modelPreviewOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 107 */     int size = 10;
/* 108 */     if (this.model != null) size += this.model.computeSize(); 
/* 109 */     if (this.modelPreview != null) size += this.modelPreview.computeSize();
/*     */     
/* 111 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 115 */     if (buffer.readableBytes() - offset < 10) {
/* 116 */       return ValidationResult.error("Buffer too small: expected at least 10 bytes");
/*     */     }
/*     */     
/* 119 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 122 */     if ((nullBits & 0x1) != 0) {
/* 123 */       int modelOffset = buffer.getIntLE(offset + 2);
/* 124 */       if (modelOffset < 0) {
/* 125 */         return ValidationResult.error("Invalid offset for Model");
/*     */       }
/* 127 */       int pos = offset + 10 + modelOffset;
/* 128 */       if (pos >= buffer.writerIndex()) {
/* 129 */         return ValidationResult.error("Offset out of bounds for Model");
/*     */       }
/* 131 */       ValidationResult modelResult = Model.validateStructure(buffer, pos);
/* 132 */       if (!modelResult.isValid()) {
/* 133 */         return ValidationResult.error("Invalid Model: " + modelResult.error());
/*     */       }
/* 135 */       pos += Model.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 138 */     if ((nullBits & 0x2) != 0) {
/* 139 */       int modelPreviewOffset = buffer.getIntLE(offset + 6);
/* 140 */       if (modelPreviewOffset < 0) {
/* 141 */         return ValidationResult.error("Invalid offset for ModelPreview");
/*     */       }
/* 143 */       int pos = offset + 10 + modelPreviewOffset;
/* 144 */       if (pos >= buffer.writerIndex()) {
/* 145 */         return ValidationResult.error("Offset out of bounds for ModelPreview");
/*     */       }
/* 147 */       ValidationResult modelPreviewResult = Model.validateStructure(buffer, pos);
/* 148 */       if (!modelPreviewResult.isValid()) {
/* 149 */         return ValidationResult.error("Invalid ModelPreview: " + modelPreviewResult.error());
/*     */       }
/* 151 */       pos += Model.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 153 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public DeployableConfig clone() {
/* 157 */     DeployableConfig copy = new DeployableConfig();
/* 158 */     copy.model = (this.model != null) ? this.model.clone() : null;
/* 159 */     copy.modelPreview = (this.modelPreview != null) ? this.modelPreview.clone() : null;
/* 160 */     copy.allowPlaceOnWalls = this.allowPlaceOnWalls;
/* 161 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     DeployableConfig other;
/* 167 */     if (this == obj) return true; 
/* 168 */     if (obj instanceof DeployableConfig) { other = (DeployableConfig)obj; } else { return false; }
/* 169 */      return (Objects.equals(this.model, other.model) && Objects.equals(this.modelPreview, other.modelPreview) && this.allowPlaceOnWalls == other.allowPlaceOnWalls);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 174 */     return Objects.hash(new Object[] { this.model, this.modelPreview, Boolean.valueOf(this.allowPlaceOnWalls) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\DeployableConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */