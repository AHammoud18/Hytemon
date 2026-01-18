/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
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
/*      */ public class Double2ReferenceRBTreeMap<V>
/*      */   extends AbstractDouble2ReferenceSortedMap<V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<V> firstEntry;
/*      */   protected transient Entry<V> lastEntry;
/*      */   protected transient ObjectSortedSet<Double2ReferenceMap.Entry<V>> entries;
/*      */   protected transient DoubleSortedSet keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Double> storedComparator;
/*      */   protected transient DoubleComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry<V>[] nodePath;
/*      */   
/*      */   public Double2ReferenceRBTreeMap() {
/*   70 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   77 */     this.tree = null;
/*   78 */     this.count = 0;
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
/*   90 */     this.actualComparator = DoubleComparators.asDoubleComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceRBTreeMap(Comparator<? super Double> c) {
/*   99 */     this();
/*  100 */     this.storedComparator = c;
/*  101 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceRBTreeMap(Map<? extends Double, ? extends V> m) {
/*  110 */     this();
/*  111 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceRBTreeMap(SortedMap<Double, V> m) {
/*  120 */     this(m.comparator());
/*  121 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceRBTreeMap(Double2ReferenceMap<? extends V> m) {
/*  130 */     this();
/*  131 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceRBTreeMap(Double2ReferenceSortedMap<V> m) {
/*  140 */     this(m.comparator());
/*  141 */     putAll(m);
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
/*      */   public Double2ReferenceRBTreeMap(double[] k, V[] v, Comparator<? super Double> c) {
/*  153 */     this(c);
/*  154 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  155 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceRBTreeMap(double[] k, V[] v) {
/*  166 */     this(k, v, (Comparator<? super Double>)null);
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
/*      */   final int compare(double k1, double k2) {
/*  192 */     return (this.actualComparator == null) ? Double.compare(k1, k2) : this.actualComparator.compare(k1, k2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<V> findKey(double k) {
/*  202 */     Entry<V> e = this.tree;
/*      */     int cmp;
/*  204 */     for (; e != null && (cmp = compare(k, e.key)) != 0; e = (cmp < 0) ? e.left() : e.right());
/*  205 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<V> locateKey(double k) {
/*  216 */     Entry<V> e = this.tree, last = this.tree;
/*  217 */     int cmp = 0;
/*  218 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  219 */       last = e;
/*  220 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  222 */     return (cmp == 0) ? e : last;
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
/*  234 */     this.dirPath = new boolean[64];
/*  235 */     this.nodePath = (Entry<V>[])new Entry[64];
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(double k, V v) {
/*  240 */     Entry<V> e = add(k);
/*  241 */     V oldValue = e.value;
/*  242 */     e.value = v;
/*  243 */     return oldValue;
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
/*      */   private Entry<V> add(double k) {
/*      */     Entry<V> e;
/*  257 */     this.modified = false;
/*  258 */     int maxDepth = 0;
/*      */     
/*  260 */     if (this.tree == null) {
/*  261 */       this.count++;
/*  262 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*      */     } else {
/*  264 */       Entry<V> p = this.tree;
/*  265 */       int i = 0; while (true) {
/*      */         int cmp;
/*  267 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  269 */           for (; i-- != 0; this.nodePath[i] = null);
/*  270 */           return p;
/*      */         } 
/*  272 */         this.nodePath[i] = p;
/*  273 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  274 */           if (p.succ()) {
/*  275 */             this.count++;
/*  276 */             e = new Entry<>(k, this.defRetValue);
/*  277 */             if (p.right == null) this.lastEntry = e; 
/*  278 */             e.left = p;
/*  279 */             e.right = p.right;
/*  280 */             p.right(e);
/*      */             break;
/*      */           } 
/*  283 */           p = p.right; continue;
/*      */         } 
/*  285 */         if (p.pred()) {
/*  286 */           this.count++;
/*  287 */           e = new Entry<>(k, this.defRetValue);
/*  288 */           if (p.left == null) this.firstEntry = e; 
/*  289 */           e.right = p;
/*  290 */           e.left = p.left;
/*  291 */           p.left(e);
/*      */           break;
/*      */         } 
/*  294 */         p = p.left;
/*      */       } 
/*      */       
/*  297 */       this.modified = true;
/*  298 */       maxDepth = i--;
/*  299 */       while (i > 0 && !this.nodePath[i].black()) {
/*  300 */         if (!this.dirPath[i - 1]) {
/*  301 */           Entry<V> entry1 = (this.nodePath[i - 1]).right;
/*  302 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  303 */             this.nodePath[i].black(true);
/*  304 */             entry1.black(true);
/*  305 */             this.nodePath[i - 1].black(false);
/*  306 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  309 */           if (!this.dirPath[i]) { entry1 = this.nodePath[i]; }
/*      */           else
/*  311 */           { Entry<V> entry = this.nodePath[i];
/*  312 */             entry1 = entry.right;
/*  313 */             entry.right = entry1.left;
/*  314 */             entry1.left = entry;
/*  315 */             (this.nodePath[i - 1]).left = entry1;
/*  316 */             if (entry1.pred()) {
/*  317 */               entry1.pred(false);
/*  318 */               entry.succ(entry1);
/*      */             }  }
/*      */           
/*  321 */           Entry<V> entry2 = this.nodePath[i - 1];
/*  322 */           entry2.black(false);
/*  323 */           entry1.black(true);
/*  324 */           entry2.left = entry1.right;
/*  325 */           entry1.right = entry2;
/*  326 */           if (i < 2) { this.tree = entry1; }
/*      */           
/*  328 */           else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = entry1; }
/*  329 */           else { (this.nodePath[i - 2]).left = entry1; }
/*      */           
/*  331 */           if (entry1.succ()) {
/*  332 */             entry1.succ(false);
/*  333 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  338 */         Entry<V> y = (this.nodePath[i - 1]).left;
/*  339 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  340 */           this.nodePath[i].black(true);
/*  341 */           y.black(true);
/*  342 */           this.nodePath[i - 1].black(false);
/*  343 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  346 */         if (this.dirPath[i]) { y = this.nodePath[i]; }
/*      */         else
/*  348 */         { Entry<V> entry = this.nodePath[i];
/*  349 */           y = entry.left;
/*  350 */           entry.left = y.right;
/*  351 */           y.right = entry;
/*  352 */           (this.nodePath[i - 1]).right = y;
/*  353 */           if (y.succ()) {
/*  354 */             y.succ(false);
/*  355 */             entry.pred(y);
/*      */           }  }
/*      */         
/*  358 */         Entry<V> x = this.nodePath[i - 1];
/*  359 */         x.black(false);
/*  360 */         y.black(true);
/*  361 */         x.right = y.left;
/*  362 */         y.left = x;
/*  363 */         if (i < 2) { this.tree = y; }
/*      */         
/*  365 */         else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = y; }
/*  366 */         else { (this.nodePath[i - 2]).left = y; }
/*      */         
/*  368 */         if (y.pred()) {
/*  369 */           y.pred(false);
/*  370 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  377 */     this.tree.black(true);
/*      */     
/*  379 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  380 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(double k) {
/*  387 */     this.modified = false;
/*  388 */     if (this.tree == null) return this.defRetValue; 
/*  389 */     Entry<V> p = this.tree;
/*      */     
/*  391 */     int i = 0;
/*  392 */     double kk = k;
/*      */     int cmp;
/*  394 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  395 */       this.dirPath[i] = (cmp > 0);
/*  396 */       this.nodePath[i] = p;
/*  397 */       if (this.dirPath[i++]) {
/*  398 */         if ((p = p.right()) == null) {
/*      */           
/*  400 */           for (; i-- != 0; this.nodePath[i] = null);
/*  401 */           return this.defRetValue;
/*      */         }  continue;
/*      */       } 
/*  404 */       if ((p = p.left()) == null) {
/*      */         
/*  406 */         for (; i-- != 0; this.nodePath[i] = null);
/*  407 */         return this.defRetValue;
/*      */       } 
/*      */     } 
/*      */     
/*  411 */     if (p.left == null) this.firstEntry = p.next(); 
/*  412 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  413 */     if (p.succ()) {
/*  414 */       if (p.pred()) {
/*  415 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  417 */         else if (this.dirPath[i - 1]) { this.nodePath[i - 1].succ(p.right); }
/*  418 */         else { this.nodePath[i - 1].pred(p.left); }
/*      */       
/*      */       } else {
/*  421 */         (p.prev()).right = p.right;
/*  422 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  424 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = p.left; }
/*  425 */         else { (this.nodePath[i - 1]).left = p.left; }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  430 */       Entry<V> r = p.right;
/*  431 */       if (r.pred()) {
/*  432 */         r.left = p.left;
/*  433 */         r.pred(p.pred());
/*  434 */         if (!r.pred()) (r.prev()).right = r; 
/*  435 */         if (i == 0) { this.tree = r; }
/*      */         
/*  437 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = r; }
/*  438 */         else { (this.nodePath[i - 1]).left = r; }
/*      */         
/*  440 */         boolean color = r.black();
/*  441 */         r.black(p.black());
/*  442 */         p.black(color);
/*  443 */         this.dirPath[i] = true;
/*  444 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry<V> s;
/*  447 */         int j = i++;
/*      */         while (true) {
/*  449 */           this.dirPath[i] = false;
/*  450 */           this.nodePath[i++] = r;
/*  451 */           s = r.left;
/*  452 */           if (s.pred())
/*  453 */             break;  r = s;
/*      */         } 
/*  455 */         this.dirPath[j] = true;
/*  456 */         this.nodePath[j] = s;
/*  457 */         if (s.succ()) { r.pred(s); }
/*  458 */         else { r.left = s.right; }
/*  459 */          s.left = p.left;
/*  460 */         if (!p.pred()) {
/*  461 */           (p.prev()).right = s;
/*  462 */           s.pred(false);
/*      */         } 
/*  464 */         s.right(p.right);
/*  465 */         boolean color = s.black();
/*  466 */         s.black(p.black());
/*  467 */         p.black(color);
/*  468 */         if (j == 0) { this.tree = s; }
/*      */         
/*  470 */         else if (this.dirPath[j - 1]) { (this.nodePath[j - 1]).right = s; }
/*  471 */         else { (this.nodePath[j - 1]).left = s; }
/*      */       
/*      */       } 
/*      */     } 
/*  475 */     int maxDepth = i;
/*  476 */     if (p.black()) {
/*  477 */       for (; i > 0; i--) {
/*  478 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  479 */           Entry<V> x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  480 */           if (!x.black()) {
/*  481 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  485 */         if (!this.dirPath[i - 1]) {
/*  486 */           Entry<V> w = (this.nodePath[i - 1]).right;
/*  487 */           if (!w.black()) {
/*  488 */             w.black(true);
/*  489 */             this.nodePath[i - 1].black(false);
/*  490 */             (this.nodePath[i - 1]).right = w.left;
/*  491 */             w.left = this.nodePath[i - 1];
/*  492 */             if (i < 2) { this.tree = w; }
/*      */             
/*  494 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  495 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  497 */             this.nodePath[i] = this.nodePath[i - 1];
/*  498 */             this.dirPath[i] = false;
/*  499 */             this.nodePath[i - 1] = w;
/*  500 */             if (maxDepth == i++) maxDepth++; 
/*  501 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  503 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  504 */             w.black(false);
/*      */           } else {
/*  506 */             if (w.succ() || w.right.black()) {
/*  507 */               Entry<V> y = w.left;
/*  508 */               y.black(true);
/*  509 */               w.black(false);
/*  510 */               w.left = y.right;
/*  511 */               y.right = w;
/*  512 */               w = (this.nodePath[i - 1]).right = y;
/*  513 */               if (w.succ()) {
/*  514 */                 w.succ(false);
/*  515 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  518 */             w.black(this.nodePath[i - 1].black());
/*  519 */             this.nodePath[i - 1].black(true);
/*  520 */             w.right.black(true);
/*  521 */             (this.nodePath[i - 1]).right = w.left;
/*  522 */             w.left = this.nodePath[i - 1];
/*  523 */             if (i < 2) { this.tree = w; }
/*      */             
/*  525 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  526 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  528 */             if (w.pred()) {
/*  529 */               w.pred(false);
/*  530 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  535 */           Entry<V> w = (this.nodePath[i - 1]).left;
/*  536 */           if (!w.black()) {
/*  537 */             w.black(true);
/*  538 */             this.nodePath[i - 1].black(false);
/*  539 */             (this.nodePath[i - 1]).left = w.right;
/*  540 */             w.right = this.nodePath[i - 1];
/*  541 */             if (i < 2) { this.tree = w; }
/*      */             
/*  543 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  544 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  546 */             this.nodePath[i] = this.nodePath[i - 1];
/*  547 */             this.dirPath[i] = true;
/*  548 */             this.nodePath[i - 1] = w;
/*  549 */             if (maxDepth == i++) maxDepth++; 
/*  550 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  552 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  553 */             w.black(false);
/*      */           } else {
/*  555 */             if (w.pred() || w.left.black()) {
/*  556 */               Entry<V> y = w.right;
/*  557 */               y.black(true);
/*  558 */               w.black(false);
/*  559 */               w.right = y.left;
/*  560 */               y.left = w;
/*  561 */               w = (this.nodePath[i - 1]).left = y;
/*  562 */               if (w.pred()) {
/*  563 */                 w.pred(false);
/*  564 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  567 */             w.black(this.nodePath[i - 1].black());
/*  568 */             this.nodePath[i - 1].black(true);
/*  569 */             w.left.black(true);
/*  570 */             (this.nodePath[i - 1]).left = w.right;
/*  571 */             w.right = this.nodePath[i - 1];
/*  572 */             if (i < 2) { this.tree = w; }
/*      */             
/*  574 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  575 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  577 */             if (w.succ()) {
/*  578 */               w.succ(false);
/*  579 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  585 */       if (this.tree != null) this.tree.black(true); 
/*      */     } 
/*  587 */     this.modified = true;
/*  588 */     this.count--;
/*      */     
/*  590 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  591 */     return p.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  596 */     ValueIterator i = new ValueIterator();
/*      */     
/*  598 */     int j = this.count;
/*  599 */     while (j-- != 0) {
/*  600 */       Object ev = i.next();
/*  601 */       if (ev == v) return true; 
/*      */     } 
/*  603 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  608 */     this.count = 0;
/*  609 */     this.tree = null;
/*  610 */     this.entries = null;
/*  611 */     this.values = null;
/*  612 */     this.keys = null;
/*  613 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<V>
/*      */     extends AbstractDouble2ReferenceMap.BasicEntry<V>
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
/*      */     Entry<V> left;
/*      */ 
/*      */     
/*      */     Entry<V> right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry() {
/*  642 */       super(0.0D, (V)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(double k, V v) {
/*  652 */       super(k, v);
/*  653 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> left() {
/*  662 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> right() {
/*  671 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  680 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  689 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  698 */       if (pred) { this.info |= 0x40000000; }
/*  699 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  708 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  709 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<V> pred) {
/*  718 */       this.info |= 0x40000000;
/*  719 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<V> succ) {
/*  728 */       this.info |= Integer.MIN_VALUE;
/*  729 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<V> left) {
/*  738 */       this.info &= 0xBFFFFFFF;
/*  739 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<V> right) {
/*  748 */       this.info &= Integer.MAX_VALUE;
/*  749 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  758 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  767 */       if (black) { this.info |= 0x1; }
/*  768 */       else { this.info &= 0xFFFFFFFE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> next() {
/*  777 */       Entry<V> next = this.right;
/*  778 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  779 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> prev() {
/*  788 */       Entry<V> prev = this.left;
/*  789 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  790 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public V setValue(V value) {
/*  795 */       V oldValue = this.value;
/*  796 */       this.value = value;
/*  797 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry<V> clone() {
/*      */       Entry<V> c;
/*      */       try {
/*  805 */         c = (Entry<V>)super.clone();
/*  806 */       } catch (CloneNotSupportedException cantHappen) {
/*  807 */         throw new InternalError();
/*      */       } 
/*  809 */       c.key = this.key;
/*  810 */       c.value = this.value;
/*  811 */       c.info = this.info;
/*  812 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  818 */       if (!(o instanceof Map.Entry)) return false; 
/*  819 */       Map.Entry<Double, V> e = (Map.Entry<Double, V>)o;
/*  820 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && this.value == e.getValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  825 */       return HashCommon.double2int(this.key) ^ ((this.value == null) ? 0 : System.identityHashCode(this.value));
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  830 */       return this.key + "=>" + this.value;
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
/*      */   public boolean containsKey(double k) {
/*  866 */     return (findKey(k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  871 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  876 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(double k) {
/*  881 */     Entry<V> e = findKey(k);
/*  882 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public double firstDoubleKey() {
/*  887 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  888 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public double lastDoubleKey() {
/*  893 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  894 */     return this.lastEntry.key;
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
/*      */     Double2ReferenceRBTreeMap.Entry<V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2ReferenceRBTreeMap.Entry<V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Double2ReferenceRBTreeMap.Entry<V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  923 */     int index = 0;
/*      */     
/*      */     TreeIterator() {
/*  926 */       this.next = Double2ReferenceRBTreeMap.this.firstEntry;
/*      */     }
/*      */     
/*      */     TreeIterator(double k) {
/*  930 */       if ((this.next = Double2ReferenceRBTreeMap.this.locateKey(k)) != null)
/*  931 */         if (Double2ReferenceRBTreeMap.this.compare(this.next.key, k) <= 0)
/*  932 */         { this.prev = this.next;
/*  933 */           this.next = this.next.next(); }
/*  934 */         else { this.prev = this.next.prev(); }
/*      */          
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  939 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  943 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/*  947 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     Double2ReferenceRBTreeMap.Entry<V> nextEntry() {
/*  951 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  952 */       this.curr = this.prev = this.next;
/*  953 */       this.index++;
/*  954 */       updateNext();
/*  955 */       return this.curr;
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/*  959 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     Double2ReferenceRBTreeMap.Entry<V> previousEntry() {
/*  963 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  964 */       this.curr = this.next = this.prev;
/*  965 */       this.index--;
/*  966 */       updatePrevious();
/*  967 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  971 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  975 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  979 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/*  982 */       if (this.curr == this.prev) this.index--; 
/*  983 */       this.next = this.prev = this.curr;
/*  984 */       updatePrevious();
/*  985 */       updateNext();
/*  986 */       Double2ReferenceRBTreeMap.this.remove(this.curr.key);
/*  987 */       this.curr = null;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/*  991 */       int i = n;
/*  992 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  993 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/*  997 */       int i = n;
/*  998 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/*  999 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Double2ReferenceMap.Entry<V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     EntryIterator(double k) {
/* 1014 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public Double2ReferenceMap.Entry<V> next() {
/* 1019 */       return nextEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Double2ReferenceMap.Entry<V> previous() {
/* 1024 */       return previousEntry();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 1031 */     if (this.entries == null) this.entries = (ObjectSortedSet<Double2ReferenceMap.Entry<V>>)new AbstractObjectSortedSet<Double2ReferenceMap.Entry<V>>()
/*      */         {
/*      */           final Comparator<? super Double2ReferenceMap.Entry<V>> comparator;
/*      */           
/*      */           public Comparator<? super Double2ReferenceMap.Entry<V>> comparator() {
/* 1036 */             return this.comparator;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> iterator() {
/* 1041 */             return (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>>)new Double2ReferenceRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> iterator(Double2ReferenceMap.Entry<V> from) {
/* 1046 */             return (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>>)new Double2ReferenceRBTreeMap.EntryIterator(from.getDoubleKey());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1052 */             if (o == null || !(o instanceof Map.Entry)) return false; 
/* 1053 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1054 */             if (e.getKey() == null) return false; 
/* 1055 */             if (!(e.getKey() instanceof Double)) return false; 
/* 1056 */             Double2ReferenceRBTreeMap.Entry<V> f = Double2ReferenceRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1057 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1063 */             if (!(o instanceof Map.Entry)) return false; 
/* 1064 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1065 */             if (e.getKey() == null) return false; 
/* 1066 */             if (!(e.getKey() instanceof Double)) return false; 
/* 1067 */             Double2ReferenceRBTreeMap.Entry<V> f = Double2ReferenceRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1068 */             if (f == null || f.getValue() != e.getValue()) return false; 
/* 1069 */             Double2ReferenceRBTreeMap.this.remove(f.key);
/* 1070 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1075 */             return Double2ReferenceRBTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1080 */             Double2ReferenceRBTreeMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public Double2ReferenceMap.Entry<V> first() {
/* 1085 */             return Double2ReferenceRBTreeMap.this.firstEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public Double2ReferenceMap.Entry<V> last() {
/* 1090 */             return Double2ReferenceRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Double2ReferenceMap.Entry<V>> subSet(Double2ReferenceMap.Entry<V> from, Double2ReferenceMap.Entry<V> to) {
/* 1095 */             return Double2ReferenceRBTreeMap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2ReferenceEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Double2ReferenceMap.Entry<V>> headSet(Double2ReferenceMap.Entry<V> to) {
/* 1100 */             return Double2ReferenceRBTreeMap.this.headMap(to.getDoubleKey()).double2ReferenceEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Double2ReferenceMap.Entry<V>> tailSet(Double2ReferenceMap.Entry<V> from) {
/* 1105 */             return Double2ReferenceRBTreeMap.this.tailMap(from.getDoubleKey()).double2ReferenceEntrySet();
/*      */           }
/*      */         }; 
/* 1108 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements DoubleListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(double k) {
/* 1124 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1129 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public double previousDouble() {
/* 1134 */       return (previousEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractDouble2ReferenceSortedMap<V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public DoubleBidirectionalIterator iterator() {
/* 1142 */       return new Double2ReferenceRBTreeMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBidirectionalIterator iterator(double from) {
/* 1147 */       return new Double2ReferenceRBTreeMap.KeyIterator(from);
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
/*      */   public DoubleSortedSet keySet() {
/* 1162 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1163 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<V>
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1177 */       return (nextEntry()).value;
/*      */     }
/*      */ 
/*      */     
/*      */     public V previous() {
/* 1182 */       return (previousEntry()).value;
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
/*      */   public ReferenceCollection<V> values() {
/* 1197 */     if (this.values == null) this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1200 */             return (ObjectIterator<V>)new Double2ReferenceRBTreeMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object k) {
/* 1205 */             return Double2ReferenceRBTreeMap.this.containsValue(k);
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1210 */             return Double2ReferenceRBTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1215 */             Double2ReferenceRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1218 */     return this.values;
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1223 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public Double2ReferenceSortedMap<V> headMap(double to) {
/* 1228 */     return new Submap(0.0D, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 1233 */     return new Submap(from, false, 0.0D, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 1238 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractDouble2ReferenceSortedMap<V>
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     double from;
/*      */ 
/*      */ 
/*      */     
/*      */     double to;
/*      */ 
/*      */ 
/*      */     
/*      */     boolean bottom;
/*      */ 
/*      */     
/*      */     boolean top;
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<Double2ReferenceMap.Entry<V>> entries;
/*      */ 
/*      */     
/*      */     protected transient DoubleSortedSet keys;
/*      */ 
/*      */     
/*      */     protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(double from, boolean bottom, double to, boolean top) {
/* 1277 */       if (!bottom && !top && Double2ReferenceRBTreeMap.this.compare(from, to) > 0) throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1278 */       this.from = from;
/* 1279 */       this.bottom = bottom;
/* 1280 */       this.to = to;
/* 1281 */       this.top = top;
/* 1282 */       this.defRetValue = Double2ReferenceRBTreeMap.this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1287 */       SubmapIterator i = new SubmapIterator();
/* 1288 */       while (i.hasNext()) {
/* 1289 */         i.nextEntry();
/* 1290 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(double k) {
/* 1301 */       return ((this.bottom || Double2ReferenceRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Double2ReferenceRBTreeMap.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 1306 */       if (this.entries == null) this.entries = (ObjectSortedSet<Double2ReferenceMap.Entry<V>>)new AbstractObjectSortedSet<Double2ReferenceMap.Entry<V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> iterator() {
/* 1309 */               return (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>>)new Double2ReferenceRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> iterator(Double2ReferenceMap.Entry<V> from) {
/* 1314 */               return (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>>)new Double2ReferenceRBTreeMap.Submap.SubmapEntryIterator(from.getDoubleKey());
/*      */             }
/*      */ 
/*      */             
/*      */             public Comparator<? super Double2ReferenceMap.Entry<V>> comparator() {
/* 1319 */               return Double2ReferenceRBTreeMap.this.double2ReferenceEntrySet().comparator();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1325 */               if (!(o instanceof Map.Entry)) return false; 
/* 1326 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1327 */               if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 1328 */               Double2ReferenceRBTreeMap.Entry<V> f = Double2ReferenceRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1329 */               return (f != null && Double2ReferenceRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1335 */               if (!(o instanceof Map.Entry)) return false; 
/* 1336 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1337 */               if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 1338 */               Double2ReferenceRBTreeMap.Entry<V> f = Double2ReferenceRBTreeMap.this.findKey(((Double)e.getKey()).doubleValue());
/* 1339 */               if (f != null && Double2ReferenceRBTreeMap.Submap.this.in(f.key)) Double2ReferenceRBTreeMap.Submap.this.remove(f.key); 
/* 1340 */               return (f != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1345 */               int c = 0;
/* 1346 */               for (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); ) { c++; objectBidirectionalIterator.next(); }
/* 1347 */                return c;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1352 */               return !(new Double2ReferenceRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1357 */               Double2ReferenceRBTreeMap.Submap.this.clear();
/*      */             }
/*      */ 
/*      */             
/*      */             public Double2ReferenceMap.Entry<V> first() {
/* 1362 */               return Double2ReferenceRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public Double2ReferenceMap.Entry<V> last() {
/* 1367 */               return Double2ReferenceRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Double2ReferenceMap.Entry<V>> subSet(Double2ReferenceMap.Entry<V> from, Double2ReferenceMap.Entry<V> to) {
/* 1372 */               return Double2ReferenceRBTreeMap.Submap.this.subMap(from.getDoubleKey(), to.getDoubleKey()).double2ReferenceEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Double2ReferenceMap.Entry<V>> headSet(Double2ReferenceMap.Entry<V> to) {
/* 1377 */               return Double2ReferenceRBTreeMap.Submap.this.headMap(to.getDoubleKey()).double2ReferenceEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Double2ReferenceMap.Entry<V>> tailSet(Double2ReferenceMap.Entry<V> from) {
/* 1382 */               return Double2ReferenceRBTreeMap.Submap.this.tailMap(from.getDoubleKey()).double2ReferenceEntrySet();
/*      */             }
/*      */           }; 
/* 1385 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractDouble2ReferenceSortedMap<V>.KeySet { private KeySet() {}
/*      */       
/*      */       public DoubleBidirectionalIterator iterator() {
/* 1391 */         return new Double2ReferenceRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public DoubleBidirectionalIterator iterator(double from) {
/* 1396 */         return new Double2ReferenceRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleSortedSet keySet() {
/* 1402 */       if (this.keys == null) this.keys = new KeySet(); 
/* 1403 */       return this.keys;
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceCollection<V> values() {
/* 1408 */       if (this.values == null) this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1411 */               return (ObjectIterator<V>)new Double2ReferenceRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object k) {
/* 1416 */               return Double2ReferenceRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1421 */               return Double2ReferenceRBTreeMap.Submap.this.size();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1426 */               Double2ReferenceRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1429 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(double k) {
/* 1436 */       return (in(k) && Double2ReferenceRBTreeMap.this.containsKey(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1441 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1443 */       while (i.hasNext()) {
/* 1444 */         Object ev = (i.nextEntry()).value;
/* 1445 */         if (ev == v) return true; 
/*      */       } 
/* 1447 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(double k) {
/* 1454 */       double kk = k; Double2ReferenceRBTreeMap.Entry<V> e;
/* 1455 */       return (in(kk) && (e = Double2ReferenceRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V put(double k, V v) {
/* 1460 */       Double2ReferenceRBTreeMap.this.modified = false;
/* 1461 */       if (!in(k)) throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1462 */       V oldValue = Double2ReferenceRBTreeMap.this.put(k, v);
/* 1463 */       return Double2ReferenceRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V remove(double k) {
/* 1469 */       Double2ReferenceRBTreeMap.this.modified = false;
/* 1470 */       if (!in(k)) return this.defRetValue; 
/* 1471 */       V oldValue = Double2ReferenceRBTreeMap.this.remove(k);
/* 1472 */       return Double2ReferenceRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1477 */       SubmapIterator i = new SubmapIterator();
/* 1478 */       int n = 0;
/* 1479 */       while (i.hasNext()) {
/* 1480 */         n++;
/* 1481 */         i.nextEntry();
/*      */       } 
/* 1483 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1488 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1493 */       return Double2ReferenceRBTreeMap.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Double2ReferenceSortedMap<V> headMap(double to) {
/* 1498 */       if (this.top) return new Submap(this.from, this.bottom, to, false); 
/* 1499 */       return (Double2ReferenceRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 1504 */       if (this.bottom) return new Submap(from, false, this.to, this.top); 
/* 1505 */       return (Double2ReferenceRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 1510 */       if (this.top && this.bottom) return new Submap(from, false, to, false); 
/* 1511 */       if (!this.top) to = (Double2ReferenceRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1512 */       if (!this.bottom) from = (Double2ReferenceRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1513 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1514 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2ReferenceRBTreeMap.Entry<V> firstEntry() {
/*      */       Double2ReferenceRBTreeMap.Entry<V> e;
/* 1523 */       if (Double2ReferenceRBTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1527 */       if (this.bottom) { e = Double2ReferenceRBTreeMap.this.firstEntry; }
/*      */       else
/* 1529 */       { e = Double2ReferenceRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1531 */         if (Double2ReferenceRBTreeMap.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1535 */       if (e == null || (!this.top && Double2ReferenceRBTreeMap.this.compare(e.key, this.to) >= 0)) return null; 
/* 1536 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double2ReferenceRBTreeMap.Entry<V> lastEntry() {
/*      */       Double2ReferenceRBTreeMap.Entry<V> e;
/* 1545 */       if (Double2ReferenceRBTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1549 */       if (this.top) { e = Double2ReferenceRBTreeMap.this.lastEntry; }
/*      */       else
/* 1551 */       { e = Double2ReferenceRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1553 */         if (Double2ReferenceRBTreeMap.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1557 */       if (e == null || (!this.bottom && Double2ReferenceRBTreeMap.this.compare(e.key, this.from) < 0)) return null; 
/* 1558 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public double firstDoubleKey() {
/* 1563 */       Double2ReferenceRBTreeMap.Entry<V> e = firstEntry();
/* 1564 */       if (e == null) throw new NoSuchElementException(); 
/* 1565 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public double lastDoubleKey() {
/* 1570 */       Double2ReferenceRBTreeMap.Entry<V> e = lastEntry();
/* 1571 */       if (e == null) throw new NoSuchElementException(); 
/* 1572 */       return e.key;
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
/*      */       extends Double2ReferenceRBTreeMap<V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1586 */         this.next = Double2ReferenceRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubmapIterator(double k) {
/* 1590 */         this();
/* 1591 */         if (this.next != null) {
/* 1592 */           if (!Double2ReferenceRBTreeMap.Submap.this.bottom && Double2ReferenceRBTreeMap.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1593 */           else if (!Double2ReferenceRBTreeMap.Submap.this.top && Double2ReferenceRBTreeMap.this.compare(k, (this.prev = Double2ReferenceRBTreeMap.Submap.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1595 */           { this.next = Double2ReferenceRBTreeMap.this.locateKey(k);
/* 1596 */             if (Double2ReferenceRBTreeMap.this.compare(this.next.key, k) <= 0)
/* 1597 */             { this.prev = this.next;
/* 1598 */               this.next = this.next.next(); }
/* 1599 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1606 */         this.prev = this.prev.prev();
/* 1607 */         if (!Double2ReferenceRBTreeMap.Submap.this.bottom && this.prev != null && Double2ReferenceRBTreeMap.this.compare(this.prev.key, Double2ReferenceRBTreeMap.Submap.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1612 */         this.next = this.next.next();
/* 1613 */         if (!Double2ReferenceRBTreeMap.Submap.this.top && this.next != null && Double2ReferenceRBTreeMap.this.compare(this.next.key, Double2ReferenceRBTreeMap.Submap.this.to) >= 0) this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Double2ReferenceMap.Entry<V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(double k) {
/* 1622 */         super(k);
/*      */       }
/*      */ 
/*      */       
/*      */       public Double2ReferenceMap.Entry<V> next() {
/* 1627 */         return nextEntry();
/*      */       }
/*      */ 
/*      */       
/*      */       public Double2ReferenceMap.Entry<V> previous() {
/* 1632 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements DoubleListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(double from) {
/* 1651 */         super(from);
/*      */       }
/*      */ 
/*      */       
/*      */       public double nextDouble() {
/* 1656 */         return (nextEntry()).key;
/*      */       }
/*      */ 
/*      */       
/*      */       public double previousDouble() {
/* 1661 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements ObjectListIterator<V>
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public V next() {
/* 1677 */         return (nextEntry()).value;
/*      */       }
/*      */ 
/*      */       
/*      */       public V previous() {
/* 1682 */         return (previousEntry()).value;
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
/*      */   public Double2ReferenceRBTreeMap<V> clone() {
/*      */     Double2ReferenceRBTreeMap<V> c;
/*      */     try {
/* 1701 */       c = (Double2ReferenceRBTreeMap<V>)super.clone();
/* 1702 */     } catch (CloneNotSupportedException cantHappen) {
/* 1703 */       throw new InternalError();
/*      */     } 
/* 1705 */     c.keys = null;
/* 1706 */     c.values = null;
/* 1707 */     c.entries = null;
/* 1708 */     c.allocatePaths();
/* 1709 */     if (this.count != 0) {
/*      */       
/* 1711 */       Entry<V> rp = new Entry<>(), rq = new Entry<>();
/* 1712 */       Entry<V> p = rp;
/* 1713 */       rp.left(this.tree);
/* 1714 */       Entry<V> q = rq;
/* 1715 */       rq.pred((Entry<V>)null);
/*      */       while (true) {
/* 1717 */         if (!p.pred()) {
/* 1718 */           Entry<V> e = p.left.clone();
/* 1719 */           e.pred(q.left);
/* 1720 */           e.succ(q);
/* 1721 */           q.left(e);
/* 1722 */           p = p.left;
/* 1723 */           q = q.left;
/*      */         } else {
/* 1725 */           while (p.succ()) {
/* 1726 */             p = p.right;
/* 1727 */             if (p == null) {
/* 1728 */               q.right = null;
/* 1729 */               c.tree = rq.left;
/* 1730 */               c.firstEntry = c.tree;
/* 1731 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1732 */               c.lastEntry = c.tree;
/* 1733 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1734 */               return c;
/*      */             } 
/* 1736 */             q = q.right;
/*      */           } 
/* 1738 */           p = p.right;
/* 1739 */           q = q.right;
/*      */         } 
/* 1741 */         if (!p.succ()) {
/* 1742 */           Entry<V> e = p.right.clone();
/* 1743 */           e.succ(q.right);
/* 1744 */           e.pred(q);
/* 1745 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1749 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1753 */     int n = this.count;
/* 1754 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1756 */     s.defaultWriteObject();
/* 1757 */     while (n-- != 0) {
/* 1758 */       Entry<V> e = i.nextEntry();
/* 1759 */       s.writeDouble(e.key);
/* 1760 */       s.writeObject(e.value);
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
/*      */   private Entry<V> readTree(ObjectInputStream s, int n, Entry<V> pred, Entry<V> succ) throws IOException, ClassNotFoundException {
/* 1774 */     if (n == 1) {
/* 1775 */       Entry<V> entry = new Entry<>(s.readDouble(), (V)s.readObject());
/* 1776 */       entry.pred(pred);
/* 1777 */       entry.succ(succ);
/* 1778 */       entry.black(true);
/* 1779 */       return entry;
/*      */     } 
/* 1781 */     if (n == 2) {
/*      */ 
/*      */       
/* 1784 */       Entry<V> entry = new Entry<>(s.readDouble(), (V)s.readObject());
/* 1785 */       entry.black(true);
/* 1786 */       entry.right(new Entry<>(s.readDouble(), (V)s.readObject()));
/* 1787 */       entry.right.pred(entry);
/* 1788 */       entry.pred(pred);
/* 1789 */       entry.right.succ(succ);
/* 1790 */       return entry;
/*      */     } 
/*      */     
/* 1793 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1794 */     Entry<V> top = new Entry<>();
/* 1795 */     top.left(readTree(s, leftN, pred, top));
/* 1796 */     top.key = s.readDouble();
/* 1797 */     top.value = (V)s.readObject();
/* 1798 */     top.black(true);
/* 1799 */     top.right(readTree(s, rightN, top, succ));
/* 1800 */     if (n + 2 == (n + 2 & -(n + 2))) top.right.black(false);
/*      */     
/* 1802 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1806 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1809 */     setActualComparator();
/* 1810 */     allocatePaths();
/* 1811 */     if (this.count != 0) {
/* 1812 */       this.tree = readTree(s, this.count, (Entry<V>)null, (Entry<V>)null);
/*      */       
/* 1814 */       Entry<V> e = this.tree;
/* 1815 */       for (; e.left() != null; e = e.left());
/* 1816 */       this.firstEntry = e;
/* 1817 */       e = this.tree;
/* 1818 */       for (; e.right() != null; e = e.right());
/* 1819 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2ReferenceRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */