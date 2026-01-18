/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityStatEffects
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 6;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public boolean triggerAtZero;
/*     */   public int soundEventIndex;
/*     */   @Nullable
/*     */   public ModelParticle[] particles;
/*     */   
/*     */   public EntityStatEffects() {}
/*     */   
/*     */   public EntityStatEffects(boolean triggerAtZero, int soundEventIndex, @Nullable ModelParticle[] particles) {
/*  28 */     this.triggerAtZero = triggerAtZero;
/*  29 */     this.soundEventIndex = soundEventIndex;
/*  30 */     this.particles = particles;
/*     */   }
/*     */   
/*     */   public EntityStatEffects(@Nonnull EntityStatEffects other) {
/*  34 */     this.triggerAtZero = other.triggerAtZero;
/*  35 */     this.soundEventIndex = other.soundEventIndex;
/*  36 */     this.particles = other.particles;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EntityStatEffects deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     EntityStatEffects obj = new EntityStatEffects();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.triggerAtZero = (buf.getByte(offset + 1) != 0);
/*  44 */     obj.soundEventIndex = buf.getIntLE(offset + 2);
/*     */     
/*  46 */     int pos = offset + 6;
/*  47 */     if ((nullBits & 0x1) != 0) { int particlesCount = VarInt.peek(buf, pos);
/*  48 */       if (particlesCount < 0) throw ProtocolException.negativeLength("Particles", particlesCount); 
/*  49 */       if (particlesCount > 4096000) throw ProtocolException.arrayTooLong("Particles", particlesCount, 4096000); 
/*  50 */       int particlesVarLen = VarInt.size(particlesCount);
/*  51 */       if ((pos + particlesVarLen) + particlesCount * 34L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("Particles", pos + particlesVarLen + particlesCount * 34, buf.readableBytes()); 
/*  53 */       pos += particlesVarLen;
/*  54 */       obj.particles = new ModelParticle[particlesCount];
/*  55 */       for (int i = 0; i < particlesCount; i++) {
/*  56 */         obj.particles[i] = ModelParticle.deserialize(buf, pos);
/*  57 */         pos += ModelParticle.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int pos = offset + 6;
/*  66 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  67 */       for (int i = 0; i < arrLen; ) { pos += ModelParticle.computeBytesConsumed(buf, pos); i++; }  }
/*  68 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  73 */     byte nullBits = 0;
/*  74 */     if (this.particles != null) nullBits = (byte)(nullBits | 0x1); 
/*  75 */     buf.writeByte(nullBits);
/*     */     
/*  77 */     buf.writeByte(this.triggerAtZero ? 1 : 0);
/*  78 */     buf.writeIntLE(this.soundEventIndex);
/*     */     
/*  80 */     if (this.particles != null) { if (this.particles.length > 4096000) throw ProtocolException.arrayTooLong("Particles", this.particles.length, 4096000);  VarInt.write(buf, this.particles.length); for (ModelParticle item : this.particles) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  85 */     int size = 6;
/*  86 */     if (this.particles != null) {
/*  87 */       int particlesSize = 0;
/*  88 */       for (ModelParticle elem : this.particles) particlesSize += elem.computeSize(); 
/*  89 */       size += VarInt.size(this.particles.length) + particlesSize;
/*     */     } 
/*     */     
/*  92 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  96 */     if (buffer.readableBytes() - offset < 6) {
/*  97 */       return ValidationResult.error("Buffer too small: expected at least 6 bytes");
/*     */     }
/*     */     
/* 100 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 102 */     int pos = offset + 6;
/*     */     
/* 104 */     if ((nullBits & 0x1) != 0) {
/* 105 */       int particlesCount = VarInt.peek(buffer, pos);
/* 106 */       if (particlesCount < 0) {
/* 107 */         return ValidationResult.error("Invalid array count for Particles");
/*     */       }
/* 109 */       if (particlesCount > 4096000) {
/* 110 */         return ValidationResult.error("Particles exceeds max length 4096000");
/*     */       }
/* 112 */       pos += VarInt.length(buffer, pos);
/* 113 */       for (int i = 0; i < particlesCount; i++) {
/* 114 */         ValidationResult structResult = ModelParticle.validateStructure(buffer, pos);
/* 115 */         if (!structResult.isValid()) {
/* 116 */           return ValidationResult.error("Invalid ModelParticle in Particles[" + i + "]: " + structResult.error());
/*     */         }
/* 118 */         pos += ModelParticle.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 121 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public EntityStatEffects clone() {
/* 125 */     EntityStatEffects copy = new EntityStatEffects();
/* 126 */     copy.triggerAtZero = this.triggerAtZero;
/* 127 */     copy.soundEventIndex = this.soundEventIndex;
/* 128 */     copy.particles = (this.particles != null) ? (ModelParticle[])Arrays.<ModelParticle>stream(this.particles).map(e -> e.clone()).toArray(x$0 -> new ModelParticle[x$0]) : null;
/* 129 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     EntityStatEffects other;
/* 135 */     if (this == obj) return true; 
/* 136 */     if (obj instanceof EntityStatEffects) { other = (EntityStatEffects)obj; } else { return false; }
/* 137 */      return (this.triggerAtZero == other.triggerAtZero && this.soundEventIndex == other.soundEventIndex && Arrays.equals((Object[])this.particles, (Object[])other.particles));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 142 */     int result = 1;
/* 143 */     result = 31 * result + Boolean.hashCode(this.triggerAtZero);
/* 144 */     result = 31 * result + Integer.hashCode(this.soundEventIndex);
/* 145 */     result = 31 * result + Arrays.hashCode((Object[])this.particles);
/* 146 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\EntityStatEffects.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */