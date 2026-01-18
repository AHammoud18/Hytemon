/*     */ package com.hypixel.hytale.protocol;
/*     */ 
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
/*     */ 
/*     */ public class ProjectileConfig
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 163;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 171;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public PhysicsConfig physicsConfig;
/*     */   @Nullable
/*     */   public Model model;
/*     */   
/*     */   public ProjectileConfig(@Nullable PhysicsConfig physicsConfig, @Nullable Model model, double launchForce, @Nullable Vector3f spawnOffset, @Nullable Direction rotationOffset, @Nullable Map<InteractionType, Integer> interactions, int launchLocalSoundEventIndex, int projectileSoundEventIndex) {
/*  33 */     this.physicsConfig = physicsConfig;
/*  34 */     this.model = model;
/*  35 */     this.launchForce = launchForce;
/*  36 */     this.spawnOffset = spawnOffset;
/*  37 */     this.rotationOffset = rotationOffset;
/*  38 */     this.interactions = interactions;
/*  39 */     this.launchLocalSoundEventIndex = launchLocalSoundEventIndex;
/*  40 */     this.projectileSoundEventIndex = projectileSoundEventIndex; } public double launchForce; @Nullable
/*     */   public Vector3f spawnOffset; @Nullable
/*     */   public Direction rotationOffset; @Nullable
/*     */   public Map<InteractionType, Integer> interactions; public int launchLocalSoundEventIndex; public int projectileSoundEventIndex; public ProjectileConfig() {} public ProjectileConfig(@Nonnull ProjectileConfig other) {
/*  44 */     this.physicsConfig = other.physicsConfig;
/*  45 */     this.model = other.model;
/*  46 */     this.launchForce = other.launchForce;
/*  47 */     this.spawnOffset = other.spawnOffset;
/*  48 */     this.rotationOffset = other.rotationOffset;
/*  49 */     this.interactions = other.interactions;
/*  50 */     this.launchLocalSoundEventIndex = other.launchLocalSoundEventIndex;
/*  51 */     this.projectileSoundEventIndex = other.projectileSoundEventIndex;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ProjectileConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     ProjectileConfig obj = new ProjectileConfig();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     if ((nullBits & 0x1) != 0) obj.physicsConfig = PhysicsConfig.deserialize(buf, offset + 1); 
/*  59 */     obj.launchForce = buf.getDoubleLE(offset + 123);
/*  60 */     if ((nullBits & 0x4) != 0) obj.spawnOffset = Vector3f.deserialize(buf, offset + 131); 
/*  61 */     if ((nullBits & 0x8) != 0) obj.rotationOffset = Direction.deserialize(buf, offset + 143); 
/*  62 */     obj.launchLocalSoundEventIndex = buf.getIntLE(offset + 155);
/*  63 */     obj.projectileSoundEventIndex = buf.getIntLE(offset + 159);
/*     */     
/*  65 */     if ((nullBits & 0x2) != 0) {
/*  66 */       int varPos0 = offset + 171 + buf.getIntLE(offset + 163);
/*  67 */       obj.model = Model.deserialize(buf, varPos0);
/*     */     } 
/*  69 */     if ((nullBits & 0x10) != 0) {
/*  70 */       int varPos1 = offset + 171 + buf.getIntLE(offset + 167);
/*  71 */       int interactionsCount = VarInt.peek(buf, varPos1);
/*  72 */       if (interactionsCount < 0) throw ProtocolException.negativeLength("Interactions", interactionsCount); 
/*  73 */       if (interactionsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Interactions", interactionsCount, 4096000); 
/*  74 */       int varIntLen = VarInt.length(buf, varPos1);
/*  75 */       obj.interactions = new HashMap<>(interactionsCount);
/*  76 */       int dictPos = varPos1 + varIntLen;
/*  77 */       for (int i = 0; i < interactionsCount; i++) {
/*  78 */         InteractionType key = InteractionType.fromValue(buf.getByte(dictPos)); dictPos++;
/*  79 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/*  80 */         if (obj.interactions.put(key, Integer.valueOf(val)) != null) {
/*  81 */           throw ProtocolException.duplicateKey("interactions", key);
/*     */         }
/*     */       } 
/*     */     } 
/*  85 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  89 */     byte nullBits = buf.getByte(offset);
/*  90 */     int maxEnd = 171;
/*  91 */     if ((nullBits & 0x2) != 0) {
/*  92 */       int fieldOffset0 = buf.getIntLE(offset + 163);
/*  93 */       int pos0 = offset + 171 + fieldOffset0;
/*  94 */       pos0 += Model.computeBytesConsumed(buf, pos0);
/*  95 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  97 */     if ((nullBits & 0x10) != 0) {
/*  98 */       int fieldOffset1 = buf.getIntLE(offset + 167);
/*  99 */       int pos1 = offset + 171 + fieldOffset1;
/* 100 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 101 */       for (int i = 0; i < dictLen; ) { pos1++; pos1 += 4; i++; }
/* 102 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 104 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 109 */     int startPos = buf.writerIndex();
/* 110 */     byte nullBits = 0;
/* 111 */     if (this.physicsConfig != null) nullBits = (byte)(nullBits | 0x1); 
/* 112 */     if (this.model != null) nullBits = (byte)(nullBits | 0x2); 
/* 113 */     if (this.spawnOffset != null) nullBits = (byte)(nullBits | 0x4); 
/* 114 */     if (this.rotationOffset != null) nullBits = (byte)(nullBits | 0x8); 
/* 115 */     if (this.interactions != null) nullBits = (byte)(nullBits | 0x10); 
/* 116 */     buf.writeByte(nullBits);
/*     */     
/* 118 */     if (this.physicsConfig != null) { this.physicsConfig.serialize(buf); } else { buf.writeZero(122); }
/* 119 */      buf.writeDoubleLE(this.launchForce);
/* 120 */     if (this.spawnOffset != null) { this.spawnOffset.serialize(buf); } else { buf.writeZero(12); }
/* 121 */      if (this.rotationOffset != null) { this.rotationOffset.serialize(buf); } else { buf.writeZero(12); }
/* 122 */      buf.writeIntLE(this.launchLocalSoundEventIndex);
/* 123 */     buf.writeIntLE(this.projectileSoundEventIndex);
/*     */     
/* 125 */     int modelOffsetSlot = buf.writerIndex();
/* 126 */     buf.writeIntLE(0);
/* 127 */     int interactionsOffsetSlot = buf.writerIndex();
/* 128 */     buf.writeIntLE(0);
/*     */     
/* 130 */     int varBlockStart = buf.writerIndex();
/* 131 */     if (this.model != null) {
/* 132 */       buf.setIntLE(modelOffsetSlot, buf.writerIndex() - varBlockStart);
/* 133 */       this.model.serialize(buf);
/*     */     } else {
/* 135 */       buf.setIntLE(modelOffsetSlot, -1);
/*     */     } 
/* 137 */     if (this.interactions != null)
/* 138 */     { buf.setIntLE(interactionsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 139 */       if (this.interactions.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Interactions", this.interactions.size(), 4096000);  VarInt.write(buf, this.interactions.size()); for (Map.Entry<InteractionType, Integer> e : this.interactions.entrySet()) { buf.writeByte(((InteractionType)e.getKey()).getValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/* 141 */     else { buf.setIntLE(interactionsOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 147 */     int size = 171;
/* 148 */     if (this.model != null) size += this.model.computeSize(); 
/* 149 */     if (this.interactions != null) size += VarInt.size(this.interactions.size()) + this.interactions.size() * 5;
/*     */     
/* 151 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 155 */     if (buffer.readableBytes() - offset < 171) {
/* 156 */       return ValidationResult.error("Buffer too small: expected at least 171 bytes");
/*     */     }
/*     */     
/* 159 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 162 */     if ((nullBits & 0x2) != 0) {
/* 163 */       int modelOffset = buffer.getIntLE(offset + 163);
/* 164 */       if (modelOffset < 0) {
/* 165 */         return ValidationResult.error("Invalid offset for Model");
/*     */       }
/* 167 */       int pos = offset + 171 + modelOffset;
/* 168 */       if (pos >= buffer.writerIndex()) {
/* 169 */         return ValidationResult.error("Offset out of bounds for Model");
/*     */       }
/* 171 */       ValidationResult modelResult = Model.validateStructure(buffer, pos);
/* 172 */       if (!modelResult.isValid()) {
/* 173 */         return ValidationResult.error("Invalid Model: " + modelResult.error());
/*     */       }
/* 175 */       pos += Model.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 178 */     if ((nullBits & 0x10) != 0) {
/* 179 */       int interactionsOffset = buffer.getIntLE(offset + 167);
/* 180 */       if (interactionsOffset < 0) {
/* 181 */         return ValidationResult.error("Invalid offset for Interactions");
/*     */       }
/* 183 */       int pos = offset + 171 + interactionsOffset;
/* 184 */       if (pos >= buffer.writerIndex()) {
/* 185 */         return ValidationResult.error("Offset out of bounds for Interactions");
/*     */       }
/* 187 */       int interactionsCount = VarInt.peek(buffer, pos);
/* 188 */       if (interactionsCount < 0) {
/* 189 */         return ValidationResult.error("Invalid dictionary count for Interactions");
/*     */       }
/* 191 */       if (interactionsCount > 4096000) {
/* 192 */         return ValidationResult.error("Interactions exceeds max length 4096000");
/*     */       }
/* 194 */       pos += VarInt.length(buffer, pos);
/* 195 */       for (int i = 0; i < interactionsCount; i++) {
/* 196 */         pos++;
/*     */         
/* 198 */         pos += 4;
/* 199 */         if (pos > buffer.writerIndex()) {
/* 200 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 204 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ProjectileConfig clone() {
/* 208 */     ProjectileConfig copy = new ProjectileConfig();
/* 209 */     copy.physicsConfig = (this.physicsConfig != null) ? this.physicsConfig.clone() : null;
/* 210 */     copy.model = (this.model != null) ? this.model.clone() : null;
/* 211 */     copy.launchForce = this.launchForce;
/* 212 */     copy.spawnOffset = (this.spawnOffset != null) ? this.spawnOffset.clone() : null;
/* 213 */     copy.rotationOffset = (this.rotationOffset != null) ? this.rotationOffset.clone() : null;
/* 214 */     copy.interactions = (this.interactions != null) ? new HashMap<>(this.interactions) : null;
/* 215 */     copy.launchLocalSoundEventIndex = this.launchLocalSoundEventIndex;
/* 216 */     copy.projectileSoundEventIndex = this.projectileSoundEventIndex;
/* 217 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ProjectileConfig other;
/* 223 */     if (this == obj) return true; 
/* 224 */     if (obj instanceof ProjectileConfig) { other = (ProjectileConfig)obj; } else { return false; }
/* 225 */      return (Objects.equals(this.physicsConfig, other.physicsConfig) && Objects.equals(this.model, other.model) && this.launchForce == other.launchForce && Objects.equals(this.spawnOffset, other.spawnOffset) && Objects.equals(this.rotationOffset, other.rotationOffset) && Objects.equals(this.interactions, other.interactions) && this.launchLocalSoundEventIndex == other.launchLocalSoundEventIndex && this.projectileSoundEventIndex == other.projectileSoundEventIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 230 */     return Objects.hash(new Object[] { this.physicsConfig, this.model, Double.valueOf(this.launchForce), this.spawnOffset, this.rotationOffset, this.interactions, Integer.valueOf(this.launchLocalSoundEventIndex), Integer.valueOf(this.projectileSoundEventIndex) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ProjectileConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */