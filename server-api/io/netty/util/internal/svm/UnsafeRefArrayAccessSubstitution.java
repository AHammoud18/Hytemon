package io.netty.util.internal.svm;

import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.RecomputeFieldValue;
import com.oracle.svm.core.annotate.TargetClass;

@TargetClass(className = "io.netty.util.internal.shaded.org.jctools.util.UnsafeRefArrayAccess")
final class UnsafeRefArrayAccessSubstitution {
  @Alias
  @RecomputeFieldValue(kind = RecomputeFieldValue.Kind.ArrayIndexShift, declClass = Object[].class)
  public static int REF_ELEMENT_SHIFT;
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\svm\UnsafeRefArrayAccessSubstitution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */