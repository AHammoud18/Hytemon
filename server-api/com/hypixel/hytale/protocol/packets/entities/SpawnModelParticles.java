/*     */ package com.hypixel.hytale.protocol.packets.entities;
/*     */ import com.hypixel.hytale.protocol.ModelParticle;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class SpawnModelParticles implements Packet {
/*     */   public static final int PACKET_ID = 165;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public int entityId;
/*     */   @Nullable
/*     */   public ModelParticle[] modelParticles;
/*     */   
/*     */   public int getId() {
/*  25 */     return 165;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnModelParticles() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnModelParticles(int entityId, @Nullable ModelParticle[] modelParticles) {
/*  35 */     this.entityId = entityId;
/*  36 */     this.modelParticles = modelParticles;
/*     */   }
/*     */   
/*     */   public SpawnModelParticles(@Nonnull SpawnModelParticles other) {
/*  40 */     this.entityId = other.entityId;
/*  41 */     this.modelParticles = other.modelParticles;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SpawnModelParticles deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     SpawnModelParticles obj = new SpawnModelParticles();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.entityId = buf.getIntLE(offset + 1);
/*     */     
/*  50 */     int pos = offset + 5;
/*  51 */     if ((nullBits & 0x1) != 0) { int modelParticlesCount = VarInt.peek(buf, pos);
/*  52 */       if (modelParticlesCount < 0) throw ProtocolException.negativeLength("ModelParticles", modelParticlesCount); 
/*  53 */       if (modelParticlesCount > 4096000) throw ProtocolException.arrayTooLong("ModelParticles", modelParticlesCount, 4096000); 
/*  54 */       int modelParticlesVarLen = VarInt.size(modelParticlesCount);
/*  55 */       if ((pos + modelParticlesVarLen) + modelParticlesCount * 34L > buf.readableBytes())
/*  56 */         throw ProtocolException.bufferTooSmall("ModelParticles", pos + modelParticlesVarLen + modelParticlesCount * 34, buf.readableBytes()); 
/*  57 */       pos += modelParticlesVarLen;
/*  58 */       obj.modelParticles = new ModelParticle[modelParticlesCount];
/*  59 */       for (int i = 0; i < modelParticlesCount; i++) {
/*  60 */         obj.modelParticles[i] = ModelParticle.deserialize(buf, pos);
/*  61 */         pos += ModelParticle.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     byte nullBits = buf.getByte(offset);
/*  69 */     int pos = offset + 5;
/*  70 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  71 */       for (int i = 0; i < arrLen; ) { pos += ModelParticle.computeBytesConsumed(buf, pos); i++; }  }
/*  72 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  78 */     byte nullBits = 0;
/*  79 */     if (this.modelParticles != null) nullBits = (byte)(nullBits | 0x1); 
/*  80 */     buf.writeByte(nullBits);
/*     */     
/*  82 */     buf.writeIntLE(this.entityId);
/*     */     
/*  84 */     if (this.modelParticles != null) { if (this.modelParticles.length > 4096000) throw ProtocolException.arrayTooLong("ModelParticles", this.modelParticles.length, 4096000);  VarInt.write(buf, this.modelParticles.length); for (ModelParticle item : this.modelParticles) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  89 */     int size = 5;
/*  90 */     if (this.modelParticles != null) {
/*  91 */       int modelParticlesSize = 0;
/*  92 */       for (ModelParticle elem : this.modelParticles) modelParticlesSize += elem.computeSize(); 
/*  93 */       size += VarInt.size(this.modelParticles.length) + modelParticlesSize;
/*     */     } 
/*     */     
/*  96 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 100 */     if (buffer.readableBytes() - offset < 5) {
/* 101 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/* 104 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 106 */     int pos = offset + 5;
/*     */     
/* 108 */     if ((nullBits & 0x1) != 0) {
/* 109 */       int modelParticlesCount = VarInt.peek(buffer, pos);
/* 110 */       if (modelParticlesCount < 0) {
/* 111 */         return ValidationResult.error("Invalid array count for ModelParticles");
/*     */       }
/* 113 */       if (modelParticlesCount > 4096000) {
/* 114 */         return ValidationResult.error("ModelParticles exceeds max length 4096000");
/*     */       }
/* 116 */       pos += VarInt.length(buffer, pos);
/* 117 */       for (int i = 0; i < modelParticlesCount; i++) {
/* 118 */         ValidationResult structResult = ModelParticle.validateStructure(buffer, pos);
/* 119 */         if (!structResult.isValid()) {
/* 120 */           return ValidationResult.error("Invalid ModelParticle in ModelParticles[" + i + "]: " + structResult.error());
/*     */         }
/* 122 */         pos += ModelParticle.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 125 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SpawnModelParticles clone() {
/* 129 */     SpawnModelParticles copy = new SpawnModelParticles();
/* 130 */     copy.entityId = this.entityId;
/* 131 */     copy.modelParticles = (this.modelParticles != null) ? (ModelParticle[])Arrays.<ModelParticle>stream(this.modelParticles).map(e -> e.clone()).toArray(x$0 -> new ModelParticle[x$0]) : null;
/* 132 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SpawnModelParticles other;
/* 138 */     if (this == obj) return true; 
/* 139 */     if (obj instanceof SpawnModelParticles) { other = (SpawnModelParticles)obj; } else { return false; }
/* 140 */      return (this.entityId == other.entityId && Arrays.equals((Object[])this.modelParticles, (Object[])other.modelParticles));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 145 */     int result = 1;
/* 146 */     result = 31 * result + Integer.hashCode(this.entityId);
/* 147 */     result = 31 * result + Arrays.hashCode((Object[])this.modelParticles);
/* 148 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\entities\SpawnModelParticles.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */