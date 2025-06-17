import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

static final Pattern SPACES = Pattern.compile(" +");

record DistancePair(int left, int right) {

}

DistancePair parseDistancePair(String line) {
  var tokens = SPACES.split(line);
  var leftValue = Integer.parseInt(tokens[0]);
  var rightValue = Integer.parseInt(tokens[1]);
  return new DistancePair(leftValue, rightValue);
}

int distance(int left, int right) {
  return Math.abs(left - right);
}

int distanceSum(int[] lefts, int[] rights) {
  return IntStream.range(0, lefts.length).map(i -> distance(lefts[i], rights[i])).sum();
}

void main(String... args) {
  var path = Path.of("input", "Day1_input.txt");
  try (var reader = Files.newBufferedReader(path)) {
    var pairs = reader.lines().map(this::parseDistancePair).toList();
    var lefts = pairs.stream().mapToInt(DistancePair::left).sorted().toArray();
    var rights = pairs.stream().mapToInt(DistancePair::right).sorted().toArray();
    System.out.println(distanceSum(lefts, rights));
  } catch (IOException e) {
    System.out.println(e.getMessage());
    System.exit(1);
  }
}