import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

static final Pattern CORRECTMULPATTERN = Pattern.compile("mul\\((\\d{1,3})\\,(\\d{1,3})\\)");
static final Pattern INSTRUCTIONSPATTERN = Pattern.compile("mul\\((\\d{1,3})\\,(\\d{1,3})\\)|do\\(\\)|don't\\(\\)");

void main() {
  var path = Path.of("input", "Day3_input.txt");
  try (var reader = Files.newBufferedReader(path)) {
    // PART 1
    var input = reader.lines().collect(Collectors.joining());
    System.out.println(CORRECTMULPATTERN.matcher(input).results()
        .mapToInt(result -> Integer.parseInt(result.group(1)) * Integer.parseInt(result.group(2))).sum());
    // PART 2
    var instructionsEnabled = true;
    var total = 0;
    var matcher = INSTRUCTIONSPATTERN.matcher(input);
    while (matcher.find()) {
      switch (matcher.group()) {
      case "do()" -> instructionsEnabled = true;
      case "don't()" -> instructionsEnabled = false;
      default -> {
        if (instructionsEnabled)
          total += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
      }
      }
    }
    System.out.println(total);
  } catch (IOException e) {
    System.out.println(e.getMessage());
  }
}