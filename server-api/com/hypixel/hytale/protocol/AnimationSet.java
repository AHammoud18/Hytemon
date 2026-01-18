/*     */ package com.hypixel.hytale.protocol;
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
/*     */ public class AnimationSet {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 17;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public Animation[] animations;
/*     */   @Nullable
/*     */   public Rangef nextAnimationDelay;
/*     */   
/*     */   public AnimationSet() {}
/*     */   
/*     */   public AnimationSet(@Nullable String id, @Nullable Animation[] animations, @Nullable Rangef nextAnimationDelay) {
/*  28 */     this.id = id;
/*  29 */     this.animations = animations;
/*  30 */     this.nextAnimationDelay = nextAnimationDelay;
/*     */   }
/*     */   
/*     */   public AnimationSet(@Nonnull AnimationSet other) {
/*  34 */     this.id = other.id;
/*  35 */     this.animations = other.animations;
/*  36 */     this.nextAnimationDelay = other.nextAnimationDelay;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AnimationSet deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     AnimationSet obj = new AnimationSet();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     if ((nullBits & 0x4) != 0) obj.nextAnimationDelay = Rangef.deserialize(buf, offset + 1);
/*     */     
/*  45 */     if ((nullBits & 0x1) != 0) {
/*  46 */       int varPos0 = offset + 17 + buf.getIntLE(offset + 9);
/*  47 */       int idLen = VarInt.peek(buf, varPos0);
/*  48 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  49 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  50 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  52 */     if ((nullBits & 0x2) != 0) {
/*  53 */       int varPos1 = offset + 17 + buf.getIntLE(offset + 13);
/*  54 */       int animationsCount = VarInt.peek(buf, varPos1);
/*  55 */       if (animationsCount < 0) throw ProtocolException.negativeLength("Animations", animationsCount); 
/*  56 */       if (animationsCount > 4096000) throw ProtocolException.arrayTooLong("Animations", animationsCount, 4096000); 
/*  57 */       int varIntLen = VarInt.length(buf, varPos1);
/*  58 */       if ((varPos1 + varIntLen) + animationsCount * 22L > buf.readableBytes())
/*  59 */         throw ProtocolException.bufferTooSmall("Animations", varPos1 + varIntLen + animationsCount * 22, buf.readableBytes()); 
/*  60 */       obj.animations = new Animation[animationsCount];
/*  61 */       int elemPos = varPos1 + varIntLen;
/*  62 */       for (int i = 0; i < animationsCount; i++) {
/*  63 */         obj.animations[i] = Animation.deserialize(buf, elemPos);
/*  64 */         elemPos += Animation.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     byte nullBits = buf.getByte(offset);
/*  73 */     int maxEnd = 17;
/*  74 */     if ((nullBits & 0x1) != 0) {
/*  75 */       int fieldOffset0 = buf.getIntLE(offset + 9);
/*  76 */       int pos0 = offset + 17 + fieldOffset0;
/*  77 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  78 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  80 */     if ((nullBits & 0x2) != 0) {
/*  81 */       int fieldOffset1 = buf.getIntLE(offset + 13);
/*  82 */       int pos1 = offset + 17 + fieldOffset1;
/*  83 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/*  84 */       for (int i = 0; i < arrLen; ) { pos1 += Animation.computeBytesConsumed(buf, pos1); i++; }
/*  85 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  87 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  92 */     int startPos = buf.writerIndex();
/*  93 */     byte nullBits = 0;
/*  94 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/*  95 */     if (this.animations != null) nullBits = (byte)(nullBits | 0x2); 
/*  96 */     if (this.nextAnimationDelay != null) nullBits = (byte)(nullBits | 0x4); 
/*  97 */     buf.writeByte(nullBits);
/*     */     
/*  99 */     if (this.nextAnimationDelay != null) { this.nextAnimationDelay.serialize(buf); } else { buf.writeZero(8); }
/*     */     
/* 101 */     int idOffsetSlot = buf.writerIndex();
/* 102 */     buf.writeIntLE(0);
/* 103 */     int animationsOffsetSlot = buf.writerIndex();
/* 104 */     buf.writeIntLE(0);
/*     */     
/* 106 */     int varBlockStart = buf.writerIndex();
/* 107 */     if (this.id != null) {
/* 108 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 109 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 111 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 113 */     if (this.animations != null) {
/* 114 */       buf.setIntLE(animationsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 115 */       if (this.animations.length > 4096000) throw ProtocolException.arrayTooLong("Animations", this.animations.length, 4096000);  VarInt.write(buf, this.animations.length); for (Animation item : this.animations) item.serialize(buf); 
/*     */     } else {
/* 117 */       buf.setIntLE(animationsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 123 */     int size = 17;
/* 124 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 125 */     if (this.animations != null) {
/* 126 */       int animationsSize = 0;
/* 127 */       for (Animation elem : this.animations) animationsSize += elem.computeSize(); 
/* 128 */       size += VarInt.size(this.animations.length) + animationsSize;
/*     */     } 
/*     */     
/* 131 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 135 */     if (buffer.readableBytes() - offset < 17) {
/* 136 */       return ValidationResult.error("Buffer too small: expected at least 17 bytes");
/*     */     }
/*     */     
/* 139 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 142 */     if ((nullBits & 0x1) != 0) {
/* 143 */       int idOffset = buffer.getIntLE(offset + 9);
/* 144 */       if (idOffset < 0) {
/* 145 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 147 */       int pos = offset + 17 + idOffset;
/* 148 */       if (pos >= buffer.writerIndex()) {
/* 149 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 151 */       int idLen = VarInt.peek(buffer, pos);
/* 152 */       if (idLen < 0) {
/* 153 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 155 */       if (idLen > 4096000) {
/* 156 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 158 */       pos += VarInt.length(buffer, pos);
/* 159 */       pos += idLen;
/* 160 */       if (pos > buffer.writerIndex()) {
/* 161 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 165 */     if ((nullBits & 0x2) != 0) {
/* 166 */       int animationsOffset = buffer.getIntLE(offset + 13);
/* 167 */       if (animationsOffset < 0) {
/* 168 */         return ValidationResult.error("Invalid offset for Animations");
/*     */       }
/* 170 */       int pos = offset + 17 + animationsOffset;
/* 171 */       if (pos >= buffer.writerIndex()) {
/* 172 */         return ValidationResult.error("Offset out of bounds for Animations");
/*     */       }
/* 174 */       int animationsCount = VarInt.peek(buffer, pos);
/* 175 */       if (animationsCount < 0) {
/* 176 */         return ValidationResult.error("Invalid array count for Animations");
/*     */       }
/* 178 */       if (animationsCount > 4096000) {
/* 179 */         return ValidationResult.error("Animations exceeds max length 4096000");
/*     */       }
/* 181 */       pos += VarInt.length(buffer, pos);
/* 182 */       for (int i = 0; i < animationsCount; i++) {
/* 183 */         ValidationResult structResult = Animation.validateStructure(buffer, pos);
/* 184 */         if (!structResult.isValid()) {
/* 185 */           return ValidationResult.error("Invalid Animation in Animations[" + i + "]: " + structResult.error());
/*     */         }
/* 187 */         pos += Animation.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 190 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AnimationSet clone() {
/* 194 */     AnimationSet copy = new AnimationSet();
/* 195 */     copy.id = this.id;
/* 196 */     copy.animations = (this.animations != null) ? (Animation[])Arrays.<Animation>stream(this.animations).map(e -> e.clone()).toArray(x$0 -> new Animation[x$0]) : null;
/* 197 */     copy.nextAnimationDelay = (this.nextAnimationDelay != null) ? this.nextAnimationDelay.clone() : null;
/* 198 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AnimationSet other;
/* 204 */     if (this == obj) return true; 
/* 205 */     if (obj instanceof AnimationSet) { other = (AnimationSet)obj; } else { return false; }
/* 206 */      return (Objects.equals(this.id, other.id) && Arrays.equals((Object[])this.animations, (Object[])other.animations) && Objects.equals(this.nextAnimationDelay, other.nextAnimationDelay));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 211 */     int result = 1;
/* 212 */     result = 31 * result + Objects.hashCode(this.id);
/* 213 */     result = 31 * result + Arrays.hashCode((Object[])this.animations);
/* 214 */     result = 31 * result + Objects.hashCode(this.nextAnimationDelay);
/* 215 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AnimationSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */