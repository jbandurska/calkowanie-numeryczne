import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Optional;

abstract class MyMatrix {
  protected int numberOfRows;
  protected int numberOfColumns;
  protected boolean isSolved;
  protected double[] absoluteTerms;

  public enum FieldFill {
    NO_FILL, CAN_FILL, MUST_FILL
  }

  public MyMatrix(int rows, int columns) {
    this.numberOfRows = rows;
    this.numberOfColumns = columns;
    this.isSolved = false;
    this.absoluteTerms = new double[rows];
  }

  public String toString() {
    StringBuilder matrixString = new StringBuilder();
    NumberFormat matrixCellFormat = new DecimalFormat("#0.0000");

    for (int i = 0; i < numberOfRows; i++) {
      for (int j = 0; j < numberOfColumns; j++) {
        double cell = getItem(i, j);
        matrixString.append(matrixCellFormat.format(cell));
        matrixString.append(" ");
      }
      matrixString.append("\n");
    }

    return matrixString.toString();
  }

  public Optional<double[]> getSolution() {
    if (!isSolved)
      return Optional.empty();

    return Optional.of(this.absoluteTerms);
  }

  double[] solveGauss() {
    return solveGauss(false);
  }

  double[] solveGauss(boolean partialChoice) {
    for (int i = 0; i < numberOfRows; i++) {
      if (partialChoice)
        swapRowLargestLeading(i);

      clearColumn(i);
      makeLeadingElementOne(i);
    }

    setSolved();
    return absoluteTerms;
  }

  void swapRowLargestLeading(int currentDiagonal) {
    int row = -1;
    double largestValue = 0.0;

    for (int i = currentDiagonal; i < numberOfRows; i++) {
      double elem = Math.abs(getItem(i, currentDiagonal));

      if (elem > largestValue) {
        largestValue = elem;
        row = i;
      }
    }

    if (row != -1)
      swapRows(currentDiagonal, row);
  }

  void clearColumn(int currentDiagonal) {
    double value = getItem(currentDiagonal, currentDiagonal);

    for (int i = 0; i < numberOfRows; i++) {
      if (i != currentDiagonal) {
        double elem = getItem(i, currentDiagonal);

        if (elem != 0.0) {
          double multiply = (-elem) / value;
          addRows(i, currentDiagonal, multiply);
          setItem(i, currentDiagonal, 0.0);
        }
      }
    }
  }

  void makeLeadingElementOne(int currentDiagonal) {
    double value = getItem(currentDiagonal, currentDiagonal);
    multiplyRow(currentDiagonal, 1 / value);
  }

  void swapRows(int first, int second) {
    for (int i = 0; i < numberOfColumns; i++) {
      double val1 = getItem(first, i);
      double val2 = getItem(second, i);

      setItem(first, i, val2);
      setItem(second, i, val1);
    }

    double firstAbs = absoluteTerms[first];
    absoluteTerms[first] = absoluteTerms[second];
    absoluteTerms[second] = firstAbs;
  }

  void multiplyRow(int row, double value) {
    for (int i = 0; i < numberOfColumns; i++) {
      double val = getItem(row, i);
      setItem(row, i, val * value);
    }
    absoluteTerms[row] *= value;
  }

  void addRows(int first, int second, double secondMult) {
    for (int i = 0; i < numberOfColumns; i++) {
      double val1 = getItem(first, i);
      double val2 = getItem(second, i);

      setItem(first, i, val1 + val2 * secondMult);
    }
    absoluteTerms[first] += secondMult * absoluteTerms[second];
  }

  void printMatrix() {
    System.out.println(this.toString());

    if (isSolved()) {
      System.out.println("Solution: " + Arrays.toString(getSolution().get()));
    } else {
      System.out.println("Matrix is not solved");
    }

    System.out.println("AbsoluteTerms: " + Arrays.toString(absoluteTerms) + "\n");
  }

  void setNotSolved() {
    this.isSolved = false;
  }

  void setSolved() {
    this.isSolved = true;
  }

  boolean isSolved() {
    return this.isSolved;
  }

  protected abstract double getItem(int row, int column);

  protected abstract void setItem(int row, int column, double value);

  protected abstract FieldFill canFillField(int row, int column);
}