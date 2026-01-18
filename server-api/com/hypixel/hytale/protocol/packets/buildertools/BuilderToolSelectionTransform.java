/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BuilderToolSelectionTransform
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 405;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 52;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 52;
/*     */   public static final int MAX_SIZE = 16384057;
/*     */   
/*     */   public int getId() {
/*  26 */     return 405;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public float[] transformationMatrix;
/*     */   
/*     */   @Nullable
/*     */   public BlockPosition initialSelectionMin;
/*     */   
/*     */   @Nullable
/*     */   public BlockPosition initialSelectionMax;
/*     */   @Nullable
/*     */   public Vector3f initialRotationOrigin;
/*     */   
/*     */   public BuilderToolSelectionTransform(@Nullable float[] transformationMatrix, @Nullable BlockPosition initialSelectionMin, @Nullable BlockPosition initialSelectionMax, @Nullable Vector3f initialRotationOrigin, boolean cutOriginal, boolean applyTransformationToSelectionMinMax, boolean isExitingTransformMode, @Nullable BlockPosition initialPastePointForClipboardPaste) {
/*  42 */     this.transformationMatrix = transformationMatrix;
/*  43 */     this.initialSelectionMin = initialSelectionMin;
/*  44 */     this.initialSelectionMax = initialSelectionMax;
/*  45 */     this.initialRotationOrigin = initialRotationOrigin;
/*  46 */     this.cutOriginal = cutOriginal;
/*  47 */     this.applyTransformationToSelectionMinMax = applyTransformationToSelectionMinMax;
/*  48 */     this.isExitingTransformMode = isExitingTransformMode;
/*  49 */     this.initialPastePointForClipboardPaste = initialPastePointForClipboardPaste;
/*     */   } public boolean cutOriginal; public boolean applyTransformationToSelectionMinMax; public boolean isExitingTransformMode; @Nullable
/*     */   public BlockPosition initialPastePointForClipboardPaste; public BuilderToolSelectionTransform() {}
/*     */   public BuilderToolSelectionTransform(@Nonnull BuilderToolSelectionTransform other) {
/*  53 */     this.transformationMatrix = other.transformationMatrix;
/*  54 */     this.initialSelectionMin = other.initialSelectionMin;
/*  55 */     this.initialSelectionMax = other.initialSelectionMax;
/*  56 */     this.initialRotationOrigin = other.initialRotationOrigin;
/*  57 */     this.cutOriginal = other.cutOriginal;
/*  58 */     this.applyTransformationToSelectionMinMax = other.applyTransformationToSelectionMinMax;
/*  59 */     this.isExitingTransformMode = other.isExitingTransformMode;
/*  60 */     this.initialPastePointForClipboardPaste = other.initialPastePointForClipboardPaste;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolSelectionTransform deserialize(@Nonnull ByteBuf buf, int offset) {
/*  65 */     BuilderToolSelectionTransform obj = new BuilderToolSelectionTransform();
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     if ((nullBits & 0x2) != 0) obj.initialSelectionMin = BlockPosition.deserialize(buf, offset + 1); 
/*  68 */     if ((nullBits & 0x4) != 0) obj.initialSelectionMax = BlockPosition.deserialize(buf, offset + 13); 
/*  69 */     if ((nullBits & 0x8) != 0) obj.initialRotationOrigin = Vector3f.deserialize(buf, offset + 25); 
/*  70 */     obj.cutOriginal = (buf.getByte(offset + 37) != 0);
/*  71 */     obj.applyTransformationToSelectionMinMax = (buf.getByte(offset + 38) != 0);
/*  72 */     obj.isExitingTransformMode = (buf.getByte(offset + 39) != 0);
/*  73 */     if ((nullBits & 0x10) != 0) obj.initialPastePointForClipboardPaste = BlockPosition.deserialize(buf, offset + 40);
/*     */     
/*  75 */     int pos = offset + 52;
/*  76 */     if ((nullBits & 0x1) != 0) { int transformationMatrixCount = VarInt.peek(buf, pos);
/*  77 */       if (transformationMatrixCount < 0) throw ProtocolException.negativeLength("TransformationMatrix", transformationMatrixCount); 
/*  78 */       if (transformationMatrixCount > 4096000) throw ProtocolException.arrayTooLong("TransformationMatrix", transformationMatrixCount, 4096000); 
/*  79 */       int transformationMatrixVarLen = VarInt.size(transformationMatrixCount);
/*  80 */       if ((pos + transformationMatrixVarLen) + transformationMatrixCount * 4L > buf.readableBytes())
/*  81 */         throw ProtocolException.bufferTooSmall("TransformationMatrix", pos + transformationMatrixVarLen + transformationMatrixCount * 4, buf.readableBytes()); 
/*  82 */       pos += transformationMatrixVarLen;
/*  83 */       obj.transformationMatrix = new float[transformationMatrixCount];
/*  84 */       for (int i = 0; i < transformationMatrixCount; i++) {
/*  85 */         obj.transformationMatrix[i] = buf.getFloatLE(pos + i * 4);
/*     */       }
/*  87 */       pos += transformationMatrixCount * 4; }
/*     */     
/*  89 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  93 */     byte nullBits = buf.getByte(offset);
/*  94 */     int pos = offset + 52;
/*  95 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 4; }
/*  96 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 102 */     byte nullBits = 0;
/* 103 */     if (this.transformationMatrix != null) nullBits = (byte)(nullBits | 0x1); 
/* 104 */     if (this.initialSelectionMin != null) nullBits = (byte)(nullBits | 0x2); 
/* 105 */     if (this.initialSelectionMax != null) nullBits = (byte)(nullBits | 0x4); 
/* 106 */     if (this.initialRotationOrigin != null) nullBits = (byte)(nullBits | 0x8); 
/* 107 */     if (this.initialPastePointForClipboardPaste != null) nullBits = (byte)(nullBits | 0x10); 
/* 108 */     buf.writeByte(nullBits);
/*     */     
/* 110 */     if (this.initialSelectionMin != null) { this.initialSelectionMin.serialize(buf); } else { buf.writeZero(12); }
/* 111 */      if (this.initialSelectionMax != null) { this.initialSelectionMax.serialize(buf); } else { buf.writeZero(12); }
/* 112 */      if (this.initialRotationOrigin != null) { this.initialRotationOrigin.serialize(buf); } else { buf.writeZero(12); }
/* 113 */      buf.writeByte(this.cutOriginal ? 1 : 0);
/* 114 */     buf.writeByte(this.applyTransformationToSelectionMinMax ? 1 : 0);
/* 115 */     buf.writeByte(this.isExitingTransformMode ? 1 : 0);
/* 116 */     if (this.initialPastePointForClipboardPaste != null) { this.initialPastePointForClipboardPaste.serialize(buf); } else { buf.writeZero(12); }
/*     */     
/* 118 */     if (this.transformationMatrix != null) { if (this.transformationMatrix.length > 4096000) throw ProtocolException.arrayTooLong("TransformationMatrix", this.transformationMatrix.length, 4096000);  VarInt.write(buf, this.transformationMatrix.length); for (float item : this.transformationMatrix) buf.writeFloatLE(item);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/* 123 */     int size = 52;
/* 124 */     if (this.transformationMatrix != null) size += VarInt.size(this.transformationMatrix.length) + this.transformationMatrix.length * 4;
/*     */     
/* 126 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 130 */     if (buffer.readableBytes() - offset < 52) {
/* 131 */       return ValidationResult.error("Buffer too small: expected at least 52 bytes");
/*     */     }
/*     */     
/* 134 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 136 */     int pos = offset + 52;
/*     */     
/* 138 */     if ((nullBits & 0x1) != 0) {
/* 139 */       int transformationMatrixCount = VarInt.peek(buffer, pos);
/* 140 */       if (transformationMatrixCount < 0) {
/* 141 */         return ValidationResult.error("Invalid array count for TransformationMatrix");
/*     */       }
/* 143 */       if (transformationMatrixCount > 4096000) {
/* 144 */         return ValidationResult.error("TransformationMatrix exceeds max length 4096000");
/*     */       }
/* 146 */       pos += VarInt.length(buffer, pos);
/* 147 */       pos += transformationMatrixCount * 4;
/* 148 */       if (pos > buffer.writerIndex()) {
/* 149 */         return ValidationResult.error("Buffer overflow reading TransformationMatrix");
/*     */       }
/*     */     } 
/* 152 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolSelectionTransform clone() {
/* 156 */     BuilderToolSelectionTransform copy = new BuilderToolSelectionTransform();
/* 157 */     copy.transformationMatrix = (this.transformationMatrix != null) ? Arrays.copyOf(this.transformationMatrix, this.transformationMatrix.length) : null;
/* 158 */     copy.initialSelectionMin = (this.initialSelectionMin != null) ? this.initialSelectionMin.clone() : null;
/* 159 */     copy.initialSelectionMax = (this.initialSelectionMax != null) ? this.initialSelectionMax.clone() : null;
/* 160 */     copy.initialRotationOrigin = (this.initialRotationOrigin != null) ? this.initialRotationOrigin.clone() : null;
/* 161 */     copy.cutOriginal = this.cutOriginal;
/* 162 */     copy.applyTransformationToSelectionMinMax = this.applyTransformationToSelectionMinMax;
/* 163 */     copy.isExitingTransformMode = this.isExitingTransformMode;
/* 164 */     copy.initialPastePointForClipboardPaste = (this.initialPastePointForClipboardPaste != null) ? this.initialPastePointForClipboardPaste.clone() : null;
/* 165 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolSelectionTransform other;
/* 171 */     if (this == obj) return true; 
/* 172 */     if (obj instanceof BuilderToolSelectionTransform) { other = (BuilderToolSelectionTransform)obj; } else { return false; }
/* 173 */      return (Arrays.equals(this.transformationMatrix, other.transformationMatrix) && Objects.equals(this.initialSelectionMin, other.initialSelectionMin) && Objects.equals(this.initialSelectionMax, other.initialSelectionMax) && Objects.equals(this.initialRotationOrigin, other.initialRotationOrigin) && this.cutOriginal == other.cutOriginal && this.applyTransformationToSelectionMinMax == other.applyTransformationToSelectionMinMax && this.isExitingTransformMode == other.isExitingTransformMode && Objects.equals(this.initialPastePointForClipboardPaste, other.initialPastePointForClipboardPaste));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 178 */     int result = 1;
/* 179 */     result = 31 * result + Arrays.hashCode(this.transformationMatrix);
/* 180 */     result = 31 * result + Objects.hashCode(this.initialSelectionMin);
/* 181 */     result = 31 * result + Objects.hashCode(this.initialSelectionMax);
/* 182 */     result = 31 * result + Objects.hashCode(this.initialRotationOrigin);
/* 183 */     result = 31 * result + Boolean.hashCode(this.cutOriginal);
/* 184 */     result = 31 * result + Boolean.hashCode(this.applyTransformationToSelectionMinMax);
/* 185 */     result = 31 * result + Boolean.hashCode(this.isExitingTransformMode);
/* 186 */     result = 31 * result + Objects.hashCode(this.initialPastePointForClipboardPaste);
/* 187 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolSelectionTransform.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */