package br.ufmg.dcc.pm.saracura.clinic;


/**
 * The Schedulable interface provides a relationship between operators and cooperators in
 * an exam. As both have an agenda, a scheduled exam must be present on both agendas. This
 * interface provides a method to get the correlated agenda from an operator.
 */
public interface Schedulable<
  Operator extends Schedulable<Operator, Cooperator>,
  Cooperator extends Schedulable<Cooperator, Operator>
> {
  /**
   * Get the operator's agenda, related to the cooperator.
   */
  public Agenda<Operator, Cooperator> getAgenda();
}
