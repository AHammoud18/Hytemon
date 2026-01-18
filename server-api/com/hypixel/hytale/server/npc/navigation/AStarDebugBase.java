/*     */ package com.hypixel.hytale.server.npc.navigation;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.npc.movement.controllers.MotionController;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AStarDebugBase
/*     */ {
/*     */   public static final char CENTER = ' ';
/*     */   public static final char CROSS = '┼';
/*     */   public static final char HLINE = '─';
/*     */   public static final char VLINE = '│';
/*     */   public static final char OPEN_NODE = '◆';
/*     */   public static final char CLOSED_NODE = '◇';
/*     */   public static final char CLOSED_PATH_NODE = '◯';
/*     */   public static final char OPEN_PATH_NODE = '◉';
/*     */   public static final char BLOCKED_NODE = '×';
/*     */   public static final char START_POSITION = '@';
/*     */   public static final char END_POSITION = 'Ω';
/*  33 */   public static final String BORDER_PATTERN = "─┼".repeat(1025);
/*  34 */   public static final String CENTER_PATTERN = " │".repeat(1025);
/*     */   
/*     */   protected AStarBase aStarBase;
/*     */   protected HytaleLogger logger;
/*     */   protected HytaleLogger.Api loggerInfo;
/*     */   
/*     */   public AStarDebugBase(AStarBase base, @Nonnull HytaleLogger logger) {
/*  41 */     this.aStarBase = base;
/*  42 */     this.logger = logger;
/*  43 */     this.loggerInfo = logger.at(Level.INFO);
/*     */   }
/*     */   
/*     */   public void dumpOpens(MotionController controller) {
/*  47 */     int openCount = this.aStarBase.getOpenCount();
/*  48 */     List<AStarNode> openNodes = this.aStarBase.getOpenNodes();
/*  49 */     int maxLength = -1;
/*     */     int i;
/*  51 */     for (i = 0; i < openCount; i++) {
/*  52 */       int length = ((AStarNode)openNodes.get(i)).getLength();
/*  53 */       if (length > maxLength) maxLength = length;
/*     */     
/*     */     } 
/*  56 */     this.loggerInfo.log("== A* iter=%s opens=%s total=%s maxLength=%s %s", Integer.valueOf(this.aStarBase.getIterations()), Integer.valueOf(openCount), Integer.valueOf(this.aStarBase.getVisitedBlocks().size()), Integer.valueOf(maxLength), getExtraLogString(controller));
/*     */     
/*  58 */     for (i = 0; i < openCount; i++) {
/*  59 */       this.loggerInfo.log("%2d %s", i, ((AStarNode)openNodes.get(i)).toString());
/*     */     }
/*     */   }
/*     */   
/*     */   public void dumpPath() {
/*  64 */     AStarNode node = this.aStarBase.getPath();
/*     */     
/*  66 */     this.loggerInfo.log("== A* Path iter=%s opens=%s total=%s", Integer.valueOf(this.aStarBase.getIterations()), Integer.valueOf(this.aStarBase.getOpenCount()), Integer.valueOf(this.aStarBase.getVisitedBlocks().size()));
/*  67 */     while (node != null) {
/*  68 */       this.loggerInfo.log("%s", node.toString());
/*  69 */       node = node.getNextPathNode();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dumpMap(boolean drawPath, MotionController controller) {
/*  74 */     int openCount = this.aStarBase.getOpenCount();
/*  75 */     List<AStarNode> openNodes = this.aStarBase.getOpenNodes();
/*  76 */     AStarNode start = null;
/*  77 */     boolean finalPath = false;
/*     */     
/*  79 */     if (drawPath) {
/*  80 */       AStarNode path = this.aStarBase.getPath();
/*  81 */       if (path != null) {
/*  82 */         start = path;
/*  83 */         finalPath = true;
/*  84 */       } else if (openCount > 0) {
/*  85 */         start = openNodes.get(openCount - 1);
/*     */       } 
/*     */     } 
/*  88 */     dumpMap(start, finalPath, controller);
/*     */   }
/*     */   public void dumpMap(@Nullable AStarNode pathNode, boolean isFinalPath, MotionController controller) {
/*     */     int minX, maxX, minZ, maxZ, maxLength;
/*  92 */     long startPositionIndex = this.aStarBase.getStartPositionIndex();
/*  93 */     Long2ObjectMap<AStarNode> visitedBlocks = this.aStarBase.getVisitedBlocks();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     int s = AStarBase.xFromIndex(startPositionIndex);
/* 101 */     int e = getDumpMapRegionX(s);
/*     */     
/* 103 */     if (s < e) {
/* 104 */       minX = s;
/* 105 */       maxX = e;
/*     */     } else {
/* 107 */       minX = e;
/* 108 */       maxX = s;
/*     */     } 
/*     */     
/* 111 */     s = AStarBase.zFromIndex(startPositionIndex);
/* 112 */     e = getDumpMapRegionZ(s);
/*     */     
/* 114 */     if (s < e) {
/* 115 */       minZ = s;
/* 116 */       maxZ = e;
/*     */     } else {
/* 118 */       minZ = e;
/* 119 */       maxZ = s;
/*     */     } 
/*     */     
/* 122 */     ObjectIterator<Long2ObjectMap.Entry<AStarNode>> fastIterator = Long2ObjectMaps.fastIterator(visitedBlocks);
/*     */     
/* 124 */     while (fastIterator.hasNext()) {
/* 125 */       AStarNode node = (AStarNode)((Long2ObjectMap.Entry)fastIterator.next()).getValue();
/* 126 */       int x = AStarBase.xFromIndex(node.getPositionIndex());
/* 127 */       int z = AStarBase.zFromIndex(node.getPositionIndex());
/*     */       
/* 129 */       if (x < minX) minX = x; 
/* 130 */       if (x > maxX) maxX = x; 
/* 131 */       if (z < minZ) minZ = z; 
/* 132 */       if (z > maxZ) maxZ = z;
/*     */     
/*     */     } 
/* 135 */     int rows = maxZ - minZ + 1;
/* 136 */     int columns = maxX - minX + 1;
/* 137 */     int offset = minX & 0x1;
/* 138 */     boolean evenStart = ((minZ & 0x1) == 0);
/*     */     
/* 140 */     String first = "'" + (evenStart ? CENTER_PATTERN : BORDER_PATTERN).substring(offset, offset + columns) + "'";
/* 141 */     String second = "'" + (evenStart ? BORDER_PATTERN : CENTER_PATTERN).substring(offset, offset + columns) + "'";
/* 142 */     StringBuilder[] map = new StringBuilder[rows];
/*     */     
/* 144 */     for (int i = 0; i < rows; i += 2) {
/* 145 */       map[i] = new StringBuilder(first);
/* 146 */       if (i + 1 < rows) map[i + 1] = new StringBuilder(second);
/*     */     
/*     */     } 
/* 149 */     fastIterator = Long2ObjectMaps.fastIterator(visitedBlocks);
/*     */     
/* 151 */     while (fastIterator.hasNext()) {
/* 152 */       AStarNode node = (AStarNode)((Long2ObjectMap.Entry)fastIterator.next()).getValue();
/*     */       
/* 154 */       plot(node.getPositionIndex(), node.isInvalid() ? 215 : (node.isOpen() ? 9670 : 9671), map, minX, minZ);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 159 */     int openCount = this.aStarBase.getOpenCount();
/* 160 */     if (pathNode != null) {
/* 161 */       maxLength = pathNode.getLength();
/* 162 */       while (pathNode != null) {
/* 163 */         plot(pathNode.getPositionIndex(), pathNode.isOpen() ? 9673 : 9711, map, minX, minZ);
/* 164 */         pathNode = isFinalPath ? pathNode.getNextPathNode() : pathNode.getPredecessor();
/*     */       } 
/*     */     } else {
/* 167 */       List<AStarNode> openNodes = this.aStarBase.getOpenNodes();
/* 168 */       int index = openCount;
/* 169 */       maxLength = 0;
/* 170 */       while (--index >= 0) {
/* 171 */         int pos = openCount - index;
/* 172 */         if (pos > 51)
/* 173 */           break;  plot(((AStarNode)openNodes.get(index)).getPositionIndex(), (char)((pos >= 26) ? (pos - 26 + 97) : (pos + 65)), map, minX, minZ);
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     plot(startPositionIndex, '@', map, minX, minZ);
/* 178 */     drawMapFinish(map, minX, minZ);
/*     */     
/* 180 */     this.loggerInfo.log("== A* iter=%s, opens=%s total=%s maxLength=%s %s", Integer.valueOf(this.aStarBase.getIterations()), Integer.valueOf(openCount), Integer.valueOf(visitedBlocks.size()), Integer.valueOf(maxLength), getExtraLogString(controller));
/* 181 */     for (StringBuilder stringBuilder : map) {
/* 182 */       this.loggerInfo.log(stringBuilder.toString());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void plot(long positionIndex, char character, @Nonnull StringBuilder[] map, int minX, int minZ) {
/* 187 */     int row = AStarBase.zFromIndex(positionIndex) - minZ;
/* 188 */     int column = AStarBase.xFromIndex(positionIndex) - minX + 1;
/* 189 */     map[row].setCharAt(column, character);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawMapFinish(StringBuilder[] map, int minX, int minZ) {}
/*     */   
/*     */   protected int getDumpMapRegionZ(int def) {
/* 196 */     return def;
/*     */   }
/*     */   
/*     */   protected int getDumpMapRegionX(int def) {
/* 200 */     return def;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected String getExtraLogString(MotionController controller) {
/* 205 */     return "";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\navigation\AStarDebugBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */