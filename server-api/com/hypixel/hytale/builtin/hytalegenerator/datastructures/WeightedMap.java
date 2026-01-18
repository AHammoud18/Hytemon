/*     */ package com.hypixel.hytale.builtin.hytalegenerator.datastructures;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WeightedMap<T>
/*     */ {
/*     */   @Nonnull
/*     */   private final Set<T> elementSet;
/*     */   @Nonnull
/*     */   private final List<T> elements;
/*     */   @Nonnull
/*     */   private final List<Double> weights;
/*     */   @Nonnull
/*     */   private final Map<T, Integer> indices;
/*  26 */   private double totalWeight = 0.0D;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean immutable = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WeightedMap(@Nonnull WeightedMap<T> other) {
/*  36 */     this.totalWeight = other.totalWeight;
/*  37 */     this.elementSet = new HashSet<>(other.elementSet);
/*  38 */     this.elements = new ArrayList<>(other.elements);
/*  39 */     this.weights = new ArrayList<>(other.weights);
/*  40 */     this.indices = new HashMap<>(other.indices);
/*  41 */     this.immutable = other.immutable;
/*     */   }
/*     */ 
/*     */   
/*     */   public WeightedMap() {
/*  46 */     this(2);
/*     */   }
/*     */ 
/*     */   
/*     */   public WeightedMap(int initialCapacity) {
/*  51 */     this.elementSet = new HashSet<>(initialCapacity);
/*  52 */     this.elements = new ArrayList<>(initialCapacity);
/*  53 */     this.weights = new ArrayList<>(initialCapacity);
/*  54 */     this.indices = new HashMap<>(initialCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WeightedMap<T> add(@Nonnull T element, double weight) {
/*  60 */     if (element == null)
/*  61 */       throw new NullPointerException(); 
/*  62 */     if (this.immutable) {
/*  63 */       throw new IllegalStateException("method can't be called when object is immutable");
/*     */     }
/*  65 */     if (weight < 0.0D)
/*  66 */       throw new IllegalArgumentException("weight must be positive"); 
/*  67 */     this.elements.add(element);
/*  68 */     this.weights.add(Double.valueOf(weight));
/*  69 */     this.elementSet.add(element);
/*  70 */     this.totalWeight += weight;
/*  71 */     this.indices.put(element, Integer.valueOf(this.indices.size()));
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(@Nonnull T element) {
/*  77 */     if (element == null)
/*  78 */       throw new NullPointerException(); 
/*  79 */     if (this.immutable) {
/*  80 */       throw new IllegalStateException("method can't be called when object is immutable");
/*     */     }
/*     */ 
/*     */     
/*  84 */     if (!this.elementSet.contains(element)) {
/*  85 */       return 0.0D;
/*     */     }
/*  87 */     return ((Double)this.weights.get(((Integer)this.indices.get(element)).intValue())).doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T pick(@Nonnull Random rand) {
/*  93 */     if (rand == null)
/*  94 */       throw new NullPointerException(); 
/*  95 */     if (this.elements.isEmpty()) {
/*  96 */       throw new IllegalStateException("can't be empty when calling this method");
/*     */     }
/*     */     
/*  99 */     double pointer = rand.nextDouble() * this.totalWeight;
/* 100 */     for (int i = 0; i < this.elements.size(); i++) {
/* 101 */       pointer -= ((Double)this.weights.get(i)).doubleValue();
/*     */ 
/*     */       
/* 104 */       if (pointer <= 0.0D)
/* 105 */         return this.elements.get(i); 
/*     */     } 
/* 107 */     return this.elements.getLast();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 112 */     return this.elements.size();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<T> allElements() {
/* 117 */     return new ArrayList<>(this.elements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void makeImmutable() {
/* 125 */     this.immutable = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isImmutable() {
/* 130 */     return this.immutable;
/*     */   }
/*     */   
/*     */   public void forEach(@Nonnull BiConsumer<T, Double> consumer) {
/* 134 */     for (int i = 0; i < this.elements.size(); i++) {
/* 135 */       consumer.accept(this.elements.get(i), this.weights.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 142 */     return "WeighedMap{elementSet=" + String.valueOf(this.elementSet) + ", elements=" + String.valueOf(this.elements) + ", weights=" + String.valueOf(this.weights) + ", indices=" + String.valueOf(this.indices) + ", totalWeight=" + this.totalWeight + ", immutable=" + this.immutable + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\datastructures\WeightedMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */