/*      */ package com.nimbusds.jose.jwk;
/*      */ 
/*      */ import com.nimbusds.jose.Algorithm;
/*      */ import com.nimbusds.jose.JWEHeader;
/*      */ import com.nimbusds.jose.JWSAlgorithm;
/*      */ import com.nimbusds.jose.JWSHeader;
/*      */ import com.nimbusds.jose.shaded.jcip.Immutable;
/*      */ import com.nimbusds.jose.util.Base64;
/*      */ import com.nimbusds.jose.util.Base64URL;
/*      */ import com.nimbusds.jose.util.X509CertUtils;
/*      */ import java.security.cert.CertificateException;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.LinkedHashSet;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */ public class JWKMatcher
/*      */ {
/*      */   private final Set<KeyType> types;
/*      */   private final Set<KeyUse> uses;
/*      */   private final Set<KeyOperation> ops;
/*      */   private final Set<Algorithm> algs;
/*      */   private final Set<String> ids;
/*      */   private final boolean withUseOnly;
/*      */   private final boolean withIDOnly;
/*      */   private final boolean privateOnly;
/*      */   private final boolean publicOnly;
/*      */   private final boolean nonRevokedOnly;
/*      */   private final boolean revokedOnly;
/*      */   private final int minSizeBits;
/*      */   private final int maxSizeBits;
/*      */   private final Set<Integer> sizesBits;
/*      */   private final Set<Curve> curves;
/*      */   private final Set<Base64URL> x5tS256s;
/*      */   private final boolean withX5COnly;
/*      */   
/*      */   public static class Builder
/*      */   {
/*      */     private Set<KeyType> types;
/*      */     private Set<KeyUse> uses;
/*      */     private Set<KeyOperation> ops;
/*      */     private Set<Algorithm> algs;
/*      */     private Set<String> ids;
/*      */     private boolean withUseOnly = false;
/*      */     private boolean withIDOnly = false;
/*      */     private boolean privateOnly = false;
/*      */     private boolean publicOnly = false;
/*      */     private boolean nonRevokedOnly = false;
/*      */     private boolean revokedOnly = false;
/*  254 */     private int minSizeBits = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  261 */     private int maxSizeBits = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Set<Integer> sizesBits;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Set<Curve> curves;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Set<Base64URL> x5tS256s;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean withX5COnly = false;
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
/*      */     public Builder(JWKMatcher jwkMatcher) {
/*  304 */       this.types = jwkMatcher.getKeyTypes();
/*  305 */       this.uses = jwkMatcher.getKeyUses();
/*  306 */       this.ops = jwkMatcher.getKeyOperations();
/*  307 */       this.algs = jwkMatcher.getAlgorithms();
/*  308 */       this.ids = jwkMatcher.getKeyIDs();
/*  309 */       this.withUseOnly = jwkMatcher.isWithKeyUseOnly();
/*  310 */       this.withIDOnly = jwkMatcher.isWithKeyIDOnly();
/*  311 */       this.privateOnly = jwkMatcher.isPrivateOnly();
/*  312 */       this.publicOnly = jwkMatcher.isPublicOnly();
/*  313 */       this.nonRevokedOnly = jwkMatcher.isNonRevokedOnly();
/*  314 */       this.revokedOnly = jwkMatcher.isNonRevokedOnly();
/*  315 */       this.minSizeBits = jwkMatcher.getMinKeySize();
/*  316 */       this.maxSizeBits = jwkMatcher.getMaxKeySize();
/*  317 */       this.sizesBits = jwkMatcher.getKeySizes();
/*  318 */       this.curves = jwkMatcher.getCurves();
/*  319 */       this.x5tS256s = jwkMatcher.getX509CertSHA256Thumbprints();
/*  320 */       this.withX5COnly = jwkMatcher.isWithX509CertChainOnly();
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
/*      */     public Builder keyType(KeyType kty) {
/*  333 */       if (kty == null) {
/*  334 */         this.types = null;
/*      */       } else {
/*  336 */         this.types = new HashSet<>(Collections.singletonList(kty));
/*      */       } 
/*      */       
/*  339 */       return this;
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
/*      */     public Builder keyTypes(KeyType... types) {
/*  352 */       keyTypes(new LinkedHashSet<>(Arrays.asList(types)));
/*  353 */       return this;
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
/*      */     public Builder keyTypes(Set<KeyType> types) {
/*  366 */       this.types = types;
/*  367 */       return this;
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
/*      */     public Builder keyUse(KeyUse use) {
/*  381 */       if (use == null) {
/*  382 */         this.uses = null;
/*      */       } else {
/*  384 */         this.uses = new HashSet<>(Collections.singletonList(use));
/*      */       } 
/*  386 */       return this;
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
/*      */     public Builder keyUses(KeyUse... uses) {
/*  399 */       keyUses(new LinkedHashSet<>(Arrays.asList(uses)));
/*  400 */       return this;
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
/*      */     public Builder keyUses(Set<KeyUse> uses) {
/*  414 */       this.uses = uses;
/*  415 */       return this;
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
/*      */     public Builder keyOperation(KeyOperation op) {
/*  428 */       if (op == null) {
/*  429 */         this.ops = null;
/*      */       } else {
/*  431 */         this.ops = new HashSet<>(Collections.singletonList(op));
/*      */       } 
/*  433 */       return this;
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
/*      */     public Builder keyOperations(KeyOperation... ops) {
/*  446 */       keyOperations(new LinkedHashSet<>(Arrays.asList(ops)));
/*  447 */       return this;
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
/*      */     public Builder keyOperations(Set<KeyOperation> ops) {
/*  461 */       this.ops = ops;
/*  462 */       return this;
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
/*      */     public Builder algorithm(Algorithm alg) {
/*  476 */       if (alg == null) {
/*  477 */         this.algs = null;
/*      */       } else {
/*  479 */         this.algs = new HashSet<>(Collections.singletonList(alg));
/*      */       } 
/*  481 */       return this;
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
/*      */     public Builder algorithms(Algorithm... algs) {
/*  494 */       algorithms(new LinkedHashSet<>(Arrays.asList(algs)));
/*  495 */       return this;
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
/*      */     public Builder algorithms(Set<Algorithm> algs) {
/*  509 */       this.algs = algs;
/*  510 */       return this;
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
/*      */     public Builder keyID(String id) {
/*  523 */       if (id == null) {
/*  524 */         this.ids = null;
/*      */       } else {
/*  526 */         this.ids = new HashSet<>(Collections.singletonList(id));
/*      */       } 
/*  528 */       return this;
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
/*      */     public Builder keyIDs(String... ids) {
/*  541 */       keyIDs(new LinkedHashSet<>(Arrays.asList(ids)));
/*  542 */       return this;
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
/*      */     public Builder keyIDs(Set<String> ids) {
/*  555 */       this.ids = ids;
/*  556 */       return this;
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
/*      */     @Deprecated
/*      */     public Builder hasKeyUse(boolean hasUse) {
/*  571 */       return withKeyUseOnly(hasUse);
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
/*      */     public Builder withKeyUseOnly(boolean withUseOnly) {
/*  585 */       this.withUseOnly = withUseOnly;
/*  586 */       return this;
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
/*      */     @Deprecated
/*      */     public Builder hasKeyID(boolean hasID) {
/*  601 */       return withKeyIDOnly(hasID);
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
/*      */     public Builder withKeyIDOnly(boolean withIDOnly) {
/*  615 */       this.withIDOnly = withIDOnly;
/*  616 */       return this;
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
/*      */     public Builder privateOnly(boolean privateOnly) {
/*  629 */       this.privateOnly = privateOnly;
/*  630 */       return this;
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
/*      */     public Builder publicOnly(boolean publicOnly) {
/*  643 */       this.publicOnly = publicOnly;
/*  644 */       return this;
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
/*      */     public Builder nonRevokedOnly(boolean nonRevokedOnly) {
/*  658 */       this.nonRevokedOnly = nonRevokedOnly;
/*  659 */       return this;
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
/*      */     public Builder revokedOnly(boolean revokedOnly) {
/*  672 */       this.revokedOnly = revokedOnly;
/*  673 */       return this;
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
/*      */     public Builder minKeySize(int minSizeBits) {
/*  687 */       this.minSizeBits = minSizeBits;
/*  688 */       return this;
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
/*      */     public Builder maxKeySize(int maxSizeBits) {
/*  702 */       this.maxSizeBits = maxSizeBits;
/*  703 */       return this;
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
/*      */     public Builder keySize(int keySizeBits) {
/*  716 */       if (keySizeBits <= 0) {
/*  717 */         this.sizesBits = null;
/*      */       } else {
/*  719 */         this.sizesBits = Collections.singleton(Integer.valueOf(keySizeBits));
/*      */       } 
/*  721 */       return this;
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
/*      */     public Builder keySizes(int... keySizesBits) {
/*  733 */       Set<Integer> sizesSet = new LinkedHashSet<>();
/*  734 */       for (int keySize : keySizesBits) {
/*  735 */         sizesSet.add(Integer.valueOf(keySize));
/*      */       }
/*  737 */       keySizes(sizesSet);
/*  738 */       return this;
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
/*      */     public Builder keySizes(Set<Integer> keySizesBits) {
/*  751 */       this.sizesBits = keySizesBits;
/*  752 */       return this;
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
/*      */     public Builder curve(Curve curve) {
/*  765 */       if (curve == null) {
/*  766 */         this.curves = null;
/*      */       } else {
/*  768 */         this.curves = Collections.singleton(curve);
/*      */       } 
/*  770 */       return this;
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
/*      */     public Builder curves(Curve... curves) {
/*  783 */       curves(new LinkedHashSet<>(Arrays.asList(curves)));
/*  784 */       return this;
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
/*      */     public Builder curves(Set<Curve> curves) {
/*  797 */       this.curves = curves;
/*  798 */       return this;
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
/*      */     public Builder x509CertSHA256Thumbprint(Base64URL x5tS256) {
/*  812 */       if (x5tS256 == null) {
/*  813 */         this.x5tS256s = null;
/*      */       } else {
/*  815 */         this.x5tS256s = Collections.singleton(x5tS256);
/*      */       } 
/*  817 */       return this;
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
/*      */     public Builder x509CertSHA256Thumbprints(Base64URL... x5tS256s) {
/*  830 */       return x509CertSHA256Thumbprints(new LinkedHashSet<>(Arrays.asList(x5tS256s)));
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
/*      */     public Builder x509CertSHA256Thumbprints(Set<Base64URL> x5tS256s) {
/*  844 */       this.x5tS256s = x5tS256s;
/*  845 */       return this;
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
/*      */     @Deprecated
/*      */     public Builder hasX509CertChain(boolean hasX5C) {
/*  860 */       return withX509CertChainOnly(hasX5C);
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
/*      */     public Builder withX509CertChainOnly(boolean withX5CONly) {
/*  874 */       this.withX5COnly = withX5CONly;
/*  875 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public JWKMatcher build() {
/*  886 */       return new JWKMatcher(this.types, this.uses, this.ops, this.algs, this.ids, this.withUseOnly, this.withIDOnly, this.privateOnly, this.publicOnly, this.nonRevokedOnly, this.revokedOnly, this.minSizeBits, this.maxSizeBits, this.sizesBits, this.curves, this.x5tS256s, this.withX5COnly);
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
/*      */   @Deprecated
/*      */   public JWKMatcher(Set<KeyType> types, Set<KeyUse> uses, Set<KeyOperation> ops, Set<Algorithm> algs, Set<String> ids, boolean privateOnly, boolean publicOnly) {
/*  923 */     this(types, uses, ops, algs, ids, privateOnly, publicOnly, 0, 0);
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
/*      */   @Deprecated
/*      */   public JWKMatcher(Set<KeyType> types, Set<KeyUse> uses, Set<KeyOperation> ops, Set<Algorithm> algs, Set<String> ids, boolean privateOnly, boolean publicOnly, int minSizeBits, int maxSizeBits) {
/*  958 */     this(types, uses, ops, algs, ids, privateOnly, publicOnly, minSizeBits, maxSizeBits, null);
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
/*      */   @Deprecated
/*      */   public JWKMatcher(Set<KeyType> types, Set<KeyUse> uses, Set<KeyOperation> ops, Set<Algorithm> algs, Set<String> ids, boolean privateOnly, boolean publicOnly, int minSizeBits, int maxSizeBits, Set<Curve> curves) {
/*  996 */     this(types, uses, ops, algs, ids, privateOnly, publicOnly, minSizeBits, maxSizeBits, null, curves);
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
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JWKMatcher(Set<KeyType> types, Set<KeyUse> uses, Set<KeyOperation> ops, Set<Algorithm> algs, Set<String> ids, boolean privateOnly, boolean publicOnly, int minSizeBits, int maxSizeBits, Set<Integer> sizesBits, Set<Curve> curves) {
/* 1037 */     this(types, uses, ops, algs, ids, false, false, privateOnly, publicOnly, minSizeBits, maxSizeBits, sizesBits, curves);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JWKMatcher(Set<KeyType> types, Set<KeyUse> uses, Set<KeyOperation> ops, Set<Algorithm> algs, Set<String> ids, boolean withUseOnly, boolean withIDOnly, boolean privateOnly, boolean publicOnly, int minSizeBits, int maxSizeBits, Set<Integer> sizesBits, Set<Curve> curves) {
/* 1082 */     this(types, uses, ops, algs, ids, withUseOnly, withIDOnly, privateOnly, publicOnly, minSizeBits, maxSizeBits, sizesBits, curves, null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JWKMatcher(Set<KeyType> types, Set<KeyUse> uses, Set<KeyOperation> ops, Set<Algorithm> algs, Set<String> ids, boolean withUseOnly, boolean withIDOnly, boolean privateOnly, boolean publicOnly, int minSizeBits, int maxSizeBits, Set<Integer> sizesBits, Set<Curve> curves, Set<Base64URL> x5tS256s) {
/* 1130 */     this(types, uses, ops, algs, ids, withUseOnly, withIDOnly, privateOnly, publicOnly, minSizeBits, maxSizeBits, sizesBits, curves, x5tS256s, false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JWKMatcher(Set<KeyType> types, Set<KeyUse> uses, Set<KeyOperation> ops, Set<Algorithm> algs, Set<String> ids, boolean withUseOnly, boolean withIDOnly, boolean privateOnly, boolean publicOnly, int minSizeBits, int maxSizeBits, Set<Integer> sizesBits, Set<Curve> curves, Set<Base64URL> x5tS256s, boolean withX5COnly) {
/* 1181 */     this(types, uses, ops, algs, ids, withUseOnly, withIDOnly, privateOnly, publicOnly, false, false, minSizeBits, maxSizeBits, sizesBits, curves, x5tS256s, withX5COnly);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JWKMatcher(Set<KeyType> types, Set<KeyUse> uses, Set<KeyOperation> ops, Set<Algorithm> algs, Set<String> ids, boolean withUseOnly, boolean withIDOnly, boolean privateOnly, boolean publicOnly, boolean nonRevokedOnly, boolean revokedOnly, int minSizeBits, int maxSizeBits, Set<Integer> sizesBits, Set<Curve> curves, Set<Base64URL> x5tS256s, boolean withX5COnly) {
/* 1235 */     this.types = types;
/* 1236 */     this.uses = uses;
/* 1237 */     this.ops = ops;
/* 1238 */     this.algs = algs;
/* 1239 */     this.ids = ids;
/* 1240 */     this.withUseOnly = withUseOnly;
/* 1241 */     this.withIDOnly = withIDOnly;
/* 1242 */     this.privateOnly = privateOnly;
/* 1243 */     this.publicOnly = publicOnly;
/* 1244 */     this.nonRevokedOnly = nonRevokedOnly;
/* 1245 */     this.revokedOnly = revokedOnly;
/* 1246 */     this.minSizeBits = minSizeBits;
/* 1247 */     this.maxSizeBits = maxSizeBits;
/* 1248 */     this.sizesBits = sizesBits;
/* 1249 */     this.curves = curves;
/* 1250 */     this.x5tS256s = x5tS256s;
/* 1251 */     this.withX5COnly = withX5COnly;
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
/*      */   public static JWKMatcher forJWEHeader(JWEHeader jweHeader) {
/* 1279 */     return (new Builder())
/* 1280 */       .keyType(KeyType.forAlgorithm((Algorithm)jweHeader.getAlgorithm()))
/* 1281 */       .keyID(jweHeader.getKeyID())
/* 1282 */       .keyUses(new KeyUse[] { KeyUse.ENCRYPTION, null
/* 1283 */         }).algorithms(new Algorithm[] { (Algorithm)jweHeader.getAlgorithm(), null
/* 1284 */         }).build();
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
/*      */   public static JWKMatcher forJWSHeader(JWSHeader jwsHeader) {
/* 1314 */     JWSAlgorithm algorithm = jwsHeader.getAlgorithm();
/* 1315 */     if (JWSAlgorithm.Family.RSA.contains(algorithm) || JWSAlgorithm.Family.EC.contains(algorithm))
/*      */     {
/* 1317 */       return (new Builder())
/* 1318 */         .keyType(KeyType.forAlgorithm((Algorithm)algorithm))
/* 1319 */         .keyID(jwsHeader.getKeyID())
/* 1320 */         .keyUses(new KeyUse[] { KeyUse.SIGNATURE, null
/* 1321 */           }).algorithms(new Algorithm[] { (Algorithm)algorithm, null
/* 1322 */           }).x509CertSHA256Thumbprint(jwsHeader.getX509CertSHA256Thumbprint())
/* 1323 */         .build(); } 
/* 1324 */     if (JWSAlgorithm.Family.HMAC_SHA.contains(algorithm))
/*      */     {
/* 1326 */       return (new Builder())
/* 1327 */         .keyType(KeyType.forAlgorithm((Algorithm)algorithm))
/* 1328 */         .keyID(jwsHeader.getKeyID())
/* 1329 */         .privateOnly(true)
/* 1330 */         .algorithms(new Algorithm[] { (Algorithm)algorithm, null
/* 1331 */           }).build(); } 
/* 1332 */     if (JWSAlgorithm.Family.ED.contains(algorithm)) {
/* 1333 */       return (new Builder())
/* 1334 */         .keyType(KeyType.forAlgorithm((Algorithm)algorithm))
/* 1335 */         .keyID(jwsHeader.getKeyID())
/* 1336 */         .keyUses(new KeyUse[] { KeyUse.SIGNATURE, null
/* 1337 */           }).algorithms(new Algorithm[] { (Algorithm)algorithm, null
/* 1338 */           }).curves(Curve.forJWSAlgorithm(algorithm))
/* 1339 */         .build();
/*      */     }
/* 1341 */     return null;
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
/*      */   public Set<KeyType> getKeyTypes() {
/* 1353 */     return this.types;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<KeyUse> getKeyUses() {
/* 1364 */     return this.uses;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<KeyOperation> getKeyOperations() {
/* 1375 */     return this.ops;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Algorithm> getAlgorithms() {
/* 1386 */     return this.algs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getKeyIDs() {
/* 1397 */     return this.ids;
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
/*      */   @Deprecated
/*      */   public boolean hasKeyUse() {
/* 1410 */     return isWithKeyUseOnly();
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
/*      */   public boolean isWithKeyUseOnly() {
/* 1422 */     return this.withUseOnly;
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
/*      */   @Deprecated
/*      */   public boolean hasKeyID() {
/* 1435 */     return isWithKeyIDOnly();
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
/*      */   public boolean isWithKeyIDOnly() {
/* 1447 */     return this.withIDOnly;
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
/*      */   public boolean isPrivateOnly() {
/* 1459 */     return this.privateOnly;
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
/*      */   public boolean isPublicOnly() {
/* 1471 */     return this.publicOnly;
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
/*      */   public boolean isNonRevokedOnly() {
/* 1483 */     return this.nonRevokedOnly;
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
/*      */   public boolean isRevokedOnly() {
/* 1495 */     return this.revokedOnly;
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
/*      */   @Deprecated
/*      */   public int getMinSize() {
/* 1508 */     return getMinKeySize();
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
/*      */   public int getMinKeySize() {
/* 1520 */     return this.minSizeBits;
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
/*      */   @Deprecated
/*      */   public int getMaxSize() {
/* 1533 */     return getMaxKeySize();
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
/*      */   public int getMaxKeySize() {
/* 1545 */     return this.maxSizeBits;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Integer> getKeySizes() {
/* 1556 */     return this.sizesBits;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Curve> getCurves() {
/* 1567 */     return this.curves;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Base64URL> getX509CertSHA256Thumbprints() {
/* 1577 */     return this.x5tS256s;
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
/*      */   @Deprecated
/*      */   public boolean hasX509CertChain() {
/* 1591 */     return isWithX509CertChainOnly();
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
/*      */   public boolean isWithX509CertChainOnly() {
/* 1604 */     return this.withX5COnly;
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
/*      */   public boolean matches(JWK key) {
/* 1617 */     if (this.withUseOnly && key.getKeyUse() == null) {
/* 1618 */       return false;
/*      */     }
/* 1620 */     if (this.withIDOnly && (key.getKeyID() == null || key.getKeyID().trim().isEmpty())) {
/* 1621 */       return false;
/*      */     }
/* 1623 */     if (this.privateOnly && !key.isPrivate()) {
/* 1624 */       return false;
/*      */     }
/* 1626 */     if (this.publicOnly && key.isPrivate()) {
/* 1627 */       return false;
/*      */     }
/* 1629 */     if (this.nonRevokedOnly && key.getKeyRevocation() != null) {
/* 1630 */       return false;
/*      */     }
/* 1632 */     if (this.revokedOnly && key.getKeyRevocation() == null) {
/* 1633 */       return false;
/*      */     }
/* 1635 */     if (this.types != null && !this.types.contains(key.getKeyType())) {
/* 1636 */       return false;
/*      */     }
/* 1638 */     if (this.uses != null && !this.uses.contains(key.getKeyUse())) {
/* 1639 */       return false;
/*      */     }
/* 1641 */     if (this.ops != null)
/*      */     {
/* 1643 */       if (!this.ops.contains(null) || key.getKeyOperations() != null)
/*      */       {
/* 1645 */         if (key.getKeyOperations() == null || !this.ops.containsAll(key.getKeyOperations()))
/*      */         {
/*      */           
/* 1648 */           return false;
/*      */         }
/*      */       }
/*      */     }
/* 1652 */     if (this.algs != null && !this.algs.contains(key.getAlgorithm())) {
/* 1653 */       return false;
/*      */     }
/* 1655 */     if (this.ids != null && !this.ids.contains(key.getKeyID())) {
/* 1656 */       return false;
/*      */     }
/* 1658 */     if (this.minSizeBits > 0)
/*      */     {
/* 1660 */       if (key.size() < this.minSizeBits) {
/* 1661 */         return false;
/*      */       }
/*      */     }
/* 1664 */     if (this.maxSizeBits > 0)
/*      */     {
/* 1666 */       if (key.size() > this.maxSizeBits) {
/* 1667 */         return false;
/*      */       }
/*      */     }
/* 1670 */     if (this.sizesBits != null && 
/* 1671 */       !this.sizesBits.contains(Integer.valueOf(key.size()))) {
/* 1672 */       return false;
/*      */     }
/*      */     
/* 1675 */     if (this.curves != null) {
/*      */       
/* 1677 */       if (!(key instanceof CurveBasedJWK)) {
/* 1678 */         return false;
/*      */       }
/* 1680 */       CurveBasedJWK curveBasedJWK = (CurveBasedJWK)key;
/*      */       
/* 1682 */       if (!this.curves.contains(curveBasedJWK.getCurve())) {
/* 1683 */         return false;
/*      */       }
/*      */     } 
/* 1686 */     if (this.x5tS256s != null) {
/*      */       
/* 1688 */       boolean matchingCertFound = false;
/*      */       
/* 1690 */       if (key.getX509CertChain() != null && !key.getX509CertChain().isEmpty()) {
/*      */         try {
/* 1692 */           X509Certificate cert = X509CertUtils.parseWithException(((Base64)key.getX509CertChain().get(0)).decode());
/* 1693 */           matchingCertFound = this.x5tS256s.contains(X509CertUtils.computeSHA256Thumbprint(cert));
/* 1694 */         } catch (CertificateException certificateException) {}
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1699 */       boolean matchingX5T256Found = this.x5tS256s.contains(key.getX509CertSHA256Thumbprint());
/*      */       
/* 1701 */       if (!matchingCertFound && !matchingX5T256Found) {
/* 1702 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1706 */     if (this.withX5COnly) {
/* 1707 */       return (key.getX509CertChain() != null && !key.getX509CertChain().isEmpty());
/*      */     }
/*      */     
/* 1710 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1716 */     StringBuilder sb = new StringBuilder();
/*      */     
/* 1718 */     append(sb, "kty", this.types);
/* 1719 */     append(sb, "use", this.uses);
/* 1720 */     append(sb, "key_ops", this.ops);
/* 1721 */     append(sb, "alg", this.algs);
/* 1722 */     append(sb, "kid", this.ids);
/*      */     
/* 1724 */     if (this.withUseOnly) {
/* 1725 */       sb.append("with_use_only=true ");
/*      */     }
/*      */     
/* 1728 */     if (this.withIDOnly) {
/* 1729 */       sb.append("with_id_only=true ");
/*      */     }
/*      */     
/* 1732 */     if (this.privateOnly) {
/* 1733 */       sb.append("private_only=true ");
/*      */     }
/*      */     
/* 1736 */     if (this.publicOnly) {
/* 1737 */       sb.append("public_only=true ");
/*      */     }
/*      */     
/* 1740 */     if (this.nonRevokedOnly) {
/* 1741 */       sb.append("non_revoked_only=true ");
/*      */     }
/*      */     
/* 1744 */     if (this.revokedOnly) {
/* 1745 */       sb.append("revoked_only=true ");
/*      */     }
/*      */     
/* 1748 */     if (this.minSizeBits > 0) {
/* 1749 */       sb.append("min_size=" + this.minSizeBits + " ");
/*      */     }
/*      */     
/* 1752 */     if (this.maxSizeBits > 0) {
/* 1753 */       sb.append("max_size=" + this.maxSizeBits + " ");
/*      */     }
/*      */     
/* 1756 */     append(sb, "size", this.sizesBits);
/* 1757 */     append(sb, "crv", this.curves);
/* 1758 */     append(sb, "x5t#S256", this.x5tS256s);
/* 1759 */     if (this.withX5COnly) {
/* 1760 */       sb.append("with_x5c_only=true");
/*      */     }
/*      */     
/* 1763 */     return sb.toString().trim();
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
/*      */   private static void append(StringBuilder sb, String key, Set<?> values) {
/* 1776 */     if (values != null) {
/*      */       
/* 1778 */       sb.append(key);
/* 1779 */       sb.append('=');
/* 1780 */       if (values.size() == 1) {
/* 1781 */         Object value = values.iterator().next();
/* 1782 */         if (value == null) {
/* 1783 */           sb.append("ANY");
/*      */         } else {
/* 1785 */           sb.append(value.toString().trim());
/*      */         } 
/*      */       } else {
/* 1788 */         sb.append(values.toString().trim());
/*      */       } 
/*      */       
/* 1791 */       sb.append(' ');
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jose\jwk\JWKMatcher.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */