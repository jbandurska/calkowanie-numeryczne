import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class MySparseMatrix extends MyMatrix {
  // Macierz to lista wierszy (słowników)
  ArrayList<Map<Integer, MutableDouble>> matrix;

  public MySparseMatrix(int rows, int columns) {
    super(rows, columns);
    matrix = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      matrix.add(i, new HashMap<Integer, MutableDouble>());
    }
  }

  protected FieldFill canFillField(int row, int column) {
    if (row == column)
      return FieldFill.MUST_FILL;

    return FieldFill.CAN_FILL;
  }

  protected double getItem(int row, int column) {
    Map<Integer, MutableDouble> matrixRow = matrix.get(row);

    if (matrixRow.containsKey(column)) {
      return matrixRow.get(column).getValue();
    }

    return 0.0;
  }

  protected void setItem(int row, int column, double value) {
    setNotSolved();

    // Jeśli value jest równe zero, usuwamy to pole
    if (value == 0.0) {
      matrix.get(row).remove(column);
      return;
    }

    Map<Integer, MutableDouble> matrixRow = matrix.get(row);

    if (matrixRow.containsKey(column)) {
      matrixRow.get(column).setValue(value);
    } else {
      matrixRow.put(column, new MutableDouble(value));
    }
  }

  void swapRows(int first, int second) {
    Map<Integer, MutableDouble> firstRow = matrix.get(first);
    Map<Integer, MutableDouble> secondRow = matrix.get(first);
    matrix.set(first, secondRow);
    matrix.set(second, firstRow);

    double firstAbs = absoluteTerms[first];
    absoluteTerms[first] = absoluteTerms[second];
    absoluteTerms[second] = firstAbs;
  }

  void multiplyRow(int row, double value) {
    Map<Integer, MutableDouble> currRow = matrix.get(row);

    for (Integer cellColumn : currRow.keySet()) {
      MutableDouble currCell = currRow.get(cellColumn);
      currCell.setValue(currCell.getValue() * value);
    }

    absoluteTerms[row] *= value;
  }

  void addRows(int first, int second, double secondMult) {
    Map<Integer, MutableDouble> secondRow = matrix.get(second);

    for (Map.Entry<Integer, MutableDouble> row : secondRow.entrySet()) {
      Integer column = row.getKey();

      Double firstValue = getItem(first, column);
      Double secondValue = row.getValue().getValue();
      Double changedValue = firstValue + secondValue * secondMult;

      setItem(first, column, changedValue);
    }

    absoluteTerms[first] += secondMult * absoluteTerms[second];
  }
}