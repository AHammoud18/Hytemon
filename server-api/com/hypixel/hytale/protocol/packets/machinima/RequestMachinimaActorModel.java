/*     */ package com.hypixel.hytale.protocol.packets.machinima;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
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
/*     */ public class RequestMachinimaActorModel
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 260;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   
/*     */   public int getId() {
/*  25 */     return 260;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 13; public static final int MAX_SIZE = 49152028; @Nullable
/*     */   public String modelId; @Nullable
/*     */   public String sceneName;
/*     */   @Nullable
/*     */   public String actorName;
/*     */   
/*     */   public RequestMachinimaActorModel() {}
/*     */   
/*     */   public RequestMachinimaActorModel(@Nullable String modelId, @Nullable String sceneName, @Nullable String actorName) {
/*  36 */     this.modelId = modelId;
/*  37 */     this.sceneName = sceneName;
/*  38 */     this.actorName = actorName;
/*     */   }
/*     */   
/*     */   public RequestMachinimaActorModel(@Nonnull RequestMachinimaActorModel other) {
/*  42 */     this.modelId = other.modelId;
/*  43 */     this.sceneName = other.sceneName;
/*  44 */     this.actorName = other.actorName;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static RequestMachinimaActorModel deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     RequestMachinimaActorModel obj = new RequestMachinimaActorModel();
/*  50 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  52 */     if ((nullBits & 0x1) != 0) {
/*  53 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 1);
/*  54 */       int modelIdLen = VarInt.peek(buf, varPos0);
/*  55 */       if (modelIdLen < 0) throw ProtocolException.negativeLength("ModelId", modelIdLen); 
/*  56 */       if (modelIdLen > 4096000) throw ProtocolException.stringTooLong("ModelId", modelIdLen, 4096000); 
/*  57 */       obj.modelId = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  59 */     if ((nullBits & 0x2) != 0) {
/*  60 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 5);
/*  61 */       int sceneNameLen = VarInt.peek(buf, varPos1);
/*  62 */       if (sceneNameLen < 0) throw ProtocolException.negativeLength("SceneName", sceneNameLen); 
/*  63 */       if (sceneNameLen > 4096000) throw ProtocolException.stringTooLong("SceneName", sceneNameLen, 4096000); 
/*  64 */       obj.sceneName = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  66 */     if ((nullBits & 0x4) != 0) {
/*  67 */       int varPos2 = offset + 13 + buf.getIntLE(offset + 9);
/*  68 */       int actorNameLen = VarInt.peek(buf, varPos2);
/*  69 */       if (actorNameLen < 0) throw ProtocolException.negativeLength("ActorName", actorNameLen); 
/*  70 */       if (actorNameLen > 4096000) throw ProtocolException.stringTooLong("ActorName", actorNameLen, 4096000); 
/*  71 */       obj.actorName = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  74 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  78 */     byte nullBits = buf.getByte(offset);
/*  79 */     int maxEnd = 13;
/*  80 */     if ((nullBits & 0x1) != 0) {
/*  81 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  82 */       int pos0 = offset + 13 + fieldOffset0;
/*  83 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  84 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  86 */     if ((nullBits & 0x2) != 0) {
/*  87 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  88 */       int pos1 = offset + 13 + fieldOffset1;
/*  89 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  90 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  92 */     if ((nullBits & 0x4) != 0) {
/*  93 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/*  94 */       int pos2 = offset + 13 + fieldOffset2;
/*  95 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/*  96 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/*  98 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 104 */     int startPos = buf.writerIndex();
/* 105 */     byte nullBits = 0;
/* 106 */     if (this.modelId != null) nullBits = (byte)(nullBits | 0x1); 
/* 107 */     if (this.sceneName != null) nullBits = (byte)(nullBits | 0x2); 
/* 108 */     if (this.actorName != null) nullBits = (byte)(nullBits | 0x4); 
/* 109 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 112 */     int modelIdOffsetSlot = buf.writerIndex();
/* 113 */     buf.writeIntLE(0);
/* 114 */     int sceneNameOffsetSlot = buf.writerIndex();
/* 115 */     buf.writeIntLE(0);
/* 116 */     int actorNameOffsetSlot = buf.writerIndex();
/* 117 */     buf.writeIntLE(0);
/*     */     
/* 119 */     int varBlockStart = buf.writerIndex();
/* 120 */     if (this.modelId != null) {
/* 121 */       buf.setIntLE(modelIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 122 */       PacketIO.writeVarString(buf, this.modelId, 4096000);
/*     */     } else {
/* 124 */       buf.setIntLE(modelIdOffsetSlot, -1);
/*     */     } 
/* 126 */     if (this.sceneName != null) {
/* 127 */       buf.setIntLE(sceneNameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 128 */       PacketIO.writeVarString(buf, this.sceneName, 4096000);
/*     */     } else {
/* 130 */       buf.setIntLE(sceneNameOffsetSlot, -1);
/*     */     } 
/* 132 */     if (this.actorName != null) {
/* 133 */       buf.setIntLE(actorNameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 134 */       PacketIO.writeVarString(buf, this.actorName, 4096000);
/*     */     } else {
/* 136 */       buf.setIntLE(actorNameOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 142 */     int size = 13;
/* 143 */     if (this.modelId != null) size += PacketIO.stringSize(this.modelId); 
/* 144 */     if (this.sceneName != null) size += PacketIO.stringSize(this.sceneName); 
/* 145 */     if (this.actorName != null) size += PacketIO.stringSize(this.actorName);
/*     */     
/* 147 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 151 */     if (buffer.readableBytes() - offset < 13) {
/* 152 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 155 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 158 */     if ((nullBits & 0x1) != 0) {
/* 159 */       int modelIdOffset = buffer.getIntLE(offset + 1);
/* 160 */       if (modelIdOffset < 0) {
/* 161 */         return ValidationResult.error("Invalid offset for ModelId");
/*     */       }
/* 163 */       int pos = offset + 13 + modelIdOffset;
/* 164 */       if (pos >= buffer.writerIndex()) {
/* 165 */         return ValidationResult.error("Offset out of bounds for ModelId");
/*     */       }
/* 167 */       int modelIdLen = VarInt.peek(buffer, pos);
/* 168 */       if (modelIdLen < 0) {
/* 169 */         return ValidationResult.error("Invalid string length for ModelId");
/*     */       }
/* 171 */       if (modelIdLen > 4096000) {
/* 172 */         return ValidationResult.error("ModelId exceeds max length 4096000");
/*     */       }
/* 174 */       pos += VarInt.length(buffer, pos);
/* 175 */       pos += modelIdLen;
/* 176 */       if (pos > buffer.writerIndex()) {
/* 177 */         return ValidationResult.error("Buffer overflow reading ModelId");
/*     */       }
/*     */     } 
/*     */     
/* 181 */     if ((nullBits & 0x2) != 0) {
/* 182 */       int sceneNameOffset = buffer.getIntLE(offset + 5);
/* 183 */       if (sceneNameOffset < 0) {
/* 184 */         return ValidationResult.error("Invalid offset for SceneName");
/*     */       }
/* 186 */       int pos = offset + 13 + sceneNameOffset;
/* 187 */       if (pos >= buffer.writerIndex()) {
/* 188 */         return ValidationResult.error("Offset out of bounds for SceneName");
/*     */       }
/* 190 */       int sceneNameLen = VarInt.peek(buffer, pos);
/* 191 */       if (sceneNameLen < 0) {
/* 192 */         return ValidationResult.error("Invalid string length for SceneName");
/*     */       }
/* 194 */       if (sceneNameLen > 4096000) {
/* 195 */         return ValidationResult.error("SceneName exceeds max length 4096000");
/*     */       }
/* 197 */       pos += VarInt.length(buffer, pos);
/* 198 */       pos += sceneNameLen;
/* 199 */       if (pos > buffer.writerIndex()) {
/* 200 */         return ValidationResult.error("Buffer overflow reading SceneName");
/*     */       }
/*     */     } 
/*     */     
/* 204 */     if ((nullBits & 0x4) != 0) {
/* 205 */       int actorNameOffset = buffer.getIntLE(offset + 9);
/* 206 */       if (actorNameOffset < 0) {
/* 207 */         return ValidationResult.error("Invalid offset for ActorName");
/*     */       }
/* 209 */       int pos = offset + 13 + actorNameOffset;
/* 210 */       if (pos >= buffer.writerIndex()) {
/* 211 */         return ValidationResult.error("Offset out of bounds for ActorName");
/*     */       }
/* 213 */       int actorNameLen = VarInt.peek(buffer, pos);
/* 214 */       if (actorNameLen < 0) {
/* 215 */         return ValidationResult.error("Invalid string length for ActorName");
/*     */       }
/* 217 */       if (actorNameLen > 4096000) {
/* 218 */         return ValidationResult.error("ActorName exceeds max length 4096000");
/*     */       }
/* 220 */       pos += VarInt.length(buffer, pos);
/* 221 */       pos += actorNameLen;
/* 222 */       if (pos > buffer.writerIndex()) {
/* 223 */         return ValidationResult.error("Buffer overflow reading ActorName");
/*     */       }
/*     */     } 
/* 226 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public RequestMachinimaActorModel clone() {
/* 230 */     RequestMachinimaActorModel copy = new RequestMachinimaActorModel();
/* 231 */     copy.modelId = this.modelId;
/* 232 */     copy.sceneName = this.sceneName;
/* 233 */     copy.actorName = this.actorName;
/* 234 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RequestMachinimaActorModel other;
/* 240 */     if (this == obj) return true; 
/* 241 */     if (obj instanceof RequestMachinimaActorModel) { other = (RequestMachinimaActorModel)obj; } else { return false; }
/* 242 */      return (Objects.equals(this.modelId, other.modelId) && Objects.equals(this.sceneName, other.sceneName) && Objects.equals(this.actorName, other.actorName));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 247 */     return Objects.hash(new Object[] { this.modelId, this.sceneName, this.actorName });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\machinima\RequestMachinimaActorModel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */