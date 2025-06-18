import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

enum Order {
  INCREASING, DECREASING
}

static final Pattern SPACES = Pattern.compile(" +");

int[] parseLine(String line) {
  Objects.requireNonNull(line, "Line is null.");
  return Arrays.stream(SPACES.split(line)).mapToInt(Integer::parseInt).toArray();
}

Order order(int left, int right) {
  return left < right ? Order.INCREASING : Order.DECREASING;
}

boolean isLevelValid(int left, int right) {
  var level = Math.abs(left - right);
  return level >= 1 && level <= 3;
}

boolean isArrayValid(String line) {
  Objects.requireNonNull(line, "Line is null.");
  var array = parseLine(line);
  var order = (Order) null;
  for (var i = 0; i < array.length - 1; i++) {
    var left = array[i];
    var right = array[i + 1];
    var currentOrder = order(left, right);
    if (!isLevelValid(left, right) || (order != null && order != currentOrder)) {
      return false;
    }
    order = currentOrder;
  }
  return true;
}

String removeLevel(String line, int index) {
  Objects.requireNonNull(line, "Line is null.");
  var parsedLine = SPACES.split(line);
  var newString = new StringBuilder();
  for (var i = 0; i < parsedLine.length; i++) {
    if (i != index) {
      newString.append(parsedLine[i]).append(" ");
    }
  }
  return newString.toString();
}

boolean isArrayValidWithDampener(String line) {
  Objects.requireNonNull(line, "Line is null.");
  if (isArrayValid(line)) {
    return true;
  }
  var parsedLine = SPACES.split(line);
  return IntStream.range(0, parsedLine.length).anyMatch(i -> isArrayValid(removeLevel(line, i)));
}

void main(String... args) {
  var path = Path.of("input", "Day2_input.txt");
  try (var reader = Files.newBufferedReader(path)) {
    var lines = reader.lines().toList(); // "It can't be done with a single stream as in Part 1, so I stored the lines
                                         // in memory for multiple uses."
    // PART 1
    System.out.println(lines.stream().filter(line -> isArrayValid(line)).count());
    // PART 2
    System.out.println(lines.stream().filter(line -> isArrayValidWithDampener(line)).count());
  } catch (IOException e) {
    System.out.println(e.getMessage());
  }
}