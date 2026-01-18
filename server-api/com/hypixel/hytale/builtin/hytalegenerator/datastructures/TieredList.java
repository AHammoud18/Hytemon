/*     */ package com.hypixel.hytale.builtin.hytalegenerator.datastructures;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class TieredList<E> {
/*     */   @Nonnull
/*     */   private final Map<Integer, ArrayList<E>> elements;
/*     */   private final int tiers;
/*     */   private List<Integer> sortedTierList;
/*     */   
/*     */   public TieredList() {
/*  19 */     this(0);
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
/*     */   public TieredList(int tiers) {
/*  31 */     if (tiers < 0)
/*  32 */       throw new IllegalArgumentException("negative number of tiers"); 
/*  33 */     this.tiers = tiers;
/*  34 */     this.elements = new HashMap<>();
/*  35 */     for (int tier = 0; tier < tiers; tier++)
/*  36 */       this.elements.put(Integer.valueOf(tier), new ArrayList<>()); 
/*  37 */     updateSortedTierList();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public TieredList<E> addTier(int tier) {
/*  43 */     if (tierExists(tier))
/*  44 */       throw new IllegalArgumentException("tier already exists " + tier); 
/*  45 */     this.elements.put(Integer.valueOf(tier), new ArrayList<>());
/*  46 */     updateSortedTierList();
/*  47 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public TieredList<E> removeTier(int tier) {
/*  53 */     if (!tierExists(tier))
/*  54 */       return this; 
/*  55 */     this.elements.remove(Integer.valueOf(tier));
/*  56 */     updateSortedTierList();
/*  57 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(@Nonnull E element, int tier) {
/*  65 */     if (element == null)
/*  66 */       throw new NullPointerException(); 
/*  67 */     if (!tierExists(tier))
/*  68 */       addTier(tier); 
/*  69 */     ((ArrayList<E>)this.elements.get(Integer.valueOf(tier))).add(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  74 */     for (List<E> list : this.elements.values()) {
/*  75 */       if (!list.isEmpty())
/*  76 */         return false; 
/*  77 */     }  return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public E peek() {
/*  82 */     for (int tier = 0; tier < this.tiers; ) {
/*     */       
/*  84 */       List<E> tierElements = this.elements.get(Integer.valueOf(tier));
/*  85 */       if (tierElements.isEmpty()) {
/*     */         tier++; continue;
/*  87 */       }  return tierElements.getFirst();
/*     */     } 
/*  89 */     throw new IllegalStateException("queue is empty");
/*     */   }
/*     */ 
/*     */   
/*     */   public E remove() {
/*  94 */     for (int tier = 0; tier < this.tiers; ) {
/*     */       
/*  96 */       List<E> tierElements = this.elements.get(Integer.valueOf(tier));
/*  97 */       if (tierElements.isEmpty()) {
/*     */         tier++; continue;
/*  99 */       }  return tierElements.removeFirst();
/*     */     } 
/* 101 */     throw new IllegalStateException("queue is empty");
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 106 */     int size = 0;
/* 107 */     for (List<E> list : this.elements.values())
/* 108 */       size += list.size(); 
/* 109 */     return size;
/*     */   }
/*     */   
/*     */   public int size(int tier) {
/* 113 */     if (!tierExists(tier))
/* 114 */       return 0; 
/* 115 */     return ((ArrayList)this.elements.get(Integer.valueOf(tier))).size();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public TieredList<E> forEach(int tier, @Nonnull Consumer<? super E> consumer) {
/* 121 */     if (!tierExists(tier))
/* 122 */       return this; 
/* 123 */     ((ArrayList<E>)this.elements.get(Integer.valueOf(tier))).forEach(consumer);
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public TieredList<E> removeEach(int tier, @Nonnull Consumer<? super E> consumer) {
/* 130 */     if (!tierExists(tier))
/* 131 */       return this; 
/* 132 */     List<E> tierList = this.elements.get(Integer.valueOf(tier));
/* 133 */     for (E e : tierList) consumer.accept(e); 
/* 134 */     tierList = new ArrayList<>();
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public TieredList<E> forEach(@Nonnull Consumer<? super E> consumer) {
/* 141 */     ArrayList<Integer> tiers = new ArrayList<>(getTiers());
/* 142 */     tiers.sort(Comparator.naturalOrder());
/* 143 */     for (Iterator<Integer> iterator = tiers.iterator(); iterator.hasNext(); ) { int tier = ((Integer)iterator.next()).intValue();
/* 144 */       forEach(tier, consumer); }
/* 145 */      return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public TieredList<E> removeEach(@Nonnull Consumer<? super E> consumer) {
/* 151 */     for (Iterator<Integer> iterator = getTiers().iterator(); iterator.hasNext(); ) { int tier = ((Integer)iterator.next()).intValue();
/* 152 */       removeEach(tier, consumer); }
/* 153 */      return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Iterator<E> iterator(int tier) {
/* 159 */     if (!tierExists(tier))
/* 160 */       throw new IllegalArgumentException("tier doesn't exist"); 
/* 161 */     return ((ArrayList<E>)this.elements.get(Integer.valueOf(tier))).iterator();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<E> listOf(int tier) {
/* 166 */     if (!tierExists(tier))
/* 167 */       throw new IllegalArgumentException("tier doesn't exist"); 
/* 168 */     return Collections.unmodifiableList(this.elements.get(Integer.valueOf(tier)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tierExists(int tier) {
/* 173 */     return this.elements.containsKey(Integer.valueOf(tier));
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Integer> getTiers() {
/* 178 */     return this.sortedTierList;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateSortedTierList() {
/* 183 */     List<Integer> tierList = new ArrayList<>(this.elements.keySet());
/* 184 */     tierList.sort(Comparator.naturalOrder());
/* 185 */     tierList = Collections.unmodifiableList(tierList);
/* 186 */     this.sortedTierList = tierList;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 192 */     return "TieredList{elements=" + String.valueOf(this.elements) + ", tiers=" + this.tiers + ", sortedTierList=" + String.valueOf(this.sortedTierList) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\datastructures\TieredList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */