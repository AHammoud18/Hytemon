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
/*     */ public class InteractionConfiguration
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 4;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 12;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public boolean displayOutlines = true;
/*     */   public boolean debugOutlines;
/*     */   @Nullable
/*     */   public Map<GameMode, Float> useDistance;
/*     */   public boolean allEntities;
/*     */   @Nullable
/*     */   public Map<InteractionType, InteractionPriority> priorities;
/*     */   
/*     */   public InteractionConfiguration(boolean displayOutlines, boolean debugOutlines, @Nullable Map<GameMode, Float> useDistance, boolean allEntities, @Nullable Map<InteractionType, InteractionPriority> priorities) {
/*  30 */     this.displayOutlines = displayOutlines;
/*  31 */     this.debugOutlines = debugOutlines;
/*  32 */     this.useDistance = useDistance;
/*  33 */     this.allEntities = allEntities;
/*  34 */     this.priorities = priorities;
/*     */   }
/*     */   
/*     */   public InteractionConfiguration(@Nonnull InteractionConfiguration other) {
/*  38 */     this.displayOutlines = other.displayOutlines;
/*  39 */     this.debugOutlines = other.debugOutlines;
/*  40 */     this.useDistance = other.useDistance;
/*  41 */     this.allEntities = other.allEntities;
/*  42 */     this.priorities = other.priorities;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static InteractionConfiguration deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     InteractionConfiguration obj = new InteractionConfiguration();
/*  48 */     byte nullBits = buf.getByte(offset);
/*  49 */     obj.displayOutlines = (buf.getByte(offset + 1) != 0);
/*  50 */     obj.debugOutlines = (buf.getByte(offset + 2) != 0);
/*  51 */     obj.allEntities = (buf.getByte(offset + 3) != 0);
/*     */     
/*  53 */     if ((nullBits & 0x1) != 0) {
/*  54 */       int varPos0 = offset + 12 + buf.getIntLE(offset + 4);
/*  55 */       int useDistanceCount = VarInt.peek(buf, varPos0);
/*  56 */       if (useDistanceCount < 0) throw ProtocolException.negativeLength("UseDistance", useDistanceCount); 
/*  57 */       if (useDistanceCount > 4096000) throw ProtocolException.dictionaryTooLarge("UseDistance", useDistanceCount, 4096000); 
/*  58 */       int varIntLen = VarInt.length(buf, varPos0);
/*  59 */       obj.useDistance = new HashMap<>(useDistanceCount);
/*  60 */       int dictPos = varPos0 + varIntLen;
/*  61 */       for (int i = 0; i < useDistanceCount; i++) {
/*  62 */         GameMode key = GameMode.fromValue(buf.getByte(dictPos)); dictPos++;
/*  63 */         float val = buf.getFloatLE(dictPos); dictPos += 4;
/*  64 */         if (obj.useDistance.put(key, Float.valueOf(val)) != null)
/*  65 */           throw ProtocolException.duplicateKey("useDistance", key); 
/*     */       } 
/*     */     } 
/*  68 */     if ((nullBits & 0x2) != 0) {
/*  69 */       int varPos1 = offset + 12 + buf.getIntLE(offset + 8);
/*  70 */       int prioritiesCount = VarInt.peek(buf, varPos1);
/*  71 */       if (prioritiesCount < 0) throw ProtocolException.negativeLength("Priorities", prioritiesCount); 
/*  72 */       if (prioritiesCount > 4096000) throw ProtocolException.dictionaryTooLarge("Priorities", prioritiesCount, 4096000); 
/*  73 */       int varIntLen = VarInt.length(buf, varPos1);
/*  74 */       obj.priorities = new HashMap<>(prioritiesCount);
/*  75 */       int dictPos = varPos1 + varIntLen;
/*  76 */       for (int i = 0; i < prioritiesCount; i++) {
/*  77 */         InteractionType key = InteractionType.fromValue(buf.getByte(dictPos)); dictPos++;
/*  78 */         InteractionPriority val = InteractionPriority.deserialize(buf, dictPos);
/*  79 */         dictPos += InteractionPriority.computeBytesConsumed(buf, dictPos);
/*  80 */         if (obj.priorities.put(key, val) != null) {
/*  81 */           throw ProtocolException.duplicateKey("priorities", key);
/*     */         }
/*     */       } 
/*     */     } 
/*  85 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  89 */     byte nullBits = buf.getByte(offset);
/*  90 */     int maxEnd = 12;
/*  91 */     if ((nullBits & 0x1) != 0) {
/*  92 */       int fieldOffset0 = buf.getIntLE(offset + 4);
/*  93 */       int pos0 = offset + 12 + fieldOffset0;
/*  94 */       int dictLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/*  95 */       for (int i = 0; i < dictLen; ) { pos0++; pos0 += 4; i++; }
/*  96 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  98 */     if ((nullBits & 0x2) != 0) {
/*  99 */       int fieldOffset1 = buf.getIntLE(offset + 8);
/* 100 */       int pos1 = offset + 12 + fieldOffset1;
/* 101 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 102 */       for (int i = 0; i < dictLen; ) { pos1 = ++pos1 + InteractionPriority.computeBytesConsumed(buf, pos1); i++; }
/* 103 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 105 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 110 */     int startPos = buf.writerIndex();
/* 111 */     byte nullBits = 0;
/* 112 */     if (this.useDistance != null) nullBits = (byte)(nullBits | 0x1); 
/* 113 */     if (this.priorities != null) nullBits = (byte)(nullBits | 0x2); 
/* 114 */     buf.writeByte(nullBits);
/*     */     
/* 116 */     buf.writeByte(this.displayOutlines ? 1 : 0);
/* 117 */     buf.writeByte(this.debugOutlines ? 1 : 0);
/* 118 */     buf.writeByte(this.allEntities ? 1 : 0);
/*     */     
/* 120 */     int useDistanceOffsetSlot = buf.writerIndex();
/* 121 */     buf.writeIntLE(0);
/* 122 */     int prioritiesOffsetSlot = buf.writerIndex();
/* 123 */     buf.writeIntLE(0);
/*     */     
/* 125 */     int varBlockStart = buf.writerIndex();
/* 126 */     if (this.useDistance != null)
/* 127 */     { buf.setIntLE(useDistanceOffsetSlot, buf.writerIndex() - varBlockStart);
/* 128 */       if (this.useDistance.size() > 4096000) throw ProtocolException.dictionaryTooLarge("UseDistance", this.useDistance.size(), 4096000);  VarInt.write(buf, this.useDistance.size()); for (Map.Entry<GameMode, Float> e : this.useDistance.entrySet()) { buf.writeByte(((GameMode)e.getKey()).getValue()); buf.writeFloatLE(((Float)e.getValue()).floatValue()); }
/*     */        }
/* 130 */     else { buf.setIntLE(useDistanceOffsetSlot, -1); }
/*     */     
/* 132 */     if (this.priorities != null)
/* 133 */     { buf.setIntLE(prioritiesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 134 */       if (this.priorities.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Priorities", this.priorities.size(), 4096000);  VarInt.write(buf, this.priorities.size()); for (Map.Entry<InteractionType, InteractionPriority> e : this.priorities.entrySet()) { buf.writeByte(((InteractionType)e.getKey()).getValue()); ((InteractionPriority)e.getValue()).serialize(buf); }
/*     */        }
/* 136 */     else { buf.setIntLE(prioritiesOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 142 */     int size = 12;
/* 143 */     if (this.useDistance != null) size += VarInt.size(this.useDistance.size()) + this.useDistance.size() * 5; 
/* 144 */     if (this.priorities != null) {
/* 145 */       int prioritiesSize = 0;
/* 146 */       for (Map.Entry<InteractionType, InteractionPriority> kvp : this.priorities.entrySet()) prioritiesSize += 1 + ((InteractionPriority)kvp.getValue()).computeSize(); 
/* 147 */       size += VarInt.size(this.priorities.size()) + prioritiesSize;
/*     */     } 
/*     */     
/* 150 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 154 */     if (buffer.readableBytes() - offset < 12) {
/* 155 */       return ValidationResult.error("Buffer too small: expected at least 12 bytes");
/*     */     }
/*     */     
/* 158 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 161 */     if ((nullBits & 0x1) != 0) {
/* 162 */       int useDistanceOffset = buffer.getIntLE(offset + 4);
/* 163 */       if (useDistanceOffset < 0) {
/* 164 */         return ValidationResult.error("Invalid offset for UseDistance");
/*     */       }
/* 166 */       int pos = offset + 12 + useDistanceOffset;
/* 167 */       if (pos >= buffer.writerIndex()) {
/* 168 */         return ValidationResult.error("Offset out of bounds for UseDistance");
/*     */       }
/* 170 */       int useDistanceCount = VarInt.peek(buffer, pos);
/* 171 */       if (useDistanceCount < 0) {
/* 172 */         return ValidationResult.error("Invalid dictionary count for UseDistance");
/*     */       }
/* 174 */       if (useDistanceCount > 4096000) {
/* 175 */         return ValidationResult.error("UseDistance exceeds max length 4096000");
/*     */       }
/* 177 */       pos += VarInt.length(buffer, pos);
/* 178 */       for (int i = 0; i < useDistanceCount; i++) {
/* 179 */         pos++;
/*     */         
/* 181 */         pos += 4;
/* 182 */         if (pos > buffer.writerIndex()) {
/* 183 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 188 */     if ((nullBits & 0x2) != 0) {
/* 189 */       int prioritiesOffset = buffer.getIntLE(offset + 8);
/* 190 */       if (prioritiesOffset < 0) {
/* 191 */         return ValidationResult.error("Invalid offset for Priorities");
/*     */       }
/* 193 */       int pos = offset + 12 + prioritiesOffset;
/* 194 */       if (pos >= buffer.writerIndex()) {
/* 195 */         return ValidationResult.error("Offset out of bounds for Priorities");
/*     */       }
/* 197 */       int prioritiesCount = VarInt.peek(buffer, pos);
/* 198 */       if (prioritiesCount < 0) {
/* 199 */         return ValidationResult.error("Invalid dictionary count for Priorities");
/*     */       }
/* 201 */       if (prioritiesCount > 4096000) {
/* 202 */         return ValidationResult.error("Priorities exceeds max length 4096000");
/*     */       }
/* 204 */       pos += VarInt.length(buffer, pos);
/* 205 */       for (int i = 0; i < prioritiesCount; i++) {
/* 206 */         pos++;
/*     */         
/* 208 */         pos += InteractionPriority.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 212 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public InteractionConfiguration clone() {
/* 216 */     InteractionConfiguration copy = new InteractionConfiguration();
/* 217 */     copy.displayOutlines = this.displayOutlines;
/* 218 */     copy.debugOutlines = this.debugOutlines;
/* 219 */     copy.useDistance = (this.useDistance != null) ? new HashMap<>(this.useDistance) : null;
/* 220 */     copy.allEntities = this.allEntities;
/* 221 */     if (this.priorities != null) {
/* 222 */       Map<InteractionType, InteractionPriority> m = new HashMap<>();
/* 223 */       for (Map.Entry<InteractionType, InteractionPriority> e : this.priorities.entrySet()) m.put(e.getKey(), ((InteractionPriority)e.getValue()).clone()); 
/* 224 */       copy.priorities = m;
/*     */     } 
/* 226 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     InteractionConfiguration other;
/* 232 */     if (this == obj) return true; 
/* 233 */     if (obj instanceof InteractionConfiguration) { other = (InteractionConfiguration)obj; } else { return false; }
/* 234 */      return (this.displayOutlines == other.displayOutlines && this.debugOutlines == other.debugOutlines && Objects.equals(this.useDistance, other.useDistance) && this.allEntities == other.allEntities && Objects.equals(this.priorities, other.priorities));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 239 */     return Objects.hash(new Object[] { Boolean.valueOf(this.displayOutlines), Boolean.valueOf(this.debugOutlines), this.useDistance, Boolean.valueOf(this.allEntities), this.priorities });
/*     */   }
/*     */   
/*     */   public InteractionConfiguration() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\InteractionConfiguration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */