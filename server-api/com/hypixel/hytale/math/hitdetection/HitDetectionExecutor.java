/*     */ package com.hypixel.hytale.math.hitdetection;
/*     */ 
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.matrix.Matrix4d;
/*     */ import com.hypixel.hytale.math.shape.Quad4d;
/*     */ import com.hypixel.hytale.math.shape.Triangle4d;
/*     */ import com.hypixel.hytale.math.vector.Vector4d;
/*     */ import java.util.Arrays;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HitDetectionExecutor
/*     */ {
/*  19 */   public static final HytaleLogger log = HytaleLogger.forEnclosingClass();
/*     */ 
/*     */   
/*  22 */   private static final Vector4d[] VERTEX_POINTS = new Vector4d[] {
/*  23 */       Vector4d.newPosition(0.0D, 1.0D, 1.0D), 
/*  24 */       Vector4d.newPosition(0.0D, 1.0D, 0.0D), 
/*  25 */       Vector4d.newPosition(1.0D, 1.0D, 1.0D), 
/*  26 */       Vector4d.newPosition(1.0D, 1.0D, 0.0D), 
/*  27 */       Vector4d.newPosition(0.0D, 0.0D, 1.0D), 
/*  28 */       Vector4d.newPosition(0.0D, 0.0D, 0.0D), 
/*  29 */       Vector4d.newPosition(1.0D, 0.0D, 1.0D), 
/*  30 */       Vector4d.newPosition(1.0D, 0.0D, 0.0D)
/*     */     };
/*     */   
/*  33 */   public static final Quad4d[] CUBE_QUADS = new Quad4d[] { new Quad4d(VERTEX_POINTS, 0, 1, 3, 2), new Quad4d(VERTEX_POINTS, 0, 4, 5, 1), new Quad4d(VERTEX_POINTS, 4, 5, 7, 6), new Quad4d(VERTEX_POINTS, 2, 3, 7, 6), new Quad4d(VERTEX_POINTS, 1, 3, 7, 5), new Quad4d(VERTEX_POINTS, 0, 2, 6, 4) };
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
/*     */   @Nonnull
/*  56 */   private final Matrix4d pvmMatrix = new Matrix4d(); @Nonnull
/*  57 */   private final Matrix4d invPvMatrix = new Matrix4d(); @Nonnull
/*  58 */   private final Vector4d origin = new Vector4d(); @Nonnull
/*  59 */   private final HitDetectionBuffer buffer = new HitDetectionBuffer(); private MatrixProvider projectionProvider;
/*  60 */   private LineOfSightProvider losProvider = LineOfSightProvider.DEFAULT_TRUE; private MatrixProvider viewProvider;
/*  61 */   private int maxRayTests = 10;
/*     */ 
/*     */   
/*     */   public Vector4d getHitLocation() {
/*  65 */     return this.buffer.hitPosition;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public HitDetectionExecutor setProjectionProvider(MatrixProvider provider) {
/*  70 */     this.projectionProvider = provider;
/*  71 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public HitDetectionExecutor setViewProvider(MatrixProvider provider) {
/*  76 */     this.viewProvider = provider;
/*  77 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public HitDetectionExecutor setLineOfSightProvider(LineOfSightProvider losProvider) {
/*  82 */     this.losProvider = losProvider;
/*  83 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public HitDetectionExecutor setMaxRayTests(int maxRayTests) {
/*  88 */     this.maxRayTests = maxRayTests;
/*  89 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public HitDetectionExecutor setOrigin(double x, double y, double z) {
/*  94 */     this.origin.assign(x, y, z, 1.0D);
/*  95 */     return this;
/*     */   }
/*     */   
/*     */   private void setupMatrices(@Nonnull Matrix4d modelMatrix) {
/*  99 */     Matrix4d projectionMatrix = this.projectionProvider.getMatrix();
/* 100 */     Matrix4d viewMatrix = this.viewProvider.getMatrix();
/*     */     
/* 102 */     this.pvmMatrix.assign(projectionMatrix)
/* 103 */       .multiply(viewMatrix);
/* 104 */     this.invPvMatrix.assign(this.pvmMatrix)
/* 105 */       .invert();
/* 106 */     this.pvmMatrix.multiply(modelMatrix);
/*     */   }
/*     */   
/*     */   public boolean test(@Nonnull Vector4d point, @Nonnull Matrix4d modelMatrix) {
/* 110 */     setupMatrices(modelMatrix);
/* 111 */     return testPoint(point);
/*     */   }
/*     */   
/*     */   public boolean test(@Nonnull Quad4d[] model, @Nonnull Matrix4d modelMatrix) {
/*     */     try {
/* 116 */       setupMatrices(modelMatrix);
/* 117 */       return testModel(model);
/* 118 */     } catch (Throwable t) {
/*     */       
/* 120 */       ((HytaleLogger.Api)log.at(Level.SEVERE).withCause(t)).log("Error occured during Hit Detection execution. Dumping parameters!");
/* 121 */       log.at(Level.SEVERE).log("this = %s", this);
/* 122 */       log.at(Level.SEVERE).log("model = %s", Arrays.toString((Object[])model));
/* 123 */       log.at(Level.SEVERE).log("modelMatrix = %s", modelMatrix);
/* 124 */       log.at(Level.SEVERE).log("thread = %s", Thread.currentThread().getName());
/* 125 */       throw t;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean testPoint(@Nonnull Vector4d point) {
/* 130 */     this.pvmMatrix.multiply(point, this.buffer.transformedPoint);
/* 131 */     if (!this.buffer.transformedPoint.isInsideFrustum()) return false;
/*     */     
/* 133 */     Vector4d hit = this.buffer.transformedPoint;
/* 134 */     this.invPvMatrix.multiply(hit);
/* 135 */     hit.perspectiveTransform();
/*     */     
/* 137 */     return this.losProvider.test(this.origin.x, this.origin.y, this.origin.z, hit.x, hit.y, hit.z);
/*     */   }
/*     */   
/*     */   private boolean testModel(@Nonnull Quad4d[] model) {
/* 141 */     int testsDone = 0;
/* 142 */     double minDistanceSquared = Double.POSITIVE_INFINITY;
/* 143 */     for (Quad4d quad : model) {
/*     */       
/* 145 */       if (testsDone++ == this.maxRayTests) {
/* 146 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 150 */       quad.multiply(this.pvmMatrix, this.buffer.transformedQuad);
/*     */       
/* 152 */       if (insideFrustum()) {
/* 153 */         Vector4d hit = this.buffer.tempHitPosition;
/* 154 */         if (this.buffer.containsFully) {
/* 155 */           this.buffer.transformedQuad.getRandom(this.buffer.random, hit);
/*     */         } else {
/* 157 */           this.buffer.visibleTriangle.getRandom(this.buffer.random, hit);
/*     */         } 
/* 159 */         this.invPvMatrix.multiply(hit);
/* 160 */         hit.perspectiveTransform();
/*     */         
/* 162 */         double dx = this.origin.x - hit.x;
/* 163 */         double dy = this.origin.y - hit.y;
/* 164 */         double dz = this.origin.z - hit.z;
/* 165 */         double distanceSquared = dx * dx + dy * dy + dz * dz;
/* 166 */         if (distanceSquared < minDistanceSquared)
/*     */         {
/*     */ 
/*     */           
/* 170 */           if (this.losProvider.test(this.origin.x, this.origin.y, this.origin.z, hit.x, hit.y, hit.z)) {
/* 171 */             minDistanceSquared = distanceSquared;
/* 172 */             this.buffer.hitPosition.assign(hit);
/*     */           }  } 
/*     */       } 
/*     */     } 
/* 176 */     return (minDistanceSquared != Double.POSITIVE_INFINITY);
/*     */   }
/*     */   
/*     */   protected boolean insideFrustum() {
/* 180 */     Quad4d quad = this.buffer.transformedQuad;
/* 181 */     if (quad.isFullyInsideFrustum()) {
/*     */       
/* 183 */       this.buffer.containsFully = true;
/* 184 */       return true;
/*     */     } 
/* 186 */     this.buffer.containsFully = false;
/*     */     
/* 188 */     Vector4dBufferList vertices = this.buffer.vertexList1;
/* 189 */     Vector4dBufferList auxillaryList = this.buffer.vertexList2;
/* 190 */     vertices.clear();
/* 191 */     auxillaryList.clear();
/*     */     
/* 193 */     vertices.next().assign(quad.getA());
/* 194 */     vertices.next().assign(quad.getB());
/* 195 */     vertices.next().assign(quad.getC());
/* 196 */     vertices.next().assign(quad.getD());
/*     */     
/* 198 */     if (clipPolygonAxis(0) && 
/* 199 */       clipPolygonAxis(1) && 
/* 200 */       clipPolygonAxis(2)) {
/* 201 */       Vector4d initialVertex = vertices.get(0);
/*     */       
/* 203 */       int i = 1; if (i < vertices.size() - 1) {
/* 204 */         Triangle4d triangle = this.buffer.visibleTriangle;
/* 205 */         triangle.assign(initialVertex, vertices.get(i), vertices.get(i + 1));
/*     */         
/* 207 */         return true;
/*     */       } 
/*     */     } 
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean clipPolygonAxis(int componentIndex) {
/* 215 */     clipPolygonComponent(this.buffer.vertexList1, componentIndex, 1.0D, this.buffer.vertexList2);
/* 216 */     this.buffer.vertexList1.clear();
/*     */     
/* 218 */     if (this.buffer.vertexList2.isEmpty()) {
/* 219 */       return false;
/*     */     }
/*     */     
/* 222 */     clipPolygonComponent(this.buffer.vertexList2, componentIndex, -1.0D, this.buffer.vertexList1);
/* 223 */     this.buffer.vertexList2.clear();
/*     */     
/* 225 */     return !this.buffer.vertexList1.isEmpty();
/*     */   }
/*     */   
/*     */   private static void clipPolygonComponent(@Nonnull Vector4dBufferList vertices, int componentIndex, double componentFactor, @Nonnull Vector4dBufferList result) {
/* 229 */     Vector4d previousVertex = vertices.get(vertices.size() - 1);
/* 230 */     double previousComponent = previousVertex.get(componentIndex) * componentFactor;
/* 231 */     boolean previousInside = (previousComponent <= previousVertex.w);
/*     */     
/* 233 */     for (int i = 0; i < vertices.size(); i++) {
/* 234 */       Vector4d vertex = vertices.get(i);
/* 235 */       double component = vertex.get(componentIndex) * componentFactor;
/* 236 */       boolean inside = (component <= vertex.w);
/*     */       
/* 238 */       if (inside ^ previousInside) {
/* 239 */         double lerp = (previousVertex.w - previousComponent) / (previousVertex.w - previousComponent - vertex.w - component);
/*     */ 
/*     */         
/* 242 */         previousVertex.lerp(vertex, lerp, result.next());
/*     */       } 
/*     */       
/* 245 */       if (inside) {
/* 246 */         result.next().assign(vertex);
/*     */       }
/*     */       
/* 249 */       previousVertex = vertex;
/* 250 */       previousComponent = component;
/* 251 */       previousInside = inside;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 258 */     return "HitDetectionExecutor{pvmMatrix=" + String.valueOf(this.pvmMatrix) + ", invPvMatrix=" + String.valueOf(this.invPvMatrix) + ", origin=" + String.valueOf(this.origin) + ", buffer=" + String.valueOf(this.buffer) + ", projectionProvider=" + String.valueOf(this.projectionProvider) + ", viewProvider=" + String.valueOf(this.viewProvider) + ", losProvider=" + String.valueOf(this.losProvider) + ", maxRayTests=" + this.maxRayTests + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\hitdetection\HitDetectionExecutor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */