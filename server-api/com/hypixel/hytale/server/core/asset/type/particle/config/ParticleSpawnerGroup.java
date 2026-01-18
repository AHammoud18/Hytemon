/*     */ package com.hypixel.hytale.server.core.asset.type.particle.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.InitialVelocity;
/*     */ import com.hypixel.hytale.protocol.ParticleAttractor;
/*     */ import com.hypixel.hytale.protocol.ParticleSpawnerGroup;
/*     */ import com.hypixel.hytale.protocol.RangeVector3f;
/*     */ import com.hypixel.hytale.protocol.Rangef;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ 
/*     */ 
/*     */ public class ParticleSpawnerGroup
/*     */   implements NetworkSerializable<ParticleSpawnerGroup>
/*     */ {
/*     */   public static final BuilderCodec<ParticleSpawnerGroup> CODEC;
/*     */   protected String spawnerId;
/*     */   protected Vector3f positionOffset;
/*     */   protected Direction rotationOffset;
/*     */   protected boolean fixedRotation;
/*     */   protected Rangef spawnRate;
/*     */   protected Rangef lifeSpan;
/*     */   protected float startDelay;
/*     */   protected Rangef waveDelay;
/*     */   
/*     */   static {
/*  86 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ParticleSpawnerGroup.class, ParticleSpawnerGroup::new).append(new KeyedCodec("SpawnerId", (Codec)Codec.STRING), (particleSpawnerGroup, s) -> particleSpawnerGroup.spawnerId = s, particleSpawnerGroup -> particleSpawnerGroup.spawnerId).addValidator(ParticleSpawner.VALIDATOR_CACHE.getValidator()).add()).addField(new KeyedCodec("PositionOffset", (Codec)ProtocolCodecs.VECTOR3F), (particleSpawnerGroup, o) -> particleSpawnerGroup.positionOffset = o, particleSpawnerGroup -> particleSpawnerGroup.positionOffset)).addField(new KeyedCodec("RotationOffset", (Codec)ProtocolCodecs.DIRECTION), (particleSpawnerGroup, o) -> particleSpawnerGroup.rotationOffset = o, particleSpawnerGroup -> particleSpawnerGroup.rotationOffset)).addField(new KeyedCodec("FixedRotation", (Codec)Codec.BOOLEAN), (particleSpawnerGroup, b) -> particleSpawnerGroup.fixedRotation = b.booleanValue(), particleSpawnerGroup -> Boolean.valueOf(particleSpawnerGroup.fixedRotation))).addField(new KeyedCodec("SpawnRate", (Codec)ProtocolCodecs.RANGEF), (particleSpawnerGroup, b) -> particleSpawnerGroup.spawnRate = b, particleSpawnerGroup -> particleSpawnerGroup.spawnRate)).addField(new KeyedCodec("LifeSpan", (Codec)ProtocolCodecs.RANGEF), (particleSpawnerGroup, b) -> particleSpawnerGroup.lifeSpan = b, particleSpawnerGroup -> particleSpawnerGroup.lifeSpan)).addField(new KeyedCodec("StartDelay", (Codec)Codec.FLOAT), (particleSpawner, f) -> particleSpawner.startDelay = f.floatValue(), particleSpawner -> Float.valueOf(particleSpawner.startDelay))).addField(new KeyedCodec("WaveDelay", (Codec)ProtocolCodecs.RANGEF), (particleSpawnerGroup, b) -> particleSpawnerGroup.waveDelay = b, particleSpawnerGroup -> particleSpawnerGroup.waveDelay)).addField(new KeyedCodec("TotalSpawners", (Codec)Codec.INTEGER), (particleSpawnerGroup, i) -> particleSpawnerGroup.totalSpawners = i.intValue(), particleSpawnerGroup -> Integer.valueOf(particleSpawnerGroup.totalSpawners))).addField(new KeyedCodec("MaxConcurrent", (Codec)Codec.INTEGER), (particleSpawnerGroup, i) -> particleSpawnerGroup.maxConcurrent = i.intValue(), particleSpawnerGroup -> Integer.valueOf(particleSpawnerGroup.maxConcurrent))).addField(new KeyedCodec("InitialVelocity", (Codec)ProtocolCodecs.INITIAL_VELOCITY), (particleSpawnerGroup, o) -> particleSpawnerGroup.initialVelocity = o, particleSpawnerGroup -> particleSpawnerGroup.initialVelocity)).addField(new KeyedCodec("EmitOffset", (Codec)ProtocolCodecs.RANGE_VECTOR3F), (particleSpawnerGroup, o) -> particleSpawnerGroup.emitOffset = o, particleSpawnerGroup -> particleSpawnerGroup.emitOffset)).addField(new KeyedCodec("Attractors", (Codec)new ArrayCodec((Codec)ParticleAttractor.CODEC, x$0 -> new ParticleAttractor[x$0])), (particleSpawnerGroup, o) -> particleSpawnerGroup.attractors = o, particleSpawnerGroup -> particleSpawnerGroup.attractors)).build();
/*     */   }
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
/*  98 */   protected int totalSpawners = 1;
/*     */   
/*     */   protected int maxConcurrent;
/*     */   
/*     */   protected InitialVelocity initialVelocity;
/*     */   protected RangeVector3f emitOffset;
/*     */   protected ParticleAttractor[] attractors;
/*     */   
/*     */   public ParticleSpawnerGroup(String spawnerId, Vector3f positionOffset, Direction rotationOffset, boolean fixedRotation, Rangef spawnRate, Rangef lifeSpan, float startDelay, Rangef waveDelay, int totalSpawners, int maxConcurrent, InitialVelocity initialVelocity, RangeVector3f emitOffset, ParticleAttractor[] attractors) {
/* 107 */     this.spawnerId = spawnerId;
/* 108 */     this.positionOffset = positionOffset;
/* 109 */     this.rotationOffset = rotationOffset;
/* 110 */     this.fixedRotation = fixedRotation;
/* 111 */     this.spawnRate = spawnRate;
/* 112 */     this.startDelay = startDelay;
/* 113 */     this.lifeSpan = lifeSpan;
/* 114 */     this.waveDelay = waveDelay;
/* 115 */     this.totalSpawners = totalSpawners;
/* 116 */     this.maxConcurrent = maxConcurrent;
/* 117 */     this.initialVelocity = initialVelocity;
/* 118 */     this.emitOffset = emitOffset;
/* 119 */     this.attractors = attractors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ParticleSpawnerGroup toPacket() {
/* 128 */     ParticleSpawnerGroup packet = new ParticleSpawnerGroup();
/* 129 */     packet.spawnerId = this.spawnerId;
/* 130 */     packet.positionOffset = this.positionOffset;
/* 131 */     packet.rotationOffset = this.rotationOffset;
/*     */     
/* 133 */     if (this.spawnRate != null) {
/* 134 */       packet.spawnRate = this.spawnRate;
/*     */     }
/*     */     
/* 137 */     if (this.lifeSpan != null) {
/* 138 */       packet.lifeSpan = this.lifeSpan;
/*     */     }
/*     */     
/* 141 */     if (this.waveDelay != null) {
/* 142 */       packet.waveDelay = this.waveDelay;
/*     */     }
/*     */     
/* 145 */     packet.startDelay = this.startDelay;
/* 146 */     packet.maxConcurrent = this.maxConcurrent;
/* 147 */     packet.totalSpawners = this.totalSpawners;
/* 148 */     packet.initialVelocity = this.initialVelocity;
/* 149 */     packet.emitOffset = this.emitOffset;
/*     */     
/* 151 */     if (this.attractors != null && this.attractors.length > 0) {
/* 152 */       packet.attractors = (ParticleAttractor[])ArrayUtil.copyAndMutate((Object[])this.attractors, ParticleAttractor::toPacket, x$0 -> new ParticleAttractor[x$0]);
/*     */     }
/*     */     
/* 155 */     packet.fixedRotation = this.fixedRotation;
/* 156 */     return packet;
/*     */   }
/*     */   
/*     */   public String getSpawnerId() {
/* 160 */     return this.spawnerId;
/*     */   }
/*     */   
/*     */   public Vector3f getPositionOffset() {
/* 164 */     return this.positionOffset;
/*     */   }
/*     */   
/*     */   public Direction getRotationOffset() {
/* 168 */     return this.rotationOffset;
/*     */   }
/*     */   
/*     */   public boolean isFixedRotation() {
/* 172 */     return this.fixedRotation;
/*     */   }
/*     */   
/*     */   public Rangef getSpawnRate() {
/* 176 */     return this.spawnRate;
/*     */   }
/*     */   
/*     */   public Rangef getLifeSpan() {
/* 180 */     return this.lifeSpan;
/*     */   }
/*     */   
/*     */   public float getStartDelay() {
/* 184 */     return this.startDelay;
/*     */   }
/*     */   
/*     */   public Rangef getWaveDelay() {
/* 188 */     return this.waveDelay;
/*     */   }
/*     */   
/*     */   public int getTotalSpawners() {
/* 192 */     return this.totalSpawners;
/*     */   }
/*     */   
/*     */   public int getMaxConcurrent() {
/* 196 */     return this.maxConcurrent;
/*     */   }
/*     */   
/*     */   public InitialVelocity getInitialVelocity() {
/* 200 */     return this.initialVelocity;
/*     */   }
/*     */   
/*     */   public RangeVector3f getEmitOffset() {
/* 204 */     return this.emitOffset;
/*     */   }
/*     */   
/*     */   public ParticleAttractor[] getAttractors() {
/* 208 */     return this.attractors;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 214 */     return "ParticleSpawnerGroup{spawnerId='" + this.spawnerId + "', positionOffset=" + String.valueOf(this.positionOffset) + ", rotationOffset=" + String.valueOf(this.rotationOffset) + ", fixedRotation=" + this.fixedRotation + ", spawnRate=" + String.valueOf(this.spawnRate) + ", lifeSpan=" + String.valueOf(this.lifeSpan) + ", startDelay=" + this.startDelay + ", waveDelay=" + String.valueOf(this.waveDelay) + ", totalSpawners=" + this.totalSpawners + ", maxConcurrent=" + this.maxConcurrent + ", initialVelocity=" + String.valueOf(this.initialVelocity) + ", emitOffset=" + String.valueOf(this.emitOffset) + ", attractors=" + 
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
/*     */ 
/*     */       
/* 227 */       Arrays.toString((Object[])this.attractors) + "}";
/*     */   }
/*     */   
/*     */   protected ParticleSpawnerGroup() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\particle\config\ParticleSpawnerGroup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */