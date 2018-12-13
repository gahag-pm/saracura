package br.ufmg.dcc.pm.saracura.util;

import java.util.Objects;


/**
 * An immutable tuple.
 */
public class Tuple<T, U> {
  /**
   * The first element in the tuple.
   */
  public final T first;
  /**
   * The second element in the tuple.
   */
  public final U second;



  /**
   * Creates a tuple.
   * @param first  the first element
   * @param second the second element
   */
  public Tuple(T first, U second) {
    this.first = first;
    this.second = second;
  }



  @Override
  public boolean equals(Object o) {
    if (o == null || !(o instanceof Tuple))
      return false;

    final var obj = (Tuple<?, ?>) o;

    if (this.first == obj.first && this.second == obj.second)
      return true;

    if (this.first == null || this.second == null)
      return false;

    return this.first.equals(obj.first)
        && this.second.equals(obj.second);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.first, this.second);
  }

  public String toString() {
    return "Tuple(" + this.first + ", " + this.second + ")";
  }
}
