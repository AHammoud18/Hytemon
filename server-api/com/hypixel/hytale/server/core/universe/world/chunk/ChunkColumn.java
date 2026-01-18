/*     */ package com.hypixel.hytale.server.core.universe.world.chunk;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.store.StoredCodec;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.modules.LegacyModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ @Deprecated
/*     */ public class ChunkColumn
/*     */   implements Component<ChunkStore>
/*     */ {
/*     */   public static final BuilderCodec<ChunkColumn> CODEC;
/*     */   
/*     */   public static ComponentType<ChunkStore, ChunkColumn> getComponentType() {
/*  27 */     return LegacyModule.get().getChunkColumnComponentType();
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
/*     */   static {
/*  63 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ChunkColumn.class, ChunkColumn::new).append(new KeyedCodec("Sections", (Codec)new ArrayCodec((Codec)new StoredCodec(ChunkStore.HOLDER_CODEC_KEY), x$0 -> new Holder[x$0])), (chunk, holders) -> chunk.sectionHolders = (Holder<ChunkStore>[])holders, chunk -> { int length = chunk.sections.length; if (chunk.sectionHolders != null) length = Math.max(chunk.sectionHolders.length, chunk.sections.length);  Holder[] arrayOfHolder = new Holder[length]; if (chunk.sectionHolders != null) System.arraycopy(chunk.sectionHolders, 0, arrayOfHolder, 0, chunk.sectionHolders.length);  for (int i = 0; i < chunk.sections.length; i++) { Ref<ChunkStore> section = chunk.sections[i]; if (section == null) break;  Store<ChunkStore> store = section.getStore(); arrayOfHolder[i] = store.copySerializableEntity(section); }  return (Function)arrayOfHolder; }).add()).build();
/*     */   }
/*  65 */   private final Ref<ChunkStore>[] sections = (Ref<ChunkStore>[])new Ref[10];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Holder<ChunkStore>[] sectionHolders;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkColumn(Holder<ChunkStore>[] sectionHolders) {
/*  78 */     this.sectionHolders = sectionHolders;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<ChunkStore> getSection(int section) {
/*  83 */     if (section < 0 || section >= this.sections.length) return null; 
/*  84 */     return this.sections[section];
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Ref<ChunkStore>[] getSections() {
/*  89 */     return this.sections;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Holder<ChunkStore>[] getSectionHolders() {
/*  94 */     return this.sectionHolders;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Holder<ChunkStore>[] takeSectionHolders() {
/*  99 */     Holder<ChunkStore>[] temp = this.sectionHolders;
/* 100 */     this.sectionHolders = null;
/* 101 */     return temp;
/*     */   }
/*     */   
/*     */   public void putSectionHolders(Holder<ChunkStore>[] holders) {
/* 105 */     this.sectionHolders = holders;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<ChunkStore> clone() {
/* 111 */     ChunkColumn newChunk = new ChunkColumn();
/* 112 */     int length = this.sections.length;
/* 113 */     if (this.sectionHolders != null) {
/* 114 */       length = Math.max(this.sectionHolders.length, this.sections.length);
/*     */     }
/*     */     
/* 117 */     Holder[] arrayOfHolder = new Holder[length];
/* 118 */     if (this.sectionHolders != null)
/* 119 */       for (int j = 0; j < this.sectionHolders.length; j++) {
/* 120 */         Holder<ChunkStore> sectionHolder = this.sectionHolders[j];
/* 121 */         if (sectionHolder != null) {
/* 122 */           arrayOfHolder[j] = sectionHolder.clone();
/*     */         }
/*     */       }  
/* 125 */     for (int i = 0; i < this.sections.length; i++) {
/* 126 */       Ref<ChunkStore> section = this.sections[i];
/* 127 */       if (section != null)
/* 128 */         arrayOfHolder[i] = section.getStore().copyEntity(section); 
/*     */     } 
/* 130 */     newChunk.sectionHolders = (Holder<ChunkStore>[])arrayOfHolder;
/* 131 */     return newChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<ChunkStore> cloneSerializable() {
/* 137 */     ChunkColumn newChunk = new ChunkColumn();
/* 138 */     int length = this.sections.length;
/* 139 */     if (this.sectionHolders != null) {
/* 140 */       length = Math.max(this.sectionHolders.length, this.sections.length);
/*     */     }
/*     */     
/* 143 */     Holder[] arrayOfHolder = new Holder[length];
/* 144 */     if (this.sectionHolders != null)
/* 145 */       for (int j = 0; j < this.sectionHolders.length; j++) {
/* 146 */         Holder<ChunkStore> sectionHolder = this.sectionHolders[j];
/* 147 */         if (sectionHolder != null) {
/* 148 */           arrayOfHolder[j] = sectionHolder.clone();
/*     */         }
/*     */       }  
/* 151 */     for (int i = 0; i < this.sections.length; i++) {
/* 152 */       Ref<ChunkStore> section = this.sections[i];
/* 153 */       if (section != null)
/* 154 */         arrayOfHolder[i] = section.getStore().copySerializableEntity(section); 
/*     */     } 
/* 156 */     newChunk.sectionHolders = (Holder<ChunkStore>[])arrayOfHolder;
/* 157 */     return newChunk;
/*     */   }
/*     */   
/*     */   public ChunkColumn() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\ChunkColumn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */