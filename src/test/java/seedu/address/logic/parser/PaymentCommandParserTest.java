package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PaymentCommand;
import seedu.address.model.person.Payment;
import seedu.address.model.person.PaymentFilterPredicate;


public class PaymentCommandParserTest {

    private PaymentCommandParser parser = new PaymentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaymentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no trailing whitespaces
        PaymentCommand expectedPaymentCommand =
                new PaymentCommand(new PaymentFilterPredicate(new Payment("Paid")));
        assertParseSuccess(parser, " pa/paid", expectedPaymentCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n pa/ \n \t paid  \t", expectedPaymentCommand);
    }

}
