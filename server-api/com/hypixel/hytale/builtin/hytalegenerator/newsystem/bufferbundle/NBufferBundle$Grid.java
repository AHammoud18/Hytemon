/*     */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle;
/*     */ 
/*     */ 
/*     */ public class Grid implements MemInstrument {
/*     */   private final NBufferType bufferType;
/*     */   private final Map<Vector3i, TrackedBuffer> buffers;
/*     */   private final Deque<Vector3i> oldestColumnEntryDeque_bufferGrid;
/*     */   private final int capacity;
/*     */   private final List<NBufferBundle.Access> accessors;
/*     */   
/*     */   public static final class TrackedBuffer extends Record implements MemInstrument {
/*     */     @Nonnull
/*     */     private final NBufferBundle.Tracker tracker;
/*     */     @Nonnull
/*     */     private final NBuffer buffer;
/*     */     
/*  17 */     public TrackedBuffer(@Nonnull NBufferBundle.Tracker tracker, @Nonnull NBuffer buffer) { this.tracker = tracker; this.buffer = buffer; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #17	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  17 */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer; } @Nonnull public NBufferBundle.Tracker tracker() { return this.tracker; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #17	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #17	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;
/*  17 */       //   0	8	1	o	Ljava/lang/Object; } @Nonnull public NBuffer buffer() { return this.buffer; }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public MemInstrument.Report getMemoryUsage() {
/*  23 */       long size_bytes = 16L + this.tracker.getMemoryUsage().size_bytes() + this.buffer.getMemoryUsage().size_bytes();
/*  24 */       return new MemInstrument.Report(size_bytes);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Grid(@Nonnull NBufferType bufferType, int capacity) {
/*  35 */     this.bufferType = bufferType;
/*  36 */     this.buffers = new HashMap<>();
/*  37 */     this.oldestColumnEntryDeque_bufferGrid = new ArrayDeque<>();
/*  38 */     this.capacity = Math.max(capacity, 0);
/*  39 */     this.accessors = new ArrayList<>();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public NBufferType getBufferType() {
/*  44 */     return this.bufferType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public NBufferBundle.Access openAccess(@Nonnull Bounds3i bounds_bufferGrid) {
/*  49 */     NBufferBundle.Access access = new NBufferBundle.Access(this, bounds_bufferGrid);
/*  50 */     this.accessors.add(access);
/*  51 */     access.loadGrid();
/*     */     
/*  53 */     return access;
/*     */   }
/*     */   
/*     */   public void closeAllAccesses() {
/*  57 */     for (int i = this.accessors.size() - 1; i >= 0; i--) {
/*  58 */       NBufferBundle.Access access = this.accessors.get(i);
/*  59 */       access.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MemInstrument.Report getMemoryUsage() {
/*  66 */     long size_bytes = 68L;
/*     */ 
/*     */     
/*  69 */     size_bytes += 28L * this.buffers.size();
/*  70 */     size_bytes += 4L * this.buffers.size();
/*  71 */     size_bytes += 32L * this.buffers.size();
/*  72 */     for (TrackedBuffer buffer : this.buffers.values()) {
/*  73 */       size_bytes += buffer.getMemoryUsage().size_bytes();
/*     */     }
/*     */     
/*  76 */     size_bytes += 8L * this.accessors.size();
/*  77 */     for (NBufferBundle.Access access : this.accessors) {
/*  78 */       size_bytes += access.getMemoryUsage().size_bytes();
/*     */     }
/*     */     
/*  81 */     return new MemInstrument.Report(size_bytes);
/*     */   }
/*     */   
/*     */   private void ensureBufferColumnExists(@Nonnull Vector3i position_bufferGrid, @Nonnull TrackedBuffer[] trackedBuffersOut) {
/*  85 */     assert position_bufferGrid.y == 0;
/*  86 */     assert trackedBuffersOut.length == 40;
/*     */     
/*  88 */     TrackedBuffer buffer = this.buffers.get(position_bufferGrid);
/*  89 */     if (buffer == null) {
/*  90 */       createBufferColumn(position_bufferGrid, trackedBuffersOut);
/*     */       
/*     */       return;
/*     */     } 
/*  94 */     Vector3i positionClone_bufferGrid = new Vector3i(position_bufferGrid);
/*  95 */     for (int i = 0; i < trackedBuffersOut.length; i++) {
/*  96 */       positionClone_bufferGrid.setY(i + 0);
/*     */       
/*  98 */       trackedBuffersOut[i] = this.buffers.get(positionClone_bufferGrid);
/*  99 */       assert trackedBuffersOut[i] != null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createBufferColumn(@Nonnull Vector3i position_bufferGrid, @Nonnull TrackedBuffer[] trackedBuffersOut) {
/* 104 */     assert !this.buffers.containsKey(position_bufferGrid);
/* 105 */     assert trackedBuffersOut.length == 40;
/*     */     
/* 107 */     tryTrimSurplus(40);
/*     */     
/* 109 */     int i = 0;
/* 110 */     for (int y = 0; y < 40; y++) {
/* 111 */       Vector3i finalPosition_bufferGrid = new Vector3i(position_bufferGrid.x, y, position_bufferGrid.z);
/* 112 */       NBufferBundle.Tracker tracker = new NBufferBundle.Tracker();
/* 113 */       NBuffer buffer = this.bufferType.bufferSupplier.get();
/* 114 */       assert this.bufferType.isValid(buffer);
/* 115 */       trackedBuffersOut[i] = new TrackedBuffer(tracker, buffer);
/*     */       
/* 117 */       this.buffers.put(finalPosition_bufferGrid, trackedBuffersOut[i]);
/* 118 */       i++;
/*     */     } 
/*     */     
/* 121 */     Vector3i tilePosition_bufferGrid = new Vector3i(position_bufferGrid.x, 0, position_bufferGrid.z);
/* 122 */     this.oldestColumnEntryDeque_bufferGrid.addLast(tilePosition_bufferGrid);
/*     */   }
/*     */   
/*     */   private void tryTrimSurplus(int extraRoom) {
/* 126 */     int surplusCount = Math.max(0, this.buffers.size() - this.capacity - extraRoom);
/*     */ 
/*     */     
/* 129 */     int surplusColumnsCount = (surplusCount == 0) ? 0 : (surplusCount / 40 + 1);
/*     */     
/* 131 */     for (int i = 0; i < surplusColumnsCount; i++) {
/* 132 */       if (!destroyOldestBufferColumn())
/*     */         return; 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean destroyOldestBufferColumn() {
/* 138 */     assert !this.oldestColumnEntryDeque_bufferGrid.isEmpty();
/*     */     
/* 140 */     for (int i = 0; i < this.oldestColumnEntryDeque_bufferGrid.size(); i++) {
/* 141 */       Vector3i oldest_bufferGrid = this.oldestColumnEntryDeque_bufferGrid.removeFirst();
/* 142 */       if (isBufferColumnInAccess(oldest_bufferGrid)) {
/* 143 */         this.oldestColumnEntryDeque_bufferGrid.addLast(oldest_bufferGrid);
/*     */       }
/*     */       else {
/*     */         
/* 147 */         removeBufferColumn(oldest_bufferGrid);
/* 148 */         return true;
/*     */       } 
/*     */     } 
/* 151 */     return false;
/*     */   }
/*     */   
/*     */   private void removeBufferColumn(@Nonnull Vector3i position_bufferGrid) {
/* 155 */     assert position_bufferGrid.y == 0;
/*     */     
/* 157 */     Vector3i removalPosition_bufferGrid = new Vector3i(position_bufferGrid);
/* 158 */     for (int y = 0; y < 40; y++) {
/* 159 */       removalPosition_bufferGrid.setY(y);
/*     */       
/* 161 */       this.buffers.remove(removalPosition_bufferGrid);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isBufferColumnInAccess(@Nonnull Vector3i position_bufferGrid) {
/* 166 */     assert position_bufferGrid.y == 0;
/*     */     
/* 168 */     for (NBufferBundle.Access access : this.accessors) {
/* 169 */       if (access.bounds_bufferGrid.contains(position_bufferGrid)) {
/* 170 */         return true;
/*     */       }
/*     */     } 
/* 173 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\bufferbundle\NBufferBundle$Grid.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */