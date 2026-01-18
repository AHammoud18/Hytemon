package org.bouncycastle.oer;

public class DeferredElementSupplier implements ElementSupplier {
  private final OERDefinition.Builder src;
  
  private Element buildProduct;
  
  public DeferredElementSupplier(OERDefinition.Builder paramBuilder) {
    this.src = paramBuilder;
  }
  
  public Element build() {
    synchronized (this) {
      if (this.buildProduct == null)
        this.buildProduct = this.src.build(); 
      return this.buildProduct;
    } 
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\oer\DeferredElementSupplier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */