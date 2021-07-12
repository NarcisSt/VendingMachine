package Shell;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Command {
    private CommandEnum commandEnum;
    private String command;
}
