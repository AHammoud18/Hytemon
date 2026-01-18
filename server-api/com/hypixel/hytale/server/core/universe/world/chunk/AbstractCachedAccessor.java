/*     */ package com.hypixel.hytale.server.core.universe.world.chunk;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public abstract class AbstractCachedAccessor
/*     */ {
/*     */   protected ComponentAccessor<ChunkStore> commandBuffer;
/*     */   private int minX;
/*     */   private int minY;
/*     */   private int minZ;
/*     */   private int length;
/*     */   private Ref<ChunkStore>[] chunks;
/*     */   private Ref<ChunkStore>[] sections;
/*     */   private Component<ChunkStore>[][] sectionComponents;
/*     */   
/*     */   protected AbstractCachedAccessor(int numComponents) {
/*  25 */     this.sectionComponents = (Component<ChunkStore>[][])new Component[numComponents][];
/*     */   }
/*     */   
/*     */   protected void init(ComponentAccessor<ChunkStore> commandBuffer, int cx, int cy, int cz, int radius) {
/*  29 */     this.commandBuffer = commandBuffer;
/*     */     
/*  31 */     radius = ChunkUtil.chunkCoordinate(radius) + 1;
/*     */     
/*  33 */     this.minX = cx - radius;
/*  34 */     this.minY = cy - radius;
/*  35 */     this.minZ = cz - radius;
/*     */     
/*  37 */     this.length = radius * 2 + 1;
/*  38 */     int size2d = this.length * this.length;
/*  39 */     int size3d = this.length * this.length * this.length;
/*     */     
/*  41 */     if (this.chunks == null || this.chunks.length < size2d) {
/*     */       
/*  43 */       this.chunks = (Ref<ChunkStore>[])new Ref[size2d];
/*     */     } else {
/*  45 */       Arrays.fill((Object[])this.chunks, (Object)null);
/*     */     } 
/*  47 */     for (int i = 0; i < this.sectionComponents.length; i++) {
/*  48 */       Component<ChunkStore>[] sectionComps = this.sectionComponents[i];
/*  49 */       if (sectionComps == null || sectionComps.length < size3d) {
/*     */         
/*  51 */         this.sectionComponents[i] = (Component<ChunkStore>[])new Component[size3d];
/*     */       } else {
/*  53 */         Arrays.fill((Object[])sectionComps, (Object)null);
/*     */       } 
/*     */     } 
/*  56 */     if (this.sections == null || this.sections.length < size3d) {
/*     */       
/*  58 */       this.sections = (Ref<ChunkStore>[])new Ref[size3d];
/*     */     } else {
/*  60 */       Arrays.fill((Object[])this.sections, (Object)null);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void insertSection(Ref<ChunkStore> section, int cx, int cy, int cz) {
/*  65 */     int x = cx - this.minX;
/*  66 */     int y = cy - this.minY;
/*  67 */     int z = cz - this.minZ;
/*     */     
/*  69 */     int index3d = x + z * this.length + y * this.length * this.length;
/*  70 */     if (index3d >= 0 && index3d < this.sections.length) {
/*  71 */       this.sections[index3d] = section;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void insertSectionComponent(int index, Component<ChunkStore> component, int cx, int cy, int cz) {
/*  76 */     int x = cx - this.minX;
/*  77 */     int y = cy - this.minY;
/*  78 */     int z = cz - this.minZ;
/*     */     
/*  80 */     int index3d = x + z * this.length + y * this.length * this.length;
/*  81 */     if (index3d >= 0 && index3d < (this.sectionComponents[index]).length) {
/*  82 */       this.sectionComponents[index][index3d] = component;
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<ChunkStore> getChunk(int cx, int cz) {
/*  88 */     int x = cx - this.minX;
/*  89 */     int z = cz - this.minZ;
/*  90 */     int index = x + z * this.length;
/*  91 */     if (index >= 0 && index < this.chunks.length) {
/*  92 */       Ref<ChunkStore> chunk = this.chunks[index];
/*  93 */       if (chunk == null) {
/*  94 */         this.chunks[index] = chunk = ((ChunkStore)this.commandBuffer.getExternalData()).getChunkReference(ChunkUtil.indexChunk(cx, cz));
/*     */       }
/*  96 */       return chunk;
/*     */     } 
/*  98 */     return ((ChunkStore)this.commandBuffer.getExternalData()).getChunkReference(ChunkUtil.indexChunk(cx, cz));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Ref<ChunkStore> getSection(int cx, int cy, int cz) {
/* 103 */     if (cy < 0 || cy >= 10) return null; 
/* 104 */     int x = cx - this.minX;
/* 105 */     int y = cy - this.minY;
/* 106 */     int z = cz - this.minZ;
/* 107 */     int index = x + z * this.length + y * this.length * this.length;
/* 108 */     if (index >= 0 && index < this.sections.length) {
/* 109 */       Ref<ChunkStore> section = this.sections[index];
/* 110 */       if (section == null) {
/* 111 */         this.sections[index] = section = ((ChunkStore)this.commandBuffer.getExternalData()).getChunkSectionReference(this.commandBuffer, cx, cy, cz);
/*     */       }
/* 113 */       return section;
/*     */     } 
/*     */     
/* 116 */     return ((ChunkStore)this.commandBuffer.getExternalData()).getChunkSectionReference(this.commandBuffer, cx, cy, cz);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected <T extends Component<ChunkStore>> T getComponentSection(int cx, int cy, int cz, int typeIndex, @Nonnull ComponentType<ChunkStore, T> componentType) {
/* 121 */     int x = cx - this.minX;
/* 122 */     int y = cy - this.minY;
/* 123 */     int z = cz - this.minZ;
/* 124 */     int index = x + z * this.length + y * this.length * this.length;
/* 125 */     if (index >= 0 && index < this.sections.length) {
/*     */       
/* 127 */       Component<ChunkStore> component = this.sectionComponents[typeIndex][index];
/* 128 */       if (component == null) {
/* 129 */         Ref<ChunkStore> ref = getSection(cx, cy, cz);
/* 130 */         if (ref == null) return null; 
/* 131 */         this.sectionComponents[typeIndex][index] = component = this.commandBuffer.getComponent(ref, componentType);
/*     */       } 
/* 133 */       return (T)component;
/*     */     } 
/*     */     
/* 136 */     Ref<ChunkStore> section = getSection(cx, cy, cz);
/* 137 */     if (section == null) return null; 
/* 138 */     return (T)this.commandBuffer.getComponent(section, componentType);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\AbstractCachedAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */