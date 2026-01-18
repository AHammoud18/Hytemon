/*      */ package com.nimbusds.jwt;
/*      */ 
/*      */ import com.nimbusds.jose.Payload;
/*      */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*      */ import com.nimbusds.jose.util.JSONArrayUtils;
/*      */ import com.nimbusds.jose.util.JSONObjectUtils;
/*      */ import com.nimbusds.jwt.util.DateUtils;
/*      */ import java.io.Serializable;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.text.ParseException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Immutable
/*      */ public final class JWTClaimsSet
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   private static final Set<String> REGISTERED_CLAIM_NAMES;
/*      */   
/*      */   static {
/*   98 */     Set<String> n = new HashSet<>();
/*      */     
/*  100 */     n.add("iss");
/*  101 */     n.add("sub");
/*  102 */     n.add("aud");
/*  103 */     n.add("exp");
/*  104 */     n.add("nbf");
/*  105 */     n.add("iat");
/*  106 */     n.add("jti");
/*      */     
/*  108 */     REGISTERED_CLAIM_NAMES = Collections.unmodifiableSet(n);
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
/*      */   public static class Builder
/*      */   {
/*  131 */     private final Map<String, Object> claims = new LinkedHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean serializeNullClaims = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder(JWTClaimsSet jwtClaimsSet) {
/*  158 */       this.claims.putAll(jwtClaimsSet.claims);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder serializeNullClaims(boolean enable) {
/*  175 */       this.serializeNullClaims = enable;
/*  176 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder issuer(String iss) {
/*  189 */       this.claims.put("iss", iss);
/*  190 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder subject(String sub) {
/*  203 */       this.claims.put("sub", sub);
/*  204 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder audience(List<String> aud) {
/*  218 */       this.claims.put("aud", aud);
/*  219 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder audience(String aud) {
/*  233 */       if (aud == null) {
/*  234 */         this.claims.put("aud", null);
/*      */       } else {
/*  236 */         this.claims.put("aud", Collections.singletonList(aud));
/*      */       } 
/*  238 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder expirationTime(Date exp) {
/*  252 */       this.claims.put("exp", exp);
/*  253 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder notBeforeTime(Date nbf) {
/*  267 */       this.claims.put("nbf", nbf);
/*  268 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder issueTime(Date iat) {
/*  282 */       this.claims.put("iat", iat);
/*  283 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder jwtID(String jti) {
/*  296 */       this.claims.put("jti", jti);
/*  297 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Builder claim(String name, Object value) {
/*  313 */       this.claims.put(name, value);
/*  314 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map<String, Object> getClaims() {
/*  331 */       return Collections.unmodifiableMap(this.claims);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JWTClaimsSet build() {
/*  342 */       return new JWTClaimsSet(this.claims, this.serializeNullClaims);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  350 */   private final Map<String, Object> claims = new LinkedHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean serializeNullClaims;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JWTClaimsSet(Map<String, Object> claims, boolean serializeNullClaims) {
/*  367 */     this.claims.putAll(claims);
/*  368 */     this.serializeNullClaims = serializeNullClaims;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Set<String> getRegisteredNames() {
/*  379 */     return REGISTERED_CLAIM_NAMES;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getIssuer() {
/*      */     try {
/*  391 */       return getStringClaim("iss");
/*  392 */     } catch (ParseException e) {
/*  393 */       return null;
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
/*      */   public String getSubject() {
/*      */     try {
/*  406 */       return getStringClaim("sub");
/*  407 */     } catch (ParseException e) {
/*  408 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getAudience() {
/*      */     List<String> aud;
/*  420 */     Object audValue = getClaim("aud");
/*      */     
/*  422 */     if (audValue instanceof String)
/*      */     {
/*  424 */       return Collections.singletonList((String)audValue);
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  429 */       aud = getStringListClaim("aud");
/*  430 */     } catch (ParseException e) {
/*  431 */       return Collections.emptyList();
/*      */     } 
/*  433 */     return (aud != null) ? aud : Collections.<String>emptyList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Date getExpirationTime() {
/*      */     try {
/*  445 */       return getDateClaim("exp");
/*  446 */     } catch (ParseException e) {
/*  447 */       return null;
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
/*      */   public Date getNotBeforeTime() {
/*      */     try {
/*  460 */       return getDateClaim("nbf");
/*  461 */     } catch (ParseException e) {
/*  462 */       return null;
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
/*      */   public Date getIssueTime() {
/*      */     try {
/*  475 */       return getDateClaim("iat");
/*  476 */     } catch (ParseException e) {
/*  477 */       return null;
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
/*      */   public String getJWTID() {
/*      */     try {
/*  490 */       return getStringClaim("jti");
/*  491 */     } catch (ParseException e) {
/*  492 */       return null;
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
/*      */   public Object getClaim(String name) {
/*  506 */     return this.claims.get(name);
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
/*      */   public String getStringClaim(String name) throws ParseException {
/*  524 */     Object value = getClaim(name);
/*      */     
/*  526 */     if (value == null || value instanceof String) {
/*  527 */       return (String)value;
/*      */     }
/*  529 */     throw new ParseException("The " + name + " claim is not a String", 0);
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
/*      */   public String getClaimAsString(String name) throws ParseException {
/*  549 */     Object value = getClaim(name);
/*      */ 
/*      */     
/*  552 */     if (value == null || value instanceof String)
/*  553 */       return (String)value;  Class<?> clazz;
/*  554 */     if ((clazz = value.getClass()).isPrimitive() || isWrapper(clazz)) {
/*  555 */       return String.valueOf(value);
/*      */     }
/*  557 */     throw new ParseException("The " + name + " claim is not and cannot be converted to a String", 0);
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
/*      */   private static boolean isWrapper(Class<?> clazz) {
/*  571 */     return (clazz == Integer.class || clazz == Double.class || clazz == Float.class || clazz == Long.class || clazz == Short.class || clazz == Byte.class || clazz == Character.class || clazz == Boolean.class);
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
/*      */   public List<Object> getListClaim(String name) throws ParseException {
/*  591 */     Object value = getClaim(name);
/*      */     
/*  593 */     if (value == null) {
/*  594 */       return null;
/*      */     }
/*      */     
/*      */     try {
/*  598 */       return (List<Object>)getClaim(name);
/*      */     }
/*  600 */     catch (ClassCastException e) {
/*  601 */       throw new ParseException("The " + name + " claim is not a list / JSON array", 0);
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
/*      */   public String[] getStringArrayClaim(String name) throws ParseException {
/*  620 */     List<?> list = getListClaim(name);
/*      */     
/*  622 */     if (list == null) {
/*  623 */       return null;
/*      */     }
/*      */     
/*  626 */     String[] stringArray = new String[list.size()];
/*      */     
/*  628 */     for (int i = 0; i < stringArray.length; i++) {
/*      */       
/*      */       try {
/*  631 */         stringArray[i] = (String)list.get(i);
/*  632 */       } catch (ClassCastException e) {
/*  633 */         throw new ParseException("The " + name + " claim is not a list / JSON array of strings", 0);
/*      */       } 
/*      */     } 
/*      */     
/*  637 */     return stringArray;
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
/*      */   public List<String> getStringListClaim(String name) throws ParseException {
/*  655 */     String[] stringArray = getStringArrayClaim(name);
/*      */     
/*  657 */     if (stringArray == null) {
/*  658 */       return null;
/*      */     }
/*      */     
/*  661 */     return Collections.unmodifiableList(Arrays.asList(stringArray));
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
/*      */   public URI getURIClaim(String name) throws ParseException {
/*  678 */     String uriString = getStringClaim(name);
/*      */     
/*  680 */     if (uriString == null) {
/*  681 */       return null;
/*      */     }
/*      */     
/*      */     try {
/*  685 */       return new URI(uriString);
/*  686 */     } catch (URISyntaxException e) {
/*  687 */       throw new ParseException("The \"" + name + "\" claim is not a URI: " + e.getMessage(), 0);
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
/*      */   public Boolean getBooleanClaim(String name) throws ParseException {
/*  706 */     Object value = getClaim(name);
/*      */     
/*  708 */     if (value == null || value instanceof Boolean) {
/*  709 */       return (Boolean)value;
/*      */     }
/*  711 */     throw new ParseException("The \"" + name + "\" claim is not a Boolean", 0);
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
/*      */   public Integer getIntegerClaim(String name) throws ParseException {
/*  731 */     Object value = getClaim(name);
/*      */     
/*  733 */     if (value == null)
/*  734 */       return null; 
/*  735 */     if (value instanceof Number) {
/*  736 */       return Integer.valueOf(((Number)value).intValue());
/*      */     }
/*  738 */     throw new ParseException("The \"" + name + "\" claim is not an Integer", 0);
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
/*      */   public Long getLongClaim(String name) throws ParseException {
/*  758 */     Object value = getClaim(name);
/*      */     
/*  760 */     if (value == null)
/*  761 */       return null; 
/*  762 */     if (value instanceof Number) {
/*  763 */       return Long.valueOf(((Number)value).longValue());
/*      */     }
/*  765 */     throw new ParseException("The \"" + name + "\" claim is not a Number", 0);
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
/*      */   public Date getDateClaim(String name) throws ParseException {
/*  785 */     Object value = getClaim(name);
/*      */     
/*  787 */     if (value == null)
/*  788 */       return null; 
/*  789 */     if (value instanceof Date)
/*  790 */       return (Date)value; 
/*  791 */     if (value instanceof Number) {
/*  792 */       return DateUtils.fromSecondsSinceEpoch(((Number)value).longValue());
/*      */     }
/*  794 */     throw new ParseException("The \"" + name + "\" claim is not a Date", 0);
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
/*      */   public Float getFloatClaim(String name) throws ParseException {
/*  815 */     Object value = getClaim(name);
/*      */     
/*  817 */     if (value == null)
/*  818 */       return null; 
/*  819 */     if (value instanceof Number) {
/*  820 */       return Float.valueOf(((Number)value).floatValue());
/*      */     }
/*  822 */     throw new ParseException("The \"" + name + "\" claim is not a Float", 0);
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
/*      */   public Double getDoubleClaim(String name) throws ParseException {
/*  843 */     Object value = getClaim(name);
/*      */     
/*  845 */     if (value == null)
/*  846 */       return null; 
/*  847 */     if (value instanceof Number) {
/*  848 */       return Double.valueOf(((Number)value).doubleValue());
/*      */     }
/*  850 */     throw new ParseException("The \"" + name + "\" claim is not a Double", 0);
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
/*      */   public Map<String, Object> getJSONObjectClaim(String name) throws ParseException {
/*  868 */     Object value = getClaim(name);
/*      */     
/*  870 */     if (value == null)
/*  871 */       return null; 
/*  872 */     if (value instanceof Map) {
/*  873 */       Map<String, Object> jsonObject = JSONObjectUtils.newJSONObject();
/*  874 */       Map<?, ?> map = (Map<?, ?>)value;
/*  875 */       for (Map.Entry<?, ?> entry : map.entrySet()) {
/*  876 */         if (entry.getKey() instanceof String) {
/*  877 */           jsonObject.put((String)entry.getKey(), entry.getValue());
/*      */         }
/*      */       } 
/*  880 */       return jsonObject;
/*      */     } 
/*  882 */     throw new ParseException("The \"" + name + "\" claim is not a JSON object or Map", 0);
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
/*      */   public Map<String, Object> getClaims() {
/*  898 */     return Collections.unmodifiableMap(this.claims);
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
/*      */   public Payload toPayload() {
/*  912 */     return new Payload(toJSONObject(this.serializeNullClaims));
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
/*      */   public Payload toPayload(boolean serializeNullClaims) {
/*  928 */     return new Payload(toJSONObject(serializeNullClaims));
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
/*      */   public Map<String, Object> toJSONObject() {
/*  942 */     return toJSONObject(this.serializeNullClaims);
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
/*      */   public Map<String, Object> toJSONObject(boolean serializeNullClaims) {
/*  958 */     Map<String, Object> o = JSONObjectUtils.newJSONObject();
/*      */     
/*  960 */     for (Map.Entry<String, Object> claim : this.claims.entrySet()) {
/*      */       
/*  962 */       if (claim.getValue() instanceof Date) {
/*      */ 
/*      */         
/*  965 */         Date dateValue = (Date)claim.getValue();
/*  966 */         o.put(claim.getKey(), Long.valueOf(DateUtils.toSecondsSinceEpoch(dateValue))); continue;
/*      */       } 
/*  968 */       if ("aud".equals(claim.getKey())) {
/*      */ 
/*      */         
/*  971 */         List<String> audList = getAudience();
/*      */         
/*  973 */         if (audList != null && !audList.isEmpty()) {
/*  974 */           if (audList.size() == 1) {
/*  975 */             o.put("aud", audList.get(0)); continue;
/*      */           } 
/*  977 */           List<Object> audArray = JSONArrayUtils.newJSONArray();
/*  978 */           audArray.addAll(audList);
/*  979 */           o.put("aud", audArray); continue;
/*      */         } 
/*  981 */         if (serializeNullClaims)
/*  982 */           o.put("aud", null); 
/*      */         continue;
/*      */       } 
/*  985 */       if (claim.getValue() != null) {
/*  986 */         o.put(claim.getKey(), claim.getValue()); continue;
/*  987 */       }  if (serializeNullClaims) {
/*  988 */         o.put(claim.getKey(), null);
/*      */       }
/*      */     } 
/*      */     
/*  992 */     return o;
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
/*      */   public String toString() {
/* 1007 */     return JSONObjectUtils.toJSONString(toJSONObject());
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
/*      */   public String toString(boolean serializeNullClaims) {
/* 1023 */     return JSONObjectUtils.toJSONString(toJSONObject(serializeNullClaims));
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
/*      */   public <T> T toType(JWTClaimsSetTransformer<T> transformer) {
/* 1038 */     return transformer.transform(this);
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
/*      */   public static JWTClaimsSet parse(Map<String, Object> json) throws ParseException {
/* 1056 */     Builder builder = new Builder();
/*      */ 
/*      */     
/* 1059 */     for (String name : json.keySet()) {
/*      */       Object subValue, audValue;
/* 1061 */       switch (name) {
/*      */         case "iss":
/* 1063 */           builder.issuer(JSONObjectUtils.getString(json, "iss"));
/*      */           continue;
/*      */         case "sub":
/* 1066 */           subValue = json.get("sub");
/* 1067 */           if (subValue instanceof String) {
/* 1068 */             builder.subject(JSONObjectUtils.getString(json, "sub")); continue;
/* 1069 */           }  if (subValue instanceof Number) {
/*      */ 
/*      */ 
/*      */             
/* 1073 */             builder.subject(String.valueOf(subValue)); continue;
/* 1074 */           }  if (subValue == null) {
/* 1075 */             builder.subject(null); continue;
/*      */           } 
/* 1077 */           throw new ParseException("Illegal sub claim", 0);
/*      */ 
/*      */         
/*      */         case "aud":
/* 1081 */           audValue = json.get("aud");
/* 1082 */           if (audValue instanceof String) {
/* 1083 */             List<String> singleAud = new ArrayList<>();
/* 1084 */             singleAud.add(JSONObjectUtils.getString(json, "aud"));
/* 1085 */             builder.audience(singleAud); continue;
/* 1086 */           }  if (audValue instanceof List) {
/* 1087 */             builder.audience(JSONObjectUtils.getStringList(json, "aud")); continue;
/* 1088 */           }  if (audValue == null) {
/* 1089 */             builder.audience((String)null); continue;
/*      */           } 
/* 1091 */           throw new ParseException("Illegal aud claim", 0);
/*      */ 
/*      */         
/*      */         case "exp":
/* 1095 */           builder.expirationTime(JSONObjectUtils.getEpochSecondAsDate(json, "exp"));
/*      */           continue;
/*      */         case "nbf":
/* 1098 */           builder.notBeforeTime(JSONObjectUtils.getEpochSecondAsDate(json, "nbf"));
/*      */           continue;
/*      */         case "iat":
/* 1101 */           builder.issueTime(JSONObjectUtils.getEpochSecondAsDate(json, "iat"));
/*      */           continue;
/*      */         case "jti":
/* 1104 */           builder.jwtID(JSONObjectUtils.getString(json, "jti"));
/*      */           continue;
/*      */       } 
/* 1107 */       builder.claim(name, json.get(name));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1112 */     return builder.build();
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
/*      */   public static JWTClaimsSet parse(String s) throws ParseException {
/* 1130 */     return parse(JSONObjectUtils.parse(s));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 1136 */     if (this == o) return true; 
/* 1137 */     if (!(o instanceof JWTClaimsSet)) return false; 
/* 1138 */     JWTClaimsSet that = (JWTClaimsSet)o;
/* 1139 */     return Objects.equals(this.claims, that.claims);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1145 */     return Objects.hash(new Object[] { this.claims });
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jwt\JWTClaimsSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */