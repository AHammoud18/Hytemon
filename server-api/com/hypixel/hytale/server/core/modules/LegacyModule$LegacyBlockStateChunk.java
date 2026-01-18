/*     */ package com.hypixel.hytale.server.core.modules;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.store.StoredCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*     */ class LegacyBlockStateChunk
/*     */   implements Component<ChunkStore>
/*     */ {
/*     */   public static final BuilderCodec<LegacyBlockStateChunk> CODEC;
/*     */   public Holder<ChunkStore>[] holders;
/*     */   
/*     */   static {
/* 152 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(LegacyBlockStateChunk.class, LegacyBlockStateChunk::new).addField(new KeyedCodec("States", (Codec)new ArrayCodec((Codec)new StoredCodec(ChunkStore.HOLDER_CODEC_KEY), x$0 -> new Holder[x$0])), (entityChunk, array) -> entityChunk.holders = (Holder<ChunkStore>[])array, entityChunk -> { throw new UnsupportedOperationException("Serialise is not allowed for BlockStateChunk"); })).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public LegacyBlockStateChunk() {}
/*     */ 
/*     */   
/*     */   public LegacyBlockStateChunk(Holder<ChunkStore>[] holders) {
/* 160 */     this.holders = holders;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<ChunkStore> clone() {
/* 167 */     Holder[] arrayOfHolder = new Holder[this.holders.length];
/* 168 */     for (int i = 0; i < this.holders.length; i++) {
/* 169 */       arrayOfHolder[i] = this.holders[i].clone();
/*     */     }
/* 171 */     return new LegacyBlockStateChunk((Holder<ChunkStore>[])arrayOfHolder);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\LegacyModule$LegacyBlockStateChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */