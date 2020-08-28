package picttranslate.parser;

import java.util.List;

public class PictCustomVisitor extends PictBaseVisitor{

    @Override
    public String visitStart(PictParser.StartContext ctx) {
        StringBuilder result = new StringBuilder();
        result.append("Parameters: \n");
        for (PictParser.ParameterContext parCtx : ctx.parameter()) {
            result.append(visitParameter(parCtx));
            result.append("\n");
        }
        result.append("\nConstraints: \n");
        for (PictParser.ConstraintContext consCtx : ctx.constraint()){
            result.append("# ");
            result.append(visitConstraint(consCtx));
            result.append(" #\n");
        }
        return result.toString();
    }

    @Override
    public String visitParameter(PictParser.ParameterContext ctx) {
        List<PictParser.ValueContext> values = ctx.value();
        StringBuilder line = new StringBuilder();
        line.append(visitValue(values.get(0))).append(" : { ");
        values.remove(0);
        for (PictParser.ValueContext valCtx : values) {
            line.append(visitValue(valCtx)).append(" ");
        }
        line.append("};");
        return line.toString();
    }

    @Override
    public String visitValue(PictParser.ValueContext ctx) {
        return ctx.TESTO().getText();
    }

    @Override
    public String visitConstraint(PictParser.ConstraintContext ctx) {
        StringBuilder line = new StringBuilder();
        if (ctx.iftext() != null){
            line.append(visitPredicate(ctx.predicate(0)));
            line.append(" => ");
            line.append(visitPredicate(ctx.predicate(1)));
        }else {
            line.append(visitPredicate(ctx.predicate(0)));
        }
        return line.toString();
    }

    @Override
    public String visitPredicate(PictParser.PredicateContext ctx) {
        if (ctx.logicalOperator() == null){
            return visitClause(ctx.clause());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("(").append(visitClause(ctx.clause()));
            sb.append(" ").append(visitLogicalOperator(ctx.logicalOperator())).append(" ");
            sb.append(visitPredicate(ctx.predicate())).append(")");
            return sb.toString();
        }
    }

    @Override
    public String visitClause(PictParser.ClauseContext ctx) {
        if (ctx.predicate() != null) {
            return visitPredicate(ctx.predicate());
        }
        else{
            return visitTerm(ctx.term());
        }
    }

    @Override
    public String visitTerm(PictParser.TermContext ctx) {
        StringBuilder sb = new StringBuilder();
        if (ctx.relation() != null){
            sb.append(visitValue(ctx.value(0)));
            sb.append(visitRelation(ctx.relation()));
            sb.append(visitValue(ctx.value(1)));
        } else {
            String mathOperator, logicOperator;
            if (ctx.inClause() != null){
                mathOperator = "=" ;
                logicOperator = " OR ";
            } else {
                mathOperator = "!=" ;
                logicOperator = " AND ";
            }
            String param = visitValue(ctx.value(0));
            sb.append("(");
            sb.append(param).append(mathOperator).append(visitValue(ctx.value(1)));
            for (int i = 2; i < ctx.value().size(); i++) {
                sb.append(logicOperator);
                sb.append(param).append(mathOperator).append(visitValue(ctx.value(i)));
            }
            sb.append(")");
        }
        return sb.toString();
    }

    @Override
    public Object visitRelation(PictParser.RelationContext ctx) {
        if (ctx.getText().equals("<>"))
            return "!=";
        return ctx.getText();
    }

    @Override
    public Object visitLogicalOperator(PictParser.LogicalOperatorContext ctx) {
        if (ctx.getText().equals("and"))
            return "AND";
        else
            return "OR";
    }
}
