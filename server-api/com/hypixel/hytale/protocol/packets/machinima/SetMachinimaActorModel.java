/*     */ package com.hypixel.hytale.protocol.packets.machinima;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Model;
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
/*     */ public class SetMachinimaActorModel
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 261;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   
/*     */   public int getId() {
/*  25 */     return 261;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public Model model;
/*     */   
/*     */   public SetMachinimaActorModel(@Nullable Model model, @Nullable String sceneName, @Nullable String actorName) {
/*  36 */     this.model = model;
/*  37 */     this.sceneName = sceneName;
/*  38 */     this.actorName = actorName;
/*     */   } @Nullable
/*     */   public String sceneName; @Nullable
/*     */   public String actorName; public SetMachinimaActorModel() {} public SetMachinimaActorModel(@Nonnull SetMachinimaActorModel other) {
/*  42 */     this.model = other.model;
/*  43 */     this.sceneName = other.sceneName;
/*  44 */     this.actorName = other.actorName;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SetMachinimaActorModel deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     SetMachinimaActorModel obj = new SetMachinimaActorModel();
/*  50 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  52 */     if ((nullBits & 0x1) != 0) {
/*  53 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 1);
/*  54 */       obj.model = Model.deserialize(buf, varPos0);
/*     */     } 
/*  56 */     if ((nullBits & 0x2) != 0) {
/*  57 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 5);
/*  58 */       int sceneNameLen = VarInt.peek(buf, varPos1);
/*  59 */       if (sceneNameLen < 0) throw ProtocolException.negativeLength("SceneName", sceneNameLen); 
/*  60 */       if (sceneNameLen > 4096000) throw ProtocolException.stringTooLong("SceneName", sceneNameLen, 4096000); 
/*  61 */       obj.sceneName = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  63 */     if ((nullBits & 0x4) != 0) {
/*  64 */       int varPos2 = offset + 13 + buf.getIntLE(offset + 9);
/*  65 */       int actorNameLen = VarInt.peek(buf, varPos2);
/*  66 */       if (actorNameLen < 0) throw ProtocolException.negativeLength("ActorName", actorNameLen); 
/*  67 */       if (actorNameLen > 4096000) throw ProtocolException.stringTooLong("ActorName", actorNameLen, 4096000); 
/*  68 */       obj.actorName = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  71 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  75 */     byte nullBits = buf.getByte(offset);
/*  76 */     int maxEnd = 13;
/*  77 */     if ((nullBits & 0x1) != 0) {
/*  78 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  79 */       int pos0 = offset + 13 + fieldOffset0;
/*  80 */       pos0 += Model.computeBytesConsumed(buf, pos0);
/*  81 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  83 */     if ((nullBits & 0x2) != 0) {
/*  84 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  85 */       int pos1 = offset + 13 + fieldOffset1;
/*  86 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  87 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  89 */     if ((nullBits & 0x4) != 0) {
/*  90 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/*  91 */       int pos2 = offset + 13 + fieldOffset2;
/*  92 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/*  93 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/*  95 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 101 */     int startPos = buf.writerIndex();
/* 102 */     byte nullBits = 0;
/* 103 */     if (this.model != null) nullBits = (byte)(nullBits | 0x1); 
/* 104 */     if (this.sceneName != null) nullBits = (byte)(nullBits | 0x2); 
/* 105 */     if (this.actorName != null) nullBits = (byte)(nullBits | 0x4); 
/* 106 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 109 */     int modelOffsetSlot = buf.writerIndex();
/* 110 */     buf.writeIntLE(0);
/* 111 */     int sceneNameOffsetSlot = buf.writerIndex();
/* 112 */     buf.writeIntLE(0);
/* 113 */     int actorNameOffsetSlot = buf.writerIndex();
/* 114 */     buf.writeIntLE(0);
/*     */     
/* 116 */     int varBlockStart = buf.writerIndex();
/* 117 */     if (this.model != null) {
/* 118 */       buf.setIntLE(modelOffsetSlot, buf.writerIndex() - varBlockStart);
/* 119 */       this.model.serialize(buf);
/*     */     } else {
/* 121 */       buf.setIntLE(modelOffsetSlot, -1);
/*     */     } 
/* 123 */     if (this.sceneName != null) {
/* 124 */       buf.setIntLE(sceneNameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 125 */       PacketIO.writeVarString(buf, this.sceneName, 4096000);
/*     */     } else {
/* 127 */       buf.setIntLE(sceneNameOffsetSlot, -1);
/*     */     } 
/* 129 */     if (this.actorName != null) {
/* 130 */       buf.setIntLE(actorNameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 131 */       PacketIO.writeVarString(buf, this.actorName, 4096000);
/*     */     } else {
/* 133 */       buf.setIntLE(actorNameOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 139 */     int size = 13;
/* 140 */     if (this.model != null) size += this.model.computeSize(); 
/* 141 */     if (this.sceneName != null) size += PacketIO.stringSize(this.sceneName); 
/* 142 */     if (this.actorName != null) size += PacketIO.stringSize(this.actorName);
/*     */     
/* 144 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 148 */     if (buffer.readableBytes() - offset < 13) {
/* 149 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 152 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 155 */     if ((nullBits & 0x1) != 0) {
/* 156 */       int modelOffset = buffer.getIntLE(offset + 1);
/* 157 */       if (modelOffset < 0) {
/* 158 */         return ValidationResult.error("Invalid offset for Model");
/*     */       }
/* 160 */       int pos = offset + 13 + modelOffset;
/* 161 */       if (pos >= buffer.writerIndex()) {
/* 162 */         return ValidationResult.error("Offset out of bounds for Model");
/*     */       }
/* 164 */       ValidationResult modelResult = Model.validateStructure(buffer, pos);
/* 165 */       if (!modelResult.isValid()) {
/* 166 */         return ValidationResult.error("Invalid Model: " + modelResult.error());
/*     */       }
/* 168 */       pos += Model.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 171 */     if ((nullBits & 0x2) != 0) {
/* 172 */       int sceneNameOffset = buffer.getIntLE(offset + 5);
/* 173 */       if (sceneNameOffset < 0) {
/* 174 */         return ValidationResult.error("Invalid offset for SceneName");
/*     */       }
/* 176 */       int pos = offset + 13 + sceneNameOffset;
/* 177 */       if (pos >= buffer.writerIndex()) {
/* 178 */         return ValidationResult.error("Offset out of bounds for SceneName");
/*     */       }
/* 180 */       int sceneNameLen = VarInt.peek(buffer, pos);
/* 181 */       if (sceneNameLen < 0) {
/* 182 */         return ValidationResult.error("Invalid string length for SceneName");
/*     */       }
/* 184 */       if (sceneNameLen > 4096000) {
/* 185 */         return ValidationResult.error("SceneName exceeds max length 4096000");
/*     */       }
/* 187 */       pos += VarInt.length(buffer, pos);
/* 188 */       pos += sceneNameLen;
/* 189 */       if (pos > buffer.writerIndex()) {
/* 190 */         return ValidationResult.error("Buffer overflow reading SceneName");
/*     */       }
/*     */     } 
/*     */     
/* 194 */     if ((nullBits & 0x4) != 0) {
/* 195 */       int actorNameOffset = buffer.getIntLE(offset + 9);
/* 196 */       if (actorNameOffset < 0) {
/* 197 */         return ValidationResult.error("Invalid offset for ActorName");
/*     */       }
/* 199 */       int pos = offset + 13 + actorNameOffset;
/* 200 */       if (pos >= buffer.writerIndex()) {
/* 201 */         return ValidationResult.error("Offset out of bounds for ActorName");
/*     */       }
/* 203 */       int actorNameLen = VarInt.peek(buffer, pos);
/* 204 */       if (actorNameLen < 0) {
/* 205 */         return ValidationResult.error("Invalid string length for ActorName");
/*     */       }
/* 207 */       if (actorNameLen > 4096000) {
/* 208 */         return ValidationResult.error("ActorName exceeds max length 4096000");
/*     */       }
/* 210 */       pos += VarInt.length(buffer, pos);
/* 211 */       pos += actorNameLen;
/* 212 */       if (pos > buffer.writerIndex()) {
/* 213 */         return ValidationResult.error("Buffer overflow reading ActorName");
/*     */       }
/*     */     } 
/* 216 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SetMachinimaActorModel clone() {
/* 220 */     SetMachinimaActorModel copy = new SetMachinimaActorModel();
/* 221 */     copy.model = (this.model != null) ? this.model.clone() : null;
/* 222 */     copy.sceneName = this.sceneName;
/* 223 */     copy.actorName = this.actorName;
/* 224 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SetMachinimaActorModel other;
/* 230 */     if (this == obj) return true; 
/* 231 */     if (obj instanceof SetMachinimaActorModel) { other = (SetMachinimaActorModel)obj; } else { return false; }
/* 232 */      return (Objects.equals(this.model, other.model) && Objects.equals(this.sceneName, other.sceneName) && Objects.equals(this.actorName, other.actorName));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 237 */     return Objects.hash(new Object[] { this.model, this.sceneName, this.actorName });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\machinima\SetMachinimaActorModel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */