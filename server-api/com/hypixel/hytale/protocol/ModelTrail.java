/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ModelTrail {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 27;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 35;
/*     */   public static final int MAX_SIZE = 32768045;
/*     */   @Nullable
/*     */   public String trailId;
/*     */   @Nonnull
/*  21 */   public EntityPart targetEntityPart = EntityPart.Self;
/*     */   @Nullable
/*     */   public String targetNodeName;
/*     */   @Nullable
/*     */   public Vector3f positionOffset;
/*     */   @Nullable
/*     */   public Direction rotationOffset;
/*     */   public boolean fixedRotation;
/*     */   
/*     */   public ModelTrail(@Nullable String trailId, @Nonnull EntityPart targetEntityPart, @Nullable String targetNodeName, @Nullable Vector3f positionOffset, @Nullable Direction rotationOffset, boolean fixedRotation) {
/*  31 */     this.trailId = trailId;
/*  32 */     this.targetEntityPart = targetEntityPart;
/*  33 */     this.targetNodeName = targetNodeName;
/*  34 */     this.positionOffset = positionOffset;
/*  35 */     this.rotationOffset = rotationOffset;
/*  36 */     this.fixedRotation = fixedRotation;
/*     */   }
/*     */   
/*     */   public ModelTrail(@Nonnull ModelTrail other) {
/*  40 */     this.trailId = other.trailId;
/*  41 */     this.targetEntityPart = other.targetEntityPart;
/*  42 */     this.targetNodeName = other.targetNodeName;
/*  43 */     this.positionOffset = other.positionOffset;
/*  44 */     this.rotationOffset = other.rotationOffset;
/*  45 */     this.fixedRotation = other.fixedRotation;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ModelTrail deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     ModelTrail obj = new ModelTrail();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.targetEntityPart = EntityPart.fromValue(buf.getByte(offset + 1));
/*  53 */     if ((nullBits & 0x4) != 0) obj.positionOffset = Vector3f.deserialize(buf, offset + 2); 
/*  54 */     if ((nullBits & 0x8) != 0) obj.rotationOffset = Direction.deserialize(buf, offset + 14); 
/*  55 */     obj.fixedRotation = (buf.getByte(offset + 26) != 0);
/*     */     
/*  57 */     if ((nullBits & 0x1) != 0) {
/*  58 */       int varPos0 = offset + 35 + buf.getIntLE(offset + 27);
/*  59 */       int trailIdLen = VarInt.peek(buf, varPos0);
/*  60 */       if (trailIdLen < 0) throw ProtocolException.negativeLength("TrailId", trailIdLen); 
/*  61 */       if (trailIdLen > 4096000) throw ProtocolException.stringTooLong("TrailId", trailIdLen, 4096000); 
/*  62 */       obj.trailId = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  64 */     if ((nullBits & 0x2) != 0) {
/*  65 */       int varPos1 = offset + 35 + buf.getIntLE(offset + 31);
/*  66 */       int targetNodeNameLen = VarInt.peek(buf, varPos1);
/*  67 */       if (targetNodeNameLen < 0) throw ProtocolException.negativeLength("TargetNodeName", targetNodeNameLen); 
/*  68 */       if (targetNodeNameLen > 4096000) throw ProtocolException.stringTooLong("TargetNodeName", targetNodeNameLen, 4096000); 
/*  69 */       obj.targetNodeName = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  72 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  76 */     byte nullBits = buf.getByte(offset);
/*  77 */     int maxEnd = 35;
/*  78 */     if ((nullBits & 0x1) != 0) {
/*  79 */       int fieldOffset0 = buf.getIntLE(offset + 27);
/*  80 */       int pos0 = offset + 35 + fieldOffset0;
/*  81 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  82 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  84 */     if ((nullBits & 0x2) != 0) {
/*  85 */       int fieldOffset1 = buf.getIntLE(offset + 31);
/*  86 */       int pos1 = offset + 35 + fieldOffset1;
/*  87 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  88 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  90 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  95 */     int startPos = buf.writerIndex();
/*  96 */     byte nullBits = 0;
/*  97 */     if (this.trailId != null) nullBits = (byte)(nullBits | 0x1); 
/*  98 */     if (this.targetNodeName != null) nullBits = (byte)(nullBits | 0x2); 
/*  99 */     if (this.positionOffset != null) nullBits = (byte)(nullBits | 0x4); 
/* 100 */     if (this.rotationOffset != null) nullBits = (byte)(nullBits | 0x8); 
/* 101 */     buf.writeByte(nullBits);
/*     */     
/* 103 */     buf.writeByte(this.targetEntityPart.getValue());
/* 104 */     if (this.positionOffset != null) { this.positionOffset.serialize(buf); } else { buf.writeZero(12); }
/* 105 */      if (this.rotationOffset != null) { this.rotationOffset.serialize(buf); } else { buf.writeZero(12); }
/* 106 */      buf.writeByte(this.fixedRotation ? 1 : 0);
/*     */     
/* 108 */     int trailIdOffsetSlot = buf.writerIndex();
/* 109 */     buf.writeIntLE(0);
/* 110 */     int targetNodeNameOffsetSlot = buf.writerIndex();
/* 111 */     buf.writeIntLE(0);
/*     */     
/* 113 */     int varBlockStart = buf.writerIndex();
/* 114 */     if (this.trailId != null) {
/* 115 */       buf.setIntLE(trailIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 116 */       PacketIO.writeVarString(buf, this.trailId, 4096000);
/*     */     } else {
/* 118 */       buf.setIntLE(trailIdOffsetSlot, -1);
/*     */     } 
/* 120 */     if (this.targetNodeName != null) {
/* 121 */       buf.setIntLE(targetNodeNameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 122 */       PacketIO.writeVarString(buf, this.targetNodeName, 4096000);
/*     */     } else {
/* 124 */       buf.setIntLE(targetNodeNameOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 130 */     int size = 35;
/* 131 */     if (this.trailId != null) size += PacketIO.stringSize(this.trailId); 
/* 132 */     if (this.targetNodeName != null) size += PacketIO.stringSize(this.targetNodeName);
/*     */     
/* 134 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 138 */     if (buffer.readableBytes() - offset < 35) {
/* 139 */       return ValidationResult.error("Buffer too small: expected at least 35 bytes");
/*     */     }
/*     */     
/* 142 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 145 */     if ((nullBits & 0x1) != 0) {
/* 146 */       int trailIdOffset = buffer.getIntLE(offset + 27);
/* 147 */       if (trailIdOffset < 0) {
/* 148 */         return ValidationResult.error("Invalid offset for TrailId");
/*     */       }
/* 150 */       int pos = offset + 35 + trailIdOffset;
/* 151 */       if (pos >= buffer.writerIndex()) {
/* 152 */         return ValidationResult.error("Offset out of bounds for TrailId");
/*     */       }
/* 154 */       int trailIdLen = VarInt.peek(buffer, pos);
/* 155 */       if (trailIdLen < 0) {
/* 156 */         return ValidationResult.error("Invalid string length for TrailId");
/*     */       }
/* 158 */       if (trailIdLen > 4096000) {
/* 159 */         return ValidationResult.error("TrailId exceeds max length 4096000");
/*     */       }
/* 161 */       pos += VarInt.length(buffer, pos);
/* 162 */       pos += trailIdLen;
/* 163 */       if (pos > buffer.writerIndex()) {
/* 164 */         return ValidationResult.error("Buffer overflow reading TrailId");
/*     */       }
/*     */     } 
/*     */     
/* 168 */     if ((nullBits & 0x2) != 0) {
/* 169 */       int targetNodeNameOffset = buffer.getIntLE(offset + 31);
/* 170 */       if (targetNodeNameOffset < 0) {
/* 171 */         return ValidationResult.error("Invalid offset for TargetNodeName");
/*     */       }
/* 173 */       int pos = offset + 35 + targetNodeNameOffset;
/* 174 */       if (pos >= buffer.writerIndex()) {
/* 175 */         return ValidationResult.error("Offset out of bounds for TargetNodeName");
/*     */       }
/* 177 */       int targetNodeNameLen = VarInt.peek(buffer, pos);
/* 178 */       if (targetNodeNameLen < 0) {
/* 179 */         return ValidationResult.error("Invalid string length for TargetNodeName");
/*     */       }
/* 181 */       if (targetNodeNameLen > 4096000) {
/* 182 */         return ValidationResult.error("TargetNodeName exceeds max length 4096000");
/*     */       }
/* 184 */       pos += VarInt.length(buffer, pos);
/* 185 */       pos += targetNodeNameLen;
/* 186 */       if (pos > buffer.writerIndex()) {
/* 187 */         return ValidationResult.error("Buffer overflow reading TargetNodeName");
/*     */       }
/*     */     } 
/* 190 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ModelTrail clone() {
/* 194 */     ModelTrail copy = new ModelTrail();
/* 195 */     copy.trailId = this.trailId;
/* 196 */     copy.targetEntityPart = this.targetEntityPart;
/* 197 */     copy.targetNodeName = this.targetNodeName;
/* 198 */     copy.positionOffset = (this.positionOffset != null) ? this.positionOffset.clone() : null;
/* 199 */     copy.rotationOffset = (this.rotationOffset != null) ? this.rotationOffset.clone() : null;
/* 200 */     copy.fixedRotation = this.fixedRotation;
/* 201 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ModelTrail other;
/* 207 */     if (this == obj) return true; 
/* 208 */     if (obj instanceof ModelTrail) { other = (ModelTrail)obj; } else { return false; }
/* 209 */      return (Objects.equals(this.trailId, other.trailId) && Objects.equals(this.targetEntityPart, other.targetEntityPart) && Objects.equals(this.targetNodeName, other.targetNodeName) && Objects.equals(this.positionOffset, other.positionOffset) && Objects.equals(this.rotationOffset, other.rotationOffset) && this.fixedRotation == other.fixedRotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 214 */     return Objects.hash(new Object[] { this.trailId, this.targetEntityPart, this.targetNodeName, this.positionOffset, this.rotationOffset, Boolean.valueOf(this.fixedRotation) });
/*     */   }
/*     */   
/*     */   public ModelTrail() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ModelTrail.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */