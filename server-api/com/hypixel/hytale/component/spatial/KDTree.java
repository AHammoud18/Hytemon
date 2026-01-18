/*     */ package com.hypixel.hytale.component.spatial;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KDTree<T>
/*     */   implements SpatialStructure<T>
/*     */ {
/*     */   @Nonnull
/*  22 */   private final List<Node<T>> nodePool = (List<Node<T>>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   private int nodePoolIndex = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  33 */   private final List<List<T>> dataListPool = (List<List<T>>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   private int dataListPoolIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int size;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private final Predicate<T> collectionFilter;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Node<T> root;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KDTree(@Nonnull Predicate<T> collectionFilter) {
/*  64 */     this.collectionFilter = collectionFilter;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  69 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebuild(@Nonnull SpatialData<T> spatialData) {
/*  74 */     this.root = null;
/*  75 */     this.size = 0;
/*     */     
/*  77 */     int spatialDataSize = spatialData.size();
/*  78 */     if (spatialDataSize == 0) {
/*     */       return;
/*     */     }
/*  81 */     for (int i = 0; i < this.dataListPoolIndex; i++) {
/*  82 */       ((List)this.dataListPool.get(i)).clear();
/*     */     }
/*  84 */     this.nodePoolIndex = 0;
/*  85 */     this.dataListPoolIndex = 0;
/*     */     
/*  87 */     spatialData.sortMorton();
/*     */ 
/*     */     
/*  90 */     int mid = spatialDataSize / 2;
/*  91 */     int sortedIndex = spatialData.getSortedIndex(mid);
/*  92 */     Vector3d vector = spatialData.getVector(sortedIndex);
/*  93 */     T data = spatialData.getData(sortedIndex);
/*     */     
/*  95 */     List<T> list = getPooledDataList();
/*  96 */     list.add(data);
/*     */ 
/*     */     
/*  99 */     int left = mid - 1;
/* 100 */     while (left >= 0) {
/* 101 */       int leftSortedIndex = spatialData.getSortedIndex(left);
/* 102 */       Vector3d leftVector = spatialData.getVector(leftSortedIndex);
/* 103 */       if (!leftVector.equals(vector))
/*     */         break; 
/* 105 */       T leftData = spatialData.getData(leftSortedIndex);
/* 106 */       list.add(leftData);
/* 107 */       left--;
/*     */     } 
/*     */     
/* 110 */     int right = mid + 1;
/* 111 */     while (right < spatialDataSize) {
/* 112 */       int rightSortedIndex = spatialData.getSortedIndex(right);
/* 113 */       Vector3d rightVector = spatialData.getVector(rightSortedIndex);
/* 114 */       if (!rightVector.equals(vector))
/*     */         break; 
/* 116 */       T rightData = spatialData.getData(rightSortedIndex);
/* 117 */       list.add(rightData);
/* 118 */       right++;
/*     */     } 
/*     */     
/* 121 */     this.root = getPooledNode(vector, list);
/*     */     
/* 123 */     if (0 < left + 1) build0(spatialData, 0, left + 1); 
/* 124 */     if (right < spatialDataSize) build0(spatialData, right, spatialDataSize);
/*     */     
/* 126 */     this.size = spatialDataSize;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T closest(@Nonnull Vector3d point) {
/* 132 */     ClosestState<T> closestState = new ClosestState<>(null, Double.MAX_VALUE);
/* 133 */     closest0(closestState, this.root, point, 0);
/*     */     
/* 135 */     if (closestState.node == null) return null; 
/* 136 */     return closestState.node.data.getFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   public void collect(@Nonnull Vector3d center, double radius, @Nonnull List<T> results) {
/* 141 */     double distanceSq = radius * radius;
/* 142 */     collect0(results, this.root, center, distanceSq, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void collectCylinder(@Nonnull Vector3d center, double radius, double height, @Nonnull List<T> results) {
/* 147 */     double radiusSq = radius * radius;
/* 148 */     double halfHeight = height / 2.0D;
/* 149 */     collectCylinder0(results, this.root, center, radiusSq, halfHeight, radius, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void collectBox(@Nonnull Vector3d min, @Nonnull Vector3d max, @Nonnull List<T> results) {
/* 154 */     collectBox0(results, this.root, min, max, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void ordered(@Nonnull Vector3d center, double radius, @Nonnull List<T> results) {
/* 159 */     double distanceSq = radius * radius;
/* 160 */     ObjectArrayList<OrderedEntry<T>> entryResults = new ObjectArrayList();
/* 161 */     ordered0((List<OrderedEntry<T>>)entryResults, this.root, center, distanceSq, 0);
/*     */ 
/*     */     
/* 164 */     entryResults.sort(Comparator.comparingDouble(o -> o.distanceSq));
/*     */     
/* 166 */     for (ObjectListIterator<OrderedEntry<T>> objectListIterator = entryResults.iterator(); objectListIterator.hasNext(); ) { OrderedEntry<T> entry = objectListIterator.next();
/* 167 */       for (int i = 0, bound = entry.values.size(); i < bound; i++) {
/* 168 */         T data = entry.values.get(i);
/* 169 */         if (this.collectionFilter.test(data)) {
/* 170 */           results.add(data);
/*     */         }
/*     */       }  }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void ordered3DAxis(@Nonnull Vector3d center, double xSearchRadius, double YSearchRadius, double zSearchRadius, @Nonnull List<T> results) {
/* 178 */     ObjectArrayList<OrderedEntry<T>> entryResults = new ObjectArrayList();
/* 179 */     _internal_ordered3DAxis((List<OrderedEntry<T>>)entryResults, this.root, center, xSearchRadius, YSearchRadius, zSearchRadius, 0);
/* 180 */     entryResults.sort(Comparator.comparingDouble(o -> o.distanceSq));
/*     */     
/* 182 */     for (ObjectListIterator<OrderedEntry<T>> objectListIterator = entryResults.iterator(); objectListIterator.hasNext(); ) { OrderedEntry<T> entry = objectListIterator.next();
/* 183 */       for (int i = 0, bound = entry.values.size(); i < bound; i++) {
/* 184 */         T data = entry.values.get(i);
/* 185 */         if (this.collectionFilter.test(data)) {
/* 186 */           results.add(data);
/*     */         }
/*     */       }  }
/*     */   
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String dump() {
/* 194 */     return "KDTree(size=" + this.size + ")\n" + ((this.root == null) ? null : this.root.dump(0));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private Node<T> getPooledNode(Vector3d vector, List<T> data) {
/* 199 */     if (this.nodePoolIndex < this.nodePool.size()) {
/* 200 */       Node<T> node1 = this.nodePool.get(this.nodePoolIndex++);
/* 201 */       node1.reset(vector, data);
/* 202 */       return node1;
/*     */     } 
/* 204 */     Node<T> node = new Node<>(vector, data);
/* 205 */     this.nodePool.add(node);
/* 206 */     this.nodePoolIndex++;
/* 207 */     return node;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<T> getPooledDataList() {
/* 212 */     if (this.dataListPoolIndex < this.dataListPool.size()) {
/* 213 */       return this.dataListPool.get(this.dataListPoolIndex++);
/*     */     }
/* 215 */     ObjectArrayList<T> set = new ObjectArrayList(1);
/* 216 */     this.dataListPool.add(set);
/* 217 */     this.dataListPoolIndex++;
/* 218 */     return (List<T>)set;
/*     */   }
/*     */ 
/*     */   
/*     */   private void build0(@Nonnull SpatialData<T> spatialData, int start, int end) {
/* 223 */     int mid = (start + end) / 2;
/* 224 */     int sortedIndex = spatialData.getSortedIndex(mid);
/* 225 */     Vector3d vector = spatialData.getVector(sortedIndex);
/* 226 */     T data = spatialData.getData(sortedIndex);
/*     */     
/* 228 */     List<T> list = getPooledDataList();
/* 229 */     list.add(data);
/*     */ 
/*     */     
/* 232 */     int left = mid - 1;
/* 233 */     while (left >= start) {
/* 234 */       int leftSortedIndex = spatialData.getSortedIndex(left);
/* 235 */       Vector3d leftVector = spatialData.getVector(leftSortedIndex);
/* 236 */       if (!leftVector.equals(vector))
/*     */         break; 
/* 238 */       T leftData = spatialData.getData(leftSortedIndex);
/* 239 */       list.add(leftData);
/* 240 */       left--;
/*     */     } 
/*     */     
/* 243 */     int right = mid + 1;
/* 244 */     while (right < end) {
/* 245 */       int rightSortedIndex = spatialData.getSortedIndex(right);
/* 246 */       Vector3d rightVector = spatialData.getVector(rightSortedIndex);
/* 247 */       if (!rightVector.equals(vector))
/*     */         break; 
/* 249 */       T rightData = spatialData.getData(rightSortedIndex);
/* 250 */       list.add(rightData);
/* 251 */       right++;
/*     */     } 
/*     */ 
/*     */     
/* 255 */     put0(this.root, vector, list, 0);
/*     */     
/* 257 */     if (start < left + 1) build0(spatialData, start, left + 1); 
/* 258 */     if (right < end) build0(spatialData, right, end); 
/*     */   }
/*     */   
/*     */   private void put0(@Nonnull Node<T> node, @Nonnull Vector3d vector, @Nonnull List<T> list, int axis) {
/* 262 */     if (compare(node.vector, vector, axis) < 0) {
/* 263 */       if (node.one == null) {
/* 264 */         node.one = getPooledNode(vector, list);
/*     */       } else {
/* 266 */         put0(node.one, vector, list, (axis + 1) % 3);
/*     */       }
/*     */     
/* 269 */     } else if (node.two == null) {
/* 270 */       node.two = getPooledNode(vector, list);
/*     */     } else {
/* 272 */       put0(node.two, vector, list, (axis + 1) % 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void closest0(@Nonnull ClosestState<T> closestState, @Nullable Node<T> node, @Nonnull Vector3d vector, int depth) {
/* 278 */     if (node == null)
/*     */       return; 
/* 280 */     if (vector.equals(node.vector)) {
/* 281 */       closestState.distanceSq = 0.0D;
/* 282 */       closestState.node = node;
/*     */       
/*     */       return;
/*     */     } 
/* 286 */     int axis = depth % 3;
/* 287 */     int compare = compare(node.vector, vector, axis);
/*     */     
/* 289 */     double distanceSq = node.vector.distanceSquaredTo(vector);
/* 290 */     if (distanceSq < closestState.distanceSq) {
/* 291 */       closestState.node = node;
/* 292 */       closestState.distanceSq = distanceSq;
/*     */     } 
/*     */     
/* 295 */     int newDepth = depth + 1;
/*     */     
/* 297 */     if (compare < 0) {
/* 298 */       closest0(closestState, node.one, vector, newDepth);
/*     */     } else {
/* 300 */       closest0(closestState, node.two, vector, newDepth);
/*     */     } 
/*     */     
/* 303 */     double plane = get(node.vector, axis);
/* 304 */     double component = get(closestState.node.vector, axis);
/*     */ 
/*     */ 
/*     */     
/* 308 */     double planeDistance = Math.abs(component - plane);
/* 309 */     if (planeDistance * planeDistance < closestState.distanceSq) {
/* 310 */       if (compare < 0) {
/* 311 */         closest0(closestState, node.two, vector, newDepth);
/*     */       } else {
/* 313 */         closest0(closestState, node.one, vector, newDepth);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void collect0(@Nonnull List<T> results, @Nullable Node<T> node, @Nonnull Vector3d vector, double distanceSq, int depth) {
/* 319 */     if (node == null)
/*     */       return; 
/* 321 */     int axis = depth % 3;
/* 322 */     int compare = compare(node.vector, vector, axis);
/*     */     
/* 324 */     double nodeDistanceSq = node.vector.distanceSquaredTo(vector);
/* 325 */     if (nodeDistanceSq < distanceSq) {
/* 326 */       for (int i = 0, bound = node.data.size(); i < bound; i++) {
/* 327 */         T data = node.data.get(i);
/* 328 */         if (this.collectionFilter.test(data)) {
/* 329 */           results.add(data);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 334 */     int newDepth = depth + 1;
/*     */     
/* 336 */     if (compare < 0) {
/* 337 */       collect0(results, node.one, vector, distanceSq, newDepth);
/*     */     } else {
/* 339 */       collect0(results, node.two, vector, distanceSq, newDepth);
/*     */     } 
/*     */     
/* 342 */     double plane = get(node.vector, axis);
/* 343 */     double component = get(vector, axis);
/*     */ 
/*     */ 
/*     */     
/* 347 */     double planeDistance = Math.abs(component - plane);
/* 348 */     if (planeDistance * planeDistance < distanceSq) {
/* 349 */       if (compare < 0) {
/* 350 */         collect0(results, node.two, vector, distanceSq, newDepth);
/*     */       } else {
/* 352 */         collect0(results, node.one, vector, distanceSq, newDepth);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void collectCylinder0(@Nonnull List<T> results, @Nullable Node<T> node, @Nonnull Vector3d center, double radiusSq, double halfHeight, double radius, int depth) {
/* 359 */     if (node == null)
/*     */       return; 
/* 361 */     int axis = depth % 3;
/* 362 */     int compare = compare(node.vector, center, axis);
/*     */ 
/*     */     
/* 365 */     double dy = node.vector.y - center.y;
/* 366 */     if (Math.abs(dy) <= halfHeight) {
/* 367 */       double dx = node.vector.x - center.x;
/* 368 */       double dz = node.vector.z - center.z;
/* 369 */       double xzDistanceSq = dx * dx + dz * dz;
/* 370 */       if (xzDistanceSq <= radiusSq) {
/* 371 */         for (int i = 0, bound = node.data.size(); i < bound; i++) {
/* 372 */           T data = node.data.get(i);
/* 373 */           if (this.collectionFilter.test(data)) {
/* 374 */             results.add(data);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 380 */     int newDepth = depth + 1;
/*     */ 
/*     */     
/* 383 */     if (compare < 0) {
/* 384 */       collectCylinder0(results, node.one, center, radiusSq, halfHeight, radius, newDepth);
/*     */     } else {
/* 386 */       collectCylinder0(results, node.two, center, radiusSq, halfHeight, radius, newDepth);
/*     */     } 
/*     */ 
/*     */     
/* 390 */     double plane = get(node.vector, axis);
/* 391 */     double component = get(center, axis);
/* 392 */     double axisRadius = (axis == 2) ? halfHeight : radius;
/*     */ 
/*     */     
/* 395 */     if (Math.abs(component - plane) <= axisRadius) {
/* 396 */       if (compare < 0) {
/* 397 */         collectCylinder0(results, node.two, center, radiusSq, halfHeight, radius, newDepth);
/*     */       } else {
/* 399 */         collectCylinder0(results, node.one, center, radiusSq, halfHeight, radius, newDepth);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void collectBox0(@Nonnull List<T> results, @Nullable Node<T> node, @Nonnull Vector3d min, @Nonnull Vector3d max, int depth) {
/* 406 */     if (node == null)
/*     */       return; 
/* 408 */     int axis = depth % 3;
/*     */ 
/*     */     
/* 411 */     if (node.vector.x >= min.x && node.vector.x <= max.x && node.vector.y >= min.y && node.vector.y <= max.y && node.vector.z >= min.z && node.vector.z <= max.z)
/*     */     {
/*     */       
/* 414 */       for (int i = 0, bound = node.data.size(); i < bound; i++) {
/* 415 */         T data = node.data.get(i);
/* 416 */         if (this.collectionFilter.test(data)) {
/* 417 */           results.add(data);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 422 */     int newDepth = depth + 1;
/* 423 */     double plane = get(node.vector, axis);
/* 424 */     double minComponent = get(min, axis);
/* 425 */     double maxComponent = get(max, axis);
/*     */ 
/*     */ 
/*     */     
/* 429 */     if (maxComponent >= plane) {
/* 430 */       collectBox0(results, node.one, min, max, newDepth);
/*     */     }
/*     */ 
/*     */     
/* 434 */     if (minComponent <= plane) {
/* 435 */       collectBox0(results, node.two, min, max, newDepth);
/*     */     }
/*     */   }
/*     */   
/*     */   private void ordered0(@Nonnull List<OrderedEntry<T>> results, @Nullable Node<T> node, @Nonnull Vector3d vector, double distanceSq, int depth) {
/* 440 */     if (node == null)
/*     */       return; 
/* 442 */     int axis = depth % 3;
/* 443 */     int compare = compare(node.vector, vector, axis);
/*     */     
/* 445 */     double nodeDistanceSq = node.vector.distanceSquaredTo(vector);
/* 446 */     if (nodeDistanceSq < distanceSq) {
/* 447 */       results.add(new OrderedEntry<>(nodeDistanceSq, node.data));
/*     */     }
/*     */     
/* 450 */     int newDepth = depth + 1;
/*     */     
/* 452 */     if (compare < 0) {
/* 453 */       ordered0(results, node.one, vector, distanceSq, newDepth);
/*     */     } else {
/* 455 */       ordered0(results, node.two, vector, distanceSq, newDepth);
/*     */     } 
/*     */     
/* 458 */     double plane = get(node.vector, axis);
/* 459 */     double component = get(vector, axis);
/*     */ 
/*     */ 
/*     */     
/* 463 */     double planeDistance = Math.abs(component - plane);
/* 464 */     if (planeDistance * planeDistance < distanceSq) {
/* 465 */       if (compare < 0) {
/* 466 */         ordered0(results, node.two, vector, distanceSq, newDepth);
/*     */       } else {
/* 468 */         ordered0(results, node.one, vector, distanceSq, newDepth);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void _internal_ordered3DAxis(@Nonnull List<OrderedEntry<T>> results, @Nullable Node<T> node, @Nonnull Vector3d center, double xSearchRadius, double ySearchRadius, double zSearchRadius, int depth) {
/* 474 */     if (node == null)
/*     */       return; 
/* 476 */     int axis = depth % 3;
/*     */ 
/*     */     
/* 479 */     boolean inCuboid = (node.vector.x >= center.x - xSearchRadius && node.vector.x <= center.x + xSearchRadius && node.vector.y >= center.y - ySearchRadius && node.vector.y <= center.y + ySearchRadius && node.vector.z >= center.z - zSearchRadius && node.vector.z <= center.z + zSearchRadius);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 484 */     if (inCuboid) {
/* 485 */       double nodeDistanceSq = node.vector.distanceSquaredTo(center);
/* 486 */       results.add(new OrderedEntry<>(nodeDistanceSq, node.data));
/*     */     } 
/*     */     
/* 489 */     int newDepth = depth + 1;
/* 490 */     int compare = compare(node.vector, center, axis);
/*     */     
/* 492 */     Node<T> primary = (compare < 0) ? node.one : node.two;
/* 493 */     Node<T> secondary = (compare < 0) ? node.two : node.one;
/*     */     
/* 495 */     _internal_ordered3DAxis(results, primary, center, xSearchRadius, ySearchRadius, zSearchRadius, newDepth);
/*     */ 
/*     */     
/* 498 */     double plane = get(node.vector, axis);
/* 499 */     double component = get(center, axis);
/* 500 */     double radius = (axis == 0) ? xSearchRadius : ((axis == 1) ? ySearchRadius : zSearchRadius);
/* 501 */     if (Math.abs(component - plane) <= radius) {
/* 502 */       _internal_ordered3DAxis(results, secondary, center, xSearchRadius, ySearchRadius, zSearchRadius, newDepth);
/*     */     }
/*     */   }
/*     */   
/*     */   private static int compare(@Nonnull Vector3d v1, @Nonnull Vector3d v2, int axis) {
/* 507 */     switch (axis) { case 0: 
/*     */       case 1:
/*     */       
/*     */       case 2:
/* 511 */        }  throw new IllegalArgumentException("Invalid axis: " + axis);
/*     */   }
/*     */ 
/*     */   
/*     */   private static double get(@Nonnull Vector3d v, int axis) {
/* 516 */     switch (axis) { case 0: 
/*     */       case 1:
/*     */       
/*     */       case 2:
/* 520 */        }  throw new IllegalArgumentException("Invalid axis: " + axis);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Node<T>
/*     */   {
/*     */     private Vector3d vector;
/*     */     private List<T> data;
/*     */     @Nullable
/*     */     private Node<T> one;
/*     */     @Nullable
/*     */     private Node<T> two;
/*     */     
/*     */     public Node(Vector3d vector, List<T> data) {
/* 534 */       this.vector = vector;
/* 535 */       this.data = data;
/*     */     }
/*     */     
/*     */     public void reset(Vector3d vector, List<T> data) {
/* 539 */       this.vector = vector;
/* 540 */       this.data = data;
/* 541 */       this.one = null;
/* 542 */       this.two = null;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public String dump(int depth) {
/* 547 */       int nextDepth = depth + 1;
/* 548 */       return "vector=" + String.valueOf(this.vector) + ", data=" + String.valueOf(this.data) + ",\n" + " "
/* 549 */         .repeat(depth) + "one=" + ((this.one == null) ? null : this.one.dump(nextDepth)) + ",\n" + " "
/* 550 */         .repeat(depth) + "two=" + ((this.two == null) ? null : this.two.dump(nextDepth));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ClosestState<T> {
/*     */     private KDTree.Node<T> node;
/*     */     private double distanceSq;
/*     */     
/*     */     public ClosestState(KDTree.Node<T> node, double distanceSq) {
/* 559 */       this.node = node;
/* 560 */       this.distanceSq = distanceSq;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class OrderedEntry<T> {
/*     */     private final double distanceSq;
/*     */     private final List<T> values;
/*     */     
/*     */     public OrderedEntry(double distanceSq, List<T> values) {
/* 569 */       this.distanceSq = distanceSq;
/* 570 */       this.values = values;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\spatial\KDTree.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */