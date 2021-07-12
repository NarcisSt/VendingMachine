package Shell;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
/**
 * The Command class represents the commands which can be: LIST, COKE, HELL, LAYS, NUTLINE, SNICKERS, MARS, MASK, SANDWICH or QUIT
 */
public class Command {
    private CommandEnum commandEnum;
    private String command;
}
