package br.ufmg.dcc.pm.saracura.util;

import java.util.Objects;


/**
 * An immutable triple.
 */
public class Triple<T, U, V> {
  /**
   * The first element in the triple.
   */
  public final T first;
  /**
   * The second element in the triple.
   */
  public final U second;
  /**
   * The third element in the triple.
   */
  public final V third;



  /**
   * Creates a triple.
   * @param first  the first element
   * @param second the second element
   */
  public Triple(T first, U second, V third) {
    this.first = first;
    this.second = second;
    this.third = third;
  }



  @Override
  public boolean equals(Object o) {
    if (o == null || !(o instanceof Triple))
      return false;

    final var obj = (Triple<?, ?, ?>) o;

    if (this.first == obj.first && this.second == obj.second && this.third == obj.third)
      return true;

    if (this.first == null || this.second == null || this.third == null)
      return false;

    return this.first.equals(obj.first)
        && this.second.equals(obj.second)
        && this.third.equals(obj.third);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.first, this.second, this.third);
  }

  public String toString() {
    return "Triple(" + this.first + ", " + this.second + ", " + this.third + ")";
  }
}
