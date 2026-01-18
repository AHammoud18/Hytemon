/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharListIterator;
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
/*      */ 
/*      */ public class Short2CharRBTreeMap
/*      */   extends AbstractShort2CharSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Short2CharMap.Entry> entries;
/*      */   protected transient ShortSortedSet keys;
/*      */   protected transient CharCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Short> storedComparator;
/*      */   protected transient ShortComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Short2CharRBTreeMap() {
/*   71 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   78 */     this.tree = null;
/*   79 */     this.count = 0;
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
/*   91 */     this.actualComparator = ShortComparators.asShortComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2CharRBTreeMap(Comparator<? super Short> c) {
/*  100 */     this();
/*  101 */     this.storedComparator = c;
/*  102 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2CharRBTreeMap(Map<? extends Short, ? extends Character> m) {
/*  111 */     this();
/*  112 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2CharRBTreeMap(SortedMap<Short, Character> m) {
/*  121 */     this(m.comparator());
/*  122 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2CharRBTreeMap(Short2CharMap m) {
/*  131 */     this();
/*  132 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2CharRBTreeMap(Short2CharSortedMap m) {
/*  141 */     this(m.comparator());
/*  142 */     putAll(m);
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
/*      */   public Short2CharRBTreeMap(short[] k, char[] v, Comparator<? super Short> c) {
/*  154 */     this(c);
/*  155 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  156 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2CharRBTreeMap(short[] k, char[] v) {
/*  167 */     this(k, v, (Comparator<? super Short>)null);
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
/*      */   final int compare(short k1, short k2) {
/*  193 */     return (this.actualComparator == null) ? Short.compare(k1, k2) : this.actualComparator.compare(k1, k2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry findKey(short k) {
/*  203 */     Entry e = this.tree;
/*      */     int cmp;
/*  205 */     for (; e != null && (cmp = compare(k, e.key)) != 0; e = (cmp < 0) ? e.left() : e.right());
/*  206 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry locateKey(short k) {
/*  217 */     Entry e = this.tree, last = this.tree;
/*  218 */     int cmp = 0;
/*  219 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  220 */       last = e;
/*  221 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  223 */     return (cmp == 0) ? e : last;
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
/*  234 */     this.dirPath = new boolean[64];
/*  235 */     this.nodePath = new Entry[64];
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
/*      */   public char addTo(short k, char incr) {
/*  252 */     Entry e = add(k);
/*  253 */     char oldValue = e.value;
/*  254 */     e.value = (char)(e.value + incr);
/*  255 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char put(short k, char v) {
/*  260 */     Entry e = add(k);
/*  261 */     char oldValue = e.value;
/*  262 */     e.value = v;
/*  263 */     return oldValue;
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
/*      */   private Entry add(short k) {
/*      */     Entry e;
/*  277 */     this.modified = false;
/*  278 */     int maxDepth = 0;
/*      */     
/*  280 */     if (this.tree == null) {
/*  281 */       this.count++;
/*  282 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry(k, this.defRetValue);
/*      */     } else {
/*  284 */       Entry p = this.tree;
/*  285 */       int i = 0; while (true) {
/*      */         int cmp;
/*  287 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  289 */           for (; i-- != 0; this.nodePath[i] = null);
/*  290 */           return p;
/*      */         } 
/*  292 */         this.nodePath[i] = p;
/*  293 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  294 */           if (p.succ()) {
/*  295 */             this.count++;
/*  296 */             e = new Entry(k, this.defRetValue);
/*  297 */             if (p.right == null) this.lastEntry = e; 
/*  298 */             e.left = p;
/*  299 */             e.right = p.right;
/*  300 */             p.right(e);
/*      */             break;
/*      */           } 
/*  303 */           p = p.right; continue;
/*      */         } 
/*  305 */         if (p.pred()) {
/*  306 */           this.count++;
/*  307 */           e = new Entry(k, this.defRetValue);
/*  308 */           if (p.left == null) this.firstEntry = e; 
/*  309 */           e.right = p;
/*  310 */           e.left = p.left;
/*  311 */           p.left(e);
/*      */           break;
/*      */         } 
/*  314 */         p = p.left;
/*      */       } 
/*      */       
/*  317 */       this.modified = true;
/*  318 */       maxDepth = i--;
/*  319 */       while (i > 0 && !this.nodePath[i].black()) {
/*  320 */         if (!this.dirPath[i - 1]) {
/*  321 */           Entry entry1 = (this.nodePath[i - 1]).right;
/*  322 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  323 */             this.nodePath[i].black(true);
/*  324 */             entry1.black(true);
/*  325 */             this.nodePath[i - 1].black(false);
/*  326 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  329 */           if (!this.dirPath[i]) { entry1 = this.nodePath[i]; }
/*      */           else
/*  331 */           { Entry entry = this.nodePath[i];
/*  332 */             entry1 = entry.right;
/*  333 */             entry.right = entry1.left;
/*  334 */             entry1.left = entry;
/*  335 */             (this.nodePath[i - 1]).left = entry1;
/*  336 */             if (entry1.pred()) {
/*  337 */               entry1.pred(false);
/*  338 */               entry.succ(entry1);
/*      */             }  }
/*      */           
/*  341 */           Entry entry2 = this.nodePath[i - 1];
/*  342 */           entry2.black(false);
/*  343 */           entry1.black(true);
/*  344 */           entry2.left = entry1.right;
/*  345 */           entry1.right = entry2;
/*  346 */           if (i < 2) { this.tree = entry1; }
/*      */           
/*  348 */           else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = entry1; }
/*  349 */           else { (this.nodePath[i - 2]).left = entry1; }
/*      */           
/*  351 */           if (entry1.succ()) {
/*  352 */             entry1.succ(false);
/*  353 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  358 */         Entry y = (this.nodePath[i - 1]).left;
/*  359 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  360 */           this.nodePath[i].black(true);
/*  361 */           y.black(true);
/*  362 */           this.nodePath[i - 1].black(false);
/*  363 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  366 */         if (this.dirPath[i]) { y = this.nodePath[i]; }
/*      */         else
/*  368 */         { Entry entry = this.nodePath[i];
/*  369 */           y = entry.left;
/*  370 */           entry.left = y.right;
/*  371 */           y.right = entry;
/*  372 */           (this.nodePath[i - 1]).right = y;
/*  373 */           if (y.succ()) {
/*  374 */             y.succ(false);
/*  375 */             entry.pred(y);
/*      */           }  }
/*      */         
/*  378 */         Entry x = this.nodePath[i - 1];
/*  379 */         x.black(false);
/*  380 */         y.black(true);
/*  381 */         x.right = y.left;
/*  382 */         y.left = x;
/*  383 */         if (i < 2) { this.tree = y; }
/*      */         
/*  385 */         else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = y; }
/*  386 */         else { (this.nodePath[i - 2]).left = y; }
/*      */         
/*  388 */         if (y.pred()) {
/*  389 */           y.pred(false);
/*  390 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  397 */     this.tree.black(true);
/*      */     
/*  399 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  400 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char remove(short k) {
/*  407 */     this.modified = false;
/*  408 */     if (this.tree == null) return this.defRetValue; 
/*  409 */     Entry p = this.tree;
/*      */     
/*  411 */     int i = 0;
/*  412 */     short kk = k;
/*      */     int cmp;
/*  414 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  415 */       this.dirPath[i] = (cmp > 0);
/*  416 */       this.nodePath[i] = p;
/*  417 */       if (this.dirPath[i++]) {
/*  418 */         if ((p = p.right()) == null) {
/*      */           
/*  420 */           for (; i-- != 0; this.nodePath[i] = null);
/*  421 */           return this.defRetValue;
/*      */         }  continue;
/*      */       } 
/*  424 */       if ((p = p.left()) == null) {
/*      */         
/*  426 */         for (; i-- != 0; this.nodePath[i] = null);
/*  427 */         return this.defRetValue;
/*      */       } 
/*      */     } 
/*      */     
/*  431 */     if (p.left == null) this.firstEntry = p.next(); 
/*  432 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  433 */     if (p.succ()) {
/*  434 */       if (p.pred()) {
/*  435 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  437 */         else if (this.dirPath[i - 1]) { this.nodePath[i - 1].succ(p.right); }
/*  438 */         else { this.nodePath[i - 1].pred(p.left); }
/*      */       
/*      */       } else {
/*  441 */         (p.prev()).right = p.right;
/*  442 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  444 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = p.left; }
/*  445 */         else { (this.nodePath[i - 1]).left = p.left; }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  450 */       Entry r = p.right;
/*  451 */       if (r.pred()) {
/*  452 */         r.left = p.left;
/*  453 */         r.pred(p.pred());
/*  454 */         if (!r.pred()) (r.prev()).right = r; 
/*  455 */         if (i == 0) { this.tree = r; }
/*      */         
/*  457 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = r; }
/*  458 */         else { (this.nodePath[i - 1]).left = r; }
/*      */         
/*  460 */         boolean color = r.black();
/*  461 */         r.black(p.black());
/*  462 */         p.black(color);
/*  463 */         this.dirPath[i] = true;
/*  464 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry s;
/*  467 */         int j = i++;
/*      */         while (true) {
/*  469 */           this.dirPath[i] = false;
/*  470 */           this.nodePath[i++] = r;
/*  471 */           s = r.left;
/*  472 */           if (s.pred())
/*  473 */             break;  r = s;
/*      */         } 
/*  475 */         this.dirPath[j] = true;
/*  476 */         this.nodePath[j] = s;
/*  477 */         if (s.succ()) { r.pred(s); }
/*  478 */         else { r.left = s.right; }
/*  479 */          s.left = p.left;
/*  480 */         if (!p.pred()) {
/*  481 */           (p.prev()).right = s;
/*  482 */           s.pred(false);
/*      */         } 
/*  484 */         s.right(p.right);
/*  485 */         boolean color = s.black();
/*  486 */         s.black(p.black());
/*  487 */         p.black(color);
/*  488 */         if (j == 0) { this.tree = s; }
/*      */         
/*  490 */         else if (this.dirPath[j - 1]) { (this.nodePath[j - 1]).right = s; }
/*  491 */         else { (this.nodePath[j - 1]).left = s; }
/*      */       
/*      */       } 
/*      */     } 
/*  495 */     int maxDepth = i;
/*  496 */     if (p.black()) {
/*  497 */       for (; i > 0; i--) {
/*  498 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  499 */           Entry x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  500 */           if (!x.black()) {
/*  501 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  505 */         if (!this.dirPath[i - 1]) {
/*  506 */           Entry w = (this.nodePath[i - 1]).right;
/*  507 */           if (!w.black()) {
/*  508 */             w.black(true);
/*  509 */             this.nodePath[i - 1].black(false);
/*  510 */             (this.nodePath[i - 1]).right = w.left;
/*  511 */             w.left = this.nodePath[i - 1];
/*  512 */             if (i < 2) { this.tree = w; }
/*      */             
/*  514 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  515 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  517 */             this.nodePath[i] = this.nodePath[i - 1];
/*  518 */             this.dirPath[i] = false;
/*  519 */             this.nodePath[i - 1] = w;
/*  520 */             if (maxDepth == i++) maxDepth++; 
/*  521 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  523 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  524 */             w.black(false);
/*      */           } else {
/*  526 */             if (w.succ() || w.right.black()) {
/*  527 */               Entry y = w.left;
/*  528 */               y.black(true);
/*  529 */               w.black(false);
/*  530 */               w.left = y.right;
/*  531 */               y.right = w;
/*  532 */               w = (this.nodePath[i - 1]).right = y;
/*  533 */               if (w.succ()) {
/*  534 */                 w.succ(false);
/*  535 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  538 */             w.black(this.nodePath[i - 1].black());
/*  539 */             this.nodePath[i - 1].black(true);
/*  540 */             w.right.black(true);
/*  541 */             (this.nodePath[i - 1]).right = w.left;
/*  542 */             w.left = this.nodePath[i - 1];
/*  543 */             if (i < 2) { this.tree = w; }
/*      */             
/*  545 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  546 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  548 */             if (w.pred()) {
/*  549 */               w.pred(false);
/*  550 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  555 */           Entry w = (this.nodePath[i - 1]).left;
/*  556 */           if (!w.black()) {
/*  557 */             w.black(true);
/*  558 */             this.nodePath[i - 1].black(false);
/*  559 */             (this.nodePath[i - 1]).left = w.right;
/*  560 */             w.right = this.nodePath[i - 1];
/*  561 */             if (i < 2) { this.tree = w; }
/*      */             
/*  563 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  564 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  566 */             this.nodePath[i] = this.nodePath[i - 1];
/*  567 */             this.dirPath[i] = true;
/*  568 */             this.nodePath[i - 1] = w;
/*  569 */             if (maxDepth == i++) maxDepth++; 
/*  570 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  572 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  573 */             w.black(false);
/*      */           } else {
/*  575 */             if (w.pred() || w.left.black()) {
/*  576 */               Entry y = w.right;
/*  577 */               y.black(true);
/*  578 */               w.black(false);
/*  579 */               w.right = y.left;
/*  580 */               y.left = w;
/*  581 */               w = (this.nodePath[i - 1]).left = y;
/*  582 */               if (w.pred()) {
/*  583 */                 w.pred(false);
/*  584 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  587 */             w.black(this.nodePath[i - 1].black());
/*  588 */             this.nodePath[i - 1].black(true);
/*  589 */             w.left.black(true);
/*  590 */             (this.nodePath[i - 1]).left = w.right;
/*  591 */             w.right = this.nodePath[i - 1];
/*  592 */             if (i < 2) { this.tree = w; }
/*      */             
/*  594 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  595 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  597 */             if (w.succ()) {
/*  598 */               w.succ(false);
/*  599 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  605 */       if (this.tree != null) this.tree.black(true); 
/*      */     } 
/*  607 */     this.modified = true;
/*  608 */     this.count--;
/*      */     
/*  610 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  611 */     return p.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(char v) {
/*  616 */     ValueIterator i = new ValueIterator();
/*      */     
/*  618 */     int j = this.count;
/*  619 */     while (j-- != 0) {
/*  620 */       char ev = i.nextChar();
/*  621 */       if (ev == v) return true; 
/*      */     } 
/*  623 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  628 */     this.count = 0;
/*  629 */     this.tree = null;
/*  630 */     this.entries = null;
/*  631 */     this.values = null;
/*  632 */     this.keys = null;
/*  633 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
/*      */     extends AbstractShort2CharMap.BasicEntry
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
/*  662 */       super((short)0, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(short k, char v) {
/*  672 */       super(k, v);
/*  673 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  682 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  691 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  700 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  709 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  718 */       if (pred) { this.info |= 0x40000000; }
/*  719 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  728 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  729 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  738 */       this.info |= 0x40000000;
/*  739 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  748 */       this.info |= Integer.MIN_VALUE;
/*  749 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  758 */       this.info &= 0xBFFFFFFF;
/*  759 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  768 */       this.info &= Integer.MAX_VALUE;
/*  769 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  778 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  787 */       if (black) { this.info |= 0x1; }
/*  788 */       else { this.info &= 0xFFFFFFFE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  797 */       Entry next = this.right;
/*  798 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  799 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  808 */       Entry prev = this.left;
/*  809 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  810 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public char setValue(char value) {
/*  815 */       char oldValue = this.value;
/*  816 */       this.value = value;
/*  817 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  825 */         c = (Entry)super.clone();
/*  826 */       } catch (CloneNotSupportedException cantHappen) {
/*  827 */         throw new InternalError();
/*      */       } 
/*  829 */       c.key = this.key;
/*  830 */       c.value = this.value;
/*  831 */       c.info = this.info;
/*  832 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  838 */       if (!(o instanceof Map.Entry)) return false; 
/*  839 */       Map.Entry<Short, Character> e = (Map.Entry<Short, Character>)o;
/*  840 */       return (this.key == ((Short)e.getKey()).shortValue() && this.value == ((Character)e.getValue()).charValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  845 */       return this.key ^ this.value;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  850 */       return this.key + "=>" + this.value;
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
/*      */   public boolean containsKey(short k) {
/*  886 */     return (findKey(k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  891 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  896 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public char get(short k) {
/*  901 */     Entry e = findKey(k);
/*  902 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public short firstShortKey() {
/*  907 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  908 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public short lastShortKey() {
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
/*      */     Short2CharRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Short2CharRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Short2CharRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  943 */     int index = 0;
/*      */     
/*      */     TreeIterator() {
/*  946 */       this.next = Short2CharRBTreeMap.this.firstEntry;
/*      */     }
/*      */     
/*      */     TreeIterator(short k) {
/*  950 */       if ((this.next = Short2CharRBTreeMap.this.locateKey(k)) != null)
/*  951 */         if (Short2CharRBTreeMap.this.compare(this.next.key, k) <= 0)
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
/*      */     Short2CharRBTreeMap.Entry nextEntry() {
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
/*      */     Short2CharRBTreeMap.Entry previousEntry() {
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
/* 1006 */       Short2CharRBTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Short2CharMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     EntryIterator(short k) {
/* 1034 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public Short2CharMap.Entry next() {
/* 1039 */       return nextEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Short2CharMap.Entry previous() {
/* 1044 */       return previousEntry();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<Short2CharMap.Entry> short2CharEntrySet() {
/* 1051 */     if (this.entries == null) this.entries = (ObjectSortedSet<Short2CharMap.Entry>)new AbstractObjectSortedSet<Short2CharMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Short2CharMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Short2CharMap.Entry> comparator() {
/* 1056 */             return this.comparator;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Short2CharMap.Entry> iterator() {
/* 1061 */             return (ObjectBidirectionalIterator<Short2CharMap.Entry>)new Short2CharRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Short2CharMap.Entry> iterator(Short2CharMap.Entry from) {
/* 1066 */             return (ObjectBidirectionalIterator<Short2CharMap.Entry>)new Short2CharRBTreeMap.EntryIterator(from.getShortKey());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1072 */             if (o == null || !(o instanceof Map.Entry)) return false; 
/* 1073 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1074 */             if (e.getKey() == null) return false; 
/* 1075 */             if (!(e.getKey() instanceof Short)) return false; 
/* 1076 */             if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 1077 */             Short2CharRBTreeMap.Entry f = Short2CharRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1078 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1084 */             if (!(o instanceof Map.Entry)) return false; 
/* 1085 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1086 */             if (e.getKey() == null) return false; 
/* 1087 */             if (!(e.getKey() instanceof Short)) return false; 
/* 1088 */             if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 1089 */             Short2CharRBTreeMap.Entry f = Short2CharRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1090 */             if (f == null || f.getCharValue() != ((Character)e.getValue()).charValue()) return false; 
/* 1091 */             Short2CharRBTreeMap.this.remove(f.key);
/* 1092 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1097 */             return Short2CharRBTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1102 */             Short2CharRBTreeMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public Short2CharMap.Entry first() {
/* 1107 */             return Short2CharRBTreeMap.this.firstEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public Short2CharMap.Entry last() {
/* 1112 */             return Short2CharRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Short2CharMap.Entry> subSet(Short2CharMap.Entry from, Short2CharMap.Entry to) {
/* 1117 */             return Short2CharRBTreeMap.this.subMap(from.getShortKey(), to.getShortKey()).short2CharEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Short2CharMap.Entry> headSet(Short2CharMap.Entry to) {
/* 1122 */             return Short2CharRBTreeMap.this.headMap(to.getShortKey()).short2CharEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Short2CharMap.Entry> tailSet(Short2CharMap.Entry from) {
/* 1127 */             return Short2CharRBTreeMap.this.tailMap(from.getShortKey()).short2CharEntrySet();
/*      */           }
/*      */         }; 
/* 1130 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements ShortListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(short k) {
/* 1146 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public short nextShort() {
/* 1151 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public short previousShort() {
/* 1156 */       return (previousEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractShort2CharSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ShortBidirectionalIterator iterator() {
/* 1164 */       return new Short2CharRBTreeMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortBidirectionalIterator iterator(short from) {
/* 1169 */       return new Short2CharRBTreeMap.KeyIterator(from);
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
/*      */   public ShortSortedSet keySet() {
/* 1184 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1185 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements CharListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1199 */       return (nextEntry()).value;
/*      */     }
/*      */ 
/*      */     
/*      */     public char previousChar() {
/* 1204 */       return (previousEntry()).value;
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
/*      */   public CharCollection values() {
/* 1219 */     if (this.values == null) this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1222 */             return (CharIterator)new Short2CharRBTreeMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(char k) {
/* 1227 */             return Short2CharRBTreeMap.this.containsValue(k);
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1232 */             return Short2CharRBTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1237 */             Short2CharRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1240 */     return this.values;
/*      */   }
/*      */ 
/*      */   
/*      */   public ShortComparator comparator() {
/* 1245 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public Short2CharSortedMap headMap(short to) {
/* 1250 */     return new Submap((short)0, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public Short2CharSortedMap tailMap(short from) {
/* 1255 */     return new Submap(from, false, (short)0, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Short2CharSortedMap subMap(short from, short to) {
/* 1260 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractShort2CharSortedMap
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     short from;
/*      */ 
/*      */ 
/*      */     
/*      */     short to;
/*      */ 
/*      */ 
/*      */     
/*      */     boolean bottom;
/*      */ 
/*      */     
/*      */     boolean top;
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<Short2CharMap.Entry> entries;
/*      */ 
/*      */     
/*      */     protected transient ShortSortedSet keys;
/*      */ 
/*      */     
/*      */     protected transient CharCollection values;
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(short from, boolean bottom, short to, boolean top) {
/* 1299 */       if (!bottom && !top && Short2CharRBTreeMap.this.compare(from, to) > 0) throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1300 */       this.from = from;
/* 1301 */       this.bottom = bottom;
/* 1302 */       this.to = to;
/* 1303 */       this.top = top;
/* 1304 */       this.defRetValue = Short2CharRBTreeMap.this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1309 */       SubmapIterator i = new SubmapIterator();
/* 1310 */       while (i.hasNext()) {
/* 1311 */         i.nextEntry();
/* 1312 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(short k) {
/* 1323 */       return ((this.bottom || Short2CharRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Short2CharRBTreeMap.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Short2CharMap.Entry> short2CharEntrySet() {
/* 1328 */       if (this.entries == null) this.entries = (ObjectSortedSet<Short2CharMap.Entry>)new AbstractObjectSortedSet<Short2CharMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Short2CharMap.Entry> iterator() {
/* 1331 */               return (ObjectBidirectionalIterator<Short2CharMap.Entry>)new Short2CharRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Short2CharMap.Entry> iterator(Short2CharMap.Entry from) {
/* 1336 */               return (ObjectBidirectionalIterator<Short2CharMap.Entry>)new Short2CharRBTreeMap.Submap.SubmapEntryIterator(from.getShortKey());
/*      */             }
/*      */ 
/*      */             
/*      */             public Comparator<? super Short2CharMap.Entry> comparator() {
/* 1341 */               return Short2CharRBTreeMap.this.short2CharEntrySet().comparator();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1347 */               if (!(o instanceof Map.Entry)) return false; 
/* 1348 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1349 */               if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 1350 */               if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 1351 */               Short2CharRBTreeMap.Entry f = Short2CharRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1352 */               return (f != null && Short2CharRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1358 */               if (!(o instanceof Map.Entry)) return false; 
/* 1359 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1360 */               if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 1361 */               if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 1362 */               Short2CharRBTreeMap.Entry f = Short2CharRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1363 */               if (f != null && Short2CharRBTreeMap.Submap.this.in(f.key)) Short2CharRBTreeMap.Submap.this.remove(f.key); 
/* 1364 */               return (f != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1369 */               int c = 0;
/* 1370 */               for (ObjectBidirectionalIterator<Short2CharMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); ) { c++; objectBidirectionalIterator.next(); }
/* 1371 */                return c;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1376 */               return !(new Short2CharRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1381 */               Short2CharRBTreeMap.Submap.this.clear();
/*      */             }
/*      */ 
/*      */             
/*      */             public Short2CharMap.Entry first() {
/* 1386 */               return Short2CharRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public Short2CharMap.Entry last() {
/* 1391 */               return Short2CharRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Short2CharMap.Entry> subSet(Short2CharMap.Entry from, Short2CharMap.Entry to) {
/* 1396 */               return Short2CharRBTreeMap.Submap.this.subMap(from.getShortKey(), to.getShortKey()).short2CharEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Short2CharMap.Entry> headSet(Short2CharMap.Entry to) {
/* 1401 */               return Short2CharRBTreeMap.Submap.this.headMap(to.getShortKey()).short2CharEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Short2CharMap.Entry> tailSet(Short2CharMap.Entry from) {
/* 1406 */               return Short2CharRBTreeMap.Submap.this.tailMap(from.getShortKey()).short2CharEntrySet();
/*      */             }
/*      */           }; 
/* 1409 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractShort2CharSortedMap.KeySet { private KeySet() {}
/*      */       
/*      */       public ShortBidirectionalIterator iterator() {
/* 1415 */         return new Short2CharRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public ShortBidirectionalIterator iterator(short from) {
/* 1420 */         return new Short2CharRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public ShortSortedSet keySet() {
/* 1426 */       if (this.keys == null) this.keys = new KeySet(); 
/* 1427 */       return this.keys;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharCollection values() {
/* 1432 */       if (this.values == null) this.values = (CharCollection)new AbstractCharCollection()
/*      */           {
/*      */             public CharIterator iterator() {
/* 1435 */               return (CharIterator)new Short2CharRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(char k) {
/* 1440 */               return Short2CharRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1445 */               return Short2CharRBTreeMap.Submap.this.size();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1450 */               Short2CharRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1453 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(short k) {
/* 1460 */       return (in(k) && Short2CharRBTreeMap.this.containsKey(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(char v) {
/* 1465 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1467 */       while (i.hasNext()) {
/* 1468 */         char ev = (i.nextEntry()).value;
/* 1469 */         if (ev == v) return true; 
/*      */       } 
/* 1471 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char get(short k) {
/* 1478 */       short kk = k; Short2CharRBTreeMap.Entry e;
/* 1479 */       return (in(kk) && (e = Short2CharRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public char put(short k, char v) {
/* 1484 */       Short2CharRBTreeMap.this.modified = false;
/* 1485 */       if (!in(k)) throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1486 */       char oldValue = Short2CharRBTreeMap.this.put(k, v);
/* 1487 */       return Short2CharRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public char remove(short k) {
/* 1493 */       Short2CharRBTreeMap.this.modified = false;
/* 1494 */       if (!in(k)) return this.defRetValue; 
/* 1495 */       char oldValue = Short2CharRBTreeMap.this.remove(k);
/* 1496 */       return Short2CharRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1501 */       SubmapIterator i = new SubmapIterator();
/* 1502 */       int n = 0;
/* 1503 */       while (i.hasNext()) {
/* 1504 */         n++;
/* 1505 */         i.nextEntry();
/*      */       } 
/* 1507 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1512 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortComparator comparator() {
/* 1517 */       return Short2CharRBTreeMap.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Short2CharSortedMap headMap(short to) {
/* 1522 */       if (this.top) return new Submap(this.from, this.bottom, to, false); 
/* 1523 */       return (Short2CharRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Short2CharSortedMap tailMap(short from) {
/* 1528 */       if (this.bottom) return new Submap(from, false, this.to, this.top); 
/* 1529 */       return (Short2CharRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Short2CharSortedMap subMap(short from, short to) {
/* 1534 */       if (this.top && this.bottom) return new Submap(from, false, to, false); 
/* 1535 */       if (!this.top) to = (Short2CharRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1536 */       if (!this.bottom) from = (Short2CharRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1537 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1538 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Short2CharRBTreeMap.Entry firstEntry() {
/*      */       Short2CharRBTreeMap.Entry e;
/* 1547 */       if (Short2CharRBTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1551 */       if (this.bottom) { e = Short2CharRBTreeMap.this.firstEntry; }
/*      */       else
/* 1553 */       { e = Short2CharRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1555 */         if (Short2CharRBTreeMap.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1559 */       if (e == null || (!this.top && Short2CharRBTreeMap.this.compare(e.key, this.to) >= 0)) return null; 
/* 1560 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Short2CharRBTreeMap.Entry lastEntry() {
/*      */       Short2CharRBTreeMap.Entry e;
/* 1569 */       if (Short2CharRBTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1573 */       if (this.top) { e = Short2CharRBTreeMap.this.lastEntry; }
/*      */       else
/* 1575 */       { e = Short2CharRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1577 */         if (Short2CharRBTreeMap.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1581 */       if (e == null || (!this.bottom && Short2CharRBTreeMap.this.compare(e.key, this.from) < 0)) return null; 
/* 1582 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public short firstShortKey() {
/* 1587 */       Short2CharRBTreeMap.Entry e = firstEntry();
/* 1588 */       if (e == null) throw new NoSuchElementException(); 
/* 1589 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public short lastShortKey() {
/* 1594 */       Short2CharRBTreeMap.Entry e = lastEntry();
/* 1595 */       if (e == null) throw new NoSuchElementException(); 
/* 1596 */       return e.key;
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
/*      */       extends Short2CharRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1610 */         this.next = Short2CharRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubmapIterator(short k) {
/* 1614 */         this();
/* 1615 */         if (this.next != null) {
/* 1616 */           if (!Short2CharRBTreeMap.Submap.this.bottom && Short2CharRBTreeMap.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1617 */           else if (!Short2CharRBTreeMap.Submap.this.top && Short2CharRBTreeMap.this.compare(k, (this.prev = Short2CharRBTreeMap.Submap.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1619 */           { this.next = Short2CharRBTreeMap.this.locateKey(k);
/* 1620 */             if (Short2CharRBTreeMap.this.compare(this.next.key, k) <= 0)
/* 1621 */             { this.prev = this.next;
/* 1622 */               this.next = this.next.next(); }
/* 1623 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1630 */         this.prev = this.prev.prev();
/* 1631 */         if (!Short2CharRBTreeMap.Submap.this.bottom && this.prev != null && Short2CharRBTreeMap.this.compare(this.prev.key, Short2CharRBTreeMap.Submap.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1636 */         this.next = this.next.next();
/* 1637 */         if (!Short2CharRBTreeMap.Submap.this.top && this.next != null && Short2CharRBTreeMap.this.compare(this.next.key, Short2CharRBTreeMap.Submap.this.to) >= 0) this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Short2CharMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(short k) {
/* 1646 */         super(k);
/*      */       }
/*      */ 
/*      */       
/*      */       public Short2CharMap.Entry next() {
/* 1651 */         return nextEntry();
/*      */       }
/*      */ 
/*      */       
/*      */       public Short2CharMap.Entry previous() {
/* 1656 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements ShortListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(short from) {
/* 1675 */         super(from);
/*      */       }
/*      */ 
/*      */       
/*      */       public short nextShort() {
/* 1680 */         return (nextEntry()).key;
/*      */       }
/*      */ 
/*      */       
/*      */       public short previousShort() {
/* 1685 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements CharListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public char nextChar() {
/* 1701 */         return (nextEntry()).value;
/*      */       }
/*      */ 
/*      */       
/*      */       public char previousChar() {
/* 1706 */         return (previousEntry()).value;
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
/*      */   public Short2CharRBTreeMap clone() {
/*      */     Short2CharRBTreeMap c;
/*      */     try {
/* 1725 */       c = (Short2CharRBTreeMap)super.clone();
/* 1726 */     } catch (CloneNotSupportedException cantHappen) {
/* 1727 */       throw new InternalError();
/*      */     } 
/* 1729 */     c.keys = null;
/* 1730 */     c.values = null;
/* 1731 */     c.entries = null;
/* 1732 */     c.allocatePaths();
/* 1733 */     if (this.count != 0) {
/*      */       
/* 1735 */       Entry rp = new Entry(), rq = new Entry();
/* 1736 */       Entry p = rp;
/* 1737 */       rp.left(this.tree);
/* 1738 */       Entry q = rq;
/* 1739 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1741 */         if (!p.pred()) {
/* 1742 */           Entry e = p.left.clone();
/* 1743 */           e.pred(q.left);
/* 1744 */           e.succ(q);
/* 1745 */           q.left(e);
/* 1746 */           p = p.left;
/* 1747 */           q = q.left;
/*      */         } else {
/* 1749 */           while (p.succ()) {
/* 1750 */             p = p.right;
/* 1751 */             if (p == null) {
/* 1752 */               q.right = null;
/* 1753 */               c.tree = rq.left;
/* 1754 */               c.firstEntry = c.tree;
/* 1755 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1756 */               c.lastEntry = c.tree;
/* 1757 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1758 */               return c;
/*      */             } 
/* 1760 */             q = q.right;
/*      */           } 
/* 1762 */           p = p.right;
/* 1763 */           q = q.right;
/*      */         } 
/* 1765 */         if (!p.succ()) {
/* 1766 */           Entry e = p.right.clone();
/* 1767 */           e.succ(q.right);
/* 1768 */           e.pred(q);
/* 1769 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1773 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1777 */     int n = this.count;
/* 1778 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1780 */     s.defaultWriteObject();
/* 1781 */     while (n-- != 0) {
/* 1782 */       Entry e = i.nextEntry();
/* 1783 */       s.writeShort(e.key);
/* 1784 */       s.writeChar(e.value);
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
/* 1798 */     if (n == 1) {
/* 1799 */       Entry entry = new Entry(s.readShort(), s.readChar());
/* 1800 */       entry.pred(pred);
/* 1801 */       entry.succ(succ);
/* 1802 */       entry.black(true);
/* 1803 */       return entry;
/*      */     } 
/* 1805 */     if (n == 2) {
/*      */ 
/*      */       
/* 1808 */       Entry entry = new Entry(s.readShort(), s.readChar());
/* 1809 */       entry.black(true);
/* 1810 */       entry.right(new Entry(s.readShort(), s.readChar()));
/* 1811 */       entry.right.pred(entry);
/* 1812 */       entry.pred(pred);
/* 1813 */       entry.right.succ(succ);
/* 1814 */       return entry;
/*      */     } 
/*      */     
/* 1817 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1818 */     Entry top = new Entry();
/* 1819 */     top.left(readTree(s, leftN, pred, top));
/* 1820 */     top.key = s.readShort();
/* 1821 */     top.value = s.readChar();
/* 1822 */     top.black(true);
/* 1823 */     top.right(readTree(s, rightN, top, succ));
/* 1824 */     if (n + 2 == (n + 2 & -(n + 2))) top.right.black(false);
/*      */     
/* 1826 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1830 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1833 */     setActualComparator();
/* 1834 */     allocatePaths();
/* 1835 */     if (this.count != 0) {
/* 1836 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1838 */       Entry e = this.tree;
/* 1839 */       for (; e.left() != null; e = e.left());
/* 1840 */       this.firstEntry = e;
/* 1841 */       e = this.tree;
/* 1842 */       for (; e.right() != null; e = e.right());
/* 1843 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2CharRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */