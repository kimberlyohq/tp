package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_FLAG;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.commands.ViewCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    public static final String FLAG_ANSWER = "a";

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        Index index;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FLAG);

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());

        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_FLAG).isPresent()) {
            if (argMultimap.getValue(PREFIX_FLAG).get().equals(FLAG_ANSWER)) {
                return new ViewCommand(index, true);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
            }
        }

        return new ViewCommand(index, false);

    }

}
