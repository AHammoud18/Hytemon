/*     */ package com.hypixel.hytale.protocol;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ModelParticle {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 34;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 42;
/*     */   public static final int MAX_SIZE = 32768052;
/*     */   @Nullable
/*     */   public String systemId;
/*     */   public float scale;
/*     */   @Nullable
/*     */   public Color color;
/*     */   @Nonnull
/*  23 */   public EntityPart targetEntityPart = EntityPart.Self;
/*     */   @Nullable
/*     */   public String targetNodeName;
/*     */   @Nullable
/*     */   public Vector3f positionOffset;
/*     */   @Nullable
/*     */   public Direction rotationOffset;
/*     */   public boolean detachedFromModel;
/*     */   
/*     */   public ModelParticle(@Nullable String systemId, float scale, @Nullable Color color, @Nonnull EntityPart targetEntityPart, @Nullable String targetNodeName, @Nullable Vector3f positionOffset, @Nullable Direction rotationOffset, boolean detachedFromModel) {
/*  33 */     this.systemId = systemId;
/*  34 */     this.scale = scale;
/*  35 */     this.color = color;
/*  36 */     this.targetEntityPart = targetEntityPart;
/*  37 */     this.targetNodeName = targetNodeName;
/*  38 */     this.positionOffset = positionOffset;
/*  39 */     this.rotationOffset = rotationOffset;
/*  40 */     this.detachedFromModel = detachedFromModel;
/*     */   }
/*     */   
/*     */   public ModelParticle(@Nonnull ModelParticle other) {
/*  44 */     this.systemId = other.systemId;
/*  45 */     this.scale = other.scale;
/*  46 */     this.color = other.color;
/*  47 */     this.targetEntityPart = other.targetEntityPart;
/*  48 */     this.targetNodeName = other.targetNodeName;
/*  49 */     this.positionOffset = other.positionOffset;
/*  50 */     this.rotationOffset = other.rotationOffset;
/*  51 */     this.detachedFromModel = other.detachedFromModel;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ModelParticle deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     ModelParticle obj = new ModelParticle();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.scale = buf.getFloatLE(offset + 1);
/*  59 */     if ((nullBits & 0x2) != 0) obj.color = Color.deserialize(buf, offset + 5); 
/*  60 */     obj.targetEntityPart = EntityPart.fromValue(buf.getByte(offset + 8));
/*  61 */     if ((nullBits & 0x8) != 0) obj.positionOffset = Vector3f.deserialize(buf, offset + 9); 
/*  62 */     if ((nullBits & 0x10) != 0) obj.rotationOffset = Direction.deserialize(buf, offset + 21); 
/*  63 */     obj.detachedFromModel = (buf.getByte(offset + 33) != 0);
/*     */     
/*  65 */     if ((nullBits & 0x1) != 0) {
/*  66 */       int varPos0 = offset + 42 + buf.getIntLE(offset + 34);
/*  67 */       int systemIdLen = VarInt.peek(buf, varPos0);
/*  68 */       if (systemIdLen < 0) throw ProtocolException.negativeLength("SystemId", systemIdLen); 
/*  69 */       if (systemIdLen > 4096000) throw ProtocolException.stringTooLong("SystemId", systemIdLen, 4096000); 
/*  70 */       obj.systemId = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  72 */     if ((nullBits & 0x4) != 0) {
/*  73 */       int varPos1 = offset + 42 + buf.getIntLE(offset + 38);
/*  74 */       int targetNodeNameLen = VarInt.peek(buf, varPos1);
/*  75 */       if (targetNodeNameLen < 0) throw ProtocolException.negativeLength("TargetNodeName", targetNodeNameLen); 
/*  76 */       if (targetNodeNameLen > 4096000) throw ProtocolException.stringTooLong("TargetNodeName", targetNodeNameLen, 4096000); 
/*  77 */       obj.targetNodeName = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  80 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  84 */     byte nullBits = buf.getByte(offset);
/*  85 */     int maxEnd = 42;
/*  86 */     if ((nullBits & 0x1) != 0) {
/*  87 */       int fieldOffset0 = buf.getIntLE(offset + 34);
/*  88 */       int pos0 = offset + 42 + fieldOffset0;
/*  89 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  90 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  92 */     if ((nullBits & 0x4) != 0) {
/*  93 */       int fieldOffset1 = buf.getIntLE(offset + 38);
/*  94 */       int pos1 = offset + 42 + fieldOffset1;
/*  95 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  96 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  98 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 103 */     int startPos = buf.writerIndex();
/* 104 */     byte nullBits = 0;
/* 105 */     if (this.systemId != null) nullBits = (byte)(nullBits | 0x1); 
/* 106 */     if (this.color != null) nullBits = (byte)(nullBits | 0x2); 
/* 107 */     if (this.targetNodeName != null) nullBits = (byte)(nullBits | 0x4); 
/* 108 */     if (this.positionOffset != null) nullBits = (byte)(nullBits | 0x8); 
/* 109 */     if (this.rotationOffset != null) nullBits = (byte)(nullBits | 0x10); 
/* 110 */     buf.writeByte(nullBits);
/*     */     
/* 112 */     buf.writeFloatLE(this.scale);
/* 113 */     if (this.color != null) { this.color.serialize(buf); } else { buf.writeZero(3); }
/* 114 */      buf.writeByte(this.targetEntityPart.getValue());
/* 115 */     if (this.positionOffset != null) { this.positionOffset.serialize(buf); } else { buf.writeZero(12); }
/* 116 */      if (this.rotationOffset != null) { this.rotationOffset.serialize(buf); } else { buf.writeZero(12); }
/* 117 */      buf.writeByte(this.detachedFromModel ? 1 : 0);
/*     */     
/* 119 */     int systemIdOffsetSlot = buf.writerIndex();
/* 120 */     buf.writeIntLE(0);
/* 121 */     int targetNodeNameOffsetSlot = buf.writerIndex();
/* 122 */     buf.writeIntLE(0);
/*     */     
/* 124 */     int varBlockStart = buf.writerIndex();
/* 125 */     if (this.systemId != null) {
/* 126 */       buf.setIntLE(systemIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 127 */       PacketIO.writeVarString(buf, this.systemId, 4096000);
/*     */     } else {
/* 129 */       buf.setIntLE(systemIdOffsetSlot, -1);
/*     */     } 
/* 131 */     if (this.targetNodeName != null) {
/* 132 */       buf.setIntLE(targetNodeNameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 133 */       PacketIO.writeVarString(buf, this.targetNodeName, 4096000);
/*     */     } else {
/* 135 */       buf.setIntLE(targetNodeNameOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 141 */     int size = 42;
/* 142 */     if (this.systemId != null) size += PacketIO.stringSize(this.systemId); 
/* 143 */     if (this.targetNodeName != null) size += PacketIO.stringSize(this.targetNodeName);
/*     */     
/* 145 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 149 */     if (buffer.readableBytes() - offset < 42) {
/* 150 */       return ValidationResult.error("Buffer too small: expected at least 42 bytes");
/*     */     }
/*     */     
/* 153 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 156 */     if ((nullBits & 0x1) != 0) {
/* 157 */       int systemIdOffset = buffer.getIntLE(offset + 34);
/* 158 */       if (systemIdOffset < 0) {
/* 159 */         return ValidationResult.error("Invalid offset for SystemId");
/*     */       }
/* 161 */       int pos = offset + 42 + systemIdOffset;
/* 162 */       if (pos >= buffer.writerIndex()) {
/* 163 */         return ValidationResult.error("Offset out of bounds for SystemId");
/*     */       }
/* 165 */       int systemIdLen = VarInt.peek(buffer, pos);
/* 166 */       if (systemIdLen < 0) {
/* 167 */         return ValidationResult.error("Invalid string length for SystemId");
/*     */       }
/* 169 */       if (systemIdLen > 4096000) {
/* 170 */         return ValidationResult.error("SystemId exceeds max length 4096000");
/*     */       }
/* 172 */       pos += VarInt.length(buffer, pos);
/* 173 */       pos += systemIdLen;
/* 174 */       if (pos > buffer.writerIndex()) {
/* 175 */         return ValidationResult.error("Buffer overflow reading SystemId");
/*     */       }
/*     */     } 
/*     */     
/* 179 */     if ((nullBits & 0x4) != 0) {
/* 180 */       int targetNodeNameOffset = buffer.getIntLE(offset + 38);
/* 181 */       if (targetNodeNameOffset < 0) {
/* 182 */         return ValidationResult.error("Invalid offset for TargetNodeName");
/*     */       }
/* 184 */       int pos = offset + 42 + targetNodeNameOffset;
/* 185 */       if (pos >= buffer.writerIndex()) {
/* 186 */         return ValidationResult.error("Offset out of bounds for TargetNodeName");
/*     */       }
/* 188 */       int targetNodeNameLen = VarInt.peek(buffer, pos);
/* 189 */       if (targetNodeNameLen < 0) {
/* 190 */         return ValidationResult.error("Invalid string length for TargetNodeName");
/*     */       }
/* 192 */       if (targetNodeNameLen > 4096000) {
/* 193 */         return ValidationResult.error("TargetNodeName exceeds max length 4096000");
/*     */       }
/* 195 */       pos += VarInt.length(buffer, pos);
/* 196 */       pos += targetNodeNameLen;
/* 197 */       if (pos > buffer.writerIndex()) {
/* 198 */         return ValidationResult.error("Buffer overflow reading TargetNodeName");
/*     */       }
/*     */     } 
/* 201 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ModelParticle clone() {
/* 205 */     ModelParticle copy = new ModelParticle();
/* 206 */     copy.systemId = this.systemId;
/* 207 */     copy.scale = this.scale;
/* 208 */     copy.color = (this.color != null) ? this.color.clone() : null;
/* 209 */     copy.targetEntityPart = this.targetEntityPart;
/* 210 */     copy.targetNodeName = this.targetNodeName;
/* 211 */     copy.positionOffset = (this.positionOffset != null) ? this.positionOffset.clone() : null;
/* 212 */     copy.rotationOffset = (this.rotationOffset != null) ? this.rotationOffset.clone() : null;
/* 213 */     copy.detachedFromModel = this.detachedFromModel;
/* 214 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ModelParticle other;
/* 220 */     if (this == obj) return true; 
/* 221 */     if (obj instanceof ModelParticle) { other = (ModelParticle)obj; } else { return false; }
/* 222 */      return (Objects.equals(this.systemId, other.systemId) && this.scale == other.scale && Objects.equals(this.color, other.color) && Objects.equals(this.targetEntityPart, other.targetEntityPart) && Objects.equals(this.targetNodeName, other.targetNodeName) && Objects.equals(this.positionOffset, other.positionOffset) && Objects.equals(this.rotationOffset, other.rotationOffset) && this.detachedFromModel == other.detachedFromModel);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 227 */     return Objects.hash(new Object[] { this.systemId, Float.valueOf(this.scale), this.color, this.targetEntityPart, this.targetNodeName, this.positionOffset, this.rotationOffset, Boolean.valueOf(this.detachedFromModel) });
/*     */   }
/*     */   
/*     */   public ModelParticle() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ModelParticle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */