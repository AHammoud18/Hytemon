/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.AbstractSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public final class ConcurrentSet<E>
/*    */   extends AbstractSet<E>
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6761513279741915432L;
/* 38 */   private final ConcurrentMap<E, Boolean> map = new ConcurrentHashMap<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public int size() {
/* 43 */     return this.map.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(Object o) {
/* 48 */     return this.map.containsKey(o);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean add(E o) {
/* 53 */     return (this.map.putIfAbsent(o, Boolean.TRUE) == null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean remove(Object o) {
/* 58 */     return (this.map.remove(o) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 63 */     this.map.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<E> iterator() {
/* 68 */     return this.map.keySet().iterator();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\ConcurrentSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */