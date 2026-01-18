/*     */ package com.hypixel.hytale.protocol.packets.machinima;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class UpdateMachinimaScene
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 262;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   
/*     */   public int getId() {
/*  25 */     return 262;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 18; public static final int MAX_SIZE = 36864033; @Nullable
/*     */   public String player; @Nullable
/*     */   public String sceneName; public float frame;
/*     */   @Nonnull
/*  31 */   public SceneUpdateType updateType = SceneUpdateType.Update;
/*     */   
/*     */   @Nullable
/*     */   public byte[] scene;
/*     */ 
/*     */   
/*     */   public UpdateMachinimaScene(@Nullable String player, @Nullable String sceneName, float frame, @Nonnull SceneUpdateType updateType, @Nullable byte[] scene) {
/*  38 */     this.player = player;
/*  39 */     this.sceneName = sceneName;
/*  40 */     this.frame = frame;
/*  41 */     this.updateType = updateType;
/*  42 */     this.scene = scene;
/*     */   }
/*     */   
/*     */   public UpdateMachinimaScene(@Nonnull UpdateMachinimaScene other) {
/*  46 */     this.player = other.player;
/*  47 */     this.sceneName = other.sceneName;
/*  48 */     this.frame = other.frame;
/*  49 */     this.updateType = other.updateType;
/*  50 */     this.scene = other.scene;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateMachinimaScene deserialize(@Nonnull ByteBuf buf, int offset) {
/*  55 */     UpdateMachinimaScene obj = new UpdateMachinimaScene();
/*  56 */     byte nullBits = buf.getByte(offset);
/*  57 */     obj.frame = buf.getFloatLE(offset + 1);
/*  58 */     obj.updateType = SceneUpdateType.fromValue(buf.getByte(offset + 5));
/*     */     
/*  60 */     if ((nullBits & 0x1) != 0) {
/*  61 */       int varPos0 = offset + 18 + buf.getIntLE(offset + 6);
/*  62 */       int playerLen = VarInt.peek(buf, varPos0);
/*  63 */       if (playerLen < 0) throw ProtocolException.negativeLength("Player", playerLen); 
/*  64 */       if (playerLen > 4096000) throw ProtocolException.stringTooLong("Player", playerLen, 4096000); 
/*  65 */       obj.player = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  67 */     if ((nullBits & 0x2) != 0) {
/*  68 */       int varPos1 = offset + 18 + buf.getIntLE(offset + 10);
/*  69 */       int sceneNameLen = VarInt.peek(buf, varPos1);
/*  70 */       if (sceneNameLen < 0) throw ProtocolException.negativeLength("SceneName", sceneNameLen); 
/*  71 */       if (sceneNameLen > 4096000) throw ProtocolException.stringTooLong("SceneName", sceneNameLen, 4096000); 
/*  72 */       obj.sceneName = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  74 */     if ((nullBits & 0x4) != 0) {
/*  75 */       int varPos2 = offset + 18 + buf.getIntLE(offset + 14);
/*  76 */       int sceneCount = VarInt.peek(buf, varPos2);
/*  77 */       if (sceneCount < 0) throw ProtocolException.negativeLength("Scene", sceneCount); 
/*  78 */       if (sceneCount > 4096000) throw ProtocolException.arrayTooLong("Scene", sceneCount, 4096000); 
/*  79 */       int varIntLen = VarInt.length(buf, varPos2);
/*  80 */       if ((varPos2 + varIntLen) + sceneCount * 1L > buf.readableBytes())
/*  81 */         throw ProtocolException.bufferTooSmall("Scene", varPos2 + varIntLen + sceneCount * 1, buf.readableBytes()); 
/*  82 */       obj.scene = new byte[sceneCount];
/*  83 */       for (int i = 0; i < sceneCount; i++) {
/*  84 */         obj.scene[i] = buf.getByte(varPos2 + varIntLen + i * 1);
/*     */       }
/*     */     } 
/*     */     
/*  88 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  92 */     byte nullBits = buf.getByte(offset);
/*  93 */     int maxEnd = 18;
/*  94 */     if ((nullBits & 0x1) != 0) {
/*  95 */       int fieldOffset0 = buf.getIntLE(offset + 6);
/*  96 */       int pos0 = offset + 18 + fieldOffset0;
/*  97 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  98 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 100 */     if ((nullBits & 0x2) != 0) {
/* 101 */       int fieldOffset1 = buf.getIntLE(offset + 10);
/* 102 */       int pos1 = offset + 18 + fieldOffset1;
/* 103 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 104 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 106 */     if ((nullBits & 0x4) != 0) {
/* 107 */       int fieldOffset2 = buf.getIntLE(offset + 14);
/* 108 */       int pos2 = offset + 18 + fieldOffset2;
/* 109 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + arrLen * 1;
/* 110 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 112 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 118 */     int startPos = buf.writerIndex();
/* 119 */     byte nullBits = 0;
/* 120 */     if (this.player != null) nullBits = (byte)(nullBits | 0x1); 
/* 121 */     if (this.sceneName != null) nullBits = (byte)(nullBits | 0x2); 
/* 122 */     if (this.scene != null) nullBits = (byte)(nullBits | 0x4); 
/* 123 */     buf.writeByte(nullBits);
/*     */     
/* 125 */     buf.writeFloatLE(this.frame);
/* 126 */     buf.writeByte(this.updateType.getValue());
/*     */     
/* 128 */     int playerOffsetSlot = buf.writerIndex();
/* 129 */     buf.writeIntLE(0);
/* 130 */     int sceneNameOffsetSlot = buf.writerIndex();
/* 131 */     buf.writeIntLE(0);
/* 132 */     int sceneOffsetSlot = buf.writerIndex();
/* 133 */     buf.writeIntLE(0);
/*     */     
/* 135 */     int varBlockStart = buf.writerIndex();
/* 136 */     if (this.player != null) {
/* 137 */       buf.setIntLE(playerOffsetSlot, buf.writerIndex() - varBlockStart);
/* 138 */       PacketIO.writeVarString(buf, this.player, 4096000);
/*     */     } else {
/* 140 */       buf.setIntLE(playerOffsetSlot, -1);
/*     */     } 
/* 142 */     if (this.sceneName != null) {
/* 143 */       buf.setIntLE(sceneNameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 144 */       PacketIO.writeVarString(buf, this.sceneName, 4096000);
/*     */     } else {
/* 146 */       buf.setIntLE(sceneNameOffsetSlot, -1);
/*     */     } 
/* 148 */     if (this.scene != null) {
/* 149 */       buf.setIntLE(sceneOffsetSlot, buf.writerIndex() - varBlockStart);
/* 150 */       if (this.scene.length > 4096000) throw ProtocolException.arrayTooLong("Scene", this.scene.length, 4096000);  VarInt.write(buf, this.scene.length); for (byte item : this.scene) buf.writeByte(item); 
/*     */     } else {
/* 152 */       buf.setIntLE(sceneOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 158 */     int size = 18;
/* 159 */     if (this.player != null) size += PacketIO.stringSize(this.player); 
/* 160 */     if (this.sceneName != null) size += PacketIO.stringSize(this.sceneName); 
/* 161 */     if (this.scene != null) size += VarInt.size(this.scene.length) + this.scene.length * 1;
/*     */     
/* 163 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 167 */     if (buffer.readableBytes() - offset < 18) {
/* 168 */       return ValidationResult.error("Buffer too small: expected at least 18 bytes");
/*     */     }
/*     */     
/* 171 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 174 */     if ((nullBits & 0x1) != 0) {
/* 175 */       int playerOffset = buffer.getIntLE(offset + 6);
/* 176 */       if (playerOffset < 0) {
/* 177 */         return ValidationResult.error("Invalid offset for Player");
/*     */       }
/* 179 */       int pos = offset + 18 + playerOffset;
/* 180 */       if (pos >= buffer.writerIndex()) {
/* 181 */         return ValidationResult.error("Offset out of bounds for Player");
/*     */       }
/* 183 */       int playerLen = VarInt.peek(buffer, pos);
/* 184 */       if (playerLen < 0) {
/* 185 */         return ValidationResult.error("Invalid string length for Player");
/*     */       }
/* 187 */       if (playerLen > 4096000) {
/* 188 */         return ValidationResult.error("Player exceeds max length 4096000");
/*     */       }
/* 190 */       pos += VarInt.length(buffer, pos);
/* 191 */       pos += playerLen;
/* 192 */       if (pos > buffer.writerIndex()) {
/* 193 */         return ValidationResult.error("Buffer overflow reading Player");
/*     */       }
/*     */     } 
/*     */     
/* 197 */     if ((nullBits & 0x2) != 0) {
/* 198 */       int sceneNameOffset = buffer.getIntLE(offset + 10);
/* 199 */       if (sceneNameOffset < 0) {
/* 200 */         return ValidationResult.error("Invalid offset for SceneName");
/*     */       }
/* 202 */       int pos = offset + 18 + sceneNameOffset;
/* 203 */       if (pos >= buffer.writerIndex()) {
/* 204 */         return ValidationResult.error("Offset out of bounds for SceneName");
/*     */       }
/* 206 */       int sceneNameLen = VarInt.peek(buffer, pos);
/* 207 */       if (sceneNameLen < 0) {
/* 208 */         return ValidationResult.error("Invalid string length for SceneName");
/*     */       }
/* 210 */       if (sceneNameLen > 4096000) {
/* 211 */         return ValidationResult.error("SceneName exceeds max length 4096000");
/*     */       }
/* 213 */       pos += VarInt.length(buffer, pos);
/* 214 */       pos += sceneNameLen;
/* 215 */       if (pos > buffer.writerIndex()) {
/* 216 */         return ValidationResult.error("Buffer overflow reading SceneName");
/*     */       }
/*     */     } 
/*     */     
/* 220 */     if ((nullBits & 0x4) != 0) {
/* 221 */       int sceneOffset = buffer.getIntLE(offset + 14);
/* 222 */       if (sceneOffset < 0) {
/* 223 */         return ValidationResult.error("Invalid offset for Scene");
/*     */       }
/* 225 */       int pos = offset + 18 + sceneOffset;
/* 226 */       if (pos >= buffer.writerIndex()) {
/* 227 */         return ValidationResult.error("Offset out of bounds for Scene");
/*     */       }
/* 229 */       int sceneCount = VarInt.peek(buffer, pos);
/* 230 */       if (sceneCount < 0) {
/* 231 */         return ValidationResult.error("Invalid array count for Scene");
/*     */       }
/* 233 */       if (sceneCount > 4096000) {
/* 234 */         return ValidationResult.error("Scene exceeds max length 4096000");
/*     */       }
/* 236 */       pos += VarInt.length(buffer, pos);
/* 237 */       pos += sceneCount * 1;
/* 238 */       if (pos > buffer.writerIndex()) {
/* 239 */         return ValidationResult.error("Buffer overflow reading Scene");
/*     */       }
/*     */     } 
/* 242 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateMachinimaScene clone() {
/* 246 */     UpdateMachinimaScene copy = new UpdateMachinimaScene();
/* 247 */     copy.player = this.player;
/* 248 */     copy.sceneName = this.sceneName;
/* 249 */     copy.frame = this.frame;
/* 250 */     copy.updateType = this.updateType;
/* 251 */     copy.scene = (this.scene != null) ? Arrays.copyOf(this.scene, this.scene.length) : null;
/* 252 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateMachinimaScene other;
/* 258 */     if (this == obj) return true; 
/* 259 */     if (obj instanceof UpdateMachinimaScene) { other = (UpdateMachinimaScene)obj; } else { return false; }
/* 260 */      return (Objects.equals(this.player, other.player) && Objects.equals(this.sceneName, other.sceneName) && this.frame == other.frame && Objects.equals(this.updateType, other.updateType) && Arrays.equals(this.scene, other.scene));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 265 */     int result = 1;
/* 266 */     result = 31 * result + Objects.hashCode(this.player);
/* 267 */     result = 31 * result + Objects.hashCode(this.sceneName);
/* 268 */     result = 31 * result + Float.hashCode(this.frame);
/* 269 */     result = 31 * result + Objects.hashCode(this.updateType);
/* 270 */     result = 31 * result + Arrays.hashCode(this.scene);
/* 271 */     return result;
/*     */   }
/*     */   
/*     */   public UpdateMachinimaScene() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\machinima\UpdateMachinimaScene.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */