package seedu.flashcard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.commons.core.Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW;
import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashcard.testutil.TypicalFlashcards.FLASHCARD_1;
import static seedu.flashcard.testutil.TypicalFlashcards.FLASHCARD_2;
import static seedu.flashcard.testutil.TypicalFlashcards.FLASHCARD_3;
import static seedu.flashcard.testutil.TypicalFlashcards.getTypicalFlashcardDeck;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.flashcard.model.Model;
import seedu.flashcard.model.ModelManager;
import seedu.flashcard.model.UserPrefs;
import seedu.flashcard.model.flashcard.QuestionContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalFlashcardDeck(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFlashcardDeck(), new UserPrefs());

    @Test
    public void equals() {
        QuestionContainsKeywordsPredicate firstPredicate =
                new QuestionContainsKeywordsPredicate(Collections.singletonList("first"));
        QuestionContainsKeywordsPredicate secondPredicate =
                new QuestionContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different flashcard -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noFlashcardFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 0);
        QuestionContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFlashcardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFlashcardList());
    }

    @Test
    public void execute_multipleKeywords_multipleFlashcardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 3);
        QuestionContainsKeywordsPredicate predicate = preparePredicate("SDLC control assertions");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFlashcardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FLASHCARD_1, FLASHCARD_2, FLASHCARD_3), model.getFilteredFlashcardList());
    }

    /**
     * Parses {@code userInput} into a {@code QuestionContainsKeywordsPredicate}.
     */
    private QuestionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new QuestionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}