/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
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
/*      */ public class Float2FloatRBTreeMap
/*      */   extends AbstractFloat2FloatSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Float2FloatMap.Entry> entries;
/*      */   protected transient FloatSortedSet keys;
/*      */   protected transient FloatCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Float> storedComparator;
/*      */   protected transient FloatComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Float2FloatRBTreeMap() {
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
/*   87 */     this.actualComparator = FloatComparators.asFloatComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatRBTreeMap(Comparator<? super Float> c) {
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
/*      */   public Float2FloatRBTreeMap(Map<? extends Float, ? extends Float> m) {
/*  107 */     this();
/*  108 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatRBTreeMap(SortedMap<Float, Float> m) {
/*  117 */     this(m.comparator());
/*  118 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatRBTreeMap(Float2FloatMap m) {
/*  127 */     this();
/*  128 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatRBTreeMap(Float2FloatSortedMap m) {
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
/*      */   public Float2FloatRBTreeMap(float[] k, float[] v, Comparator<? super Float> c) {
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
/*      */   public Float2FloatRBTreeMap(float[] k, float[] v) {
/*  163 */     this(k, v, (Comparator<? super Float>)null);
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
/*      */   final int compare(float k1, float k2) {
/*  189 */     return (this.actualComparator == null) ? Float.compare(k1, k2) : this.actualComparator.compare(k1, k2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry findKey(float k) {
/*  199 */     Entry e = this.tree;
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
/*      */   final Entry locateKey(float k) {
/*  213 */     Entry e = this.tree, last = this.tree;
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
/*      */   private void allocatePaths() {
/*  230 */     this.dirPath = new boolean[64];
/*  231 */     this.nodePath = new Entry[64];
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
/*      */   public float addTo(float k, float incr) {
/*  248 */     Entry e = add(k);
/*  249 */     float oldValue = e.value;
/*  250 */     e.value += incr;
/*  251 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float put(float k, float v) {
/*  256 */     Entry e = add(k);
/*  257 */     float oldValue = e.value;
/*  258 */     e.value = v;
/*  259 */     return oldValue;
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
/*      */   private Entry add(float k) {
/*      */     Entry e;
/*  273 */     this.modified = false;
/*  274 */     int maxDepth = 0;
/*      */     
/*  276 */     if (this.tree == null) {
/*  277 */       this.count++;
/*  278 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry(k, this.defRetValue);
/*      */     } else {
/*  280 */       Entry p = this.tree;
/*  281 */       int i = 0; while (true) {
/*      */         int cmp;
/*  283 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  285 */           for (; i-- != 0; this.nodePath[i] = null);
/*  286 */           return p;
/*      */         } 
/*  288 */         this.nodePath[i] = p;
/*  289 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  290 */           if (p.succ()) {
/*  291 */             this.count++;
/*  292 */             e = new Entry(k, this.defRetValue);
/*  293 */             if (p.right == null) this.lastEntry = e; 
/*  294 */             e.left = p;
/*  295 */             e.right = p.right;
/*  296 */             p.right(e);
/*      */             break;
/*      */           } 
/*  299 */           p = p.right; continue;
/*      */         } 
/*  301 */         if (p.pred()) {
/*  302 */           this.count++;
/*  303 */           e = new Entry(k, this.defRetValue);
/*  304 */           if (p.left == null) this.firstEntry = e; 
/*  305 */           e.right = p;
/*  306 */           e.left = p.left;
/*  307 */           p.left(e);
/*      */           break;
/*      */         } 
/*  310 */         p = p.left;
/*      */       } 
/*      */       
/*  313 */       this.modified = true;
/*  314 */       maxDepth = i--;
/*  315 */       while (i > 0 && !this.nodePath[i].black()) {
/*  316 */         if (!this.dirPath[i - 1]) {
/*  317 */           Entry entry1 = (this.nodePath[i - 1]).right;
/*  318 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  319 */             this.nodePath[i].black(true);
/*  320 */             entry1.black(true);
/*  321 */             this.nodePath[i - 1].black(false);
/*  322 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  325 */           if (!this.dirPath[i]) { entry1 = this.nodePath[i]; }
/*      */           else
/*  327 */           { Entry entry = this.nodePath[i];
/*  328 */             entry1 = entry.right;
/*  329 */             entry.right = entry1.left;
/*  330 */             entry1.left = entry;
/*  331 */             (this.nodePath[i - 1]).left = entry1;
/*  332 */             if (entry1.pred()) {
/*  333 */               entry1.pred(false);
/*  334 */               entry.succ(entry1);
/*      */             }  }
/*      */           
/*  337 */           Entry entry2 = this.nodePath[i - 1];
/*  338 */           entry2.black(false);
/*  339 */           entry1.black(true);
/*  340 */           entry2.left = entry1.right;
/*  341 */           entry1.right = entry2;
/*  342 */           if (i < 2) { this.tree = entry1; }
/*      */           
/*  344 */           else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = entry1; }
/*  345 */           else { (this.nodePath[i - 2]).left = entry1; }
/*      */           
/*  347 */           if (entry1.succ()) {
/*  348 */             entry1.succ(false);
/*  349 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  354 */         Entry y = (this.nodePath[i - 1]).left;
/*  355 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  356 */           this.nodePath[i].black(true);
/*  357 */           y.black(true);
/*  358 */           this.nodePath[i - 1].black(false);
/*  359 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  362 */         if (this.dirPath[i]) { y = this.nodePath[i]; }
/*      */         else
/*  364 */         { Entry entry = this.nodePath[i];
/*  365 */           y = entry.left;
/*  366 */           entry.left = y.right;
/*  367 */           y.right = entry;
/*  368 */           (this.nodePath[i - 1]).right = y;
/*  369 */           if (y.succ()) {
/*  370 */             y.succ(false);
/*  371 */             entry.pred(y);
/*      */           }  }
/*      */         
/*  374 */         Entry x = this.nodePath[i - 1];
/*  375 */         x.black(false);
/*  376 */         y.black(true);
/*  377 */         x.right = y.left;
/*  378 */         y.left = x;
/*  379 */         if (i < 2) { this.tree = y; }
/*      */         
/*  381 */         else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = y; }
/*  382 */         else { (this.nodePath[i - 2]).left = y; }
/*      */         
/*  384 */         if (y.pred()) {
/*  385 */           y.pred(false);
/*  386 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  393 */     this.tree.black(true);
/*      */     
/*  395 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  396 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float remove(float k) {
/*  403 */     this.modified = false;
/*  404 */     if (this.tree == null) return this.defRetValue; 
/*  405 */     Entry p = this.tree;
/*      */     
/*  407 */     int i = 0;
/*  408 */     float kk = k;
/*      */     int cmp;
/*  410 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  411 */       this.dirPath[i] = (cmp > 0);
/*  412 */       this.nodePath[i] = p;
/*  413 */       if (this.dirPath[i++]) {
/*  414 */         if ((p = p.right()) == null) {
/*      */           
/*  416 */           for (; i-- != 0; this.nodePath[i] = null);
/*  417 */           return this.defRetValue;
/*      */         }  continue;
/*      */       } 
/*  420 */       if ((p = p.left()) == null) {
/*      */         
/*  422 */         for (; i-- != 0; this.nodePath[i] = null);
/*  423 */         return this.defRetValue;
/*      */       } 
/*      */     } 
/*      */     
/*  427 */     if (p.left == null) this.firstEntry = p.next(); 
/*  428 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  429 */     if (p.succ()) {
/*  430 */       if (p.pred()) {
/*  431 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  433 */         else if (this.dirPath[i - 1]) { this.nodePath[i - 1].succ(p.right); }
/*  434 */         else { this.nodePath[i - 1].pred(p.left); }
/*      */       
/*      */       } else {
/*  437 */         (p.prev()).right = p.right;
/*  438 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  440 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = p.left; }
/*  441 */         else { (this.nodePath[i - 1]).left = p.left; }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  446 */       Entry r = p.right;
/*  447 */       if (r.pred()) {
/*  448 */         r.left = p.left;
/*  449 */         r.pred(p.pred());
/*  450 */         if (!r.pred()) (r.prev()).right = r; 
/*  451 */         if (i == 0) { this.tree = r; }
/*      */         
/*  453 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = r; }
/*  454 */         else { (this.nodePath[i - 1]).left = r; }
/*      */         
/*  456 */         boolean color = r.black();
/*  457 */         r.black(p.black());
/*  458 */         p.black(color);
/*  459 */         this.dirPath[i] = true;
/*  460 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry s;
/*  463 */         int j = i++;
/*      */         while (true) {
/*  465 */           this.dirPath[i] = false;
/*  466 */           this.nodePath[i++] = r;
/*  467 */           s = r.left;
/*  468 */           if (s.pred())
/*  469 */             break;  r = s;
/*      */         } 
/*  471 */         this.dirPath[j] = true;
/*  472 */         this.nodePath[j] = s;
/*  473 */         if (s.succ()) { r.pred(s); }
/*  474 */         else { r.left = s.right; }
/*  475 */          s.left = p.left;
/*  476 */         if (!p.pred()) {
/*  477 */           (p.prev()).right = s;
/*  478 */           s.pred(false);
/*      */         } 
/*  480 */         s.right(p.right);
/*  481 */         boolean color = s.black();
/*  482 */         s.black(p.black());
/*  483 */         p.black(color);
/*  484 */         if (j == 0) { this.tree = s; }
/*      */         
/*  486 */         else if (this.dirPath[j - 1]) { (this.nodePath[j - 1]).right = s; }
/*  487 */         else { (this.nodePath[j - 1]).left = s; }
/*      */       
/*      */       } 
/*      */     } 
/*  491 */     int maxDepth = i;
/*  492 */     if (p.black()) {
/*  493 */       for (; i > 0; i--) {
/*  494 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  495 */           Entry x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  496 */           if (!x.black()) {
/*  497 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  501 */         if (!this.dirPath[i - 1]) {
/*  502 */           Entry w = (this.nodePath[i - 1]).right;
/*  503 */           if (!w.black()) {
/*  504 */             w.black(true);
/*  505 */             this.nodePath[i - 1].black(false);
/*  506 */             (this.nodePath[i - 1]).right = w.left;
/*  507 */             w.left = this.nodePath[i - 1];
/*  508 */             if (i < 2) { this.tree = w; }
/*      */             
/*  510 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  511 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  513 */             this.nodePath[i] = this.nodePath[i - 1];
/*  514 */             this.dirPath[i] = false;
/*  515 */             this.nodePath[i - 1] = w;
/*  516 */             if (maxDepth == i++) maxDepth++; 
/*  517 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  519 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  520 */             w.black(false);
/*      */           } else {
/*  522 */             if (w.succ() || w.right.black()) {
/*  523 */               Entry y = w.left;
/*  524 */               y.black(true);
/*  525 */               w.black(false);
/*  526 */               w.left = y.right;
/*  527 */               y.right = w;
/*  528 */               w = (this.nodePath[i - 1]).right = y;
/*  529 */               if (w.succ()) {
/*  530 */                 w.succ(false);
/*  531 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  534 */             w.black(this.nodePath[i - 1].black());
/*  535 */             this.nodePath[i - 1].black(true);
/*  536 */             w.right.black(true);
/*  537 */             (this.nodePath[i - 1]).right = w.left;
/*  538 */             w.left = this.nodePath[i - 1];
/*  539 */             if (i < 2) { this.tree = w; }
/*      */             
/*  541 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  542 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  544 */             if (w.pred()) {
/*  545 */               w.pred(false);
/*  546 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  551 */           Entry w = (this.nodePath[i - 1]).left;
/*  552 */           if (!w.black()) {
/*  553 */             w.black(true);
/*  554 */             this.nodePath[i - 1].black(false);
/*  555 */             (this.nodePath[i - 1]).left = w.right;
/*  556 */             w.right = this.nodePath[i - 1];
/*  557 */             if (i < 2) { this.tree = w; }
/*      */             
/*  559 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  560 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  562 */             this.nodePath[i] = this.nodePath[i - 1];
/*  563 */             this.dirPath[i] = true;
/*  564 */             this.nodePath[i - 1] = w;
/*  565 */             if (maxDepth == i++) maxDepth++; 
/*  566 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  568 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  569 */             w.black(false);
/*      */           } else {
/*  571 */             if (w.pred() || w.left.black()) {
/*  572 */               Entry y = w.right;
/*  573 */               y.black(true);
/*  574 */               w.black(false);
/*  575 */               w.right = y.left;
/*  576 */               y.left = w;
/*  577 */               w = (this.nodePath[i - 1]).left = y;
/*  578 */               if (w.pred()) {
/*  579 */                 w.pred(false);
/*  580 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  583 */             w.black(this.nodePath[i - 1].black());
/*  584 */             this.nodePath[i - 1].black(true);
/*  585 */             w.left.black(true);
/*  586 */             (this.nodePath[i - 1]).left = w.right;
/*  587 */             w.right = this.nodePath[i - 1];
/*  588 */             if (i < 2) { this.tree = w; }
/*      */             
/*  590 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  591 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  593 */             if (w.succ()) {
/*  594 */               w.succ(false);
/*  595 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  601 */       if (this.tree != null) this.tree.black(true); 
/*      */     } 
/*  603 */     this.modified = true;
/*  604 */     this.count--;
/*      */     
/*  606 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  607 */     return p.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(float v) {
/*  612 */     ValueIterator i = new ValueIterator();
/*      */     
/*  614 */     int j = this.count;
/*  615 */     while (j-- != 0) {
/*  616 */       float ev = i.nextFloat();
/*  617 */       if (Float.floatToIntBits(ev) == Float.floatToIntBits(v)) return true; 
/*      */     } 
/*  619 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  624 */     this.count = 0;
/*  625 */     this.tree = null;
/*  626 */     this.entries = null;
/*  627 */     this.values = null;
/*  628 */     this.keys = null;
/*  629 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
/*      */     extends AbstractFloat2FloatMap.BasicEntry
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
/*      */     Entry left;
/*      */ 
/*      */     
/*      */     Entry right;
/*      */ 
/*      */     
/*      */     int info;
/*      */ 
/*      */ 
/*      */     
/*      */     Entry() {
/*  658 */       super(0.0F, 0.0F);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(float k, float v) {
/*  668 */       super(k, v);
/*  669 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  678 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  687 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  696 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  705 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  714 */       if (pred) { this.info |= 0x40000000; }
/*  715 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  724 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  725 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  734 */       this.info |= 0x40000000;
/*  735 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  744 */       this.info |= Integer.MIN_VALUE;
/*  745 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  754 */       this.info &= 0xBFFFFFFF;
/*  755 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  764 */       this.info &= Integer.MAX_VALUE;
/*  765 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  774 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  783 */       if (black) { this.info |= 0x1; }
/*  784 */       else { this.info &= 0xFFFFFFFE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  793 */       Entry next = this.right;
/*  794 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  795 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  804 */       Entry prev = this.left;
/*  805 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  806 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public float setValue(float value) {
/*  811 */       float oldValue = this.value;
/*  812 */       this.value = value;
/*  813 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  821 */         c = (Entry)super.clone();
/*  822 */       } catch (CloneNotSupportedException cantHappen) {
/*  823 */         throw new InternalError();
/*      */       } 
/*  825 */       c.key = this.key;
/*  826 */       c.value = this.value;
/*  827 */       c.info = this.info;
/*  828 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  834 */       if (!(o instanceof Map.Entry)) return false; 
/*  835 */       Map.Entry<Float, Float> e = (Map.Entry<Float, Float>)o;
/*  836 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  841 */       return HashCommon.float2int(this.key) ^ HashCommon.float2int(this.value);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  846 */       return this.key + "=>" + this.value;
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
/*      */   public boolean containsKey(float k) {
/*  882 */     return (findKey(k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  887 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  892 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public float get(float k) {
/*  897 */     Entry e = findKey(k);
/*  898 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public float firstFloatKey() {
/*  903 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  904 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public float lastFloatKey() {
/*  909 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  910 */     return this.lastEntry.key;
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
/*      */     Float2FloatRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2FloatRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2FloatRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  939 */     int index = 0;
/*      */     
/*      */     TreeIterator() {
/*  942 */       this.next = Float2FloatRBTreeMap.this.firstEntry;
/*      */     }
/*      */     
/*      */     TreeIterator(float k) {
/*  946 */       if ((this.next = Float2FloatRBTreeMap.this.locateKey(k)) != null)
/*  947 */         if (Float2FloatRBTreeMap.this.compare(this.next.key, k) <= 0)
/*  948 */         { this.prev = this.next;
/*  949 */           this.next = this.next.next(); }
/*  950 */         else { this.prev = this.next.prev(); }
/*      */          
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  955 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  959 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/*  963 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     Float2FloatRBTreeMap.Entry nextEntry() {
/*  967 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  968 */       this.curr = this.prev = this.next;
/*  969 */       this.index++;
/*  970 */       updateNext();
/*  971 */       return this.curr;
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/*  975 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     Float2FloatRBTreeMap.Entry previousEntry() {
/*  979 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  980 */       this.curr = this.next = this.prev;
/*  981 */       this.index--;
/*  982 */       updatePrevious();
/*  983 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  987 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  991 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  995 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/*  998 */       if (this.curr == this.prev) this.index--; 
/*  999 */       this.next = this.prev = this.curr;
/* 1000 */       updatePrevious();
/* 1001 */       updateNext();
/* 1002 */       Float2FloatRBTreeMap.this.remove(this.curr.key);
/* 1003 */       this.curr = null;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1007 */       int i = n;
/* 1008 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1009 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1013 */       int i = n;
/* 1014 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1015 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Float2FloatMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     EntryIterator(float k) {
/* 1030 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2FloatMap.Entry next() {
/* 1035 */       return nextEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2FloatMap.Entry previous() {
/* 1040 */       return previousEntry();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<Float2FloatMap.Entry> float2FloatEntrySet() {
/* 1047 */     if (this.entries == null) this.entries = (ObjectSortedSet<Float2FloatMap.Entry>)new AbstractObjectSortedSet<Float2FloatMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Float2FloatMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Float2FloatMap.Entry> comparator() {
/* 1052 */             return this.comparator;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2FloatMap.Entry> iterator() {
/* 1057 */             return (ObjectBidirectionalIterator<Float2FloatMap.Entry>)new Float2FloatRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2FloatMap.Entry> iterator(Float2FloatMap.Entry from) {
/* 1062 */             return (ObjectBidirectionalIterator<Float2FloatMap.Entry>)new Float2FloatRBTreeMap.EntryIterator(from.getFloatKey());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1068 */             if (o == null || !(o instanceof Map.Entry)) return false; 
/* 1069 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1070 */             if (e.getKey() == null) return false; 
/* 1071 */             if (!(e.getKey() instanceof Float)) return false; 
/* 1072 */             if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 1073 */             Float2FloatRBTreeMap.Entry f = Float2FloatRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1074 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1080 */             if (!(o instanceof Map.Entry)) return false; 
/* 1081 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1082 */             if (e.getKey() == null) return false; 
/* 1083 */             if (!(e.getKey() instanceof Float)) return false; 
/* 1084 */             if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 1085 */             Float2FloatRBTreeMap.Entry f = Float2FloatRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1086 */             if (f == null || Float.floatToIntBits(f.getFloatValue()) != Float.floatToIntBits(((Float)e.getValue()).floatValue())) return false; 
/* 1087 */             Float2FloatRBTreeMap.this.remove(f.key);
/* 1088 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1093 */             return Float2FloatRBTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1098 */             Float2FloatRBTreeMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public Float2FloatMap.Entry first() {
/* 1103 */             return Float2FloatRBTreeMap.this.firstEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public Float2FloatMap.Entry last() {
/* 1108 */             return Float2FloatRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Float2FloatMap.Entry> subSet(Float2FloatMap.Entry from, Float2FloatMap.Entry to) {
/* 1113 */             return Float2FloatRBTreeMap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2FloatEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Float2FloatMap.Entry> headSet(Float2FloatMap.Entry to) {
/* 1118 */             return Float2FloatRBTreeMap.this.headMap(to.getFloatKey()).float2FloatEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Float2FloatMap.Entry> tailSet(Float2FloatMap.Entry from) {
/* 1123 */             return Float2FloatRBTreeMap.this.tailMap(from.getFloatKey()).float2FloatEntrySet();
/*      */           }
/*      */         }; 
/* 1126 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements FloatListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(float k) {
/* 1142 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1147 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public float previousFloat() {
/* 1152 */       return (previousEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractFloat2FloatSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatBidirectionalIterator iterator() {
/* 1160 */       return new Float2FloatRBTreeMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBidirectionalIterator iterator(float from) {
/* 1165 */       return new Float2FloatRBTreeMap.KeyIterator(from);
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
/*      */   public FloatSortedSet keySet() {
/* 1180 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1181 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements FloatListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1195 */       return (nextEntry()).value;
/*      */     }
/*      */ 
/*      */     
/*      */     public float previousFloat() {
/* 1200 */       return (previousEntry()).value;
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
/*      */   public FloatCollection values() {
/* 1215 */     if (this.values == null) this.values = new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1218 */             return new Float2FloatRBTreeMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(float k) {
/* 1223 */             return Float2FloatRBTreeMap.this.containsValue(k);
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1228 */             return Float2FloatRBTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1233 */             Float2FloatRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1236 */     return this.values;
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatComparator comparator() {
/* 1241 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public Float2FloatSortedMap headMap(float to) {
/* 1246 */     return new Submap(0.0F, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public Float2FloatSortedMap tailMap(float from) {
/* 1251 */     return new Submap(from, false, 0.0F, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Float2FloatSortedMap subMap(float from, float to) {
/* 1256 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractFloat2FloatSortedMap
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     float from;
/*      */ 
/*      */ 
/*      */     
/*      */     float to;
/*      */ 
/*      */ 
/*      */     
/*      */     boolean bottom;
/*      */ 
/*      */     
/*      */     boolean top;
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<Float2FloatMap.Entry> entries;
/*      */ 
/*      */     
/*      */     protected transient FloatSortedSet keys;
/*      */ 
/*      */     
/*      */     protected transient FloatCollection values;
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(float from, boolean bottom, float to, boolean top) {
/* 1295 */       if (!bottom && !top && Float2FloatRBTreeMap.this.compare(from, to) > 0) throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1296 */       this.from = from;
/* 1297 */       this.bottom = bottom;
/* 1298 */       this.to = to;
/* 1299 */       this.top = top;
/* 1300 */       this.defRetValue = Float2FloatRBTreeMap.this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1305 */       SubmapIterator i = new SubmapIterator();
/* 1306 */       while (i.hasNext()) {
/* 1307 */         i.nextEntry();
/* 1308 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(float k) {
/* 1319 */       return ((this.bottom || Float2FloatRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Float2FloatRBTreeMap.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Float2FloatMap.Entry> float2FloatEntrySet() {
/* 1324 */       if (this.entries == null) this.entries = (ObjectSortedSet<Float2FloatMap.Entry>)new AbstractObjectSortedSet<Float2FloatMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Float2FloatMap.Entry> iterator() {
/* 1327 */               return (ObjectBidirectionalIterator<Float2FloatMap.Entry>)new Float2FloatRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Float2FloatMap.Entry> iterator(Float2FloatMap.Entry from) {
/* 1332 */               return (ObjectBidirectionalIterator<Float2FloatMap.Entry>)new Float2FloatRBTreeMap.Submap.SubmapEntryIterator(from.getFloatKey());
/*      */             }
/*      */ 
/*      */             
/*      */             public Comparator<? super Float2FloatMap.Entry> comparator() {
/* 1337 */               return Float2FloatRBTreeMap.this.float2FloatEntrySet().comparator();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1343 */               if (!(o instanceof Map.Entry)) return false; 
/* 1344 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1345 */               if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1346 */               if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 1347 */               Float2FloatRBTreeMap.Entry f = Float2FloatRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1348 */               return (f != null && Float2FloatRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1354 */               if (!(o instanceof Map.Entry)) return false; 
/* 1355 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1356 */               if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1357 */               if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 1358 */               Float2FloatRBTreeMap.Entry f = Float2FloatRBTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1359 */               if (f != null && Float2FloatRBTreeMap.Submap.this.in(f.key)) Float2FloatRBTreeMap.Submap.this.remove(f.key); 
/* 1360 */               return (f != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1365 */               int c = 0;
/* 1366 */               for (ObjectBidirectionalIterator<Float2FloatMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); ) { c++; objectBidirectionalIterator.next(); }
/* 1367 */                return c;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1372 */               return !(new Float2FloatRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1377 */               Float2FloatRBTreeMap.Submap.this.clear();
/*      */             }
/*      */ 
/*      */             
/*      */             public Float2FloatMap.Entry first() {
/* 1382 */               return Float2FloatRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public Float2FloatMap.Entry last() {
/* 1387 */               return Float2FloatRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Float2FloatMap.Entry> subSet(Float2FloatMap.Entry from, Float2FloatMap.Entry to) {
/* 1392 */               return Float2FloatRBTreeMap.Submap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2FloatEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Float2FloatMap.Entry> headSet(Float2FloatMap.Entry to) {
/* 1397 */               return Float2FloatRBTreeMap.Submap.this.headMap(to.getFloatKey()).float2FloatEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Float2FloatMap.Entry> tailSet(Float2FloatMap.Entry from) {
/* 1402 */               return Float2FloatRBTreeMap.Submap.this.tailMap(from.getFloatKey()).float2FloatEntrySet();
/*      */             }
/*      */           }; 
/* 1405 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractFloat2FloatSortedMap.KeySet { private KeySet() {}
/*      */       
/*      */       public FloatBidirectionalIterator iterator() {
/* 1411 */         return new Float2FloatRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public FloatBidirectionalIterator iterator(float from) {
/* 1416 */         return new Float2FloatRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public FloatSortedSet keySet() {
/* 1422 */       if (this.keys == null) this.keys = new KeySet(); 
/* 1423 */       return this.keys;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatCollection values() {
/* 1428 */       if (this.values == null) this.values = new AbstractFloatCollection()
/*      */           {
/*      */             public FloatIterator iterator() {
/* 1431 */               return new Float2FloatRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(float k) {
/* 1436 */               return Float2FloatRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1441 */               return Float2FloatRBTreeMap.Submap.this.size();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1446 */               Float2FloatRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1449 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(float k) {
/* 1456 */       return (in(k) && Float2FloatRBTreeMap.this.containsKey(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(float v) {
/* 1461 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1463 */       while (i.hasNext()) {
/* 1464 */         float ev = (i.nextEntry()).value;
/* 1465 */         if (Float.floatToIntBits(ev) == Float.floatToIntBits(v)) return true; 
/*      */       } 
/* 1467 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float get(float k) {
/* 1474 */       float kk = k; Float2FloatRBTreeMap.Entry e;
/* 1475 */       return (in(kk) && (e = Float2FloatRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public float put(float k, float v) {
/* 1480 */       Float2FloatRBTreeMap.this.modified = false;
/* 1481 */       if (!in(k)) throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1482 */       float oldValue = Float2FloatRBTreeMap.this.put(k, v);
/* 1483 */       return Float2FloatRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public float remove(float k) {
/* 1489 */       Float2FloatRBTreeMap.this.modified = false;
/* 1490 */       if (!in(k)) return this.defRetValue; 
/* 1491 */       float oldValue = Float2FloatRBTreeMap.this.remove(k);
/* 1492 */       return Float2FloatRBTreeMap.this.modified ? oldValue : this.defRetValue;
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
/*      */     public FloatComparator comparator() {
/* 1513 */       return Float2FloatRBTreeMap.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2FloatSortedMap headMap(float to) {
/* 1518 */       if (this.top) return new Submap(this.from, this.bottom, to, false); 
/* 1519 */       return (Float2FloatRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2FloatSortedMap tailMap(float from) {
/* 1524 */       if (this.bottom) return new Submap(from, false, this.to, this.top); 
/* 1525 */       return (Float2FloatRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2FloatSortedMap subMap(float from, float to) {
/* 1530 */       if (this.top && this.bottom) return new Submap(from, false, to, false); 
/* 1531 */       if (!this.top) to = (Float2FloatRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1532 */       if (!this.bottom) from = (Float2FloatRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1533 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1534 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2FloatRBTreeMap.Entry firstEntry() {
/*      */       Float2FloatRBTreeMap.Entry e;
/* 1543 */       if (Float2FloatRBTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1547 */       if (this.bottom) { e = Float2FloatRBTreeMap.this.firstEntry; }
/*      */       else
/* 1549 */       { e = Float2FloatRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1551 */         if (Float2FloatRBTreeMap.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1555 */       if (e == null || (!this.top && Float2FloatRBTreeMap.this.compare(e.key, this.to) >= 0)) return null; 
/* 1556 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2FloatRBTreeMap.Entry lastEntry() {
/*      */       Float2FloatRBTreeMap.Entry e;
/* 1565 */       if (Float2FloatRBTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1569 */       if (this.top) { e = Float2FloatRBTreeMap.this.lastEntry; }
/*      */       else
/* 1571 */       { e = Float2FloatRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1573 */         if (Float2FloatRBTreeMap.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1577 */       if (e == null || (!this.bottom && Float2FloatRBTreeMap.this.compare(e.key, this.from) < 0)) return null; 
/* 1578 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public float firstFloatKey() {
/* 1583 */       Float2FloatRBTreeMap.Entry e = firstEntry();
/* 1584 */       if (e == null) throw new NoSuchElementException(); 
/* 1585 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public float lastFloatKey() {
/* 1590 */       Float2FloatRBTreeMap.Entry e = lastEntry();
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
/*      */       extends Float2FloatRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1606 */         this.next = Float2FloatRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubmapIterator(float k) {
/* 1610 */         this();
/* 1611 */         if (this.next != null) {
/* 1612 */           if (!Float2FloatRBTreeMap.Submap.this.bottom && Float2FloatRBTreeMap.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1613 */           else if (!Float2FloatRBTreeMap.Submap.this.top && Float2FloatRBTreeMap.this.compare(k, (this.prev = Float2FloatRBTreeMap.Submap.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1615 */           { this.next = Float2FloatRBTreeMap.this.locateKey(k);
/* 1616 */             if (Float2FloatRBTreeMap.this.compare(this.next.key, k) <= 0)
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
/* 1627 */         if (!Float2FloatRBTreeMap.Submap.this.bottom && this.prev != null && Float2FloatRBTreeMap.this.compare(this.prev.key, Float2FloatRBTreeMap.Submap.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1632 */         this.next = this.next.next();
/* 1633 */         if (!Float2FloatRBTreeMap.Submap.this.top && this.next != null && Float2FloatRBTreeMap.this.compare(this.next.key, Float2FloatRBTreeMap.Submap.this.to) >= 0) this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Float2FloatMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(float k) {
/* 1642 */         super(k);
/*      */       }
/*      */ 
/*      */       
/*      */       public Float2FloatMap.Entry next() {
/* 1647 */         return nextEntry();
/*      */       }
/*      */ 
/*      */       
/*      */       public Float2FloatMap.Entry previous() {
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
/*      */       implements FloatListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(float from) {
/* 1671 */         super(from);
/*      */       }
/*      */ 
/*      */       
/*      */       public float nextFloat() {
/* 1676 */         return (nextEntry()).key;
/*      */       }
/*      */ 
/*      */       
/*      */       public float previousFloat() {
/* 1681 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements FloatListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public float nextFloat() {
/* 1697 */         return (nextEntry()).value;
/*      */       }
/*      */ 
/*      */       
/*      */       public float previousFloat() {
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
/*      */   public Float2FloatRBTreeMap clone() {
/*      */     Float2FloatRBTreeMap c;
/*      */     try {
/* 1721 */       c = (Float2FloatRBTreeMap)super.clone();
/* 1722 */     } catch (CloneNotSupportedException cantHappen) {
/* 1723 */       throw new InternalError();
/*      */     } 
/* 1725 */     c.keys = null;
/* 1726 */     c.values = null;
/* 1727 */     c.entries = null;
/* 1728 */     c.allocatePaths();
/* 1729 */     if (this.count != 0) {
/*      */       
/* 1731 */       Entry rp = new Entry(), rq = new Entry();
/* 1732 */       Entry p = rp;
/* 1733 */       rp.left(this.tree);
/* 1734 */       Entry q = rq;
/* 1735 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1737 */         if (!p.pred()) {
/* 1738 */           Entry e = p.left.clone();
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
/* 1762 */           Entry e = p.right.clone();
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
/* 1778 */       Entry e = i.nextEntry();
/* 1779 */       s.writeFloat(e.key);
/* 1780 */       s.writeFloat(e.value);
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
/*      */   private Entry readTree(ObjectInputStream s, int n, Entry pred, Entry succ) throws IOException, ClassNotFoundException {
/* 1794 */     if (n == 1) {
/* 1795 */       Entry entry = new Entry(s.readFloat(), s.readFloat());
/* 1796 */       entry.pred(pred);
/* 1797 */       entry.succ(succ);
/* 1798 */       entry.black(true);
/* 1799 */       return entry;
/*      */     } 
/* 1801 */     if (n == 2) {
/*      */ 
/*      */       
/* 1804 */       Entry entry = new Entry(s.readFloat(), s.readFloat());
/* 1805 */       entry.black(true);
/* 1806 */       entry.right(new Entry(s.readFloat(), s.readFloat()));
/* 1807 */       entry.right.pred(entry);
/* 1808 */       entry.pred(pred);
/* 1809 */       entry.right.succ(succ);
/* 1810 */       return entry;
/*      */     } 
/*      */     
/* 1813 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1814 */     Entry top = new Entry();
/* 1815 */     top.left(readTree(s, leftN, pred, top));
/* 1816 */     top.key = s.readFloat();
/* 1817 */     top.value = s.readFloat();
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
/* 1832 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1834 */       Entry e = this.tree;
/* 1835 */       for (; e.left() != null; e = e.left());
/* 1836 */       this.firstEntry = e;
/* 1837 */       e = this.tree;
/* 1838 */       for (; e.right() != null; e = e.right());
/* 1839 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2FloatRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */