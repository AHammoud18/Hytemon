/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
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
/*      */ public class Short2BooleanRBTreeMap
/*      */   extends AbstractShort2BooleanSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Short2BooleanMap.Entry> entries;
/*      */   protected transient ShortSortedSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Short> storedComparator;
/*      */   protected transient ShortComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public Short2BooleanRBTreeMap() {
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
/*      */   public Short2BooleanRBTreeMap(Comparator<? super Short> c) {
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
/*      */   public Short2BooleanRBTreeMap(Map<? extends Short, ? extends Boolean> m) {
/*  111 */     this();
/*  112 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanRBTreeMap(SortedMap<Short, Boolean> m) {
/*  121 */     this(m.comparator());
/*  122 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanRBTreeMap(Short2BooleanMap m) {
/*  131 */     this();
/*  132 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanRBTreeMap(Short2BooleanSortedMap m) {
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
/*      */   public Short2BooleanRBTreeMap(short[] k, boolean[] v, Comparator<? super Short> c) {
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
/*      */   public Short2BooleanRBTreeMap(short[] k, boolean[] v) {
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
/*      */   public boolean put(short k, boolean v) {
/*  240 */     Entry e = add(k);
/*  241 */     boolean oldValue = e.value;
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
/*      */   private Entry add(short k) {
/*      */     Entry e;
/*  257 */     this.modified = false;
/*  258 */     int maxDepth = 0;
/*      */     
/*  260 */     if (this.tree == null) {
/*  261 */       this.count++;
/*  262 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry(k, this.defRetValue);
/*      */     } else {
/*  264 */       Entry p = this.tree;
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
/*  276 */             e = new Entry(k, this.defRetValue);
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
/*  287 */           e = new Entry(k, this.defRetValue);
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
/*  301 */           Entry entry1 = (this.nodePath[i - 1]).right;
/*  302 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  303 */             this.nodePath[i].black(true);
/*  304 */             entry1.black(true);
/*  305 */             this.nodePath[i - 1].black(false);
/*  306 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  309 */           if (!this.dirPath[i]) { entry1 = this.nodePath[i]; }
/*      */           else
/*  311 */           { Entry entry = this.nodePath[i];
/*  312 */             entry1 = entry.right;
/*  313 */             entry.right = entry1.left;
/*  314 */             entry1.left = entry;
/*  315 */             (this.nodePath[i - 1]).left = entry1;
/*  316 */             if (entry1.pred()) {
/*  317 */               entry1.pred(false);
/*  318 */               entry.succ(entry1);
/*      */             }  }
/*      */           
/*  321 */           Entry entry2 = this.nodePath[i - 1];
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
/*  338 */         Entry y = (this.nodePath[i - 1]).left;
/*  339 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  340 */           this.nodePath[i].black(true);
/*  341 */           y.black(true);
/*  342 */           this.nodePath[i - 1].black(false);
/*  343 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  346 */         if (this.dirPath[i]) { y = this.nodePath[i]; }
/*      */         else
/*  348 */         { Entry entry = this.nodePath[i];
/*  349 */           y = entry.left;
/*  350 */           entry.left = y.right;
/*  351 */           y.right = entry;
/*  352 */           (this.nodePath[i - 1]).right = y;
/*  353 */           if (y.succ()) {
/*  354 */             y.succ(false);
/*  355 */             entry.pred(y);
/*      */           }  }
/*      */         
/*  358 */         Entry x = this.nodePath[i - 1];
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
/*      */   public boolean remove(short k) {
/*  387 */     this.modified = false;
/*  388 */     if (this.tree == null) return this.defRetValue; 
/*  389 */     Entry p = this.tree;
/*      */     
/*  391 */     int i = 0;
/*  392 */     short kk = k;
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
/*  430 */       Entry r = p.right;
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
/*      */         Entry s;
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
/*  479 */           Entry x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  480 */           if (!x.black()) {
/*  481 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  485 */         if (!this.dirPath[i - 1]) {
/*  486 */           Entry w = (this.nodePath[i - 1]).right;
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
/*  507 */               Entry y = w.left;
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
/*  535 */           Entry w = (this.nodePath[i - 1]).left;
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
/*  556 */               Entry y = w.right;
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
/*      */   public boolean containsValue(boolean v) {
/*  596 */     ValueIterator i = new ValueIterator();
/*      */     
/*  598 */     int j = this.count;
/*  599 */     while (j-- != 0) {
/*  600 */       boolean ev = i.nextBoolean();
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
/*      */   private static final class Entry
/*      */     extends AbstractShort2BooleanMap.BasicEntry
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
/*  642 */       super((short)0, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(short k, boolean v) {
/*  652 */       super(k, v);
/*  653 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  662 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
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
/*      */     void pred(Entry pred) {
/*  718 */       this.info |= 0x40000000;
/*  719 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  728 */       this.info |= Integer.MIN_VALUE;
/*  729 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  738 */       this.info &= 0xBFFFFFFF;
/*  739 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
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
/*      */     Entry next() {
/*  777 */       Entry next = this.right;
/*  778 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  779 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  788 */       Entry prev = this.left;
/*  789 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  790 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setValue(boolean value) {
/*  795 */       boolean oldValue = this.value;
/*  796 */       this.value = value;
/*  797 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  805 */         c = (Entry)super.clone();
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
/*  819 */       Map.Entry<Short, Boolean> e = (Map.Entry<Short, Boolean>)o;
/*  820 */       return (this.key == ((Short)e.getKey()).shortValue() && this.value == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  825 */       return this.key ^ (this.value ? 1231 : 1237);
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
/*      */   public boolean containsKey(short k) {
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
/*      */   public boolean get(short k) {
/*  881 */     Entry e = findKey(k);
/*  882 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public short firstShortKey() {
/*  887 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  888 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public short lastShortKey() {
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
/*      */     Short2BooleanRBTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Short2BooleanRBTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Short2BooleanRBTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  923 */     int index = 0;
/*      */     
/*      */     TreeIterator() {
/*  926 */       this.next = Short2BooleanRBTreeMap.this.firstEntry;
/*      */     }
/*      */     
/*      */     TreeIterator(short k) {
/*  930 */       if ((this.next = Short2BooleanRBTreeMap.this.locateKey(k)) != null)
/*  931 */         if (Short2BooleanRBTreeMap.this.compare(this.next.key, k) <= 0)
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
/*      */     Short2BooleanRBTreeMap.Entry nextEntry() {
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
/*      */     Short2BooleanRBTreeMap.Entry previousEntry() {
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
/*  986 */       Short2BooleanRBTreeMap.this.remove(this.curr.key);
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
/*      */     implements ObjectListIterator<Short2BooleanMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     EntryIterator(short k) {
/* 1014 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public Short2BooleanMap.Entry next() {
/* 1019 */       return nextEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Short2BooleanMap.Entry previous() {
/* 1024 */       return previousEntry();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<Short2BooleanMap.Entry> short2BooleanEntrySet() {
/* 1031 */     if (this.entries == null) this.entries = (ObjectSortedSet<Short2BooleanMap.Entry>)new AbstractObjectSortedSet<Short2BooleanMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Short2BooleanMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Short2BooleanMap.Entry> comparator() {
/* 1036 */             return this.comparator;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Short2BooleanMap.Entry> iterator() {
/* 1041 */             return (ObjectBidirectionalIterator<Short2BooleanMap.Entry>)new Short2BooleanRBTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Short2BooleanMap.Entry> iterator(Short2BooleanMap.Entry from) {
/* 1046 */             return (ObjectBidirectionalIterator<Short2BooleanMap.Entry>)new Short2BooleanRBTreeMap.EntryIterator(from.getShortKey());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1052 */             if (o == null || !(o instanceof Map.Entry)) return false; 
/* 1053 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1054 */             if (e.getKey() == null) return false; 
/* 1055 */             if (!(e.getKey() instanceof Short)) return false; 
/* 1056 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1057 */             Short2BooleanRBTreeMap.Entry f = Short2BooleanRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1058 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1064 */             if (!(o instanceof Map.Entry)) return false; 
/* 1065 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1066 */             if (e.getKey() == null) return false; 
/* 1067 */             if (!(e.getKey() instanceof Short)) return false; 
/* 1068 */             if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1069 */             Short2BooleanRBTreeMap.Entry f = Short2BooleanRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1070 */             if (f == null || f.getBooleanValue() != ((Boolean)e.getValue()).booleanValue()) return false; 
/* 1071 */             Short2BooleanRBTreeMap.this.remove(f.key);
/* 1072 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1077 */             return Short2BooleanRBTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1082 */             Short2BooleanRBTreeMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public Short2BooleanMap.Entry first() {
/* 1087 */             return Short2BooleanRBTreeMap.this.firstEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public Short2BooleanMap.Entry last() {
/* 1092 */             return Short2BooleanRBTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Short2BooleanMap.Entry> subSet(Short2BooleanMap.Entry from, Short2BooleanMap.Entry to) {
/* 1097 */             return Short2BooleanRBTreeMap.this.subMap(from.getShortKey(), to.getShortKey()).short2BooleanEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Short2BooleanMap.Entry> headSet(Short2BooleanMap.Entry to) {
/* 1102 */             return Short2BooleanRBTreeMap.this.headMap(to.getShortKey()).short2BooleanEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Short2BooleanMap.Entry> tailSet(Short2BooleanMap.Entry from) {
/* 1107 */             return Short2BooleanRBTreeMap.this.tailMap(from.getShortKey()).short2BooleanEntrySet();
/*      */           }
/*      */         }; 
/* 1110 */     return this.entries;
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
/* 1126 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public short nextShort() {
/* 1131 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public short previousShort() {
/* 1136 */       return (previousEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractShort2BooleanSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ShortBidirectionalIterator iterator() {
/* 1144 */       return new Short2BooleanRBTreeMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortBidirectionalIterator iterator(short from) {
/* 1149 */       return new Short2BooleanRBTreeMap.KeyIterator(from);
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
/* 1164 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1165 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends TreeIterator
/*      */     implements BooleanListIterator
/*      */   {
/*      */     private ValueIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1179 */       return (nextEntry()).value;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean previousBoolean() {
/* 1184 */       return (previousEntry()).value;
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
/*      */   public BooleanCollection values() {
/* 1199 */     if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1202 */             return (BooleanIterator)new Short2BooleanRBTreeMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(boolean k) {
/* 1207 */             return Short2BooleanRBTreeMap.this.containsValue(k);
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1212 */             return Short2BooleanRBTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1217 */             Short2BooleanRBTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1220 */     return this.values;
/*      */   }
/*      */ 
/*      */   
/*      */   public ShortComparator comparator() {
/* 1225 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public Short2BooleanSortedMap headMap(short to) {
/* 1230 */     return new Submap((short)0, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public Short2BooleanSortedMap tailMap(short from) {
/* 1235 */     return new Submap(from, false, (short)0, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Short2BooleanSortedMap subMap(short from, short to) {
/* 1240 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractShort2BooleanSortedMap
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
/*      */     protected transient ObjectSortedSet<Short2BooleanMap.Entry> entries;
/*      */ 
/*      */     
/*      */     protected transient ShortSortedSet keys;
/*      */ 
/*      */     
/*      */     protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(short from, boolean bottom, short to, boolean top) {
/* 1279 */       if (!bottom && !top && Short2BooleanRBTreeMap.this.compare(from, to) > 0) throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1280 */       this.from = from;
/* 1281 */       this.bottom = bottom;
/* 1282 */       this.to = to;
/* 1283 */       this.top = top;
/* 1284 */       this.defRetValue = Short2BooleanRBTreeMap.this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1289 */       SubmapIterator i = new SubmapIterator();
/* 1290 */       while (i.hasNext()) {
/* 1291 */         i.nextEntry();
/* 1292 */         i.remove();
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
/* 1303 */       return ((this.bottom || Short2BooleanRBTreeMap.this.compare(k, this.from) >= 0) && (this.top || Short2BooleanRBTreeMap.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Short2BooleanMap.Entry> short2BooleanEntrySet() {
/* 1308 */       if (this.entries == null) this.entries = (ObjectSortedSet<Short2BooleanMap.Entry>)new AbstractObjectSortedSet<Short2BooleanMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Short2BooleanMap.Entry> iterator() {
/* 1311 */               return (ObjectBidirectionalIterator<Short2BooleanMap.Entry>)new Short2BooleanRBTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Short2BooleanMap.Entry> iterator(Short2BooleanMap.Entry from) {
/* 1316 */               return (ObjectBidirectionalIterator<Short2BooleanMap.Entry>)new Short2BooleanRBTreeMap.Submap.SubmapEntryIterator(from.getShortKey());
/*      */             }
/*      */ 
/*      */             
/*      */             public Comparator<? super Short2BooleanMap.Entry> comparator() {
/* 1321 */               return Short2BooleanRBTreeMap.this.short2BooleanEntrySet().comparator();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1327 */               if (!(o instanceof Map.Entry)) return false; 
/* 1328 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1329 */               if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 1330 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1331 */               Short2BooleanRBTreeMap.Entry f = Short2BooleanRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1332 */               return (f != null && Short2BooleanRBTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1338 */               if (!(o instanceof Map.Entry)) return false; 
/* 1339 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1340 */               if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 1341 */               if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1342 */               Short2BooleanRBTreeMap.Entry f = Short2BooleanRBTreeMap.this.findKey(((Short)e.getKey()).shortValue());
/* 1343 */               if (f != null && Short2BooleanRBTreeMap.Submap.this.in(f.key)) Short2BooleanRBTreeMap.Submap.this.remove(f.key); 
/* 1344 */               return (f != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1349 */               int c = 0;
/* 1350 */               for (ObjectBidirectionalIterator<Short2BooleanMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); ) { c++; objectBidirectionalIterator.next(); }
/* 1351 */                return c;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1356 */               return !(new Short2BooleanRBTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1361 */               Short2BooleanRBTreeMap.Submap.this.clear();
/*      */             }
/*      */ 
/*      */             
/*      */             public Short2BooleanMap.Entry first() {
/* 1366 */               return Short2BooleanRBTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public Short2BooleanMap.Entry last() {
/* 1371 */               return Short2BooleanRBTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Short2BooleanMap.Entry> subSet(Short2BooleanMap.Entry from, Short2BooleanMap.Entry to) {
/* 1376 */               return Short2BooleanRBTreeMap.Submap.this.subMap(from.getShortKey(), to.getShortKey()).short2BooleanEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Short2BooleanMap.Entry> headSet(Short2BooleanMap.Entry to) {
/* 1381 */               return Short2BooleanRBTreeMap.Submap.this.headMap(to.getShortKey()).short2BooleanEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Short2BooleanMap.Entry> tailSet(Short2BooleanMap.Entry from) {
/* 1386 */               return Short2BooleanRBTreeMap.Submap.this.tailMap(from.getShortKey()).short2BooleanEntrySet();
/*      */             }
/*      */           }; 
/* 1389 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractShort2BooleanSortedMap.KeySet { private KeySet() {}
/*      */       
/*      */       public ShortBidirectionalIterator iterator() {
/* 1395 */         return new Short2BooleanRBTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public ShortBidirectionalIterator iterator(short from) {
/* 1400 */         return new Short2BooleanRBTreeMap.Submap.SubmapKeyIterator(from);
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public ShortSortedSet keySet() {
/* 1406 */       if (this.keys == null) this.keys = new KeySet(); 
/* 1407 */       return this.keys;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanCollection values() {
/* 1412 */       if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */           {
/*      */             public BooleanIterator iterator() {
/* 1415 */               return (BooleanIterator)new Short2BooleanRBTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(boolean k) {
/* 1420 */               return Short2BooleanRBTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1425 */               return Short2BooleanRBTreeMap.Submap.this.size();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1430 */               Short2BooleanRBTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1433 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(short k) {
/* 1440 */       return (in(k) && Short2BooleanRBTreeMap.this.containsKey(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(boolean v) {
/* 1445 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1447 */       while (i.hasNext()) {
/* 1448 */         boolean ev = (i.nextEntry()).value;
/* 1449 */         if (ev == v) return true; 
/*      */       } 
/* 1451 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean get(short k) {
/* 1458 */       short kk = k; Short2BooleanRBTreeMap.Entry e;
/* 1459 */       return (in(kk) && (e = Short2BooleanRBTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean put(short k, boolean v) {
/* 1464 */       Short2BooleanRBTreeMap.this.modified = false;
/* 1465 */       if (!in(k)) throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1466 */       boolean oldValue = Short2BooleanRBTreeMap.this.put(k, v);
/* 1467 */       return Short2BooleanRBTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(short k) {
/* 1473 */       Short2BooleanRBTreeMap.this.modified = false;
/* 1474 */       if (!in(k)) return this.defRetValue; 
/* 1475 */       boolean oldValue = Short2BooleanRBTreeMap.this.remove(k);
/* 1476 */       return Short2BooleanRBTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1481 */       SubmapIterator i = new SubmapIterator();
/* 1482 */       int n = 0;
/* 1483 */       while (i.hasNext()) {
/* 1484 */         n++;
/* 1485 */         i.nextEntry();
/*      */       } 
/* 1487 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1492 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortComparator comparator() {
/* 1497 */       return Short2BooleanRBTreeMap.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Short2BooleanSortedMap headMap(short to) {
/* 1502 */       if (this.top) return new Submap(this.from, this.bottom, to, false); 
/* 1503 */       return (Short2BooleanRBTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Short2BooleanSortedMap tailMap(short from) {
/* 1508 */       if (this.bottom) return new Submap(from, false, this.to, this.top); 
/* 1509 */       return (Short2BooleanRBTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Short2BooleanSortedMap subMap(short from, short to) {
/* 1514 */       if (this.top && this.bottom) return new Submap(from, false, to, false); 
/* 1515 */       if (!this.top) to = (Short2BooleanRBTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1516 */       if (!this.bottom) from = (Short2BooleanRBTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1517 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1518 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Short2BooleanRBTreeMap.Entry firstEntry() {
/*      */       Short2BooleanRBTreeMap.Entry e;
/* 1527 */       if (Short2BooleanRBTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1531 */       if (this.bottom) { e = Short2BooleanRBTreeMap.this.firstEntry; }
/*      */       else
/* 1533 */       { e = Short2BooleanRBTreeMap.this.locateKey(this.from);
/*      */         
/* 1535 */         if (Short2BooleanRBTreeMap.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1539 */       if (e == null || (!this.top && Short2BooleanRBTreeMap.this.compare(e.key, this.to) >= 0)) return null; 
/* 1540 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Short2BooleanRBTreeMap.Entry lastEntry() {
/*      */       Short2BooleanRBTreeMap.Entry e;
/* 1549 */       if (Short2BooleanRBTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1553 */       if (this.top) { e = Short2BooleanRBTreeMap.this.lastEntry; }
/*      */       else
/* 1555 */       { e = Short2BooleanRBTreeMap.this.locateKey(this.to);
/*      */         
/* 1557 */         if (Short2BooleanRBTreeMap.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1561 */       if (e == null || (!this.bottom && Short2BooleanRBTreeMap.this.compare(e.key, this.from) < 0)) return null; 
/* 1562 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public short firstShortKey() {
/* 1567 */       Short2BooleanRBTreeMap.Entry e = firstEntry();
/* 1568 */       if (e == null) throw new NoSuchElementException(); 
/* 1569 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public short lastShortKey() {
/* 1574 */       Short2BooleanRBTreeMap.Entry e = lastEntry();
/* 1575 */       if (e == null) throw new NoSuchElementException(); 
/* 1576 */       return e.key;
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
/*      */       extends Short2BooleanRBTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1590 */         this.next = Short2BooleanRBTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubmapIterator(short k) {
/* 1594 */         this();
/* 1595 */         if (this.next != null) {
/* 1596 */           if (!Short2BooleanRBTreeMap.Submap.this.bottom && Short2BooleanRBTreeMap.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1597 */           else if (!Short2BooleanRBTreeMap.Submap.this.top && Short2BooleanRBTreeMap.this.compare(k, (this.prev = Short2BooleanRBTreeMap.Submap.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1599 */           { this.next = Short2BooleanRBTreeMap.this.locateKey(k);
/* 1600 */             if (Short2BooleanRBTreeMap.this.compare(this.next.key, k) <= 0)
/* 1601 */             { this.prev = this.next;
/* 1602 */               this.next = this.next.next(); }
/* 1603 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1610 */         this.prev = this.prev.prev();
/* 1611 */         if (!Short2BooleanRBTreeMap.Submap.this.bottom && this.prev != null && Short2BooleanRBTreeMap.this.compare(this.prev.key, Short2BooleanRBTreeMap.Submap.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1616 */         this.next = this.next.next();
/* 1617 */         if (!Short2BooleanRBTreeMap.Submap.this.top && this.next != null && Short2BooleanRBTreeMap.this.compare(this.next.key, Short2BooleanRBTreeMap.Submap.this.to) >= 0) this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Short2BooleanMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(short k) {
/* 1626 */         super(k);
/*      */       }
/*      */ 
/*      */       
/*      */       public Short2BooleanMap.Entry next() {
/* 1631 */         return nextEntry();
/*      */       }
/*      */ 
/*      */       
/*      */       public Short2BooleanMap.Entry previous() {
/* 1636 */         return previousEntry();
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
/* 1655 */         super(from);
/*      */       }
/*      */ 
/*      */       
/*      */       public short nextShort() {
/* 1660 */         return (nextEntry()).key;
/*      */       }
/*      */ 
/*      */       
/*      */       public short previousShort() {
/* 1665 */         return (previousEntry()).key;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapValueIterator
/*      */       extends SubmapIterator
/*      */       implements BooleanListIterator
/*      */     {
/*      */       private SubmapValueIterator() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean nextBoolean() {
/* 1681 */         return (nextEntry()).value;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean previousBoolean() {
/* 1686 */         return (previousEntry()).value;
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
/*      */   public Short2BooleanRBTreeMap clone() {
/*      */     Short2BooleanRBTreeMap c;
/*      */     try {
/* 1705 */       c = (Short2BooleanRBTreeMap)super.clone();
/* 1706 */     } catch (CloneNotSupportedException cantHappen) {
/* 1707 */       throw new InternalError();
/*      */     } 
/* 1709 */     c.keys = null;
/* 1710 */     c.values = null;
/* 1711 */     c.entries = null;
/* 1712 */     c.allocatePaths();
/* 1713 */     if (this.count != 0) {
/*      */       
/* 1715 */       Entry rp = new Entry(), rq = new Entry();
/* 1716 */       Entry p = rp;
/* 1717 */       rp.left(this.tree);
/* 1718 */       Entry q = rq;
/* 1719 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1721 */         if (!p.pred()) {
/* 1722 */           Entry e = p.left.clone();
/* 1723 */           e.pred(q.left);
/* 1724 */           e.succ(q);
/* 1725 */           q.left(e);
/* 1726 */           p = p.left;
/* 1727 */           q = q.left;
/*      */         } else {
/* 1729 */           while (p.succ()) {
/* 1730 */             p = p.right;
/* 1731 */             if (p == null) {
/* 1732 */               q.right = null;
/* 1733 */               c.tree = rq.left;
/* 1734 */               c.firstEntry = c.tree;
/* 1735 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1736 */               c.lastEntry = c.tree;
/* 1737 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1738 */               return c;
/*      */             } 
/* 1740 */             q = q.right;
/*      */           } 
/* 1742 */           p = p.right;
/* 1743 */           q = q.right;
/*      */         } 
/* 1745 */         if (!p.succ()) {
/* 1746 */           Entry e = p.right.clone();
/* 1747 */           e.succ(q.right);
/* 1748 */           e.pred(q);
/* 1749 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1753 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1757 */     int n = this.count;
/* 1758 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1760 */     s.defaultWriteObject();
/* 1761 */     while (n-- != 0) {
/* 1762 */       Entry e = i.nextEntry();
/* 1763 */       s.writeShort(e.key);
/* 1764 */       s.writeBoolean(e.value);
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
/* 1778 */     if (n == 1) {
/* 1779 */       Entry entry = new Entry(s.readShort(), s.readBoolean());
/* 1780 */       entry.pred(pred);
/* 1781 */       entry.succ(succ);
/* 1782 */       entry.black(true);
/* 1783 */       return entry;
/*      */     } 
/* 1785 */     if (n == 2) {
/*      */ 
/*      */       
/* 1788 */       Entry entry = new Entry(s.readShort(), s.readBoolean());
/* 1789 */       entry.black(true);
/* 1790 */       entry.right(new Entry(s.readShort(), s.readBoolean()));
/* 1791 */       entry.right.pred(entry);
/* 1792 */       entry.pred(pred);
/* 1793 */       entry.right.succ(succ);
/* 1794 */       return entry;
/*      */     } 
/*      */     
/* 1797 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1798 */     Entry top = new Entry();
/* 1799 */     top.left(readTree(s, leftN, pred, top));
/* 1800 */     top.key = s.readShort();
/* 1801 */     top.value = s.readBoolean();
/* 1802 */     top.black(true);
/* 1803 */     top.right(readTree(s, rightN, top, succ));
/* 1804 */     if (n + 2 == (n + 2 & -(n + 2))) top.right.black(false);
/*      */     
/* 1806 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1810 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1813 */     setActualComparator();
/* 1814 */     allocatePaths();
/* 1815 */     if (this.count != 0) {
/* 1816 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1818 */       Entry e = this.tree;
/* 1819 */       for (; e.left() != null; e = e.left());
/* 1820 */       this.firstEntry = e;
/* 1821 */       e = this.tree;
/* 1822 */       for (; e.right() != null; e = e.right());
/* 1823 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2BooleanRBTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */