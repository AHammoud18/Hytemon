/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
/*      */ public class Float2ObjectAVLTreeMap<V>
/*      */   extends AbstractFloat2ObjectSortedMap<V>
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   protected transient Entry<V> tree;
/*      */   protected int count;
/*      */   protected transient Entry<V> firstEntry;
/*      */   protected transient Entry<V> lastEntry;
/*      */   protected transient ObjectSortedSet<Float2ObjectMap.Entry<V>> entries;
/*      */   protected transient FloatSortedSet keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   protected transient boolean modified;
/*      */   protected Comparator<? super Float> storedComparator;
/*      */   protected transient FloatComparator actualComparator;
/*      */   private static final long serialVersionUID = -7046029254386353129L;
/*      */   private transient boolean[] dirPath;
/*      */   
/*      */   public Float2ObjectAVLTreeMap() {
/*   69 */     allocatePaths();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   76 */     this.tree = null;
/*   77 */     this.count = 0;
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
/*   89 */     this.actualComparator = FloatComparators.asFloatComparator(this.storedComparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ObjectAVLTreeMap(Comparator<? super Float> c) {
/*   98 */     this();
/*   99 */     this.storedComparator = c;
/*  100 */     setActualComparator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ObjectAVLTreeMap(Map<? extends Float, ? extends V> m) {
/*  109 */     this();
/*  110 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ObjectAVLTreeMap(SortedMap<Float, V> m) {
/*  119 */     this(m.comparator());
/*  120 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ObjectAVLTreeMap(Float2ObjectMap<? extends V> m) {
/*  129 */     this();
/*  130 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ObjectAVLTreeMap(Float2ObjectSortedMap<V> m) {
/*  139 */     this(m.comparator());
/*  140 */     putAll(m);
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
/*      */   public Float2ObjectAVLTreeMap(float[] k, V[] v, Comparator<? super Float> c) {
/*  152 */     this(c);
/*  153 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  154 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ObjectAVLTreeMap(float[] k, V[] v) {
/*  165 */     this(k, v, (Comparator<? super Float>)null);
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
/*  191 */     return (this.actualComparator == null) ? Float.compare(k1, k2) : this.actualComparator.compare(k1, k2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<V> findKey(float k) {
/*  201 */     Entry<V> e = this.tree;
/*      */     int cmp;
/*  203 */     for (; e != null && (cmp = compare(k, e.key)) != 0; e = (cmp < 0) ? e.left() : e.right());
/*  204 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Entry<V> locateKey(float k) {
/*  215 */     Entry<V> e = this.tree, last = this.tree;
/*  216 */     int cmp = 0;
/*  217 */     while (e != null && (cmp = compare(k, e.key)) != 0) {
/*  218 */       last = e;
/*  219 */       e = (cmp < 0) ? e.left() : e.right();
/*      */     } 
/*  221 */     return (cmp == 0) ? e : last;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void allocatePaths() {
/*  231 */     this.dirPath = new boolean[48];
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(float k, V v) {
/*  236 */     Entry<V> e = add(k);
/*  237 */     V oldValue = e.value;
/*  238 */     e.value = v;
/*  239 */     return oldValue;
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
/*      */   private Entry<V> add(float k) {
/*  253 */     this.modified = false;
/*  254 */     Entry<V> e = null;
/*  255 */     if (this.tree == null) {
/*  256 */       this.count++;
/*  257 */       e = this.tree = this.lastEntry = this.firstEntry = new Entry<>(k, this.defRetValue);
/*  258 */       this.modified = true;
/*      */     } else {
/*  260 */       Entry<V> p = this.tree, q = null, y = this.tree, z = null, w = null;
/*  261 */       int i = 0; while (true) {
/*      */         int cmp;
/*  263 */         if ((cmp = compare(k, p.key)) == 0) {
/*  264 */           return p;
/*      */         }
/*  266 */         if (p.balance() != 0) {
/*  267 */           i = 0;
/*  268 */           z = q;
/*  269 */           y = p;
/*      */         } 
/*  271 */         this.dirPath[i++] = (cmp > 0); if ((cmp > 0)) {
/*  272 */           if (p.succ()) {
/*  273 */             this.count++;
/*  274 */             e = new Entry<>(k, this.defRetValue);
/*  275 */             this.modified = true;
/*  276 */             if (p.right == null) this.lastEntry = e; 
/*  277 */             e.left = p;
/*  278 */             e.right = p.right;
/*  279 */             p.right(e);
/*      */             break;
/*      */           } 
/*  282 */           q = p;
/*  283 */           p = p.right; continue;
/*      */         } 
/*  285 */         if (p.pred()) {
/*  286 */           this.count++;
/*  287 */           e = new Entry<>(k, this.defRetValue);
/*  288 */           this.modified = true;
/*  289 */           if (p.left == null) this.firstEntry = e; 
/*  290 */           e.right = p;
/*  291 */           e.left = p.left;
/*  292 */           p.left(e);
/*      */           break;
/*      */         } 
/*  295 */         q = p;
/*  296 */         p = p.left;
/*      */       } 
/*      */       
/*  299 */       p = y;
/*  300 */       i = 0;
/*  301 */       while (p != e) {
/*  302 */         if (this.dirPath[i]) { p.incBalance(); }
/*  303 */         else { p.decBalance(); }
/*  304 */          p = this.dirPath[i++] ? p.right : p.left;
/*      */       } 
/*  306 */       if (y.balance() == -2)
/*  307 */       { Entry<V> x = y.left;
/*  308 */         if (x.balance() == -1) {
/*  309 */           w = x;
/*  310 */           if (x.succ())
/*  311 */           { x.succ(false);
/*  312 */             y.pred(x); }
/*  313 */           else { y.left = x.right; }
/*  314 */            x.right = y;
/*  315 */           x.balance(0);
/*  316 */           y.balance(0);
/*      */         } else {
/*  318 */           assert x.balance() == 1;
/*  319 */           w = x.right;
/*  320 */           x.right = w.left;
/*  321 */           w.left = x;
/*  322 */           y.left = w.right;
/*  323 */           w.right = y;
/*  324 */           if (w.balance() == -1) {
/*  325 */             x.balance(0);
/*  326 */             y.balance(1);
/*  327 */           } else if (w.balance() == 0) {
/*  328 */             x.balance(0);
/*  329 */             y.balance(0);
/*      */           } else {
/*  331 */             x.balance(-1);
/*  332 */             y.balance(0);
/*      */           } 
/*  334 */           w.balance(0);
/*  335 */           if (w.pred()) {
/*  336 */             x.succ(w);
/*  337 */             w.pred(false);
/*      */           } 
/*  339 */           if (w.succ()) {
/*  340 */             y.pred(w);
/*  341 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  344 */       else if (y.balance() == 2)
/*  345 */       { Entry<V> x = y.right;
/*  346 */         if (x.balance() == 1) {
/*  347 */           w = x;
/*  348 */           if (x.pred())
/*  349 */           { x.pred(false);
/*  350 */             y.succ(x); }
/*  351 */           else { y.right = x.left; }
/*  352 */            x.left = y;
/*  353 */           x.balance(0);
/*  354 */           y.balance(0);
/*      */         } else {
/*  356 */           assert x.balance() == -1;
/*  357 */           w = x.left;
/*  358 */           x.left = w.right;
/*  359 */           w.right = x;
/*  360 */           y.right = w.left;
/*  361 */           w.left = y;
/*  362 */           if (w.balance() == 1) {
/*  363 */             x.balance(0);
/*  364 */             y.balance(-1);
/*  365 */           } else if (w.balance() == 0) {
/*  366 */             x.balance(0);
/*  367 */             y.balance(0);
/*      */           } else {
/*  369 */             x.balance(1);
/*  370 */             y.balance(0);
/*      */           } 
/*  372 */           w.balance(0);
/*  373 */           if (w.pred()) {
/*  374 */             y.succ(w);
/*  375 */             w.pred(false);
/*      */           } 
/*  377 */           if (w.succ()) {
/*  378 */             x.pred(w);
/*  379 */             w.succ(false);
/*      */           } 
/*      */         }  }
/*  382 */       else { return e; }
/*  383 */        if (z == null) { this.tree = w; }
/*      */       
/*  385 */       else if (z.left == y) { z.left = w; }
/*  386 */       else { z.right = w; }
/*      */     
/*      */     } 
/*  389 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Entry<V> parent(Entry<V> e) {
/*  399 */     if (e == this.tree) return null;
/*      */     
/*  401 */     Entry<V> y = e, x = y;
/*      */     while (true) {
/*  403 */       if (y.succ()) {
/*  404 */         Entry<V> p = y.right;
/*  405 */         if (p == null || p.left != e) {
/*  406 */           for (; !x.pred(); x = x.left);
/*  407 */           p = x.left;
/*      */         } 
/*  409 */         return p;
/*  410 */       }  if (x.pred()) {
/*  411 */         Entry<V> p = x.left;
/*  412 */         if (p == null || p.right != e) {
/*  413 */           for (; !y.succ(); y = y.right);
/*  414 */           p = y.right;
/*      */         } 
/*  416 */         return p;
/*      */       } 
/*  418 */       x = x.left;
/*  419 */       y = y.right;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(float k) {
/*  427 */     this.modified = false;
/*  428 */     if (this.tree == null) return this.defRetValue;
/*      */     
/*  430 */     Entry<V> p = this.tree, q = null;
/*  431 */     boolean dir = false;
/*  432 */     float kk = k;
/*      */     int cmp;
/*  434 */     while ((cmp = compare(kk, p.key)) != 0) {
/*  435 */       if (dir = (cmp > 0)) {
/*  436 */         q = p;
/*  437 */         if ((p = p.right()) == null) return this.defRetValue;  continue;
/*      */       } 
/*  439 */       q = p;
/*  440 */       if ((p = p.left()) == null) return this.defRetValue;
/*      */     
/*      */     } 
/*  443 */     if (p.left == null) this.firstEntry = p.next(); 
/*  444 */     if (p.right == null) this.lastEntry = p.prev(); 
/*  445 */     if (p.succ())
/*  446 */     { if (p.pred())
/*  447 */       { if (q != null)
/*  448 */         { if (dir) { q.succ(p.right); }
/*  449 */           else { q.pred(p.left); }  }
/*  450 */         else { this.tree = dir ? p.right : p.left; }
/*      */          }
/*  452 */       else { (p.prev()).right = p.right;
/*  453 */         if (q != null)
/*  454 */         { if (dir) { q.right = p.left; }
/*  455 */           else { q.left = p.left; }  }
/*  456 */         else { this.tree = p.left; }
/*      */          }
/*      */        }
/*  459 */     else { Entry<V> r = p.right;
/*  460 */       if (r.pred()) {
/*  461 */         r.left = p.left;
/*  462 */         r.pred(p.pred());
/*  463 */         if (!r.pred()) (r.prev()).right = r; 
/*  464 */         if (q != null)
/*  465 */         { if (dir) { q.right = r; }
/*  466 */           else { q.left = r; }  }
/*  467 */         else { this.tree = r; }
/*  468 */          r.balance(p.balance());
/*  469 */         q = r;
/*  470 */         dir = true;
/*      */       } else {
/*      */         Entry<V> s;
/*      */         while (true) {
/*  474 */           s = r.left;
/*  475 */           if (s.pred())
/*  476 */             break;  r = s;
/*      */         } 
/*  478 */         if (s.succ()) { r.pred(s); }
/*  479 */         else { r.left = s.right; }
/*  480 */          s.left = p.left;
/*  481 */         if (!p.pred()) {
/*  482 */           (p.prev()).right = s;
/*  483 */           s.pred(false);
/*      */         } 
/*  485 */         s.right = p.right;
/*  486 */         s.succ(false);
/*  487 */         if (q != null)
/*  488 */         { if (dir) { q.right = s; }
/*  489 */           else { q.left = s; }  }
/*  490 */         else { this.tree = s; }
/*  491 */          s.balance(p.balance());
/*  492 */         q = r;
/*  493 */         dir = false;
/*      */       }  }
/*      */ 
/*      */     
/*  497 */     while (q != null) {
/*  498 */       Entry<V> y = q;
/*  499 */       q = parent(y);
/*  500 */       if (!dir) {
/*  501 */         dir = (q != null && q.left != y);
/*  502 */         y.incBalance();
/*  503 */         if (y.balance() == 1)
/*  504 */           break;  if (y.balance() == 2) {
/*  505 */           Entry<V> x = y.right;
/*  506 */           assert x != null;
/*  507 */           if (x.balance() == -1) {
/*      */             
/*  509 */             assert x.balance() == -1;
/*  510 */             Entry<V> w = x.left;
/*  511 */             x.left = w.right;
/*  512 */             w.right = x;
/*  513 */             y.right = w.left;
/*  514 */             w.left = y;
/*  515 */             if (w.balance() == 1) {
/*  516 */               x.balance(0);
/*  517 */               y.balance(-1);
/*  518 */             } else if (w.balance() == 0) {
/*  519 */               x.balance(0);
/*  520 */               y.balance(0);
/*      */             } else {
/*  522 */               assert w.balance() == -1;
/*  523 */               x.balance(1);
/*  524 */               y.balance(0);
/*      */             } 
/*  526 */             w.balance(0);
/*  527 */             if (w.pred()) {
/*  528 */               y.succ(w);
/*  529 */               w.pred(false);
/*      */             } 
/*  531 */             if (w.succ()) {
/*  532 */               x.pred(w);
/*  533 */               w.succ(false);
/*      */             } 
/*  535 */             if (q != null) {
/*  536 */               if (dir) { q.right = w; continue; }
/*  537 */                q.left = w; continue;
/*  538 */             }  this.tree = w; continue;
/*      */           } 
/*  540 */           if (q != null)
/*  541 */           { if (dir) { q.right = x; }
/*  542 */             else { q.left = x; }  }
/*  543 */           else { this.tree = x; }
/*  544 */            if (x.balance() == 0) {
/*  545 */             y.right = x.left;
/*  546 */             x.left = y;
/*  547 */             x.balance(-1);
/*  548 */             y.balance(1);
/*      */             break;
/*      */           } 
/*  551 */           assert x.balance() == 1;
/*  552 */           if (x.pred())
/*  553 */           { y.succ(true);
/*  554 */             x.pred(false); }
/*  555 */           else { y.right = x.left; }
/*  556 */            x.left = y;
/*  557 */           y.balance(0);
/*  558 */           x.balance(0);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  562 */       dir = (q != null && q.left != y);
/*  563 */       y.decBalance();
/*  564 */       if (y.balance() == -1)
/*  565 */         break;  if (y.balance() == -2) {
/*  566 */         Entry<V> x = y.left;
/*  567 */         assert x != null;
/*  568 */         if (x.balance() == 1) {
/*      */           
/*  570 */           assert x.balance() == 1;
/*  571 */           Entry<V> w = x.right;
/*  572 */           x.right = w.left;
/*  573 */           w.left = x;
/*  574 */           y.left = w.right;
/*  575 */           w.right = y;
/*  576 */           if (w.balance() == -1) {
/*  577 */             x.balance(0);
/*  578 */             y.balance(1);
/*  579 */           } else if (w.balance() == 0) {
/*  580 */             x.balance(0);
/*  581 */             y.balance(0);
/*      */           } else {
/*  583 */             assert w.balance() == 1;
/*  584 */             x.balance(-1);
/*  585 */             y.balance(0);
/*      */           } 
/*  587 */           w.balance(0);
/*  588 */           if (w.pred()) {
/*  589 */             x.succ(w);
/*  590 */             w.pred(false);
/*      */           } 
/*  592 */           if (w.succ()) {
/*  593 */             y.pred(w);
/*  594 */             w.succ(false);
/*      */           } 
/*  596 */           if (q != null) {
/*  597 */             if (dir) { q.right = w; continue; }
/*  598 */              q.left = w; continue;
/*  599 */           }  this.tree = w; continue;
/*      */         } 
/*  601 */         if (q != null)
/*  602 */         { if (dir) { q.right = x; }
/*  603 */           else { q.left = x; }  }
/*  604 */         else { this.tree = x; }
/*  605 */          if (x.balance() == 0) {
/*  606 */           y.left = x.right;
/*  607 */           x.right = y;
/*  608 */           x.balance(1);
/*  609 */           y.balance(-1);
/*      */           break;
/*      */         } 
/*  612 */         assert x.balance() == -1;
/*  613 */         if (x.succ())
/*  614 */         { y.pred(true);
/*  615 */           x.succ(false); }
/*  616 */         else { y.left = x.right; }
/*  617 */          x.right = y;
/*  618 */         y.balance(0);
/*  619 */         x.balance(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  624 */     this.modified = true;
/*  625 */     this.count--;
/*  626 */     return p.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  631 */     ValueIterator i = new ValueIterator();
/*      */     
/*  633 */     int j = this.count;
/*  634 */     while (j-- != 0) {
/*  635 */       V ev = i.next();
/*  636 */       if (Objects.equals(ev, v)) return true; 
/*      */     } 
/*  638 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  643 */     this.count = 0;
/*  644 */     this.tree = null;
/*  645 */     this.entries = null;
/*  646 */     this.values = null;
/*  647 */     this.keys = null;
/*  648 */     this.firstEntry = this.lastEntry = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Entry<V>
/*      */     extends AbstractFloat2ObjectMap.BasicEntry<V>
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
/*  677 */       super(0.0F, (V)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry(float k, V v) {
/*  687 */       super(k, v);
/*  688 */       this.info = -1073741824;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> left() {
/*  697 */       return ((this.info & 0x40000000) != 0) ? null : this.left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> right() {
/*  706 */       return ((this.info & Integer.MIN_VALUE) != 0) ? null : this.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean pred() {
/*  715 */       return ((this.info & 0x40000000) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean succ() {
/*  724 */       return ((this.info & Integer.MIN_VALUE) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(boolean pred) {
/*  733 */       if (pred) { this.info |= 0x40000000; }
/*  734 */       else { this.info &= 0xBFFFFFFF; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(boolean succ) {
/*  743 */       if (succ) { this.info |= Integer.MIN_VALUE; }
/*  744 */       else { this.info &= Integer.MAX_VALUE; }
/*      */     
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void pred(Entry<V> pred) {
/*  753 */       this.info |= 0x40000000;
/*  754 */       this.left = pred;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void succ(Entry<V> succ) {
/*  763 */       this.info |= Integer.MIN_VALUE;
/*  764 */       this.right = succ;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void left(Entry<V> left) {
/*  773 */       this.info &= 0xBFFFFFFF;
/*  774 */       this.left = left;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void right(Entry<V> right) {
/*  783 */       this.info &= Integer.MAX_VALUE;
/*  784 */       this.right = right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int balance() {
/*  793 */       return (byte)this.info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void balance(int level) {
/*  802 */       this.info &= 0xFFFFFF00;
/*  803 */       this.info |= level & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     void incBalance() {
/*  808 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info + 1 & 0xFF;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void decBalance() {
/*  813 */       this.info = this.info & 0xFFFFFF00 | (byte)this.info - 1 & 0xFF;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> next() {
/*  822 */       Entry<V> next = this.right;
/*  823 */       if ((this.info & Integer.MIN_VALUE) == 0) for (; (next.info & 0x40000000) == 0; next = next.left); 
/*  824 */       return next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Entry<V> prev() {
/*  833 */       Entry<V> prev = this.left;
/*  834 */       if ((this.info & 0x40000000) == 0) for (; (prev.info & Integer.MIN_VALUE) == 0; prev = prev.right); 
/*  835 */       return prev;
/*      */     }
/*      */ 
/*      */     
/*      */     public V setValue(V value) {
/*  840 */       V oldValue = this.value;
/*  841 */       this.value = value;
/*  842 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Entry<V> clone() {
/*      */       Entry<V> c;
/*      */       try {
/*  850 */         c = (Entry<V>)super.clone();
/*  851 */       } catch (CloneNotSupportedException cantHappen) {
/*  852 */         throw new InternalError();
/*      */       } 
/*  854 */       c.key = this.key;
/*  855 */       c.value = this.value;
/*  856 */       c.info = this.info;
/*  857 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  863 */       if (!(o instanceof Map.Entry)) return false; 
/*  864 */       Map.Entry<Float, V> e = (Map.Entry<Float, V>)o;
/*  865 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Objects.equals(this.value, e.getValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  870 */       return HashCommon.float2int(this.key) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  875 */       return this.key + "=>" + this.value;
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
/*      */   public boolean containsKey(float k) {
/*  913 */     return (findKey(k) != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  918 */     return this.count;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  923 */     return (this.count == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(float k) {
/*  928 */     Entry<V> e = findKey(k);
/*  929 */     return (e == null) ? this.defRetValue : e.value;
/*      */   }
/*      */ 
/*      */   
/*      */   public float firstFloatKey() {
/*  934 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  935 */     return this.firstEntry.key;
/*      */   }
/*      */ 
/*      */   
/*      */   public float lastFloatKey() {
/*  940 */     if (this.tree == null) throw new NoSuchElementException(); 
/*  941 */     return this.lastEntry.key;
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
/*      */     Float2ObjectAVLTreeMap.Entry<V> prev;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2ObjectAVLTreeMap.Entry<V> next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Float2ObjectAVLTreeMap.Entry<V> curr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  970 */     int index = 0;
/*      */     
/*      */     TreeIterator() {
/*  973 */       this.next = Float2ObjectAVLTreeMap.this.firstEntry;
/*      */     }
/*      */     
/*      */     TreeIterator(float k) {
/*  977 */       if ((this.next = Float2ObjectAVLTreeMap.this.locateKey(k)) != null)
/*  978 */         if (Float2ObjectAVLTreeMap.this.compare(this.next.key, k) <= 0)
/*  979 */         { this.prev = this.next;
/*  980 */           this.next = this.next.next(); }
/*  981 */         else { this.prev = this.next.prev(); }
/*      */          
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  986 */       return (this.next != null);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  990 */       return (this.prev != null);
/*      */     }
/*      */     
/*      */     void updateNext() {
/*  994 */       this.next = this.next.next();
/*      */     }
/*      */     
/*      */     Float2ObjectAVLTreeMap.Entry<V> nextEntry() {
/*  998 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  999 */       this.curr = this.prev = this.next;
/* 1000 */       this.index++;
/* 1001 */       updateNext();
/* 1002 */       return this.curr;
/*      */     }
/*      */     
/*      */     void updatePrevious() {
/* 1006 */       this.prev = this.prev.prev();
/*      */     }
/*      */     
/*      */     Float2ObjectAVLTreeMap.Entry<V> previousEntry() {
/* 1010 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1011 */       this.curr = this.next = this.prev;
/* 1012 */       this.index--;
/* 1013 */       updatePrevious();
/* 1014 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1018 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1022 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1026 */       if (this.curr == null) throw new IllegalStateException();
/*      */ 
/*      */       
/* 1029 */       if (this.curr == this.prev) this.index--; 
/* 1030 */       this.next = this.prev = this.curr;
/* 1031 */       updatePrevious();
/* 1032 */       updateNext();
/* 1033 */       Float2ObjectAVLTreeMap.this.remove(this.curr.key);
/* 1034 */       this.curr = null;
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1038 */       int i = n;
/* 1039 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1040 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1044 */       int i = n;
/* 1045 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1046 */       return n - i - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class EntryIterator
/*      */     extends TreeIterator
/*      */     implements ObjectListIterator<Float2ObjectMap.Entry<V>>
/*      */   {
/*      */     EntryIterator() {}
/*      */ 
/*      */ 
/*      */     
/*      */     EntryIterator(float k) {
/* 1061 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2ObjectMap.Entry<V> next() {
/* 1066 */       return nextEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2ObjectMap.Entry<V> previous() {
/* 1071 */       return previousEntry();
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(Float2ObjectMap.Entry<V> ok) {
/* 1076 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(Float2ObjectMap.Entry<V> ok) {
/* 1081 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
/* 1088 */     if (this.entries == null) this.entries = (ObjectSortedSet<Float2ObjectMap.Entry<V>>)new AbstractObjectSortedSet<Float2ObjectMap.Entry<V>>()
/*      */         {
/*      */           final Comparator<? super Float2ObjectMap.Entry<V>> comparator;
/*      */           
/*      */           public Comparator<? super Float2ObjectMap.Entry<V>> comparator() {
/* 1093 */             return this.comparator;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator() {
/* 1098 */             return (ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>>)new Float2ObjectAVLTreeMap.EntryIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator(Float2ObjectMap.Entry<V> from) {
/* 1103 */             return (ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>>)new Float2ObjectAVLTreeMap.EntryIterator(from.getFloatKey());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean contains(Object o) {
/* 1109 */             if (o == null || !(o instanceof Map.Entry)) return false; 
/* 1110 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1111 */             if (e.getKey() == null) return false; 
/* 1112 */             if (!(e.getKey() instanceof Float)) return false; 
/* 1113 */             Float2ObjectAVLTreeMap.Entry<V> f = Float2ObjectAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1114 */             return e.equals(f);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean remove(Object o) {
/* 1120 */             if (!(o instanceof Map.Entry)) return false; 
/* 1121 */             Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1122 */             if (e.getKey() == null) return false; 
/* 1123 */             if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1124 */             Float2ObjectAVLTreeMap.Entry<V> f = Float2ObjectAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1125 */             if (f == null || !Objects.equals(f.getValue(), e.getValue())) return false; 
/* 1126 */             Float2ObjectAVLTreeMap.this.remove(f.key);
/* 1127 */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1132 */             return Float2ObjectAVLTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1137 */             Float2ObjectAVLTreeMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public Float2ObjectMap.Entry<V> first() {
/* 1142 */             return Float2ObjectAVLTreeMap.this.firstEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public Float2ObjectMap.Entry<V> last() {
/* 1147 */             return Float2ObjectAVLTreeMap.this.lastEntry;
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Float2ObjectMap.Entry<V>> subSet(Float2ObjectMap.Entry<V> from, Float2ObjectMap.Entry<V> to) {
/* 1152 */             return Float2ObjectAVLTreeMap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2ObjectEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Float2ObjectMap.Entry<V>> headSet(Float2ObjectMap.Entry<V> to) {
/* 1157 */             return Float2ObjectAVLTreeMap.this.headMap(to.getFloatKey()).float2ObjectEntrySet();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSortedSet<Float2ObjectMap.Entry<V>> tailSet(Float2ObjectMap.Entry<V> from) {
/* 1162 */             return Float2ObjectAVLTreeMap.this.tailMap(from.getFloatKey()).float2ObjectEntrySet();
/*      */           }
/*      */         }; 
/* 1165 */     return this.entries;
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
/* 1181 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1186 */       return (nextEntry()).key;
/*      */     }
/*      */ 
/*      */     
/*      */     public float previousFloat() {
/* 1191 */       return (previousEntry()).key;
/*      */     }
/*      */   }
/*      */   
/*      */   private class KeySet extends AbstractFloat2ObjectSortedMap<V>.KeySet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatBidirectionalIterator iterator() {
/* 1199 */       return new Float2ObjectAVLTreeMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBidirectionalIterator iterator(float from) {
/* 1204 */       return new Float2ObjectAVLTreeMap.KeyIterator(from);
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
/* 1219 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1220 */     return this.keys;
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
/* 1234 */       return (nextEntry()).value;
/*      */     }
/*      */ 
/*      */     
/*      */     public V previous() {
/* 1239 */       return (previousEntry()).value;
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
/*      */   public ObjectCollection<V> values() {
/* 1254 */     if (this.values == null) this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1257 */             return (ObjectIterator<V>)new Float2ObjectAVLTreeMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object k) {
/* 1262 */             return Float2ObjectAVLTreeMap.this.containsValue(k);
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1267 */             return Float2ObjectAVLTreeMap.this.count;
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1272 */             Float2ObjectAVLTreeMap.this.clear();
/*      */           }
/*      */         }; 
/* 1275 */     return this.values;
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatComparator comparator() {
/* 1280 */     return this.actualComparator;
/*      */   }
/*      */ 
/*      */   
/*      */   public Float2ObjectSortedMap<V> headMap(float to) {
/* 1285 */     return new Submap(0.0F, true, to, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public Float2ObjectSortedMap<V> tailMap(float from) {
/* 1290 */     return new Submap(from, false, 0.0F, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public Float2ObjectSortedMap<V> subMap(float from, float to) {
/* 1295 */     return new Submap(from, false, to, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class Submap
/*      */     extends AbstractFloat2ObjectSortedMap<V>
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
/*      */     protected transient ObjectSortedSet<Float2ObjectMap.Entry<V>> entries;
/*      */ 
/*      */     
/*      */     protected transient FloatSortedSet keys;
/*      */ 
/*      */     
/*      */     protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */     
/*      */     public Submap(float from, boolean bottom, float to, boolean top) {
/* 1334 */       if (!bottom && !top && Float2ObjectAVLTreeMap.this.compare(from, to) > 0) throw new IllegalArgumentException("Start key (" + from + ") is larger than end key (" + to + ")"); 
/* 1335 */       this.from = from;
/* 1336 */       this.bottom = bottom;
/* 1337 */       this.to = to;
/* 1338 */       this.top = top;
/* 1339 */       this.defRetValue = Float2ObjectAVLTreeMap.this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1344 */       SubmapIterator i = new SubmapIterator();
/* 1345 */       while (i.hasNext()) {
/* 1346 */         i.nextEntry();
/* 1347 */         i.remove();
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
/* 1358 */       return ((this.bottom || Float2ObjectAVLTreeMap.this.compare(k, this.from) >= 0) && (this.top || Float2ObjectAVLTreeMap.this.compare(k, this.to) < 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Float2ObjectMap.Entry<V>> float2ObjectEntrySet() {
/* 1363 */       if (this.entries == null) this.entries = (ObjectSortedSet<Float2ObjectMap.Entry<V>>)new AbstractObjectSortedSet<Float2ObjectMap.Entry<V>>()
/*      */           {
/*      */             public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator() {
/* 1366 */               return (ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>>)new Float2ObjectAVLTreeMap.Submap.SubmapEntryIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> iterator(Float2ObjectMap.Entry<V> from) {
/* 1371 */               return (ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>>)new Float2ObjectAVLTreeMap.Submap.SubmapEntryIterator(from.getFloatKey());
/*      */             }
/*      */ 
/*      */             
/*      */             public Comparator<? super Float2ObjectMap.Entry<V>> comparator() {
/* 1376 */               return Float2ObjectAVLTreeMap.this.float2ObjectEntrySet().comparator();
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean contains(Object o) {
/* 1382 */               if (!(o instanceof Map.Entry)) return false; 
/* 1383 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1384 */               if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1385 */               Float2ObjectAVLTreeMap.Entry<V> f = Float2ObjectAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1386 */               return (f != null && Float2ObjectAVLTreeMap.Submap.this.in(f.key) && e.equals(f));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public boolean remove(Object o) {
/* 1392 */               if (!(o instanceof Map.Entry)) return false; 
/* 1393 */               Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1394 */               if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1395 */               Float2ObjectAVLTreeMap.Entry<V> f = Float2ObjectAVLTreeMap.this.findKey(((Float)e.getKey()).floatValue());
/* 1396 */               if (f != null && Float2ObjectAVLTreeMap.Submap.this.in(f.key)) Float2ObjectAVLTreeMap.Submap.this.remove(f.key); 
/* 1397 */               return (f != null);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1402 */               int c = 0;
/* 1403 */               for (ObjectBidirectionalIterator<Float2ObjectMap.Entry<V>> objectBidirectionalIterator = iterator(); objectBidirectionalIterator.hasNext(); ) { c++; objectBidirectionalIterator.next(); }
/* 1404 */                return c;
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean isEmpty() {
/* 1409 */               return !(new Float2ObjectAVLTreeMap.Submap.SubmapIterator()).hasNext();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1414 */               Float2ObjectAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */ 
/*      */             
/*      */             public Float2ObjectMap.Entry<V> first() {
/* 1419 */               return Float2ObjectAVLTreeMap.Submap.this.firstEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public Float2ObjectMap.Entry<V> last() {
/* 1424 */               return Float2ObjectAVLTreeMap.Submap.this.lastEntry();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Float2ObjectMap.Entry<V>> subSet(Float2ObjectMap.Entry<V> from, Float2ObjectMap.Entry<V> to) {
/* 1429 */               return Float2ObjectAVLTreeMap.Submap.this.subMap(from.getFloatKey(), to.getFloatKey()).float2ObjectEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Float2ObjectMap.Entry<V>> headSet(Float2ObjectMap.Entry<V> to) {
/* 1434 */               return Float2ObjectAVLTreeMap.Submap.this.headMap(to.getFloatKey()).float2ObjectEntrySet();
/*      */             }
/*      */ 
/*      */             
/*      */             public ObjectSortedSet<Float2ObjectMap.Entry<V>> tailSet(Float2ObjectMap.Entry<V> from) {
/* 1439 */               return Float2ObjectAVLTreeMap.Submap.this.tailMap(from.getFloatKey()).float2ObjectEntrySet();
/*      */             }
/*      */           }; 
/* 1442 */       return this.entries;
/*      */     }
/*      */     
/*      */     private class KeySet extends AbstractFloat2ObjectSortedMap<V>.KeySet { private KeySet() {}
/*      */       
/*      */       public FloatBidirectionalIterator iterator() {
/* 1448 */         return new Float2ObjectAVLTreeMap.Submap.SubmapKeyIterator();
/*      */       }
/*      */ 
/*      */       
/*      */       public FloatBidirectionalIterator iterator(float from) {
/* 1453 */         return new Float2ObjectAVLTreeMap.Submap.SubmapKeyIterator(from);
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public FloatSortedSet keySet() {
/* 1459 */       if (this.keys == null) this.keys = new KeySet(); 
/* 1460 */       return this.keys;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectCollection<V> values() {
/* 1465 */       if (this.values == null) this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */           {
/*      */             public ObjectIterator<V> iterator() {
/* 1468 */               return (ObjectIterator<V>)new Float2ObjectAVLTreeMap.Submap.SubmapValueIterator();
/*      */             }
/*      */ 
/*      */             
/*      */             public boolean contains(Object k) {
/* 1473 */               return Float2ObjectAVLTreeMap.Submap.this.containsValue(k);
/*      */             }
/*      */ 
/*      */             
/*      */             public int size() {
/* 1478 */               return Float2ObjectAVLTreeMap.Submap.this.size();
/*      */             }
/*      */ 
/*      */             
/*      */             public void clear() {
/* 1483 */               Float2ObjectAVLTreeMap.Submap.this.clear();
/*      */             }
/*      */           }; 
/* 1486 */       return this.values;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean containsKey(float k) {
/* 1493 */       return (in(k) && Float2ObjectAVLTreeMap.this.containsKey(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean containsValue(Object v) {
/* 1498 */       SubmapIterator i = new SubmapIterator();
/*      */       
/* 1500 */       while (i.hasNext()) {
/* 1501 */         Object ev = (i.nextEntry()).value;
/* 1502 */         if (Objects.equals(ev, v)) return true; 
/*      */       } 
/* 1504 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(float k) {
/* 1511 */       float kk = k; Float2ObjectAVLTreeMap.Entry<V> e;
/* 1512 */       return (in(kk) && (e = Float2ObjectAVLTreeMap.this.findKey(kk)) != null) ? e.value : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public V put(float k, V v) {
/* 1517 */       Float2ObjectAVLTreeMap.this.modified = false;
/* 1518 */       if (!in(k)) throw new IllegalArgumentException("Key (" + k + ") out of range [" + (this.bottom ? "-" : String.valueOf(this.from)) + ", " + (this.top ? "-" : String.valueOf(this.to)) + ")"); 
/* 1519 */       V oldValue = Float2ObjectAVLTreeMap.this.put(k, v);
/* 1520 */       return Float2ObjectAVLTreeMap.this.modified ? this.defRetValue : oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public V remove(float k) {
/* 1526 */       Float2ObjectAVLTreeMap.this.modified = false;
/* 1527 */       if (!in(k)) return this.defRetValue; 
/* 1528 */       V oldValue = Float2ObjectAVLTreeMap.this.remove(k);
/* 1529 */       return Float2ObjectAVLTreeMap.this.modified ? oldValue : this.defRetValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1534 */       SubmapIterator i = new SubmapIterator();
/* 1535 */       int n = 0;
/* 1536 */       while (i.hasNext()) {
/* 1537 */         n++;
/* 1538 */         i.nextEntry();
/*      */       } 
/* 1540 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isEmpty() {
/* 1545 */       return !(new SubmapIterator()).hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatComparator comparator() {
/* 1550 */       return Float2ObjectAVLTreeMap.this.actualComparator;
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2ObjectSortedMap<V> headMap(float to) {
/* 1555 */       if (this.top) return new Submap(this.from, this.bottom, to, false); 
/* 1556 */       return (Float2ObjectAVLTreeMap.this.compare(to, this.to) < 0) ? new Submap(this.from, this.bottom, to, false) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2ObjectSortedMap<V> tailMap(float from) {
/* 1561 */       if (this.bottom) return new Submap(from, false, this.to, this.top); 
/* 1562 */       return (Float2ObjectAVLTreeMap.this.compare(from, this.from) > 0) ? new Submap(from, false, this.to, this.top) : this;
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2ObjectSortedMap<V> subMap(float from, float to) {
/* 1567 */       if (this.top && this.bottom) return new Submap(from, false, to, false); 
/* 1568 */       if (!this.top) to = (Float2ObjectAVLTreeMap.this.compare(to, this.to) < 0) ? to : this.to; 
/* 1569 */       if (!this.bottom) from = (Float2ObjectAVLTreeMap.this.compare(from, this.from) > 0) ? from : this.from; 
/* 1570 */       if (!this.top && !this.bottom && from == this.from && to == this.to) return this; 
/* 1571 */       return new Submap(from, false, to, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2ObjectAVLTreeMap.Entry<V> firstEntry() {
/*      */       Float2ObjectAVLTreeMap.Entry<V> e;
/* 1580 */       if (Float2ObjectAVLTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1584 */       if (this.bottom) { e = Float2ObjectAVLTreeMap.this.firstEntry; }
/*      */       else
/* 1586 */       { e = Float2ObjectAVLTreeMap.this.locateKey(this.from);
/*      */         
/* 1588 */         if (Float2ObjectAVLTreeMap.this.compare(e.key, this.from) < 0) e = e.next();
/*      */          }
/*      */ 
/*      */       
/* 1592 */       if (e == null || (!this.top && Float2ObjectAVLTreeMap.this.compare(e.key, this.to) >= 0)) return null; 
/* 1593 */       return e;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Float2ObjectAVLTreeMap.Entry<V> lastEntry() {
/*      */       Float2ObjectAVLTreeMap.Entry<V> e;
/* 1602 */       if (Float2ObjectAVLTreeMap.this.tree == null) return null;
/*      */ 
/*      */ 
/*      */       
/* 1606 */       if (this.top) { e = Float2ObjectAVLTreeMap.this.lastEntry; }
/*      */       else
/* 1608 */       { e = Float2ObjectAVLTreeMap.this.locateKey(this.to);
/*      */         
/* 1610 */         if (Float2ObjectAVLTreeMap.this.compare(e.key, this.to) >= 0) e = e.prev();
/*      */          }
/*      */ 
/*      */       
/* 1614 */       if (e == null || (!this.bottom && Float2ObjectAVLTreeMap.this.compare(e.key, this.from) < 0)) return null; 
/* 1615 */       return e;
/*      */     }
/*      */ 
/*      */     
/*      */     public float firstFloatKey() {
/* 1620 */       Float2ObjectAVLTreeMap.Entry<V> e = firstEntry();
/* 1621 */       if (e == null) throw new NoSuchElementException(); 
/* 1622 */       return e.key;
/*      */     }
/*      */ 
/*      */     
/*      */     public float lastFloatKey() {
/* 1627 */       Float2ObjectAVLTreeMap.Entry<V> e = lastEntry();
/* 1628 */       if (e == null) throw new NoSuchElementException(); 
/* 1629 */       return e.key;
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
/*      */       extends Float2ObjectAVLTreeMap<V>.TreeIterator
/*      */     {
/*      */       SubmapIterator() {
/* 1643 */         this.next = Float2ObjectAVLTreeMap.Submap.this.firstEntry();
/*      */       }
/*      */       
/*      */       SubmapIterator(float k) {
/* 1647 */         this();
/* 1648 */         if (this.next != null) {
/* 1649 */           if (!Float2ObjectAVLTreeMap.Submap.this.bottom && Float2ObjectAVLTreeMap.this.compare(k, this.next.key) < 0) { this.prev = null; }
/* 1650 */           else if (!Float2ObjectAVLTreeMap.Submap.this.top && Float2ObjectAVLTreeMap.this.compare(k, (this.prev = Float2ObjectAVLTreeMap.Submap.this.lastEntry()).key) >= 0) { this.next = null; }
/*      */           else
/* 1652 */           { this.next = Float2ObjectAVLTreeMap.this.locateKey(k);
/* 1653 */             if (Float2ObjectAVLTreeMap.this.compare(this.next.key, k) <= 0)
/* 1654 */             { this.prev = this.next;
/* 1655 */               this.next = this.next.next(); }
/* 1656 */             else { this.prev = this.next.prev(); }
/*      */              }
/*      */         
/*      */         }
/*      */       }
/*      */       
/*      */       void updatePrevious() {
/* 1663 */         this.prev = this.prev.prev();
/* 1664 */         if (!Float2ObjectAVLTreeMap.Submap.this.bottom && this.prev != null && Float2ObjectAVLTreeMap.this.compare(this.prev.key, Float2ObjectAVLTreeMap.Submap.this.from) < 0) this.prev = null;
/*      */       
/*      */       }
/*      */       
/*      */       void updateNext() {
/* 1669 */         this.next = this.next.next();
/* 1670 */         if (!Float2ObjectAVLTreeMap.Submap.this.top && this.next != null && Float2ObjectAVLTreeMap.this.compare(this.next.key, Float2ObjectAVLTreeMap.Submap.this.to) >= 0) this.next = null; 
/*      */       }
/*      */     }
/*      */     
/*      */     private class SubmapEntryIterator
/*      */       extends SubmapIterator implements ObjectListIterator<Float2ObjectMap.Entry<V>> {
/*      */       SubmapEntryIterator() {}
/*      */       
/*      */       SubmapEntryIterator(float k) {
/* 1679 */         super(k);
/*      */       }
/*      */ 
/*      */       
/*      */       public Float2ObjectMap.Entry<V> next() {
/* 1684 */         return nextEntry();
/*      */       }
/*      */ 
/*      */       
/*      */       public Float2ObjectMap.Entry<V> previous() {
/* 1689 */         return previousEntry();
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
/* 1708 */         super(from);
/*      */       }
/*      */ 
/*      */       
/*      */       public float nextFloat() {
/* 1713 */         return (nextEntry()).key;
/*      */       }
/*      */ 
/*      */       
/*      */       public float previousFloat() {
/* 1718 */         return (previousEntry()).key;
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
/* 1734 */         return (nextEntry()).value;
/*      */       }
/*      */ 
/*      */       
/*      */       public V previous() {
/* 1739 */         return (previousEntry()).value;
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
/*      */   public Float2ObjectAVLTreeMap<V> clone() {
/*      */     Float2ObjectAVLTreeMap<V> c;
/*      */     try {
/* 1758 */       c = (Float2ObjectAVLTreeMap<V>)super.clone();
/* 1759 */     } catch (CloneNotSupportedException cantHappen) {
/* 1760 */       throw new InternalError();
/*      */     } 
/* 1762 */     c.keys = null;
/* 1763 */     c.values = null;
/* 1764 */     c.entries = null;
/* 1765 */     c.allocatePaths();
/* 1766 */     if (this.count != 0) {
/*      */       
/* 1768 */       Entry<V> rp = new Entry<>(), rq = new Entry<>();
/* 1769 */       Entry<V> p = rp;
/* 1770 */       rp.left(this.tree);
/* 1771 */       Entry<V> q = rq;
/* 1772 */       rq.pred((Entry<V>)null);
/*      */       while (true) {
/* 1774 */         if (!p.pred()) {
/* 1775 */           Entry<V> e = p.left.clone();
/* 1776 */           e.pred(q.left);
/* 1777 */           e.succ(q);
/* 1778 */           q.left(e);
/* 1779 */           p = p.left;
/* 1780 */           q = q.left;
/*      */         } else {
/* 1782 */           while (p.succ()) {
/* 1783 */             p = p.right;
/* 1784 */             if (p == null) {
/* 1785 */               q.right = null;
/* 1786 */               c.tree = rq.left;
/* 1787 */               c.firstEntry = c.tree;
/* 1788 */               for (; c.firstEntry.left != null; c.firstEntry = c.firstEntry.left);
/* 1789 */               c.lastEntry = c.tree;
/* 1790 */               for (; c.lastEntry.right != null; c.lastEntry = c.lastEntry.right);
/* 1791 */               return c;
/*      */             } 
/* 1793 */             q = q.right;
/*      */           } 
/* 1795 */           p = p.right;
/* 1796 */           q = q.right;
/*      */         } 
/* 1798 */         if (!p.succ()) {
/* 1799 */           Entry<V> e = p.right.clone();
/* 1800 */           e.succ(q.right);
/* 1801 */           e.pred(q);
/* 1802 */           q.right(e);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1806 */     return c;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1810 */     int n = this.count;
/* 1811 */     EntryIterator i = new EntryIterator();
/*      */     
/* 1813 */     s.defaultWriteObject();
/* 1814 */     while (n-- != 0) {
/* 1815 */       Entry<V> e = i.nextEntry();
/* 1816 */       s.writeFloat(e.key);
/* 1817 */       s.writeObject(e.value);
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
/* 1831 */     if (n == 1) {
/* 1832 */       Entry<V> entry = new Entry<>(s.readFloat(), (V)s.readObject());
/* 1833 */       entry.pred(pred);
/* 1834 */       entry.succ(succ);
/* 1835 */       return entry;
/*      */     } 
/* 1837 */     if (n == 2) {
/*      */ 
/*      */       
/* 1840 */       Entry<V> entry = new Entry<>(s.readFloat(), (V)s.readObject());
/* 1841 */       entry.right(new Entry<>(s.readFloat(), (V)s.readObject()));
/* 1842 */       entry.right.pred(entry);
/* 1843 */       entry.balance(1);
/* 1844 */       entry.pred(pred);
/* 1845 */       entry.right.succ(succ);
/* 1846 */       return entry;
/*      */     } 
/*      */     
/* 1849 */     int rightN = n / 2, leftN = n - rightN - 1;
/* 1850 */     Entry<V> top = new Entry<>();
/* 1851 */     top.left(readTree(s, leftN, pred, top));
/* 1852 */     top.key = s.readFloat();
/* 1853 */     top.value = (V)s.readObject();
/* 1854 */     top.right(readTree(s, rightN, top, succ));
/* 1855 */     if (n == (n & -n)) top.balance(1); 
/* 1856 */     return top;
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1860 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1863 */     setActualComparator();
/* 1864 */     allocatePaths();
/* 1865 */     if (this.count != 0) {
/* 1866 */       this.tree = readTree(s, this.count, (Entry<V>)null, (Entry<V>)null);
/*      */       
/* 1868 */       Entry<V> e = this.tree;
/* 1869 */       for (; e.left() != null; e = e.left());
/* 1870 */       this.firstEntry = e;
/* 1871 */       e = this.tree;
/* 1872 */       for (; e.right() != null; e = e.right());
/* 1873 */       this.lastEntry = e;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2ObjectAVLTreeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */