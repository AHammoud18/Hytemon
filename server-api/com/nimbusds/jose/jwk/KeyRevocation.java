/*     */ package com.nimbusds.jose.jwk;
/*     */ 
/*     */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*     */ import com.nimbusds.jose.util.JSONObjectUtils;
/*     */ import com.nimbusds.jwt.util.DateUtils;
/*     */ import java.io.Serializable;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
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
/*     */ @Immutable
/*     */ public final class KeyRevocation
/*     */   implements Serializable
/*     */ {
/*     */   private final Date revokedAt;
/*     */   private final Reason reason;
/*     */   
/*     */   public static class Reason
/*     */   {
/*  50 */     public static final Reason UNSPECIFIED = new Reason("unspecified");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     public static final Reason COMPROMISED = new Reason("compromised");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     public static final Reason SUPERSEDED = new Reason("superseded");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Reason(String value) {
/*  77 */       this.value = Objects.<String>requireNonNull(value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getValue() {
/*  87 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/*  93 */       return getValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  98 */       if (this == o) return true; 
/*  99 */       if (!(o instanceof Reason)) return false; 
/* 100 */       Reason reason = (Reason)o;
/* 101 */       return Objects.equals(getValue(), reason.getValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 106 */       return Objects.hashCode(getValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Reason parse(String s) {
/* 118 */       if (UNSPECIFIED.getValue().equals(s))
/* 119 */         return UNSPECIFIED; 
/* 120 */       if (COMPROMISED.getValue().equals(s))
/* 121 */         return COMPROMISED; 
/* 122 */       if (SUPERSEDED.getValue().equals(s)) {
/* 123 */         return SUPERSEDED;
/*     */       }
/* 125 */       return new Reason(s);
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyRevocation(Date revokedAt, Reason reason) {
/* 150 */     this.revokedAt = Objects.<Date>requireNonNull(revokedAt);
/* 151 */     this.reason = reason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getRevocationTime() {
/* 161 */     return this.revokedAt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reason getReason() {
/* 171 */     return this.reason;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 177 */     if (this == o) return true; 
/* 178 */     if (!(o instanceof KeyRevocation)) return false; 
/* 179 */     KeyRevocation that = (KeyRevocation)o;
/* 180 */     return (Objects.equals(this.revokedAt, that.revokedAt) && Objects.equals(getReason(), that.getReason()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 186 */     return Objects.hash(new Object[] { this.revokedAt, getReason() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> toJSONObject() {
/* 196 */     Map<String, Object> o = JSONObjectUtils.newJSONObject();
/* 197 */     o.put("revoked_at", Long.valueOf(DateUtils.toSecondsSinceEpoch(getRevocationTime())));
/* 198 */     if (getReason() != null) {
/* 199 */       o.put("reason", getReason().getValue());
/*     */     }
/* 201 */     return o;
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
/*     */   public static KeyRevocation parse(Map<String, Object> jsonObject) throws ParseException {
/* 216 */     Date revokedAt = DateUtils.fromSecondsSinceEpoch(JSONObjectUtils.getLong(jsonObject, "revoked_at"));
/* 217 */     Reason reason = null;
/* 218 */     if (jsonObject.get("reason") != null) {
/* 219 */       reason = Reason.parse(JSONObjectUtils.getString(jsonObject, "reason"));
/*     */     }
/* 221 */     return new KeyRevocation(revokedAt, reason);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\KeyRevocation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */