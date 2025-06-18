import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Pattern;

enum Order {
  INCREASING, DECREASING, INVALID
}

static final Pattern SPACES = Pattern.compile(" +");

int[] parseLine(String line) {
  return Arrays.stream(SPACES.split(line)).mapToInt(Integer::parseInt).toArray();
}

Order order(int left, int right) {
  return left < right ? Order.INCREASING : Order.DECREASING;
}

boolean isLevelValid(int left, int right) {
  var level = Math.abs(left - right);
  return level >= 1 && level <= 3;
}

boolean isArrayValid(int[] array) {
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

void main(String... args) {
  var path = Path.of("input", "Day2_input.txt");
  try (var reader = Files.newBufferedReader(path)) {
    System.out.println(reader.lines().filter(line -> isArrayValid(parseLine(line))).count());
  } catch (IOException e) {
    System.out.println(e.getMessage());
  }
}