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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelDisplay
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 37;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 45;
/*     */   public static final int MAX_SIZE = 32768055;
/*     */   
/*     */   public ModelDisplay(@Nullable String node, @Nullable String attachTo, @Nullable Vector3f translation, @Nullable Vector3f rotation, @Nullable Vector3f scale) {
/*  30 */     this.node = node;
/*  31 */     this.attachTo = attachTo;
/*  32 */     this.translation = translation;
/*  33 */     this.rotation = rotation;
/*  34 */     this.scale = scale; } @Nullable public String node; @Nullable
/*     */   public String attachTo; @Nullable
/*     */   public Vector3f translation; @Nullable
/*     */   public Vector3f rotation; @Nullable
/*  38 */   public Vector3f scale; public ModelDisplay() {} public ModelDisplay(@Nonnull ModelDisplay other) { this.node = other.node;
/*  39 */     this.attachTo = other.attachTo;
/*  40 */     this.translation = other.translation;
/*  41 */     this.rotation = other.rotation;
/*  42 */     this.scale = other.scale; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ModelDisplay deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     ModelDisplay obj = new ModelDisplay();
/*  48 */     byte nullBits = buf.getByte(offset);
/*  49 */     if ((nullBits & 0x4) != 0) obj.translation = Vector3f.deserialize(buf, offset + 1); 
/*  50 */     if ((nullBits & 0x8) != 0) obj.rotation = Vector3f.deserialize(buf, offset + 13); 
/*  51 */     if ((nullBits & 0x10) != 0) obj.scale = Vector3f.deserialize(buf, offset + 25);
/*     */     
/*  53 */     if ((nullBits & 0x1) != 0) {
/*  54 */       int varPos0 = offset + 45 + buf.getIntLE(offset + 37);
/*  55 */       int nodeLen = VarInt.peek(buf, varPos0);
/*  56 */       if (nodeLen < 0) throw ProtocolException.negativeLength("Node", nodeLen); 
/*  57 */       if (nodeLen > 4096000) throw ProtocolException.stringTooLong("Node", nodeLen, 4096000); 
/*  58 */       obj.node = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  60 */     if ((nullBits & 0x2) != 0) {
/*  61 */       int varPos1 = offset + 45 + buf.getIntLE(offset + 41);
/*  62 */       int attachToLen = VarInt.peek(buf, varPos1);
/*  63 */       if (attachToLen < 0) throw ProtocolException.negativeLength("AttachTo", attachToLen); 
/*  64 */       if (attachToLen > 4096000) throw ProtocolException.stringTooLong("AttachTo", attachToLen, 4096000); 
/*  65 */       obj.attachTo = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     byte nullBits = buf.getByte(offset);
/*  73 */     int maxEnd = 45;
/*  74 */     if ((nullBits & 0x1) != 0) {
/*  75 */       int fieldOffset0 = buf.getIntLE(offset + 37);
/*  76 */       int pos0 = offset + 45 + fieldOffset0;
/*  77 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  78 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  80 */     if ((nullBits & 0x2) != 0) {
/*  81 */       int fieldOffset1 = buf.getIntLE(offset + 41);
/*  82 */       int pos1 = offset + 45 + fieldOffset1;
/*  83 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  84 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  86 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  91 */     int startPos = buf.writerIndex();
/*  92 */     byte nullBits = 0;
/*  93 */     if (this.node != null) nullBits = (byte)(nullBits | 0x1); 
/*  94 */     if (this.attachTo != null) nullBits = (byte)(nullBits | 0x2); 
/*  95 */     if (this.translation != null) nullBits = (byte)(nullBits | 0x4); 
/*  96 */     if (this.rotation != null) nullBits = (byte)(nullBits | 0x8); 
/*  97 */     if (this.scale != null) nullBits = (byte)(nullBits | 0x10); 
/*  98 */     buf.writeByte(nullBits);
/*     */     
/* 100 */     if (this.translation != null) { this.translation.serialize(buf); } else { buf.writeZero(12); }
/* 101 */      if (this.rotation != null) { this.rotation.serialize(buf); } else { buf.writeZero(12); }
/* 102 */      if (this.scale != null) { this.scale.serialize(buf); } else { buf.writeZero(12); }
/*     */     
/* 104 */     int nodeOffsetSlot = buf.writerIndex();
/* 105 */     buf.writeIntLE(0);
/* 106 */     int attachToOffsetSlot = buf.writerIndex();
/* 107 */     buf.writeIntLE(0);
/*     */     
/* 109 */     int varBlockStart = buf.writerIndex();
/* 110 */     if (this.node != null) {
/* 111 */       buf.setIntLE(nodeOffsetSlot, buf.writerIndex() - varBlockStart);
/* 112 */       PacketIO.writeVarString(buf, this.node, 4096000);
/*     */     } else {
/* 114 */       buf.setIntLE(nodeOffsetSlot, -1);
/*     */     } 
/* 116 */     if (this.attachTo != null) {
/* 117 */       buf.setIntLE(attachToOffsetSlot, buf.writerIndex() - varBlockStart);
/* 118 */       PacketIO.writeVarString(buf, this.attachTo, 4096000);
/*     */     } else {
/* 120 */       buf.setIntLE(attachToOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 126 */     int size = 45;
/* 127 */     if (this.node != null) size += PacketIO.stringSize(this.node); 
/* 128 */     if (this.attachTo != null) size += PacketIO.stringSize(this.attachTo);
/*     */     
/* 130 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 134 */     if (buffer.readableBytes() - offset < 45) {
/* 135 */       return ValidationResult.error("Buffer too small: expected at least 45 bytes");
/*     */     }
/*     */     
/* 138 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 141 */     if ((nullBits & 0x1) != 0) {
/* 142 */       int nodeOffset = buffer.getIntLE(offset + 37);
/* 143 */       if (nodeOffset < 0) {
/* 144 */         return ValidationResult.error("Invalid offset for Node");
/*     */       }
/* 146 */       int pos = offset + 45 + nodeOffset;
/* 147 */       if (pos >= buffer.writerIndex()) {
/* 148 */         return ValidationResult.error("Offset out of bounds for Node");
/*     */       }
/* 150 */       int nodeLen = VarInt.peek(buffer, pos);
/* 151 */       if (nodeLen < 0) {
/* 152 */         return ValidationResult.error("Invalid string length for Node");
/*     */       }
/* 154 */       if (nodeLen > 4096000) {
/* 155 */         return ValidationResult.error("Node exceeds max length 4096000");
/*     */       }
/* 157 */       pos += VarInt.length(buffer, pos);
/* 158 */       pos += nodeLen;
/* 159 */       if (pos > buffer.writerIndex()) {
/* 160 */         return ValidationResult.error("Buffer overflow reading Node");
/*     */       }
/*     */     } 
/*     */     
/* 164 */     if ((nullBits & 0x2) != 0) {
/* 165 */       int attachToOffset = buffer.getIntLE(offset + 41);
/* 166 */       if (attachToOffset < 0) {
/* 167 */         return ValidationResult.error("Invalid offset for AttachTo");
/*     */       }
/* 169 */       int pos = offset + 45 + attachToOffset;
/* 170 */       if (pos >= buffer.writerIndex()) {
/* 171 */         return ValidationResult.error("Offset out of bounds for AttachTo");
/*     */       }
/* 173 */       int attachToLen = VarInt.peek(buffer, pos);
/* 174 */       if (attachToLen < 0) {
/* 175 */         return ValidationResult.error("Invalid string length for AttachTo");
/*     */       }
/* 177 */       if (attachToLen > 4096000) {
/* 178 */         return ValidationResult.error("AttachTo exceeds max length 4096000");
/*     */       }
/* 180 */       pos += VarInt.length(buffer, pos);
/* 181 */       pos += attachToLen;
/* 182 */       if (pos > buffer.writerIndex()) {
/* 183 */         return ValidationResult.error("Buffer overflow reading AttachTo");
/*     */       }
/*     */     } 
/* 186 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ModelDisplay clone() {
/* 190 */     ModelDisplay copy = new ModelDisplay();
/* 191 */     copy.node = this.node;
/* 192 */     copy.attachTo = this.attachTo;
/* 193 */     copy.translation = (this.translation != null) ? this.translation.clone() : null;
/* 194 */     copy.rotation = (this.rotation != null) ? this.rotation.clone() : null;
/* 195 */     copy.scale = (this.scale != null) ? this.scale.clone() : null;
/* 196 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ModelDisplay other;
/* 202 */     if (this == obj) return true; 
/* 203 */     if (obj instanceof ModelDisplay) { other = (ModelDisplay)obj; } else { return false; }
/* 204 */      return (Objects.equals(this.node, other.node) && Objects.equals(this.attachTo, other.attachTo) && Objects.equals(this.translation, other.translation) && Objects.equals(this.rotation, other.rotation) && Objects.equals(this.scale, other.scale));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 209 */     return Objects.hash(new Object[] { this.node, this.attachTo, this.translation, this.rotation, this.scale });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ModelDisplay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */