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
/*      */ 
/*      */ public class DoubleAVLTreeSet
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
/*      */   
/*      */   public DoubleAVLTreeSet() {
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
/*      */   public DoubleAVLTreeSet(Comparator<? super Double> c) {
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
/*      */   public DoubleAVLTreeSet(Collection<? extends Double> c) {
/*   91 */     this();
/*   92 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleAVLTreeSet(SortedSet<Double> s) {
/*  101 */     this(s.comparator());
/*  102 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleAVLTreeSet(DoubleCollection c) {
/*  111 */     this();
/*  112 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleAVLTreeSet(DoubleSortedSet s) {
/*  121 */     this(s.comparator());
/*  122 */     addAll(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleAVLTreeSet(DoubleIterator i) {
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
/*      */   public DoubleAVLTreeSet(Iterator<?> i) {
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
/*      */   public DoubleAVLTreeSet(double[] a, int offset, int length, Comparator<? super Double> c) {
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
/*      */   public DoubleAVLTreeSet(double[] a, int offset, int length) {
/*  167 */     this(a, offset, length, (Comparator<? super Double>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleAVLTreeSet(double[] a) {
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
/*      */   public DoubleAVLTreeSet(double[] a, Comparator<? super Double> c) {
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
/*      */   private void allocatePaths() {
/*  256 */     this.dirPath = new boolean[48];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean add(double k) {
/*  262 */     if (this.tree == null) {
/*  263 */       this.count++;
/*  264 */       this.tree = this.lastEntry = this.firstEntry = new Entry(k);
/*      */     } else {
/*  266 */       Entry p = this.tree, q = null, y = this.tree, z = null, e = null, w = null;
/*  267 */       int i = 0; while (true) {
/*      */         int cmp;
/*  269 */         if ((cmp = compare(k, p.key)) == 0) return false; 
/*  270 */         if (p.balance() != 0) {
/*  271 */           i = 0;
/*  272 */           z = q;
/*  273 */           y = p;
/*      */         } 
/*  275 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  276 */           if (p.succ()) {
/*  277 */             this.count++;
/*  278 */             e = new Entry(k);
/*  279 */             if (p.right == null) this.lastEntry = e; 
/*  280 */             e.left = p;
/*  281 */             e.right = p.right;
/*  282 */             p.right(e);
/*      */             break;
/*      */           } 
/*  285 */           q = p;
/*  286 */           p = p.right; continue;
/*      */         } 
/*  288 */         if (p.pred()) {
/*  289 */           this.count++;
/*  290 */           e = new Entry(k);
/*  291 */           if (p.left == null) this.firstEntry = e; 
/*  292 */           e.right = p;
/*  293 */           e.left = p.left;
/*  294 */           p.left(e);
/*      */           break;
/*      */         } 
/*  297 */         q = p;
/*  298 */         p = p.left;
/*      */       } 
/*      */       
/*  301 */       p = y;
/*  302 */       i = 0;
/*  303 */       while (p != e) {
/*  304 */         if (this.dirPath[i]) { p.incBalance(); }
/*  305 */         else { p.decBalance(); }
/*  306 */          p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  308 */       if (y.balance() == -2)
/*  309 */       { Entry x = y.left;
/*  310 */         if (x.balance() == -1) {
/*  311 */           w = x;
/*  312 */           if (x.succ())
/*  313 */           { x.succ(false);
/*  314 */             y.pred(x); }
/*  315 */           else { y.left = x.right; }
/*  316 */            x.right = y;
/*  317 */           x.balance(0);
/*  318 */           y.balance(0);
/*      */         } else {
/*  320 */           assert x.balance() == 1;
/*  321 */           w = x.right;
/*  322 */           x.right = w.left;
/*  323 */           w.left = x;
/*  324 */           y.left = w.right;
/*  325 */           w.right = y;
/*  326 */           if (w.balance() == -1) {
/*  327 */             x.balance(0);
/*  328 */             y.balance(1);
/*  329 */           } else if (w.balance() == 0) {
/*  330 */             x.balance(0);
/*  331 */             y.balance(0);
/*      */           } else {
/*  333 */             x.balance(-1);
/*  334 */             y.balance(0);
/*      */           } 
/*  336 */           w.balance(0);
/*  337 */           if (w.pred()) {
/*  338 */             x.succ(w);
/*  339 */             w.pred(false);
/*      */           } 
/*  341 */           if (w.succ()) {
/*  342 */             y.pred(w);
/*  343 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  346 */       else if (y.balance() == 2)
/*  347 */       { Entry x = y.right;
/*  348 */         if (x.balance() == 1) {
/*  349 */           w = x;
/*  350 */           if (x.pred())
/*  351 */           { x.pred(false);
/*  352 */             y.succ(x); }
/*  353 */           else { y.right = x.left; }
/*  354 */            x.left = y;
/*  355 */           x.balance(0);
/*  356 */           y.balance(0);
/*      */         } else {
/*  358 */           assert x.balance() == -1;
/*  359 */           w = x.left;
/*  360 */           x.left = w.right;
/*  361 */           w.right = x;
/*  362 */           y.right = w.left;
/*  363 */           w.left = y;
/*  364 */           if (w.balance() == 1) {
/*  365 */             x.balance(0);
/*  366 */             y.balance(-1);
/*  367 */           } else if (w.balance() == 0) {
/*  368 */             x.balance(0);
/*  369 */             y.balance(0);
/*      */           } else {
/*  371 */             x.balance(1);
/*  372 */             y.balance(0);
/*      */           } 
/*  374 */           w.balance(0);
/*  375 */           if (w.pred()) {
/*  376 */             y.succ(w);
/*  377 */             w.pred(false);
/*      */           } 
/*  379 */           if (w.succ()) {
/*  380 */             x.pred(w);
/*  381 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  384 */       else { return true; }
/*  385 */        if (z == null) { this.tree = w; }
/*      */       
/*  387 */       else if (z.left == y) { z.left = w; }
/*  388 */       else { z.right = w; }
/*      */     
/*      */     } 
/*  391 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry parent(Entry e) {
/*  401 */     if (e == this.tree) return null;
/*      */     
/*  403 */     Entry y = e, x = y;
/*      */     while (true) {
/*  405 */       if (y.succ()) {
/*  406 */         Entry p = y.right;
/*  407 */         if (p == null || p.left != e) {
/*  408 */           for (; !x.pred(); x = x.left);
/*  409 */           p = x.left;
/*      */         } 
/*  411 */         return p;
/*  412 */       }  if (x.pred()) {
/*  413 */         Entry p = x.left;
/*  414 */         if (p == null || p.right != e) {
/*  415 */           for (; !y.succ(); y = y.right);
/*  416 */           p = y.right;
/*      */         } 
/*  418 */         return p;
/*      */       } 
/*  420 */       x = x.left;
/*  421 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(double k) {
/*  427 */     if (this.tree == null) return false;
/*      */     
/*  429 */     Entry p = this.tree, q = null;
/*  430 */     boolean dir = false;
/*  431 */     double kk = k;
/*      */     int cmp;
/*  433 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  434 */       if (dir = (cmp > 0)) {
/*  435 */         q = p;
/*  436 */         if ((p = p.right()) == null) return false;  continue;
/*      */       } 
/*  438 */       q = p;
/*  439 */       if ((p = p.left()) == null) return false;
/*      */     
/*      */     } 
/*  442 */     if (p.left == null) this.firstEntry = p.next(); 
/*  443 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  444 */     if (p.succ())
/*  445 */     { if (p.pred())
/*  446 */       { if (q != null)
/*  447 */         { if (dir) { q.succ(p.right); }
/*  448 */           else { q.pred(p.left); }  }
/*  449 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  451 */       else { (p.prev()).right = p.right;
/*  452 */         if (q != null)
/*  453 */         { if (dir) { q.right = p.left; }
/*  454 */           else { q.left = p.left; }  }
/*  455 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  458 */     else { Entry r = p.right;
/*  459 */       if (r.pred()) {
/*  460 */         r.left = p.left;
/*  461 */         r.pred(p.pred());
/*  462 */         if (!r.pred()) (r.prev()).right = r; 
/*  463 */         if (q != null)
/*  464 */         { if (dir) { q.right = r; }
/*  465 */           else { q.left = r; }  }
/*  466 */         else { this.tree = r; }
/*  467 */          r.balance(p.balance());
/*  468 */         q = r;
/*  469 */         dir = true;
/*      */       } else {
/*      */         Entry s;
/*      */         while (true) {
/*  473 */           s = r.left;
/*  474 */           if (s.pred())
/*  475 */             break;  r = s;
/*      */         } 
/*  477 */         if (s.succ()) { r.pred(s); }
/*  478 */         else { r.left = s.right; }
/*  479 */          s.left = p.left;
/*  480 */         if (!p.pred()) {
/*  481 */           (p.prev()).right = s;
/*  482 */           s.pred(false);
/*      */         } 
/*  484 */         s.right = p.right;
/*  485 */         s.succ(false);
/*  486 */         if (q != null)
/*  487 */         { if (dir) { q.right = s; }
/*  488 */           else { q.left = s; }  }
/*  489 */         else { this.tree = s; }
/*  490 */          s.balance(p.balance());
/*  491 */         q = r;
/*  492 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  496 */     while (q != null) {
/*  497 */       Entry y = q;
/*  498 */       q = parent(y);
/*  499 */       if (!dir) {
/*  500 */         dir = (q != null && q.left != y);
/*  501 */         y.incBalance();
/*  502 */         if (y.balance() == 1)
/*  503 */           break;  if (y.balance() == 2) {
/*  504 */           Entry x = y.right;
/*  505 */           assert x != null;
/*  506 */           if (x.balance() == -1) {
/*      */             
/*  508 */             assert x.balance() == -1;
/*  509 */             Entry w = x.left;
/*  510 */             x.left = w.right;
/*  511 */             w.right = x;
/*  512 */             y.right = w.left;
/*  513 */             w.left = y;
/*  514 */             if (w.balance() == 1) {
/*  515 */               x.balance(0);
/*  516 */               y.balance(-1);
/*  517 */             } else if (w.balance() == 0) {
/*  518 */               x.balance(0);
/*  519 */               y.balance(0);
/*      */             } else {
/*  521 */               assert w.balance() == -1;
/*  522 */               x.balance(1);
/*  523 */               y.balance(0);
/*      */             } 
/*  525 */             w.balance(0);
/*  526 */             if (w.pred()) {
/*  527 */               y.succ(w);
/*  528 */               w.pred(false);
/*      */             } 
/*  530 */             if (w.succ()) {
/*  531 */               x.pred(w);
/*  532 */               w.succ(false);
/*      */             } 
/*  534 */             if (q != null) {
/*  535 */               if (dir) { q.right = w; continue; }
/*  536 */                q.left = w; continue;
/*  537 */             }  this.tree = w; continue;
/*      */           } 
/*  539 */           if (q != null)
/*  540 */           { if (dir) { q.right = x; }
/*  541 */             else { q.left = x; }  }
/*  542 */           else { this.tree = x; }
/*  543 */            if (x.balance() == 0) {
/*  544 */             y.right = x.left;
/*  545 */             x.left = y;
/*  546 */             x.balance(-1);
/*  547 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  550 */           assert x.balance() == 1;
/*  551 */           if (x.pred())
/*  552 */           { y.succ(true);
/*  553 */             x.pred(false); }
/*  554 */           else { y.right = x.left; }
/*  555 */            x.left = y;
/*  556 */           y.balance(0);
/*  557 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  561 */       dir = (q != null && q.left != y);
/*  562 */       y.decBalance();
/*  563 */       if (y.balance() == -1)
/*  564 */         break;  if (y.balance() == -2) {
/*  565 */         Entry x = y.left;
/*  566 */         assert x != null;
/*  567 */         if (x.balance() == 1) {
/*      */           
/*  569 */           assert x.balance() == 1;
/*  570 */           Entry w = x.right;
/*  571 */           x.right = w.left;
/*  572 */           w.left = x;
/*  573 */           y.left = w.right;
/*  574 */           w.right = y;
/*  575 */           if (w.balance() == -1) {
/*  576 */             x.balance(0);
/*  577 */             y.balance(1);
/*  578 */           } else if (w.balance() == 0) {
/*  579 */             x.balance(0);
/*  580 */             y.balance(0);
/*      */           } else {
/*  582 */             assert w.balance() == 1;
/*  583 */             x.balance(-1);
/*  584 */             y.balance(0);
/*      */           } 
/*  586 */           w.balance(0);
/*  587 */           if (w.pred()) {
/*  588 */             x.succ(w);
/*  589 */             w.pred(false);
/*      */           } 
/*  591 */           if (w.succ()) {
/*  592 */             y.pred(w);
/*  593 */             w.succ(false);
/*      */           } 
/*  595 */           if (q != null) {
/*  596 */             if (dir) { q.right = w; continue; }
/*  597 */              q.left = w; continue;
/*  598 */           }  this.tree = w; continue;
/*      */         } 
/*  600 */         if (q != null)
/*  601 */         { if (dir) { q.right = x; }
/*  602 */           else { q.left = x; }  }
/*  603 */         else { this.tree = x; }
/*  604 */          if (x.balance() == 0) {
/*  605 */           y.left = x.right;
/*  606 */           x.right = y;
/*  607 */           x.balance(1);
/*  608 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  611 */         assert x.balance() == -1;
/*  612 */         if (x.succ())
/*  613 */         { y.pred(true);
/*  614 */           x.succ(false); }
/*  615 */         else { y.left = x.right; }
/*  616 */          x.right = y;
/*  617 */         y.balance(0);
/*  618 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  623 */     this.count--;
/*  624 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean contains(double k) {
/*  629 */     return (findKey(k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  634 */     this.count = 0;
/*  635 */     this.tree = null;
/*  636 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
/*      */     implements Cloneable
/*      */   {
/*      */     private static final int SUCC_MASK = -2147483648;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int PRED_MASK = 1073741824;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int BALANCE_MASK = 255;
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
/*  675 */       this.key = k;
/*  676 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  685 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  694 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  703 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  712 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  721 */       if (pred) { this.info |= 0x40000000; }
/*  722 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  731 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  732 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  741 */       this.info |= 0x40000000;
/*  742 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  751 */       this.info |= Integer.MIN_VALUE;
/*  752 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  761 */       this.info &= 0xBFFFFFFF;
/*  762 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  771 */       this.info &= Integer.MAX_VALUE;
/*  772 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  781 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  790 */       this.info &= 0xFFFFFF00;
/*  791 */       this.info |= level & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     void incBalance() {
/*  796 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void decBalance() {
/*  801 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  810 */       Entry next = this.right;
/*  811 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  812 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  821 */       Entry prev = this.left;
/*  822 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  823 */       return prev;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  831 */         c = (Entry)super.clone();
/*  832 */       } catch (CloneNotSupportedException cantHappen) {
/*  833 */         throw new InternalError();
/*      */       } 
/*  835 */       c.key = this.key;
/*  836 */       c.info = this.info;
/*  837 */       return c;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  842 */       if (!(o instanceof Entry)) return false; 
/*  843 */       Entry e = (Entry)o;
/*  844 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(e.key));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  849 */       return HashCommon.double2int(this.key);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  854 */       return String.valueOf(this.key);
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
/*  891 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  896 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public double firstDouble() {
/*  901 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  902 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public double lastDouble() {
/*  907 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  908 */     return this.lastEntry.key;
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
/*      */     DoubleAVLTreeSet.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DoubleAVLTreeSet.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DoubleAVLTreeSet.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  937 */     int index = 0;
/*      */     
/*      */     SetIterator() {
/*  940 */       this.next = DoubleAVLTreeSet.this.firstEntry;
/*      */     }
/*      */     
/*      */     SetIterator(double k) {
/*  944 */       if ((this.next = DoubleAVLTreeSet.this.locateKey(k)) != null) {
/*  945 */         if (DoubleAVLTreeSet.this.compare(this.next.key, k) <= 0)
/*  946 */         { this.prev = this.next;
/*  947 */           this.next = this.next.next(); }
/*  948 */         else { this.prev = this.next.prev(); }
/*      */       
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  954 */       return (this.next != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  959 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/*  963 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     DoubleAVLTreeSet.Entry nextEntry() {
/*  967 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  968 */       this.curr = this.prev = this.next;
/*  969 */       this.index++;
/*  970 */       updateNext();
/*  971 */       return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/*  976 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public double previousDouble() {
/*  981 */       return (previousEntry()).key;
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/*  985 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     DoubleAVLTreeSet.Entry previousEntry() {
/*  989 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  990 */       this.curr = this.next = this.prev;
/*  991 */       this.index--;
/*  992 */       updatePrevious();
/*  993 */       return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  998 */       return this.index;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1003 */       return this.index - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1008 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/* 1011 */       if (this.curr == this.prev) this.index--; 
/* 1012 */       this.next = this.prev = this.curr;
/* 1013 */       updatePrevious();
/* 1014 */       updateNext();
/* 1015 */       DoubleAVLTreeSet.this.remove(this.curr.key);
/* 1016 */       this.curr = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleBidirectionalIterator iterator() {
/* 1022 */     return new SetIterator();
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleBidirectionalIterator iterator(double from) {
/* 1027 */     return new SetIterator(from);
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1032 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet headSet(double to) {
/* 1037 */     return new Subset(0.0D, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet tailSet(double from) {
/* 1042 */     return new Subset(from, false, 0.0D, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet subSet(double from, double to) {
/* 1047 */     return new Subset(from, false, to, false);
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
/* 1080 */       if (!bottom && !top && DoubleAVLTreeSet.this.compare(from, to) > 0) throw new IllegalArgumentException("Start element (" + from + ") is larger than end element (" + to + ")"); 
/* 1081 */       this.from = from;
/* 1082 */       this.bottom = bottom;
/* 1083 */       this.to = to;
/* 1084 */       this.top = top;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1089 */       SubsetIterator i = new SubsetIterator();
/* 1090 */       while (i.hasNext()) {
/* 1091 */         i.nextDouble();
/* 1092 */         i.remove();
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
/* 1103 */       return ((this.bottom || DoubleAVLTreeSet.this.compare(k, this.from) >= 0) && (this.top || DoubleAVLTreeSet.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(double k) {
/* 1108 */       return (in(k) && DoubleAVLTreeSet.this.contains(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean add(double k) {
/* 1113 */       if (!in(k)) throw new IllegalArgumentException("Element (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1114 */       return DoubleAVLTreeSet.this.add(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(double k) {
/* 1119 */       if (!in(k)) return false; 
/* 1120 */       return DoubleAVLTreeSet.this.remove(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1125 */       SubsetIterator i = new SubsetIterator();
/* 1126 */       int n = 0;
/* 1127 */       while (i.hasNext()) {
/* 1128 */         n++;
/* 1129 */         i.nextDouble();
/*      */       } 
/* 1131 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1136 */       return !(new SubsetIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1141 */       return DoubleAVLTreeSet.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBidirectionalIterator iterator() {
/* 1146 */       return new SubsetIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBidirectionalIterator iterator(double from) {
/* 1151 */       return new SubsetIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSortedSet headSet(double to) {
/* 1156 */       if (this.top) return new Subset(this.from, this.bottom, to, false); 
/* 1157 */       return (DoubleAVLTreeSet.this.compare(to, this.to) < 0) ? new Subset(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSortedSet tailSet(double from) {
/* 1162 */       if (this.bottom) return new Subset(from, false, this.to, this.top); 
/* 1163 */       return (DoubleAVLTreeSet.this.compare(from, this.from) > 0) ? new Subset(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSortedSet subSet(double from, double to) {
/* 1168 */       if (this.top && this.bottom) return new Subset(from, false, to, false); 
/* 1169 */       if (!this.top) to = (DoubleAVLTreeSet.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1170 */       if (!this.bottom) from = (DoubleAVLTreeSet.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1171 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1172 */       return new Subset(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleAVLTreeSet.Entry firstEntry() {
/*      */       DoubleAVLTreeSet.Entry e;
/* 1181 */       if (DoubleAVLTreeSet.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1185 */       if (this.bottom) { e = DoubleAVLTreeSet.this.firstEntry; }
/*      */       else
/* 1187 */       { e = DoubleAVLTreeSet.this.locateKey(this.from);
/*      */         
/* 1189 */         if (DoubleAVLTreeSet.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1193 */       if (e == null || (!this.top && DoubleAVLTreeSet.this.compare(e.key, this.to) >= 0)) return null; 
/* 1194 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DoubleAVLTreeSet.Entry lastEntry() {
/*      */       DoubleAVLTreeSet.Entry e;
/* 1203 */       if (DoubleAVLTreeSet.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1207 */       if (this.top) { e = DoubleAVLTreeSet.this.lastEntry; }
/*      */       else
/* 1209 */       { e = DoubleAVLTreeSet.this.locateKey(this.to);
/*      */         
/* 1211 */         if (DoubleAVLTreeSet.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1215 */       if (e == null || (!this.bottom && DoubleAVLTreeSet.this.compare(e.key, this.from) < 0)) return null; 
/* 1216 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public double firstDouble() {
/* 1221 */       DoubleAVLTreeSet.Entry e = firstEntry();
/* 1222 */       if (e == null) throw new NoSuchElementException(); 
/* 1223 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public double lastDouble() {
/* 1228 */       DoubleAVLTreeSet.Entry e = lastEntry();
/* 1229 */       if (e == null) throw new NoSuchElementException(); 
/* 1230 */       return e.key;
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
/*      */       extends DoubleAVLTreeSet.SetIterator
/*      */     {
/*      */       SubsetIterator() {
/* 1244 */         this.next = DoubleAVLTreeSet.Subset.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubsetIterator(double k) {
/* 1248 */         this();
/* 1249 */         if (this.next != null) {
/* 1250 */           if (!DoubleAVLTreeSet.Subset.this.bottom && DoubleAVLTreeSet.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1251 */           else if (!DoubleAVLTreeSet.Subset.this.top && DoubleAVLTreeSet.this.compare(k, (this.prev = DoubleAVLTreeSet.Subset.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1253 */           { this.next = DoubleAVLTreeSet.this.locateKey(k);
/* 1254 */             if (DoubleAVLTreeSet.this.compare(this.next.key, k) <= 0)
/* 1255 */             { this.prev = this.next;
/* 1256 */               this.next = this.next.next(); }
/* 1257 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1264 */         this.prev = this.prev.prev();
/* 1265 */         if (!DoubleAVLTreeSet.Subset.this.bottom && this.prev != null && DoubleAVLTreeSet.this.compare(this.prev.key, DoubleAVLTreeSet.Subset.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1270 */         this.next = this.next.next();
/* 1271 */         if (!DoubleAVLTreeSet.Subset.this.top && this.next != null && DoubleAVLTreeSet.this.compare(this.next.key, DoubleAVLTreeSet.Subset.this.to) >= 0) this.next = null;
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
/*      */     DoubleAVLTreeSet c;
/*      */     try {
/* 1290 */       c = (DoubleAVLTreeSet)super.clone();
/* 1291 */     } catch (CloneNotSupportedException cantHappen) {
/* 1292 */       throw new InternalError();
/*      */     } 
/* 1294 */     c.allocatePaths();
/* 1295 */     if (this.count != 0) {
/*      */       
/* 1297 */       Entry rp = new Entry(), rq = new Entry();
/* 1298 */       Entry p = rp;
/* 1299 */       rp.left(this.tree);
/* 1300 */       Entry q = rq;
/* 1301 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1303 */         if (!p.pred()) {
/* 1304 */           Entry e = p.left.clone();
/* 1305 */           e.pred(q.left);
/* 1306 */           e.succ(q);
/* 1307 */           q.left(e);
/* 1308 */           p = p.left;
/* 1309 */           q = q.left;
/*      */         } else {
/* 1311 */           while (p.succ()) {
/* 1312 */             p = p.right;
/* 1313 */             if (p == null) {
/* 1314 */               q.right = null;
/* 1315 */               c.tree = rq.left;
/* 1316 */               c.firstEntry = c.tree;
/* 1317 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1318 */               c.lastEntry = c.tree;
/* 1319 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1320 */               return c;
/*      */             } 
/* 1322 */             q = q.right;
/*      */           } 
/* 1324 */           p = p.right;
/* 1325 */           q = q.right;
/*      */         } 
/* 1327 */         if (!p.succ()) {
/* 1328 */           Entry e = p.right.clone();
/* 1329 */           e.succ(q.right);
/* 1330 */           e.pred(q);
/* 1331 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1335 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1339 */     int n = this.count;
/* 1340 */     SetIterator i = new SetIterator();
/* 1341 */     s.defaultWriteObject();
/* 1342 */     for (; n-- != 0; s.writeDouble(i.nextDouble()));
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
/* 1355 */     if (n == 1) {
/* 1356 */       Entry entry = new Entry(s.readDouble());
/* 1357 */       entry.pred(pred);
/* 1358 */       entry.succ(succ);
/* 1359 */       return entry;
/*      */     } 
/* 1361 */     if (n == 2) {
/*      */ 
/*      */       
/* 1364 */       Entry entry = new Entry(s.readDouble());
/* 1365 */       entry.right(new Entry(s.readDouble()));
/* 1366 */       entry.right.pred(entry);
/* 1367 */       entry.balance(1);
/* 1368 */       entry.pred(pred);
/* 1369 */       entry.right.succ(succ);
/* 1370 */       return entry;
/*      */     } 
/*      */     
/* 1373 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1374 */     Entry top = new Entry();
/* 1375 */     top.left(readTree(s, leftN, pred, top));
/* 1376 */     top.key = s.readDouble();
/* 1377 */     top.right(readTree(s, rightN, top, succ));
/* 1378 */     if (n == (n & -n)) top.balance(1); 
/* 1379 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1383 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1386 */     setActualComparator();
/* 1387 */     allocatePaths();
/* 1388 */     if (this.count != 0) {
/* 1389 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1391 */       Entry e = this.tree;
/* 1392 */       for (; e.left() != null; e = e.left());
/* 1393 */       this.firstEntry = e;
/* 1394 */       e = this.tree;
/* 1395 */       for (; e.right() != null; e = e.right());
/* 1396 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleAVLTreeSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */