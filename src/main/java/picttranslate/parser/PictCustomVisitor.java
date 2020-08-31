package picttranslate.parser;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PictCustomVisitor extends PictBaseVisitor{

    //  alias, parametro
    HashMap<String, String> aliases = new HashMap<>();
    HashMap<String, List<String>> parameters = new HashMap<>();

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
        if (ctx.submodel() != null)
            return "";
        StringBuilder line = new StringBuilder();
        String paramName = visitValue(ctx.value());
        line.append(paramName).append(" : { ");
        List<PictParser.ParamValueContext> values = ctx.paramValue();
        List<String> stringValues = new ArrayList<>();
        for (PictParser.ParamValueContext valCtx : values) {
            String s = visitParamValue(valCtx);
            stringValues.add(s);
            line.append(s).append(" ");
        }
        parameters.put(paramName, stringValues);
        line.append("};");
        return line.toString();
    }

    @Override
    public String visitParamValue(PictParser.ParamValueContext ctx) {
        if (ctx.aliasValue() != null)
            aliases.put(visitValue(ctx.aliasValue().value()), visitValue(ctx.value()));
        if (ctx.reusedValue() == null)
            return visitValue(ctx.value());
        else{
            String reusedParamName = visitValue(ctx.reusedValue().value());
            if (parameters.containsKey(reusedParamName)){
                StringBuilder sb = new StringBuilder();
                for (String name : parameters.get(reusedParamName)) {
                    sb.append(name).append(" ");
                }
                String result = sb.toString();
                return result.substring(0, result.length()-1);
            }
        }
        return "";
    }

    @Override
    public String visitValue(PictParser.ValueContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (PictParser.ValueTextContext textVal : ctx.valueText()){
            sb.append(visitValueText(textVal));
        }
        String value = sb.toString();
        if (aliases.containsKey(value))
            value = aliases.get(value);
        return value;
    }

    @Override
    public String visitValueText(PictParser.ValueTextContext ctx) {
        StringBuilder sb = new StringBuilder();
        for (TerminalNode text : ctx.TESTO()) {
            sb.append(text.getText());
        }
        return sb.toString();
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
        if (ctx.getText().equals("and") || ctx.getText().equals("AND"))
            return "AND";
        else
            return "OR";
    }
}
