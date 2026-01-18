/*     */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle;import com.hypixel.hytale.math.vector.Vector3i;
/*     */ public class NBufferBundle implements MemInstrument { private final Map<NBufferType, Grid> grids;
/*     */   
/*     */   public static class Grid implements MemInstrument {
/*     */     private final NBufferType bufferType;
/*     */     private final Map<Vector3i, TrackedBuffer> buffers;
/*     */     private final Deque<Vector3i> oldestColumnEntryDeque_bufferGrid;
/*     */     private final int capacity;
/*     */     private final List<NBufferBundle.Access> accessors;
/*     */     
/*     */     public static final class TrackedBuffer extends Record implements MemInstrument {
/*     */       @Nonnull
/*     */       private final NBufferBundle.Tracker tracker;
/*     */       @Nonnull
/*     */       private final NBuffer buffer;
/*     */       
/*  17 */       public TrackedBuffer(@Nonnull NBufferBundle.Tracker tracker, @Nonnull NBuffer buffer) { this.tracker = tracker; this.buffer = buffer; } public final String toString() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;)Ljava/lang/String;
/*     */         //   6: areturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #17	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer; } public final int hashCode() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;)I
/*     */         //   6: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #17	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer; } public final boolean equals(Object o) { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: aload_1
/*     */         //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;Ljava/lang/Object;)Z
/*     */         //   7: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #17	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	8	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;
/*  17 */         //   0	8	1	o	Ljava/lang/Object; } @Nonnull public NBufferBundle.Tracker tracker() { return this.tracker; } @Nonnull public NBuffer buffer() { return this.buffer; }
/*     */ 
/*     */ 
/*     */       
/*     */       @Nonnull
/*     */       public MemInstrument.Report getMemoryUsage() {
/*  23 */         long size_bytes = 16L + this.tracker.getMemoryUsage().size_bytes() + this.buffer.getMemoryUsage().size_bytes();
/*  24 */         return new MemInstrument.Report(size_bytes);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Grid(@Nonnull NBufferType bufferType, int capacity) {
/*  35 */       this.bufferType = bufferType;
/*  36 */       this.buffers = new HashMap<>();
/*  37 */       this.oldestColumnEntryDeque_bufferGrid = new ArrayDeque<>();
/*  38 */       this.capacity = Math.max(capacity, 0);
/*  39 */       this.accessors = new ArrayList<>();
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public NBufferType getBufferType() {
/*  44 */       return this.bufferType;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public NBufferBundle.Access openAccess(@Nonnull Bounds3i bounds_bufferGrid) {
/*  49 */       NBufferBundle.Access access = new NBufferBundle.Access(this, bounds_bufferGrid);
/*  50 */       this.accessors.add(access);
/*  51 */       access.loadGrid();
/*     */       
/*  53 */       return access;
/*     */     }
/*     */     
/*     */     public void closeAllAccesses() {
/*  57 */       for (int i = this.accessors.size() - 1; i >= 0; i--) {
/*  58 */         NBufferBundle.Access access = this.accessors.get(i);
/*  59 */         access.close();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public MemInstrument.Report getMemoryUsage() {
/*  66 */       long size_bytes = 68L;
/*     */ 
/*     */       
/*  69 */       size_bytes += 28L * this.buffers.size();
/*  70 */       size_bytes += 4L * this.buffers.size();
/*  71 */       size_bytes += 32L * this.buffers.size();
/*  72 */       for (TrackedBuffer buffer : this.buffers.values()) {
/*  73 */         size_bytes += buffer.getMemoryUsage().size_bytes();
/*     */       }
/*     */       
/*  76 */       size_bytes += 8L * this.accessors.size();
/*  77 */       for (NBufferBundle.Access access : this.accessors) {
/*  78 */         size_bytes += access.getMemoryUsage().size_bytes();
/*     */       }
/*     */       
/*  81 */       return new MemInstrument.Report(size_bytes);
/*     */     }
/*     */     
/*     */     private void ensureBufferColumnExists(@Nonnull Vector3i position_bufferGrid, @Nonnull TrackedBuffer[] trackedBuffersOut) {
/*  85 */       assert position_bufferGrid.y == 0;
/*  86 */       assert trackedBuffersOut.length == 40;
/*     */       
/*  88 */       TrackedBuffer buffer = this.buffers.get(position_bufferGrid);
/*  89 */       if (buffer == null) {
/*  90 */         createBufferColumn(position_bufferGrid, trackedBuffersOut);
/*     */         
/*     */         return;
/*     */       } 
/*  94 */       Vector3i positionClone_bufferGrid = new Vector3i(position_bufferGrid);
/*  95 */       for (int i = 0; i < trackedBuffersOut.length; i++) {
/*  96 */         positionClone_bufferGrid.setY(i + 0);
/*     */         
/*  98 */         trackedBuffersOut[i] = this.buffers.get(positionClone_bufferGrid);
/*  99 */         assert trackedBuffersOut[i] != null;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void createBufferColumn(@Nonnull Vector3i position_bufferGrid, @Nonnull TrackedBuffer[] trackedBuffersOut) {
/* 104 */       assert !this.buffers.containsKey(position_bufferGrid);
/* 105 */       assert trackedBuffersOut.length == 40;
/*     */       
/* 107 */       tryTrimSurplus(40);
/*     */       
/* 109 */       int i = 0;
/* 110 */       for (int y = 0; y < 40; y++) {
/* 111 */         Vector3i finalPosition_bufferGrid = new Vector3i(position_bufferGrid.x, y, position_bufferGrid.z);
/* 112 */         NBufferBundle.Tracker tracker = new NBufferBundle.Tracker();
/* 113 */         NBuffer buffer = this.bufferType.bufferSupplier.get();
/* 114 */         assert this.bufferType.isValid(buffer);
/* 115 */         trackedBuffersOut[i] = new TrackedBuffer(tracker, buffer);
/*     */         
/* 117 */         this.buffers.put(finalPosition_bufferGrid, trackedBuffersOut[i]);
/* 118 */         i++;
/*     */       } 
/*     */       
/* 121 */       Vector3i tilePosition_bufferGrid = new Vector3i(position_bufferGrid.x, 0, position_bufferGrid.z);
/* 122 */       this.oldestColumnEntryDeque_bufferGrid.addLast(tilePosition_bufferGrid);
/*     */     }
/*     */     
/*     */     private void tryTrimSurplus(int extraRoom) {
/* 126 */       int surplusCount = Math.max(0, this.buffers.size() - this.capacity - extraRoom);
/*     */ 
/*     */       
/* 129 */       int surplusColumnsCount = (surplusCount == 0) ? 0 : (surplusCount / 40 + 1);
/*     */       
/* 131 */       for (int i = 0; i < surplusColumnsCount; i++) {
/* 132 */         if (!destroyOldestBufferColumn())
/*     */           return; 
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean destroyOldestBufferColumn() {
/* 138 */       assert !this.oldestColumnEntryDeque_bufferGrid.isEmpty();
/*     */       
/* 140 */       for (int i = 0; i < this.oldestColumnEntryDeque_bufferGrid.size(); i++) {
/* 141 */         Vector3i oldest_bufferGrid = this.oldestColumnEntryDeque_bufferGrid.removeFirst();
/* 142 */         if (isBufferColumnInAccess(oldest_bufferGrid)) {
/* 143 */           this.oldestColumnEntryDeque_bufferGrid.addLast(oldest_bufferGrid);
/*     */         }
/*     */         else {
/*     */           
/* 147 */           removeBufferColumn(oldest_bufferGrid);
/* 148 */           return true;
/*     */         } 
/*     */       } 
/* 151 */       return false;
/*     */     }
/*     */     
/*     */     private void removeBufferColumn(@Nonnull Vector3i position_bufferGrid) {
/* 155 */       assert position_bufferGrid.y == 0;
/*     */       
/* 157 */       Vector3i removalPosition_bufferGrid = new Vector3i(position_bufferGrid);
/* 158 */       for (int y = 0; y < 40; y++) {
/* 159 */         removalPosition_bufferGrid.setY(y);
/*     */         
/* 161 */         this.buffers.remove(removalPosition_bufferGrid);
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean isBufferColumnInAccess(@Nonnull Vector3i position_bufferGrid) {
/* 166 */       assert position_bufferGrid.y == 0;
/*     */       
/* 168 */       for (NBufferBundle.Access access : this.accessors) {
/* 169 */         if (access.bounds_bufferGrid.contains(position_bufferGrid)) {
/* 170 */           return true;
/*     */         }
/*     */       } 
/* 173 */       return false;
/*     */     }
/*     */   } public static final class TrackedBuffer extends Record implements MemInstrument { @Nonnull private final NBufferBundle.Tracker tracker; @Nonnull private final NBuffer buffer; public TrackedBuffer(@Nonnull NBufferBundle.Tracker tracker, @Nonnull NBuffer buffer) { this.tracker = tracker; this.buffer = buffer; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #17	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$Grid$TrackedBuffer; } public final int hashCode() { // Byte code:
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
/*     */       //   0	8	1	o	Ljava/lang/Object; } @Nonnull public NBufferBundle.Tracker tracker() { return this.tracker; }
/*     */     @Nonnull public NBuffer buffer() { return this.buffer; }
/*     */     @Nonnull public MemInstrument.Report getMemoryUsage() { long size_bytes = 16L + this.tracker.getMemoryUsage().size_bytes() + this.buffer.getMemoryUsage().size_bytes(); return new MemInstrument.Report(size_bytes); } }
/* 178 */   public static class Tracker implements MemInstrument { public final int INITIAL_STAGE_INDEX = -1;
/*     */     
/* 180 */     public int stageIndex = -1;
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public MemInstrument.Report getMemoryUsage() {
/* 185 */       return new MemInstrument.Report(4L);
/*     */     } }
/*     */   public static class Access implements MemInstrument { private final NBufferBundle.Grid grid; private final Bounds3i bounds_bufferGrid;
/*     */     private final NBufferBundle.Grid.TrackedBuffer[] buffers;
/*     */     private boolean isClosed;
/*     */     
/*     */     public static class View { private final NBufferBundle.Access access;
/*     */       private final Bounds3i bounds_bufferGrid;
/*     */       
/*     */       private View(@Nonnull NBufferBundle.Access access, @Nonnull Bounds3i bounds_bufferGrid) {
/* 195 */         assert access.bounds_bufferGrid.contains(bounds_bufferGrid);
/*     */         
/* 197 */         this.access = access;
/* 198 */         this.bounds_bufferGrid = bounds_bufferGrid;
/*     */       }
/*     */       
/*     */       @Nonnull
/*     */       public NBufferBundle.Grid.TrackedBuffer getBuffer(@Nonnull Vector3i position_bufferGrid) {
/* 203 */         assert !this.access.isClosed;
/* 204 */         assert this.bounds_bufferGrid.contains(position_bufferGrid);
/*     */         
/* 206 */         return this.access.getBuffer(position_bufferGrid);
/*     */       }
/*     */       
/*     */       @Nonnull
/*     */       public Bounds3i getBounds_bufferGrid() {
/* 211 */         return this.bounds_bufferGrid.clone();
/*     */       } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Access(@Nonnull NBufferBundle.Grid grid, @Nonnull Bounds3i bounds_bufferGrid) {
/* 222 */       assert bounds_bufferGrid.isCorrect();
/*     */       
/* 224 */       this.grid = grid;
/* 225 */       this.bounds_bufferGrid = bounds_bufferGrid.clone();
/* 226 */       this.bounds_bufferGrid.min.y = 0;
/* 227 */       this.bounds_bufferGrid.max.y = 40;
/*     */       
/* 229 */       Vector3i boundsSize_bufferGrid = this.bounds_bufferGrid.getSize();
/* 230 */       int bufferCount = boundsSize_bufferGrid.x * boundsSize_bufferGrid.y * boundsSize_bufferGrid.z;
/* 231 */       this.buffers = new NBufferBundle.Grid.TrackedBuffer[bufferCount];
/*     */       
/* 233 */       this.isClosed = false;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public View createView(@Nonnull Bounds3i viewBounds_bufferGrid) {
/* 238 */       assert this.bounds_bufferGrid.contains(viewBounds_bufferGrid);
/*     */       
/* 240 */       return new View(this, viewBounds_bufferGrid);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public View createView() {
/* 245 */       return new View(this, this.bounds_bufferGrid);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public NBufferBundle.Grid.TrackedBuffer getBuffer(@Nonnull Vector3i position_bufferGrid) {
/* 250 */       assert !this.isClosed;
/* 251 */       assert this.bounds_bufferGrid.contains(position_bufferGrid);
/*     */       
/* 253 */       int index = GridUtils.toIndexFromPositionYXZ(position_bufferGrid, this.bounds_bufferGrid);
/* 254 */       return this.buffers[index];
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Bounds3i getBounds_bufferGrid() {
/* 259 */       return this.bounds_bufferGrid.clone();
/*     */     }
/*     */     
/*     */     public void close() {
/* 263 */       this.grid.accessors.remove(this);
/* 264 */       this.isClosed = true;
/* 265 */       Arrays.fill((Object[])this.buffers, (Object)null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public MemInstrument.Report getMemoryUsage() {
/* 272 */       long size_bytes = 8L + this.bounds_bufferGrid.getMemoryUsage().size_bytes();
/* 273 */       return new MemInstrument.Report(size_bytes);
/*     */     }
/*     */     
/*     */     private void loadGrid() {
/* 277 */       assert !this.isClosed;
/* 278 */       assert this.bounds_bufferGrid.min.y == 0 && this.bounds_bufferGrid.max.y == 40;
/*     */       
/* 280 */       Vector3i position_bufferGrid = this.bounds_bufferGrid.min.clone();
/* 281 */       position_bufferGrid.setY(0);
/*     */       
/* 283 */       NBufferBundle.Grid.TrackedBuffer[] trackedBuffersOutput = new NBufferBundle.Grid.TrackedBuffer[40];
/*     */ 
/*     */       
/* 286 */       for (position_bufferGrid.z = this.bounds_bufferGrid.min.z; position_bufferGrid.z < this.bounds_bufferGrid.max.z; position_bufferGrid.z++) {
/* 287 */         for (position_bufferGrid.x = this.bounds_bufferGrid.min.x; position_bufferGrid.x < this.bounds_bufferGrid.max.x; position_bufferGrid.x++) {
/* 288 */           position_bufferGrid.setY(0);
/*     */           
/* 290 */           this.grid.ensureBufferColumnExists(position_bufferGrid, trackedBuffersOutput);
/* 291 */           int i = 0;
/* 292 */           for (position_bufferGrid.y = 0; position_bufferGrid.y < 40; position_bufferGrid.y++) {
/* 293 */             position_bufferGrid.dropHash();
/*     */             
/* 295 */             int index = GridUtils.toIndexFromPositionYXZ(position_bufferGrid, this.bounds_bufferGrid);
/* 296 */             this.buffers[index] = trackedBuffersOutput[i];
/* 297 */             i++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } }
/*     */    public static class View { private final NBufferBundle.Access access; private final Bounds3i bounds_bufferGrid; private View(@Nonnull NBufferBundle.Access access, @Nonnull Bounds3i bounds_bufferGrid) { assert access.bounds_bufferGrid.contains(bounds_bufferGrid); this.access = access;
/*     */       this.bounds_bufferGrid = bounds_bufferGrid; } @Nonnull public NBufferBundle.Grid.TrackedBuffer getBuffer(@Nonnull Vector3i position_bufferGrid) { assert !this.access.isClosed;
/*     */       assert this.bounds_bufferGrid.contains(position_bufferGrid);
/*     */       return this.access.getBuffer(position_bufferGrid); }
/*     */     @Nonnull public Bounds3i getBounds_bufferGrid() { return this.bounds_bufferGrid.clone(); } }
/* 307 */   public NBufferBundle() { this.grids = new HashMap<>(); }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Grid createGrid(@Nonnull NBufferType bufferType, int capacity) {
/* 312 */     assert capacity >= 0;
/* 313 */     assert !this.grids.containsKey(bufferType);
/* 314 */     assert !existingGridHasBufferTypeIndex(bufferType.index);
/*     */     
/* 316 */     Grid grid = new Grid(bufferType, capacity);
/* 317 */     this.grids.put(bufferType, grid);
/*     */     
/* 319 */     return grid;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Access createBufferAccess(@Nonnull NBufferType bufferType, @Nonnull Bounds3i bounds_bufferGrid) {
/* 324 */     assert bounds_bufferGrid.isCorrect();
/*     */     
/* 326 */     return getGrid(bufferType).openAccess(bounds_bufferGrid);
/*     */   }
/*     */   
/*     */   public void closeALlAccesses() {
/* 330 */     for (Grid grid : this.grids.values()) {
/* 331 */       grid.closeAllAccesses();
/*     */     }
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Grid getGrid(@Nonnull NBufferType contentType) {
/* 337 */     Grid grid = this.grids.get(contentType);
/* 338 */     assert grid != null;
/*     */     
/* 340 */     return grid;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MemInstrument.Report getMemoryUsage() {
/* 346 */     long size_bytes = 16L;
/* 347 */     for (Map.Entry<NBufferType, Grid> entry : this.grids.entrySet()) {
/* 348 */       size_bytes += ((Grid)entry.getValue()).getMemoryUsage().size_bytes();
/*     */     }
/* 350 */     return new MemInstrument.Report(size_bytes);
/*     */   }
/*     */   
/*     */   private boolean existingGridHasBufferTypeIndex(int bufferTypeIndex) {
/* 354 */     for (Grid grid : this.grids.values()) {
/* 355 */       if (grid.bufferType.index == bufferTypeIndex) {
/* 356 */         return true;
/*     */       }
/*     */     } 
/* 359 */     return false;
/*     */   } public static class MemoryReport { public final List<GridEntry> gridEntries;
/*     */     public static final class GridEntry extends Record { private final MemInstrument.Report report; private final int bufferCount; @Nonnull
/*     */       private final NBufferType bufferType;
/* 363 */       public GridEntry(MemInstrument.Report report, int bufferCount, @Nonnull NBufferType bufferType) { this.report = report; this.bufferCount = bufferCount; this.bufferType = bufferType; } public final String toString() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;)Ljava/lang/String;
/*     */         //   6: areturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #363	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry; } public final int hashCode() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;)I
/*     */         //   6: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #363	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry; } public final boolean equals(Object o) { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: aload_1
/*     */         //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;Ljava/lang/Object;)Z
/*     */         //   7: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #363	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	8	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;
/* 363 */         //   0	8	1	o	Ljava/lang/Object; } public MemInstrument.Report report() { return this.report; } public int bufferCount() { return this.bufferCount; } @Nonnull public NBufferType bufferType() { return this.bufferType; }
/*     */        @Nonnull
/*     */       public String toString(int indentation) {
/* 366 */         long size_mb = this.report.size_bytes() / 1000000L;
/*     */         
/* 368 */         StringBuilder builder = new StringBuilder();
/* 369 */         builder.append("\t".repeat(indentation)).append(this.bufferType.name + " Grid (Index ").append((bufferType()).index).append("):\n");
/* 370 */         builder.append("\t".repeat(indentation + 1)).append("Memory Footprint: ").append(size_mb).append(" mb\n");
/* 371 */         builder.append("\t".repeat(indentation + 1)).append("Buffer Count: ").append(this.bufferCount).append("\n");
/*     */         
/* 373 */         return builder.toString();
/*     */       } }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MemoryReport() {
/* 380 */       this.gridEntries = new ArrayList<>();
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 385 */       this.gridEntries.sort((o1, o2) -> ((o1.bufferType()).index > (o2.bufferType()).index) ? 1 : (((o1.bufferType()).index < (o2.bufferType()).index) ? -1 : 0));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 395 */       StringBuilder builder = new StringBuilder();
/*     */       
/* 397 */       long total_mb = 0L;
/* 398 */       for (GridEntry entry : this.gridEntries) {
/* 399 */         total_mb += entry.report.size_bytes();
/*     */       }
/* 401 */       total_mb /= 1000000L;
/*     */       
/* 403 */       builder.append("Memory Usage Report\n");
/* 404 */       builder.append("Buffers Memory Usage: ").append(total_mb).append(" mb\n");
/*     */       
/* 406 */       for (GridEntry entry : this.gridEntries) {
/* 407 */         builder.append(entry.toString(1));
/*     */       }
/*     */       
/* 410 */       return builder.toString();
/*     */     } }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MemoryReport createMemoryReport() {
/* 416 */     MemoryReport memoryReport = new MemoryReport();
/*     */     
/* 418 */     for (Grid grid : this.grids.values()) {
/* 419 */       MemInstrument.Report gridUsage = grid.getMemoryUsage();
/* 420 */       int gridBufferCount = grid.buffers.size();
/*     */       
/* 422 */       memoryReport.gridEntries.add(new MemoryReport.GridEntry(gridUsage, gridBufferCount, grid.bufferType));
/*     */     } 
/*     */     
/* 425 */     return memoryReport;
/*     */   }
/*     */   
/*     */   public static final class GridEntry extends Record {
/*     */     private final MemInstrument.Report report;
/*     */     private final int bufferCount;
/*     */     @Nonnull
/*     */     private final NBufferType bufferType;
/*     */     
/*     */     public GridEntry(MemInstrument.Report report, int bufferCount, @Nonnull NBufferType bufferType) {
/*     */       this.report = report;
/*     */       this.bufferCount = bufferCount;
/*     */       this.bufferType = bufferType;
/*     */     }
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #363	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #363	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;
/*     */     }
/*     */     
/*     */     public final boolean equals(Object o) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #363	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;
/*     */       //   0	8	1	o	Ljava/lang/Object;
/*     */     }
/*     */     
/*     */     public MemInstrument.Report report() {
/*     */       return this.report;
/*     */     }
/*     */     
/*     */     public int bufferCount() {
/*     */       return this.bufferCount;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public NBufferType bufferType() {
/*     */       return this.bufferType;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public String toString(int indentation) {
/*     */       long size_mb = this.report.size_bytes() / 1000000L;
/*     */       StringBuilder builder = new StringBuilder();
/*     */       builder.append("\t".repeat(indentation)).append(this.bufferType.name + " Grid (Index ").append((bufferType()).index).append("):\n");
/*     */       builder.append("\t".repeat(indentation + 1)).append("Memory Footprint: ").append(size_mb).append(" mb\n");
/*     */       builder.append("\t".repeat(indentation + 1)).append("Buffer Count: ").append(this.bufferCount).append("\n");
/*     */       return builder.toString();
/*     */     }
/*     */   } }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\bufferbundle\NBufferBundle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */