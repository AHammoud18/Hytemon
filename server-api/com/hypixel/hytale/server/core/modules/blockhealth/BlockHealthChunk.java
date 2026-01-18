/*     */ package com.hypixel.hytale.server.core.modules.blockhealth;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.world.UpdateBlockDamage;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.util.io.ByteBufUtil;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.time.Instant;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Predicate;
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
/*     */ public class BlockHealthChunk
/*     */   implements Component<ChunkStore>
/*     */ {
/*     */   private static final byte SERIALIZATION_VERSION = 2;
/*     */   public static final BuilderCodec<BlockHealthChunk> CODEC;
/*     */   
/*     */   static {
/*  45 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockHealthChunk.class, BlockHealthChunk::new).append(new KeyedCodec("Data", Codec.BYTE_ARRAY), BlockHealthChunk::deserialize, BlockHealthChunk::serialize).documentation("Binary data representing the state of this BlockHealthChunk").add()).append(new KeyedCodec("LastRepairGameTime", (Codec)Codec.INSTANT), (o, l) -> o.lastRepairGameTime = l, o -> o.lastRepairGameTime).documentation("The last tick of the world this BlockHealthChunk processed.").add()).build();
/*     */   }
/*  47 */   private final Map<Vector3i, BlockHealth> blockHealthMap = (Map<Vector3i, BlockHealth>)new Object2ObjectOpenHashMap(0);
/*  48 */   private final Map<Vector3i, FragileBlock> blockFragilityMap = (Map<Vector3i, FragileBlock>)new Object2ObjectOpenHashMap(0);
/*     */ 
/*     */ 
/*     */   
/*     */   private Instant lastRepairGameTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Instant getLastRepairGameTime() {
/*  58 */     return this.lastRepairGameTime;
/*     */   }
/*     */   
/*     */   public void setLastRepairGameTime(Instant lastRepairGameTime) {
/*  62 */     this.lastRepairGameTime = lastRepairGameTime;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<Vector3i, BlockHealth> getBlockHealthMap() {
/*  67 */     return this.blockHealthMap;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Map<Vector3i, FragileBlock> getBlockFragilityMap() {
/*  72 */     return this.blockFragilityMap;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockHealth damageBlock(Instant currentUptime, @Nonnull World world, @Nonnull Vector3i block, float health) {
/*  79 */     BlockHealth blockHealth = this.blockHealthMap.compute(block, (key, value) -> {
/*     */           if (value == null) {
/*     */             value = new BlockHealth();
/*     */           }
/*     */           
/*     */           value.setHealth(value.getHealth() - health);
/*     */           
/*     */           value.setLastDamageGameTime(currentUptime);
/*     */           
/*     */           return (value.getHealth() < 1.0D) ? value : null;
/*     */         });
/*  90 */     if (blockHealth != null && !blockHealth.isDestroyed()) {
/*     */ 
/*     */ 
/*     */       
/*  94 */       Predicate<PlayerRef> filter = player -> true;
/*  95 */       world.getNotificationHandler().updateBlockDamage(block.getX(), block.getY(), block.getZ(), blockHealth.getHealth(), -health, filter);
/*     */     } 
/*     */     
/*  98 */     return Objects.<BlockHealth>requireNonNullElse(blockHealth, BlockHealth.NO_DAMAGE_INSTANCE);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockHealth repairBlock(@Nonnull World world, @Nonnull Vector3i block, float progress) {
/* 104 */     BlockHealth blockHealth = Objects.<BlockHealth>requireNonNullElse(this.blockHealthMap.computeIfPresent(block, (key, value) -> { value.setHealth(value.getHealth() + progress); return (value.getHealth() > 1.0D) ? value : null; }), BlockHealth.NO_DAMAGE_INSTANCE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     world.getNotificationHandler().updateBlockDamage(block.getX(), block.getY(), block.getZ(), blockHealth.getHealth(), progress);
/*     */     
/* 111 */     return blockHealth;
/*     */   }
/*     */   
/*     */   public void removeBlock(@Nonnull World world, @Nonnull Vector3i block) {
/* 115 */     if (this.blockHealthMap.remove(block) != null) {
/* 116 */       world.getNotificationHandler().updateBlockDamage(block.getX(), block.getY(), block.getZ(), BlockHealth.NO_DAMAGE_INSTANCE.getHealth(), 0.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void makeBlockFragile(Vector3i blockLocation, float fragileDuration) {
/* 121 */     this.blockFragilityMap.compute(blockLocation, (key, value) -> {
/*     */           if (value == null) {
/*     */             value = new FragileBlock(fragileDuration);
/*     */           }
/*     */           value.setDurationSeconds(fragileDuration);
/*     */           return (value.getDurationSeconds() <= 0.0D) ? null : value;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlockFragile(Vector3i block) {
/* 133 */     return (this.blockFragilityMap.get(block) != null);
/*     */   }
/*     */   
/*     */   public float getBlockHealth(Vector3i block) {
/* 137 */     return ((BlockHealth)this.blockHealthMap.getOrDefault(block, BlockHealth.NO_DAMAGE_INSTANCE)).getHealth();
/*     */   }
/*     */   
/*     */   public void createBlockDamagePackets(@Nonnull List<Packet> list) {
/* 141 */     for (Map.Entry<Vector3i, BlockHealth> entry : this.blockHealthMap.entrySet()) {
/* 142 */       Vector3i block = entry.getKey();
/* 143 */       BlockPosition blockPosition = new BlockPosition(block.getX(), block.getY(), block.getZ());
/* 144 */       list.add(new UpdateBlockDamage(blockPosition, ((BlockHealth)entry.getValue()).getHealth(), 0.0F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockHealthChunk clone() {
/* 151 */     BlockHealthChunk copy = new BlockHealthChunk();
/* 152 */     copy.lastRepairGameTime = this.lastRepairGameTime;
/* 153 */     for (Map.Entry<Vector3i, BlockHealth> entry : this.blockHealthMap.entrySet()) {
/* 154 */       copy.blockHealthMap.put(entry.getKey(), ((BlockHealth)entry.getValue()).clone());
/*     */     }
/* 156 */     for (Map.Entry<Vector3i, FragileBlock> entry : this.blockFragilityMap.entrySet()) {
/* 157 */       copy.blockFragilityMap.put(entry.getKey(), ((FragileBlock)entry.getValue()).clone());
/*     */     }
/* 159 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void deserialize(@Nonnull byte[] data) {
/* 165 */     this.blockHealthMap.clear();
/*     */     
/* 167 */     ByteBuf buf = Unpooled.wrappedBuffer(data);
/* 168 */     byte version = buf.readByte();
/* 169 */     int healthEntries = buf.readInt();
/* 170 */     for (int i = 0; i < healthEntries; i++) {
/* 171 */       int x = buf.readInt();
/* 172 */       int y = buf.readInt();
/* 173 */       int z = buf.readInt();
/* 174 */       BlockHealth bh = new BlockHealth();
/* 175 */       bh.deserialize(buf, version);
/* 176 */       this.blockHealthMap.put(new Vector3i(x, y, z), bh);
/*     */     } 
/*     */     
/* 179 */     if (version > 1) {
/* 180 */       int fragilityEntries = buf.readInt();
/* 181 */       for (int j = 0; j < fragilityEntries; j++) {
/* 182 */         int x = buf.readInt();
/* 183 */         int y = buf.readInt();
/* 184 */         int z = buf.readInt();
/* 185 */         FragileBlock fragileBlock = new FragileBlock();
/* 186 */         fragileBlock.deserialize(buf, version);
/* 187 */         this.blockFragilityMap.put(new Vector3i(x, y, z), fragileBlock);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] serialize() {
/* 195 */     ByteBuf buf = Unpooled.buffer();
/* 196 */     buf.writeByte(2);
/* 197 */     buf.writeInt(this.blockHealthMap.size());
/* 198 */     for (Map.Entry<Vector3i, BlockHealth> entry : this.blockHealthMap.entrySet()) {
/* 199 */       Vector3i vec = entry.getKey();
/* 200 */       buf.writeInt(vec.x);
/* 201 */       buf.writeInt(vec.y);
/* 202 */       buf.writeInt(vec.z);
/* 203 */       BlockHealth bh = entry.getValue();
/* 204 */       bh.serialize(buf);
/*     */     } 
/*     */     
/* 207 */     buf.writeInt(this.blockFragilityMap.size());
/* 208 */     for (Map.Entry<Vector3i, FragileBlock> entry : this.blockFragilityMap.entrySet()) {
/* 209 */       Vector3i vec = entry.getKey();
/* 210 */       buf.writeInt(vec.x);
/* 211 */       buf.writeInt(vec.y);
/* 212 */       buf.writeInt(vec.z);
/* 213 */       ((FragileBlock)entry.getValue()).serialize(buf);
/*     */     } 
/*     */     
/* 216 */     return ByteBufUtil.getBytesRelease(buf);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\blockhealth\BlockHealthChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */