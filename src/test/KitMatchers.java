package test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Contains Matchers that are not provided {@code #org.hamcrest.CoreMatchers} but needed by a test.
 *
 * @author Joshua Gleitze
 * @version 1.0
 * @since 02.02.2015
 */
public class KitMatchers {

	/**
	 * Creates a matcher that matches if the examined String contains exactly the elements in the specified String
	 * Array, separated exactly by {@code divider}.
	 *
	 * @param substrings
	 *            the substrings that the returned matcher will expect to be contained by any examined string
	 * @param divider
	 *            the divider String that the returned matcher will expect the Strings of {@code substrings} to be
	 *            divided by in any examined string
	 */
	public static Matcher<String> containsExactlyDividedBy(final String[] substrings, final String divider) {
		return new BaseMatcher<String>() {
			private List<String> notContained = new LinkedList<>(Arrays.asList(substrings));
			private boolean hadTooMuch;

			@Override
			public boolean matches(final Object testObject) {
				String testString = (String) testObject;
				boolean contains = true;
				int length = 0;
				// we don't break at a mismatch as we want to have the notContained list ready
				for (String sub : substrings) {
					length += sub.length();
					int innerIndex = testString.indexOf(divider + sub + divider);
					int lastItemIndex = (testString.length() - sub.length() - divider.length());

					boolean found = ((innerIndex > 0) && (innerIndex < lastItemIndex)); // as inner
					found = found || (testString.indexOf(sub + divider) == 0); // at start
					found = found || (testString.indexOf(divider + sub) == lastItemIndex); // at end

					if (found) {
						notContained.remove(sub);
					}

					contains = contains && found;
				}

				length += (substrings.length - 1) * divider.length();
				hadTooMuch = contains && (testString.length() != length);
				return contains && !hadTooMuch;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("A String containing exactly ").appendValueList("", ", ", "", substrings)
						.appendText(" divided exactly by ").appendValue(divider);
			}

			@Override
			public void describeMismatch(final Object item, final Description description) {
				description.appendText("was ").appendValue(item);
				if (!notContained.isEmpty()) {
					description.appendText("\nnot containing these items correctly: ").appendValueList("", ", ", "",
						notContained);
				}
				if (hadTooMuch) {
					description.appendText("\nhaving to many characters");
				}
			}

		};
	}

	/**
	 * Creates a matcher that matches if the examined String array contains exactly as much elements as specified by
	 * {@code outputLines}. This matcher is meant to assert a tested class' output. It provides a appropriate error
	 * message.
	 *
	 * @param outputLines
	 *            How many lines the tested class should print.
	 * @param outputDescription
	 *            A description of the output that was expected from the tested class.
	 */
	public static Matcher<String[]> hasExcactlyThatMuchLines(final int outputLines, final String outputDescription) {
		return new BaseMatcher<String[]>() {
			private String[] providedLines;

			@Override
			public boolean matches(final Object testObject) {
				providedLines = (String[]) testObject;
				return providedLines.length == outputLines;
			}

			@Override
			public void describeTo(Description description) {
				description.appendText(outputDescription);
			}

			@Override
			public void describeMismatch(final Object item, final Description description) {
				description.appendText("found ").appendText((providedLines.length == 1) ? " call" : " calls")
						.appendText(" to Terminal.printLine");
			}

		};
	}
}
