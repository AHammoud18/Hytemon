package io.netty.util.internal.svm;

import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.RecomputeFieldValue;
import com.oracle.svm.core.annotate.TargetClass;

@TargetClass(className = "io.netty.util.internal.RefCnt$UnsafeRefCnt")
final class RefCntSubstitution {
  @Alias
  @RecomputeFieldValue(kind = RecomputeFieldValue.Kind.FieldOffset, declClassName = "io.netty.util.internal.RefCnt", name = "value")
  public static long VALUE_OFFSET;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\svm\RefCntSubstitution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */