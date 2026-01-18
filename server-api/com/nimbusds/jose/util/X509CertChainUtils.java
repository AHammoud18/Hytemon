/*     */ package com.nimbusds.jose.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.nio.file.Files;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.text.ParseException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import org.bouncycastle.cert.X509CertificateHolder;
/*     */ import org.bouncycastle.openssl.PEMParser;
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
/*     */ public class X509CertChainUtils
/*     */ {
/*     */   public static List<Base64> toBase64List(List<Object> jsonArray) throws ParseException {
/*  62 */     if (jsonArray == null) {
/*  63 */       return null;
/*     */     }
/*  65 */     List<Base64> chain = new LinkedList<>();
/*     */     
/*  67 */     for (int i = 0; i < jsonArray.size(); i++) {
/*     */       
/*  69 */       Object item = jsonArray.get(i);
/*     */       
/*  71 */       if (item == null) {
/*  72 */         throw new ParseException("The X.509 certificate at position " + i + " must not be null", 0);
/*     */       }
/*     */       
/*  75 */       if (!(item instanceof String)) {
/*  76 */         throw new ParseException("The X.509 certificate at position " + i + " must be encoded as a Base64 string", 0);
/*     */       }
/*     */       
/*  79 */       chain.add(new Base64((String)item));
/*     */     } 
/*     */     
/*  82 */     return chain;
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
/*     */   public static List<X509Certificate> parse(List<Base64> b64List) throws ParseException {
/* 100 */     if (b64List == null) {
/* 101 */       return null;
/*     */     }
/* 103 */     List<X509Certificate> out = new LinkedList<>();
/*     */     
/* 105 */     for (int i = 0; i < b64List.size(); i++) {
/*     */       
/* 107 */       if (b64List.get(i) != null) {
/*     */         X509Certificate cert;
/*     */         
/*     */         try {
/* 111 */           cert = X509CertUtils.parseWithException(((Base64)b64List.get(i)).decode());
/* 112 */         } catch (CertificateException e) {
/* 113 */           throw new ParseException("Invalid X.509 certificate at position " + i + ": " + e.getMessage(), 0);
/*     */         } 
/*     */         
/* 116 */         out.add(cert);
/*     */       } 
/*     */     } 
/* 119 */     return out;
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
/*     */   public static List<X509Certificate> parse(File pemFile) throws IOException, CertificateException {
/* 140 */     String pemString = new String(Files.readAllBytes(pemFile.toPath()), StandardCharset.UTF_8);
/* 141 */     return parse(pemString);
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
/*     */   public static List<X509Certificate> parse(String pemString) throws IOException, CertificateException {
/*     */     Object pemObject;
/* 162 */     Reader pemReader = new StringReader(pemString);
/* 163 */     PEMParser parser = new PEMParser(pemReader);
/*     */     
/* 165 */     List<X509Certificate> certChain = new LinkedList<>();
/*     */ 
/*     */     
/*     */     do {
/* 169 */       pemObject = parser.readObject();
/*     */       
/* 171 */       if (!(pemObject instanceof X509CertificateHolder))
/*     */         continue; 
/* 173 */       X509CertificateHolder certHolder = (X509CertificateHolder)pemObject;
/* 174 */       byte[] derEncodedCert = certHolder.getEncoded();
/* 175 */       certChain.add(X509CertUtils.parseWithException(derEncodedCert));
/*     */ 
/*     */     
/*     */     }
/* 179 */     while (pemObject != null);
/*     */     
/* 181 */     return certChain;
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
/*     */   public static List<UUID> store(KeyStore trustStore, List<X509Certificate> certChain) throws KeyStoreException {
/* 202 */     List<UUID> aliases = new LinkedList<>();
/*     */     
/* 204 */     for (X509Certificate cert : certChain) {
/* 205 */       UUID alias = UUID.randomUUID();
/* 206 */       trustStore.setCertificateEntry(alias.toString(), cert);
/* 207 */       aliases.add(alias);
/*     */     } 
/*     */     
/* 210 */     return aliases;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jos\\util\X509CertChainUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */