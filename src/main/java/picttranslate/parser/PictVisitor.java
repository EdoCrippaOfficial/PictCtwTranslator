// Generated from /home/edo/Documents/PictTranslate/src/main/java/picttranslate/parser/Pict.g4 by ANTLR 4.8
package picttranslate.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PictParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PictVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PictParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(PictParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(PictParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#paramValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamValue(PictParser.ParamValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#submodel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubmodel(PictParser.SubmodelContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#constraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraint(PictParser.ConstraintContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicate(PictParser.PredicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClause(PictParser.ClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(PictParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(PictParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#valueText}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueText(PictParser.ValueTextContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#reusedValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReusedValue(PictParser.ReusedValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#aliasValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAliasValue(PictParser.AliasValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#relation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelation(PictParser.RelationContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#logicalOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOperator(PictParser.LogicalOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#inClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInClause(PictParser.InClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#notInClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotInClause(PictParser.NotInClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#iftext}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIftext(PictParser.IftextContext ctx);
	/**
	 * Visit a parse tree produced by {@link PictParser#thentext}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThentext(PictParser.ThentextContext ctx);
}