/*     */ package com.nimbusds.jose.proc;
/*     */ 
/*     */ import com.nimbusds.jose.JOSEObjectType;
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Immutable
/*     */ public class DefaultJOSEObjectTypeVerifier<C extends SecurityContext>
/*     */   implements JOSEObjectTypeVerifier<C>
/*     */ {
/*     */   private final Set<JOSEObjectType> allowedTypes;
/*  75 */   public static final DefaultJOSEObjectTypeVerifier JOSE = new DefaultJOSEObjectTypeVerifier(new JOSEObjectType[] { JOSEObjectType.JOSE, null });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static final DefaultJOSEObjectTypeVerifier JWT = new DefaultJOSEObjectTypeVerifier(new JOSEObjectType[] { JOSEObjectType.JWT, null });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultJOSEObjectTypeVerifier() {
/*  90 */     this.allowedTypes = Collections.singleton(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultJOSEObjectTypeVerifier(Set<JOSEObjectType> allowedTypes) {
/* 104 */     if (allowedTypes.isEmpty()) {
/* 105 */       throw new IllegalArgumentException("The allowed types must not be empty");
/*     */     }
/* 107 */     this.allowedTypes = allowedTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultJOSEObjectTypeVerifier(JOSEObjectType... allowedTypes) {
/* 121 */     if (allowedTypes.length == 0) {
/* 122 */       throw new IllegalArgumentException("The allowed types must not be empty");
/*     */     }
/* 124 */     this.allowedTypes = new HashSet<>(Arrays.asList(allowedTypes));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<JOSEObjectType> getAllowedTypes() {
/* 135 */     return this.allowedTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verify(JOSEObjectType type, C context) throws BadJOSEException {
/* 143 */     if (type == null && !this.allowedTypes.contains(null)) {
/* 144 */       throw new BadJOSEException("Required JOSE header typ (type) parameter is missing");
/*     */     }
/*     */     
/* 147 */     if (!this.allowedTypes.contains(type))
/* 148 */       throw new BadJOSEException("JOSE header typ (type) " + type + " not allowed"); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\proc\DefaultJOSEObjectTypeVerifier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */