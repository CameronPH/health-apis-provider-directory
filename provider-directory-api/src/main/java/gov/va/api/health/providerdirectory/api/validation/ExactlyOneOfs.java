package gov.va.api.health.providerdirectory.api.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * A way to have multiple groups of fields for validation.
 *
 * <p>Consider the following example.
 *
 * <ul>
 *   <li>a and b , c and d are separate groups that are looked at for ZeroOrOneOf validation
 * </ul>
 *
 * <pre>
 *  &#064RelatedFields({
 *  &#064;ZeroOrOneOf({"a","b"})
 *  &#064;ZeroOrOneOf({"c","d"})
 *  })
 * class Foo {
 *   String a;
 *   String b;
 *   String c;
 *   String d;
 * }
 * </pre>
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface ExactlyOneOfs {
  /** Collections of related field annotations. */
  ExactlyOneOf[] value();
}
