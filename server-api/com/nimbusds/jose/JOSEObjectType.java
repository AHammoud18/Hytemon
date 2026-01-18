/*     */ package com.nimbusds.jose;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*     */ import com.nimbusds.jose.util.JSONStringUtils;
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
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
/*     */ public final class JOSEObjectType
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  57 */   public static final JOSEObjectType JOSE = new JOSEObjectType("JOSE");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final JOSEObjectType JOSE_JSON = new JOSEObjectType("JOSE+JSON");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final JOSEObjectType JWT = new JOSEObjectType("JWT");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JOSEObjectType(String type) {
/*  85 */     this.type = Objects.<String>requireNonNull(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/*  96 */     return this.type;
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
/*     */   public int hashCode() {
/* 108 */     return this.type.toLowerCase().hashCode();
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
/*     */   
/*     */   public boolean equals(Object object) {
/* 123 */     return (object instanceof JOSEObjectType && this.type
/* 124 */       .equalsIgnoreCase(((JOSEObjectType)object).type));
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
/*     */   public String toString() {
/* 138 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toJSONString() {
/* 148 */     return JSONStringUtils.toJSONString(this.type);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\JOSEObjectType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */