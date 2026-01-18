/*    */ package com.nimbusds.jose.proc;
/*    */ 
/*    */ import com.nimbusds.jose.JWSAlgorithm;
/*    */ import com.nimbusds.jose.JWSHeader;
/*    */ import java.security.Key;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
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
/*    */ public class SingleKeyJWSKeySelector<C extends SecurityContext>
/*    */   implements JWSKeySelector<C>
/*    */ {
/*    */   private final List<Key> singletonKeyList;
/*    */   private final JWSAlgorithm expectedJWSAlg;
/*    */   
/*    */   public SingleKeyJWSKeySelector(JWSAlgorithm expectedJWSAlg, Key key) {
/* 37 */     this.singletonKeyList = Collections.singletonList(Objects.requireNonNull(key));
/* 38 */     this.expectedJWSAlg = Objects.<JWSAlgorithm>requireNonNull(expectedJWSAlg);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<? extends Key> selectJWSKeys(JWSHeader header, C context) {
/* 45 */     if (!this.expectedJWSAlg.equals(header.getAlgorithm())) {
/* 46 */       return Collections.emptyList();
/*    */     }
/*    */     
/* 49 */     return this.singletonKeyList;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\proc\SingleKeyJWSKeySelector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */