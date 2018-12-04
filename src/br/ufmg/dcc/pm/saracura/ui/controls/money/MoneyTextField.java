package br.ufmg.dcc.pm.saracura.ui.controls.money;

import java.awt.Toolkit;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;


/**
 * A text field for monetary values.
 */
public class MoneyTextField extends JTextField {
  /**
   * The money's format.
   */
  protected DecimalFormat format = new DecimalFormat("###,##0.00");
  /**
   * The decimal's separator.
   */
  protected String decimal;



  /**
   * Creates a MoneyTextField.
   */
  public MoneyTextField() {
    this.decimal = Character.toString(
      this.format.getDecimalFormatSymbols().getDecimalSeparator()
    );

    this.setColumns(format.toPattern().length());
    this.setHorizontalAlignment(JFormattedTextField.TRAILING);
    this.setText(format.format(0.0));

    AbstractDocument doc = (AbstractDocument) this.getDocument();
    doc.setDocumentFilter(new ABMFilter());
  }



  /**
   * A getter to the money input
   */
  public BigDecimal getValue() {
    try{
      BigDecimal value = new BigDecimal((this.format.parse(this.getText())).toString());
      return value;
    }catch(Exception e){
      return null;
    }
  }

  @Override
  public void setText(String text) {
    Number number = this.format.parse(text, new ParsePosition(0));

    if (number != null)
      super.setText(text);
  }



  /**
   * The DocumentFilter for the monetary format.
   */
  protected class ABMFilter extends DocumentFilter {
    public void insertString(
      FilterBypass fb,
      int offs,
      String str,
      AttributeSet a
    ) throws BadLocationException {
      replace(fb, offs, 0, str, a);
    }


    public void replace(
      FilterBypass fb,
      int offs,
      int length,
      String str,
      AttributeSet a
    ) throws BadLocationException {
      if ("0123456789".contains(str)) {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder(doc.getText(0, doc.getLength()));

        int decimalOffset = sb.indexOf(decimal);

        if (decimalOffset != -1) {
          sb.deleteCharAt(decimalOffset);
          sb.insert(decimalOffset + 1, decimal);
        }

        sb.append(str);

        try {
          String text = format.format(format.parse(sb.toString()));
          super.replace(fb, 0, doc.getLength(), text, a);
        }
        catch(ParseException e) { }
      }
      else
        Toolkit.getDefaultToolkit().beep();
    }


    public void remove(
      DocumentFilter.FilterBypass fb,
      int offset,
      int length
    ) throws BadLocationException {
      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder(doc.getText(0, doc.getLength()));

      int decimalOffset = sb.indexOf(decimal);

      if (decimalOffset != -1) {
        sb.deleteCharAt(decimalOffset);
        sb.insert(decimalOffset - 1, decimal);
      }

      sb.deleteCharAt(sb.length() - 1) ;

      try {
        String text = format.format(format.parse(sb.toString()));
        super.replace(fb, 0, doc.getLength(), text, null);
      }
      catch(ParseException e) { }
    }
  }
}
