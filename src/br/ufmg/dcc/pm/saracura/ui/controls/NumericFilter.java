package br.ufmg.dcc.pm.saracura.ui.controls;

import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;


/**
 * A DocumentFilter that only allows numbers as input.
 */
public class NumericFilter extends DocumentFilter {
  protected final Pattern numberPattern = Pattern.compile("[0-9]+");



  @Override
  public void insertString(
    FilterBypass fb,
    int offs,
    String str,
    AttributeSet a
  ) throws BadLocationException {
    if (str == null)
      return;

    if (numberPattern.matcher(str).matches())
      super.insertString(fb, offs, str, a);
  }

  @Override
  public void replace(
    FilterBypass fb,
    int offset,
    int length,
    String str,
    AttributeSet attrs
  ) throws BadLocationException {
    if (str == null)
      return;

    if (numberPattern.matcher(str).matches())
      fb.replace(offset, length, str, attrs);
  }
}

