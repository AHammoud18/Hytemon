/*     */ package io.netty.buffer.search;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Queue;
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
/*     */ public class AhoCorasicSearchProcessorFactory
/*     */   extends AbstractMultiSearchProcessorFactory
/*     */ {
/*     */   private final int[] jumpTable;
/*     */   private final int[] matchForNeedleId;
/*     */   static final int BITS_PER_SYMBOL = 8;
/*     */   static final int ALPHABET_SIZE = 256;
/*     */   
/*     */   private static class Context
/*     */   {
/*     */     int[] jumpTable;
/*     */     int[] matchForNeedleId;
/*     */     
/*     */     private Context() {}
/*     */   }
/*     */   
/*     */   public static class Processor
/*     */     implements MultiSearchProcessor
/*     */   {
/*     */     private final int[] jumpTable;
/*     */     private final int[] matchForNeedleId;
/*     */     private long currentPosition;
/*     */     
/*     */     Processor(int[] jumpTable, int[] matchForNeedleId) {
/*  53 */       this.jumpTable = jumpTable;
/*  54 */       this.matchForNeedleId = matchForNeedleId;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean process(byte value) {
/*  59 */       this.currentPosition = PlatformDependent.getInt(this.jumpTable, this.currentPosition | value & 0xFFL);
/*  60 */       if (this.currentPosition < 0L) {
/*  61 */         this.currentPosition = -this.currentPosition;
/*  62 */         return false;
/*     */       } 
/*  64 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getFoundNeedleId() {
/*  69 */       return this.matchForNeedleId[(int)this.currentPosition >> 8];
/*     */     }
/*     */ 
/*     */     
/*     */     public void reset() {
/*  74 */       this.currentPosition = 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   AhoCorasicSearchProcessorFactory(byte[]... needles) {
/*  80 */     for (byte[] needle : needles) {
/*  81 */       if (needle.length == 0) {
/*  82 */         throw new IllegalArgumentException("Needle must be non empty");
/*     */       }
/*     */     } 
/*     */     
/*  86 */     Context context = buildTrie(needles);
/*  87 */     this.jumpTable = context.jumpTable;
/*  88 */     this.matchForNeedleId = context.matchForNeedleId;
/*     */     
/*  90 */     linkSuffixes();
/*     */     
/*  92 */     for (int i = 0; i < this.jumpTable.length; i++) {
/*  93 */       if (this.matchForNeedleId[this.jumpTable[i] >> 8] >= 0) {
/*  94 */         this.jumpTable[i] = -this.jumpTable[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Context buildTrie(byte[][] needles) {
/* 101 */     ArrayList<Integer> jumpTableBuilder = new ArrayList<>(256);
/* 102 */     for (int i = 0; i < 256; i++) {
/* 103 */       jumpTableBuilder.add(Integer.valueOf(-1));
/*     */     }
/*     */     
/* 106 */     ArrayList<Integer> matchForBuilder = new ArrayList<>();
/* 107 */     matchForBuilder.add(Integer.valueOf(-1));
/*     */     
/* 109 */     for (int needleId = 0; needleId < needles.length; needleId++) {
/* 110 */       byte[] needle = needles[needleId];
/* 111 */       int currentPosition = 0;
/*     */       
/* 113 */       for (byte ch0 : needle) {
/*     */         
/* 115 */         int ch = ch0 & 0xFF;
/* 116 */         int next = currentPosition + ch;
/*     */         
/* 118 */         if (((Integer)jumpTableBuilder.get(next)).intValue() == -1) {
/* 119 */           jumpTableBuilder.set(next, Integer.valueOf(jumpTableBuilder.size()));
/* 120 */           for (int k = 0; k < 256; k++) {
/* 121 */             jumpTableBuilder.add(Integer.valueOf(-1));
/*     */           }
/* 123 */           matchForBuilder.add(Integer.valueOf(-1));
/*     */         } 
/*     */         
/* 126 */         currentPosition = ((Integer)jumpTableBuilder.get(next)).intValue();
/*     */       } 
/*     */       
/* 129 */       matchForBuilder.set(currentPosition >> 8, Integer.valueOf(needleId));
/*     */     } 
/*     */     
/* 132 */     Context context = new Context();
/*     */     
/* 134 */     context.jumpTable = new int[jumpTableBuilder.size()]; int j;
/* 135 */     for (j = 0; j < jumpTableBuilder.size(); j++) {
/* 136 */       context.jumpTable[j] = ((Integer)jumpTableBuilder.get(j)).intValue();
/*     */     }
/*     */     
/* 139 */     context.matchForNeedleId = new int[matchForBuilder.size()];
/* 140 */     for (j = 0; j < matchForBuilder.size(); j++) {
/* 141 */       context.matchForNeedleId[j] = ((Integer)matchForBuilder.get(j)).intValue();
/*     */     }
/*     */     
/* 144 */     return context;
/*     */   }
/*     */ 
/*     */   
/*     */   private void linkSuffixes() {
/* 149 */     Queue<Integer> queue = new ArrayDeque<>();
/* 150 */     queue.add(Integer.valueOf(0));
/*     */     
/* 152 */     int[] suffixLinks = new int[this.matchForNeedleId.length];
/* 153 */     Arrays.fill(suffixLinks, -1);
/*     */     
/* 155 */     while (!queue.isEmpty()) {
/*     */       
/* 157 */       int v = ((Integer)queue.remove()).intValue();
/* 158 */       int vPosition = v >> 8;
/* 159 */       int u = (suffixLinks[vPosition] == -1) ? 0 : suffixLinks[vPosition];
/*     */       
/* 161 */       if (this.matchForNeedleId[vPosition] == -1) {
/* 162 */         this.matchForNeedleId[vPosition] = this.matchForNeedleId[u >> 8];
/*     */       }
/*     */       
/* 165 */       for (int ch = 0; ch < 256; ch++) {
/*     */         
/* 167 */         int vIndex = v | ch;
/* 168 */         int uIndex = u | ch;
/*     */         
/* 170 */         int jumpV = this.jumpTable[vIndex];
/* 171 */         int jumpU = this.jumpTable[uIndex];
/*     */         
/* 173 */         if (jumpV != -1) {
/* 174 */           suffixLinks[jumpV >> 8] = (v > 0 && jumpU != -1) ? jumpU : 0;
/* 175 */           queue.add(Integer.valueOf(jumpV));
/*     */         } else {
/* 177 */           this.jumpTable[vIndex] = (jumpU != -1) ? jumpU : 0;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Processor newSearchProcessor() {
/* 188 */     return new Processor(this.jumpTable, this.matchForNeedleId);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\buffer\search\AhoCorasicSearchProcessorFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */