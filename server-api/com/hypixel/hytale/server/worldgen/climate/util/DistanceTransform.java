/*     */ package com.hypixel.hytale.server.worldgen.climate.util;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Arrays;
/*     */ import java.util.PriorityQueue;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class DistanceTransform {
/*  12 */   private static final IntArrayList EMPTY_LIST = new IntArrayList();
/*  13 */   private static final int[] DX = new int[] { -1, 1, 0, 0, -1, -1, 1, 1 };
/*  14 */   private static final int[] DY = new int[] { 0, 0, -1, 1, -1, 1, -1, 1 };
/*  15 */   private static final double[] COST = new double[] { 1.0D, 1.0D, 1.0D, 1.0D, 
/*     */       
/*  17 */       Math.sqrt(2.0D), Math.sqrt(2.0D), Math.sqrt(2.0D), Math.sqrt(2.0D) };
/*     */ 
/*     */   
/*     */   public static void apply(@Nonnull IntMap source, @Nonnull DoubleMap dest, double radius) {
/*  21 */     if (radius <= 0.0D) throw new IllegalArgumentException("radius must be > 0");
/*     */     
/*  23 */     int width = source.width;
/*  24 */     int height = source.height;
/*  25 */     int size = width * height;
/*     */     
/*  27 */     Int2ObjectOpenHashMap<IntArrayList> regions = new Int2ObjectOpenHashMap();
/*  28 */     Int2ObjectOpenHashMap<IntArrayList> boundaries = new Int2ObjectOpenHashMap();
/*     */ 
/*     */     
/*  31 */     for (int y = 0; y < height; y++) {
/*  32 */       for (int x = 0; x < width; x++) {
/*  33 */         int index = source.index(x, y);
/*  34 */         int value = source.at(index);
/*  35 */         ((IntArrayList)regions.computeIfAbsent(value, k -> new IntArrayList())).add(index);
/*     */ 
/*     */         
/*  38 */         for (int i = 0; i < 4; i++) {
/*  39 */           int nx = x + DX[i];
/*  40 */           int ny = y + DY[i];
/*     */           
/*  42 */           if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
/*  43 */             int neighborIndex = source.index(nx, ny);
/*  44 */             if (source.at(neighborIndex) != value) {
/*  45 */               ((IntArrayList)boundaries.computeIfAbsent(value, k -> new IntArrayList())).add(index);
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  53 */     double[] dist = new double[size];
/*  54 */     PriorityQueue<Node> queue = new PriorityQueue<>(Node::sort);
/*     */ 
/*     */     
/*  57 */     for (ObjectIterator<Int2ObjectMap.Entry<IntArrayList>> objectIterator = regions.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<IntArrayList> entry = objectIterator.next();
/*  58 */       int id = entry.getIntKey();
/*  59 */       IntArrayList region = (IntArrayList)entry.getValue();
/*  60 */       IntArrayList boundary = (IntArrayList)boundaries.getOrDefault(id, EMPTY_LIST);
/*     */ 
/*     */       
/*  63 */       if (boundary.isEmpty()) {
/*  64 */         for (int j = 0; j < region.size(); j++) {
/*  65 */           dest.set(region.getInt(j), 1.0D);
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/*  70 */       Arrays.fill(dist, radius);
/*     */       int i;
/*  72 */       for (i = 0; i < boundary.size(); i++) {
/*  73 */         int index = boundary.getInt(i);
/*  74 */         dist[index] = 0.0D;
/*  75 */         queue.offer(new Node(index, 0.0D));
/*     */       } 
/*     */       
/*  78 */       while (!queue.isEmpty()) {
/*  79 */         Node node = queue.poll();
/*  80 */         int index = node.index;
/*  81 */         if (node.distance > dist[index])
/*     */           continue; 
/*  83 */         int cx = index % width;
/*  84 */         int cy = index / width;
/*     */         
/*  86 */         for (int j = 0; j < DX.length; j++) {
/*  87 */           int nx = cx + DX[j];
/*  88 */           int ny = cy + DY[j];
/*     */           
/*  90 */           if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
/*     */ 
/*     */ 
/*     */             
/*  94 */             int neighborIndex = source.index(nx, ny);
/*  95 */             int neighborId = source.at(neighborIndex);
/*  96 */             if (neighborId == id) {
/*     */ 
/*     */ 
/*     */               
/* 100 */               double distance = node.distance + COST[j];
/* 101 */               if (distance < dist[neighborIndex]) {
/* 102 */                 dist[neighborIndex] = distance;
/* 103 */                 queue.offer(new Node(neighborIndex, distance));
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 109 */       for (i = 0; i < region.size(); i++) {
/* 110 */         int index = region.getInt(i);
/* 111 */         double value = MathUtil.clamp(dist[index], 0.0D, radius);
/* 112 */         dest.set(index, value / radius);
/*     */       }  }
/*     */   
/*     */   }
/*     */   private static final class Node extends Record { private final int index; private final double distance;
/* 117 */     private Node(int index, double distance) { this.index = index; this.distance = distance; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/worldgen/climate/util/DistanceTransform$Node;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #117	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 117 */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/climate/util/DistanceTransform$Node; } public int index() { return this.index; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/worldgen/climate/util/DistanceTransform$Node;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #117	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/climate/util/DistanceTransform$Node; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/worldgen/climate/util/DistanceTransform$Node;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #117	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/worldgen/climate/util/DistanceTransform$Node;
/* 117 */       //   0	8	1	o	Ljava/lang/Object; } public double distance() { return this.distance; }
/*     */      public static int sort(Node a, Node b) {
/* 119 */       return Double.compare(a.distance, b.distance);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\climat\\util\DistanceTransform.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */