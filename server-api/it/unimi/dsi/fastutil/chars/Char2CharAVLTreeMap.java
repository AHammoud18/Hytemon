/*      */ package it.unimi.dsi.fastutil.chars;
/*      */ 
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
/*      */ public class Char2CharAVLTreeMap
/*      */   extends AbstractChar2CharSortedMap
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry tree;
/*      */   protected int count;
/*      */   protected transient Entry firstEntry;
/*      */   protected transient Entry lastEntry;
/*      */   protected transient ObjectSortedSet<Char2CharMap.Entry> entries;
/*      */   protected transient CharSortedSet keys;
/*      */   protected transient CharCollection values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Character> storedComparator;
/*      */   protected transient CharComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Char2CharAVLTreeMap() {
/*   66 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   73 */     this.tree = null;
/*   74 */     this.count = 0;
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
/*   86 */     this.actualComparator = CharComparators.asCharComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2CharAVLTreeMap(Comparator<? super Character> c) {
/*   95 */     this();
/*   96 */     this.storedComparator = c;
/*   97 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2CharAVLTreeMap(Map<? extends Character, ? extends Character> m) {
/*  106 */     this();
/*  107 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2CharAVLTreeMap(SortedMap<Character, Character> m) {
/*  116 */     this(m.comparator());
/*  117 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2CharAVLTreeMap(Char2CharMap m) {
/*  126 */     this();
/*  127 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2CharAVLTreeMap(Char2CharSortedMap m) {
/*  136 */     this(m.comparator());
/*  137 */     putAll(m);
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
/*      */   public Char2CharAVLTreeMap(char[] k, char[] v, Comparator<? super Character> c) {
/*  149 */     this(c);
/*  150 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  151 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Char2CharAVLTreeMap(char[] k, char[] v) {
/*  162 */     this(k, v, (Comparator<? super Character>)null);
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
/*      */   final int compare(char k1, char k2) {
/*  188 */     return (this.actualComparator == null) ? Character.compare(k1, k2) : this.actualComparator.compare(k1, k2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry findKey(char k) {
/*  198 */     Entry e = this.tree;
/*      */     int cmp;
/*  200 */     for (; e != null && (cmp = compare(k, e.key)) != 0; e = (cmp < 0) ? e.left() : e.right());
/*  201 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry locateKey(char k) {
/*  212 */     Entry e = this.tree, last = this.tree;
/*  213 */     int cmp = 0;
/*  214 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  215 */       last = e;
/*  216 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  218 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  228 */     this.dirPath = new boolean[48];
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
/*      */   public char addTo(char k, char incr) {
/*  245 */     Entry e = add(k);
/*  246 */     char oldValue = e.value;
/*  247 */     e.value = (char)(e.value + incr);
/*  248 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char put(char k, char v) {
/*  253 */     Entry e = add(k);
/*  254 */     char oldValue = e.value;
/*  255 */     e.value = v;
/*  256 */     return oldValue;
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
/*      */   private Entry add(char k) {
/*  270 */     this.modified = false;
/*  271 */     Entry e = null;
/*  272 */     if (this.tree == null) {
/*  273 */       this.count++;
/*  274 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry(k, this.defRetValue);
/*  275 */       this.modified = true;
/*      */     } else {
/*  277 */       Entry p = this.tree, q = null, y = this.tree, z = null, w = null;
/*  278 */       int i = 0; while (true) {
/*      */         int cmp;
/*  280 */         if ((cmp = compare(k, p.key)) == 0) {
/*  281 */           return p;
/*      */         }
/*  283 */         if (p.balance() != 0) {
/*  284 */           i = 0;
/*  285 */           z = q;
/*  286 */           y = p;
/*      */         } 
/*  288 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  289 */           if (p.succ()) {
/*  290 */             this.count++;
/*  291 */             e = new Entry(k, this.defRetValue);
/*  292 */             this.modified = true;
/*  293 */             if (p.right == null) this.lastEntry = e; 
/*  294 */             e.left = p;
/*  295 */             e.right = p.right;
/*  296 */             p.right(e);
/*      */             break;
/*      */           } 
/*  299 */           q = p;
/*  300 */           p = p.right; continue;
/*      */         } 
/*  302 */         if (p.pred()) {
/*  303 */           this.count++;
/*  304 */           e = new Entry(k, this.defRetValue);
/*  305 */           this.modified = true;
/*  306 */           if (p.left == null) this.firstEntry = e; 
/*  307 */           e.right = p;
/*  308 */           e.left = p.left;
/*  309 */           p.left(e);
/*      */           break;
/*      */         } 
/*  312 */         q = p;
/*  313 */         p = p.left;
/*      */       } 
/*      */       
/*  316 */       p = y;
/*  317 */       i = 0;
/*  318 */       while (p != e) {
/*  319 */         if (this.dirPath[i]) { p.incBalance(); }
/*  320 */         else { p.decBalance(); }
/*  321 */          p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  323 */       if (y.balance() == -2)
/*  324 */       { Entry x = y.left;
/*  325 */         if (x.balance() == -1) {
/*  326 */           w = x;
/*  327 */           if (x.succ())
/*  328 */           { x.succ(false);
/*  329 */             y.pred(x); }
/*  330 */           else { y.left = x.right; }
/*  331 */            x.right = y;
/*  332 */           x.balance(0);
/*  333 */           y.balance(0);
/*      */         } else {
/*  335 */           assert x.balance() == 1;
/*  336 */           w = x.right;
/*  337 */           x.right = w.left;
/*  338 */           w.left = x;
/*  339 */           y.left = w.right;
/*  340 */           w.right = y;
/*  341 */           if (w.balance() == -1) {
/*  342 */             x.balance(0);
/*  343 */             y.balance(1);
/*  344 */           } else if (w.balance() == 0) {
/*  345 */             x.balance(0);
/*  346 */             y.balance(0);
/*      */           } else {
/*  348 */             x.balance(-1);
/*  349 */             y.balance(0);
/*      */           } 
/*  351 */           w.balance(0);
/*  352 */           if (w.pred()) {
/*  353 */             x.succ(w);
/*  354 */             w.pred(false);
/*      */           } 
/*  356 */           if (w.succ()) {
/*  357 */             y.pred(w);
/*  358 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  361 */       else if (y.balance() == 2)
/*  362 */       { Entry x = y.right;
/*  363 */         if (x.balance() == 1) {
/*  364 */           w = x;
/*  365 */           if (x.pred())
/*  366 */           { x.pred(false);
/*  367 */             y.succ(x); }
/*  368 */           else { y.right = x.left; }
/*  369 */            x.left = y;
/*  370 */           x.balance(0);
/*  371 */           y.balance(0);
/*      */         } else {
/*  373 */           assert x.balance() == -1;
/*  374 */           w = x.left;
/*  375 */           x.left = w.right;
/*  376 */           w.right = x;
/*  377 */           y.right = w.left;
/*  378 */           w.left = y;
/*  379 */           if (w.balance() == 1) {
/*  380 */             x.balance(0);
/*  381 */             y.balance(-1);
/*  382 */           } else if (w.balance() == 0) {
/*  383 */             x.balance(0);
/*  384 */             y.balance(0);
/*      */           } else {
/*  386 */             x.balance(1);
/*  387 */             y.balance(0);
/*      */           } 
/*  389 */           w.balance(0);
/*  390 */           if (w.pred()) {
/*  391 */             y.succ(w);
/*  392 */             w.pred(false);
/*      */           } 
/*  394 */           if (w.succ()) {
/*  395 */             x.pred(w);
/*  396 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  399 */       else { return e; }
/*  400 */        if (z == null) { this.tree = w; }
/*      */       
/*  402 */       else if (z.left == y) { z.left = w; }
/*  403 */       else { z.right = w; }
/*      */     
/*      */     } 
/*  406 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry parent(Entry e) {
/*  416 */     if (e == this.tree) return null;
/*      */     
/*  418 */     Entry y = e, x = y;
/*      */     while (true) {
/*  420 */       if (y.succ()) {
/*  421 */         Entry p = y.right;
/*  422 */         if (p == null || p.left != e) {
/*  423 */           for (; !x.pred(); x = x.left);
/*  424 */           p = x.left;
/*      */         } 
/*  426 */         return p;
/*  427 */       }  if (x.pred()) {
/*  428 */         Entry p = x.left;
/*  429 */         if (p == null || p.right != e) {
/*  430 */           for (; !y.succ(); y = y.right);
/*  431 */           p = y.right;
/*      */         } 
/*  433 */         return p;
/*      */       } 
/*  435 */       x = x.left;
/*  436 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char remove(char k) {
/*  444 */     this.modified = false;
/*  445 */     if (this.tree == null) return this.defRetValue;
/*      */     
/*  447 */     Entry p = this.tree, q = null;
/*  448 */     boolean dir = false;
/*  449 */     char kk = k;
/*      */     int cmp;
/*  451 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  452 */       if (dir = (cmp > 0)) {
/*  453 */         q = p;
/*  454 */         if ((p = p.right()) == null) return this.defRetValue;  continue;
/*      */       } 
/*  456 */       q = p;
/*  457 */       if ((p = p.left()) == null) return this.defRetValue;
/*      */     
/*      */     } 
/*  460 */     if (p.left == null) this.firstEntry = p.next(); 
/*  461 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  462 */     if (p.succ())
/*  463 */     { if (p.pred())
/*  464 */       { if (q != null)
/*  465 */         { if (dir) { q.succ(p.right); }
/*  466 */           else { q.pred(p.left); }  }
/*  467 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  469 */       else { (p.prev()).right = p.right;
/*  470 */         if (q != null)
/*  471 */         { if (dir) { q.right = p.left; }
/*  472 */           else { q.left = p.left; }  }
/*  473 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  476 */     else { Entry r = p.right;
/*  477 */       if (r.pred()) {
/*  478 */         r.left = p.left;
/*  479 */         r.pred(p.pred());
/*  480 */         if (!r.pred()) (r.prev()).right = r; 
/*  481 */         if (q != null)
/*  482 */         { if (dir) { q.right = r; }
/*  483 */           else { q.left = r; }  }
/*  484 */         else { this.tree = r; }
/*  485 */          r.balance(p.balance());
/*  486 */         q = r;
/*  487 */         dir = true;
/*      */       } else {
/*      */         Entry s;
/*      */         while (true) {
/*  491 */           s = r.left;
/*  492 */           if (s.pred())
/*  493 */             break;  r = s;
/*      */         } 
/*  495 */         if (s.succ()) { r.pred(s); }
/*  496 */         else { r.left = s.right; }
/*  497 */          s.left = p.left;
/*  498 */         if (!p.pred()) {
/*  499 */           (p.prev()).right = s;
/*  500 */           s.pred(false);
/*      */         } 
/*  502 */         s.right = p.right;
/*  503 */         s.succ(false);
/*  504 */         if (q != null)
/*  505 */         { if (dir) { q.right = s; }
/*  506 */           else { q.left = s; }  }
/*  507 */         else { this.tree = s; }
/*  508 */          s.balance(p.balance());
/*  509 */         q = r;
/*  510 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  514 */     while (q != null) {
/*  515 */       Entry y = q;
/*  516 */       q = parent(y);
/*  517 */       if (!dir) {
/*  518 */         dir = (q != null && q.left != y);
/*  519 */         y.incBalance();
/*  520 */         if (y.balance() == 1)
/*  521 */           break;  if (y.balance() == 2) {
/*  522 */           Entry x = y.right;
/*  523 */           assert x != null;
/*  524 */           if (x.balance() == -1) {
/*      */             
/*  526 */             assert x.balance() == -1;
/*  527 */             Entry w = x.left;
/*  528 */             x.left = w.right;
/*  529 */             w.right = x;
/*  530 */             y.right = w.left;
/*  531 */             w.left = y;
/*  532 */             if (w.balance() == 1) {
/*  533 */               x.balance(0);
/*  534 */               y.balance(-1);
/*  535 */             } else if (w.balance() == 0) {
/*  536 */               x.balance(0);
/*  537 */               y.balance(0);
/*      */             } else {
/*  539 */               assert w.balance() == -1;
/*  540 */               x.balance(1);
/*  541 */               y.balance(0);
/*      */             } 
/*  543 */             w.balance(0);
/*  544 */             if (w.pred()) {
/*  545 */               y.succ(w);
/*  546 */               w.pred(false);
/*      */             } 
/*  548 */             if (w.succ()) {
/*  549 */               x.pred(w);
/*  550 */               w.succ(false);
/*      */             } 
/*  552 */             if (q != null) {
/*  553 */               if (dir) { q.right = w; continue; }
/*  554 */                q.left = w; continue;
/*  555 */             }  this.tree = w; continue;
/*      */           } 
/*  557 */           if (q != null)
/*  558 */           { if (dir) { q.right = x; }
/*  559 */             else { q.left = x; }  }
/*  560 */           else { this.tree = x; }
/*  561 */            if (x.balance() == 0) {
/*  562 */             y.right = x.left;
/*  563 */             x.left = y;
/*  564 */             x.balance(-1);
/*  565 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  568 */           assert x.balance() == 1;
/*  569 */           if (x.pred())
/*  570 */           { y.succ(true);
/*  571 */             x.pred(false); }
/*  572 */           else { y.right = x.left; }
/*  573 */            x.left = y;
/*  574 */           y.balance(0);
/*  575 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  579 */       dir = (q != null && q.left != y);
/*  580 */       y.decBalance();
/*  581 */       if (y.balance() == -1)
/*  582 */         break;  if (y.balance() == -2) {
/*  583 */         Entry x = y.left;
/*  584 */         assert x != null;
/*  585 */         if (x.balance() == 1) {
/*      */           
/*  587 */           assert x.balance() == 1;
/*  588 */           Entry w = x.right;
/*  589 */           x.right = w.left;
/*  590 */           w.left = x;
/*  591 */           y.left = w.right;
/*  592 */           w.right = y;
/*  593 */           if (w.balance() == -1) {
/*  594 */             x.balance(0);
/*  595 */             y.balance(1);
/*  596 */           } else if (w.balance() == 0) {
/*  597 */             x.balance(0);
/*  598 */             y.balance(0);
/*      */           } else {
/*  600 */             assert w.balance() == 1;
/*  601 */             x.balance(-1);
/*  602 */             y.balance(0);
/*      */           } 
/*  604 */           w.balance(0);
/*  605 */           if (w.pred()) {
/*  606 */             x.succ(w);
/*  607 */             w.pred(false);
/*      */           } 
/*  609 */           if (w.succ()) {
/*  610 */             y.pred(w);
/*  611 */             w.succ(false);
/*      */           } 
/*  613 */           if (q != null) {
/*  614 */             if (dir) { q.right = w; continue; }
/*  615 */              q.left = w; continue;
/*  616 */           }  this.tree = w; continue;
/*      */         } 
/*  618 */         if (q != null)
/*  619 */         { if (dir) { q.right = x; }
/*  620 */           else { q.left = x; }  }
/*  621 */         else { this.tree = x; }
/*  622 */          if (x.balance() == 0) {
/*  623 */           y.left = x.right;
/*  624 */           x.right = y;
/*  625 */           x.balance(1);
/*  626 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  629 */         assert x.balance() == -1;
/*  630 */         if (x.succ())
/*  631 */         { y.pred(true);
/*  632 */           x.succ(false); }
/*  633 */         else { y.left = x.right; }
/*  634 */          x.right = y;
/*  635 */         y.balance(0);
/*  636 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  641 */     this.modified = true;
/*  642 */     this.count--;
/*  643 */     return p.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(char v) {
/*  648 */     ValueIterator i = new ValueIterator();
/*      */     
/*  650 */     int j = this.count;
/*  651 */     while (j-- != 0) {
/*  652 */       char ev = i.nextChar();
/*  653 */       if (ev == v) return true; 
/*      */     } 
/*  655 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  660 */     this.count = 0;
/*  661 */     this.tree = null;
/*  662 */     this.entries = null;
/*  663 */     this.values = null;
/*  664 */     this.keys = null;
/*  665 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry
/*      */     extends AbstractChar2CharMap.BasicEntry
/*      */     implements Cloneable
/*      */   {
/*      */     private static final int SUCC_MASK = -2147483648;
/*      */ 
/*      */     
/*      */     private static final int PRED_MASK = 1073741824;
/*      */ 
/*      */     
/*      */     private static final int BALANCE_MASK = 255;
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
/*  694 */       super(false, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(char k, char v) {
/*  704 */       super(k, v);
/*  705 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry left() {
/*  714 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry right() {
/*  723 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  732 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  741 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  750 */       if (pred) { this.info |= 0x40000000; }
/*  751 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  760 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  761 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry pred) {
/*  770 */       this.info |= 0x40000000;
/*  771 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry succ) {
/*  780 */       this.info |= Integer.MIN_VALUE;
/*  781 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry left) {
/*  790 */       this.info &= 0xBFFFFFFF;
/*  791 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry right) {
/*  800 */       this.info &= Integer.MAX_VALUE;
/*  801 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  810 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  819 */       this.info &= 0xFFFFFF00;
/*  820 */       this.info |= level & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     void incBalance() {
/*  825 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void decBalance() {
/*  830 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry next() {
/*  839 */       Entry next = this.right;
/*  840 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  841 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry prev() {
/*  850 */       Entry prev = this.left;
/*  851 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  852 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public char setValue(char value) {
/*  857 */       char oldValue = this.value;
/*  858 */       this.value = value;
/*  859 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry clone() {
/*      */       Entry c;
/*      */       try {
/*  867 */         c = (Entry)super.clone();
/*  868 */       } catch (CloneNotSupportedException cantHappen) {
/*  869 */         throw new InternalError();
/*      */       } 
/*  871 */       c.key = this.key;
/*  872 */       c.value = this.value;
/*  873 */       c.info = this.info;
/*  874 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  880 */       if (!(o instanceof Map.Entry)) return false; 
/*  881 */       Map.Entry<Character, Character> e = (Map.Entry<Character, Character>)o;
/*  882 */       return (this.key == ((Character)e.getKey()).charValue() && this.value == ((Character)e.getValue()).charValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  887 */       return this.key ^ this.value;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  892 */       return this.key + "=>" + this.value;
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
/*      */   
/*      */   public boolean containsKey(char k) {
/*  930 */     return (findKey(k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  935 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  940 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public char get(char k) {
/*  945 */     Entry e = findKey(k);
/*  946 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public char firstCharKey() {
/*  951 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  952 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public char lastCharKey() {
/*  957 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  958 */     return this.lastEntry.key;
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
/*      */     Char2CharAVLTreeMap.Entry prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Char2CharAVLTreeMap.Entry next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Char2CharAVLTreeMap.Entry curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  987 */     int index = 0;
/*      */     
/*      */     TreeIterator() {
/*  990 */       this.next = Char2CharAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     
/*      */     TreeIterator(char k) {
/*  994 */       if ((this.next = Char2CharAVLTreeMap.this.locateKey(k)) != null)
/*  995 */         if (Char2CharAVLTreeMap.this.compare(this.next.key, k) <= 0)
/*  996 */         { this.prev = this.next;
/*  997 */           this.next = this.next.next(); }
/*  998 */         else { this.prev = this.next.prev(); }
/*      */          
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1003 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1007 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/* 1011 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     Char2CharAVLTreeMap.Entry nextEntry() {
/* 1015 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1016 */       this.curr = this.prev = this.next;
/* 1017 */       this.index++;
/* 1018 */       updateNext();
/* 1019 */       return this.curr;
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/* 1023 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     Char2CharAVLTreeMap.Entry previousEntry() {
/* 1027 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1028 */       this.curr = this.next = this.prev;
/* 1029 */       this.index--;
/* 1030 */       updatePrevious();
/* 1031 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1035 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1039 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1043 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/* 1046 */       if (this.curr == this.prev) this.index--; 
/* 1047 */       this.next = this.prev = this.curr;
/* 1048 */       updatePrevious();
/* 1049 */       updateNext();
/* 1050 */       Char2CharAVLTreeMap.this.remove(this.curr.key);
/* 1051 */       this.curr = null;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1055 */       int i = n;
/* 1056 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1057 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1061 */       int i = n;
/* 1062 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1063 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Char2CharMap.Entry>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     EntryIterator(char k) {
/* 1078 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public Char2CharMap.Entry next() {
/* 1083 */       return nextEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Char2CharMap.Entry previous() {
/* 1088 */       return previousEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(Char2CharMap.Entry ok) {
/* 1093 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(Char2CharMap.Entry ok) {
/* 1098 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<Char2CharMap.Entry> char2CharEntrySet() {
/* 1105 */     if (this.entries == null) this.entries = (ObjectSortedSet<Char2CharMap.Entry>)new AbstractObjectSortedSet<Char2CharMap.Entry>()
/*      */         {
/*      */           final Comparator<? super Char2CharMap.Entry> comparator;
/*      */           
/*      */           public Comparator<? super Char2CharMap.Entry> comparator() {
/* 1110 */             return this.comparator;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Char2CharMap.Entry> iterator() {
/* 1115 */             return (ObjectBidirectionalIterator<Char2CharMap.Entry>)new Char2CharAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Char2CharMap.Entry> iterator(Char2CharMap.Entry from) {
/* 1120 */             return (ObjectBidirectionalIterator<Char2CharMap.Entry>)new Char2CharAVLTreeMap.EntryIterator(from.getCharKey());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1126 */             if (o == null || !(o instanceof Map.Entry)) return false; 
/* 1127 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1128 */             if (e.getKey() == null) return false; 
/* 1129 */             if (!(e.getKey() instanceof Character)) return false; 
/* 1130 */             if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 1131 */             Char2CharAVLTreeMap.Entry f = Char2CharAVLTreeMap.this.findKey(((Character)e.getKey()).charValue());
/* 1132 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1138 */             if (!(o instanceof Map.Entry)) return false; 
/* 1139 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1140 */             if (e.getKey() == null) return false; 
/* 1141 */             if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/* 1142 */             if (!(e.getValue() instanceof Character)) return false; 
/* 1143 */             Char2CharAVLTreeMap.Entry f = Char2CharAVLTreeMap.this.findKey(((Character)e.getKey()).charValue());
/* 1144 */             if (f == null || f.getCharValue() != ((Character)e.getValue()).charValue()) return false; 
/* 1145 */             Char2CharAVLTreeMap.this.remove(f.key);
/* 1146 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1151 */             return Char2CharAVLTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1156 */             Char2CharAVLTreeMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public Char2CharMap.Entry first() {
/* 1161 */             return Char2CharAVLTreeMap.this.firstEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public Char2CharMap.Entry last() {
/* 1166 */             return Char2CharAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Char2CharMap.Entry> subSet(Char2CharMap.Entry from, Char2CharMap.Entry to) {
/* 1171 */             return Char2CharAVLTreeMap.this.subMap(from.getCharKey(), to.getCharKey()).char2CharEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Char2CharMap.Entry> headSet(Char2CharMap.Entry to) {
/* 1176 */             return Char2CharAVLTreeMap.this.headMap(to.getCharKey()).char2CharEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Char2CharMap.Entry> tailSet(Char2CharMap.Entry from) {
/* 1181 */             return Char2CharAVLTreeMap.this.tailMap(from.getCharKey()).char2CharEntrySet();
/*      */           }
/*      */         }; 
/* 1184 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends TreeIterator
/*      */     implements CharListIterator
/*      */   {
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator(char k) {
/* 1200 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1205 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public char previousChar() {
/* 1210 */       return (previousEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractChar2CharSortedMap.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public CharBidirectionalIterator iterator() {
/* 1218 */       return new Char2CharAVLTreeMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public CharBidirectionalIterator iterator(char from) {
/* 1223 */       return new Char2CharAVLTreeMap.KeyIterator(from);
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
/*      */   public CharSortedSet keySet() {
/* 1238 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1239 */     return this.keys;
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
/* 1253 */       return (nextEntry()).value;
/*      */     }
/*      */ 
/*      */     
/*      */     public char previousChar() {
/* 1258 */       return (previousEntry()).value;
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
/* 1273 */     if (this.values == null) this.values = new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1276 */             return new Char2CharAVLTreeMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(char k) {
/* 1281 */             return Char2CharAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1286 */             return Char2CharAVLTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1291 */             Char2CharAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1294 */     return this.values;
/*      */   }
/*      */ 
/*      */   
/*      */   public CharComparator comparator() {
/* 1299 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public Char2CharSortedMap headMap(char to) {
/* 1304 */     return new Submap(false, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public Char2CharSortedMap tailMap(char from) {
/* 1309 */     return new Submap(from, false, false, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Char2CharSortedMap subMap(char from, char to) {
/* 1314 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractChar2CharSortedMap
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */ 
/*      */ 
/*      */     
/*      */     char from;
/*      */ 
/*      */ 
/*      */     
/*      */     char to;
/*      */ 
/*      */ 
/*      */     
/*      */     boolean bottom;
/*      */ 
/*      */     
/*      */     boolean top;
/*      */ 
/*      */     
/*      */     protected transient ObjectSortedSet<Char2CharMap.Entry> entries;
/*      */ 
/*      */     
/*      */     protected transient CharSortedSet keys;
/*      */ 
/*      */     
/*      */     protected transient CharCollection values;
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(char from, boolean bottom, char to, boolean top) {
/* 1353 */       if (!bottom && !top && Char2CharAVLTreeMap.this.compare(from, to) > 0) throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1354 */       this.from = from;
/* 1355 */       this.bottom = bottom;
/* 1356 */       this.to = to;
/* 1357 */       this.top = top;
/* 1358 */       this.defRetValue = Char2CharAVLTreeMap.this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1363 */       SubmapIterator i = new SubmapIterator();
/* 1364 */       while (i.hasNext()) {
/* 1365 */         i.nextEntry();
/* 1366 */         i.remove();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean in(char k) {
/* 1377 */       return ((this.bottom || Char2CharAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Char2CharAVLTreeMap.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Char2CharMap.Entry> char2CharEntrySet() {
/* 1382 */       if (this.entries == null) this.entries = (ObjectSortedSet<Char2CharMap.Entry>)new AbstractObjectSortedSet<Char2CharMap.Entry>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Char2CharMap.Entry> iterator() {
/* 1385 */               return (ObjectBidirectionalIterator<Char2CharMap.Entry>)new Char2CharAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Char2CharMap.Entry> iterator(Char2CharMap.Entry from) {
/* 1390 */               return (ObjectBidirectionalIterator<Char2CharMap.Entry>)new Char2CharAVLTreeMap.Submap.SubmapEntryIterator(from.getCharKey());
/*      */             }
/*      */ 
/*      */             
/*      */             public Comparator<? super Char2CharMap.Entry> comparator() {
/* 1395 */               return Char2CharAVLTreeMap.this.char2CharEntrySet().comparator();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1401 */               if (!(o instanceof Map.Entry)) return false; 
/* 1402 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1403 */               if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/* 1404 */               if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 1405 */               Char2CharAVLTreeMap.Entry f = Char2CharAVLTreeMap.this.findKey(((Character)e.getKey()).charValue());
/* 1406 */               return (f != null && Char2CharAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1412 */               if (!(o instanceof Map.Entry)) return false; 
/* 1413 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1414 */               if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/* 1415 */               if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 1416 */               Char2CharAVLTreeMap.Entry f = Char2CharAVLTreeMap.this.findKey(((Character)e.getKey()).charValue());
/* 1417 */               if (f != null && Char2CharAVLTreeMap.Submap.this.in(f.key)) Char2CharAVLTreeMap.Submap.this.remove(f.key); 
/* 1418 */               return (f != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1423 */               int c = 0;
/* 1424 */               for (ObjectBidirectionalIterator<Char2CharMap.Entry> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); ) { c++; objectBidirectionalIterator.next(); }
/* 1425 */                return c;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1430 */               return !(new Char2CharAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1435 */               Char2CharAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */ 
/*      */             
/*      */             public Char2CharMap.Entry first() {
/* 1440 */               return Char2CharAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public Char2CharMap.Entry last() {
/* 1445 */               return Char2CharAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Char2CharMap.Entry> subSet(Char2CharMap.Entry from, Char2CharMap.Entry to) {
/* 1450 */               return Char2CharAVLTreeMap.Submap.this.subMap(from.getCharKey(), to.getCharKey()).char2CharEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Char2CharMap.Entry> headSet(Char2CharMap.Entry to) {
/* 1455 */               return Char2CharAVLTreeMap.Submap.this.headMap(to.getCharKey()).char2CharEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Char2CharMap.Entry> tailSet(Char2CharMap.Entry from) {
/* 1460 */               return Char2CharAVLTreeMap.Submap.this.tailMap(from.getCharKey()).char2CharEntrySet();
/*      */             }
/*      */           }; 
/* 1463 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractChar2CharSortedMap.KeySet { private KeySet() {}
/*      */       
/*      */       public CharBidirectionalIterator iterator() {
/* 1469 */         return new Char2CharAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public CharBidirectionalIterator iterator(char from) {
/* 1474 */         return new Char2CharAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public CharSortedSet keySet() {
/* 1480 */       if (this.keys == null) this.keys = new KeySet(); 
/* 1481 */       return this.keys;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharCollection values() {
/* 1486 */       if (this.values == null) this.values = new AbstractCharCollection()
/*      */           {
/*      */             public CharIterator iterator() {
/* 1489 */               return new Char2CharAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(char k) {
/* 1494 */               return Char2CharAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1499 */               return Char2CharAVLTreeMap.Submap.this.size();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1504 */               Char2CharAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1507 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(char k) {
/* 1514 */       return (in(k) && Char2CharAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(char v) {
/* 1519 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1521 */       while (i.hasNext()) {
/* 1522 */         char ev = (i.nextEntry()).value;
/* 1523 */         if (ev == v) return true; 
/*      */       } 
/* 1525 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char get(char k) {
/* 1532 */       char kk = k; Char2CharAVLTreeMap.Entry e;
/* 1533 */       return (in(kk) && (e = Char2CharAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public char put(char k, char v) {
/* 1538 */       Char2CharAVLTreeMap.this.modified = false;
/* 1539 */       if (!in(k)) throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1540 */       char oldValue = Char2CharAVLTreeMap.this.put(k, v);
/* 1541 */       return Char2CharAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public char remove(char k) {
/* 1547 */       Char2CharAVLTreeMap.this.modified = false;
/* 1548 */       if (!in(k)) return this.defRetValue; 
/* 1549 */       char oldValue = Char2CharAVLTreeMap.this.remove(k);
/* 1550 */       return Char2CharAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1555 */       SubmapIterator i = new SubmapIterator();
/* 1556 */       int n = 0;
/* 1557 */       while (i.hasNext()) {
/* 1558 */         n++;
/* 1559 */         i.nextEntry();
/*      */       } 
/* 1561 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1566 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public CharComparator comparator() {
/* 1571 */       return Char2CharAVLTreeMap.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Char2CharSortedMap headMap(char to) {
/* 1576 */       if (this.top) return new Submap(this.from, this.bottom, to, false); 
/* 1577 */       return (Char2CharAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Char2CharSortedMap tailMap(char from) {
/* 1582 */       if (this.bottom) return new Submap(from, false, this.to, this.top); 
/* 1583 */       return (Char2CharAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Char2CharSortedMap subMap(char from, char to) {
/* 1588 */       if (this.top && this.bottom) return new Submap(from, false, to, false); 
/* 1589 */       if (!this.top) to = (Char2CharAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1590 */       if (!this.bottom) from = (Char2CharAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1591 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1592 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Char2CharAVLTreeMap.Entry firstEntry() {
/*      */       Char2CharAVLTreeMap.Entry e;
/* 1601 */       if (Char2CharAVLTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1605 */       if (this.bottom) { e = Char2CharAVLTreeMap.this.firstEntry; }
/*      */       else
/* 1607 */       { e = Char2CharAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1609 */         if (Char2CharAVLTreeMap.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1613 */       if (e == null || (!this.top && Char2CharAVLTreeMap.this.compare(e.key, this.to) >= 0)) return null; 
/* 1614 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Char2CharAVLTreeMap.Entry lastEntry() {
/*      */       Char2CharAVLTreeMap.Entry e;
/* 1623 */       if (Char2CharAVLTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1627 */       if (this.top) { e = Char2CharAVLTreeMap.this.lastEntry; }
/*      */       else
/* 1629 */       { e = Char2CharAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1631 */         if (Char2CharAVLTreeMap.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1635 */       if (e == null || (!this.bottom && Char2CharAVLTreeMap.this.compare(e.key, this.from) < 0)) return null; 
/* 1636 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public char firstCharKey() {
/* 1641 */       Char2CharAVLTreeMap.Entry e = firstEntry();
/* 1642 */       if (e == null) throw new NoSuchElementException(); 
/* 1643 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public char lastCharKey() {
/* 1648 */       Char2CharAVLTreeMap.Entry e = lastEntry();
/* 1649 */       if (e == null) throw new NoSuchElementException(); 
/* 1650 */       return e.key;
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
/*      */       extends Char2CharAVLTreeMap.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1664 */         this.next = Char2CharAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubmapIterator(char k) {
/* 1668 */         this();
/* 1669 */         if (this.next != null) {
/* 1670 */           if (!Char2CharAVLTreeMap.Submap.this.bottom && Char2CharAVLTreeMap.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1671 */           else if (!Char2CharAVLTreeMap.Submap.this.top && Char2CharAVLTreeMap.this.compare(k, (this.prev = Char2CharAVLTreeMap.Submap.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1673 */           { this.next = Char2CharAVLTreeMap.this.locateKey(k);
/* 1674 */             if (Char2CharAVLTreeMap.this.compare(this.next.key, k) <= 0)
/* 1675 */             { this.prev = this.next;
/* 1676 */               this.next = this.next.next(); }
/* 1677 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1684 */         this.prev = this.prev.prev();
/* 1685 */         if (!Char2CharAVLTreeMap.Submap.this.bottom && this.prev != null && Char2CharAVLTreeMap.this.compare(this.prev.key, Char2CharAVLTreeMap.Submap.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1690 */         this.next = this.next.next();
/* 1691 */         if (!Char2CharAVLTreeMap.Submap.this.top && this.next != null && Char2CharAVLTreeMap.this.compare(this.next.key, Char2CharAVLTreeMap.Submap.this.to) >= 0) this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Char2CharMap.Entry> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(char k) {
/* 1700 */         super(k);
/*      */       }
/*      */ 
/*      */       
/*      */       public Char2CharMap.Entry next() {
/* 1705 */         return nextEntry();
/*      */       }
/*      */ 
/*      */       
/*      */       public Char2CharMap.Entry previous() {
/* 1710 */         return previousEntry();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final class SubmapKeyIterator
/*      */       extends SubmapIterator
/*      */       implements CharListIterator
/*      */     {
/*      */       public SubmapKeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public SubmapKeyIterator(char from) {
/* 1729 */         super(from);
/*      */       }
/*      */ 
/*      */       
/*      */       public char nextChar() {
/* 1734 */         return (nextEntry()).key;
/*      */       }
/*      */ 
/*      */       
/*      */       public char previousChar() {
/* 1739 */         return (previousEntry()).key;
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
/* 1755 */         return (nextEntry()).value;
/*      */       }
/*      */ 
/*      */       
/*      */       public char previousChar() {
/* 1760 */         return (previousEntry()).value;
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
/*      */   public Char2CharAVLTreeMap clone() {
/*      */     Char2CharAVLTreeMap c;
/*      */     try {
/* 1779 */       c = (Char2CharAVLTreeMap)super.clone();
/* 1780 */     } catch (CloneNotSupportedException cantHappen) {
/* 1781 */       throw new InternalError();
/*      */     } 
/* 1783 */     c.keys = null;
/* 1784 */     c.values = null;
/* 1785 */     c.entries = null;
/* 1786 */     c.allocatePaths();
/* 1787 */     if (this.count != 0) {
/*      */       
/* 1789 */       Entry rp = new Entry(), rq = new Entry();
/* 1790 */       Entry p = rp;
/* 1791 */       rp.left(this.tree);
/* 1792 */       Entry q = rq;
/* 1793 */       rq.pred((Entry)null);
/*      */       while (true) {
/* 1795 */         if (!p.pred()) {
/* 1796 */           Entry e = p.left.clone();
/* 1797 */           e.pred(q.left);
/* 1798 */           e.succ(q);
/* 1799 */           q.left(e);
/* 1800 */           p = p.left;
/* 1801 */           q = q.left;
/*      */         } else {
/* 1803 */           while (p.succ()) {
/* 1804 */             p = p.right;
/* 1805 */             if (p == null) {
/* 1806 */               q.right = null;
/* 1807 */               c.tree = rq.left;
/* 1808 */               c.firstEntry = c.tree;
/* 1809 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1810 */               c.lastEntry = c.tree;
/* 1811 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1812 */               return c;
/*      */             } 
/* 1814 */             q = q.right;
/*      */           } 
/* 1816 */           p = p.right;
/* 1817 */           q = q.right;
/*      */         } 
/* 1819 */         if (!p.succ()) {
/* 1820 */           Entry e = p.right.clone();
/* 1821 */           e.succ(q.right);
/* 1822 */           e.pred(q);
/* 1823 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1827 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1831 */     int n = this.count;
/* 1832 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1834 */     s.defaultWriteObject();
/* 1835 */     while (n-- != 0) {
/* 1836 */       Entry e = i.nextEntry();
/* 1837 */       s.writeChar(e.key);
/* 1838 */       s.writeChar(e.value);
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
/* 1852 */     if (n == 1) {
/* 1853 */       Entry entry = new Entry(s.readChar(), s.readChar());
/* 1854 */       entry.pred(pred);
/* 1855 */       entry.succ(succ);
/* 1856 */       return entry;
/*      */     } 
/* 1858 */     if (n == 2) {
/*      */ 
/*      */       
/* 1861 */       Entry entry = new Entry(s.readChar(), s.readChar());
/* 1862 */       entry.right(new Entry(s.readChar(), s.readChar()));
/* 1863 */       entry.right.pred(entry);
/* 1864 */       entry.balance(1);
/* 1865 */       entry.pred(pred);
/* 1866 */       entry.right.succ(succ);
/* 1867 */       return entry;
/*      */     } 
/*      */     
/* 1870 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1871 */     Entry top = new Entry();
/* 1872 */     top.left(readTree(s, leftN, pred, top));
/* 1873 */     top.key = s.readChar();
/* 1874 */     top.value = s.readChar();
/* 1875 */     top.right(readTree(s, rightN, top, succ));
/* 1876 */     if (n == (n & -n)) top.balance(1); 
/* 1877 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1881 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1884 */     setActualComparator();
/* 1885 */     allocatePaths();
/* 1886 */     if (this.count != 0) {
/* 1887 */       this.tree = readTree(s, this.count, (Entry)null, (Entry)null);
/*      */       
/* 1889 */       Entry e = this.tree;
/* 1890 */       for (; e.left() != null; e = e.left());
/* 1891 */       this.firstEntry = e;
/* 1892 */       e = this.tree;
/* 1893 */       for (; e.right() != null; e = e.right());
/* 1894 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2CharAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */