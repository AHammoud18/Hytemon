/*     */ package com.hypixel.hytale.server.core.universe.world.chunk.environment;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*     */ import com.hypixel.hytale.server.core.modules.LegacyModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.util.io.ByteBufUtil;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2LongMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2LongOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class EnvironmentChunk
/*     */   implements Component<ChunkStore>
/*     */ {
/*  29 */   public static final BuilderCodec<EnvironmentChunk> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(EnvironmentChunk.class, EnvironmentChunk::new)
/*  30 */     .addField(new KeyedCodec("Data", Codec.BYTE_ARRAY), EnvironmentChunk::deserialize, EnvironmentChunk::serialize))
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  35 */     .build();
/*     */   
/*     */   public static ComponentType<ChunkStore, EnvironmentChunk> getComponentType() {
/*  38 */     return LegacyModule.get().getEnvironmentChunkComponentType();
/*     */   }
/*     */   
/*  41 */   private final EnvironmentColumn[] columns = new EnvironmentColumn[1024];
/*  42 */   private final Int2LongMap counts = (Int2LongMap)new Int2LongOpenHashMap();
/*     */   
/*     */   public EnvironmentChunk() {
/*  45 */     this(0);
/*     */   }
/*     */   
/*     */   public EnvironmentChunk(int defaultId) {
/*  49 */     for (int i = 0; i < this.columns.length; i++) {
/*  50 */       this.columns[i] = new EnvironmentColumn(defaultId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<ChunkStore> clone() {
/*  57 */     EnvironmentChunk chunk = new EnvironmentChunk();
/*  58 */     for (int i = 0; i < this.columns.length; i++) {
/*  59 */       chunk.columns[i].copyFrom(this.columns[i]);
/*     */     }
/*  61 */     chunk.counts.putAll((Map)this.counts);
/*  62 */     return chunk;
/*     */   }
/*     */   
/*     */   public int get(int x, int y, int z) {
/*  66 */     return this.columns[idx(x, z)].get(y);
/*     */   }
/*     */   
/*     */   public EnvironmentColumn get(int x, int z) {
/*  70 */     return this.columns[idx(x, z)];
/*     */   }
/*     */   public void setColumn(int x, int z, int environmentId) {
/*     */     int maxY;
/*  74 */     EnvironmentColumn column = this.columns[idx(x, z)];
/*     */     
/*  76 */     column.set(environmentId);
/*     */     
/*  78 */     int minY = Integer.MIN_VALUE;
/*     */     
/*     */     do {
/*  81 */       int id = column.get(minY);
/*  82 */       maxY = column.getMax(minY);
/*  83 */       int count = maxY - minY + 1;
/*     */       
/*  85 */       decrementBlockCount(id, count);
/*  86 */     } while (maxY < Integer.MAX_VALUE);
/*     */     
/*  88 */     createIfNotExist(environmentId);
/*  89 */     incrementBlockCount(environmentId, 2147483647);
/*  90 */     column.set(environmentId);
/*     */   }
/*     */   
/*     */   public boolean set(int x, int y, int z, int environmentId) {
/*  94 */     EnvironmentColumn column = this.columns[idx(x, z)];
/*  95 */     int oldInternalId = column.get(y);
/*  96 */     if (environmentId != oldInternalId) {
/*     */       
/*  98 */       decrementBlockCount(oldInternalId, 1L);
/*     */ 
/*     */       
/* 101 */       createIfNotExist(environmentId);
/* 102 */       incrementBlockCount(environmentId);
/* 103 */       column.set(y, environmentId);
/* 104 */       return true;
/*     */     } 
/* 106 */     return false;
/*     */   }
/*     */   
/*     */   public boolean contains(int environmentId) {
/* 110 */     return this.counts.containsKey(environmentId);
/*     */   }
/*     */   
/*     */   private void createIfNotExist(int environmentId) {
/* 114 */     if (!this.counts.containsKey(environmentId)) {
/* 115 */       this.counts.put(environmentId, 0L);
/*     */     }
/*     */   }
/*     */   
/*     */   private void incrementBlockCount(int internalId) {
/* 120 */     this.counts.mergeLong(internalId, 1L, Long::sum);
/*     */   }
/*     */   
/*     */   private void incrementBlockCount(int internalId, int count) {
/* 124 */     long oldCount = this.counts.get(internalId);
/* 125 */     this.counts.put(internalId, oldCount + count);
/*     */   }
/*     */   
/*     */   private boolean decrementBlockCount(int environmentId, long count) {
/* 129 */     long oldCount = this.counts.get(environmentId);
/* 130 */     if (oldCount <= count) {
/* 131 */       this.counts.remove(environmentId);
/* 132 */       return true;
/*     */     } 
/* 134 */     this.counts.put(environmentId, oldCount - count);
/* 135 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] serialize() {
/* 140 */     ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
/*     */     
/*     */     try {
/* 143 */       buf.writeInt(this.counts.size());
/*     */       
/* 145 */       for (ObjectIterator<Int2LongMap.Entry> objectIterator = this.counts.int2LongEntrySet().iterator(); objectIterator.hasNext(); ) { Int2LongMap.Entry entry = objectIterator.next();
/* 146 */         int environmentId = entry.getIntKey();
/* 147 */         Environment environment = (Environment)Environment.getAssetMap().getAsset(environmentId);
/* 148 */         String key = (environment != null) ? environment.getId() : Environment.UNKNOWN.getId();
/*     */         
/* 150 */         buf.writeInt(environmentId);
/* 151 */         ByteBufUtil.writeUTF(buf, key); }
/*     */ 
/*     */ 
/*     */       
/* 155 */       for (int i = 0; i < this.columns.length; i++) {
/* 156 */         this.columns[i].serialize(buf, (environmentId, buf0) -> buf0.writeInt(environmentId));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 161 */       return ByteBufUtil.getBytesRelease(buf);
/* 162 */     } catch (Throwable t) {
/* 163 */       buf.release();
/* 164 */       throw SneakyThrow.sneakyThrow(t);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void deserialize(@Nonnull byte[] bytes) {
/* 169 */     ByteBuf buf = Unpooled.wrappedBuffer(bytes);
/* 170 */     this.counts.clear();
/*     */     
/* 172 */     int mappingCount = buf.readInt();
/* 173 */     Int2IntOpenHashMap int2IntOpenHashMap = new Int2IntOpenHashMap(mappingCount); int i;
/* 174 */     for (i = 0; i < mappingCount; i++) {
/* 175 */       int serialId = buf.readInt();
/* 176 */       String key = ByteBufUtil.readUTF(buf);
/*     */       
/* 178 */       int environmentId = Environment.getIndexOrUnknown(key, "Failed to find environment '%s' when deserializing environment chunk", new Object[] { key });
/*     */       
/* 180 */       int2IntOpenHashMap.put(serialId, environmentId);
/* 181 */       this.counts.put(environmentId, 0L);
/*     */     } 
/*     */     
/* 184 */     for (i = 0; i < this.columns.length; i++) {
/* 185 */       EnvironmentColumn column = this.columns[i];
/* 186 */       column.deserialize(buf, buf0 -> idMapping.get(buf0.readInt()));
/*     */ 
/*     */       
/* 189 */       for (int x = 0; x < column.size(); x++) {
/* 190 */         this.counts.mergeLong(column.getValue(x), 1L, Long::sum);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] serializeProtocol() {
/* 196 */     ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
/* 197 */     for (int i = 0; i < this.columns.length; i++) {
/* 198 */       this.columns[i].serializeProtocol(buf);
/*     */     }
/* 200 */     return ByteBufUtil.getBytesRelease(buf);
/*     */   }
/*     */   
/*     */   public void trim() {
/* 204 */     for (int i = 0; i < this.columns.length; i++) {
/* 205 */       this.columns[i].trim();
/*     */     }
/*     */   }
/*     */   
/*     */   private static int idx(int x, int z) {
/* 210 */     return ChunkUtil.indexColumn(x, z);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\environment\EnvironmentChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */