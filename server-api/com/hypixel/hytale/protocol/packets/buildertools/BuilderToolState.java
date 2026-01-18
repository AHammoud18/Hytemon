/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class BuilderToolState
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 14;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public BuilderToolState(@Nullable String id, boolean isBrush, @Nullable BuilderToolBrushData brushData, @Nullable Map<String, BuilderToolArg> args) {
/*  29 */     this.id = id;
/*  30 */     this.isBrush = isBrush;
/*  31 */     this.brushData = brushData;
/*  32 */     this.args = args; } @Nullable
/*     */   public String id; public boolean isBrush; @Nullable
/*     */   public BuilderToolBrushData brushData; @Nullable
/*     */   public Map<String, BuilderToolArg> args; public BuilderToolState() {} public BuilderToolState(@Nonnull BuilderToolState other) {
/*  36 */     this.id = other.id;
/*  37 */     this.isBrush = other.isBrush;
/*  38 */     this.brushData = other.brushData;
/*  39 */     this.args = other.args;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolState deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     BuilderToolState obj = new BuilderToolState();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.isBrush = (buf.getByte(offset + 1) != 0);
/*     */     
/*  48 */     if ((nullBits & 0x1) != 0) {
/*  49 */       int varPos0 = offset + 14 + buf.getIntLE(offset + 2);
/*  50 */       int idLen = VarInt.peek(buf, varPos0);
/*  51 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  52 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  53 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  55 */     if ((nullBits & 0x2) != 0) {
/*  56 */       int varPos1 = offset + 14 + buf.getIntLE(offset + 6);
/*  57 */       obj.brushData = BuilderToolBrushData.deserialize(buf, varPos1);
/*     */     } 
/*  59 */     if ((nullBits & 0x4) != 0) {
/*  60 */       int varPos2 = offset + 14 + buf.getIntLE(offset + 10);
/*  61 */       int argsCount = VarInt.peek(buf, varPos2);
/*  62 */       if (argsCount < 0) throw ProtocolException.negativeLength("Args", argsCount); 
/*  63 */       if (argsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Args", argsCount, 4096000); 
/*  64 */       int varIntLen = VarInt.length(buf, varPos2);
/*  65 */       obj.args = new HashMap<>(argsCount);
/*  66 */       int dictPos = varPos2 + varIntLen;
/*  67 */       for (int i = 0; i < argsCount; i++) {
/*  68 */         int keyLen = VarInt.peek(buf, dictPos);
/*  69 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  70 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  71 */         int keyVarLen = VarInt.length(buf, dictPos);
/*  72 */         String key = PacketIO.readVarString(buf, dictPos);
/*  73 */         dictPos += keyVarLen + keyLen;
/*  74 */         BuilderToolArg val = BuilderToolArg.deserialize(buf, dictPos);
/*  75 */         dictPos += BuilderToolArg.computeBytesConsumed(buf, dictPos);
/*  76 */         if (obj.args.put(key, val) != null) {
/*  77 */           throw ProtocolException.duplicateKey("args", key);
/*     */         }
/*     */       } 
/*     */     } 
/*  81 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  85 */     byte nullBits = buf.getByte(offset);
/*  86 */     int maxEnd = 14;
/*  87 */     if ((nullBits & 0x1) != 0) {
/*  88 */       int fieldOffset0 = buf.getIntLE(offset + 2);
/*  89 */       int pos0 = offset + 14 + fieldOffset0;
/*  90 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  91 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  93 */     if ((nullBits & 0x2) != 0) {
/*  94 */       int fieldOffset1 = buf.getIntLE(offset + 6);
/*  95 */       int pos1 = offset + 14 + fieldOffset1;
/*  96 */       pos1 += BuilderToolBrushData.computeBytesConsumed(buf, pos1);
/*  97 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  99 */     if ((nullBits & 0x4) != 0) {
/* 100 */       int fieldOffset2 = buf.getIntLE(offset + 10);
/* 101 */       int pos2 = offset + 14 + fieldOffset2;
/* 102 */       int dictLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 103 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl; pos2 += BuilderToolArg.computeBytesConsumed(buf, pos2); i++; }
/* 104 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 106 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 111 */     int startPos = buf.writerIndex();
/* 112 */     byte nullBits = 0;
/* 113 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 114 */     if (this.brushData != null) nullBits = (byte)(nullBits | 0x2); 
/* 115 */     if (this.args != null) nullBits = (byte)(nullBits | 0x4); 
/* 116 */     buf.writeByte(nullBits);
/*     */     
/* 118 */     buf.writeByte(this.isBrush ? 1 : 0);
/*     */     
/* 120 */     int idOffsetSlot = buf.writerIndex();
/* 121 */     buf.writeIntLE(0);
/* 122 */     int brushDataOffsetSlot = buf.writerIndex();
/* 123 */     buf.writeIntLE(0);
/* 124 */     int argsOffsetSlot = buf.writerIndex();
/* 125 */     buf.writeIntLE(0);
/*     */     
/* 127 */     int varBlockStart = buf.writerIndex();
/* 128 */     if (this.id != null) {
/* 129 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 130 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 132 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 134 */     if (this.brushData != null) {
/* 135 */       buf.setIntLE(brushDataOffsetSlot, buf.writerIndex() - varBlockStart);
/* 136 */       this.brushData.serialize(buf);
/*     */     } else {
/* 138 */       buf.setIntLE(brushDataOffsetSlot, -1);
/*     */     } 
/* 140 */     if (this.args != null)
/* 141 */     { buf.setIntLE(argsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 142 */       if (this.args.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Args", this.args.size(), 4096000);  VarInt.write(buf, this.args.size()); for (Map.Entry<String, BuilderToolArg> e : this.args.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((BuilderToolArg)e.getValue()).serialize(buf); }
/*     */        }
/* 144 */     else { buf.setIntLE(argsOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 150 */     int size = 14;
/* 151 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 152 */     if (this.brushData != null) size += this.brushData.computeSize(); 
/* 153 */     if (this.args != null) {
/* 154 */       int argsSize = 0;
/* 155 */       for (Map.Entry<String, BuilderToolArg> kvp : this.args.entrySet()) argsSize += PacketIO.stringSize(kvp.getKey()) + ((BuilderToolArg)kvp.getValue()).computeSize(); 
/* 156 */       size += VarInt.size(this.args.size()) + argsSize;
/*     */     } 
/*     */     
/* 159 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 163 */     if (buffer.readableBytes() - offset < 14) {
/* 164 */       return ValidationResult.error("Buffer too small: expected at least 14 bytes");
/*     */     }
/*     */     
/* 167 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 170 */     if ((nullBits & 0x1) != 0) {
/* 171 */       int idOffset = buffer.getIntLE(offset + 2);
/* 172 */       if (idOffset < 0) {
/* 173 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 175 */       int pos = offset + 14 + idOffset;
/* 176 */       if (pos >= buffer.writerIndex()) {
/* 177 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 179 */       int idLen = VarInt.peek(buffer, pos);
/* 180 */       if (idLen < 0) {
/* 181 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 183 */       if (idLen > 4096000) {
/* 184 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 186 */       pos += VarInt.length(buffer, pos);
/* 187 */       pos += idLen;
/* 188 */       if (pos > buffer.writerIndex()) {
/* 189 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 193 */     if ((nullBits & 0x2) != 0) {
/* 194 */       int brushDataOffset = buffer.getIntLE(offset + 6);
/* 195 */       if (brushDataOffset < 0) {
/* 196 */         return ValidationResult.error("Invalid offset for BrushData");
/*     */       }
/* 198 */       int pos = offset + 14 + brushDataOffset;
/* 199 */       if (pos >= buffer.writerIndex()) {
/* 200 */         return ValidationResult.error("Offset out of bounds for BrushData");
/*     */       }
/* 202 */       ValidationResult brushDataResult = BuilderToolBrushData.validateStructure(buffer, pos);
/* 203 */       if (!brushDataResult.isValid()) {
/* 204 */         return ValidationResult.error("Invalid BrushData: " + brushDataResult.error());
/*     */       }
/* 206 */       pos += BuilderToolBrushData.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 209 */     if ((nullBits & 0x4) != 0) {
/* 210 */       int argsOffset = buffer.getIntLE(offset + 10);
/* 211 */       if (argsOffset < 0) {
/* 212 */         return ValidationResult.error("Invalid offset for Args");
/*     */       }
/* 214 */       int pos = offset + 14 + argsOffset;
/* 215 */       if (pos >= buffer.writerIndex()) {
/* 216 */         return ValidationResult.error("Offset out of bounds for Args");
/*     */       }
/* 218 */       int argsCount = VarInt.peek(buffer, pos);
/* 219 */       if (argsCount < 0) {
/* 220 */         return ValidationResult.error("Invalid dictionary count for Args");
/*     */       }
/* 222 */       if (argsCount > 4096000) {
/* 223 */         return ValidationResult.error("Args exceeds max length 4096000");
/*     */       }
/* 225 */       pos += VarInt.length(buffer, pos);
/* 226 */       for (int i = 0; i < argsCount; i++) {
/* 227 */         int keyLen = VarInt.peek(buffer, pos);
/* 228 */         if (keyLen < 0) {
/* 229 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 231 */         if (keyLen > 4096000) {
/* 232 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 234 */         pos += VarInt.length(buffer, pos);
/* 235 */         pos += keyLen;
/* 236 */         if (pos > buffer.writerIndex()) {
/* 237 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 239 */         pos += BuilderToolArg.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 243 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolState clone() {
/* 247 */     BuilderToolState copy = new BuilderToolState();
/* 248 */     copy.id = this.id;
/* 249 */     copy.isBrush = this.isBrush;
/* 250 */     copy.brushData = (this.brushData != null) ? this.brushData.clone() : null;
/* 251 */     if (this.args != null) {
/* 252 */       Map<String, BuilderToolArg> m = new HashMap<>();
/* 253 */       for (Map.Entry<String, BuilderToolArg> e : this.args.entrySet()) m.put(e.getKey(), ((BuilderToolArg)e.getValue()).clone()); 
/* 254 */       copy.args = m;
/*     */     } 
/* 256 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolState other;
/* 262 */     if (this == obj) return true; 
/* 263 */     if (obj instanceof BuilderToolState) { other = (BuilderToolState)obj; } else { return false; }
/* 264 */      return (Objects.equals(this.id, other.id) && this.isBrush == other.isBrush && Objects.equals(this.brushData, other.brushData) && Objects.equals(this.args, other.args));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 269 */     return Objects.hash(new Object[] { this.id, Boolean.valueOf(this.isBrush), this.brushData, this.args });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */