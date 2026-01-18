/*     */ package io.netty.handler.ssl.util;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.math.BigInteger;
/*     */ import java.security.AccessController;
/*     */ import java.security.KeyPair;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
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
/*     */ final class OpenJdkSelfSignedCertGenerator
/*     */ {
/*  46 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OpenJdkSelfSignedCertGenerator.class);
/*     */   
/*     */   private static final MethodHandle CERT_INFO_SET_HANDLE;
/*     */   
/*     */   private static final MethodHandle ISSUER_NAME_CONSTRUCTOR;
/*     */   
/*     */   private static final MethodHandle CERT_IMPL_CONSTRUCTOR;
/*     */   
/*     */   private static final MethodHandle X509_CERT_INFO_CONSTRUCTOR;
/*     */   
/*     */   private static final MethodHandle CERTIFICATE_VERSION_CONSTRUCTOR;
/*     */   
/*     */   private static final MethodHandle CERTIFICATE_SUBJECT_NAME_CONSTRUCTOR;
/*     */   
/*     */   private static final MethodHandle X500_NAME_CONSTRUCTOR;
/*     */   
/*     */   private static final MethodHandle CERTIFICATE_SERIAL_NUMBER_CONSTRUCTOR;
/*     */ 
/*     */   
/*     */   static {
/*  66 */     final MethodHandles.Lookup lookup = MethodHandles.lookup();
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
/*  81 */     MethodHandle certInfoSetHandle = null;
/*  82 */     MethodHandle x509CertInfoConstructor = null;
/*  83 */     MethodHandle issuerNameConstructor = null;
/*  84 */     MethodHandle certImplConstructor = null;
/*  85 */     MethodHandle x500NameConstructor = null;
/*  86 */     MethodHandle certificateVersionConstructor = null;
/*  87 */     MethodHandle certificateSubjectNameConstructor = null;
/*  88 */     MethodHandle certificateSerialNumberConstructor = null;
/*  89 */     MethodHandle certificateValidityConstructor = null;
/*  90 */     MethodHandle certificateX509KeyConstructor = null;
/*  91 */     MethodHandle certificateAlgorithmIdConstructor = null;
/*  92 */     MethodHandle certImplGetHandle = null;
/*  93 */     MethodHandle certImplSignHandle = null;
/*  94 */     MethodHandle algorithmIdGetHandle = null;
/*     */     try {
/*     */       final Class<?> x509CertInfoClass, x500NameClass, certificateIssuerNameClass, x509CertImplClass, certificateVersionClass, certificateSubjectNameClass, certificateSerialNumberClass, certificateValidityClass, certificateX509KeyClass, algorithmIdClass, certificateAlgorithmIdClass;
/*  97 */       Object maybeClasses = AccessController.doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public Object run() {
/*     */               try {
/* 101 */                 List<Class<?>> classes = new ArrayList<>();
/* 102 */                 classes.add(Class.forName("sun.security.x509.X509CertInfo", false, 
/* 103 */                       PlatformDependent.getClassLoader(OpenJdkSelfSignedCertGenerator.class)));
/* 104 */                 classes.add(Class.forName("sun.security.x509.X500Name", false, 
/* 105 */                       PlatformDependent.getClassLoader(OpenJdkSelfSignedCertGenerator.class)));
/* 106 */                 classes.add(Class.forName("sun.security.x509.CertificateIssuerName", false, 
/* 107 */                       PlatformDependent.getClassLoader(OpenJdkSelfSignedCertGenerator.class)));
/* 108 */                 classes.add(Class.forName("sun.security.x509.X509CertImpl", false, 
/* 109 */                       PlatformDependent.getClassLoader(OpenJdkSelfSignedCertGenerator.class)));
/* 110 */                 classes.add(Class.forName("sun.security.x509.CertificateVersion", false, 
/* 111 */                       PlatformDependent.getClassLoader(OpenJdkSelfSignedCertGenerator.class)));
/* 112 */                 classes.add(Class.forName("sun.security.x509.CertificateSubjectName", false, 
/* 113 */                       PlatformDependent.getClassLoader(OpenJdkSelfSignedCertGenerator.class)));
/* 114 */                 classes.add(Class.forName("sun.security.x509.CertificateSerialNumber", false, 
/* 115 */                       PlatformDependent.getClassLoader(OpenJdkSelfSignedCertGenerator.class)));
/* 116 */                 classes.add(Class.forName("sun.security.x509.CertificateValidity", false, 
/* 117 */                       PlatformDependent.getClassLoader(OpenJdkSelfSignedCertGenerator.class)));
/* 118 */                 classes.add(Class.forName("sun.security.x509.CertificateX509Key", false, 
/* 119 */                       PlatformDependent.getClassLoader(OpenJdkSelfSignedCertGenerator.class)));
/* 120 */                 classes.add(Class.forName("sun.security.x509.AlgorithmId", false, 
/* 121 */                       PlatformDependent.getClassLoader(OpenJdkSelfSignedCertGenerator.class)));
/* 122 */                 classes.add(Class.forName("sun.security.x509.CertificateAlgorithmId", false, 
/* 123 */                       PlatformDependent.getClassLoader(OpenJdkSelfSignedCertGenerator.class)));
/*     */                 
/* 125 */                 return classes;
/* 126 */               } catch (Throwable cause) {
/* 127 */                 return cause;
/*     */               } 
/*     */             }
/*     */           });
/* 131 */       if (maybeClasses instanceof List) {
/* 132 */         List<Class<?>> classes = (List<Class<?>>)maybeClasses;
/* 133 */         x509CertInfoClass = classes.get(0);
/* 134 */         x500NameClass = classes.get(1);
/* 135 */         certificateIssuerNameClass = classes.get(2);
/* 136 */         x509CertImplClass = classes.get(3);
/* 137 */         certificateVersionClass = classes.get(4);
/* 138 */         certificateSubjectNameClass = classes.get(5);
/* 139 */         certificateSerialNumberClass = classes.get(6);
/* 140 */         certificateValidityClass = classes.get(7);
/* 141 */         certificateX509KeyClass = classes.get(8);
/* 142 */         algorithmIdClass = classes.get(9);
/* 143 */         certificateAlgorithmIdClass = classes.get(10);
/*     */       } else {
/* 145 */         throw (Throwable)maybeClasses;
/*     */       } 
/*     */       
/* 148 */       Object maybeConstructors = AccessController.doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public Object run() {
/*     */               try {
/* 152 */                 List<MethodHandle> constructors = new ArrayList<>();
/* 153 */                 constructors.add(lookup
/* 154 */                     .unreflectConstructor(x509CertInfoClass.getConstructor(new Class[0]))
/* 155 */                     .asType(MethodType.methodType(x509CertInfoClass)));
/*     */                 
/* 157 */                 constructors.add(lookup
/* 158 */                     .unreflectConstructor(certificateIssuerNameClass.getConstructor(new Class[] { this.val$x500NameClass
/* 159 */                         })).asType(MethodType.methodType(certificateIssuerNameClass, x500NameClass)));
/*     */                 
/* 161 */                 constructors.add(lookup
/* 162 */                     .unreflectConstructor(x509CertImplClass.getConstructor(new Class[] { this.val$x509CertInfoClass
/* 163 */                         })).asType(MethodType.methodType(x509CertImplClass, x509CertInfoClass)));
/*     */                 
/* 165 */                 constructors.add(lookup
/* 166 */                     .unreflectConstructor(x500NameClass.getConstructor(new Class[] { String.class
/* 167 */                         })).asType(MethodType.methodType(x500NameClass, String.class)));
/*     */                 
/* 169 */                 constructors.add(lookup
/* 170 */                     .unreflectConstructor(certificateVersionClass.getConstructor(new Class[] { int.class
/* 171 */                         })).asType(MethodType.methodType(certificateVersionClass, int.class)));
/*     */                 
/* 173 */                 constructors.add(lookup
/* 174 */                     .unreflectConstructor(certificateSubjectNameClass.getConstructor(new Class[] { this.val$x500NameClass
/* 175 */                         })).asType(MethodType.methodType(certificateSubjectNameClass, x500NameClass)));
/*     */                 
/* 177 */                 constructors.add(lookup
/* 178 */                     .unreflectConstructor(certificateSerialNumberClass
/* 179 */                       .getConstructor(new Class[] { BigInteger.class
/* 180 */                         })).asType(MethodType.methodType(certificateSerialNumberClass, BigInteger.class)));
/*     */                 
/* 182 */                 constructors.add(lookup
/* 183 */                     .unreflectConstructor(certificateValidityClass
/* 184 */                       .getConstructor(new Class[] { Date.class, Date.class
/* 185 */                         })).asType(MethodType.methodType(certificateValidityClass, Date.class, new Class[] { Date.class })));
/*     */                 
/* 187 */                 constructors.add(lookup
/* 188 */                     .unreflectConstructor(certificateX509KeyClass.getConstructor(new Class[] { PublicKey.class
/* 189 */                         })).asType(MethodType.methodType(certificateX509KeyClass, PublicKey.class)));
/*     */ 
/*     */                 
/* 192 */                 constructors.add(lookup
/* 193 */                     .unreflectConstructor(certificateAlgorithmIdClass
/* 194 */                       .getConstructor(new Class[] { this.val$algorithmIdClass
/* 195 */                         })).asType(MethodType.methodType(certificateAlgorithmIdClass, algorithmIdClass)));
/*     */                 
/* 197 */                 return constructors;
/* 198 */               } catch (Throwable cause) {
/* 199 */                 return cause;
/*     */               } 
/*     */             }
/*     */           });
/* 203 */       if (maybeConstructors instanceof List) {
/* 204 */         List<MethodHandle> constructorList = (List<MethodHandle>)maybeConstructors;
/*     */         
/* 206 */         x509CertInfoConstructor = constructorList.get(0);
/* 207 */         issuerNameConstructor = constructorList.get(1);
/* 208 */         certImplConstructor = constructorList.get(2);
/* 209 */         x500NameConstructor = constructorList.get(3);
/* 210 */         certificateVersionConstructor = constructorList.get(4);
/* 211 */         certificateSubjectNameConstructor = constructorList.get(5);
/* 212 */         certificateSerialNumberConstructor = constructorList.get(6);
/* 213 */         certificateValidityConstructor = constructorList.get(7);
/* 214 */         certificateX509KeyConstructor = constructorList.get(8);
/* 215 */         certificateAlgorithmIdConstructor = constructorList.get(9);
/*     */       } else {
/* 217 */         throw (Throwable)maybeConstructors;
/*     */       } 
/*     */       
/* 220 */       Object maybeMethodHandles = AccessController.doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public Object run() {
/*     */               try {
/* 224 */                 List<MethodHandle> methods = new ArrayList<>();
/* 225 */                 methods.add(lookup
/* 226 */                     .findVirtual(x509CertInfoClass, "set", 
/* 227 */                       MethodType.methodType(void.class, String.class, new Class[] { Object.class })));
/*     */                 
/* 229 */                 methods.add(lookup
/* 230 */                     .findVirtual(x509CertImplClass, "get", 
/* 231 */                       MethodType.methodType(Object.class, String.class)));
/*     */ 
/*     */                 
/* 234 */                 methods.add(lookup
/* 235 */                     .findVirtual(x509CertImplClass, "sign", 
/* 236 */                       MethodType.methodType(void.class, PrivateKey.class, new Class[] { String.class })));
/*     */                 
/* 238 */                 methods.add(lookup
/* 239 */                     .findStatic(algorithmIdClass, "get", 
/* 240 */                       MethodType.methodType(algorithmIdClass, String.class)));
/*     */                 
/* 242 */                 return methods;
/* 243 */               } catch (Throwable cause) {
/* 244 */                 return cause;
/*     */               } 
/*     */             }
/*     */           });
/* 248 */       if (maybeMethodHandles instanceof List) {
/* 249 */         List<MethodHandle> methodHandles = (List<MethodHandle>)maybeMethodHandles;
/*     */         
/* 251 */         certInfoSetHandle = methodHandles.get(0);
/* 252 */         certImplGetHandle = methodHandles.get(1);
/* 253 */         certImplSignHandle = methodHandles.get(2);
/* 254 */         algorithmIdGetHandle = methodHandles.get(3);
/*     */       } else {
/* 256 */         throw (Throwable)maybeMethodHandles;
/*     */       } 
/* 258 */       supported = true;
/* 259 */     } catch (Throwable cause) {
/* 260 */       supported = false;
/* 261 */       logger.debug(OpenJdkSelfSignedCertGenerator.class.getSimpleName() + " not supported", cause);
/*     */     } 
/* 263 */     CERT_INFO_SET_HANDLE = certInfoSetHandle;
/* 264 */     X509_CERT_INFO_CONSTRUCTOR = x509CertInfoConstructor;
/* 265 */     ISSUER_NAME_CONSTRUCTOR = issuerNameConstructor;
/* 266 */     CERTIFICATE_VERSION_CONSTRUCTOR = certificateVersionConstructor;
/* 267 */     CERTIFICATE_SUBJECT_NAME_CONSTRUCTOR = certificateSubjectNameConstructor;
/* 268 */     CERT_IMPL_CONSTRUCTOR = certImplConstructor;
/* 269 */     X500_NAME_CONSTRUCTOR = x500NameConstructor;
/* 270 */     CERTIFICATE_SERIAL_NUMBER_CONSTRUCTOR = certificateSerialNumberConstructor;
/* 271 */     CERTIFICATE_VALIDITY_CONSTRUCTOR = certificateValidityConstructor;
/* 272 */     CERTIFICATE_X509_KEY_CONSTRUCTOR = certificateX509KeyConstructor;
/* 273 */     CERT_IMPL_GET_HANDLE = certImplGetHandle;
/* 274 */     CERT_IMPL_SIGN_HANDLE = certImplSignHandle;
/* 275 */     ALGORITHM_ID_GET_HANDLE = algorithmIdGetHandle;
/* 276 */     CERTIFICATE_ALORITHM_ID_CONSTRUCTOR = certificateAlgorithmIdConstructor;
/* 277 */     SUPPORTED = supported;
/*     */   } private static final MethodHandle CERTIFICATE_VALIDITY_CONSTRUCTOR; private static final MethodHandle CERTIFICATE_X509_KEY_CONSTRUCTOR; private static final MethodHandle CERTIFICATE_ALORITHM_ID_CONSTRUCTOR; private static final MethodHandle CERT_IMPL_GET_HANDLE; private static final MethodHandle CERT_IMPL_SIGN_HANDLE; private static final MethodHandle ALGORITHM_ID_GET_HANDLE; private static final boolean SUPPORTED; static {
/*     */     boolean supported;
/*     */   }
/*     */   static String[] generate(String fqdn, KeyPair keypair, SecureRandom random, Date notBefore, Date notAfter, String algorithm) throws Exception {
/* 282 */     if (!SUPPORTED) {
/* 283 */       throw new UnsupportedOperationException(OpenJdkSelfSignedCertGenerator.class
/* 284 */           .getSimpleName() + " not supported on the used JDK version");
/*     */     }
/*     */     try {
/* 287 */       PrivateKey key = keypair.getPrivate();
/*     */ 
/*     */       
/* 290 */       Object info = X509_CERT_INFO_CONSTRUCTOR.invoke();
/* 291 */       Object owner = X500_NAME_CONSTRUCTOR.invoke("CN=" + fqdn);
/*     */       
/* 293 */       CERT_INFO_SET_HANDLE.invoke(info, "version", CERTIFICATE_VERSION_CONSTRUCTOR.invoke(2));
/* 294 */       CERT_INFO_SET_HANDLE.invoke(info, "serialNumber", CERTIFICATE_SERIAL_NUMBER_CONSTRUCTOR
/* 295 */           .invoke(new BigInteger(64, random)));
/*     */       try {
/* 297 */         CERT_INFO_SET_HANDLE.invoke(info, "subject", CERTIFICATE_SUBJECT_NAME_CONSTRUCTOR.invoke(owner));
/* 298 */       } catch (CertificateException ex) {
/* 299 */         CERT_INFO_SET_HANDLE.invoke(info, "subject", owner);
/*     */       } 
/*     */       try {
/* 302 */         CERT_INFO_SET_HANDLE.invoke(info, "issuer", ISSUER_NAME_CONSTRUCTOR.invoke(owner));
/* 303 */       } catch (CertificateException ex) {
/* 304 */         CERT_INFO_SET_HANDLE.invoke(info, "issuer", owner);
/*     */       } 
/* 306 */       CERT_INFO_SET_HANDLE.invoke(info, "validity", CERTIFICATE_VALIDITY_CONSTRUCTOR
/* 307 */           .invoke(notBefore, notAfter));
/* 308 */       CERT_INFO_SET_HANDLE.invoke(info, "key", CERTIFICATE_X509_KEY_CONSTRUCTOR.invoke(keypair.getPublic()));
/* 309 */       CERT_INFO_SET_HANDLE.invoke(info, "algorithmID", CERTIFICATE_ALORITHM_ID_CONSTRUCTOR
/*     */           
/* 311 */           .invoke(ALGORITHM_ID_GET_HANDLE
/* 312 */             .invoke("1.2.840.113549.1.1.11")));
/*     */ 
/*     */       
/* 315 */       Object cert = CERT_IMPL_CONSTRUCTOR.invoke(info);
/* 316 */       CERT_IMPL_SIGN_HANDLE.invoke(cert, key, 
/* 317 */           algorithm.equalsIgnoreCase("EC") ? "SHA256withECDSA" : "SHA256withRSA");
/*     */ 
/*     */       
/* 320 */       CERT_INFO_SET_HANDLE.invoke(info, "algorithmID.algorithm", CERT_IMPL_GET_HANDLE
/* 321 */           .invoke(cert, "x509.algorithm"));
/* 322 */       cert = CERT_IMPL_CONSTRUCTOR.invoke(info);
/* 323 */       CERT_IMPL_SIGN_HANDLE.invoke(cert, key, 
/* 324 */           algorithm.equalsIgnoreCase("EC") ? "SHA256withECDSA" : "SHA256withRSA");
/*     */       
/* 326 */       X509Certificate x509Cert = (X509Certificate)cert;
/* 327 */       x509Cert.verify(keypair.getPublic());
/*     */       
/* 329 */       return SelfSignedCertificate.newSelfSignedCertificate(fqdn, key, x509Cert);
/* 330 */     } catch (Throwable cause) {
/* 331 */       if (cause instanceof Exception) {
/* 332 */         throw (Exception)cause;
/*     */       }
/* 334 */       if (cause instanceof Error) {
/* 335 */         throw (Error)cause;
/*     */       }
/* 337 */       throw new IllegalStateException(cause);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\OpenJdkSelfSignedCertGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */