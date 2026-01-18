/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
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
/*      */ public class DoubleRBTreeSet
/*      */   extends AbstractDoubleSortedSet
/*      */   implements Serializable, Cloneable, DoubleSortedSet
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected Comparator<? super Double> storedComparator;
/*      */   protected transient DoubleComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353130L;
/*      */   private transient boolean[] dirPath;
/*      */   private transient Entry[] nodePath;
/*      */   
/*      */   public DoubleRBTreeSet() {
/*   51 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   58 */     this.tree = null;
/*   59 */     this.count = 0;
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
/*   71 */     this.actualComparator = DoubleComparators.asDoubleComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(Comparator<? super Double> c) {
/*   80 */     this();
/*   81 */     this.storedComparator = c;
/*   82 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(Collection<? extends Double> c) {
/*   91 */     this();
/*   92 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(SortedSet<Double> s) {
/*  101 */     this(s.comparator());
/*  102 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(DoubleCollection c) {
/*  111 */     this();
/*  112 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(DoubleSortedSet s) {
/*  121 */     this(s.comparator());
/*  122 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(DoubleIterator i) {
/*      */     allocatePaths();
/*  131 */     for (; i.hasNext(); add(i.nextDouble()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(Iterator<?> i) {
/*  141 */     this(DoubleIterators.asDoubleIterator(i));
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
/*      */   public DoubleRBTreeSet(double[] a, int offset, int length, Comparator<? super Double> c) {
/*  154 */     this(c);
/*  155 */     DoubleArrays.ensureOffsetLength(a, offset, length);
/*  156 */     for (int i = 0; i < length; ) { add(a[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(double[] a, int offset, int length) {
/*  167 */     this(a, offset, length, (Comparator<? super Double>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(double[] a) {
/*  176 */     this();
/*  177 */     int i = a.length;
/*  178 */     for (; i-- != 0; add(a[i]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleRBTreeSet(double[] a, Comparator<? super Double> c) {
/*  188 */     this(c);
/*  189 */     int i = a.length;
/*  190 */     for (; i-- != 0; add(a[i]));
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
/*  216 */     return (this.actualComparator == null) ? Double.compare(k1, k2) : this.actualComparator.compare(k1, k2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry findKey(double k) {
/*  226 */     Entry e = this.tree;
/*      */     int cmp;
/*  228 */     for (; e != null && (cmp = compare(k, e.key)) != 0; e = (cmp < 0) ? e.left() : e.right());
/*  229 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry locateKey(double k) {
/*  240 */     Entry e = this.tree, last = this.tree;
/*  241 */     int cmp = 0;
/*  242 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  243 */       last = e;
/*  244 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  246 */     return (cmp == 0) ? e : last;
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
/*  257 */     this.dirPath = new boolean[64];
/*  258 */     this.nodePath = new Entry[64];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean add(double k) {
/*  264 */     int maxDepth = 0;
/*  265 */     if (this.tree == null) {
/*  266 */       this.count++;
/*  267 */       this.tree = this.lastEntry = this.firstEntry = new Entry(k);
/*      */     } else {
/*  269 */       Entry p = this.tree;
/*  270 */       int i = 0; while (true) {
/*      */         int cmp;
/*  272 */         if ((cmp = compare(k, p.key)) == 0) {
/*      */           
/*  274 */           for (; i-- != 0; this.nodePath[i] = null);
/*  275 */           return false;
/*      */         } 
/*  277 */         this.nodePath[i] = p;
/*  278 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  279 */           if (p.succ()) {
/*  280 */             this.count++;
/*  281 */             Entry e = new Entry(k);
/*  282 */             if (p.right == null) this.lastEntry = e; 
/*  283 */             e.left = p;
/*  284 */             e.right = p.right;
/*  285 */             p.right(e);
/*      */             break;
/*      */           } 
/*  288 */           p = p.right; continue;
/*      */         } 
/*  290 */         if (p.pred()) {
/*  291 */           this.count++;
/*  292 */           Entry e = new Entry(k);
/*  293 */           if (p.left == null) this.firstEntry = e; 
/*  294 */           e.right = p;
/*  295 */           e.left = p.left;
/*  296 */           p.left(e);
/*      */           break;
/*      */         } 
/*  299 */         p = p.left;
/*      */       } 
/*      */       
/*  302 */       maxDepth = i--;
/*  303 */       while (i > 0 && !this.nodePath[i].black()) {
/*  304 */         if (!this.dirPath[i - 1]) {
/*  305 */           Entry entry1 = (this.nodePath[i - 1]).right;
/*  306 */           if (!this.nodePath[i - 1].succ() && !entry1.black()) {
/*  307 */             this.nodePath[i].black(true);
/*  308 */             entry1.black(true);
/*  309 */             this.nodePath[i - 1].black(false);
/*  310 */             i -= 2;
/*      */             continue;
/*      */           } 
/*  313 */           if (!this.dirPath[i]) { entry1 = this.nodePath[i]; }
/*      */           else
/*  315 */           { Entry entry = this.nodePath[i];
/*  316 */             entry1 = entry.right;
/*  317 */             entry.right = entry1.left;
/*  318 */             entry1.left = entry;
/*  319 */             (this.nodePath[i - 1]).left = entry1;
/*  320 */             if (entry1.pred()) {
/*  321 */               entry1.pred(false);
/*  322 */               entry.succ(entry1);
/*      */             }  }
/*      */           
/*  325 */           Entry entry2 = this.nodePath[i - 1];
/*  326 */           entry2.black(false);
/*  327 */           entry1.black(true);
/*  328 */           entry2.left = entry1.right;
/*  329 */           entry1.right = entry2;
/*  330 */           if (i < 2) { this.tree = entry1; }
/*      */           
/*  332 */           else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = entry1; }
/*  333 */           else { (this.nodePath[i - 2]).left = entry1; }
/*      */           
/*  335 */           if (entry1.succ()) {
/*  336 */             entry1.succ(false);
/*  337 */             entry2.pred(entry1);
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  342 */         Entry y = (this.nodePath[i - 1]).left;
/*  343 */         if (!this.nodePath[i - 1].pred() && !y.black()) {
/*  344 */           this.nodePath[i].black(true);
/*  345 */           y.black(true);
/*  346 */           this.nodePath[i - 1].black(false);
/*  347 */           i -= 2;
/*      */           continue;
/*      */         } 
/*  350 */         if (this.dirPath[i]) { y = this.nodePath[i]; }
/*      */         else
/*  352 */         { Entry entry = this.nodePath[i];
/*  353 */           y = entry.left;
/*  354 */           entry.left = y.right;
/*  355 */           y.right = entry;
/*  356 */           (this.nodePath[i - 1]).right = y;
/*  357 */           if (y.succ()) {
/*  358 */             y.succ(false);
/*  359 */             entry.pred(y);
/*      */           }  }
/*      */         
/*  362 */         Entry x = this.nodePath[i - 1];
/*  363 */         x.black(false);
/*  364 */         y.black(true);
/*  365 */         x.right = y.left;
/*  366 */         y.left = x;
/*  367 */         if (i < 2) { this.tree = y; }
/*      */         
/*  369 */         else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = y; }
/*  370 */         else { (this.nodePath[i - 2]).left = y; }
/*      */         
/*  372 */         if (y.pred()) {
/*  373 */           y.pred(false);
/*  374 */           x.succ(y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  381 */     this.tree.black(true);
/*      */     
/*  383 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  384 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(double k) {
/*  389 */     if (this.tree == null) return false; 
/*  390 */     Entry p = this.tree;
/*      */     
/*  392 */     int i = 0;
/*  393 */     double kk = k;
/*      */     int cmp;
/*  395 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  396 */       this.dirPath[i] = (cmp > 0);
/*  397 */       this.nodePath[i] = p;
/*  398 */       if (this.dirPath[i++]) {
/*  399 */         if ((p = p.right()) == null) {
/*      */           
/*  401 */           for (; i-- != 0; this.nodePath[i] = null);
/*  402 */           return false;
/*      */         }  continue;
/*      */       } 
/*  405 */       if ((p = p.left()) == null) {
/*      */         
/*  407 */         for (; i-- != 0; this.nodePath[i] = null);
/*  408 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/*  412 */     if (p.left == null) this.firstEntry = p.next(); 
/*  413 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  414 */     if (p.succ()) {
/*  415 */       if (p.pred()) {
/*  416 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  418 */         else if (this.dirPath[i - 1]) { this.nodePath[i - 1].succ(p.right); }
/*  419 */         else { this.nodePath[i - 1].pred(p.left); }
/*      */       
/*      */       } else {
/*  422 */         (p.prev()).right = p.right;
/*  423 */         if (i == 0) { this.tree = p.left; }
/*      */         
/*  425 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = p.left; }
/*  426 */         else { (this.nodePath[i - 1]).left = p.left; }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  431 */       Entry r = p.right;
/*  432 */       if (r.pred()) {
/*  433 */         r.left = p.left;
/*  434 */         r.pred(p.pred());
/*  435 */         if (!r.pred()) (r.prev()).right = r; 
/*  436 */         if (i == 0) { this.tree = r; }
/*      */         
/*  438 */         else if (this.dirPath[i - 1]) { (this.nodePath[i - 1]).right = r; }
/*  439 */         else { (this.nodePath[i - 1]).left = r; }
/*      */         
/*  441 */         boolean color = r.black();
/*  442 */         r.black(p.black());
/*  443 */         p.black(color);
/*  444 */         this.dirPath[i] = true;
/*  445 */         this.nodePath[i++] = r;
/*      */       } else {
/*      */         Entry s;
/*  448 */         int j = i++;
/*      */         while (true) {
/*  450 */           this.dirPath[i] = false;
/*  451 */           this.nodePath[i++] = r;
/*  452 */           s = r.left;
/*  453 */           if (s.pred())
/*  454 */             break;  r = s;
/*      */         } 
/*  456 */         this.dirPath[j] = true;
/*  457 */         this.nodePath[j] = s;
/*  458 */         if (s.succ()) { r.pred(s); }
/*  459 */         else { r.left = s.right; }
/*  460 */          s.left = p.left;
/*  461 */         if (!p.pred()) {
/*  462 */           (p.prev()).right = s;
/*  463 */           s.pred(false);
/*      */         } 
/*  465 */         s.right(p.right);
/*  466 */         boolean color = s.black();
/*  467 */         s.black(p.black());
/*  468 */         p.black(color);
/*  469 */         if (j == 0) { this.tree = s; }
/*      */         
/*  471 */         else if (this.dirPath[j - 1]) { (this.nodePath[j - 1]).right = s; }
/*  472 */         else { (this.nodePath[j - 1]).left = s; }
/*      */       
/*      */       } 
/*      */     } 
/*  476 */     int maxDepth = i;
/*  477 */     if (p.black()) {
/*  478 */       for (; i > 0; i--) {
/*  479 */         if ((this.dirPath[i - 1] && !this.nodePath[i - 1].succ()) || (!this.dirPath[i - 1] && !this.nodePath[i - 1].pred())) {
/*  480 */           Entry x = this.dirPath[i - 1] ? (this.nodePath[i - 1]).right : (this.nodePath[i - 1]).left;
/*  481 */           if (!x.black()) {
/*  482 */             x.black(true);
/*      */             break;
/*      */           } 
/*      */         } 
/*  486 */         if (!this.dirPath[i - 1]) {
/*  487 */           Entry w = (this.nodePath[i - 1]).right;
/*  488 */           if (!w.black()) {
/*  489 */             w.black(true);
/*  490 */             this.nodePath[i - 1].black(false);
/*  491 */             (this.nodePath[i - 1]).right = w.left;
/*  492 */             w.left = this.nodePath[i - 1];
/*  493 */             if (i < 2) { this.tree = w; }
/*      */             
/*  495 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  496 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  498 */             this.nodePath[i] = this.nodePath[i - 1];
/*  499 */             this.dirPath[i] = false;
/*  500 */             this.nodePath[i - 1] = w;
/*  501 */             if (maxDepth == i++) maxDepth++; 
/*  502 */             w = (this.nodePath[i - 1]).right;
/*      */           } 
/*  504 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  505 */             w.black(false);
/*      */           } else {
/*  507 */             if (w.succ() || w.right.black()) {
/*  508 */               Entry y = w.left;
/*  509 */               y.black(true);
/*  510 */               w.black(false);
/*  511 */               w.left = y.right;
/*  512 */               y.right = w;
/*  513 */               w = (this.nodePath[i - 1]).right = y;
/*  514 */               if (w.succ()) {
/*  515 */                 w.succ(false);
/*  516 */                 w.right.pred(w);
/*      */               } 
/*      */             } 
/*  519 */             w.black(this.nodePath[i - 1].black());
/*  520 */             this.nodePath[i - 1].black(true);
/*  521 */             w.right.black(true);
/*  522 */             (this.nodePath[i - 1]).right = w.left;
/*  523 */             w.left = this.nodePath[i - 1];
/*  524 */             if (i < 2) { this.tree = w; }
/*      */             
/*  526 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  527 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  529 */             if (w.pred()) {
/*  530 */               w.pred(false);
/*  531 */               this.nodePath[i - 1].succ(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } else {
/*  536 */           Entry w = (this.nodePath[i - 1]).left;
/*  537 */           if (!w.black()) {
/*  538 */             w.black(true);
/*  539 */             this.nodePath[i - 1].black(false);
/*  540 */             (this.nodePath[i - 1]).left = w.right;
/*  541 */             w.right = this.nodePath[i - 1];
/*  542 */             if (i < 2) { this.tree = w; }
/*      */             
/*  544 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  545 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  547 */             this.nodePath[i] = this.nodePath[i - 1];
/*  548 */             this.dirPath[i] = true;
/*  549 */             this.nodePath[i - 1] = w;
/*  550 */             if (maxDepth == i++) maxDepth++; 
/*  551 */             w = (this.nodePath[i - 1]).left;
/*      */           } 
/*  553 */           if ((w.pred() || w.left.black()) && (w.succ() || w.right.black())) {
/*  554 */             w.black(false);
/*      */           } else {
/*  556 */             if (w.pred() || w.left.black()) {
/*  557 */               Entry y = w.right;
/*  558 */               y.black(true);
/*  559 */               w.black(false);
/*  560 */               w.right = y.left;
/*  561 */               y.left = w;
/*  562 */               w = (this.nodePath[i - 1]).left = y;
/*  563 */               if (w.pred()) {
/*  564 */                 w.pred(false);
/*  565 */                 w.left.succ(w);
/*      */               } 
/*      */             } 
/*  568 */             w.black(this.nodePath[i - 1].black());
/*  569 */             this.nodePath[i - 1].black(true);
/*  570 */             w.left.black(true);
/*  571 */             (this.nodePath[i - 1]).left = w.right;
/*  572 */             w.right = this.nodePath[i - 1];
/*  573 */             if (i < 2) { this.tree = w; }
/*      */             
/*  575 */             else if (this.dirPath[i - 2]) { (this.nodePath[i - 2]).right = w; }
/*  576 */             else { (this.nodePath[i - 2]).left = w; }
/*      */             
/*  578 */             if (w.succ()) {
/*  579 */               w.succ(false);
/*  580 */               this.nodePath[i - 1].pred(w);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  586 */       if (this.tree != null) this.tree.black(true); 
/*      */     } 
/*  588 */     this.count--;
/*      */     
/*  590 */     for (; maxDepth-- != 0; this.nodePath[maxDepth] = null);
/*  591 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean contains(double k) {
/*  596 */     return (findKey(k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  601 */     this.count = 0;
/*  602 */     this.tree = null;
/*  603 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
/*      */     implements Cloneable
/*      */   {
/*      */     private static final int BLACK_MASK = 1;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int SUCC_MASK = -2147483648;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int PRED_MASK = 1073741824;
/*      */ 
/*      */ 
/*      */     
/*      */     double key;
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
/*      */     Entry() {}
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(double k) {
/*  642 */       this.key = k;
/*  643 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  652 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  661 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  670 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  679 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  688 */       if (pred) { this.info |= 0x40000000; }
/*  689 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  698 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  699 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  708 */       this.info |= 0x40000000;
/*  709 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  718 */       this.info |= Integer.MIN_VALUE;
/*  719 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  728 */       this.info &= 0xBFFFFFFF;
/*  729 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  738 */       this.info &= Integer.MAX_VALUE;
/*  739 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean black() {
/*  748 */       return ((this.info & 0x1) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void black(boolean black) {
/*  757 */       if (black) { this.info |= 0x1; }
/*  758 */       else { this.info &= 0xFFFFFFFE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  767 */       Entry next = this.right;
/*  768 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  769 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  778 */       Entry prev = this.left;
/*  779 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  780 */       return prev;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  788 */         c = (Entry)super.clone();
/*  789 */       } catch (CloneNotSupportedException cantHappen) {
/*  790 */         throw new InternalError();
/*      */       } 
/*  792 */       c.key = this.key;
/*  793 */       c.info = this.info;
/*  794 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  799 */       if (!(o instanceof Entry)) return false; 
/*  800 */       Entry e = (Entry)o;
/*  801 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(e.key));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  806 */       return HashCommon.double2int(this.key);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  811 */       return String.valueOf(this.key);
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
/*      */   
/*      */   public int size() {
/*  848 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  853 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public double firstDouble() {
/*  858 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  859 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public double lastDouble() {
/*  864 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  865 */     return this.lastEntry.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class SetIterator
/*      */     implements DoubleListIterator
/*      */   {
/*      */     DoubleRBTreeSet.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DoubleRBTreeSet.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DoubleRBTreeSet.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  894 */     int index = 0;
/*      */     
/*      */     SetIterator() {
/*  897 */       this.next = DoubleRBTreeSet.this.firstEntry;
/*      */     }
/*      */     
/*      */     SetIterator(double k) {
/*  901 */       if ((this.next = DoubleRBTreeSet.this.locateKey(k)) != null) {
/*  902 */         if (DoubleRBTreeSet.this.compare(this.next.key, k) <= 0)
/*  903 */         { this.prev = this.next;
/*  904 */           this.next = this.next.next(); }
/*  905 */         else { this.prev = this.next.prev(); }
/*      */       
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  911 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  916 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/*  920 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/*  924 */       this.prev = this.prev.prev();
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/*  929 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public double previousDouble() {
/*  934 */       return (previousEntry()).key;
/*      */     }
/*      */     
/*      */     DoubleRBTreeSet.Entry nextEntry() {
/*  938 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  939 */       this.curr = this.prev = this.next;
/*  940 */       this.index++;
/*  941 */       updateNext();
/*  942 */       return this.curr;
/*      */     }
/*      */     
/*      */     DoubleRBTreeSet.Entry previousEntry() {
/*  946 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  947 */       this.curr = this.next = this.prev;
/*  948 */       this.index--;
/*  949 */       updatePrevious();
/*  950 */       return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  955 */       return this.index;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  960 */       return this.index - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  965 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/*  968 */       if (this.curr == this.prev) this.index--; 
/*  969 */       this.next = this.prev = this.curr;
/*  970 */       updatePrevious();
/*  971 */       updateNext();
/*  972 */       DoubleRBTreeSet.this.remove(this.curr.key);
/*  973 */       this.curr = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleBidirectionalIterator iterator() {
/*  979 */     return new SetIterator();
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleBidirectionalIterator iterator(double from) {
/*  984 */     return new SetIterator(from);
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleComparator comparator() {
/*  989 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet headSet(double to) {
/*  994 */     return new Subset(0.0D, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet tailSet(double from) {
/*  999 */     return new Subset(from, false, 0.0D, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet subSet(double from, double to) {
/* 1004 */     return new Subset(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Subset
/*      */     extends AbstractDoubleSortedSet
/*      */     implements Serializable, DoubleSortedSet
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
/*      */     
/*      */     boolean top;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Subset(double from, boolean bottom, double to, boolean top) {
/* 1037 */       if (!bottom && !top && DoubleRBTreeSet.this.compare(from, to) > 0) throw new IllegalArgumentException("Start element (" + from + ") is larger than end element (" + to + ")"); 
/* 1038 */       this.from = from;
/* 1039 */       this.bottom = bottom;
/* 1040 */       this.to = to;
/* 1041 */       this.top = top;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1046 */       SubsetIterator i = new SubsetIterator();
/* 1047 */       while (i.hasNext()) {
/* 1048 */         i.nextDouble();
/* 1049 */         i.remove();
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
/* 1060 */       return ((this.bottom || DoubleRBTreeSet.this.compare(k, this.from) >= 0) && (this.top || DoubleRBTreeSet.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(double k) {
/* 1066 */       return (in(k) && DoubleRBTreeSet.this.contains(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean add(double k) {
/* 1071 */       if (!in(k)) throw new IllegalArgumentException("Element (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1072 */       return DoubleRBTreeSet.this.add(k);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean remove(double k) {
/* 1078 */       if (!in(k)) return false; 
/* 1079 */       return DoubleRBTreeSet.this.remove(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1084 */       SubsetIterator i = new SubsetIterator();
/* 1085 */       int n = 0;
/* 1086 */       while (i.hasNext()) {
/* 1087 */         n++;
/* 1088 */         i.nextDouble();
/*      */       } 
/* 1090 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1095 */       return !(new SubsetIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1100 */       return DoubleRBTreeSet.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBidirectionalIterator iterator() {
/* 1105 */       return new SubsetIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBidirectionalIterator iterator(double from) {
/* 1110 */       return new SubsetIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSortedSet headSet(double to) {
/* 1115 */       if (this.top) return new Subset(this.from, this.bottom, to, false); 
/* 1116 */       return (DoubleRBTreeSet.this.compare(to, this.to) < 0) ? new Subset(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSortedSet tailSet(double from) {
/* 1121 */       if (this.bottom) return new Subset(from, false, this.to, this.top); 
/* 1122 */       return (DoubleRBTreeSet.this.compare(from, this.from) > 0) ? new Subset(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSortedSet subSet(double from, double to) {
/* 1127 */       if (this.top && this.bottom) return new Subset(from, false, to, false); 
/* 1128 */       if (!this.top) to = (DoubleRBTreeSet.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1129 */       if (!this.bottom) from = (DoubleRBTreeSet.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1130 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1131 */       return new Subset(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleRBTreeSet.Entry firstEntry() {
/*      */       DoubleRBTreeSet.Entry e;
/* 1140 */       if (DoubleRBTreeSet.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1144 */       if (this.bottom) { e = DoubleRBTreeSet.this.firstEntry; }
/*      */       else
/* 1146 */       { e = DoubleRBTreeSet.this.locateKey(this.from);
/*      */         
/* 1148 */         if (DoubleRBTreeSet.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1152 */       if (e == null || (!this.top && DoubleRBTreeSet.this.compare(e.key, this.to) >= 0)) return null; 
/* 1153 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleRBTreeSet.Entry lastEntry() {
/*      */       DoubleRBTreeSet.Entry e;
/* 1162 */       if (DoubleRBTreeSet.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1166 */       if (this.top) { e = DoubleRBTreeSet.this.lastEntry; }
/*      */       else
/* 1168 */       { e = DoubleRBTreeSet.this.locateKey(this.to);
/*      */         
/* 1170 */         if (DoubleRBTreeSet.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1174 */       if (e == null || (!this.bottom && DoubleRBTreeSet.this.compare(e.key, this.from) < 0)) return null; 
/* 1175 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public double firstDouble() {
/* 1180 */       DoubleRBTreeSet.Entry e = firstEntry();
/* 1181 */       if (e == null) throw new NoSuchElementException(); 
/* 1182 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public double lastDouble() {
/* 1187 */       DoubleRBTreeSet.Entry e = lastEntry();
/* 1188 */       if (e == null) throw new NoSuchElementException(); 
/* 1189 */       return e.key;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubsetIterator
/*      */       extends DoubleRBTreeSet.SetIterator
/*      */     {
/*      */       SubsetIterator() {
/* 1203 */         this.next = DoubleRBTreeSet.Subset.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubsetIterator(double k) {
/* 1207 */         this();
/* 1208 */         if (this.next != null) {
/* 1209 */           if (!DoubleRBTreeSet.Subset.this.bottom && DoubleRBTreeSet.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1210 */           else if (!DoubleRBTreeSet.Subset.this.top && DoubleRBTreeSet.this.compare(k, (this.prev = DoubleRBTreeSet.Subset.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1212 */           { this.next = DoubleRBTreeSet.this.locateKey(k);
/* 1213 */             if (DoubleRBTreeSet.this.compare(this.next.key, k) <= 0)
/* 1214 */             { this.prev = this.next;
/* 1215 */               this.next = this.next.next(); }
/* 1216 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1223 */         this.prev = this.prev.prev();
/* 1224 */         if (!DoubleRBTreeSet.Subset.this.bottom && this.prev != null && DoubleRBTreeSet.this.compare(this.prev.key, DoubleRBTreeSet.Subset.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1229 */         this.next = this.next.next();
/* 1230 */         if (!DoubleRBTreeSet.Subset.this.top && this.next != null && DoubleRBTreeSet.this.compare(this.next.key, DoubleRBTreeSet.Subset.this.to) >= 0) this.next = null;
/*      */       
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
/*      */   public Object clone() {
/*      */     DoubleRBTreeSet c;
/*      */     try {
/* 1249 */       c = (DoubleRBTreeSet)super.clone();
/* 1250 */     } catch (CloneNotSupportedException cantHappen) {
/* 1251 */       throw new InternalError();
/*      */     } 
/* 1253 */     c.allocatePaths();
/* 1254 */     if (this.count != 0) {
/*      */       
/* 1256 */       Entry rp = new Entry(), rq = new Entry();
/* 1257 */       Entry p = rp;
/* 1258 */       rp.left(this.tree);
/* 1259 */       Entry q = rq;
/* 1260 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1262 */         if (!p.pred()) {
/* 1263 */           Entry e = p.left.clone();
/* 1264 */           e.pred(q.left);
/* 1265 */           e.succ(q);
/* 1266 */           q.left(e);
/* 1267 */           p = p.left;
/* 1268 */           q = q.left;
/*      */         } else {
/* 1270 */           while (p.succ()) {
/* 1271 */             p = p.right;
/* 1272 */             if (p == null) {
/* 1273 */               q.right = null;
/* 1274 */               c.tree = rq.left;
/* 1275 */               c.firstEntry = c.tree;
/* 1276 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1277 */               c.lastEntry = c.tree;
/* 1278 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1279 */               return c;
/*      */             } 
/* 1281 */             q = q.right;
/*      */           } 
/* 1283 */           p = p.right;
/* 1284 */           q = q.right;
/*      */         } 
/* 1286 */         if (!p.succ()) {
/* 1287 */           Entry e = p.right.clone();
/* 1288 */           e.succ(q.right);
/* 1289 */           e.pred(q);
/* 1290 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1294 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1298 */     int n = this.count;
/* 1299 */     SetIterator i = new SetIterator();
/* 1300 */     s.defaultWriteObject();
/* 1301 */     for (; n-- != 0; s.writeDouble(i.nextDouble()));
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
/* 1314 */     if (n == 1) {
/* 1315 */       Entry entry = new Entry(s.readDouble());
/* 1316 */       entry.pred(pred);
/* 1317 */       entry.succ(succ);
/* 1318 */       entry.black(true);
/* 1319 */       return entry;
/*      */     } 
/* 1321 */     if (n == 2) {
/*      */ 
/*      */       
/* 1324 */       Entry entry = new Entry(s.readDouble());
/* 1325 */       entry.black(true);
/* 1326 */       entry.right(new Entry(s.readDouble()));
/* 1327 */       entry.right.pred(entry);
/* 1328 */       entry.pred(pred);
/* 1329 */       entry.right.succ(succ);
/* 1330 */       return entry;
/*      */     } 
/*      */     
/* 1333 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1334 */     Entry top = new Entry();
/* 1335 */     top.left(readTree(s, leftN, pred, top));
/* 1336 */     top.key = s.readDouble();
/* 1337 */     top.black(true);
/* 1338 */     top.right(readTree(s, rightN, top, succ));
/* 1339 */     if (n + 2 == (n + 2 & -(n + 2))) top.right.black(false);
/*      */     
/* 1341 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1345 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1348 */     setActualComparator();
/* 1349 */     allocatePaths();
/* 1350 */     if (this.count != 0) {
/* 1351 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1353 */       Entry e = this.tree;
/* 1354 */       for (; e.left() != null; e = e.left());
/* 1355 */       this.firstEntry = e;
/* 1356 */       e = this.tree;
/* 1357 */       for (; e.right() != null; e = e.right());
/* 1358 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleRBTreeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */