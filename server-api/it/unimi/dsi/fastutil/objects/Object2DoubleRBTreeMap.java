/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2DoubleRBTreeMap<K>
/*      */   extends AbstractObject2DoubleSortedMap<K>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<K> tree;
/*      */   protected int count;
/*      */   protected transient Entry<K> firstEntry;
/*      */   protected transient Entry<K> lastEntry;
/*      */   protected transient ObjectSortedSet<Object2DoubleMap.Entry<K>> entries;
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */   protected transient DoubleCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super K> storedComparator;
/*      */   protected transient Comparator<? super K> actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<K>[] nodePath;
/*      */   
/*      */   public Object2DoubleRBTreeMap() {
/*   67 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   74 */     this.tree = null;
/*   75 */     this.count = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setActualComparator() {
/*   87 */     this.actualComparator = this.storedComparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleRBTreeMap(Comparator<? super K> c) {
/*   96 */     this();
/*   97 */     this.storedComparator = c;
/*   98 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleRBTreeMap(Map<? extends K, ? extends Double> m) {
/*  107 */     this();
/*  108 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleRBTreeMap(SortedMap<K, Double> m) {
/*  117 */     this(m.comparator());
/*  118 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleRBTreeMap(Object2DoubleMap<? extends K> m) {
/*  127 */     this();
/*  128 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleRBTreeMap(Object2DoubleSortedMap<K> m) {
/*  137 */     this(m.comparator());
/*  138 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleRBTreeMap(K[] k, double[] v, Comparator<? super K> c) {
/*  150 */     this(c);
/*  151 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  152 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleRBTreeMap(K[] k, double[] v) {
/*  163 */     this(k, v, (Comparator<? super K>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int compare(K k1, K k2) {
/*  189 */     return (this.actualComparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.actualComparator.compare(k1, k2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K> findKey(K k) {
/*  199 */     Entry<K> e = this.tree;
/*      */     int cmp;
/*  201 */     for (; e != null && (cmp = compare(k, e.key)) != 0; e = (cmp < 0) ? e.left() : e.right());
/*  202 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<K> locateKey(K k) {
/*  213 */     Entry<K> e = this.tree, last = this.tree;
/*  214 */     int cmp = 0;
/*  215 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  216 */       last = e;
/*  217 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  219 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  231 */     this.dirPath = new boolean[64];
/*  232 */     this.nodePath = (Entry<K>[])new Entry[64];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double addTo(K k, double incr) {
/*  249 */     Entry<K> e = add(k);
/*  250 */     double oldValue = e.value;
/*  251 */     e.value += incr;
/*  252 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double put(K k, double v) {
/*  257 */     Entry<K> e = add(k);
/*  258 */     double oldValue = e.value;
/*  259 */     e.value = v;
/*  260 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K> add(K k) {
/*      */     Entry<K> e;
/*  273 */     Objects.requireNonNull(k);
/*  274 */     this.modified = false;
/*  275 */     int maxDepth = 0;
/*      */     
/*  277 */     if (this.tree == null) {
/*  278 */       this.count++;
/*  279 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*      */     } else {
/*  281 */       Entry<K> p = this.tree;
/*  282 */       int i = 0; while (true) {
/*      */         int cmp;
/*  284 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  286 */           for (; i-- != 0; this.nodePath[i] = null);
/*  287 */           return p;
/*      */         } 
/*  289 */         this.nodePath[i] = p;
/*  290 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  291 */           if (p.succ()) {
/*  292 */             this.count++;
/*  293 */             e = new Entry<>(k, this.defRetValue);
/*  294 */             if (p.right == null) this.lastEntry = e; 
/*  295 */             e.left = p;
/*  296 */             e.right = p.right;
/*  297 */             p.right(e);
/*      */             break;
/*      */           } 
/*  300 */           p = p.right; continue;
/*      */         } 
/*  302 */         if (p.pred()) {
/*  303 */           this.count++;
/*  304 */           e = new Entry<>(k, this.defRetValue);
/*  305 */           if (p.left == null) this.firstEntry = e; 
/*  306 */           e.right = p;
/*  307 */           e.left = p.left;
/*  308 */           p.left(e);
/*      */           break;
/*      */         } 
/*  311 */         p = p.left;
/*      */       } 
/*      */       
/*  314 */       this.modified = true;
/*  315 */       maxDepth = i--;
/*  316 */       while (i > 0 && !this.nodePath[i].black()) {
/*  317 */         if (!this.dirPath[i - 1]) {
/*  318 */           Entry<K> entry1 = (this.nodePath[i - 1]).right;
/*  319 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  320 */             this.nodePath[i].black(true);
/*  321 */             entry1.black(true);
/*  322 */             this.nodePath[i - 1].black(false);
/*  323 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  326 */           if (!this.dirPath[i]) { entry1 = this.nodePath[i]; }
/*      */           else
/*  328 */           { Entry<K> entry = this.nodePath[i];
/*  329 */             entry1 = entry.right;
/*  330 */             entry.right = entry1.left;
/*  331 */             entry1.left = entry;
/*  332 */             (this.nodePath[i - 1]).left = entry1;
/*  333 */             if (entry1.pred()) {
/*  334 */               entry1.pred(false);
/*  335 */               entry.succ(entry1);
/*      */             }  }
/*      */           
/*  338 */           Entry<K> entry2 = this.nodePath[i - 1];
/*  339 */           entry2.black(false);
/*  340 */           entry1.black(true);
/*  341 */           entry2.left = entry1.right;
/*  342 */           entry1.right = entry2;
/*  343 */           if (i < 2) { this.tree = entry1; }
/*      */           
/*  345 */           else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = entry1; }
/*  346 */           else { (this.nodePath[i - 2]).left = entry1; }
/*      */           
/*  348 */           if (entry1.succ()) {
/*  349 */             entry1.succ(false);
/*  350 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  355 */         Entry<K> y = (this.nodePath[i - 1]).left;
/*  356 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  357 */           this.nodePath[i].black(true);
/*  358 */           y.black(true);
/*  359 */           this.nodePath[i - 1].black(false);
/*  360 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  363 */         if (this.dirPath[i]) { y = this.nodePath[i]; }
/*      */         else
/*  365 */         { Entry<K> entry = this.nodePath[i];
/*  366 */           y = entry.left;
/*  367 */           entry.left = y.right;
/*  368 */           y.right = entry;
/*  369 */           (this.nodePath[i - 1]).right = y;
/*  370 */           if (y.succ()) {
/*  371 */             y.succ(false);
/*  372 */             entry.pred(y);
/*      */           }  }
/*      */         
/*  375 */         Entry<K> x = this.nodePath[i - 1];
/*  376 */         x.black(false);
/*  377 */         y.black(true);
/*  378 */         x.right = y.left;
/*  379 */         y.left = x;
/*  380 */         if (i < 2) { this.tree = y; }
/*      */         
/*  382 */         else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = y; }
/*  383 */         else { (this.nodePath[i - 2]).left = y; }
/*      */         
/*  385 */         if (y.pred()) {
/*  386 */           y.pred(false);
/*  387 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  394 */     this.tree.black(true);
/*      */     
/*  396 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  397 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double removeDouble(Object k) {
/*  405 */     this.modified = false;
/*  406 */     if (this.tree == null) return this.defRetValue; 
/*  407 */     Entry<K> p = this.tree;
/*      */     
/*  409 */     int i = 0;
/*  410 */     K kk = (K)k;
/*      */     int cmp;
/*  412 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  413 */       this.dirPath[i] = (cmp > 0);
/*  414 */       this.nodePath[i] = p;
/*  415 */       if (this.dirPath[i++]) {
/*  416 */         if ((p = p.right()) == null) {
/*      */           
/*  418 */           for (; i-- != 0; this.nodePath[i] = null);
/*  419 */           return this.defRetValue;
/*      */         }  continue;
/*      */       } 
/*  422 */       if ((p = p.left()) == null) {
/*      */         
/*  424 */         for (; i-- != 0; this.nodePath[i] = null);
/*  425 */         return this.defRetValue;
/*      */       } 
/*      */     } 
/*      */     
/*  429 */     if (p.left == null) this.firstEntry = p.next(); 
/*  430 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  431 */     if (p.succ()) {
/*  432 */       if (p.pred()) {
/*  433 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  435 */         else if (this.dirPath[i - 1]) { this.nodePath[i - 1].succ(p.right); }
/*  436 */         else { this.nodePath[i - 1].pred(p.left); }
/*      */       
/*      */       } else {
/*  439 */         (p.prev()).right = p.right;
/*  440 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  442 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = p.left; }
/*  443 */         else { (this.nodePath[i - 1]).left = p.left; }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  448 */       Entry<K> r = p.right;
/*  449 */       if (r.pred()) {
/*  450 */         r.left = p.left;
/*  451 */         r.pred(p.pred());
/*  452 */         if (!r.pred()) (r.prev()).right = r; 
/*  453 */         if (i == 0) { this.tree = r; }
/*      */         
/*  455 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = r; }
/*  456 */         else { (this.nodePath[i - 1]).left = r; }
/*      */         
/*  458 */         boolean color = r.black();
/*  459 */         r.black(p.black());
/*  460 */         p.black(color);
/*  461 */         this.dirPath[i] = true;
/*  462 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry<K> s;
/*  465 */         int j = i++;
/*      */         while (true) {
/*  467 */           this.dirPath[i] = false;
/*  468 */           this.nodePath[i++] = r;
/*  469 */           s = r.left;
/*  470 */           if (s.pred())
/*  471 */             break;  r = s;
/*      */         } 
/*  473 */         this.dirPath[j] = true;
/*  474 */         this.nodePath[j] = s;
/*  475 */         if (s.succ()) { r.pred(s); }
/*  476 */         else { r.left = s.right; }
/*  477 */          s.left = p.left;
/*  478 */         if (!p.pred()) {
/*  479 */           (p.prev()).right = s;
/*  480 */           s.pred(false);
/*      */         } 
/*  482 */         s.right(p.right);
/*  483 */         boolean color = s.black();
/*  484 */         s.black(p.black());
/*  485 */         p.black(color);
/*  486 */         if (j == 0) { this.tree = s; }
/*      */         
/*  488 */         else if (this.dirPath[j - 1]) { (this.nodePath[j - 1]).right = s; }
/*  489 */         else { (this.nodePath[j - 1]).left = s; }
/*      */       
/*      */       } 
/*      */     } 
/*  493 */     int maxDepth = i;
/*  494 */     if (p.black()) {
/*  495 */       for (; i > 0; i--) {
/*  496 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  497 */           Entry<K> x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  498 */           if (!x.black()) {
/*  499 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  503 */         if (!this.dirPath[i - 1]) {
/*  504 */           Entry<K> w = (this.nodePath[i - 1]).right;
/*  505 */           if (!w.black()) {
/*  506 */             w.black(true);
/*  507 */             this.nodePath[i - 1].black(false);
/*  508 */             (this.nodePath[i - 1]).right = w.left;
/*  509 */             w.left = this.nodePath[i - 1];
/*  510 */             if (i < 2) { this.tree = w; }
/*      */             
/*  512 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  513 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  515 */             this.nodePath[i] = this.nodePath[i - 1];
/*  516 */             this.dirPath[i] = false;
/*  517 */             this.nodePath[i - 1] = w;
/*  518 */             if (maxDepth == i++) maxDepth++; 
/*  519 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  521 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  522 */             w.black(false);
/*      */           } else {
/*  524 */             if (w.succ() || w.right.black()) {
/*  525 */               Entry<K> y = w.left;
/*  526 */               y.black(true);
/*  527 */               w.black(false);
/*  528 */               w.left = y.right;
/*  529 */               y.right = w;
/*  530 */               w = (this.nodePath[i - 1]).right = y;
/*  531 */               if (w.succ()) {
/*  532 */                 w.succ(false);
/*  533 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  536 */             w.black(this.nodePath[i - 1].black());
/*  537 */             this.nodePath[i - 1].black(true);
/*  538 */             w.right.black(true);
/*  539 */             (this.nodePath[i - 1]).right = w.left;
/*  540 */             w.left = this.nodePath[i - 1];
/*  541 */             if (i < 2) { this.tree = w; }
/*      */             
/*  543 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  544 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  546 */             if (w.pred()) {
/*  547 */               w.pred(false);
/*  548 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  553 */           Entry<K> w = (this.nodePath[i - 1]).left;
/*  554 */           if (!w.black()) {
/*  555 */             w.black(true);
/*  556 */             this.nodePath[i - 1].black(false);
/*  557 */             (this.nodePath[i - 1]).left = w.right;
/*  558 */             w.right = this.nodePath[i - 1];
/*  559 */             if (i < 2) { this.tree = w; }
/*      */             
/*  561 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  562 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  564 */             this.nodePath[i] = this.nodePath[i - 1];
/*  565 */             this.dirPath[i] = true;
/*  566 */             this.nodePath[i - 1] = w;
/*  567 */             if (maxDepth == i++) maxDepth++; 
/*  568 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  570 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  571 */             w.black(false);
/*      */           } else {
/*  573 */             if (w.pred() || w.left.black()) {
/*  574 */               Entry<K> y = w.right;
/*  575 */               y.black(true);
/*  576 */               w.black(false);
/*  577 */               w.right = y.left;
/*  578 */               y.left = w;
/*  579 */               w = (this.nodePath[i - 1]).left = y;
/*  580 */               if (w.pred()) {
/*  581 */                 w.pred(false);
/*  582 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  585 */             w.black(this.nodePath[i - 1].black());
/*  586 */             this.nodePath[i - 1].black(true);
/*  587 */             w.left.black(true);
/*  588 */             (this.nodePath[i - 1]).left = w.right;
/*  589 */             w.right = this.nodePath[i - 1];
/*  590 */             if (i < 2) { this.tree = w; }
/*      */             
/*  592 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  593 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  595 */             if (w.succ()) {
/*  596 */               w.succ(false);
/*  597 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  603 */       if (this.tree != null) this.tree.black(true); 
/*      */     } 
/*  605 */     this.modified = true;
/*  606 */     this.count--;
/*      */     
/*  608 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  609 */     return p.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(double v) {
/*  614 */     ValueIterator i = new ValueIterator();
/*      */     
/*  616 */     int j = this.count;
/*  617 */     while (j-- != 0) {
/*  618 */       double ev = i.nextDouble();
/*  619 */       if (Double.doubleToLongBits(ev) == Double.doubleToLongBits(v)) return true; 
/*      */     } 
/*  621 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  626 */     this.count = 0;
/*  627 */     this.tree = null;
/*  628 */     this.entries = null;
/*  629 */     this.values = null;
/*  630 */     this.keys = null;
/*  631 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<K>
/*      */     extends AbstractObject2DoubleMap.BasicEntry<K>
/*      */     implements Cloneable
/*      */   {
/*      */     private static final int BLACK_MASK = 1;
/*      */ 
/*      */     
/*      */     private static final int SUCC_MASK = -2147483648;
/*      */ 
/*      */     
/*      */     private static final int PRED_MASK = 1073741824;
/*      */ 
/*      */     
/*      */     Entry<K> left;
/*      */ 
/*      */     
/*      */     Entry<K> right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry() {
/*  660 */       super((K)null, 0.0D);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(K k, double v) {
/*  670 */       super(k, v);
/*  671 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> left() {
/*  680 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> right() {
/*  689 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  698 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  707 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  716 */       if (pred) { this.info |= 0x40000000; }
/*  717 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  726 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  727 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<K> pred) {
/*  736 */       this.info |= 0x40000000;
/*  737 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<K> succ) {
/*  746 */       this.info |= Integer.MIN_VALUE;
/*  747 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<K> left) {
/*  756 */       this.info &= 0xBFFFFFFF;
/*  757 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<K> right) {
/*  766 */       this.info &= Integer.MAX_VALUE;
/*  767 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  776 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  785 */       if (black) { this.info |= 0x1; }
/*  786 */       else { this.info &= 0xFFFFFFFE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> next() {
/*  795 */       Entry<K> next = this.right;
/*  796 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  797 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<K> prev() {
/*  806 */       Entry<K> prev = this.left;
/*  807 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  808 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public double setValue(double value) {
/*  813 */       double oldValue = this.value;
/*  814 */       this.value = value;
/*  815 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry<K> clone() {
/*      */       Entry<K> c;
/*      */       try {
/*  823 */         c = (Entry<K>)super.clone();
/*  824 */       } catch (CloneNotSupportedException cantHappen) {
/*  825 */         throw new InternalError();
/*      */       } 
/*  827 */       c.key = this.key;
/*  828 */       c.value = this.value;
/*  829 */       c.info = this.info;
/*  830 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  836 */       if (!(o instanceof Map.Entry)) return false; 
/*  837 */       Map.Entry<K, Double> e = (Map.Entry<K, Double>)o;
/*  838 */       return (Objects.equals(this.key, e.getKey()) && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  843 */       return this.key.hashCode() ^ HashCommon.double2int(this.value);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  848 */       return (new StringBuilder()).append(this.key).append("=>").append(this.value).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  884 */     if (k == null) return false; 
/*  885 */     return (findKey((K)k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  890 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  895 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDouble(Object k) {
/*  901 */     Entry<K> e = findKey((K)k);
/*  902 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public K firstKey() {
/*  907 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  908 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public K lastKey() {
/*  913 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  914 */     return this.lastEntry.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class TreeIterator
/*      */   {
/*      */     Object2DoubleRBTreeMap.Entry<K> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2DoubleRBTreeMap.Entry<K> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object2DoubleRBTreeMap.Entry<K> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  943 */     int index = 0;
/*      */     
/*      */     TreeIterator() {
/*  946 */       this.next = Object2DoubleRBTreeMap.this.firstEntry;
/*      */     }
/*      */     
/*      */     TreeIterator(K k) {
/*  950 */       if ((this.next = Object2DoubleRBTreeMap.this.locateKey(k)) != null)
/*  951 */         if (Object2DoubleRBTreeMap.this.compare(this.next.key, k) <= 0)
/*  952 */         { this.prev = this.next;
/*  953 */           this.next = this.next.next(); }
/*  954 */         else { this.prev = this.next.prev(); }
/*      */          
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  959 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  963 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/*  967 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     Object2DoubleRBTreeMap.Entry<K> nextEntry() {
/*  971 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  972 */       this.curr = this.prev = this.next;
/*  973 */       this.index++;
/*  974 */       updateNext();
/*  975 */       return this.curr;
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/*  979 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     Object2DoubleRBTreeMap.Entry<K> previousEntry() {
/*  983 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  984 */       this.curr = this.next = this.prev;
/*  985 */       this.index--;
/*  986 */       updatePrevious();
/*  987 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  991 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  995 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  999 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/* 1002 */       if (this.curr == this.prev) this.index--; 
/* 1003 */       this.next = this.prev = this.curr;
/* 1004 */       updatePrevious();
/* 1005 */       updateNext();
/* 1006 */       Object2DoubleRBTreeMap.this.removeDouble(this.curr.key);
/* 1007 */       this.curr = null;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1011 */       int i = n;
/* 1012 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1013 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1017 */       int i = n;
/* 1018 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1019 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Object2DoubleMap.Entry<K>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     EntryIterator(K k) {
/* 1034 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2DoubleMap.Entry<K> next() {
/* 1039 */       return nextEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2DoubleMap.Entry<K> previous() {
/* 1044 */       return previousEntry();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<Object2DoubleMap.Entry<K>> object2DoubleEntrySet() {
/* 1051 */     if (this.entries == null) this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2DoubleMap.Entry<Object2DoubleMap.Entry<K>>>()
/*      */         {
/*      */           final Comparator<? super Object2DoubleMap.Entry<K>> comparator;
/*      */           
/*      */           public Comparator<? super Object2DoubleMap.Entry<K>> comparator() {
/* 1056 */             return this.comparator;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2DoubleMap.Entry<K>> iterator() {
/* 1061 */             return new Object2DoubleRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Object2DoubleMap.Entry<K>> iterator(Object2DoubleMap.Entry<K> from) {
/* 1066 */             return new Object2DoubleRBTreeMap.EntryIterator(from.getKey());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1072 */             if (o == null || !(o instanceof Map.Entry)) return false; 
/* 1073 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1074 */             if (e.getKey() == null) return false; 
/* 1075 */             if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 1076 */             Object2DoubleRBTreeMap.Entry<K> f = Object2DoubleRBTreeMap.this.findKey((K)e.getKey());
/* 1077 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1083 */             if (!(o instanceof Map.Entry)) return false; 
/* 1084 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1085 */             if (e.getKey() == null) return false; 
/* 1086 */             if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 1087 */             Object2DoubleRBTreeMap.Entry<K> f = Object2DoubleRBTreeMap.this.findKey((K)e.getKey());
/* 1088 */             if (f == null || Double.doubleToLongBits(f.getDoubleValue()) != Double.doubleToLongBits(((Double)e.getValue()).doubleValue())) return false; 
/* 1089 */             Object2DoubleRBTreeMap.this.removeDouble(f.key);
/* 1090 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1095 */             return Object2DoubleRBTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1100 */             Object2DoubleRBTreeMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public Object2DoubleMap.Entry<K> first() {
/* 1105 */             return Object2DoubleRBTreeMap.this.firstEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public Object2DoubleMap.Entry<K> last() {
/* 1110 */             return Object2DoubleRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2DoubleMap.Entry<K>> subSet(Object2DoubleMap.Entry<K> from, Object2DoubleMap.Entry<K> to) {
/* 1115 */             return Object2DoubleRBTreeMap.this.subMap(from.getKey(), to.getKey()).object2DoubleEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2DoubleMap.Entry<K>> headSet(Object2DoubleMap.Entry<K> to) {
/* 1120 */             return Object2DoubleRBTreeMap.this.headMap(to.getKey()).object2DoubleEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Object2DoubleMap.Entry<K>> tailSet(Object2DoubleMap.Entry<K> from) {
/* 1125 */             return Object2DoubleRBTreeMap.this.tailMap(from.getKey()).object2DoubleEntrySet();
/*      */           }
/*      */         }; 
/* 1128 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(K k) {
/* 1144 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1149 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1154 */       return (previousEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractObject2DoubleSortedMap<K>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator() {
/* 1162 */       return new Object2DoubleRBTreeMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1167 */       return new Object2DoubleRBTreeMap.KeyIterator(from);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1182 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1183 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements DoubleListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1197 */       return (nextEntry()).value;
/*      */     }
/*      */ 
/*      */     
/*      */     public double previousDouble() {
/* 1202 */       return (previousEntry()).value;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleCollection values() {
/* 1217 */     if (this.values == null) this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1220 */             return (DoubleIterator)new Object2DoubleRBTreeMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(double k) {
/* 1225 */             return Object2DoubleRBTreeMap.this.containsValue(k);
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1230 */             return Object2DoubleRBTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1235 */             Object2DoubleRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1238 */     return this.values;
/*      */   }
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1243 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2DoubleSortedMap<K> headMap(K to) {
/* 1248 */     return new Submap(null, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2DoubleSortedMap<K> tailMap(K from) {
/* 1253 */     return new Submap(from, false, null, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2DoubleSortedMap<K> subMap(K from, K to) {
/* 1258 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractObject2DoubleSortedMap<K>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     K from;
/*      */ 
/*      */ 
/*      */     
/*      */     K to;
/*      */ 
/*      */ 
/*      */     
/*      */     boolean bottom;
/*      */ 
/*      */     
/*      */     boolean top;
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<Object2DoubleMap.Entry<K>> entries;
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */     
/*      */     protected transient DoubleCollection values;
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(K from, boolean bottom, K to, boolean top) {
/* 1297 */       if (!bottom && !top && Object2DoubleRBTreeMap.this.compare(from, to) > 0) throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1298 */       this.from = from;
/* 1299 */       this.bottom = bottom;
/* 1300 */       this.to = to;
/* 1301 */       this.top = top;
/* 1302 */       this.defRetValue = Object2DoubleRBTreeMap.this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1307 */       SubmapIterator i = new SubmapIterator();
/* 1308 */       while (i.hasNext()) {
/* 1309 */         i.nextEntry();
/* 1310 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(K k) {
/* 1321 */       return ((this.bottom || Object2DoubleRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Object2DoubleRBTreeMap.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2DoubleMap.Entry<K>> object2DoubleEntrySet() {
/* 1326 */       if (this.entries == null) this.entries = (ObjectSortedSet)new AbstractObjectSortedSet<Object2DoubleMap.Entry<Object2DoubleMap.Entry<K>>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Object2DoubleMap.Entry<K>> iterator() {
/* 1329 */               return new Object2DoubleRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Object2DoubleMap.Entry<K>> iterator(Object2DoubleMap.Entry<K> from) {
/* 1334 */               return new Object2DoubleRBTreeMap.Submap.SubmapEntryIterator(from.getKey());
/*      */             }
/*      */ 
/*      */             
/*      */             public Comparator<? super Object2DoubleMap.Entry<K>> comparator() {
/* 1339 */               return Object2DoubleRBTreeMap.this.object2DoubleEntrySet().comparator();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1345 */               if (!(o instanceof Map.Entry)) return false; 
/* 1346 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1347 */               if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 1348 */               Object2DoubleRBTreeMap.Entry<K> f = Object2DoubleRBTreeMap.this.findKey((K)e.getKey());
/* 1349 */               return (f != null && Object2DoubleRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1355 */               if (!(o instanceof Map.Entry)) return false; 
/* 1356 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1357 */               if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 1358 */               Object2DoubleRBTreeMap.Entry<K> f = Object2DoubleRBTreeMap.this.findKey((K)e.getKey());
/* 1359 */               if (f != null && Object2DoubleRBTreeMap.Submap.this.in(f.key)) Object2DoubleRBTreeMap.Submap.this.removeDouble(f.key); 
/* 1360 */               return (f != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1365 */               int c = 0;
/* 1366 */               for (Iterator<?> i = iterator(); i.hasNext(); ) { c++; i.next(); }
/* 1367 */                return c;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1372 */               return !(new Object2DoubleRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1377 */               Object2DoubleRBTreeMap.Submap.this.clear();
/*      */             }
/*      */ 
/*      */             
/*      */             public Object2DoubleMap.Entry<K> first() {
/* 1382 */               return Object2DoubleRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public Object2DoubleMap.Entry<K> last() {
/* 1387 */               return Object2DoubleRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2DoubleMap.Entry<K>> subSet(Object2DoubleMap.Entry<K> from, Object2DoubleMap.Entry<K> to) {
/* 1392 */               return Object2DoubleRBTreeMap.Submap.this.subMap(from.getKey(), to.getKey()).object2DoubleEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2DoubleMap.Entry<K>> headSet(Object2DoubleMap.Entry<K> to) {
/* 1397 */               return Object2DoubleRBTreeMap.Submap.this.headMap(to.getKey()).object2DoubleEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Object2DoubleMap.Entry<K>> tailSet(Object2DoubleMap.Entry<K> from) {
/* 1402 */               return Object2DoubleRBTreeMap.Submap.this.tailMap(from.getKey()).object2DoubleEntrySet();
/*      */             }
/*      */           }; 
/* 1405 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractObject2DoubleSortedMap<K>.KeySet { private KeySet() {}
/*      */       
/*      */       public ObjectBidirectionalIterator<K> iterator() {
/* 1411 */         return new Object2DoubleRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public ObjectBidirectionalIterator<K> iterator(K from) {
/* 1416 */         return new Object2DoubleRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> keySet() {
/* 1422 */       if (this.keys == null) this.keys = new KeySet(); 
/* 1423 */       return this.keys;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleCollection values() {
/* 1428 */       if (this.values == null) this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */           {
/*      */             public DoubleIterator iterator() {
/* 1431 */               return (DoubleIterator)new Object2DoubleRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(double k) {
/* 1436 */               return Object2DoubleRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1441 */               return Object2DoubleRBTreeMap.Submap.this.size();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1446 */               Object2DoubleRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1449 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(Object k) {
/* 1455 */       if (k == null) return false; 
/* 1456 */       return (in((K)k) && Object2DoubleRBTreeMap.this.containsKey(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(double v) {
/* 1461 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1463 */       while (i.hasNext()) {
/* 1464 */         double ev = (i.nextEntry()).value;
/* 1465 */         if (Double.doubleToLongBits(ev) == Double.doubleToLongBits(v)) return true; 
/*      */       } 
/* 1467 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double getDouble(Object k) {
/* 1474 */       K kk = (K)k; Object2DoubleRBTreeMap.Entry<K> e;
/* 1475 */       return (in(kk) && (e = Object2DoubleRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public double put(K k, double v) {
/* 1480 */       Object2DoubleRBTreeMap.this.modified = false;
/* 1481 */       if (!in(k)) throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1482 */       double oldValue = Object2DoubleRBTreeMap.this.put(k, v);
/* 1483 */       return Object2DoubleRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public double removeDouble(Object k) {
/* 1489 */       Object2DoubleRBTreeMap.this.modified = false;
/* 1490 */       if (!in((K)k)) return this.defRetValue; 
/* 1491 */       double oldValue = Object2DoubleRBTreeMap.this.removeDouble(k);
/* 1492 */       return Object2DoubleRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1497 */       SubmapIterator i = new SubmapIterator();
/* 1498 */       int n = 0;
/* 1499 */       while (i.hasNext()) {
/* 1500 */         n++;
/* 1501 */         i.nextEntry();
/*      */       } 
/* 1503 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1508 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1513 */       return Object2DoubleRBTreeMap.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2DoubleSortedMap<K> headMap(K to) {
/* 1518 */       if (this.top) return new Submap(this.from, this.bottom, to, false); 
/* 1519 */       return (Object2DoubleRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2DoubleSortedMap<K> tailMap(K from) {
/* 1524 */       if (this.bottom) return new Submap(from, false, this.to, this.top); 
/* 1525 */       return (Object2DoubleRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2DoubleSortedMap<K> subMap(K from, K to) {
/* 1530 */       if (this.top && this.bottom) return new Submap(from, false, to, false); 
/* 1531 */       if (!this.top) to = (Object2DoubleRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1532 */       if (!this.bottom) from = (Object2DoubleRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1533 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1534 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2DoubleRBTreeMap.Entry<K> firstEntry() {
/*      */       Object2DoubleRBTreeMap.Entry<K> e;
/* 1543 */       if (Object2DoubleRBTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1547 */       if (this.bottom) { e = Object2DoubleRBTreeMap.this.firstEntry; }
/*      */       else
/* 1549 */       { e = Object2DoubleRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1551 */         if (Object2DoubleRBTreeMap.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1555 */       if (e == null || (!this.top && Object2DoubleRBTreeMap.this.compare(e.key, this.to) >= 0)) return null; 
/* 1556 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object2DoubleRBTreeMap.Entry<K> lastEntry() {
/*      */       Object2DoubleRBTreeMap.Entry<K> e;
/* 1565 */       if (Object2DoubleRBTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1569 */       if (this.top) { e = Object2DoubleRBTreeMap.this.lastEntry; }
/*      */       else
/* 1571 */       { e = Object2DoubleRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1573 */         if (Object2DoubleRBTreeMap.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1577 */       if (e == null || (!this.bottom && Object2DoubleRBTreeMap.this.compare(e.key, this.from) < 0)) return null; 
/* 1578 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public K firstKey() {
/* 1583 */       Object2DoubleRBTreeMap.Entry<K> e = firstEntry();
/* 1584 */       if (e == null) throw new NoSuchElementException(); 
/* 1585 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public K lastKey() {
/* 1590 */       Object2DoubleRBTreeMap.Entry<K> e = lastEntry();
/* 1591 */       if (e == null) throw new NoSuchElementException(); 
/* 1592 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class SubmapIterator
/*      */       extends Object2DoubleRBTreeMap<K>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1606 */         this.next = Object2DoubleRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubmapIterator(K k) {
/* 1610 */         this();
/* 1611 */         if (this.next != null) {
/* 1612 */           if (!Object2DoubleRBTreeMap.Submap.this.bottom && Object2DoubleRBTreeMap.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1613 */           else if (!Object2DoubleRBTreeMap.Submap.this.top && Object2DoubleRBTreeMap.this.compare(k, (this.prev = Object2DoubleRBTreeMap.Submap.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1615 */           { this.next = Object2DoubleRBTreeMap.this.locateKey(k);
/* 1616 */             if (Object2DoubleRBTreeMap.this.compare(this.next.key, k) <= 0)
/* 1617 */             { this.prev = this.next;
/* 1618 */               this.next = this.next.next(); }
/* 1619 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1626 */         this.prev = this.prev.prev();
/* 1627 */         if (!Object2DoubleRBTreeMap.Submap.this.bottom && this.prev != null && Object2DoubleRBTreeMap.this.compare(this.prev.key, Object2DoubleRBTreeMap.Submap.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1632 */         this.next = this.next.next();
/* 1633 */         if (!Object2DoubleRBTreeMap.Submap.this.top && this.next != null && Object2DoubleRBTreeMap.this.compare(this.next.key, Object2DoubleRBTreeMap.Submap.this.to) >= 0) this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Object2DoubleMap.Entry<K>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(K k) {
/* 1642 */         super(k);
/*      */       }
/*      */ 
/*      */       
/*      */       public Object2DoubleMap.Entry<K> next() {
/* 1647 */         return nextEntry();
/*      */       }
/*      */ 
/*      */       
/*      */       public Object2DoubleMap.Entry<K> previous() {
/* 1652 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements ObjectListIterator<K>
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(K from) {
/* 1671 */         super(from);
/*      */       }
/*      */ 
/*      */       
/*      */       public K next() {
/* 1676 */         return (nextEntry()).key;
/*      */       }
/*      */ 
/*      */       
/*      */       public K previous() {
/* 1681 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements DoubleListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public double nextDouble() {
/* 1697 */         return (nextEntry()).value;
/*      */       }
/*      */ 
/*      */       
/*      */       public double previousDouble() {
/* 1702 */         return (previousEntry()).value;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleRBTreeMap<K> clone() {
/*      */     Object2DoubleRBTreeMap<K> c;
/*      */     try {
/* 1721 */       c = (Object2DoubleRBTreeMap<K>)super.clone();
/* 1722 */     } catch (CloneNotSupportedException cantHappen) {
/* 1723 */       throw new InternalError();
/*      */     } 
/* 1725 */     c.keys = null;
/* 1726 */     c.values = null;
/* 1727 */     c.entries = null;
/* 1728 */     c.allocatePaths();
/* 1729 */     if (this.count != 0) {
/*      */       
/* 1731 */       Entry<K> rp = new Entry<>(), rq = new Entry<>();
/* 1732 */       Entry<K> p = rp;
/* 1733 */       rp.left(this.tree);
/* 1734 */       Entry<K> q = rq;
/* 1735 */       rq.pred((Entry<K>)null);
/*      */       while (true) {
/* 1737 */         if (!p.pred()) {
/* 1738 */           Entry<K> e = p.left.clone();
/* 1739 */           e.pred(q.left);
/* 1740 */           e.succ(q);
/* 1741 */           q.left(e);
/* 1742 */           p = p.left;
/* 1743 */           q = q.left;
/*      */         } else {
/* 1745 */           while (p.succ()) {
/* 1746 */             p = p.right;
/* 1747 */             if (p == null) {
/* 1748 */               q.right = null;
/* 1749 */               c.tree = rq.left;
/* 1750 */               c.firstEntry = c.tree;
/* 1751 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1752 */               c.lastEntry = c.tree;
/* 1753 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1754 */               return c;
/*      */             } 
/* 1756 */             q = q.right;
/*      */           } 
/* 1758 */           p = p.right;
/* 1759 */           q = q.right;
/*      */         } 
/* 1761 */         if (!p.succ()) {
/* 1762 */           Entry<K> e = p.right.clone();
/* 1763 */           e.succ(q.right);
/* 1764 */           e.pred(q);
/* 1765 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1769 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1773 */     int n = this.count;
/* 1774 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1776 */     s.defaultWriteObject();
/* 1777 */     while (n-- != 0) {
/* 1778 */       Entry<K> e = i.nextEntry();
/* 1779 */       s.writeObject(e.key);
/* 1780 */       s.writeDouble(e.value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<K> readTree(ObjectInputStream s, int n, Entry<K> pred, Entry<K> succ) throws IOException, ClassNotFoundException {
/* 1794 */     if (n == 1) {
/* 1795 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readDouble());
/* 1796 */       entry.pred(pred);
/* 1797 */       entry.succ(succ);
/* 1798 */       entry.black(true);
/* 1799 */       return entry;
/*      */     } 
/* 1801 */     if (n == 2) {
/*      */ 
/*      */       
/* 1804 */       Entry<K> entry = new Entry<>((K)s.readObject(), s.readDouble());
/* 1805 */       entry.black(true);
/* 1806 */       entry.right(new Entry<>((K)s.readObject(), s.readDouble()));
/* 1807 */       entry.right.pred(entry);
/* 1808 */       entry.pred(pred);
/* 1809 */       entry.right.succ(succ);
/* 1810 */       return entry;
/*      */     } 
/*      */     
/* 1813 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1814 */     Entry<K> top = new Entry<>();
/* 1815 */     top.left(readTree(s, leftN, pred, top));
/* 1816 */     top.key = (K)s.readObject();
/* 1817 */     top.value = s.readDouble();
/* 1818 */     top.black(true);
/* 1819 */     top.right(readTree(s, rightN, top, succ));
/* 1820 */     if (n + 2 == (n + 2 & -(n + 2))) top.right.black(false);
/*      */     
/* 1822 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1826 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1829 */     setActualComparator();
/* 1830 */     allocatePaths();
/* 1831 */     if (this.count != 0) {
/* 1832 */       this.tree = readTree(s, this.count, (Entry<K>)null, (Entry<K>)null);
/*      */       
/* 1834 */       Entry<K> e = this.tree;
/* 1835 */       for (; e.left() != null; e = e.left());
/* 1836 */       this.firstEntry = e;
/* 1837 */       e = this.tree;
/* 1838 */       for (; e.right() != null; e = e.right());
/* 1839 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2DoubleRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */