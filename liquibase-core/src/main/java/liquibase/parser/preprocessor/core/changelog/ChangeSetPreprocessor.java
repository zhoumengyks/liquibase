package liquibase.parser.preprocessor.core.changelog;

import liquibase.Scope;
import liquibase.change.Change;
import liquibase.change.ChangeFactory;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.AbstractParsedNodePreprocessor;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.precondition.PreconditionFactory;

import java.util.SortedSet;

/**
 * {@link liquibase.changelog.ChangeLog} related preprocessing.
 */
public class ChangeSetPreprocessor extends AbstractParsedNodePreprocessor {

    @Override
    public Class<? extends ParsedNodePreprocessor>[] mustBeBefore() {
        return new Class[] {
                ChangeLogPreprocessor.class
        };
    }

    @Override
    public void process(ParsedNode node) throws ParseException {
        SortedSet<String> changeNames = Scope.getCurrentScope().getSingleton(ChangeFactory.class).getDefinedChanges();

        for (ParsedNode changesetNode : node.getChildren("changeSet", true)) {
            ParsedNode changes = changesetNode.getChild("changes", true);
            for (String changeName : changeNames) {
                changesetNode.moveChildren(changeName, changes);
            }
        }
    }
}
